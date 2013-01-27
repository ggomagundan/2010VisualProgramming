package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import shapes.GEGroup;
import shapes.GEPolygon;
import shapes.GESelect;
import shapes.GEShape;
import transformer.GEDrawer;
import transformer.GEGrouper;
import transformer.GEResizer;

import transformer.GEMover; //

import transformer.GETransformer;
import utils.GECursorManager;
import constants.GEConstants;
import constants.GEConstants.EAnchorTypes;
import constants.GEConstants.EState;

public class GEDrawingPanel extends JPanel {

	private MouseDrawingHandler drawingHandler;
	private GEShape currentShape, selectedShape;
	public ArrayList shapeList, copyList;
	private GETransformer transformer;
	private EState currentState;
	private Color lineColor, fillColor;
	private GECursorManager cursors;
	private GECal cal;

	public GEDrawingPanel() {
		super();
		currentState = EState.Idle;
		shapeList = new ArrayList();
		copyList = new ArrayList();
		drawingHandler = new MouseDrawingHandler();
		cursors = new GECursorManager();
		addMouseListener(drawingHandler);
		addMouseMotionListener(drawingHandler);
		this.setForeground(GEConstants.FOREGROUND_COLOR);
		this.setBackground(GEConstants.BACKGROUND_COLOR);
		initializeGraphicsAtributes();
	}

	public ArrayList getShapeList() {
		return cal.shapelist;
	}

	public void setShapeList(ArrayList shapeList) {
		cal.shapelist = shapeList;
		repaint();
	}

	public void clearShapeList() {
		shapeList.clear();
		repaint();
	}

