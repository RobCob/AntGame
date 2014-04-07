package modelTest;

import static org.junit.Assert.*;
import model.AntBrain;
import model.AntBrainReader;
import model.Colour;
import model.World;
import model.WorldReader;
import model.tile.AntHillTile;
import model.tile.ClearTile;
import model.tile.RockTile;
import model.tile.Tile;

import org.junit.Test;

public class WorldReaderTest {
/*
 * check all legal, boundary numbers and text and gaps in walls 
 */
	@Test
	public void validReadTest() {
		World readWorld = WorldReader.readWorld("assWorldTest.world");
		assertNotNull("Is an empty square world legal: ",readWorld);
	}
	@Test
	public void validReadTest2() {
		World readWorld = WorldReader.readWorld("assWorldTest.world");
		Tile[][] oracle = createGrid();
		for(int x = 0; x < readWorld.sizeX; x++){
			for(int y = 0; y < readWorld.sizeY; y++){
				assertTrue("Tile should match read tiles", readWorld.getTile(x, y).equals(oracle[x][y]));
			}
		}
	}	
	
	@Test
	public void validReadTest3() {
		World readWorld = WorldReader.readWorld("assWorldTest1.world");
		Tile[][] oracle = createGrid();	
		oracle[1][1] = new AntHillTile(Colour.RED);
		oracle[3][1] = new AntHillTile(Colour.BLACK);
		for(int x = 0; x < readWorld.sizeX; x++){
			for(int y = 0; y < readWorld.sizeY; y++){
				assertTrue("Testing 1 red and 1 black hill: ", readWorld.getTile(x, y).equals(oracle[x][y]));
			}
		}
	}
	@Test
	public void validReadTest12() {
		World readWorld = WorldReader.readWorld("assWorldTest10.world");
		assertNull("Testing 2 red and 1 black hill: ", readWorld);
	}
	@Test
	public void validReadTest4() {
		World readWorld = WorldReader.readWorld("assWorldTest2.world");
		assertNull("Test should fail on clear cell '-1': ", readWorld);
	}
	@Test
	public void validReadTest5() {
		World readWorld = WorldReader.readWorld("assWorldTest3.world");
		assertNull("Test should fail on clear cell '0': ", readWorld);
	}
	@Test
	public void validReadTest6() {
		World readWorld = WorldReader.readWorld("assWorldTest4.world");
		assertNull("Test should fail on clear cell '10': ", readWorld);
	}
	@Test
	public void validReadTest7() {
		World readWorld = WorldReader.readWorld("assWorldTest5.world");
		assertNull("Test should fail when a cell is a text char: ", readWorld);
	}
	@Test
	public void validReadTest8() {
		World readWorld = WorldReader.readWorld("assWorldTest6.world");
		assertNull("Test should fail when there is a gap in the east wall: ", readWorld);
	}
	@Test
	public void validReadTest9() {
		World readWorld = WorldReader.readWorld("assWorldTest6.world");
		assertNull("Test should fail when there is a gap in the north wall: ", readWorld);
	}
	@Test
	public void validReadTest10() {
		World readWorld = WorldReader.readWorld("assWorldTest6.world");
		assertNull("Test should fail when there is a gap in the south wall: ", readWorld);
	}
	@Test
	public void validReadTes11() {
		World readWorld = WorldReader.readWorld("assWorldTest6.world");
		assertNull("Test should fail when there is a gap in the east wall: ", readWorld);
	}
	public Tile[][] createGrid(){
		int sizeX = 5;
		int sizeY = 5;
		Tile[][] grid = new Tile[sizeX][sizeY];
		for(int x = 0; x < sizeX; x++){
			for(int y = 0; y < sizeY; y++){
				if(x == 0 || y == 0 || x == sizeX-1 || y == sizeY-1){
					grid[x][y] = new RockTile();
				}else{
					grid[x][y] = new ClearTile();
				}
			}
		}
		return grid;
	}
}
