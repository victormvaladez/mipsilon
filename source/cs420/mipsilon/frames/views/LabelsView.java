package cs420.mipsilon.frames.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;


import java.awt.*;
import java.awt.geom.*;

public class LabelsView extends AbstractView{	
		
	BasicStroke dataLine = new BasicStroke(2);
	
	Font font = new Font("myfont", Font.PLAIN, 8);
	Font smallFont = new Font("myfont2", Font.PLAIN, 5);
	
	Font smallBoldFont = new Font("myfont2", Font.BOLD, 5);
	
	
	Color innerColor;
	Color outerColor;
	
	public LabelsView(ViewColorManager vcm)
	{
		super(vcm);				
	}
	
	public void draw(Graphics g)
	{			
		Graphics2D g2 = (Graphics2D) g;
		if(display)
		{
			drawComponentLabels(g2);
			drawDataLineLabels(g2);
			//drawControlLineLabels(g2);
			
		}
	}
	
	private void drawComponentLabels(Graphics2D g2)	
	{
		Font oldFont = g2.getFont();
		g2.setFont(smallBoldFont);
		g2.setColor(vcm.getColor("innerLabel"));
		drawCenteredText(g2, 272,200, "Registers");
		drawCenteredText(g2, 82,208, "Instruction");
		drawCenteredText(g2, 82,214, "Memory");
		drawCenteredText(g2, 96,80, "add");
		drawCenteredText(g2, 224,112, "Control");
		drawCenteredText(g2, 224,118, "Unit");
		drawCenteredText(g2, 448,224, "Data");
		drawCenteredText(g2, 448,230, "Memory");		
		drawCenteredText(g2, 368,88, "add");
		drawCenteredText(g2, 368,200, "ALU");
		drawCenteredText(g2, 40,176, "PC");
		
		
		drawCenteredText(g2, 264,178, "L");
		drawCenteredText(g2, 280,178, "W");
		
		drawCenteredText(g2, 432,194, "R");
		drawCenteredText(g2, 448,194, "W");
		
		drawCenteredText(g2, 272,256, "Sign Ext.");
		
		
		g2.setFont(font);
		g2.setColor(vcm.getColor("outerLabel"));
		g2.drawString("4", 72,96);
		
		g2.setFont(oldFont);
		
	}
	
	private void drawDataLineLabels(Graphics2D g2)
	{
		Font oldFont = g2.getFont();
		g2.setFont(smallFont);
		g2.setColor(vcm.getColor("outerLabel"));
		
		g2.drawString("Instruction [31-26]",163,125);
		g2.drawString("Instruction [31-26]",163,177);
		g2.drawString("Instruction [20-16]",163,193);
		g2.drawString("Instruction [15-11]",163,217);
		g2.drawString("Instruction [15-0]" ,163,253);
			
		g2.drawString("Instruction [5-0]" ,243,277);
		g2.drawString("Instruction [25-0]" ,137,45);
		
		g2.setFont(oldFont);
	}
		
	private void drawCenteredText(Graphics2D g2, int xCenter, int yCenter, String text)	
	{
		Font currentFont = g2.getFont();
		Rectangle2D rect = currentFont.getStringBounds(text, g2.getFontRenderContext());
		
		g2.drawString(text, ((float)(xCenter - (rect.getWidth() / 2))), ((float)(yCenter + (rect.getHeight() / 2)))-2 );
	}
	
		
}