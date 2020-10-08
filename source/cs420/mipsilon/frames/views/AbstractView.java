package cs420.mipsilon.frames.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;

import javax.swing.JPanel;

public abstract class AbstractView extends JPanel {	
		
	boolean display = true;	
	double zoomFactor;
	
	ViewColorManager vcm;
		
	public AbstractView(ViewColorManager vcm)
	{ 
		super(); 
		this.vcm = vcm;
	}
		
	public void setDisplay(boolean disp)
	{
		display = disp;
	}
	
	public void setZoom(double zoom)
	{
		zoomFactor = zoom;
	}
	
	public void setColorManager(ViewColorManager vcm)
	{
		this.vcm = vcm;
	}
	
	public ViewColorManager getColorManager()
	{
		return vcm;
	}
	
	abstract public void draw(Graphics g);
}
	