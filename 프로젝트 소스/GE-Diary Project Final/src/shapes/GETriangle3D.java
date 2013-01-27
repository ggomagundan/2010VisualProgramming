package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class GETriangle3D extends GEShape {

	private Point currentP;

	public GETriangle3D() {
		super(new Line2D.Double());
	}

	@Override
	public void initDraw(Point startP) {
		this.startP = startP;
		this.currentP = startP;
	}

	@Override
	public void setCoordinate(Point currentP) {
		this.currentP = currentP;
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (currentP.x - startP.x != 0 && currentP.y - startP.y != 0) {
			g2d.drawLine(startP.x, startP.y, startP.x, currentP.y);
			g2d.drawLine(startP.x, startP.y, currentP.x, startP.y
					+ (currentP.y - startP.x) / 5 * 4);
			g2d.drawLine(startP.x, startP.y,
					startP.x - (currentP.x - startP.x), startP.y
							+ (currentP.y - startP.x) / 5 * 4);
			g2d.drawLine(currentP.x,
					startP.y + (currentP.y - startP.x) / 5 * 4, startP.x,
					currentP.y);
			g2d.drawLine(startP.x - (currentP.x - startP.x), startP.y
					+ (currentP.y - startP.x) / 5 * 4, startP.x, currentP.y);
			g2d.drawLine(currentP.x,
					startP.y + (currentP.y - startP.x) / 5 * 4, startP.x
							- (currentP.x - startP.x), startP.y
							+ (currentP.y - startP.x) / 5 * 4);
		}
	}

	public GETriangle3D clone() {
		return new GETriangle3D();
	}

	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GETriangle3D shape = new GETriangle3D();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}
}
