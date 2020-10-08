package cs420.mipsilon.frames;

import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.geom.Line2D;
import java.util.Vector;
import java.util.Iterator;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Component;
import javax.swing.OverlayLayout;


import cs420.mipsilon.frames.views.AbstractView;

class ZoomPanel extends JPanel
{

	double zoomFactor = 1;	
	Vector dynamicViews;
	Vector staticViews;
	Dimension panelDim = new Dimension(560,340);
	Dimension zoomDim = new Dimension(panelDim);
		
	Color bgColor;
			
	ZoomPanel(Color background)
	{
		super();
		setLayout(new OverlayLayout(this));
		bgColor = background;		
		updateZoom();
		setOpaque(true);
		setBackground(Color.WHITE);		
		
		dynamicViews = new Vector();
		staticViews = new Vector();		
	}
		
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(zoomFactor, zoomFactor);
			g2.setColor(bgColor);
			g2.fillRect(10,10, panelDim.width-20, panelDim.height-20);
			g2.setColor(Color.BLACK);
			g2.drawRect(10,10, panelDim.width-20, panelDim.height-20);
		paintCPUComponents(g, staticViews);
		paintCPUComponents(g, dynamicViews);		
		g2.scale(1/zoomFactor, 1/zoomFactor);
		
	}
	
	public void setBackgroundColor(Color bg)
	{
		bgColor = bg;
	}
	
	
	private void paintCPUComponents(Graphics g, Vector v)
	{
		Iterator it;
		it = v.iterator();
		AbstractView av;
		
		while(it.hasNext())
		{			
			av = (AbstractView) it.next();
			av.draw(g);
		}
	}
	
	public void updateDynamicViews()
	{		
		Iterator it;
		it = dynamicViews.iterator();
		AbstractView jv;
		
		while(it.hasNext())
		{			
			jv = (AbstractView) it.next();					
			jv.repaint();
		}		
	}	
	
	public void setZoom(double zoom)
	{
		zoomFactor = zoom;
		updateZoom();
		
	}
	public double getZoom()
	{
		return zoomFactor;
	}
	
	public void addStaticView(AbstractView av)
	{
		staticViews.add(av);
	}
	
	public void removeStaticView(AbstractView av)
	{
		
		staticViews.remove(av);
	}
	
	public void addDynamicView(AbstractView av)
	{
		av.setPreferredSize(getPreferredSize());
		av.setLocation(0,0);
		add(av);
		
		dynamicViews.add(av);
	}
	
	public void removeDynamicView(AbstractView av)
	{
		dynamicViews.remove(av);
	}		
	
	public void resetZoom()
	{
		zoomFactor = 1.00000000000;
		updateZoom();
	}
	
	
	public void zoomIn()
	{
		zoomFactor += 0.20000000000;
		updateZoom();
	}
	
	public void zoomOut()
	{
		zoomFactor -= 0.20000000000;
		updateZoom();
	}	
	
	private void updateZoom()
	{	
		Component [] comps = getComponents();
		zoomDim.setSize( zoomFactor*panelDim.width, zoomFactor*panelDim.height);
		
		for(int i = 0; i < comps.length; i++)
		{
			comps[i].setSize(zoomDim);
			comps[i].setLocation(0,0);
			((AbstractView)comps[i]).setZoom(zoomFactor);
		}
		
		
		
		setPreferredSize(zoomDim);
	}
	
	
}