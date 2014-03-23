package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Game;

public class MainMenuPanel extends JPanel{
	private Game game;
	
	public MainMenuPanel(Game game){
		this.game = game;
		
		JButton testButton = new JButton("Start Match!");
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				getGame().switchScreen(Game.MATCH_SCREEN);
				getGame().startMatch();
			}
		});
		
		this.add(testButton);
	}
	
	public Game getGame() {
		return this.game;
	}
	
	
}