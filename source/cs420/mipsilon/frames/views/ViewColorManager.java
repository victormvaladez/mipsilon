package cs420.mipsilon.frames.views;

import java.awt.Color;
import java.util.HashMap;

public class ViewColorManager
{
	Color defaultColor = Color.BLACK;
	HashMap colors;
	public ViewColorManager(Color defaultColor)
	{
		this.defaultColor = defaultColor;
		init(); 
	}
	
	public ViewColorManager()
	{ init(); }
	
	private void init()
	{
		colors = new HashMap();
	}
	
	
	public void addColor(String name, Color color)
	{
		colors.put(name, color);
	}
	
	public Color getColor(String name)
	{
		Color color;
		if( (color = (Color) colors.get(name)) == null)
			return defaultColor;
		else	
			return color;
	}
	
	

}
