package cs420.mipsilon.frames;

import cs420.mipsilon.core.Memory;
import cs420.mipsilon.core.MIPSObservation;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Component;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.DefaultListModel;

import java.util.Observer;
import java.util.Observable;

import java.util.Iterator;

import cs420.mipsilon.Util;

public class TextFrame extends AbstractFrame implements Observer {
			
	private JList memList;
	private DefaultListModel listModel;
	
	private Memory text;
	private Iterator iter;
	private Integer currentPC = new Integer(0);
	
    public TextFrame(String frameName, Memory textSegment) {
        super(frameName);
        
        text = textSegment;
        
        setSize(225,300);
                        
        getContentPane().add(new JScrollPane(memList = new JList(listModel = new DefaultListModel())));
        
	MemListRendererx renderer = new MemListRendererx(text);
	memList.setCellRenderer(renderer);
        
	refresh();
    }
	
	
	public void update(Observable o, Object arg)
	{
		MIPSObservation co = (MIPSObservation) arg;
				
		if(co.getEvent().equals("PC_CHANGE") )
		{			
			currentPC = (Integer) co.getData();
			memList.setSelectedValue(currentPC, true);
		}
		else if(co.getEvent().equals("MEM_CLEAR") )
		{
			if( o == ((Observable)text))
				refresh();
		}
		else if(co.getEvent().equals("CPU_RESET"))
		{
			refresh();
		}		
	}


    public void refresh()
    {
    		listModel.clear();
		iter = text.iterator();
		for(int i = 0; i < text.size(); i++)
		{
			
			if( (i % 4) == 0)
			{
				Integer memAddress = (Integer)iter.next();
				int val = text.read( memAddress.intValue() );				
				listModel.addElement(memAddress);
			}
			else
				iter.next();
		}
		// select CurrentPC	
		memList.setSelectedValue(currentPC, true);
    }
    
	class MemListRendererx extends JLabel
                       implements ListCellRenderer {
		Color selectedColor = new Color(204,204,255 );
		
		private Memory segment;
				
		MemListRendererx(Memory memSegment) 
		{
			segment = memSegment;
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
			setFont(new Font("Monospaced", Font.BOLD, 14));
			setBackground(Color.WHITE);
			setForeground(Color.BLACK);
	    	}
		
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			Integer memAddress = (Integer)value;
			StringBuffer memBuff = new StringBuffer();
			
			int val = segment.read( memAddress.intValue() );   
			
			memBuff.append("[0x" + Util.padZero(Integer.toHexString(memAddress.intValue()),8) + "]  ");
			memBuff.append("0x" + Util.padZero(Integer.toHexString(val),8)  +  "\n");

			setText(memBuff.toString());
			
			if(isSelected)
				setBackground(selectedColor);
			else
				setBackground(Color.WHITE);
						
		        return this;
		}		
		
	}
    
    
    
    
    
    
    
}