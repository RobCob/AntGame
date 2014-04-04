package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A tournament class that manages the matches between a group of players and a
 * group of worlds so that there is two matches (swapping teams after the first)
 * on each world with every possible pair of players.
 * @author 108069
 *
 */
public class Tournament {
	
	private ArrayList<Match> matches;
	private ArrayList<Player> players;
	private ArrayList<World> worlds;
	private HashMap<String, Integer> scores;
	private int currentMatch;
	
	/**
	 * Creates a Tournament with the supplied players and worlds.
	 * @param players
	 * @param worlds
	 */
	public Tournament(ArrayList<Player> players, ArrayList<World> worlds){
		this();
		setPlayers(players);
		setWorlds(worlds);
		generateMatches();
	}
	
	/**
	 * Creates an empty tournament ready to be filled with players and worlds.
	 */
	public Tournament(){
		scores = new HashMap<String, Integer>();
		this.matches = new ArrayList<Match>();
		currentMatch = 0;
	}
	
	/**
	 * Sets the players of the tournament to the given ArrayList.
	 * @param players
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
		for(int i = 0; i < players.size(); i++){
			scores.put(players.get(i).getNickname(), 0);
		}
	}
	
	/**
	 * Sets the Worlds of the tournament to the given ArrayList.
	 * @param worlds
	 */
	public void setWorlds(ArrayList<World> worlds) {
		this.worlds = worlds;
	}
	
	/**
	 * Gets the current Match.
	 * @return
	 */
	public Match getCurrentMatch(){
		return matches.get(currentMatch);
	}
	
	/**
	 * Is the current match the last match?
	 * @return
	 */
	public boolean isLastMatch(){
		return currentMatch >= (matches.size()-1);
	}
	
	/**
	 * Calculates scores if the current match has ended, and then increases the current match counter.
	 */
	public void nextMatch(){
		if(matches.get(currentMatch).getRoundNumber() >= Match.MAX_ROUNDS){
			if(currentMatch < matches.size()){
				Match current = matches.get(currentMatch);
				Player winner = current.getWinner();
				if(winner == null){
					if(current.isDraw()){
						Player player1 = current.getPlayer1();
						Player player2 = current.getPlayer2();
						scores.put(player1.getNickname(), scores.get(player1) + 1);
						scores.put(player2.getNickname(), scores.get(player2) + 1);
					}
				}else{
					scores.put(winner.getNickname(), scores.get(winner.getNickname()) + 2);
				}
				currentMatch++;
			}
		}else{
			System.out.println("DEBUG | ROUND NOT FINISHED");
		}
	}
	
	/**
	 * Returns the HashMap of player name to player score.
	 * @return
	 */
	public HashMap<String, Integer> getScores(){
		return scores;
	}
	
	/**
	 * Gets an individual player's score.
	 * @param player
	 * @return
	 */
	public int getScore(Player player){
		return scores.get(player.getNickname());
	}
	
	/**
	 * Generates the matches for the tournament.
	 */
	public void generateMatches(){
		currentMatch = 0;
		for(int i = 0; i < worlds.size(); i++){
			for(int j = 1; j < players.size(); j++){
				for(int k = 0; k < j; k++){
					World world = worlds.get(i);
					Player player1 = players.get(j);
					Player player2 = players.get(k);
					matches.add(new Match((World) world.clone(), (Player) player1.clone(), (Player) player2.clone()));// TODO: YOU NEED TO CLONE WORLDS
					matches.add(new Match((World) world.clone(), (Player) player2.clone(), (Player) player1.clone()));// OR THE AFTICAN CHILDREN WILL CRY
				}
			}
		}
	}
	
	/**
	 * Sorts the players in ascending order.
	 * @return
	 */
	public ArrayList<Player> getOrderedPlayers(boolean ascending) {
		ArrayList<Player> sortedPlayers = new ArrayList<Player>();
		@SuppressWarnings("unchecked")
		ArrayList<Player> tempPlayers = (ArrayList<Player>) players.clone();
		int maxPlayerIndex = -1;
		boolean finished = false;
		
		while(!finished) {
			// Find the next best player.
			int maxScore = -1;
			for (int i = 0; i < tempPlayers.size(); i++) {
				if (getScore(tempPlayers.get(i)) >= maxScore) {
					maxScore = getScore(tempPlayers.get(i));
					maxPlayerIndex = i;
				}
			}
			
			// Place player in sorted.
			sortedPlayers.add(tempPlayers.get(maxPlayerIndex));
			
			// Remove player from temp.
			tempPlayers.remove(maxPlayerIndex);
			
			if (tempPlayers.size() <= 0) {
				finished = true;
			}
		}
		
		return sortedPlayers;
	}

}
