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
	
	public static World generateWorld(int sizeX, int sizeY, int antHillSize, int rockyProportion){
		Tile[][] grid = new Tile[sizeX][sizeY];
		// Fill grid with clear tiles
		for(int i = 0; i < sizeX; i++){
			Arrays.fill(grid[i], new ClearTile());
		}
		// Fill top and bottom rock boarders
		for(int i = 0; i < sizeX; i++){
			grid[i][0] = new RockTile();
			grid[i][sizeY-1] = new RockTile();
		}
		// Fill left and right rock boarders
		for(int i = 0; i < sizeY; i++){
			grid[0][i] = new RockTile();
			grid[sizeX-1][i] = new RockTile();
		}
		// create Ant hills
		boolean redAntHillPlaced = false;
		boolean blackAntHillPlaced = false;
		while(!redAntHillPlaced || !blackAntHillPlaced){
			ArrayList<Integer> tiles = new ArrayList<Integer>();
			int randX = State.randomInt(sizeX);
			int randY = State.randomInt(sizeY);
			boolean obstructed = false;
			if(grid[randX][randY].isRocky()){
				obstructed = true;
				if(Game.DEBUG){
					System.out.println("DEBUG | ANTHILL PLACEMENT FAILED");
				}
			}
			tiles.add((randY * sizeX) + randX);
			for(int i = 1; i < antHillSize & !obstructed; i++){
				int currentID = randY * sizeX + randX + i;
				for(int j = 0; j < 6 & !obstructed; j++){
					for(int k = 0; k < i & !obstructed; k++){
						int x = ((currentID % sizeX) + sizeX) % sizeX;
						int y = currentID / sizeX;
//						System.out.println(currentID + ", " + x + ", " + y);
						if(grid[x][y].isRocky()){
							obstructed = true;
							if(Game.DEBUG){
								System.out.println("DEBUG | ANTHILL PLACEMENT FAILED");
							}
						}
						tiles.add(currentID);
						currentID = getAhead((((j+2)%6)+6)%6, currentID, sizeX);
//						System.out.println((((j+2)%6)+6)%6);
					}
				}
			}
			if(!obstructed){
				if(!redAntHillPlaced){
					for(int i = 0; i < tiles.size(); i++){
						int x = ((tiles.get(i) % sizeX) + sizeX) % sizeX;
						int y = tiles.get(i) / sizeX;
						grid[x][y] = new AntHillTile(Colour.RED);
					}
					redAntHillPlaced = true;
				}else{
					if(!blackAntHillPlaced){
						for(int i = 0; i < tiles.size(); i++){
							int x = ((tiles.get(i) % sizeX) + sizeX) % sizeX;
							int y = tiles.get(i) / sizeX;
							grid[x][y] = new AntHillTile(Colour.BLACK);
						}
					}
					blackAntHillPlaced = true;
				}
			}
//			grid[randX][randY] = new RockTile();
		}
//		for each 1 <= k <= N:
//		    H = direction(4).scale(k)
//		    for each 0 <= i <= 6:
//		        for each 0 <= j < k:
//		            results.append(H)
//		            H = neighbor(H, i)
		return new World(grid);
	}
	
	public World(Tile[][] grid){
		this.grid = grid;
		sizeX = grid.length;
		sizeY = grid[0].length;
		antHills = new ArrayList<AntHillTile>();
		ants = new HashMap<Integer, Ant>();
		changes = new HashSet<Integer>();
	}
	
	public void populate(Player player1, Player player2){
		if(Game.DEBUG){
			System.out.println("DEBUG | Populating Anthills");
		}
		for(int i = 0; i < sizeX; i++){
			for(int j = 0; j < sizeY; j++){
				setChange((j*sizeX) + i); // Disable for fog of war lolol
				Tile tile = getTile(i,j);
				if(!tile.isRocky() && ((ClearTile)tile).isAnthill()){
					AntHillTile aHill = (AntHillTile) tile;
					antHills.add(aHill);
					if(aHill.getColour().equals(player1.getColour())){
						Ant ant = new Ant(player1);
						ant.setX(i);
						ant.setY(j);
						aHill.setAnt(ant);
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
		if(Game.DEBUG){
			System.out.println("DEBUG | " + ants.size() + " Ants Added to world");
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
		int x1 = ((tileID % sizeX) + sizeX) % sizeX;
		int y1 = tileID / sizeX;
		Tile currentTile = getTile(x1, y1);
		if((!currentTile.isRocky()) && ((ClearTile)currentTile).hasAnt()){
			int antCount = 0;
			for(int i = 0; i < Ant.POSSIBLE_DIRECTIONS; i++){
				int newTileID = getAhead(i, tileID, sizeX);
				int x2 = ((newTileID % sizeX) + sizeX) % sizeX;
				int y2 = newTileID / sizeX;
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
		grid[x][y] = t;
	}
	
	public Tile getTile(int x, int y){
		return grid[x][y];
	}

	public HashMap<Integer, Ant> getAnts() {
		return ants;
	}

	public ArrayList<AntHillTile> getAntHills() {
		return antHills;
	}
	
	public static int getAhead(int direction, int currentTile, int sizeX){
		int x = ((currentTile % sizeX) + sizeX) % sizeX;
		int y = currentTile / sizeX;
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
		return (y*sizeX) + x;
	}
	
	@Override
	protected Object clone(){
		World output = new World(grid);
		return output;
	}
}
