package cs420.mipsilon.frames;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import java.awt.Color;

public abstract class AbstractFrame extends JInternalFrame 
{
			
    public AbstractFrame(String frameName) 
    {
        super(frameName, 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
     setBackground(Color.WHITE);
     
     }
     
     // ddddddddddd
     /*
     addInternalFrameListener( new InternalFrameAdapter() {
			public void internalFrameActivated(InternalFrameEvent e) 
			{
				setTitle( "(" + getWidth() + ", " + getHeight() + ") : (" + getX() + ", " + getY() + ")" );
			}
		});
     
     
     
    }
   */
    
}