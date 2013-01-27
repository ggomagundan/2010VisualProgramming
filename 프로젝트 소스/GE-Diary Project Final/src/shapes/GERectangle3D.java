package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class GERectangle3D extends GEShape {

	private Point currentP;

	public GERectangle3D() {
		super(new Rectangle());
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
		int width = (currentP.x - startP.x) / 5;
		int height = (currentP.y - startP.y) / 5;
		if (currentP.x - startP.x != 0 && currentP.y - startP.y != 0) {
			g2d.drawRect(startP.x, startP.y, currentP.x - startP.x, currentP.y
					- startP.y);
			g2d.drawRect(startP.x + width, startP.y - height, currentP.x
					- startP.x, currentP.y - startP.y);
			g2d.drawLine(startP.x, startP.y, startP.x + width, startP.y
					- height);
			g2d.drawLine(currentP.x, startP.y, currentP.x + width, startP.y
					- height);
			g2d.drawLine(startP.x, currentP.y, startP.x + width, currentP.y
					- height);
			g2d.drawLine(currentP.x, currentP.y, currentP.x + width, currentP.y
					- height);
		}
	}

	public GERectangle3D clone() {
		return new GERectangle3D();
	}

	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GERectangle3D shape = new GERectangle3D();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}
}
