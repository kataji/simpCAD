package figure;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;

public abstract class Figure extends RectangularShape implements Serializable{
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Point2D center = new Point2D.Double();
	protected double heightToWidth;
	
	protected Color color = Color.BLACK;
	protected float strokeWidth = 3;
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setstrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	
	public void setSizePercent(double sizePercent) {		
		double w = width * sizePercent;
		double h = w * heightToWidth;
		double x = center.getX() - w/2;
		double y = center.getY() - h/2;
		
		this.x = (int)x;
		this.y = (int)y;
		width = (int)w;
		height = (int)h;
	}
	
	public void setBounds(Point p1, Point p2) {
		setFrameFromDiagonal(p1, p2);
		center.setLocation((p1.x + p2.x)/2.0, (p1.y + p2.y)/2.0);
		heightToWidth = height/(double)width;
	}

	public abstract void draw(Graphics g);
	
	@Override
	public Rectangle2D getBounds2D() {
		return getBounds();
	}

	@Override
	public boolean contains(double x, double y) {
		int xDiff = (int)x - this.x;
		int yDiff = (int)y - this.y;
		
		return xDiff >= 0 && xDiff <= width &&
				yDiff >= 0 && yDiff <= height;
	}

	@Override
	public boolean intersects(double x, double y, double w, double h) {		
		return (contains(x, y) || contains(x + w, y) 
				|| contains(x, y + h) || contains(x + w, y + h)) && 
				!contains(x, y, w, h);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {		
		return contains(x, y) && contains(x + w, y) 
				&& contains(x, y + h) && contains(x + w, y + h);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public boolean isEmpty() {
		return width * height == 0;
	}

	@Override
	public void setFrame(double x, double y, double w, double h) {
		this.x = (int)x;
		this.y = (int)y;
		this.width = (int)w;
		this.height = (int)h;
	}

	public void changeStrokeWidth(float inc) {
		float newStrokeWidth = strokeWidth + inc;
		if (newStrokeWidth >= 1) {
			strokeWidth = newStrokeWidth;
		}
	}
	
	public void setLocation(Point start, Point end) {
		x = x + end.x - start.x;
		y = y + end.y - start.y;
		center.setLocation(x + width/2.0, y + height/2.0);
	}

	public boolean isSelected(Rectangle r) {
		return intersects(r);
	}
}
