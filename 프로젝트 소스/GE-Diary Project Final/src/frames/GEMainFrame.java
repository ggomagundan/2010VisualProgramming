package frames;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import menus.GEMenuBar;

import constants.GEConstants;

public class GEMainFrame extends JFrame {
	
	private static GEMainFrame uniqueMainFrame = new GEMainFrame(
			GEConstants.TITLE_MAINFRAME);
	private GEDrawingPanel drawingPanel;
	private GEMenuBar menuBar;
	private GEToolbar shapeToolbar;
	private GECal cal;
	
	public GEMainFrame(String title) {
		super(title);
		drawingPanel = new GEDrawingPanel();
		add(drawingPanel);
		menuBar = new GEMenuBar();
		setJMenuBar(menuBar);
		shapeToolbar = new GEToolbar(GEConstants.TITLE_SHAPETOOLBAR);
		add(BorderLayout.NORTH, shapeToolbar);
		cal = new GECal();
		add(BorderLayout.SOUTH, cal);
	}

	public static GEMainFrame getInstance() {
		return uniqueMainFrame;
	}

	public void init() {
		drawingPanel.init(cal);
		cal.init(drawingPanel);
		menuBar.init(cal);
		cal.init(menuBar);
		menuBar.init(drawingPanel);
		shapeToolbar.init(drawingPanel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width, GEConstants.HEIGHT_MAINFRAME);
		setVisible(true);
	}
}