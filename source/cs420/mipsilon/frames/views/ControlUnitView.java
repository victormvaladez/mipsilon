package cs420.mipsilon.frames.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import cs420.mipsilon.core.CPU;
import cs420.mipsilon.core.ALU;
import cs420.mipsilon.core.ControlUnit;
import cs420.mipsilon.core.ALUControl;

import javax.swing.JComponent;

import java.awt.*;
import java.awt.geom.*;

public class ControlUnitView extends AbstractView{	
	
	BasicStroke bs = new BasicStroke(2);
	BasicStroke line = new BasicStroke(1);
	BasicStroke dataLine = new BasicStroke(3);
	
	Font smallFont = new Font("myfont2", Font.PLAIN, 5);
	
	ControlUnit cu;
	ALU alu;
	ALUControl alucu;
	
	GeneralPath ANDPath;	
	GeneralPath muxPath;
		
	GeneralPath regDstPath;
	GeneralPath jumpPath;
	GeneralPath ALUSrcPath;
	GeneralPath memToRegPath;	
	GeneralPath zeroPath;
	GeneralPath branchPath;
	
	GeneralPath jrPath;
	GeneralPath regWritePath;
	GeneralPath linkPath;
	GeneralPath memReadPath;
	GeneralPath memWritePath;
	
	
	public ControlUnitView(ViewColorManager vcm, CPU cpu)
	{	
		super(vcm);
		setOpaque(false);
		
		
		cu = cpu.getControlUnit();
		alu = cpu.getALU();
		alucu = cpu.getALUControl();
		zoomFactor = 1;
		init();
	}
	
