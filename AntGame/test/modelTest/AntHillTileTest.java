package modelTest;

import static org.junit.Assert.*;
import model.Colour;
import model.tile.AntHillTile;

import org.junit.Test;

public class AntHillTileTest {

	@Test
	public void isAntHillTest() {
		AntHillTile tile = new AntHillTile(Colour.RED);
		assertTrue("Tile should be an instance of AntHillTile", tile.isAntHill());
	}

	@Test
	public void constructorColourTest() {
		AntHillTile redTile = new AntHillTile(Colour.RED);
		assertEquals("The red colour used to construct an AntHillTile should be persistant", Colour.RED, redTile.getColour());

		AntHillTile blackTile = new AntHillTile(Colour.BLACK);
		assertEquals("The black colour used to construct an AntHillTile should be persistant", Colour.BLACK, blackTile.getColour());
	}

	@Test
	public void cloneTest() {
		AntHillTile tile = new AntHillTile(Colour.RED);
		Object clone = tile.clone();
		assertEquals("The clone of a tile should be equal to the original tile", clone, tile);
		assertNotSame("The clone of a tile should be equal to the original tile", clone, tile);
	}

}
