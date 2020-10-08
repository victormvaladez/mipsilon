package cs420.mipsilon.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;

import java.util.Observable;
import java.util.Observer;

import cs420.mipsilon.core.Registers;
import cs420.mipsilon.core.MIPSObservation;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class RegisterFrame extends AbstractFrame implements Observer{
		
	Registers registers;
	JLabel [] registerLabels = new JLabel[32];
	JLabel pcLabel;
	//Color dkGreen = Color.GREEN.darker();
	
	int previousReg;
	
    public RegisterFrame(String frameName, Registers regs) {
        super(frameName);
        registers = regs;                
       
        init();
    }	
	
	// Update the display of the registers.
	public void refresh()
	{		        	
        	pcLabel.setText("PC: 0x" + cs420.mipsilon.Util.padZero(Integer.toHexString(registers.getPC()), 8) );
		
		for(int i=0; i < 32; i++)
		{			
			registerLabels[i].setForeground(Color.BLACK);
	        	registerLabels[i].setText(getFormattedReg(i));
	        }
	}
	
	public void init()
	{
		JPanel regPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        JPanel regPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        
	        pcLabel = new JLabel();
	        regPanel2.add(pcLabel);
	                
	        regPanel.setBackground(Color.WHITE);
	        getContentPane().setLayout(new BorderLayout());
		pcLabel.setText("PC: 0x" + cs420.mipsilon.Util.padZero(Integer.toHexString(registers.getPC()), 8) );
		
		for(int i=0; i < 32; i++)
		{
			registerLabels[i] = new JLabel();
        		registerLabels[i].setBackground(Color.WHITE);        		
			registerLabels[i].setFont(new Font("Monospaced", Font.BOLD, 14));
			registerLabels[i].setForeground(Color.BLACK);
	        	registerLabels[i].setText(getFormattedReg(i));
	        	regPanel.add(registerLabels[i]);
	        }
	        
	        getContentPane().add(regPanel, BorderLayout.CENTER);
        	getContentPane().add(regPanel2, BorderLayout.SOUTH);
        	
        	setSize(300,300);	        
		setLocation(210, 210);	
        }
	
	private String getFormattedReg(int reg)
	{
		return "$" + cs420.mipsilon.Util.padZero(new Integer(reg).toString(),2 ) + ":0x" + cs420.mipsilon.Util.padZero(Integer.toHexString(registers.getReg(reg)), 8) + " ";
	}
	
	public void update(Observable o, Object arg)
	{
		MIPSObservation co = (MIPSObservation) arg;
				
		if(co.getEvent().equals("REG_WRITE"))
		{		
			Integer reg = (Integer) co.getData();
			int regChanged = reg.intValue();			
			registerLabels[previousReg].setForeground(Color.BLACK);
			registerLabels[regChanged].setText(getFormattedReg(regChanged));			
			registerLabels[regChanged].setForeground(Color.BLUE);
			previousReg = reg.intValue();
		}
		else if(co.getEvent().equals("PC_CHANGE") )
		{
			pcLabel.setText("PC: 0x" + cs420.mipsilon.Util.padZero(Integer.toHexString(registers.getPC()), 8) );
			registerLabels[previousReg].setForeground(Color.BLACK);
		}
		else if(co.getEvent().equals("CPU_RESET") )
		{
			refresh();
		}
		
		
	}
	
}