package cs420.mipsilon;

import javax.swing.*; 

import javax.swing.JWindow; 
import javax.swing.JTextArea; 
import javax.swing.JPanel;
import javax.swing.ImageIcon; 
import javax.swing.Icon; 

import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Font;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.net.URL;

class SplashWindow extends JWindow implements MouseListener{

	private Icon splashImage;
	private boolean ready;
	private JTextArea statusText;
	private JPanel textPanel;
	private boolean finished = false;
	private boolean error = false;
	
	
	SplashWindow(Frame owner, Icon icon){
		super(owner);
		finished = false;
		error = false;
		splashImage = icon;
		statusText = new JTextArea();
		statusText.getCaret().setVisible(false);
		statusText.setEditable(false);
		statusText.setFont(new Font("Monospaced", Font.BOLD, 14));
		
		JLabel imageLabel = new JLabel(splashImage);
		JScrollPane scrollPane = new JScrollPane(statusText,
		JScrollPane.VERTICAL_SCROLLBAR_NEVER ,
		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		imageLabel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 2));

		imageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

		scrollPane.setMaximumSize(new Dimension(300,83));
		scrollPane.setPreferredSize(new Dimension(300,83));		

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS ));
		getContentPane().add(imageLabel);
		getContentPane().add(scrollPane);
		
		statusText.setLineWrap(true);
		statusText.setWrapStyleWord(false);		
		
		addMouseListener(this);
	}
	
	public void postLine(String message){
		statusText.append("\n " + message );
		statusText.setCaretPosition(statusText.getText().length());		
	}
	
	public void setFinished(boolean finish)
	{
		finished = finish;		
	}
	
	public void setError()
	{
		error = true;		
	}
	
	public boolean getFinished()
	{
		return finished;		
	}	
		
	public void mouseClicked(MouseEvent e) {}
        
	public void mouseEntered(MouseEvent e) {}
          
	public void mouseExited(MouseEvent e) {}
          
	public void mousePressed(MouseEvent e)
	{
		if(getFinished())
			dispose();
		else if(error)
			System.exit(1);		
	}
          
	public void mouseReleased(MouseEvent e) {}
          

	
}