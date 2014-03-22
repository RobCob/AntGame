package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel{
	private Window window;
	
	public MainMenuPanel(Window parent){
		this.window = parent;
		
		JButton testButton = new JButton("TEST A CHANGE OF SCREEN");
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				getWindow().switchScreen("Match");
				//getWindow().startMatch();
			}
		});
		
		this.add(testButton);
	}
	
	public Window getWindow() {
		return this.window;
	}
	
	
}