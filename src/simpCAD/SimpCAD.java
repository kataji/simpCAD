package simpCAD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.ColorChooserUI;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import figure.Figure;
import figure.Line;
import figure.StringFigure;
import view.Canvas;

public class SimpCAD extends JPanel {
	
	private JButton lineButton;
	private JButton rectangleButton;
	private JButton ellipseButton;
	private JButton textButton;
	private JButton chooseCorlorButton;
	
	private Canvas canvas;
	
	private Point startPoint;
	private Point endPoint;
	private Figure pendingFigure = null;
	private Figure selectedFigure = null;
	private HashSet<Figure> figures = new HashSet<>();
	
	public SimpCAD() {
		// things to be put in frame
		
		
		// setup the canvas
		canvas = new Canvas(figures);
		canvas.setPreferredSize(new Dimension(900, 600));
		canvas.addMouseListener(new MouseInputAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.requestFocusInWindow();
//				System.out.println("clicked");
				Point p = e.getPoint();
				boolean selectSuccess = false;
				Rectangle r = new Rectangle(p.x - 10, p.y - 10, 20, 20);
				for(Figure f : figures) {
					if (f.intersects(r)) {
						selectedFigure = f;
						selectSuccess = true;
						System.out.println("selected " + f);
						break;
					}
				}
				if (selectSuccess == false) {
					selectedFigure = null;
					System.out.println("deselected");
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
//				System.out.println("pressed");
				startPoint = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
//				System.out.println("released");
				if (pendingFigure != null) {
					figures.add(pendingFigure);
					selectedFigure = pendingFigure;
					pendingFigure = null;					
					canvas.repaint();
				}
			}
			
		});
		
		canvas.addMouseMotionListener(new MouseInputAdapter() {
		
			@Override
			public void mouseDragged(MouseEvent e) {
//				System.out.println("dragged");
				endPoint = e.getPoint();
//				System.out.println("end point set at " + e.getPoint());
				if (pendingFigure != null) {
					pendingFigure.setBounds(startPoint, endPoint);
//					System.out.println("points set " + startPoint + endPoint);
					canvas.repaint();
//					System.out.println("repainted");
					pendingFigure.draw(canvas.getGraphics());
					System.out.println("draw called");
				}
			}
			
		});
		
		canvas.setFocusable(true);
		canvas.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed (KeyEvent e) {
				if ( selectedFigure != null && e.isControlDown()) {
					System.out.println("detected selected not null & ctrl down");
					int keyCode = e.getExtendedKeyCode();
					switch (keyCode) {
					case KeyEvent.VK_COMMA:
						System.out.println("evoked ctrl + , ");
						selectedFigure.setSizePercent(0.8); break;
					case KeyEvent.VK_PERIOD:
						selectedFigure.setSizePercent(1.2); break;
					case KeyEvent.VK_OPEN_BRACKET:
						selectedFigure.changeStrokeWidth(-1); break;
					case KeyEvent.VK_CLOSE_BRACKET:
						selectedFigure.changeStrokeWidth(1); break;
					}
					canvas.repaint();
				}
			}

		});
		
		lineButton = new JButton("直线");
		rectangleButton = new JButton("长方形");
		ellipseButton = new JButton("椭圆");
		textButton = new JButton("文字");
		chooseCorlorButton = new JButton("选择颜色");
		
		lineButton.addActionListener( e -> {
			pendingFigure = new Line();
			System.out.println("pending Line");
		});
		
		textButton.addActionListener(e -> {
			String input = (String) JOptionPane.showInputDialog(this, 
					"输入文字：", "输入文字", JOptionPane.INFORMATION_MESSAGE);
			pendingFigure = new StringFigure(input);
		});
		
		chooseCorlorButton.addActionListener(e ->{
			Color color = JColorChooser.showDialog(canvas, 
					"选择颜色", Color.BLACK);
			if (color != null) {
				selectedFigure.setColor(color);
				canvas.repaint();
			}
		});
		
		// for layout, put buttons in a separate panel
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.add(lineButton);
		toolBar.add(rectangleButton);
		toolBar.add(ellipseButton);
		toolBar.add(textButton);
		toolBar.addSeparator();
		toolBar.add(chooseCorlorButton);

		this.setLayout(new BorderLayout());
		add(toolBar, BorderLayout.LINE_END);
		add(canvas, BorderLayout.CENTER);
	}
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("simpCAD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new SimpCAD());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                createAndShowGUI();
            }
        });
    }

}
