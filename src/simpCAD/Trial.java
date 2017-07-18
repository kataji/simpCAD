//package simpCAD;
//
//import java.awt.Dimension;
//import java.awt.Toolkit;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.io.File;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//import javax.swing.event.MouseInputAdapter;
//import javax.swing.filechooser.FileFilter;
//
//public class Trial extends JPanel {
////	
////	public void mouseClicked(MouseEvent arg0) { 
////		
//		javax.swing.JFileChooser fc = new javax.swing.JFileChooser() { 
//			public String paramString() {  
//				return "drhdrhdrh"; 
//			}
//		};// 这里新建一个chooser    
//			
//		FileFilter filter = new FileFilter() {
//			public boolean accept(File f) { 
//				return f.isDirectory() || (f.isFile() && (
//						f.getName().endsWith(".htm")
//						|| f.getName().endsWith(".HTM") 
//						|| f.getName().endsWith(".html") 
//						|| f.getName().endsWith(".HTML") 
//						)); // 新建一个文件类型过滤器
//			} 
//
//			public String getDescription() { 
//				return "保存为HTML文件格式"; 
//			} 
//		};
//
//		public Trial() {
//			
//		
//		fc.setFileFilter(filter);
//		int i = fc.showSaveDialog(this); // 打开保存文件对话框
//		String fname = null;
//		if(i == javax.swing.JFileChooser.APPROVE_OPTION) {
//			System.out.println("approved");
//			File file = fc.getSelectedFile();
//			System.out.println(file.getAbsolutePath());
//			// 注意这里，和下面一句, 如果这里并没有选取中任何的文件
//			//下面的jfc.getName(f)将会返回手输入的文件名
//			fname = fc.getName(file); 
//			if(fname != null && fname.trim().length()>0) {
//				if(fname.endsWith(".htm") || fname.endsWith(".HTM")
//						|| fname.endsWith(".html") || fname.endsWith(".HTML"));
//				else {
//					fname = fname.concat(".htm"); 
//				} 
//			} 
//
//			System.out.println(fname);
//			
//			if(file.isFile()) {
//				fname = file.getName(); 
//				System.out.println("file isFile");
//				System.out.println("newly got file name: " + fname);
//			}
//				
//
//			file = fc.getCurrentDirectory();
//			// 取得要保存的文件的目录,其实getSelectedFile();
//			// 已经包括了文件路径,这里这样是让大家更易了解
//
//			file = new File(file.getPath().concat(File.separator).concat(fname));
//
//			System.out.println("new file: " + file);
//			if(file.exists()) {  
//				i = javax.swing.JOptionPane.showConfirmDialog(
//						this, "该文件已经存在，确定要覆盖吗？"); 
//
//				if(i == javax.swing.JOptionPane.YES_OPTION);     
//				else 
//					return ; 
//			} 
////
////			try {  
//				file.createNewFile();
////				java.io.FileWriter fw = new java.io.FileWriter(file);  
//////				fw.write(getJtp_html().getText()); 
////				fw.close();
////				// 向要保存的文件写数据 
////			} catch(Exception ex) {  
////				javax.swing.JOptionPane.showMessageDialog(
////						this, "出错：" + ex.getMessage());  
////				return ; 
////			} 
//		}
//	}  
//	
//	public static void main(String[] args) {
//		new Trial();
//	}
//}