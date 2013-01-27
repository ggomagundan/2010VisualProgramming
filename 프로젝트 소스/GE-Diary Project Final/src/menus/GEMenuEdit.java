package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import shapes.GEGroup;
import shapes.GEShape;

import constants.GEConstants.EEditMenuItems;
import frames.GEDrawingPanel;

public class GEMenuEdit extends JMenu {

	private GEDrawingPanel drawingPanel;
	private EditMenuHandler editMenuHandler;

	public GEMenuEdit(String label) {
		super(label);
		this.setMnemonic(KeyEvent.VK_E);
		editMenuHandler = new EditMenuHandler();
		for (EEditMenuItems btn : EEditMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(editMenuHandler);
			menuItem.setActionCommand(btn.toString());
			setACC(menuItem);
			this.add(menuItem);
		}
	}

	public void init(GEDrawingPanel dp) {
		drawingPanel = dp;
	}

	private void unGroup() {
		drawingPanel.unGroup();
	}

	private void group() {
		drawingPanel.group(new GEGroup());
	}

	private void copy() {
		drawingPanel.copy();
	}

	private void paste() {
		drawingPanel.paste();
	}

	private void cut() {
		drawingPanel.cut();
	}

	private void delete() {
		drawingPanel.delete();
	}

	private void undo() {
		drawingPanel.undo();
	}

	private void redo() {
		drawingPanel.redo();
	}

	private class EditMenuHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch (EEditMenuItems.valueOf(e.getActionCommand())) {
			case group:
				group();
				break;
			case Ungroup:
				unGroup();
				break;
			case Redo:
				redo();
				break;
			case Undo:
				undo();
				break;
			case ����:
				copy();
				break;
			case ���̱�:
				paste();
				break;
			case ����:
				delete();
				break;
			case �߶󳻱�:
				cut();
				break;
			}
		}
	}

	public void setACC(JMenuItem item) {
		if (item.getActionCommand().equals("����")) {
			this.addSeparator();
			item.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
			
		} else if (item.getActionCommand().equals("Undo")) {
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
					InputEvent.CTRL_DOWN_MASK));
			// Undo ����Ű ����
		} else if (item.getActionCommand().equals("Redo")) {
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
					InputEvent.CTRL_DOWN_MASK));
			// Redo ����Ű ����
		} else if (item.getActionCommand().equals("����")) {
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					InputEvent.CTRL_DOWN_MASK));
			// Copy ����Ű ����
		} else if (item.getActionCommand().equals("�߶󳻱�")) {
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					InputEvent.CTRL_DOWN_MASK));
			// Cut ����Ű ����
		} else if (item.getActionCommand().equals("���̱�")) {
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					InputEvent.CTRL_DOWN_MASK));
			// Paste ����Ű ����
		} else if (item.getActionCommand().equals("group")) {
			this.addSeparator();
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
					InputEvent.CTRL_DOWN_MASK));
			// Group ����Ű ����
		} else if (item.getActionCommand().equals("Ungroup")) {
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
					InputEvent.CTRL_DOWN_MASK));
			// Ungroup ����Ű ����
		}
	}
}
