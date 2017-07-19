package figure;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.io.Serializable;

public interface Figure extends Shape, Serializable{
	
//	protected Point2D center = new Point2D.Double();
//	protected double heightToWidth;
//	
//	protected Color color = Color.BLACK;
//	protected float strokeWidth = 3;
//	
	void setColor(Color color);
//	public default void setColor(Color color) {
//		this.color = color;
//	}
//	
//	public void setstrokeWidth(float strokeWidth) {
//		this.strokeWidth = strokeWidth;
//	}
//	
	void setSizePercent(double sizePercent);
//	public void setSizePercent(double sizePercent) {		
//		double w = width * sizePercent;
//		double h = w * heightToWidth;
//		double x = center.getX() - w/2;
//		double y = center.getY() - h/2;
//		
//		this.x = (int)x;
//		this.y = (int)y;
//		width = (int)w;
//		height = (int)h;
//	}
//	
	void setFigure(Point p1, Point p2);
//	public void setBounds(Point p1, Point p2) {
//		setFrameFromDiagonal(p1, p2);
//		center.setLocation((p1.x + p2.x)/2.0, (p1.y + p2.y)/2.0);
//		heightToWidth = height/(double)width;
//	}
//
	void draw(Graphics g);
//	
//	@Override
//	public boolean contains(double x, double y) {
//		int xDiff = (int)x - this.x;
//		int yDiff = (int)y - this.y;
//		
//		return xDiff >= 0 && xDiff <= width &&
//				yDiff >= 0 && yDiff <= height;
//	}
//
//	@Override
//	public boolean intersects(double x, double y, double w, double h) {		
//		return (contains(x, y) || contains(x + w, y) 
//				|| contains(x, y + h) || contains(x + w, y + h)) && 
//				!contains(x, y, w, h);
//	}
//
//	@Override
//	public boolean contains(double x, double y, double w, double h) {		
//		return contains(x, y) && contains(x + w, y) 
//				&& contains(x, y + h) && contains(x + w, y + h);
//	}
//
//	@Override
//	public boolean isEmpty() {
//		return width * height == 0;
//	}
//
//	@Override
//	public void setFrame(double x, double y, double w, double h) {
//		this.x = (int)x;
//		this.y = (int)y;
//		this.width = (int)w;
//		this.height = (int)h;
//	}
//
	void changeStrokeWidth(float inc);
//	public void changeStrokeWidth(float inc) {
//		float newStrokeWidth = strokeWidth + inc;
//		if (newStrokeWidth >= 1) {
//			strokeWidth = newStrokeWidth;
//		}
//	}
//	
	void setLocation(Point start, Point end);
//	public void setLocation(Point start, Point end) {
//		x = x + end.x - start.x;
//		y = y + end.y - start.y;
//		center.setLocation(x + width/2.0, y + height/2.0);
//	}
//
	boolean isSelected(Rectangle r);
//	public boolean isSelected(Rectangle r) {
//		return intersects(r);
//	}
	
	Point getCenter();
}
