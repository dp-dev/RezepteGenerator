package de.studware.rezeptegenerator.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import de.studware.rezeptegenerator.GeneratorController;
import de.studware.rezeptegenerator.data.QueueStatus;
import de.studware.rezeptegenerator.util.ParserHandler;

public class BackgroundHandler implements Runnable {
	private static final Logger LOG = Logger.getLogger(BackgroundHandler.class.getName());
	private final ArrayBlockingQueue<QueueStatus> queue = new ArrayBlockingQueue<>(5);
	private final GeneratorController controller;
	private final ConfigHandler config;

	public BackgroundHandler(GeneratorController controller, ConfigHandler config) {
		this.controller = controller;
		this.config = config;
	}

	private void startExecution() {
		Thread thread = null;
		try {
			thread = new Thread(new ParserHandler(queue, config));
			thread.start();
			do {
				updateProgressbar();
			} while (thread.isAlive());
		} catch (InterruptedException e) {
			LOG.severe("Creation of pdf failed due to a problem with the thread. " + e.getMessage());
			if (thread.isAlive()) {
				thread.interrupt();
			}
		}
	}

	private void updateProgressbar() throws InterruptedException {
		QueueStatus current = queue.poll(100, TimeUnit.MILLISECONDS);
		if (current != null) {
			if (current.getPercentage() > 0) {
				controller.getMainscreen().setProgressStatusTo(current.getPercentage());
			} else if (current.getPercentage() == -1) {
				controller.getMainscreen().setProgressStatusTo(100);
				controller.getMainscreen().showErrorMessage(current.getTitle(), current.getMessage());
			}
		}
	}

	@Override
	public void run() {
		startExecution();		
	}

}
