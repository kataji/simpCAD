package figure;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class Line extends Figure {

	private boolean startFromLeft = true;
	private boolean startFromUp = true;
	
	private Line2D line = new Line2D.Double();
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		((Graphics2D)g).setStroke(new BasicStroke(strokeWidth));
		((Graphics2D)g).drawLine((int)line.getX1(), (int)line.getY1(), 
				(int)line.getX2(), (int)line.getY2());
	}
	
	@Override
	public void setBounds(Point p1, Point p2) {
		super.setBounds(p1, p2);
		line.setLine(p1, p2);
		if (p1.x != x) {
			startFromLeft = false;
		}
		if (p1.y != y) {
			startFromUp = false;
		}
	}
	
	@Override	
	public boolean contains(double x, double y) {
		return line.contains(x, y);
	}
	
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return line.intersects(x, y, w, h);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return line.contains(x, y, w, h);
	}
	
	@Override
	public void setFrame(double x, double y, double w, double h) {
		super.setFrame(x, y, w, h);
		updateLine();
	}

	@Override
	public void setSizePercent(double sizePercent) {
		super.setSizePercent(sizePercent);
		updateLine();
	}
	
	private void updateLine() {
		Point p1 = new Point();
		Point p2 = new Point();
		if (startFromLeft) {
			p1.x = x;
			p2.x = x + width;
		}
		else {
			p2.x = x;
			p1.x = x + width;
		}
		
		if (startFromUp) {
			p1.y = y;
			p2.y = y + height;
		}
		else {
			p2.y = y;
			p1.y = y + height;
		}
		
		line.setLine(p1, p2);
	}
}
