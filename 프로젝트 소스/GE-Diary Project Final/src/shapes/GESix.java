package shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GESix extends GEShape {

	private Point startP;

	public GESix() {
		super(new Polygon()); // 생성자 선언

	}

	public void initDraw(Point startP) {
		this.startP = startP; // Start Point 전달

	}

	public void setCoordinate(Point currentP) {
		Polygon temp = (Polygon) myShape; // Shape 구체화

		temp.npoints = 0;
		temp.addPoint(startP.x + (currentP.x - startP.x) / 30 * 8, startP.y);
		temp.addPoint(startP.x + (currentP.x - startP.x) / 30 * 23, startP.y);
		temp.addPoint(currentP.x, startP.y + (currentP.y - startP.y) / 2);
		temp.addPoint(startP.x + (currentP.x - startP.x) / 30 * 23, currentP.y);
		temp.addPoint(startP.x + (currentP.x - startP.x) / 30 * 8, currentP.y);
		temp.addPoint(startP.x, startP.y + (currentP.y - startP.y) / 2);
		temp.addPoint(startP.x + (currentP.x - startP.x) / 30 * 8, startP.y);
		// Draw Six Polygon
	}

	public GEShape clone() {
		return new GESix(); // Clone Return
	}

	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GESix shape = new GESix();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}
}
