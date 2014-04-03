package graphics.screens;

import javax.swing.JPanel;

import model.Game;

/**
 * The final results of the tournament.
 */
public class TournamentResultsPanel extends JPanel implements Screen{
	
	private Game game;
	
	public TournamentResultsPanel(Game game) {
		this.game = game;
		// Show each player's score in a list.
		// Display the winner at the top aswell?
		// back to main menu button
		// re-match button?
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
