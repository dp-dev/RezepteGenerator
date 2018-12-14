package de.studware.RezepteGenerator.display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

@SuppressWarnings("serial")
public class MainScreen extends JFrame {
	private Logger logger = Logger.getLogger(MainScreen.class.getName());
	private JScrollPane scroll;
	private JTextArea taInfo;
	private JLabel lbInfo;
	private JButton btPdf;
	
	public MainScreen(ScreenEvents screenEvents) {
		screenEvents.addEventToLog(this, "Displaying screen");
		this.setTitle("Rezepte Generator");
		this.setSize(640, 480);
		this.setMinimumSize(new Dimension(550, 300));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(5, 5));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			logger.log(Level.SEVERE, "UIManager failed by setup of look and feel of operating system ", e);
		}
		
		// Menu
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		// First Menu
		JMenu m_file = new JMenu("Datei");
		m_file.setMnemonic(KeyEvent.VK_D);
		menuBar.add(m_file);
		
		JMenuItem mi_resetAll = new JMenuItem("Alles zurücksetzen");
		mi_resetAll.addActionListener(screenEvents);
		mi_resetAll.setActionCommand("RESET_ALL");
		m_file.add(mi_resetAll);
		
		m_file.addSeparator();
		
		JMenuItem mi_exit = new JMenuItem("Beenden");
		mi_exit.addActionListener(screenEvents);
		mi_exit.setMnemonic(KeyEvent.VK_B);
		mi_exit.setActionCommand("CLOSE");
		m_file.add(mi_exit);
		
		// Second Menu
		JMenu m_help = new JMenu("Hilfe");
		m_help.setMnemonic(KeyEvent.VK_H);
		menuBar.add(m_help);
		
		JMenuItem mi_reportError = new JMenuItem("Fehler melden");
		mi_reportError.addActionListener(screenEvents);
		mi_reportError.setActionCommand("REPORT_ERROR");
		m_help.add(mi_reportError);
		
		// Top
		JPanel pTop = new JPanel();
		pTop.setLayout(new BorderLayout(5, 5));
		pTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		this.add(pTop, BorderLayout.NORTH);
		
		lbInfo = new JLabel("Link in die Zwischenablage kopieren. Danach Button anklicken, um das PDF Dokument zu erzeugen.", SwingConstants.CENTER);
		pTop.add(lbInfo, BorderLayout.CENTER);
		
		// Center
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new BorderLayout(5, 5));
		pCenter.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		this.add(pCenter, BorderLayout.CENTER);
		
		taInfo = new JTextArea();
		taInfo.setEditable(false);
		scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportView(taInfo);
		pCenter.add(scroll, BorderLayout.CENTER);
		
		// Bottom
		JPanel pBottom = new JPanel();
		pBottom.setLayout(new BorderLayout(5, 5));
		pBottom.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		this.add(pBottom, BorderLayout.SOUTH);
		
		btPdf = new JButton("PDF Datei erstellen");
		btPdf.addActionListener(screenEvents);
		btPdf.setActionCommand("CREATE_PDF");
		pBottom.add(btPdf, BorderLayout.CENTER);
		
		this.setVisible(true);
		btPdf.requestFocus();
	}
	
	public void showEvents(ArrayList<String> events) {
		for (String event : events) {
			taInfo.append(event + System.lineSeparator());
		}
	}
	
	public void resetTextArea() {
		taInfo.setText("");
	}
}