	private void init()
	{
		ANDPath = createANDPath();		
		muxPath = createMuxPath();
				
		regDstPath = createRegDstPath();
		jumpPath = createJumpPath();
		ALUSrcPath = createALUSrcPath();
		memToRegPath = createMemToRegPath();		
		zeroPath = createZeroPath();
		branchPath = createBranchPath();
		
		jrPath = createJrPath();      
		regWritePath = createRegWritePath();
		linkPath = createLinkPath();    
		memReadPath = createMemReadPath(); 
		memWritePath = createMemWritePath();
	}
	
	
	public void draw(Graphics g)
	{		
		
	}
	
	
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;	
		g2.scale(zoomFactor, zoomFactor);
			g2.setStroke(line);
			drawAND(g2);
			drawControlLines(g2);
			drawMux(328,208, g2,true ,cu.getALUSrc());
			drawMux(424,56, g2,true , (cu.getBranch() && alu.getZero()));
			drawMux(456,56, g2,false , cu.getJump() );
			drawMux(488,56, g2,true , alucu.getJr());
			drawMux(208,208, g2,true , cu.getRegDst());
			drawMux(484,208, g2,false , cu.getMemToReg());
		g2.scale(1/zoomFactor, 1/zoomFactor);
	}
	
	
	private void drawAND(Graphics2D g2)
	{
			
		if(cu.getBranch() && alu.getZero())
			g2.setColor(vcm.getColor("controlLineOn"));
		else
			g2.setColor(vcm.getColor("controlLineOff"));
		
		g2.draw(ANDPath);
				
	}	
			
	private void drawMux(int x, int y, Graphics2D g2, boolean topZeroOrientation, boolean value)
	{
		g2.translate(x, y);
		
			
		g2.setColor(vcm.getColor("control"));		
		g2.fill(muxPath); 
		
		g2.setColor(vcm.getColor("outline"));
		g2.fill(bs.createStrokedShape(muxPath));
		
		g2.setColor(vcm.getColor("dataLines"));
		if(topZeroOrientation && value)
			g2.fillRect(2, 10, 4, 4 );
		else if(!topZeroOrientation && !value)
			g2.fillRect(2, 10, 4, 4 );
		else
			g2.fillRect(2, 2, 4, 4 );	
		
		
		g2.translate(-x, -y);
	}
		
	private void drawControlLines(Graphics2D g2)
	{		
		drawControlLine(g2, ALUSrcPath, 242, 150, "AluSrc", cu.getALUSrc());
		drawControlLine(g2, regDstPath, 187, 110, "RegDst", cu.getRegDst());
		drawControlLine(g2, memToRegPath, 242, 126, "MemToReg", cu.getMemToReg());
		drawControlLine(g2, jumpPath, 242, 100, "Jump", cu.getJump());
		drawControlLine(g2, branchPath, 242, 114, "Branch", cu.getBranch());
		drawControlLine(g2, zeroPath, 0, 0, "", alu.getZero());
		
		drawControlLine(g2, jrPath, 354, 270, "Jr", alucu.getJr());
		drawControlLine(g2, regWritePath, 234, 158, "RegWrite", cu.getRegWrite());
		drawControlLine(g2, linkPath, 218, 166, "Link", cu.getLink());
		drawControlLine(g2, memReadPath, 242, 142, "MemRead", cu.getMemRead());
		drawControlLine(g2, memWritePath, 242, 134, "MemWrite", cu.getMemWrite());
		
	}
	
	private void drawControlLine(Graphics2D g2, GeneralPath p, int x, int y, String label, boolean value) //
	{		
		if(value)
			g2.setColor(vcm.getColor("controlLineOn")); 
		else
			g2.setColor(vcm.getColor("controlLineOff"));
		
		g2.draw(p);
		
		g2.setFont(smallFont);
		g2.drawString(label, x, y);
	}
		
	private GeneralPath createANDPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(400, 108);
		p.lineTo(408,108);
		p.quadTo(416,116, 408, 124);
		
		p.lineTo(400,124);		
		p.closePath();			
		
		p.moveTo(412, 116);
		p.lineTo(428,116);
		p.lineTo(428,72);
		
		return p;
	}
	
	private GeneralPath createMuxPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(0, 0);
		p.lineTo(8,0);
		p.lineTo(8,16);
		p.lineTo(0,16);
		p.closePath();
		
		return p;
	}
	
	private GeneralPath createRegDstPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(208, 112);
		p.lineTo(152,112);
		p.lineTo(152,248);
		p.lineTo(212,248);
		p.lineTo(212,224);
				
		return p;
	}
	
	private GeneralPath createALUSrcPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(241, 152);
		p.lineTo(332,152);
		p.lineTo(332,208);	
				
		return p;
	}
	
	private GeneralPath createBranchPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(241, 116);
		p.lineTo(384,116);
		p.lineTo(384,110);
		p.lineTo(400,110);	
				
		return p;
	}
	
	private GeneralPath createJumpPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(241, 104);
		p.lineTo(288,104);
		p.lineTo(288,44);
		p.lineTo(460,44);
		p.lineTo(460,56);
				
		return p;
	}
	
	private GeneralPath createMemToRegPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(241, 128);
		p.lineTo(488,128);
		p.lineTo(488,208);	
				
		return p;
	}
	
	private GeneralPath createZeroPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(384, 196);
		p.lineTo(392,196);
		p.lineTo(392,120);
		p.lineTo(400,120);
		
				
		return p;
	}	
	
	private GeneralPath createRegWritePath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(232,152);
		p.lineTo(232,160);
		p.lineTo(280,160);
		p.lineTo(280,170);	
				
		return p;
	}
	
	private GeneralPath createLinkPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(216,152);
		p.lineTo(216,168);
		p.lineTo(264,168);
		p.lineTo(264,170);
		
				
		return p;
	}
	
	private GeneralPath createMemReadPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(241,144);
		p.lineTo(368,144);
		p.lineTo(368,168);
		p.lineTo(432,168);
		p.lineTo(432,186);
		
				
		return p;
	}
	
	private GeneralPath createMemWritePath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(241,136);
		p.lineTo(376,136);
		p.lineTo(376,152);
		p.lineTo(448,152);
		p.lineTo(448,186);
		
				
		return p;
	}
		
	private GeneralPath createJrPath()
	{
		GeneralPath p = new GeneralPath();
		p.moveTo(353,272);
		p.lineTo(504,272);
		p.lineTo(504,88);
		p.lineTo(492,88);
		p.lineTo(492,72);		
				
		return p;
	}	
}