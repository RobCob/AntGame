package model;

/**
 * A tile implementation that represents a rock.
 * Unlike a ClearTile, it cannot hold anything.
 * @author 108069
 *
 */
public class RockTile implements Tile{
	@Override
	public boolean isRocky() {
		return true;		
	}
	
	@Override
	public Object clone() {
		return new RockTile();
	}
}
