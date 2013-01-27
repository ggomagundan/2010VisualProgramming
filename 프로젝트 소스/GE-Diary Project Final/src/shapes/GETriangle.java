package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class GETriangle extends GEShape {
	
	private Point currentP;

	public GETriangle() {
		// TODO Auto-generated constructor stub
		super(new Line2D.Double());
	}

	@Override
	public void initDraw(Point startP) {
		// TODO Auto-generated method stub
		this.startP = startP;
		this.currentP = startP;
	}

	@Override
	public void setCoordinate(Point currentP) {
		// TODO Auto-generated method stub
		this.currentP = currentP;
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawLine(startP.x, currentP.y, currentP.x, currentP.y);
		g2d.drawLine(startP.x, startP.y, startP.x, currentP.y);
		g2d.drawLine(startP.x, startP.y, currentP.x, currentP.y);
	}

	public GETriangle clone() {
		return new GETriangle();
	}

	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GETriangle shape = new GETriangle();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}
}
