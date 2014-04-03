package model;

import java.util.ArrayList;
import java.util.HashMap;

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
	 * 2 antbrains (1 for each player) and world for the match to be played on.
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
	
	public Match(){
		player1 = null;
		player2 = null;
		scores = new HashMap<Colour, Integer[]>();
		this.winner = null;
		isDraw = false;
		roundNumber = 0;
	}
	
	public void setPlayer1(Player player){
		this.player1 = player;
		player1.setColour(Colour.BLACK);
		Integer[] score = {0, 0};
		scores.put(player1.getColour(), score);
	}
	
	public void setPlayer2(Player player){
		this.player2 = player;
		player2.setColour(Colour.RED);
		Integer[] score = {0, 0};
		scores.put(player2.getColour(), score);
	}
	
	public void setWorld(World world){
		this.world = world;
		world.populate(player1, player2);
	}
	
	public void nextRound(){
		if(roundNumber < MAX_ROUNDS){
			HashMap<Integer, Ant> ants = world.getAnts();
			Integer[] antIDArray = ants.keySet().toArray(new Integer[0]);
			for(int i = 0; i < antIDArray.length; i++){
				int antID = antIDArray[i];
				Ant currentAnt = ants.get(antID);
				if(currentAnt != null){
					currentAnt.simulate(world);
				}
			}
			roundNumber++;
		}else{
			updateScores();
			if(winner == null && !isDraw){
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
		}
	}
	
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
		int redDeaths = (redAntHillCount - redAntCount);
		int blackDeaths = ((antHills.size()-redAntHillCount) - (antIDs.length - redAntCount));
		scores.get(Colour.RED)[0] = redScore;
		scores.get(Colour.RED)[1] = blackDeaths;
		scores.get(Colour.BLACK)[0] = blackScore;
		scores.get(Colour.BLACK)[1] = redDeaths;
	}
	
	public World getWorld(){
		return world;
	}
	
	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
	public int getScore(Player player, int i){
		return scores.get(player.getColour())[i];
	}
	
	public int getFoodScore(Player player){
		return getScore(player, 0);
	}
	
	public int getAntDeaths(Player player){
		return getScore(player, 1);
	}
	
	public int getRoundNumber(){
		return roundNumber;
	}
	
	public Player getWinner() {
		return winner;
	}
	
	public boolean isDraw(){
		return isDraw;
	}
	
	public static void main(String[] args) {
		State.seed = 80008135;
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
