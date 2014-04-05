package modelTest;

import static org.junit.Assert.*;
import model.World;
import model.tile.ClearTile;
import model.tile.Tile;

import org.junit.Test;

public class WorldTest {

	@Test
	public void generateWorldTest() {
		fail("Not yet implemented");
	}

	@Test
	public void gridConstructorTest() {
		fail("Not yet implemented");
	}

	@Test
	public void populateTest() {
		World world = new World(null);
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
