package model;
public class World {
	
	private Tile[][] grid;
	private int x,y;
	
	public World(int x, int y){
		this.x = x;
		this.y = y;
		grid = new Tile[y][x]; //in 2-D arrays y coordinate comes first
	}
	
	public World(Tile[][] grid){
		this.grid = grid;
	}
	
	public void setTile(int x, int y, Tile t){
		grid[y][x] = t;
	}
	
	public Tile getTile(int x, int y){
		return grid[y][x];
	}
	
}
