package cs420.mipsilon.frames.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.awt.*;
import java.awt.geom.*;

public class StaticView extends AbstractView{	
		
	BasicStroke bs = new BasicStroke(2);
		
	GeneralPath memoryPath;
	GeneralPath regsPath;
	GeneralPath ALUsPath;	
	GeneralPath controlUnitsPath;
	GeneralPath staticControlLines;
	
	GeneralPath grid;	
		
	public StaticView(ViewColorManager vcm)
	{	
		super(vcm);		
		init();
	}
	
	private void init()
	{
		memoryPath = createMemoryPath();
		regsPath = createRegsPath();
		ALUsPath = createALUsPath();
		controlUnitsPath = createControlUnitsPath();
		staticControlLines = createStaticControlLines();
		grid = createGrid();
	}
	
	
	public void draw(Graphics g)
	{			
		
		if(display)
		{	
			Graphics2D g2 = (Graphics2D) g;
			drawLines(g2, vcm.getColor("control"), staticControlLines);
			drawComponent(g2, vcm.getColor("memory"), memoryPath);
			drawComponent(g2, vcm.getColor("registers"), regsPath);
			drawComponent(g2, vcm.getColor("arithLogic"), ALUsPath);
			drawComponent(g2, vcm.getColor("control"), controlUnitsPath);			
			//drawGrid(g2);			
		}
	}
	
	private void drawComponent(Graphics2D g2, Color color,  GeneralPath path)
	{
		g2.setColor(color);
		g2.fill(path); 
		g2.setColor(vcm.getColor("outline"));
		g2.fill(bs.createStrokedShape(path));
	}
	
	private void drawLines(Graphics2D g2, Color color,  GeneralPath path)
	{
		g2.setColor(color);		
		g2.fill(bs.createStrokedShape(path));
	}	
	
	private GeneralPath createMemoryPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(408, 188);
		p.lineTo(472,188);
		p.lineTo(472,252);
		p.lineTo(408,252);
		p.closePath();
		p.moveTo(64, 172);
		p.lineTo(124,172);
		p.lineTo(124,236);
		p.lineTo(64,236);
		p.closePath();
		
		return p;
		
	}
	
	private GeneralPath createControlUnitsPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(208, 88);
		p.lineTo(240,88);
		p.lineTo(240,152);
		p.lineTo(208,152);
		p.closePath();
		
		p.moveTo(336,248);
		p.lineTo(352,248);
		p.lineTo(352,288);
		p.lineTo(336,288);
		p.closePath();
		
		return p;		
	}
	
	private GeneralPath createStaticControlLines()
	{
		GeneralPath p = new GeneralPath();
		
		p.moveTo(240,120);
		p.lineTo(344,120);
		p.lineTo(344,248);
		
		p.moveTo(352,260);
		p.lineTo(368,260);
		p.lineTo(368,216);
		
		return p;
	}
	
	private GeneralPath createRegsPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(240, 172);
		p.lineTo(304,172);
		p.lineTo(304,236);
		p.lineTo(240,236);		
		p.closePath();
		
		p.moveTo(32, 160);
		p.lineTo(48,160);
		p.lineTo(48,192);
		p.lineTo(32,192);		
		p.closePath();
		
		return p;
	}
	
	private GeneralPath createALUsPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(352, 64);
		p.lineTo(384,80);
		p.lineTo(384,96);
		p.lineTo(352,112);
		p.lineTo(352,92);
		p.lineTo(360,88);
		p.lineTo(352,84);
		p.closePath();
				
		p.moveTo(80, 56);
		p.lineTo(112,72);
		p.lineTo(112,84);
		p.lineTo(80,104);
		p.lineTo(80,84);
		p.lineTo(88,80);
		p.lineTo(80,76);
		p.closePath();
		
		p.moveTo(352, 176);
		p.lineTo(384,192);
		p.lineTo(384,208);
		p.lineTo(352,224);
		p.lineTo(352,204);
		p.lineTo(360,200);
		p.lineTo(352,196);
		p.closePath();
		
		p.moveTo(192,56);
		p.lineTo(208,56);
		p.lineTo(208,72);
		p.lineTo(192,72);		
		p.closePath();
		
		p.moveTo(320,96);
		p.lineTo(336,96);
		p.lineTo(336,112);
		p.lineTo(320,112);	
		p.closePath();
		
		p.moveTo(256,248);
		p.lineTo(288,248);
		p.lineTo(288,264);
		p.lineTo(256,264);	
		p.closePath();
		
		return p;
	}
		
	private GeneralPath createGrid()
	{
		GeneralPath p = new GeneralPath();		
				
		for(int i = 0; i < 560; i += 16)
		{
			p.moveTo(i,0);
			p.lineTo(i,340);
			for(int j = 0; j < 340; j += 16)
			{
				p.moveTo(0,j);
				p.lineTo(560,j);
			}
		}
		
		return p;
	}
	
	private void drawGrid(Graphics2D g2)
	{
		Font oldFont = g2.getFont();
		g2.setFont(new Font("myfont", Font.PLAIN, 8));
		
		g2.setColor(Color.CYAN);
		
		g2.setStroke(new BasicStroke(1));
		g2.draw(grid);
		
		g2.setColor(Color.BLACK);
		for(int i = 0; i < 560; i += 16)
		{			
			g2.drawString(Integer.toString(i), i+1, 9);
		}
		for(int j = 16; j < 340; j += 16)
		{
			g2.drawString(Integer.toString(j),2, j+9);
		}
		g2.setFont(oldFont);
	}	
}