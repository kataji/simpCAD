package figure;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

public class Ellipse extends Figure {

	@Override
	public void draw(Graphics graphics) {
		// TODO Auto-generated method stub

	}

	@Override	
	public boolean contains(double x, double y) {
		// TODO
		return false;
	}
	
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
