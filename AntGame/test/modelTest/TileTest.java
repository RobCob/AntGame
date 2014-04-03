package modelTest;

import static org.junit.Assert.*;
import model.*;

import org.junit.Test;

public class TileTest {

	@Test
	public void isRockyTrueTest() {
		Tile tile = new RockTile();
		assertTrue(tile.isRocky());
	}
	
	@Test
	public void isRockyFalseTest() {
		Tile tile = new ClearTile();
		assertFalse(tile.isRocky());
	}

}
