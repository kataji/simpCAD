package view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;

import figure.Figure;

public class CanvasTrial extends JPanel {

	private ArrayList<Figure> figures;
	
	public CanvasTrial(ArrayList<Figure> figures) {
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
