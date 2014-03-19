package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel{
	
	
	public MainMenuPanel(final Window parent){
		JButton testButton = new JButton("TEST A CHANGE OF SCREEN");
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.switchScreen("Match");
			}
		});
		
		this.add(testButton);
	}
}
