package simpCAD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileFilter;

import figure.Ellipse;
import figure.Figure;
import figure.Line;
import figure.StringFigure;
import view.Canvas;
import view.CanvasTrial;

public class Trial extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3154132016887305133L;
	
	private JMenuBar menuBar;
	
	private JButton lineButton;
	private JButton rectangleButton;
	private JButton ellipseButton;
	private JButton textButton;
	private JButton chooseCorlorButton;
	
	private CanvasTrial canvas;
	
	private Point startPoint;
	private Point endPoint;
	private Point lastEndPoint;
	private Figure pendingFigure = null;
	private Figure selectedFigure = null;
	private ArrayList<Figure> figures = new ArrayList<>();
	
	public Trial() {
		
		//set up menu bar
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("文件");
		JMenu helpMenu = new JMenu("帮助");
		JMenuItem openMenuItem = new JMenuItem("打开...");
		JMenuItem saveMenuItem = new JMenuItem("保存...");
		JMenuItem welcomeMenuItem = new JMenuItem("欢迎");
		JMenuItem aboutMenuItem = new JMenuItem("关于");
		
		JFileChooser fc = new JFileChooser();
		FileFilter filter = new FileFilter() {
			public boolean accept(File f) {
				if (f == null )
					return false;
				return f.isDirectory() || (f.isFile() && (
						f.getName().endsWith(".dat") || 
						f.getName().endsWith(".DAT")));
			} 

			public String getDescription() { 
				return "DAT文件格式"; 
			} 
		};
		fc.setFileFilter(filter);
		
		openMenuItem.addActionListener(e -> {
			
			int result = fc.showOpenDialog(this);
			
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				Object o = readObjectFromFile(file);
				if (o instanceof HashSet<?>) {
					o = (HashSet<?>) o;
					if(o.getClass().equals(figures.getClass())) {
						HashSet<Figure> newFigures = (HashSet<Figure>) o;
						figures.clear();
						figures.addAll(newFigures);
						canvas.repaint();
					}					
				}
			}
			
		});
		
		saveMenuItem.addActionListener(e -> {
			int result = fc.showSaveDialog(this);
			
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();			
				if(file.exists()) {  
					int ret = javax.swing.JOptionPane.showConfirmDialog(
							this, "该文件已经存在，确定要覆盖吗？"); 
					if (ret != javax.swing.JOptionPane.YES_OPTION)
						return; 
				} 
				
				String path = file.getAbsolutePath(); 
				if(!path.endsWith(".dat") && !path.endsWith(".DAT")) {
					path = path.concat(".dat"); 
				}
				file = new File(path);
				
				try {  
					file.createNewFile();
					writeObjectToFile(figures, file);
				} catch(Exception e1) {  
					javax.swing.JOptionPane.showMessageDialog(
							this, "出错：" + e1.getMessage());  
					e1.printStackTrace();
					return ; 
				} 
			}
			
		});
		
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		helpMenu.add(welcomeMenuItem);
		helpMenu.add(aboutMenuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		
		
		// setup the canvas
		canvas = new CanvasTrial(figures);
		canvas.setOpaque(true);
		canvas.setBackground(Color.WHITE);
		canvas.setPreferredSize(new Dimension(900, 600));
		canvas.addMouseListener(new MouseInputAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.requestFocusInWindow();
				Point p = e.getPoint();
				boolean selectSuccess = false;
				Rectangle r = new Rectangle(p.x - 8, p.y - 8, 16, 16);
				for(Figure f : figures) {
					if (f.isSelected(r)) {
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
				Point p = e.getPoint();
				startPoint = p;
				endPoint = p;
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
				lastEndPoint = endPoint;
				endPoint = e.getPoint();
				if (pendingFigure != null) {					
					pendingFigure.setBounds(startPoint, endPoint);
					canvas.repaint();
					pendingFigure.draw(canvas.getGraphics());
				}
				else if (selectedFigure != null) {
					selectedFigure.setLocation(lastEndPoint, endPoint);
					System.out.println(selectedFigure.getCenterX() + "," +
					selectedFigure.getCenterY());
					canvas.repaint();
				}
			}
			
		});
		
		canvas.setFocusable(true);
		canvas.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed (KeyEvent e) {
				if ( selectedFigure != null ) {
					if (e.isControlDown()){
						int keyCode = e.getExtendedKeyCode();
						switch (keyCode) {
						case KeyEvent.VK_MINUS:
							selectedFigure.setSizePercent(0.8); break;
						case KeyEvent.VK_EQUALS:
							selectedFigure.setSizePercent(1.2); break;
						case KeyEvent.VK_OPEN_BRACKET:
							selectedFigure.changeStrokeWidth(-1); break;
						case KeyEvent.VK_CLOSE_BRACKET:
							selectedFigure.changeStrokeWidth(1); break;
						}
						canvas.repaint();
					}
					else if (e.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
						figures.remove(selectedFigure);
						canvas.repaint();
					}
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
		
		rectangleButton.addActionListener(e -> {
			pendingFigure = new figure.Rectangle();
			System.out.println("pending Rect");
		});
		
		ellipseButton.addActionListener(e -> {
			pendingFigure = new Ellipse();
			System.out.println("pending ellipse");
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
		add(menuBar, BorderLayout.PAGE_START);
	}
	
	/*
	 * 如下关于对象在文件中的读写的代码来自：
	 * http://www.cnblogs.com/hrlnw/p/3617478.html
	 */
	
    public void writeObjectToFile(Object obj, File file)
    {
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
        } catch (IOException e) {
        	JOptionPane.showMessageDialog(
					this, "出错：" + e.getMessage()); 
        	e.printStackTrace();
        }
    }
	
	public Object readObjectFromFile(File file)
    {
        Object temp=null;    
        
        try {
        	FileInputStream in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
		} catch (FileNotFoundException e) {
			javax.swing.JOptionPane.showMessageDialog(
					this, "出错：" + "文件不存在");  
			e.printStackTrace();
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(
					this, "出错：" + "文件类型不正确");  
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        return temp;
    }
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Trial");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new Trial());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                //Turn off metal's use of bold fonts
//                createAndShowGUI();
//            }
//        });
//    	
    	String str = null;
    	File file = new File("D:\\Documents\\trial.dat");
    	SimpCAD s = new SimpCAD();
    	s.writeObjectToFile(str, file);
    }

}
