package cs420.mipsilon;

import cs420.mipsilon.core.*;
import cs420.mipsilon.assembler.*;
import cs420.mipsilon.frames.*;

import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.BorderLayout;


import java.io.File;
import java.net.URL;
import java.net.URI;

final class Initializer
{
	
	private Application app;
	
	private AppControl appControl;
		
	private JMenu windowMenu;
	private JToolBar mainTools;	
	
	
	Initializer(Application app)
	throws Exception
	{
		this.app = app;
		init();
	}
	
	private void init()
	throws Exception
	{		
		appControl = new AppControl(app);
			app.frame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					System.exit(0);
				}			
		});
		
		app.frame.setJMenuBar(buildMenu());			
		app.frame.getJMenuBar().revalidate(); //not sure why.		
			
		app.text = new Memory();
		app.data = new Memory();
		app.cpu = new CPU(app.text, app.data);
		app.loader = new Loader(app.text, app.data);
		
		app.assembler = new Assembler();
		//loadProgram();				
		loadWindows();
			
		app.splash.postLine("Loading tools...");
			
		app.frame.getContentPane().add(mainTools = createToolBar(), BorderLayout.PAGE_START);
		mainTools.revalidate();		
		
		// done initializing
	}
	
	private void loadProgram()
	throws Exception	
	{		
		app.splash.postLine("Loading the program...");				
		try{
			app.assembler.assemble( getClass().getResourceAsStream("/sample1.mips") );					
			app.loader.loadText(app.assembler.getText(), 0x00000000);
			app.loader.loadData(app.assembler.getData(), 0x00000000);
			app.cpu.reset();
		}
		catch(Exception e) {throw e;}
		
		
	}	
		
	private JToolBar createToolBar()	
	{		
		JToolBar tb = new JToolBar("tools");
		JButton play, pause, step, stop;
		try{
			URL url;
			//url = getClass().getResource("/Play16.gif");
			//tb.add(play = new JButton(new ImageIcon( url )));
			//url = getClass().getResource("/Pause16.gif");
			//tb.add(pause = new JButton(new ImageIcon( url )));
			url = getClass().getResource("/StepForward16.gif");
			tb.add(step = new JButton(new ImageIcon( url )));
			url = getClass().getResource("/Stop16.gif");
			tb.add(stop = new JButton(new ImageIcon( url )));
								
			
		//play.addActionListener(appControl);
		//play.setActionCommand("PLAY");
		//pause.addActionListener(appControl);
		//pause.setActionCommand("PAUSE");		
		step.addActionListener(appControl);
		step.setActionCommand("STEP");
		stop.addActionListener(appControl);
		stop.setActionCommand("STOP");
		}catch(Exception e){}		
		
		return tb;
	}			
	
	private JMenuBar buildMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.add(createFileMenu());
		menuBar.add(createCPUMenu());
		menuBar.add(createSampleProgramsMenu());
		menuBar.add(createWindowMenu());
		menuBar.add(createHelpMenu());
		
		return 	menuBar;
	}
	
	private JMenu createFileMenu()
	{
		JMenu fileMenu = new JMenu("File");
			
		fileMenu.add(createMenuItem("Load", "LOAD" ));
		fileMenu.addSeparator();
		fileMenu.add(createMenuItem("Exit", "EXIT"));
				
		return fileMenu;
	}
	
	private JMenu createCPUMenu()
	{
		JMenu cpuMenu = new JMenu("Machine");
				
		cpuMenu.add(createMenuItem("Hard Reset", "HARD_RESET"));		
		cpuMenu.add(createMenuItem("Soft Reset", "SOFT_RESET"));
				
		return cpuMenu;
	}
	
	private JMenu createWindowMenu()	
	{
		windowMenu = new JMenu("Window");
				
		return windowMenu;
	}
	
	private JMenu createSampleProgramsMenu()
	{
		JMenu sampleProgramsMenu = new JMenu("Programs");
		
		sampleProgramsMenu.add( createMenuItem("Sample 1", "LOAD_SAMPLE_1") );
		sampleProgramsMenu.add( createMenuItem("Sample 2", "LOAD_SAMPLE_2") );
		return sampleProgramsMenu;
	}
	
	
	private JMenu createHelpMenu()
	{
		JMenu helpMenu = new JMenu("Help");
		
		//helpMenu.add( createMenuItem("Help", "HELP") );
		//helpMenu.addSeparator();
		helpMenu.add( createMenuItem("About", "ABOUT") );
				
		return helpMenu;
	}
	
	private JMenuItem createMenuItem(String name, String command )
	{
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.setActionCommand(command);
		menuItem.addActionListener(appControl);
		
		return menuItem;
	}
	
	
	private void loadWindows()
	{	
			
		app.splash.postLine("Displaying registers...");
		windowMenu.add( new JMenuItem("Registers")).addActionListener(new ShowHideAction(app.registerFrame = new RegisterFrame("Registers", app.cpu.getRegisters())));
		app.registerFrame.reshape(265,340,515,260);
		app.registerFrame.setVisible(true);
		app.desktop.add(app.registerFrame);
		
		app.cpu.addObserver(app.registerFrame);
				
		app.splash.postLine("Loading memory...");
		windowMenu.add( new JMenuItem("Text")).addActionListener(new ShowHideAction(app.textFrame = new TextFrame("Text", app.text)));
		app.textFrame.reshape(30,30,225,300);
		app.textFrame.setVisible(true);
		app.desktop.add(app.textFrame);
		
		app.cpu.addObserver(app.textFrame);
		app.text.addObserver(app.textFrame);
		
		windowMenu.add( new JMenuItem("Data")).addActionListener(new ShowHideAction(app.dataFrame = new DataFrame("Data", app.data)));
		app.dataFrame.reshape(30,340,225,300);
		app.dataFrame.setVisible(true);
		app.desktop.add(app.dataFrame);		
				
		app.cpu.addObserver(app.dataFrame);
		app.data.addObserver(app.dataFrame);
				
		app.splash.postLine("Loading disassembler...");
		windowMenu.add( new JMenuItem("Disassembly")).addActionListener(new ShowHideAction(app.disassemblyFrame = new DisassemblyFrame("Disassembly", app.text)));
		app.disassemblyFrame.reshape(265,30,400,300);
		app.disassemblyFrame.setVisible(true);
		app.desktop.add(app.disassemblyFrame);
				
		app.cpu.addObserver(app.disassemblyFrame);
		app.text.addObserver(app.disassemblyFrame);
		
		app.splash.postLine("Loading cpu simulator...");
		windowMenu.add( new JMenuItem("CPU Simulator")).addActionListener(new ShowHideAction(app.cpuFrame = new CPUFrame("MIPS CPU Simulation", app.cpu, app.text)));
		app.cpuFrame.reshape(200,100,600,405);
		app.cpuFrame.setVisible(true);
		app.desktop.add(app.cpuFrame);		
		app.cpu.addObserver(app.cpuFrame);
				
	}
	
	
}
