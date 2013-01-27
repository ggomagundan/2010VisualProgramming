package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import constants.GEConstants.EFileMenuItems;
import frames.GECal;
import frames.GEDrawingPanel;

public class GEMenuFile extends JMenu {

	private GEDrawingPanel drawingPanel;
	private FileMenuHandler fileMenuHandler;
	private GECal cal;

	public GEMenuFile(String label) {
		super(label);
		this.setMnemonic(KeyEvent.VK_F);
		fileMenuHandler = new FileMenuHandler();
		for (EFileMenuItems btn : EFileMenuItems.values()) {
			JMenuItem menuItem = new JMenuItem(btn.toString());
			menuItem.addActionListener(fileMenuHandler);
			menuItem.setActionCommand(btn.toString());
			setACC(menuItem);
			this.add(menuItem);
		}
	}

	public void init(GEDrawingPanel dp) {
		drawingPanel = dp; //
		operationOpen();
	}

	public void init(GECal cal) {
		this.cal = cal; //
	}

	private void newFile() {
		drawingPanel.clearShapeList();
	}

	@SuppressWarnings("unchecked")
	public void open() {
		JFileChooser fileDialog = new JFileChooser(new File("."));
		fileDialog.showOpenDialog(null);
		File file = fileDialog.getSelectedFile();
		ObjectInputStream in = null;
		if (file != null) {
			try {
				in = new ObjectInputStream(new BufferedInputStream(
						new FileInputStream(file)));
				drawingPanel.setShapeList((ArrayList) in.readObject());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null)
						in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void save() {
		JFileChooser fileDialog = new JFileChooser(new File("."));
		fileDialog.showSaveDialog(null);
		File file = fileDialog.getSelectedFile();
		ObjectOutputStream out = null;
		if (file != null) {
			try {
				out = new ObjectOutputStream(new BufferedOutputStream(
						new FileOutputStream(file)));
				out.writeObject((drawingPanel).getShapeList());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void othersave() {
		JFileChooser fileDialog = new JFileChooser(new File("."));
		fileDialog.showSaveDialog(null);
		File file = fileDialog.getSelectedFile();
		ObjectOutputStream out = null;
		if (file != null) {
			try {
				out = new ObjectOutputStream(new BufferedOutputStream(
						new FileOutputStream(file)));
				out.writeObject((drawingPanel).getShapeList());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void exitsave() {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream("saveall")));
			out.writeObject((drawingPanel).getShapeList());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void operationOpen() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream("saveall")));
			drawingPanel.setShapeList((ArrayList) in.readObject());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void close() {
		JFrame f = new JFrame();
		int chk = JOptionPane.showConfirmDialog(f, "������ �����Ͻðڽ��ϱ�?", "����Ȯ��", 1);
		if (chk == 0) {
			JOptionPane.showMessageDialog(f, "�����մϴ�.");
			exitsave();
			System.exit(0);
		}
	}

	private class FileMenuHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch (EFileMenuItems.valueOf(e.getActionCommand())) {
			case ���θ����:
				newFile();
				break; //
			case ����:
				open();
				break; //
			case ����:
				save();
				break; //
			case �ٸ��̸���������:
				othersave();
				break; //
			case ����:
				close();
				break;
			}
		}
	}

	public void setACC(JMenuItem item) {
		if (item.getActionCommand().equals("���θ����")) {
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
					InputEvent.CTRL_DOWN_MASK));
		} else if (item.getActionCommand().equals("����")) {
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					InputEvent.CTRL_DOWN_MASK));
			// Open ����Ű ����
		} else if (item.getActionCommand().equals("����")) {
			this.addSeparator();
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					InputEvent.CTRL_DOWN_MASK));
			// Save ����Ű ����
		} else if (item.getActionCommand().equals("�ٸ��̸���������")) {
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK,
					false));
			// SaveAs ����Ű ����
		} else if (item.getActionCommand().equals("����")) {
			this.addSeparator();
			item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
					InputEvent.CTRL_DOWN_MASK));
			// Exit ����Ű ����
		}
	}
}