package model;

public class Match {
	
	private AntBrain player1;
	private AntBrain player2;
	private World world;
	
	/**
	 * This class simply holds the three components required for a match to be played.
	 * 2 antbrains (1 for each player) and world for the match to be played on.
	 * @param player1
	 * @param player2
	 * @param world
	 */
	public Match(AntBrain player1, AntBrain player2, World world){
		this.player1 = player1;
		this.player2 = player2;
		this.world = world;
	}
	
	
}
