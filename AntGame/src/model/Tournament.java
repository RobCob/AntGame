package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Tournament {
	
	private ArrayList<Match> matches;
	private ArrayList<Player> players;
	private HashMap<Player, Integer> scores;
	private int currentMatch;
	
	public Tournament(ArrayList<Player> players){
		this.matches = new ArrayList<Match>();
		this.players = players;
		scores = new HashMap<Player, Integer>();
		currentMatch = 0;
	}
	
	public Match getCurrentMatch(){
		return matches.get(currentMatch);
	}
	
	public void playMatch(){
		if(matches.get(currentMatch).getRoundNumber() < Match.MAX_ROUNDS){
			matches.get(currentMatch).nextRound();
		}else{
			currentMatch++;
		}
	}

}
