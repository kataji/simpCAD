package figure;

import java.awt.geom.Point2D.Double;

public class Point extends Double {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8841526035513333878L;

	public Point(double x, double y) {
		super(x , y);
	}

	public Point(java.awt.Point p) {
		super(p.getX(), p.getY());
	}
}
