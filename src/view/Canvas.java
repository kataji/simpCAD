package view;

import java.awt.Graphics;
import java.util.HashSet;

import javax.swing.JPanel;

import figure.Figure;

public class Canvas extends JPanel{
	private HashSet<Figure> figures;
	
	public Canvas(HashSet<Figure> figures) {
		this.figures = figures;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Figure f : figures) {
			f.draw(g);
		}
	}
}
