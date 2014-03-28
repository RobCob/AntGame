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
		this.player2 = redPlayer;
		this.world = world;
		world.populate(player1, player2);
		scores = new HashMap<Colour, Integer>();
		this.winner = null;
		roundNumber = 0;
	}
	
	public void nextRound(){
		if(winner == null){
			if(roundNumber < MAX_ROUNDS){
				ArrayList<Ant> ants = world.getAnts();
				for(int i = 0; i < ants.size(); i++){
					ants.get(i).simulate(world);
				}
			}else{
				ArrayList<AntHillTile> antHills = world.getAntHills();
				for(int i = 0; i < antHills.size(); i++){
					Colour c = antHills.get(i).getColour();
					scores.put(c, scores.get(antHills.get(i).getColour()) + antHills.get(i).getFood());
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
		return scores.get(player);
	}
	
	public int getRoundNumber(){
		return roundNumber;
	}
}
