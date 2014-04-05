package modelTest;

import static org.junit.Assert.*;
import model.tile.RockTile;

import org.junit.Test;

public class RockyTileTest {

	@Test
	public void isRockyTrueTest() {
		RockTile tile = new RockTile();
		assertTrue("Tile should be an instance of RockTile", tile.isRocky());
	}
}
