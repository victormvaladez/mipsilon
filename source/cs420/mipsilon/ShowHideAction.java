package cs420.mipsilon;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;

class ShowHideAction extends AbstractAction{
		
		JInternalFrame jif;
		
		ShowHideAction(JInternalFrame thejif)
		{
			jif = thejif;
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			try{
				if(jif.isVisible())
				{
					if(jif.isIcon())
						jif.setIcon(false);
					jif.setSelected(true);
				}
				else
					jif.show();
			}
			catch(java.beans.PropertyVetoException pvee) { }			
		}		
}