package figure;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFigure extends Rectangle{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4531340285588234532L;
	private String str;
	private int size;

	public StringFigure(String input) {
		str = input;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		
		int style = Font.PLAIN;
		if (strokeWidth > 3) {
			style = Font.BOLD;
		}

		g.setFont(new Font(Font.SANS_SERIF, style, size));
		((Graphics2D)g).drawString(str, (int)x, (int)(y + (height + size)/2));
//		((Graphics2D)g).drawRect((int)x, (int)y, (int)width, (int)height);
		
	}
	
	@Override
	public boolean isSelected(Rectangle r) {
		return intersects(r) || contains(r);
	}

	@Override
	public void changeStrokeWidth(float inc) {
		float newStrokeWidth = strokeWidth + inc;
		if (newStrokeWidth > 3) {
			strokeWidth = 4;
		}
		else if (newStrokeWidth <= 3) {
			strokeWidth = 3;
		}
	}

	@Override
	public void setFigure(Point p1, Point p2) {
		width = Math.abs(p1.x - p2.x);
		updateSize();
		height = size;
		
		x = Math.min(p1.x, p2.x);
		y = Math.min(p1.y, p2.y) + (Math.abs(p1.y - p2.y) - size) / 2;
	}

    private static int countChinese(String str) {
        int count = 0;
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher(str);
        while(m.find()){
            count++;
        }
        return count;
    }

	@Override
	public void setSizePercent(double sizePercent) {
		super.setSizePercent(sizePercent);
		updateSize();
	}
    
	private void updateSize(){
		int numChinese = countChinese(str);
		int numWestern = str.length() - numChinese;
		size = (int)(width/(numChinese + (int)(numWestern*2.0/3)));
	}
}
