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

import cs420.mipsilon.core.Memory;
import cs420.mipsilon.Util;

public class DataFrame extends AbstractFrame implements Observer {
			
	private JList memList;
	private DefaultListModel listModel;
	
	private Memory data;
	private Iterator iter;
	
	private int currentMemSize;
	
	Integer lastWritten, lastRead;
		
    public DataFrame(String frameName, Memory dataSegment) {
        super(frameName);
        
        data = dataSegment;
        
        setSize(225,300);
                        
        getContentPane().add(new JScrollPane(memList = new JList(listModel = new DefaultListModel())));
        
	MemListRenderery renderer = new MemListRenderery(data);
	memList.setCellRenderer(renderer);
        
        currentMemSize = data.size();
        
	refresh();
    }

	public void update(Observable o, Object arg) 
	{
		
    		MIPSObservation co = (MIPSObservation) arg;
				
		if(co.getEvent().equals("MEM_CLEAR") )
		{
			if( o == ((Observable)data))
				refresh();
		}
		else if(co.getEvent().equals("CPU_RESET"))
		{
			refresh();
		}
		else if(co.getEvent().equals("PC_CHANGE"))
		{
			lastWritten = null;
			lastRead = null;
			memList.setSelectedValue(new Integer(0), false);
		}
		else if(co.getEvent().equals("MEM_SIZE_CHANGE"))
		{
			refresh();
		}
		else if(co.getEvent().equals("MEM_READ"))			
		{			
			lastRead = (Integer) co.getData();			
			memList.setSelectedValue(lastRead, true);
		}
		else if(co.getEvent().equals("MEM_WRITE"))
		{
			lastWritten = (Integer) co.getData();
			memList.setSelectedValue(lastWritten, true);
		}
	}

    private void refresh()
    {
   	listModel.clear();
	iter = data.iterator();
	for(int i = 0; i < data.size(); i++)
	{
		if( (i % 4) == 0)
		{
			Integer memAddress = (Integer)iter.next();
			int val = data.read( memAddress.intValue() );				
			listModel.addElement(memAddress);
		}
		else
			iter.next();
	}
    }	
	
	class MemListRenderery extends JLabel
                       implements ListCellRenderer {
		Color selectedColor = new Color(204,204,255 );
		
		private Memory segment;
		
		
		MemListRenderery(Memory memSegment) 
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
						
			if(lastWritten != null && lastWritten.equals(memAddress))
				setForeground(Color.BLUE);
			else if(lastRead != null && lastRead.equals(memAddress))
				setForeground(Color.RED);
			else
				setForeground(Color.BLACK);
			
		        return this;
		}		
		
	}


}