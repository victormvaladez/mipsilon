package cs420.mipsilon;

import cs420.mipsilon.core.*;
import cs420.mipsilon.assembler.*;
import cs420.mipsilon.frames.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;
import javax.swing.JFrame;

import javax.swing.JOptionPane;

import java.net.URL;

import java.io.File;
import java.io.InputStream;
import javax.swing.ImageIcon;

class Application {
	
	
	// windows and such
	SplashWindow splash = null;	
	JFrame frame = null;
	JDesktopPane desktop = null;
	
	
	// Component frames (views for the comonents)
	RegisterFrame 		registerFrame;
	CPUFrame 		cpuFrame;	
	DisassemblyFrame 	disassemblyFrame;
	TextFrame 		textFrame;
	DataFrame 		dataFrame;
	
	
	// Components
	CPU cpu;
	Memory text;
	Memory data;	
	Loader loader;
	
	///
	Assembler assembler;
			
	static public void main(String[] args) {
		Application app = new Application();
	}	
	
	private Application()
	{		
		initialize();	
	}	
		
	private void initialize()	
	{
		int inset = 100;
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		try{
			frame = new JFrame("Mipsilon: Virtual MIPS Environment");
			splash = createSplashWindow();
			splash.show();		
			splash.postLine("Loading environment...");
						
			frame.getContentPane().add(desktop = new JDesktopPane());
			
					
			frame.setBounds(inset, inset, screenDim.width  - inset*2, screenDim.height - inset*2);
			
		}catch(Exception e){applicationError("An error has occured");}
				
		try{
			UIManager.setLookAndFeel(
			UIManager.getCrossPlatformLookAndFeelClassName());
		}catch(Exception e){}		
		frame.show();
		
		
			splash.postLine("Initializing...");			
			try{
				Initializer init = new Initializer(this);
				splash.postLine("Ready.");
				splash.setFinished(true);
			}
			catch(Exception e)
			{ 
				splash.postLine("ERROR:");
				splash.postLine(e.getMessage());
				splash.setError();
			}
	}
	
	SplashWindow createSplashWindow()	
	{
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();		
		URL url = Application.class.getResource("/splash.gif");	
		SplashWindow sp = new SplashWindow(frame, new ImageIcon( url ));
		sp.setBounds((screenDim.width-300)/2,(screenDim.height-200)/2, 300, 225);
		return sp;
	}
	
	
	void applicationError(String message)
	{
		JOptionPane.showMessageDialog(desktop, message);
	}
	
	void loadProgram(File file)
	{		
		text.clear();
		data.clear();
		try
		{			
			assembler.assemble(file);
			loader.loadText(assembler.getText(), 0x00000000);
			loader.loadData(assembler.getData(), 0x00000000);			
			cpu.reset();			
		}		
		catch(Exception e) {applicationError(e.getMessage()); }
	}
	
	void loadProgram(InputStream ins)
	{
		text.clear();
		data.clear();
		try
		{			
			assembler.assemble(ins);
			loader.loadText(assembler.getText(), 0x00000000);
			loader.loadData(assembler.getData(), 0x00000000);			
			cpu.reset();			
		}		
		catch(Exception e) {applicationError(e.getMessage()); }
	}
	
}