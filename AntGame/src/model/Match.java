package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to represent a competition between two players on a single world.
 * @author 108069
 *
 */
public class Match {
	public static final int MAX_ROUNDS = 300000;
	private Player player1;
	private Player player2;
	private World world;
	private Player winner;
	private boolean isDraw;
	private HashMap<Colour, Integer[]> scores;
	private int roundNumber;
	
	/**
	 * This class simply holds the three components required for a match to be played.
	 * 2 AntBrain objects (1 for each Player) and a World object for the Match to be played on.
	 * @param player1
	 * @param player2
	 * @param world
	 */
	public Match(World world, Player blackPlayer, Player redPlayer){
		this();
		setPlayer1(blackPlayer);
		setPlayer2(redPlayer);
		setWorld(world);
	}
	
	/**
	 * Creates an empty match ready for players and a world to be added.
	 */
	public Match(){
		player1 = null;
		player2 = null;
		scores = new HashMap<Colour, Integer[]>();
		this.winner = null;
		isDraw = false;
		roundNumber = 0;
	}
	
	/**
	 * Sets the first Player for the match and overwrites their Colour with Colour.BLACK
	 * @param player
	 */
	public void setPlayer1(Player player){
		this.player1 = player;
		player1.setColour(Colour.BLACK);
		Integer[] score = {0, 0, 0};
		scores.put(player1.getColour(), score);
	}
	
	/**
	 * Sets the second Player for the match and overwrites their Colour with Colour.RED
	 * @param player
	 */
	public void setPlayer2(Player player){
		this.player2 = player;
		player2.setColour(Colour.RED);
		Integer[] score = {0, 0, 0};
		scores.put(player2.getColour(), score);
	}
	
	/**
	 * Sets the World for the Match to be played on.
	 * @param world
	 */
	public void setWorld(World world){
		this.world = world;
		Ant.ID_COUNTER = 0;
		world.populate(player1, player2);
	}
	
	/**
	 * Progresses the match by a single round, this consists of:
	 * Instructing each ant (in ascending ID order) to simulate, either resting or executing a state from their brain.
	 * If it is the last round, calculate match scores and set the winner.
	 */
	public void nextRound(){
		if(roundNumber < MAX_ROUNDS){
			HashMap<Integer, Ant> ants = world.getAnts();
			int maxAnts = world.getAntHills().size();
			for(int i = 0; i < maxAnts; i++){
				int antID = i;
				Ant currentAnt = ants.get(antID);
				if(currentAnt != null){
					currentAnt.simulate(world);
				}
			}
			roundNumber++;
		}
		if(roundNumber == MAX_ROUNDS){
			if(winner == null && !isDraw){
				updateScores();
				if(scores.get(player1.getColour())[0] > scores.get(player2.getColour())[0]){
					winner = player1;
				}else{
					if(scores.get(player2.getColour())[0] > scores.get(player1.getColour())[0]){
						winner = player2;
					}else{
						winner = null;
						isDraw = true;
					}
				}
			}
			roundNumber++;
		}
	}
	
	/**
	 * Recalculates the scores for the current state of the match
	 */
	public void updateScores(){
		ArrayList<AntHillTile> antHills = world.getAntHills();
		int redScore = 0;
		int redAntHillCount = 0;
		int blackScore = 0;
		for(int i = 0; i < antHills.size(); i++){
			Colour c = antHills.get(i).getColour();
			if(c.equals(Colour.RED)){
				redAntHillCount++;
				redScore += antHills.get(i).getFood();
			}
			if(c.equals(Colour.BLACK)){
				blackScore += antHills.get(i).getFood();
			}
		}
		HashMap<Integer, Ant> ants = world.getAnts();
		Integer[] antIDs = ants.keySet().toArray(new Integer[0]);
		int redAntCount = 0;
		for(int i = 0; i < antIDs.length; i++){
			if(ants.get(antIDs[i]).getColour().equals(Colour.RED)){
				redAntCount++;
			}
		}
		int blackAntCount = ants.size() - redAntCount;
		int redDeaths = (redAntHillCount - redAntCount);
		int blackDeaths = ((antHills.size()-redAntHillCount) - (antIDs.length - redAntCount));
		scores.get(Colour.RED)[0] = redScore;
		scores.get(Colour.RED)[1] = redAntCount;
		scores.get(Colour.RED)[2] = redDeaths;
		scores.get(Colour.BLACK)[0] = blackScore;
		scores.get(Colour.BLACK)[1] = blackAntCount;
		scores.get(Colour.BLACK)[2] = blackDeaths;
	}
	
	/**
	 * Returns the world on which the match will be played.
	 * @return
	 */
	public World getWorld(){
		return world;
	}
	
	/**
	 * Gets the first Player for the match (Colour.BLACK team)
	 * @return
	 */
	public Player getPlayer1() {
		return player1;
	}
	
	/**
	 * Gets the second Player for the match (Colour.RED team)
	 * @return
	 */
	public Player getPlayer2() {
		return player2;
	}
	
	/**
	 * Gets the score for the player at the specified index.
	 * @param player The player whose score to check.
	 * @param i The index of the required score.
	 * @return The requested score.
	 */
	private int getScore(Player player, int i){
		return scores.get(player.getColour())[i];
	}
	
	/**
	 * The total number of food items on the player's AntHillTiles.
	 * @param player The player whose food score is wanted.
	 * @return The food score.
	 */
	public int getFoodScore(Player player){
		return getScore(player, 0);
	}
	
	/**
	 * The total number of living Ants on the Player's team.
	 * @param player The player whose alive Ant count is wanted.
	 * @return The number of living ants.
	 */
	public int getAliveCount(Player player){
		return getScore(player, 1);
	}
	
	/**
	 * The total number of dead Ants on the Player's team.
	 * @param player The player whose dead Ant count is wanted.
	 * @return The number of dead ants.
	 */
	public int getDeadCount(Player player){
		return getScore(player, 2);
	}
	
	/**
	 * Gets the number of rounds that have passed.
	 * @return The current round number.
	 */
	public int getRoundNumber(){
		return roundNumber;
	}
	
	/**
	 * Gets the winner of the match, the player with the greater food score.
	 * @return Will return null until the last round has passed. If the match is a draw (both player's food scores are the same), winner will remain equal to null.
	 */
	public Player getWinner() {
		return winner;
	}
	
	/**
	 * Is the match a draw?
	 * @return True if the last round has passed and the two player's food scores are even, false if otherwise. 
	 */
	public boolean isDraw(){
		return isDraw;
	}
	
	public static void main(String[] args) {
		Game.seed = 80008135;
		Player p1 = new Player("P1", AntBrainReader.readBrain("cleverbrain1.brain"));
		Player p2 = new Player("P2", AntBrainReader.readBrain("cleverbrain1.brain"));
		Match match = new Match(WorldReader.readWorld("sample3.world"), p1, p2);
		while(match.getWinner() == null){
			match.nextRound();
		}
		Player winner = match.getWinner();
		System.out.println(winner.getNickname() + " Wins!("+winner.getColour()+")");
		System.out.println("Score:\nBlack: " + match.getScore(p1, 0) + "\tRed: " + match.getScore(p2, 0));
	}
}
