package figure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Line extends Line2D.Double implements Figure {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6891657773707847266L;
	private Color color = Color.BLACK;
	private float strokeWidth = 3;
		
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		((Graphics2D)g).setStroke(new BasicStroke(strokeWidth));
		((Graphics2D)g).drawLine((int)getX1(), (int)getY1(), 
				(int)getX2(), (int)getY2());
	}
	
	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void changeStrokeWidth(float inc) {
		float newStrokeWidth = strokeWidth + inc;
		if (newStrokeWidth >= 1) {
			strokeWidth = newStrokeWidth;
		}
	}

	@Override
	public boolean isSelected(Rectangle r) {
		return intersects(r);
	}

	@Override
	public void setSizePercent(double sizePercent) {
		Point2D p = getCenter();
		x1 = x1 * sizePercent + p.getX() * (1 - sizePercent);
		y1 = y1 * sizePercent + p.getY() * (1 - sizePercent);
		x2 = x2 * sizePercent + p.getX() * (1 - sizePercent);
		y2 = y2 * sizePercent + p.getY() * (1 - sizePercent);
	}

	@Override
	public void setFigure(Point p1, Point p2) {
		setLine(p1, p2);
	}

	@Override
	public void setLocation(Point start, Point end) {
		double dX = end.getX() - start.getX();
		double dY = end.getY() - start.getY();
		x1 += dX;
		x2 += dX;
		y1 += dY;
		y2 += dY;
	}

	@Override
	public Point getCenter() {
		return new Point(((int)(x1 + x2) / 2), 
				(int)((y1 + y2) / 2));
	}
}
