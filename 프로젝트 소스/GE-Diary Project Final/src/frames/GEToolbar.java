package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import shapes.GEArc;
import shapes.GEEllipse;
import shapes.GEEllipse3D;
import shapes.GEFive;
import shapes.GELine;
import shapes.GEPolygon;
import shapes.GERectangle;
import shapes.GERectangle3D;
import shapes.GESelect;
import shapes.GEShape;
import shapes.GESix;
import shapes.GEStar;
import shapes.GETriangle;
import shapes.GETriangle3D;
import constants.GEConstants;
import constants.GEConstants.EToolBarButtons;

public class GEToolbar extends JToolBar {

	private ButtonGroup buttonGroup;
	private GEDrawingPanel drawingPanel;
	private GEToolBarHandler shapeToolBarHandler;

	public GEToolbar(String label) {
		super(label);
		buttonGroup = new ButtonGroup();
		JRadioButton rButton = null;
		shapeToolBarHandler = new GEToolBarHandler();
		for (EToolBarButtons btn : EToolBarButtons.values()) {
			rButton = new JRadioButton();
			rButton.setIcon(new ImageIcon(GEConstants.IMG_URL + btn.toString()
					+ GEConstants.SUFFIX_TOOLBAR_BTN));

			rButton.setSelectedIcon(new ImageIcon(GEConstants.IMG_URL
					+ btn.toString() + GEConstants.SUFFIX_TOOLBAR_BTN_SLT));
			rButton.addActionListener(shapeToolBarHandler);
			rButton.setActionCommand(btn.toString());
			this.add(rButton);
			buttonGroup.add(rButton);
		}
	}

	public void init(GEDrawingPanel dp) {
		drawingPanel = dp;
		setSize(GEConstants.WIDTH_SHAPETOOLBAR, GEConstants.HEIGHT_SHAPETOOLBAR);
		clickDefaultButton();
	}

	private void clickDefaultButton() {
		JRadioButton rButton = (JRadioButton) this
				.getComponent(EToolBarButtons.Rectangle.ordinal());
		rButton.doClick();
	}

	private class GEToolBarHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JRadioButton button = (JRadioButton) e.getSource();
			if (button.getActionCommand().equals(
					EToolBarButtons.Rectangle.name())) {
				drawingPanel.setCurrentShape(new GERectangle());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Ellipse.name())) {
				drawingPanel.setCurrentShape(new GEEllipse());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Line.name())) {
				drawingPanel.setCurrentShape(new GELine());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Polygon.name())) {
				drawingPanel.setCurrentShape(new GEPolygon());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Select.toString())) {
				drawingPanel.setCurrentShape(new GESelect());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Rectangle3D.toString())) {
				drawingPanel.setCurrentShape(new GERectangle3D());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Triangle3D.toString())) {
				drawingPanel.setCurrentShape(new GETriangle3D());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Triangle.toString())) {
				drawingPanel.setCurrentShape(new GETriangle());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Ellipse3D.toString())) {
				drawingPanel.setCurrentShape(new GEEllipse3D());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Five.toString())) {
				drawingPanel.setCurrentShape(new GEFive());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Six.toString())) {
				drawingPanel.setCurrentShape(new GESix());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Star.toString())) {
				drawingPanel.setCurrentShape(new GEStar());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Ellipse3D.toString())) {
				drawingPanel.setCurrentShape(new GEEllipse3D());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Arc.toString())) {
				drawingPanel.setCurrentShape(new GEArc());
			} else if (button.getActionCommand().equals(
					EToolBarButtons.New.toString())) {
				JFrame f = new JFrame();
				JOptionPane.showMessageDialog(f, "새로 만들기.");
				drawingPanel.clearShapeList();
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Open.toString())) {
				JFileChooser fileDialog = new JFileChooser(new File("."));
				fileDialog.showOpenDialog(null);
				File file = fileDialog.getSelectedFile();
				ObjectInputStream in = null;
				if (file != null) {
					try {
						in = new ObjectInputStream(new BufferedInputStream(
								new FileInputStream(file)));
						Object obj = in.readObject();
						drawingPanel.setShapeList((ArrayList<GEShape>) obj);
					} catch (IOException e2) {
						e2.printStackTrace();
					} catch (ClassNotFoundException e2) {
						e2.printStackTrace();
					} finally {
						try {
							if (in != null)
								in.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				}
			} else if (button.getActionCommand().equals(
					EToolBarButtons.Save.toString())) {
				JFileChooser fileDialog = new JFileChooser(new File("."));
				fileDialog.showSaveDialog(null);
				File file = fileDialog.getSelectedFile();
				ObjectOutputStream out = null;
				if (file != null) {
					try {
						out = new ObjectOutputStream(new BufferedOutputStream(
								new FileOutputStream(file)));
						out.writeObject((drawingPanel).getShapeList());
					} catch (IOException e2) {
						e2.printStackTrace();
					} finally {
						try {
							if (out != null)
								out.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		}
	}
}
