package figure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
		((Graphics2D)g).drawLine((int)super.getX1(), (int)super.getY1(), 
				(int)super.getX2(), (int)super.getY2());
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
		super.x1 = super.x1 * sizePercent + p.getX() * (1 - sizePercent);
		super.y1 = super.y1 * sizePercent + p.getY() * (1 - sizePercent);
		super.x2 = super.x2 * sizePercent + p.getX() * (1 - sizePercent);
		super.y2 = super.y2 * sizePercent + p.getY() * (1 - sizePercent);
	}

	@Override
	public void setFigure(Point p1, Point p2) {
		setLine(p1, p2);
	}

	@Override
	public void setLocation(Point start, Point end) {
		double dX = end.getX() - start.getX();
		double dY = end.getY() - start.getY();
		super.x1 += dX;
		super.x2 += dX;
		super.y1 += dY;
		super.y2 += dY;
	}

	@Override
	public Point getCenter() {
		return new Point((super.x1 + super.x2) / 2, 
				(super.y1 + super.y2) / 2);
	}
}
