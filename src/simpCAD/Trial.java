package simpCAD;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class Trial extends JPanel{
	private JPanel canvas;
	
	public Trial() {
		canvas = new JPanel();
		canvas.setPreferredSize(new Dimension(500, 500));
		canvas.setFocusable(true);
		canvas.addMouseListener(new MouseInputAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.requestFocusInWindow();
				System.out.println("requested focus");
			}
		});		
		
		canvas.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				/*
				 * [KEY_PRESSED,
					keyText=Ctrl,
					keyCode=17,
					rawCode=17,
					extendedKeyCode=0x11
					modifiers=Ctrl,
					extModifiers=Ctrl,] 
				 */
				System.out.println("id:" + e.getID());
				System.out.println("Text:" + e.getKeyText(e.getKeyCode()));
				System.out.println("keyCode:" + e.getKeyCode());
				System.out.println("exKeyCode" + e.getExtendedKeyCode());
				System.out.println("keyMod:" + e.getModifiers());
				System.out.println("exKeyMod:" + e.getModifiersEx());
				System.out.println();
				if (e.isControlDown()){
					int keyCode = e.getExtendedKeyCode();
					switch (keyCode) {
					case KeyEvent.VK_COMMA:
						System.out.println("evoked ctrl + , ");
						break;
					case KeyEvent.VK_PERIOD:
						System.out.println("evoked ctrl + . ");
						break;
					case KeyEvent.VK_OPEN_BRACKET:
						System.out.println("evoked ctrl + [ ");
						break;
					case KeyEvent.VK_CLOSE_BRACKET:
						System.out.println("evoked ctrl + ] ");
						break;
					}
					canvas.repaint();
				}
			}			
		});
		
		add(canvas);
	}
	
	
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("simpCAD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new Trial());

        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
    	System.out.println(Toolkit.getDefaultToolkit().getScreenResolution());
    }

}
