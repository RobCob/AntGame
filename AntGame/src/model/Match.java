package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Match {
	public static final int MAX_ROUNDS = 300000;
	private Player player1;
	private Player player2;
	private World world;
	private Player winner;
	private HashMap<Colour, Integer> scores;
	private int roundNumber;
	
	/**
	 * This class simply holds the three components required for a match to be played.
	 * 2 antbrains (1 for each player) and world for the match to be played on.
	 * @param player1
	 * @param player2
	 * @param world
	 */
	public Match(World world, Player blackPlayer, Player redPlayer){
		this.player1 = blackPlayer;
		player1.setColour(Colour.BLACK);
		this.player2 = redPlayer;
		player2.setColour(Colour.RED);
		scores = new HashMap<Colour, Integer>();
		scores.put(player1.getColour(), 0);
		scores.put(player2.getColour(), 0);
		this.world = world;
		world.populate(player1, player2);
		this.winner = null;
		roundNumber = 0;
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
			if(winner == null){
				ArrayList<AntHillTile> antHills = world.getAntHills();
				for(int i = 0; i < antHills.size(); i++){
					Colour c = antHills.get(i).getColour();
					scores.put(c, scores.get(c) + antHills.get(i).getFood());
				}
				if(scores.get(player1.getColour()) > scores.get(player2.getColour())){
					winner = player1;
				}else{
					winner = player2;
				}
			}
		}
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
	
	public int getScore(Player player){
		return scores.get(player.getColour());
	}
	
	public int getRoundNumber(){
		return roundNumber;
	}
	
	public Player getWinner() {
		return winner;
	}
	
	public static void main(String[] args) {
		State.seed = 80008135;
		Player p1 = new Player("P1", BrainReader.readBrain("cleverbrain1.brain"));
		Player p2 = new Player("P2", BrainReader.readBrain("cleverbrain1.brain"));
		Match match = new Match(WorldReader.readWorld("sample3.world"), new Player("P1", BrainReader.readBrain("cleverbrain1.brain")), new Player("P2", BrainReader.readBrain("cleverbrain1.brain")));
		while(match.getWinner() == null){
			match.nextRound();
		}
		Player winner = match.getWinner();
		System.out.println(winner.getNickname() + " Wins!("+winner.getColour()+")");
		System.out.println("Score:\nBlack: " + match.getScore(p1) + "\tRed: " + match.getScore(p2));
	}
}
