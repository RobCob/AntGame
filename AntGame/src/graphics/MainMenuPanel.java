package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel{
	private Game window;
	
	public MainMenuPanel(Game parent){
		this.window = parent;
		
		JButton testButton = new JButton("TEST A CHANGE OF SCREEN");
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				getWindow().switchScreen(Game.MATCH_SCREEN);
				//getWindow().startMatch();
			}
		});
		
		this.add(testButton);
	}
	
	public Game getWindow() {
		return this.window;
	}
	
	
}