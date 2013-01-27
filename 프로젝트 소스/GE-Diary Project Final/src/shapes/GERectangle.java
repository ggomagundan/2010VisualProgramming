package shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GERectangle extends GEShape {

	public GERectangle() {
		super(new Rectangle());
	}

	public void initDraw(Point startP) {
		this.startP = startP;
	}

	public void setCoordinate(Point currentP) {
		Rectangle tempRectangle = (Rectangle) myShape;
		tempRectangle.setFrameFromDiagonal(startP.x, startP.y, currentP.x,
				currentP.y);
		if (anchorList != null) {
			anchorList.setPosition(myShape.getBounds());
		}
	}

	public GERectangle clone() {
		return new GERectangle();
	}

	public GEShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape);
		GERectangle shape = new GERectangle();
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this);
		return shape;
	}
}
