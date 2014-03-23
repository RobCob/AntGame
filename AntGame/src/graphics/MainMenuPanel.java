package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Game;

public class MainMenuPanel extends JPanel{
	private Game game;
	
	public MainMenuPanel(Game game){
		this.game = game;
		
		final JTextField colsField = new JTextField("150",3);
		final JTextField rowsField = new JTextField("100",3);
		final JTextField sizeField = new JTextField("4", 3);
		final JTextField strokeField = new JTextField("1", 3);
		
		JButton testButton = new JButton("Start Match!");
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				getGame().createMatch(Integer.parseInt(colsField.getText()), 
									  Integer.parseInt(rowsField.getText()),
									  Integer.parseInt(sizeField.getText()),
									  Integer.parseInt(strokeField.getText()));
				getGame().switchScreen(Game.MATCH_SCREEN);
				getGame().startMatch();
			}
		});
		
		this.add(colsField);
		this.add(rowsField);
		this.add(sizeField);
		this.add(strokeField);
		this.add(testButton);
	}
	
	public Game getGame() {
		return this.game;
	}
	
	
}