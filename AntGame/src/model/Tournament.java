package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Tournament {
	
	private ArrayList<Match> matches;
	private ArrayList<Player> players;
	private ArrayList<World> worlds;
	private HashMap<String, Integer> scores;
	private int currentMatch;
	
	public Tournament(ArrayList<Player> players, ArrayList<World> worlds){
		this();
		setPlayers(players);
		setWorlds(worlds);
	}

	public Tournament(){
		scores = new HashMap<String, Integer>();
		this.matches = new ArrayList<Match>();
		currentMatch = 0;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
		for(int i = 0; i < players.size(); i++){
			scores.put(players.get(i).getNickname(), 0);
		}
	}

	public void setWorlds(ArrayList<World> worlds) {
		this.worlds = worlds;
		for(int i = 0; i < worlds.size(); i++){
			for(int j = 1; j < players.size(); j++){
				World world = worlds.get(i);
				Player player1 = players.get(j);
				Player player2 = players.get(j-1);
				matches.add(new Match((World) world.clone(), (Player) player1.clone(), (Player) player2.clone()));// TODO: YOU NEED TO CLONE WORLDS
				matches.add(new Match((World) world.clone(), (Player) player2.clone(), (Player) player1.clone()));// OR THE AFTICAN CHILDREN WILL CRY
			}
		}
	}
	
	
	public Match getCurrentMatch(){
		return matches.get(currentMatch);
	}
	
	public boolean isLastMatch(){
		return currentMatch >= (matches.size()-1);
	}
	
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
	
	public HashMap<String, Integer> getScores(){
		return scores;
	}
	public int getScore(Player player){
		return scores.get(player.getNickname());
	}
	
	public void reset(){
		for(int i = 0; i < worlds.size(); i++){
			for(int j = 1; j < players.size(); j++){
				World world = worlds.get(i);
				Player player1 = players.get(j);
				Player player2 = players.get(j-1);
				matches.add(new Match((World) world.clone(), (Player) player1.clone(), (Player) player2.clone()));// TODO: YOU NEED TO CLONE WORLDS
				matches.add(new Match((World) world.clone(), (Player) player2.clone(), (Player) player1.clone()));// OR THE AFTICAN CHILDREN WILL CRY
			}
		}
	}

}
