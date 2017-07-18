package simpCAD;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
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
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileFilter;

import figure.Ellipse;
import figure.Figure;
import figure.Line;
import figure.Rectangle;
import figure.StringFigure;
import view.Canvas;

public class SimpCAD extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3154132016887305133L;
	
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private Canvas canvas;
	
	private Point startPoint;
	private Point endPoint;
	private Point lastEndPoint;
	private Figure pendingFigure = null;
	private Figure selectedFigure = null;
//	private GenericSet<Figure> figures = new GenericSet<>(Figure.class);
	private HashSet<Figure> figures = new HashSet<>();
	
	public SimpCAD() {
		setUpMenuBar();
		setUpToolBar();
		setUpCanvas();
		
		this.setLayout(new BorderLayout());
		add(toolBar, BorderLayout.LINE_END);
		add(canvas, BorderLayout.CENTER);
		add(menuBar, BorderLayout.PAGE_START);
	}
	
	private void setUpMenuBar() {
		//set up menu bar
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("文件");
		JMenu helpMenu = new JMenu("帮助");
		JMenuItem openMenuItem = new JMenuItem("打开...");
		JMenuItem saveMenuItem = new JMenuItem("保存...");
		JMenuItem welcomeMenuItem = new JMenuItem("欢迎");
		JMenuItem aboutMenuItem = new JMenuItem("关于");
		
		// 设置JFileChooser
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
		
		// 设置打开菜单项
		openMenuItem.addActionListener(e -> {			
			int result = fc.showOpenDialog(this);
			
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();				
				if (!file.exists()) {
					JOptionPane.showMessageDialog(this, "文件不存在", 
							"错误", JOptionPane.ERROR_MESSAGE);  
				}
				else {
					Object o = readObjectFromFile(file);
					boolean castSuccess = false;
					if (o instanceof GenericSet<?> ) {
						GenericSet<?> s = (GenericSet<?>) o;
						if(s.getGenericType().equals(Figure.class)) {
							@SuppressWarnings("unchecked")
							GenericSet<Figure> newFigures = (GenericSet<Figure>) s;
							castSuccess = true;
							
							figures.clear();
							figures.addAll(newFigures);
							canvas.repaint();
						}					
					}
					if (!castSuccess) {
						JOptionPane.showMessageDialog(this, "文件类型不正确",
								 "错误", JOptionPane.ERROR_MESSAGE);  
					}
				}
			}			
		});
		
		// 设置保存菜单项
		saveMenuItem.addActionListener(e -> {
			int result = fc.showSaveDialog(this);
			
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				String path = file.getAbsolutePath(); 
				if(!path.endsWith(".dat") && !path.endsWith(".DAT")) {
					path = path.concat(".dat"); 
				}
				file = new File(path);
				
				int confirmOverride = JOptionPane.NO_OPTION;
				if(file.exists()) {  
					confirmOverride = JOptionPane.showConfirmDialog(
							this, "该文件已经存在，确定要覆盖吗？",
							"确认覆盖", JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE); 
				} 
				
				if (!file.exists() || confirmOverride == JOptionPane.YES_OPTION) {
					try {  
						file.createNewFile();
						writeObjectToFile(figures, file);
					} catch(Exception e1) {  
						JOptionPane.showMessageDialog(this, e1.getMessage(), 
								"错误", JOptionPane.ERROR_MESSAGE);  
						e1.printStackTrace();
						return ; 
					} 
				}
			}
		});
		
		// 设置欢迎菜单项
		welcomeMenuItem.addActionListener(e -> {
			StringBuffer sb = new StringBuffer();
			sb.append("欢迎使用simpCAD\n\n");
			sb.append("simpCAD实现了简单的绘图功能，包括直线、长方形、椭圆和文字的绘制。\n");
			sb.append("鼠标点击右侧按钮，可选择需要绘制的图形。\n");
			sb.append("点击后在画布内拖动绘制。\n\n");
			sb.append("绘制后，点击选中，可进行修改，包括：\n");
			sb.append("更改颜色 - 使用选择颜色按钮\n");
			sb.append("更改大小 - 使用\'Ctrl + -\'（减小）和 \'Ctrl + =\'（增大）\n");
			sb.append("更改线条粗细 - 使用\'Ctrl + [\'（减细）和\'Ctrl + ]\'（增粗）\n");
			sb.append("更改位置 - 使用鼠标拖动\n");
			sb.append("删除图形 - 使用\'Del\'键\n");
			JOptionPane.showMessageDialog(this, 
					new JTextArea(sb.toString()), 
					"欢迎", JOptionPane.PLAIN_MESSAGE);
		});
		
		// 设置关于菜单项
		aboutMenuItem.addActionListener(e -> {
			StringBuffer sb = new StringBuffer();
			sb.append("simpCAD\n\n");
			sb.append("Version: 0.1.0\n");
			sb.append("(c) Copyright kataji 2017.\n");
			sb.append("All rights reserved. \n");
			sb.append("https://github.com/kataji/simpCAD\n\n");
			sb.append("鸣谢The Java™ Tutorials\n");
			sb.append("http://docs.oracle.com/javase/tutorial/\n");
			JOptionPane.showMessageDialog(this, 
					new JTextArea(sb.toString()), 
					"关于", JOptionPane.INFORMATION_MESSAGE);
		});
		
		// 将菜单项放入菜单，将菜单放入菜单栏
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		helpMenu.add(welcomeMenuItem);
		helpMenu.add(aboutMenuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
	}
	
	private void setUpToolBar() {

		JButton lineButton = new JButton("直线");
		JButton rectangleButton = new JButton("长方形");
		JButton ellipseButton = new JButton("椭圆");
		JButton textButton = new JButton("文字");
		JButton chooseCorlorButton = new JButton("选择颜色");
		
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
				canvas.requestFocusInWindow();
			}
		});
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(JToolBar.VERTICAL);
		toolBar.add(lineButton);
		toolBar.add(rectangleButton);
		toolBar.add(ellipseButton);
		toolBar.add(textButton);
		toolBar.addSeparator();
		toolBar.add(chooseCorlorButton);
	}
	
	private void setUpCanvas() {
		// setup canvas, add mouse and key listeners
		
		// new canvas
		canvas = new Canvas(figures);
		canvas.setOpaque(true);
		canvas.setBackground(Color.WHITE);
		canvas.setPreferredSize(new Dimension(900, 600));
		
		// add Mouse Listeners
		canvas.addMouseListener(new MouseInputAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				Point p = new Point(e.getPoint());
				Rectangle r = new Rectangle(p.x-5, p.y-5, 10, 10);
				boolean selectSuccess = false;
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
				canvas.requestFocusInWindow();
				Point p = new Point(e.getPoint());
				startPoint = p;
				endPoint = p;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (pendingFigure != null) {
					figures.add(pendingFigure);
					selectedFigure = pendingFigure;
					System.out.println("new: " + selectedFigure);
					pendingFigure = null;					
					canvas.repaint();
				}

			}
			
		});
		
		canvas.addMouseMotionListener(new MouseInputAdapter() {
		
			@Override
			public void mouseDragged(MouseEvent e) {
				lastEndPoint = endPoint;
				endPoint = new Point(e.getPoint());
				if (pendingFigure != null) {					
					pendingFigure.setFigure(startPoint, endPoint);
					canvas.repaint();
					pendingFigure.draw(canvas.getGraphics());
				}
				else if (selectedFigure != null) {
					selectedFigure.setLocation(lastEndPoint, endPoint);
					canvas.repaint();
				}
			}
			
		});
		
		// add key listener
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
						boolean removed = figures.remove(selectedFigure);
						System.out.println("entered del handling");
						System.out.println("contained:" + removed);
						System.out.println(figures);
						System.out.println(selectedFigure);
						System.out.println("contains:" + figures.contains(selectedFigure));
						canvas.repaint();
					}
				}

			}

		});
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
        	JOptionPane.showMessageDialog(this, e.getMessage(),
					"错误", JOptionPane.ERROR_MESSAGE); 
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
			e.printStackTrace();
		} catch (IOException e) {
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
        JFrame frame = new JFrame("simpCAD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add content to the window.
        frame.add(new SimpCAD());

        //Display the window.
        frame.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();        
        frame.setLocation((d.width - frame.getWidth())/2, 
        		(d.height - frame.getHeight())/2);
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
