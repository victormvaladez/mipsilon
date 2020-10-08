package cs420.mipsilon.frames.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.awt.*;
import java.awt.geom.*;

public class DataLinesView extends AbstractView{	
		
	BasicStroke dataLine = new BasicStroke(2);
	
	GeneralPath intructionFetchPath;
	GeneralPath decodePath;
	GeneralPath executePath;	
	
	public DataLinesView(ViewColorManager vcm)
	{
		super(vcm);		
		init();
	}
	
	private void init()
	{
		intructionFetchPath = createIntructionFetchPath();
		decodePath = createDecodePath();
		executePath = createExecutePath();
		
	}
	
	
	public void draw(Graphics g)
	{			
		
		if(display)
		{			
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(dataLine);
			drawDataLine(g2, decodePath);
			drawDataLine(g2, intructionFetchPath);
			drawDataLine(g2, executePath);
			
			drawDot(320, 212, g2);
			drawDot(160, 128, g2);
			drawDot(136, 208, g2);
			
			drawDot(400,200, g2);
			drawDot(392,200, g2);
			drawDot(240,256, g2);
			drawDot( 56,184, g2);
			drawDot(304,72 , g2);
			drawDot(176,80, g2);
			drawDot(224,48 , g2);
			
			drawDot(160,220, g2);
			drawDot(160,180, g2);
			drawDot(160,196, g2);
			drawDot(184,196, g2);
			
			drawDot(312,220, g2);
					
		}
	}
		
	private void drawDataLine(Graphics2D g2, GeneralPath p)
	{		
		g2.setColor(vcm.getColor("dataLines"));
		g2.fill(dataLine.createStrokedShape(p));
	}
	
	private void drawDot(int x, int y, Graphics2D g2)
	{
		g2.fillOval(x-2, y-2, 4,4);
	}
		
	private GeneralPath createIntructionFetchPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(48,184);
		p.lineTo(64,184);
		
		p.moveTo(56,184);
		p.lineTo(56,72);
		p.lineTo(80,72);
		
		p.moveTo(40,160);
		p.lineTo(40,32);
		p.lineTo(508,32);
		p.lineTo(508,64);
		p.lineTo(492,64);
		
		p.moveTo(112,80);
		p.lineTo(304,80);
		p.lineTo(304,72);
		p.lineTo(352,72);
		
		p.moveTo(304,72);
		p.lineTo(304,56);
		p.lineTo(400,56);
		p.lineTo(400,60);
		p.lineTo(424,60);
		
		
		p.moveTo(384,88);
		p.lineTo(400,88);
		p.lineTo(400,68);
		p.lineTo(424,68);
		
		p.moveTo(432,64);
		p.lineTo(440,64);
		p.lineTo(440,68);
		p.lineTo(456,68);
		
		p.moveTo(464,64);
		p.lineTo(472,64);
		p.lineTo(472,60);
		p.lineTo(488,60);
		
		p.moveTo( 136,208);
		p.lineTo(136,48);
		p.lineTo(448,48);
		p.lineTo(448,60);
		p.lineTo(456,60);
		
		p.moveTo(176,80);
		p.lineTo(176,64);
		p.lineTo(224,64);
		p.lineTo(224,48);
		
		
		return p;
		
	}
	
	private GeneralPath createDecodePath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(112,208);
		p.lineTo(160,208);
		
		p.moveTo(160,128);
		p.lineTo(208,128);
		
		p.moveTo(160,128);
		p.lineTo(160,256);
		
		p.moveTo(160,180);
		p.lineTo(240,180);
		
		p.moveTo(160,196);
		p.lineTo(240,196);
		
		p.moveTo(184,196);
		p.lineTo(184,210);
		p.lineTo(208,210);
		
		p.moveTo(160,220);
		p.lineTo(208,220);
		
		p.moveTo(216,216);
		p.lineTo(240,216);		
		
		p.moveTo(160,256);
		p.lineTo(312,256);
		p.lineTo(312,104);
		p.lineTo(352,104);
		
		p.moveTo(240,256);
		p.lineTo(240,280);
		p.lineTo(336,280);		
		
		
		return p;
	}
		
	private GeneralPath createExecutePath()
	{
		GeneralPath p = new GeneralPath();
		
		p.moveTo(304,184);
		p.lineTo(352,184);
		
		p.moveTo(304,212);
		p.lineTo(328,212);
		
		p.moveTo(336,216);
		p.lineTo(352,216);
		
		p.moveTo(320,184);
		p.lineTo(352,184);
		
		p.moveTo(384,200);
		p.lineTo(408,200);
		
		p.moveTo(320,212);
		p.lineTo(320,240);
		p.lineTo(408,240);
		
		p.moveTo(400,200);
		p.lineTo(400,144);
		p.lineTo(480,144);
		p.lineTo(480,68);
		p.lineTo(488,68);
		
		p.moveTo(392,200);
		p.lineTo(392,264);
		p.lineTo(480,264);
		p.lineTo(480,218);
		p.lineTo(488,218);
		
		p.moveTo(472,212);
		p.lineTo(488,212);
		
		p.moveTo(312,220);
		p.lineTo(328,220);
		
		p.moveTo(488,216);
		p.lineTo(496,216);
		p.lineTo(496,296);
		p.lineTo(224,296);
		p.lineTo(224,228);
		p.lineTo(240,228);
		
		
		
		return p;
	}
	
	
		
}