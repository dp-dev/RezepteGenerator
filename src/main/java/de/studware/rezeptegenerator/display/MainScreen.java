package de.studware.rezeptegenerator.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.studware.rezeptegenerator.GeneratorController;

public class MainScreen {
	private static final Logger LOG = Logger.getLogger(MainScreen.class.getName());
	private static final String INSTRUCTIONS = "Kopiere den Link in die Zwischenablage und drücke danach im Menü auf den Punkt: PDF Datei erstellen.";
	private final JFrame frame;
	private JMenuItem miCreatePDF;
	private JMenuItem miExit;
	private JMenuItem miReportError;
	private JProgressBar progressBar;

	public MainScreen(String title) {
		frame = new JFrame(title);
		LOG.info("Displaying screen");
		initFrame();
		initMenuBar();
		initPanels();
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setProgressStatusTo(int value) {
		progressBar.setValue(value);
	}
	
	public int getProgressBarValue() {
		return progressBar.getValue();
	}

	public void setActionListener(GeneratorController controller) {
		miExit.addActionListener(controller);
		miExit.setActionCommand("CLOSE");

		miReportError.addActionListener(controller);
		miReportError.setActionCommand("REPORT_ERROR");

		miCreatePDF.addActionListener(controller);
		miCreatePDF.setActionCommand("CREATE_PDF");
	}

	private void initFrame() {
		frame.setSize(640, 140);
		frame.setMinimumSize(new Dimension(600, 120));
		frame.setMaximumSize(new Dimension(660, 160));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(5, 5));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			LOG.log(Level.SEVERE, "UIManager failed by setup of look and feel of operating system ", e);
		}

	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		// First menu item
		JMenu mFile = new JMenu("Datei");
		mFile.setMnemonic(KeyEvent.VK_D);
		menuBar.add(mFile);

		miCreatePDF = new JMenuItem("PDF Datei erstellen");
		miCreatePDF.setMnemonic(KeyEvent.VK_P);
		mFile.add(miCreatePDF);

		miExit = new JMenuItem("Beenden");
		miExit.setMnemonic(KeyEvent.VK_B);
		mFile.add(miExit);

		// Second menu item
		JMenu mHelp = new JMenu("Hilfe");
		mHelp.setMnemonic(KeyEvent.VK_H);
		menuBar.add(mHelp);

		miReportError = new JMenuItem("Fehler melden");
		miReportError.setMnemonic(KeyEvent.VK_F);
		mHelp.add(miReportError);
	}

	private void initPanels() {
		// Top
		JPanel pTop = new JPanel();
		pTop.setLayout(new BorderLayout(5, 5));
		pTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		frame.add(pTop, BorderLayout.CENTER);

		JLabel lbInfo = new JLabel(INSTRUCTIONS, SwingConstants.CENTER);
		pTop.add(lbInfo, BorderLayout.NORTH);

		// Bottom
		JPanel pBottom = new JPanel();
		pBottom.setLayout(new BorderLayout(5, 5));
		pBottom.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		frame.add(pBottom, BorderLayout.SOUTH);

		progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		pBottom.add(progressBar, BorderLayout.CENTER);

	}

	public void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
	}

}
