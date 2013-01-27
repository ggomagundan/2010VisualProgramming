package shapes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GEPolygon extends GEShape {

	public GEPolygon() {
		super(new Polygon());
	}

	public void initDraw(Point p) {
		((Polygon) myShape).addPoint(p.x, p.y);
	}

	public void setCoordinate(Point p) {
		Polygon tempPolygon = (Polygon) myShape;
		tempPolygon.xpoints[((Polygon) myShape).npoints - 1] = p.x;
		tempPolygon.ypoints[((Polygon) myShape).npoints - 1] = p.y;
		if (anchorList != null) {
			anchorList.setPosition(myShape.getBounds());
		}
	}

	public void continueDrawing(Point p) {
		((Polygon) myShape).addPoint(p.x, p.y);
	}

	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GEPolygon shape = new GEPolygon();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}

	public GEShape clone() {
		return new GEPolygon();
	}
}
