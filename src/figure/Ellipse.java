package figure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Ellipse extends Ellipse2D.Double implements Figure {

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		((Graphics2D)g).setStroke(new BasicStroke(strokeWidth));
		((Graphics2D)g).drawOval(x, y, width, height);
	}

	@Override	
	public boolean contains(double x, double y) {
		boolean boundRectContains = super.contains(x, y);
		if (!boundRectContains) {
			return false;
		}
		else {
			double a2 = width * width/4.0;
			double b2 = height * height/4.0;
			double dX = x - this.x;
			double dY = y - this.y;
			return b2*dX*dX + a2*dY*dY - a2*b2 < 0;	
		}
	}

	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSizePercent(double sizePercent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFigure(Point p1, Point p2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStrokeWidth(float inc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocation(Point start, Point end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelected(Rectangle r) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
