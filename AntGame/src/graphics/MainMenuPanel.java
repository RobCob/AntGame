package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel{
	
	
	
	public MainMenuPanel(Window parent){
		final Window parentWindow = parent;
		JButton testButton = new JButton("TEST A CHANGE OF SCREEN");
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				parentWindow.switchScreen("Match");
			}
		});
		
		this.add(testButton);
	}
	
	
}