	public void copy() {
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = (GEShape) shapeList.get(i - 1);
			if (shape.isSelected()) {
				copyList.add(shape);
			}
		}
		repaint();
	}

	public void paste() {
		int i = copyList.size();
		GEShape shape = (GEShape) copyList.get(i - 1);
		shapeList.add(shape);
		repaint();
	}

	public void undo() {
		if((shapeList.size()) > 0){
			GEShape shape = (GEShape) shapeList.get(shapeList.size() - 1);
			copyList.add(shape);
			shapeList.remove(shape);
			repaint();
		}
	}

	public void redo() {
		if((copyList.size()) > 0){
			int i = copyList.size();
			GEShape shape = (GEShape) copyList.get(i - 1);
			shapeList.add(shape);
			copyList.remove(shape);
			repaint();
		}
	}

	public void cut() {
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = (GEShape) shapeList.get(i - 1);
			if (shape.isSelected()) {
				copyList.add(shape);
				shapeList.remove(shape);
			}
		}
		repaint();
	}

	public void delete() {
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = (GEShape) shapeList.get(i - 1);
			if (shape.isSelected()) {
				copyList.add(shape);
				shapeList.remove(shape);
			}
		}
		repaint();
	}

	public void setLineColor(Color lineColor) {
		if (selectedSetColor(true, lineColor)) {
			return;
		}
		this.lineColor = lineColor;
	}

	public void setFillColor(Color fillColor) {
		if (selectedSetColor(false, fillColor)) {
			return;
		}
		this.fillColor = fillColor;
	}

	private boolean selectedSetColor(boolean flag, Color color) {
		boolean returnValue = false;
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = (GEShape) shapeList.get(i - 1);
			if (shape.isSelected()) {
				if (flag) {
					shape.setLineColor(color);
				} else {
					shape.setFillColor(color);
				}
				returnValue = true;
			}
		}
		repaint();
		return returnValue;
	}

	public void setCurrentShape(GEShape currentShape) {
		this.currentShape = currentShape;
	}

	public void setCurrentState(EState currentState) {
		this.currentState = currentState;
	}

	public void group(GEGroup group) {
		boolean check = false;
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = (GEShape) shapeList.get(i - 1);
			shape.setSelected(false);
			group.addShape(shape);
			shapeList.remove(shape);
			check = true;
		}
		if (check) {
			group.setSelected(true);
			shapeList.add(group);
		}
		repaint();
	}

	public void unGroup() {
		ArrayList<GEShape> tempList = new ArrayList<GEShape>();
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = (GEShape) shapeList.get(i - 1);
			if (shape instanceof GEGroup && shape.isSelected()) {
				for (GEShape childShape : ((GEGroup) shape).getChildList()) {
					childShape.setSelected(true);
					tempList.add(childShape);
				}
				shapeList.remove(shape);
			}
		}
		shapeList.addAll(tempList);
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		if (cal.shapelist.get(cal.getDateText()) != null) {
			shapeList = (ArrayList) cal.shapelist.get(cal.getDateText());
			for (int j = 0; j < shapeList.size(); j++) {
				GEShape shape = (GEShape) shapeList.get(j);
				shape.draw(g2D);
			}
			cal.shapelist.set(cal.getDateText(), shapeList);
		} else {
			shapeList = new ArrayList();
			cal.shapelist.set(cal.getDateText(), shapeList);
		}

	}

	private void initializeGraphicsAtributes() {
		lineColor = GEConstants.COLOR_LINE_DEFAULT;
		fillColor = GEConstants.COLOR_FILL_DEFAULT;
	}

	private void createShape(Point startP) {
		currentShape = currentShape.clone();
		currentShape.setLineColor(lineColor);
		currentShape.setFillColor(fillColor);
	}

	public void continueDrawing(Point p) {
		((GEDrawer) transformer).continueDrawing(p);
	}

	private GEShape onShape(Point p) {
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = (GEShape) shapeList.get(i - 1);
			if (shape.onShape(p)) {
				return (shape);
			}
		}
		return null;
	}

	public void clearSelectedShapes() {
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = (GEShape) shapeList.get(i - 1);
			shape.setSelected(false);
		}
	}

	private class MouseDrawingHandler extends MouseInputAdapter {
		public void mousePressed(MouseEvent e) {
			if (currentState == EState.Idle) {
				if (currentShape instanceof GESelect) {
					selectedShape = onShape(e.getPoint());
					if (currentShape != null) {
						clearSelectedShapes();
						if (selectedShape != null) {
							selectedShape.setSelected(true);
							selectedShape.onAnchor(e.getPoint()); //
							if (selectedShape.getSelectedAnchor() == EAnchorTypes.NONE) {
								transformer = new GEMover(selectedShape);
								((GEMover) transformer).init(e.getPoint());
								setCurrentState(EState.Moving);
							} else {
								transformer = new GEResizer(selectedShape);
								((GEResizer) transformer).init(e.getPoint());
								setCurrentState(EState.Resize);
							}
						} else {
							setCurrentState(EState.Selecting);
							clearSelectedShapes();
							createShape(e.getPoint());
							transformer = new GEGrouper(currentShape);
							((GEGrouper) transformer).init(e.getPoint());
						}
					}
				} else {
					clearSelectedShapes();
					createShape(e.getPoint());
					transformer = new GEDrawer(currentShape);
					((GEDrawer) transformer).init(e.getPoint());
					if (currentShape instanceof GEPolygon) {
						setCurrentState(EState.NPointsDrawing);
					} else {
						setCurrentState(EState.TwoPointsDrawing);
					}
				}
			}
		}

		public void mouseMoved(MouseEvent e) {
			if (currentState == EState.NPointsDrawing) {
				transformer.transformer((Graphics2D) getGraphics(),
						e.getPoint());
			} else if (currentState == EState.Idle) {
				GEShape shape = onShape(e.getPoint());
				if (shape == null) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				} else {
					if (shape.isSelected()) {
						EAnchorTypes anchorType = shape.onAnchor(e.getPoint());
						setCursor(cursors.get(anchorType.ordinal()));
					}
				}
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (currentState != EState.Idle) {
				transformer.transformer((Graphics2D) getGraphics(),
						e.getPoint());
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (currentState == EState.TwoPointsDrawing) {
				((GEDrawer) transformer).finalize(shapeList);
			} else if (currentState == EState.NPointsDrawing) {
				return;
			} else if (currentState == EState.Resize) {
				((GEResizer) transformer).finalize(e.getPoint());
			} else if (currentState == EState.Selecting) {
				((GEGrouper) transformer).finalize(shapeList);
			}
			setCurrentState(EState.Idle);
			repaint();
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (currentState == EState.NPointsDrawing) {
					if (e.getClickCount() == 1) {
						continueDrawing(e.getPoint());
					} else if (e.getClickCount() == 2) {
						((GEDrawer) transformer).finalize(shapeList);
						setCurrentState(EState.Idle);
						repaint();
					}
				}
			}
		}
	}

	public void init(GECal cal) {
		// TODO Auto-generated method stub
		this.cal = cal;
	}

	public void getpanel(int day) {
		// TODO Auto-generated method stub
		for (int i = shapeList.size(); i > 0; i--) {
			GEShape shape = (GEShape) shapeList.get(i - 1);
		}
		repaint();
	}
}