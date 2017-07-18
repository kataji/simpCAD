//package simpCAD;
//
//import java.awt.event.MouseEvent;
//import java.io.File;
//
//import javax.swing.filechooser.FileFilter;
//
//public class SaveFileMethod {
//	
//	public void mouseClicked(MouseEvent arg0) {
//		javax.swing.JFileChooser jfc = new javax.swing.JFileChooser() {
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
//						)); 
//			}  
//			
//			public String getDescription() { 
//				return "保存为HTML文件格式";   
//			}    
//		};// 新建一个文件类型过滤器 
//		
//		jfc.setFileFilter(filter);  
//		
//		int i = jfc.showSaveDialog(jContentPane); // 打开保存文件对话框    
//		String fname = null;
//		if(i == javax.swing.JFileChooser.APPROVE_OPTION) {
//			File f = jfc.getSelectedFile();
//			// 注意这里，和下面一句, 如果这里并没有选取中任何的文件，
//			// 下面的jfc.getName(f)将会返回手输入的文件名  
//			fname = jfc.getName(f);  
//			if(fname != null && fname.trim().length()>0) {   
//				if(fname.endsWith(".htm") || fname.endsWith(".HTM") || 
//						fname.endsWith(".html") || fname.endsWith(".HTML"))   
//					;    
//				else {     
//					fname = fname.concat(".htm");   
//				}   
//			} 
//			if(f.isFile())  
//				fname = f.getName();    
//			f = jfc.getCurrentDirectory();
//			// 取得要保存的文件的目录,其实getSelectedFile();
//			// 已经包括了文件路径,这里这样是让大家更易了解        
//			f = new File(f.getPath().concat(File.separator).concat(fname));  
//			if(f.exists()) {     
//				i = javax.swing.JOptionPane.showConfirmDialog(jContentPane,
//						"该文件已经存在，确定要覆盖吗？");   
//				if(i == javax.swing.JOptionPane.YES_OPTION)    
//					;   
//				else   
//					return ;  
//			}  
//			
//			try {  
//				f.createNewFile();  
//				java.io.FileWriter fw = new java.io.FileWriter(f);    
//				fw.write(getJtp_html().getText());  
//				fw.close();// 向要保存的文件写数据   
//			} catch(Exception ex) {  
//				javax.swing.JOptionPane.showMessageDialog(jContentPane,
//						"出错：" + ex.getMessage());  
//				return ;  
//			}    
//		}
//	}  
//}
