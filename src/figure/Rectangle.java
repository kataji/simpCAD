package figure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Rectangle2D.Float implements Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5274142611530412079L;
	private Color color = Color.BLACK;
	private float strokeWidth = 3;
	
	public Rectangle(double x, double y, double w, double h) {
//		super(x, y, w, h);
		super((float)x, (float)y, (float)w, (float)h);
	}

	public Rectangle() {
		super();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		((Graphics2D)g).setStroke(new BasicStroke(strokeWidth));
		((Graphics2D)g).drawRect((int)x, (int)y, (int)width, (int)height);
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
	public void setSizePercent(double sizePercent) {
		double oldW = width;
		double oldH = height;
		width *= sizePercent;
		height *= sizePercent;
		x += (oldW - width) / 2;
		y += (oldH - height) / 2;
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
	public boolean isSelected(Rectangle r) {
		return intersects(r);
	}

	@Override
	public Point getCenter() {
		return new Point((int)(x + width/2), (int)(y + height/2));
	}

	@Override
	public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits((int)getX());
        bits += java.lang.Double.doubleToLongBits((int)getY()) * 37;
        bits += java.lang.Double.doubleToLongBits((int)getWidth()) * 43;
        bits += java.lang.Double.doubleToLongBits((int)getHeight()) * 47;
        return (((int) bits) ^ ((int) (bits >> 32)));
	}	
}
