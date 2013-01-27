package shapes;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

public class GEArc extends GEShape {

	private Point startP;

	public GEArc() {
		super(new Arc2D.Double(Arc2D.OPEN)); // 생성자 선언
	}

	public void initDraw(Point startP) {
		this.startP = startP; // Start Point 전달
	}

	public void setCoordinate(Point currentP) {
		Arc2D temp = (Arc2D) myShape; // Shape 구체화
		temp.setFrame(startP.x, startP.y, currentP.x - startP.x, currentP.y
				- startP.y);
		// Arc Draw
		temp.setAngleStart(-20); // Arc StartAngle
		temp.setAngleExtent(150); // Arc ExtentAngle
	}

	public GEShape clone() {
		return new GEArc(); // Clone Return
	}

	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GEArc shape = new GEArc();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}
}
