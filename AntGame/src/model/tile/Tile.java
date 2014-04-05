package model.tile;

/**
 * A Tile interface used to collect different tile types.
 * 
 * @author 108069
 * 
 */
public interface Tile {
	/**
	 * Is the tile an instance of RockTile
	 * 
	 * @return True or False.
	 */
	public boolean isRocky();

	/**
	 * Used to create an exact copy of the given Tile object.
	 * 
	 * @return A Tile with the same data as the current tile.
	 */
	public Object clone();

}
