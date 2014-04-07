package modelTest;

import static org.junit.Assert.*;
import model.Colour;
import model.Player;
import model.World;
import model.tile.AntHillTile;
import model.tile.ClearTile;
import model.tile.RockTile;
import model.tile.Tile;

import org.junit.Test;

public class WorldTest {

	@Test
	public void generateWorldTest() {
		int sizeX = 150;
		int sizeY = 150;
		int antHillSize = 7;
		int rockCount = 14;
		int foodPileCount = 11;
		World world = World.generateWorld(sizeX, sizeY, antHillSize, rockCount, foodPileCount);
		int foundRocks = 0;
		for(int x = 0; x < sizeX; x++){
			for(int y = 0; y < sizeY; y++){
				Tile tile = world.getTile(x, y);
				if(x == 0 || y == 0 || x == sizeX-1 || y == sizeY-1){
					assertTrue(tile instanceof RockTile);
				}else{
					int tileID = (y*sizeX + x);
					if(tile.isRocky()){
						for(int i = 0; i < 6; i++){
							int neighbourID = World.getAhead(i, tileID, sizeX);
							int tempX = ((neighbourID % world.sizeX) + world.sizeX) % world.sizeX;
							int tempY = neighbourID / world.sizeX;
							assertTrue(world.getTile(tempX, tempY).equals(new ClearTile()));
						}
						foundRocks++;
					}else{
						fail("Test not completed, needs more functionality");
					}
				}
			}
		}
		assertTrue(foundRocks == rockCount);
	}

	@Test
	public void gridConstructorTest() {
	}
	
	public Tile[][] createGrid(){
		int sizeX = 4;
		int sizeY = 4;
		Tile[][] grid = new Tile[sizeX][sizeY];
		for(int x = 0; x < sizeX; x++){
			for(int y = 0; y < sizeY; y++){
				if(x == 0 || y == 0 || x == sizeX-1 || y == sizeY-1){
					grid[x][y] = new RockTile();
				}else{
					if (x > y){
						grid[x][y] = new AntHillTile(Colour.RED);
					}else{
						if (x < y){
							grid[x][y] = new AntHillTile(Colour.RED);
						}else{
							grid[x][y] = new ClearTile();
						}
					}
				}
			}
		}
		return grid;
	}
	
	@Test
	public void populateTest() {
		World world = new World(createGrid());
		Player p1 = new Player(null, null);
		Player p2 = new Player(null, null);
		p1.setColour(Colour.BLACK);
		p2.setColour(Colour.RED);
		world.populate(p1, p2);
		for (int x = 0; x < world.sizeX; x++) {
			for (int y = 0; y < world.sizeY; y++) {
				Tile currentTile = world.getTile(x, y);
				if (!currentTile.isRocky()) {
					if (((ClearTile) currentTile).isAntHill()) {
						assertTrue("Every AntHillTile should contain an ant.", ((ClearTile) currentTile).hasAnt());
					} else {
						assertFalse("Every non-AntHillTile should contain no ant.", ((ClearTile) currentTile).hasAnt());
					}
				}
			}
		}
	}
}
