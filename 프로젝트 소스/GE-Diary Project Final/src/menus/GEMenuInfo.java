package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import constants.GEConstants;
import constants.GEConstants.EInfoMenuItems;
import frames.GEDrawingPanel;

public class GEMenuInfo extends JMenu{

	private GEDrawingPanel drawingPanel;
	private InfoMenuHandler infoMenuHandler;

	public GEMenuInfo(String label){
		super(label);	
		infoMenuHandler = new InfoMenuHandler();
		for(EInfoMenuItems btn : EInfoMenuItems.values()){
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(infoMenuHandler);
			menuItem.setActionCommand(btn.toString());
			this.add(menuItem);
		}
	}
	
	public void init(GEDrawingPanel dp){
		drawingPanel = dp;
	}
	
	public void info(){
		JFrame f = new JFrame();
		JOptionPane.showMessageDialog(f, GEConstants.INFO);
	}
	
	private class InfoMenuHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			switch(EInfoMenuItems.valueOf(e.getActionCommand())){
			case 프로그램정보: info(); break;
			}
		}
	}
}
