package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class World {
	
	private Tile[][] grid;
	private HashSet<Integer> changes;
	private HashMap<Integer, Ant> ants;
	private ArrayList<AntHillTile> antHills;
	public final int sizeX,sizeY;
	
	public static World generateWorld(int x, int y, int antHillSize, int rockyProportion){
		Tile[][] grid = new Tile[x][y];
		// Fill grid with clear tiles
		for(int i = 0; i < x; i++){
			Arrays.fill(grid[i], new ClearTile());
		}
		// Fill top and bottom rock boarders
		for(int i = 0; i < x; i++){
			grid[i][0] = new RockTile();
			grid[i][y] = new RockTile();
		}
		// Fill left and right rock boarders
		for(int i = 0; i < y; i++){
			grid[0][i] = new RockTile();
			grid[x][i] = new RockTile();
		}
		// create Ant hills
		boolean redAntHill = false;
		boolean blackAntHill = false;
		while(redAntHill && blackAntHill){
			int randX = State.randomInt(x);
			int randY = State.randomInt(y);
			for(int i = 0; i < antHillSize; i++){
				for(int j = 0; j < antHillSize; j++){
					
				}
			}
		}
		return new World(grid);
	}
	
	public World(Tile[][] grid){
		this.grid = grid;
		sizeX = grid[0].length;
		sizeY = grid.length;
		antHills = new ArrayList<AntHillTile>();
		ants = new HashMap<Integer, Ant>();
		changes = new HashSet<Integer>();
	}
	
	public void populate(Player player1, Player player2){
		for(int i = 0; i < sizeX; i++){
			for(int j = 0; j < sizeY; j++){
				changes.add((i*sizeX) + j); // Disable for fog of war lolol
				Tile tile = getTile(i,j);
				if(!tile.isRocky() && ((ClearTile)tile).isAnthill()){
					AntHillTile aHill = (AntHillTile) tile;
					antHills.add(aHill);
					if(aHill.getColour().equals(player1.getColour())){
						Ant ant = new Ant(player1);
						ant.setX(i);
						ant.setY(j);
						aHill.setAnt(ant);
						setChange(i*sizeX + j);
						ants.put(ant.getID(), ant);
					}else{
						Ant ant = new Ant(player2);
						ant.setX(i);
						ant.setY(j);
						aHill.setAnt(ant);
						ants.put(ant.getID(), ant);
					}
				}
			}
		}
	}
	
	public HashSet<Integer> getChanges(){
		return changes;
	}
	
	public boolean setChange(int tileID){
		return changes.add(tileID);
	}
	
	public void resetChanges(){
		changes = new HashSet<Integer>();
	}
	
	public void triggerUpdates(int tileID) {
		update(tileID);
		for(int i = 0; i < Ant.POSSIBLE_DIRECTIONS; i++){
			update(getAhead(i, tileID, sizeX));
		}
	}
	
	private void update(int tileID){
//		changes.add(tileID); // Enable for fog of war lolol
		int x1 = tileID / sizeX;
		int y1 = ((tileID % sizeX) + sizeX) % sizeX;
		Tile currentTile = getTile(x1, y1);
		if((!currentTile.isRocky()) && ((ClearTile)currentTile).hasAnt()){
			int antCount = 0;
			for(int i = 0; i < Ant.POSSIBLE_DIRECTIONS; i++){
				int newTileID = getAhead(i, tileID, sizeX);
				int x2 = newTileID / sizeX;
				int y2 = ((newTileID % sizeX) + sizeX) % sizeX;
				Tile newTile = getTile(x2, y2);
				if((!newTile.isRocky()) && ((ClearTile)newTile).hasAnt() && (!((ClearTile)newTile).getAnt().getColour().equals(((ClearTile)currentTile).getAnt().getColour()))){
					antCount++;
				}
			}
			if(antCount >= 5){
				if(Game.DEBUG){
					System.out.println("DEBUG | ANT DEATH: " + ((ClearTile)currentTile).getAnt().getColour() + " AntDied " + ((ClearTile)currentTile).getAnt().getID());
				}
				ants.remove(((ClearTile)currentTile).getAnt().getID());
				setChange(tileID);
				((ClearTile)currentTile).removeAnt();
				((ClearTile)currentTile).setFood(3);
			}
		}
	}
	
	public void setTile(int x, int y, Tile t){
		grid[y][x] = t;
	}
	
	public Tile getTile(int x, int y){
		return grid[y][x];
	}

	public HashMap<Integer, Ant> getAnts() {
		return ants;
	}

	public ArrayList<AntHillTile> getAntHills() {
		return antHills;
	}
	
	public int getAhead(int direction, int currentTile, int worldSizeX){
		int x = currentTile / sizeX;
		int y = ((currentTile % sizeX) + sizeX) % sizeX;
		switch(direction){
			case 0:
				x = x+1;
				break;
			case 1:
				if(y%2 == 0){
					y = y+1;
				}else{
					x = x+1;
					y = y+1;
				}
				break;
			case 2:
				if(y%2 == 0){
					x = x-1;
					y = y+1;
				}else{
					y = y+1;
				}
				break;
			case 3:
				x = x-1;
				break;
			case 4:
				if(y%2 == 0){
					x = x-1;
					y = y-1;
				}else{
					y = y-1;
				}
				break;
			case 5:
				if(y%2 == 0){
					y = y-1;
				}else{
					x = x+1;
					y = y-1;
				}
				break;
			default:
				x = 0;
				y = 0;
				break;
		}
		return (x*worldSizeX) + y;
	}
	
	@Override
	protected Object clone(){
		World output = new World(grid);
		return output;
	}
}
