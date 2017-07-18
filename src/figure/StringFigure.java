package figure;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFigure extends Figure {
	private String str;

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
		int numChinese = countChinese(str);
		int numWestern = str.length() - numChinese;
		g.setFont(new Font(Font.SANS_SERIF, style, 
				width/(numChinese + (int)(numWestern*2.0/3))));
		((Graphics2D)g).drawString(str, x, y + (int)(height*1));
		((Graphics2D)g).drawRect(x, y, width, height);
		
	}
	
	@Override
	public boolean isSelected(Rectangle r) {
		return intersects(r) || contains(r);
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
}
