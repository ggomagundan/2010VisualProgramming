package menus;

import javax.swing.JMenuBar;

import constants.GEConstants;
import frames.GECal;
import frames.GEDrawingPanel;

public class GEMenuBar extends JMenuBar{

	private GEMenuFile fileMenu;
	private GEMenuEdit editMenu;
	private GEMenuColor colorMenu;
	private GEMenuInfo infoMenu;
	
	public GEMenuBar(){
		fileMenu = new GEMenuFile(GEConstants.TITLE_FILEMENU);
		add(fileMenu);
		editMenu = new GEMenuEdit(GEConstants.TITLE_EDITMENU);
		add(editMenu);
		colorMenu = new GEMenuColor(GEConstants.TITLE_COLOTMENU);
		add(colorMenu);		
		infoMenu = new GEMenuInfo(GEConstants.TITLE_INFOMENU);
		add(infoMenu);
	}
	
	// 초기화 메소드
	public void init(GEDrawingPanel dp){
		colorMenu.init(dp);
		editMenu.init(dp);
		fileMenu.init(dp);
		infoMenu.init(dp);
	}
	
	public void init(GECal cal)
	{
		cal.init(fileMenu);
		fileMenu.init(cal);
	}
}
