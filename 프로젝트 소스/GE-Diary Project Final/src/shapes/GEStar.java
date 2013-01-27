package shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GEStar extends GEShape {

	private Point startP;

	public GEStar() {
		super(new Polygon()); // 생성자 선언
	}

	public void initDraw(Point startP) {
		this.startP = startP; // Start Point 전달

	}

	public void setCoordinate(Point currentP) {
		
		Polygon temp = (Polygon) myShape; // Shape 구체화

		temp.npoints = 0;
		temp.addPoint(startP.x + (currentP.x - startP.x) / 2, startP.y);
		temp.addPoint(startP.x + (currentP.x - startP.x) / 40 * 38, currentP.y);
		temp.addPoint(startP.x, startP.y + (currentP.y - startP.y) / 8 * 3);
		temp.addPoint(currentP.x, startP.y + (currentP.y - startP.y) / 8 * 3);
		temp.addPoint(startP.x + (currentP.x - startP.x) / 40 * 6, currentP.y);
		temp.addPoint(startP.x + (currentP.x - startP.x) / 2, startP.y);
		// Draw Star
	}

	public GEShape clone() {
		return new GEStar(); // Clone Return
	}

	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GEStar shape = new GEStar();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}
}
