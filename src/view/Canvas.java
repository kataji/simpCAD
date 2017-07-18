package view;

import java.awt.Graphics;

import javax.swing.JPanel;

import figure.Figure;

public class Canvas extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9050728494984056759L;
	private GenericList<Figure> figures;
	
	public Canvas(GenericList<Figure> figures) {
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
