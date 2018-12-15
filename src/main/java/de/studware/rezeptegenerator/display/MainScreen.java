package de.studware.rezeptegenerator.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainScreen {
	private static final Logger logger = Logger.getLogger(MainScreen.class.getName());
	private JTextArea taInfo;
	private JFrame frame = new JFrame("Rezepte Generator");
	
	public MainScreen(ScreenEvents screenEvents) {
		screenEvents.addEventToLog(this, "Displaying screen");
		initScreen(screenEvents);
	}
	
	public void showEvents(List<String> events) {
		for (String event : events) {
			taInfo.append(event + System.lineSeparator());
		}
	}
	
	public void resetTextArea() {
		taInfo.setText("");
	}
	
	public JFrame getFrame() {
		return frame;		
	}
	
	private void initScreen(ScreenEvents screenEvents) {
		frame.setSize(640, 480);
		frame.setMinimumSize(new Dimension(550, 300));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout(5, 5));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			logger.log(Level.SEVERE, "UIManager failed by setup of look and feel of operating system ", e);
		}
		
		// Menu
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		// First Menu
		JMenu mFile = new JMenu("Datei");
		mFile.setMnemonic(KeyEvent.VK_D);
		menuBar.add(mFile);
		
		JMenuItem miResetAll = new JMenuItem("Alles zurücksetzen");
		miResetAll.addActionListener(screenEvents);
		miResetAll.setActionCommand("RESET_ALL");
		mFile.add(miResetAll);
		
		mFile.addSeparator();
		
		JMenuItem miExit = new JMenuItem("Beenden");
		miExit.addActionListener(screenEvents);
		miExit.setMnemonic(KeyEvent.VK_B);
		miExit.setActionCommand("CLOSE");
		mFile.add(miExit);
		
		// Second Menu
		JMenu mHelp = new JMenu("Hilfe");
		mHelp.setMnemonic(KeyEvent.VK_H);
		menuBar.add(mHelp);
		
		JMenuItem miReportError = new JMenuItem("Fehler melden");
		miReportError.addActionListener(screenEvents);
		miReportError.setActionCommand("REPORT_ERROR");
		mHelp.add(miReportError);
		
		// Top
		JPanel pTop = new JPanel();
		pTop.setLayout(new BorderLayout(5, 5));
		pTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		frame.add(pTop, BorderLayout.NORTH);
		
		JLabel lbInfo = new JLabel("Link in die Zwischenablage kopieren. Danach Button anklicken, um das PDF Dokument zu erzeugen.", SwingConstants.CENTER);
		pTop.add(lbInfo, BorderLayout.CENTER);
		
		// Center
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new BorderLayout(5, 5));
		pCenter.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		frame.add(pCenter, BorderLayout.CENTER);
		
		taInfo = new JTextArea();
		taInfo.setEditable(false);
		JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportView(taInfo);
		pCenter.add(scroll, BorderLayout.CENTER);
		
		// Bottom
		JPanel pBottom = new JPanel();
		pBottom.setLayout(new BorderLayout(5, 5));
		pBottom.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		frame.add(pBottom, BorderLayout.SOUTH);
		
		JButton btPdf = new JButton("PDF Datei erstellen");
		btPdf.addActionListener(screenEvents);
		btPdf.setActionCommand("CREATE_PDF");
		pBottom.add(btPdf, BorderLayout.CENTER);
		
		frame.setVisible(true);
		btPdf.requestFocus();
	}
	
}
