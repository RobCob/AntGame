package graphics.screens;

import javax.swing.JPanel;

import model.Game;

/**
 * Result of current match before continuing on to the next. 
 */
public class TournamentMatchResultsPanel extends JPanel{
	
	private Game game;
	
	public TournamentMatchResultsPanel(Game game) {
		this.game = game;
	}
}
