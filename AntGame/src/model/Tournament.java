package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Tournament {
	
	private ArrayList<Match> matches;
	private ArrayList<Player> players;
	private HashMap<Player, Integer> scores;
	private int currentMatch;
	
	public Tournament(ArrayList<Player> players, ArrayList<World> worlds){
		this();
		setPlayers(players);
		setWorlds(worlds);
	}

	public Tournament(){
		scores = new HashMap<Player, Integer>();
		this.matches = new ArrayList<Match>();
		currentMatch = 0;
	}
	
	private void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void setWorlds(ArrayList<World> worlds) {
		for(int i = 0; i < worlds.size(); i++){
			for(int j = 1; j < players.size(); j++){
				matches.add(new Match((World) worlds.get(i).clone(), players.get(j-1), players.get(j)));// TODO: YOU NEED TO CLONE WORLDS
				matches.add(new Match((World) worlds.get(i).clone(), players.get(j), players.get(j-1)));// OR THE AFTICAN CHILDREN WILL CRY
			}
		}
	}
	
	public void nextMatch(){
		currentMatch++;
	}
	
	
	public Match getCurrentMatch(){
		return matches.get(currentMatch);
	}
	
	public void playMatch(){
		if(matches.get(currentMatch).getRoundNumber() < Match.MAX_ROUNDS){
			matches.get(currentMatch).nextRound();
		}else{
			if(currentMatch < matches.size()){
				Match current = matches.get(currentMatch);
				Player winner = current.getWinner();
				if(winner == null){
					if(current.isDraw()){
						Player player1 = current.getPlayer1();
						Player player2 = current.getPlayer2();
						scores.put(player1, scores.get(player1) + 1);
						scores.put(player2, scores.get(player2) + 1);
					}
				}else{
					scores.put(winner, scores.get(winner) + 2);
				}
				currentMatch++;
			}
		}
	}

}
