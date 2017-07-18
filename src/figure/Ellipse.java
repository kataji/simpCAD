package figure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Ellipse extends Ellipse2D.Double implements Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1478348259909384827L;
	private Color color = Color.BLACK;
	private float strokeWidth = 3;
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		((Graphics2D)g).setStroke(new BasicStroke(strokeWidth));
		((Graphics2D)g).drawOval((int)x, (int)y, (int)width, (int)height);
	}

	@Override	
	public boolean contains(double x, double y) {
		return super.contains(x, y);
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void setSizePercent(double sizePercent) {
		double oldW = width;
		double oldH = height;
		width *= sizePercent;
		height *= sizePercent;
		x += (oldW - width) / 2;
		y += (oldH - height) / 2;
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
	public void setFigure(Point p1, Point p2) {
		setFrameFromDiagonal(p1, p2);
	}

	@Override
	public void setLocation(Point start, Point end) {
		double dX = end.getX() - start.getX();
		double dY = end.getY() - start.getY();
		x += dX;
		y += dY;		
	}

	@Override
	public Point getCenter() {
		return new Point(x + width/2, y + height/2);
	}
	
}
