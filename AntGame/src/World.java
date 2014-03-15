public class World {
	private Tile[][] grid;
	private int sizeX, sizeY;
	
	public World(int x, int y){
		this.sizeX = x;
		this.sizeY = y;
		grid = new Tile[sizeX][sizeY];
	}
	
	public void setTile(int x, int y, Tile t){
		grid[x][y] = t;
	}
	
	public Tile getTile(int x, int y){
		return grid[x][y];
	}
	
}
