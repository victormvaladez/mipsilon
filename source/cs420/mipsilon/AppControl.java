package cs420.mipsilon;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import cs420.mipsilon.core.CPUException;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.filechooser.FileFilter;

class AppControl extends AbstractAction{
		
	Application app;
	
	FileFilter ff;
		
	AppControl(Application theApp)
	{
		app = theApp;
		ff = new FileFilter(){
			public boolean accept(File f)
			{
				if (f.isDirectory())
					return true;
					
				String s = f.getName();
				int i = s.lastIndexOf('.');
				
				if (i > 0 &&  i < s.length() - 1)
				{
					String extension = s.substring(i+1).toLowerCase();

					if (extension.equals("mips"))
						return true;
					else 
					   return false;
				}
				return false;
			}
			
			public String getDescription()
			{
				return "MIPS source file";
			}
			
				
          
		};
		
	}
		
	public void actionPerformed(ActionEvent ae)
	{	
		
		String ac = ae.getActionCommand();		
		if( ac == "STEP")
			doStep();
		else if( ac == "PLAY")
			doPlay();
		else if( ac == "PAUSE")
			doPause();
		else if( ac == "STOP")
			doStop();
		else if( ac == "LOAD")
			doLoad();
		else if(ac == "EXIT")
			doExit();
		else if(ac == "HARD_RESET")
			doHardReset();
		else if(ac == "SOFT_RESET")
			doSoftReset();
		else if(ac == "ABOUT")
			doAbout();
		else if(ac == "HELP")
			doHelp();
		else if(ac == "LOAD_SAMPLE_1")
			loadSample(1);
		else if(ac == "LOAD_SAMPLE_2")
			loadSample(2);
		
	}
	
	void loadSample(int sample)
	{
		//app.applicationError("Will load sample program " + sample);
		
		try{
			switch(sample)
			{
				case 1:
					app.loadProgram( getClass().getResourceAsStream("/sample1.mips") );
				break;
				case 2:
					app.loadProgram( getClass().getResourceAsStream("/sample2.mips") );
				break;
			}			
		}
		catch(Exception e) {app.applicationError(e.getMessage());}
	}
	
	void doStop()
	{
		doSoftReset();
	}
	
	void doPlay()
	{
		//start the machine
		
	}
	
	void doPause()
	{
		//pause the machine
		
	}
	
	
	void doStep()
	{
		try
		{
			app.cpu.execute();			
		}
		catch(CPUException ce)
		{
			 app.applicationError(ce.getMessage());
		}
				
	}
	
	void doLoad()
	{
		JFileChooser jfc = new JFileChooser();		
		jfc.setFileFilter(ff);
		int result = jfc.showOpenDialog(app.desktop);		
		
    		if(result == JFileChooser.APPROVE_OPTION) 
    		{
			app.loadProgram(jfc.getSelectedFile());	            		
    		}
	}
	
	void doExit()
	{		
		System.exit(0);
	}
	
	void doHardReset()
	{
		//clear Memory
		//reset PC
		//reset Registers		
		app.text.clear();
		app.data.clear();
		app.cpu.reset();
	}
	
	void doSoftReset()
	{
		//do not clear Memory
		//reset PC
		//reset Registers
		app.cpu.reset();
	}
	
	void doAbout()
	{
		char copyright = 0x00A9;
		char trademark = 0x2122;
		char rr = 0x042f;
		SplashWindow about = app.createSplashWindow();
		
		
		about.postLine("R E D " + rr+ " U M ");
		about.postLine(" ");		
		about.postLine(" ");
		about.postLine(" ");
		about.postLine( copyright +  " 2004");
		about.postLine("mipsilon" + trademark + " v1.0 ");
		about.postLine("Program by Victor Valadez");
		about.postLine(" ");
		
		about.setFinished(true);
		about.show();		
	}
	
	void doHelp()
	{
		app.applicationError("No help yet");
	}
	
}