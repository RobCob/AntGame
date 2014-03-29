package model;

import java.util.ArrayList;
import java.util.HashSet;

public class World {
	
	private Tile[][] grid;
	private HashSet<Integer> changes;
	private ArrayList<Ant> ants;
	private ArrayList<AntHillTile> antHills;
	public final int sizeX,sizeY;
	
	public World(int x, int y){
		this(new Tile[y][x]); //Generate world here
	}
	
	public World(Tile[][] grid){
		this.grid = grid;
		sizeX = grid[0].length;
		sizeY = grid.length;
		antHills = new ArrayList<AntHillTile>();
		ants = new ArrayList<Ant>();
		changes = new HashSet<Integer>();
	}
	
	public void populate(Player player1, Player player2){
		for(int i = 0; i < sizeX; i++){
			for(int j = 0; j < sizeY; j++){
				Tile tile = getTile(i,j);
				if(!tile.isRocky() && ((ClearTile)tile).isAnthill()){
					AntHillTile aHill = (AntHillTile) tile;
					antHills.add(aHill);
					if(aHill.getColour().equals(player1.getColour())){
						Ant ant = new Ant(player1);
						aHill.setAnt(ant);
						setChange(i*sizeX + j);
						ants.add(ant.getID(), ant);
					}else{
						Ant ant = new Ant(player2);
						aHill.setAnt(ant);
						ants.add(ant.getID(), ant);
					}
				}
			}
		}
	}
	
	public boolean setChange(int i){
		return changes.add(i);
	}
	
	public void resetChanges(){
		changes = new HashSet<Integer>();
	}
	
	public HashSet<Integer> getChanges(){
		return changes;
	}
	
	public void setTile(int x, int y, Tile t){
		grid[y][x] = t;
	}
	
	public Tile getTile(int x, int y){
		return grid[y][x];
	}

	public ArrayList<Ant> getAnts() {
		return ants;
	}

	public ArrayList<AntHillTile> getAntHills() {
		return antHills;
	}
	
}
