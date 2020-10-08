package cs420.mipsilon.frames;


import cs420.mipsilon.core.CPU;
import cs420.mipsilon.core.Disassembler;
import cs420.mipsilon.core.Instruction;
import cs420.mipsilon.core.Memory;

import cs420.mipsilon.frames.views.*;

import java.net.URL;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import cs420.mipsilon.core.MIPSObservation;
import java.util.Observer;
import java.util.Observable;

public class CPUFrame extends AbstractFrame implements Observer{

	CPU cpu;
	Memory text;

	ZoomPanel zp;
	JScrollPane sp;
	JLabel zoomPercentage;
	JLabel instructionLabel;

	ViewColorManager defaultVCM;
	ViewColorManager greyVCM;
	ViewColorManager wackyVCM;

	LabelsView lv;
	DataLinesView dlv;
	ControlUnitView cuv;
	StaticView sv;

	int oldPC = 0;

	public CPUFrame(String frameName, CPU cpu, Memory text )
	{
	        super(frameName);

	        this.cpu = cpu;
	        this.text = text;

	        setupColors();


	        zp = new ZoomPanel(greyVCM.getColor("background"));
	        setJMenuBar(setupMenu());
	       	zp.addStaticView(dlv = new  DataLinesView(greyVCM));
	        zp.addStaticView(sv = new StaticView(greyVCM));
	        zp.addStaticView( lv = new LabelsView(greyVCM));
	        zp.addDynamicView( cuv =  new ControlUnitView(greyVCM, cpu ));

		changeColor(defaultVCM);

	        sp = new JScrollPane(zp);
		getContentPane().add(sp);

	}

	private void setupColors()
	{
		defaultVCM = new ViewColorManager();
	        greyVCM = new ViewColorManager();
	        wackyVCM = new ViewColorManager();

	        defaultVCM.addColor("background", new Color(0xFFFFFF) );
	        defaultVCM.addColor("innerLabel", new Color(0xFFFFFF) );
	        defaultVCM.addColor("outerLabel", new Color(0x000000) );
	        defaultVCM.addColor("arithLogic", new Color(0x6A68C5) );
	        defaultVCM.addColor("control",    new Color(0xE6C944) );
	        defaultVCM.addColor("registers",  new Color(0xC56868) );
	        defaultVCM.addColor("memory",     new Color(0x68C56C) );
	        defaultVCM.addColor("dataLines",  new Color(0x000000) );
	        defaultVCM.addColor("controlLineOn",  new Color(0xFF0000) );
	        defaultVCM.addColor("controlLineOff", new Color(0x888888) );
	        defaultVCM.addColor("outline",        new Color(0x000000) );

	        greyVCM.addColor("background", new Color(0xFFFFFF) );
	        greyVCM.addColor("innerLabel", new Color(0x000000) );
	        greyVCM.addColor("outerLabel", new Color(0x000000) );
	        greyVCM.addColor("arithLogic", new Color(0xC5C5C5) );
	        greyVCM.addColor("control",    new Color(0xE6E6E6) );
	        greyVCM.addColor("registers",  new Color(0xC0C0C0) );
	        greyVCM.addColor("memory",     new Color(0xCACACA) );
	        greyVCM.addColor("dataLines",  new Color(0x000000) );
	        greyVCM.addColor("controlLineOn",  new Color(0x888888) );
	        greyVCM.addColor("controlLineOff", new Color(0xEEEEEE) );
	        greyVCM.addColor("outline",        new Color(0x000000) );

	        wackyVCM.addColor("background", new Color(0x8A8ABA) );
	        wackyVCM.addColor("innerLabel", new Color(0xFFFFFF) );
	        wackyVCM.addColor("outerLabel", new Color(0xFFFFFF) );
	        wackyVCM.addColor("arithLogic", new Color(0x7F7FC7) );
	        wackyVCM.addColor("control",    new Color(0xC7BB00) );
	        wackyVCM.addColor("registers",  new Color(0xC77F7F) );
	        wackyVCM.addColor("memory",     new Color(0x7FC77F) );
	        wackyVCM.addColor("dataLines",  new Color(0x555555) );
	        wackyVCM.addColor("controlLineOn",  new Color(0xEEEE00) );
	        wackyVCM.addColor("controlLineOff", new Color(0xACACBA) );
	        wackyVCM.addColor("outline",        new Color(0xFFFFFF) );

	}

	void changeColor(ViewColorManager vcm)
	{
		zp.setBackgroundColor(vcm.getColor("background"));
		lv.setColorManager(vcm);
		dlv.setColorManager(vcm);
		cuv.setColorManager(vcm);
		sv.setColorManager(vcm);
		repaint();
	}

	public void update(Observable o, Object arg)
	{
		MIPSObservation co = (MIPSObservation) arg;

		if(co.getEvent().equals("PC_CHANGE") )
		{
			zp.updateDynamicViews();

			int pc  = cpu.getRegisters().getPC();
			if(pc > 0)
				instructionLabel.setText("        " + Disassembler.getDisassembly( cpu.getIR() , true));
			else
				instructionLabel.setText("");
		}
	}

	private JMenuBar setupMenu()
	{
		JButton zoomButton = null;
		JButton zoomInButton = null;
		JButton zoomOutButton = null;

		JButton bwButton = null;
		JButton colorButton = null;
		JButton wackyButton = null;

		try{
			URL url = CPUFrame.class.getResource("/ZoomOut16.gif");
			zoomOutButton = new JButton(new ImageIcon( url ));

			zoomButton = new JButton("1:1");

			url = CPUFrame.class.getResource("/ZoomIn16.gif");
			zoomInButton = new JButton(new ImageIcon(url));

			url = CPUFrame.class.getResource("/Color16.gif");
			colorButton = new JButton(new ImageIcon(url));

			url = CPUFrame.class.getResource("/BlackAndWhite16.gif");
			bwButton = new JButton(new ImageIcon(url));

			url = CPUFrame.class.getResource("/Wacky16.gif");
			wackyButton = new JButton(new ImageIcon(url));

		}catch(Exception e){ }


		JMenuBar jmb = new JMenuBar();
		jmb.add(zoomInButton);
		jmb.add(zoomButton);
		jmb.add(zoomOutButton);

		zoomPercentage = new JLabel("      " + (zp.getZoom()*100) + "%      ");
		jmb.add(zoomPercentage);

		jmb.add(colorButton);
		jmb.add(wackyButton);
		jmb.add(bwButton);

		instructionLabel = new JLabel("    ");
		jmb.add(instructionLabel);

		zoomButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent ae)
			{
				zp.resetZoom();
				zoomPercentage.setText("   " + ((int)(zp.getZoom()*100)) + "%   ");
				zp.invalidate();
				validate();
				repaint();
			}
		});

		zoomOutButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent ae)
			{
				zp.zoomOut();
				zoomPercentage.setText("      " + ((int)(zp.getZoom()*100)) + "%      ");
				zp.invalidate();
				validate();
				repaint();
			}
		});

		zoomInButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent ae)
			{
				zp.zoomIn();
				zoomPercentage.setText("      " + ((int)(zp.getZoom()*100))  + "%      ");
				zp.invalidate();
				validate();
				repaint();
			}
		});

		bwButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent ae)
			{
				changeColor(greyVCM);
			}
		});

		colorButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent ae)
			{
				changeColor(defaultVCM);
			}
		});

		wackyButton.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent ae)
			{
				changeColor(wackyVCM);
			}
		});


		return jmb;
	}

}