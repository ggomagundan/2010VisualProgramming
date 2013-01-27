package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class GEEllipse3D extends GEShape {

	private Point currentP;

	public GEEllipse3D() {
		// TODO Auto-generated constructor stub
		super(new Ellipse2D.Double());
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
		if (currentP.x - startP.x != 0 && currentP.y - startP.x != 0) {
			g2d.drawOval(startP.x, startP.y, currentP.x - startP.x,
					(currentP.y - startP.y) / 10 * 2);
			g2d.drawLine(startP.x, startP.y + (currentP.y - startP.y) / 10,
					startP.x, startP.y + (currentP.y - startP.y) / 10 * 9);
			g2d.drawLine(currentP.x, startP.y + (currentP.y - startP.y) / 10,
					currentP.x, startP.y + (currentP.y - startP.y) / 10 * 9);
			g2d.drawOval(startP.x, startP.y + (currentP.y - startP.y) / 10 * 8,
					currentP.x - startP.x, (currentP.y - startP.y) / 10 * 2);
		}
	}

	public GEEllipse3D clone() {
		return new GEEllipse3D();
	}

	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GEEllipse3D shape = new GEEllipse3D();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}
}
