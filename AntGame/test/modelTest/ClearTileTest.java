package modelTest;

import static org.junit.Assert.*;
import model.Ant;
import model.Colour;
import model.Player;
import model.tile.ClearTile;

import org.junit.Test;

public class ClearTileTest {

	@Test
	public void isRockyTrueTest() {
		ClearTile tile = new ClearTile();
		assertFalse("Tile should not be an instance of RockTile", tile.isRocky());
	}

	@Test
	public void isAntHillTest() {
		ClearTile tile = new ClearTile();
		assertFalse("Tile should not be an instance of AntHillTile", tile.isAntHill());
	}

	@Test
	public void antManipulationTest() {
		ClearTile tile = new ClearTile();
		assertNull("Initial Ant should be null.", tile.getAnt());

		Ant ant = new Ant(new Player(null, null), 1);
		tile.setAnt(ant);
		assertEquals("Set Ant should be equal to supplied ant.", tile.getAnt(), ant);

		tile.removeAnt();
		assertNull("Removed Ant should be null.", tile.getAnt());
	}

	@Test
	public void markerManipulationTest() {
		ClearTile tile = new ClearTile();
		for (int i = 0; i < 6; i++) {
			assertFalse("Initial black marker should be clear.", tile.getMarker(Colour.BLACK, i));
			assertFalse("Initial red marker should be clear.", tile.getMarker(Colour.RED, i));
		}

		for (int i = 0; i < 6; i++) {
			tile.placeMarker(Colour.BLACK, i);
			assertTrue("Placed black marker should persist.", tile.getMarker(Colour.BLACK, i));
			tile.placeMarker(Colour.RED, i);
			assertTrue("Placed red marker should persist.", tile.getMarker(Colour.RED, i));
		}

		for (int i = 0; i < 6; i++) {
			tile.removeMarker(Colour.BLACK, i);
			assertFalse("removed black marker should be clear.", tile.getMarker(Colour.BLACK, i));
			tile.removeMarker(Colour.RED, i);
			assertFalse("removed red marker should be clear.", tile.getMarker(Colour.RED, i));
		}
	}

	@Test
	public void foodManipulationTest() {
		ClearTile tile = new ClearTile();
		int foodStoredInTile = tile.getFood();
		assertEquals("Set food should be equal to 0 for simple constructor.", 0, foodStoredInTile);

		tile.addFood();
		int expectedFoodStoredInTile = foodStoredInTile + 1;
		assertEquals("Increased food should affect stored value.", expectedFoodStoredInTile, tile.getFood());

		expectedFoodStoredInTile = 10;
		tile.setFood(expectedFoodStoredInTile);
		assertEquals("Set food should equal value supplied.", expectedFoodStoredInTile, tile.getFood());
		assertTrue("Should be possible to take food from a tile containing food", tile.takeFood());

		expectedFoodStoredInTile = 5;
		tile = new ClearTile(expectedFoodStoredInTile);
		foodStoredInTile = tile.getFood();
		assertEquals("Tile constructed with food value should be equal to given value.", expectedFoodStoredInTile, foodStoredInTile);
	}

	@Test
	public void cloneTest() {
		ClearTile tile = new ClearTile();
		Object clone = tile.clone();
		assertEquals("The clone of a tile should be equal to the original tile", clone, tile);
	}
}
