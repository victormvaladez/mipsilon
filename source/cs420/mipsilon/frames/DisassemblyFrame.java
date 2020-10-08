package cs420.mipsilon.frames;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;

import java.util.Iterator;

import cs420.mipsilon.core.Memory;
import cs420.mipsilon.core.Instruction;
import cs420.mipsilon.core.Disassembler;
import cs420.mipsilon.Util;
import cs420.mipsilon.core.MIPSObservation;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.DefaultListModel;

import java.util.Observer;
import java.util.Observable;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;


public class DisassemblyFrame extends AbstractFrame implements Observer{
	
	
	private JTextArea textArea;	
	private Memory text;
	private Iterator iter;
	private JList disList;
	private DefaultListModel listModel;
	
	private Integer currentPC = new Integer(0);
	
    public DisassemblyFrame(String frameName, Memory textMemory) {
        super(frameName);
	
	text = textMemory;        
        
        setSize(400,300);        
        
        getContentPane().add(new JScrollPane(disList = new JList(listModel = new DefaultListModel())));        
	DisassemblyListRenderer renderer = new DisassemblyListRenderer(text);
	disList.setCellRenderer(renderer);
        
        
        refresh();
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
	disList.setSelectedValue(currentPC, true);    	
    }
    
	public void update(Observable o, Object arg)
	{
		MIPSObservation co = (MIPSObservation) arg;
				
		if(co.getEvent().equals("PC_CHANGE") )
		{			
			currentPC = (Integer) co.getData();
			disList.setSelectedValue(currentPC, true);
		}
		else if(co.getEvent().equals("MEM_CLEAR") )
		{
			if(  o == ((Observable) text))
				refresh();			
		}
		else if(co.getEvent().equals("CPU_RESET"))
		{
			refresh();
		}
		
	}

	class DisassemblyListRenderer extends JLabel
                       implements ListCellRenderer {
		Color selectedColor = new Color(204,204,255 );
		
		Memory segment;
		Instruction inst = new Instruction();
		
		public DisassemblyListRenderer(Memory memSegment) 
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
			StringBuffer disBuff = new StringBuffer();			
			
			int val = segment.read( memAddress.intValue() );   
			inst.setRawInstruction(val);
			disBuff.append("[0x" + Util.padZero(Integer.toHexString(memAddress.intValue()),8) + "]  ");
			disBuff.append(Disassembler.getDisassembly(inst, true) +  "\n");

			setText(disBuff.toString());
			
			if(isSelected)
				setBackground(selectedColor);
			else
				setBackground(Color.WHITE);			
			
		        return this;
		}
		
}





}