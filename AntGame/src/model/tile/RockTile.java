package model.tile;

/**
 * A tile implementation that represents a rock. Unlike a ClearTile, it cannot hold anything.
 * 
 * @author 108069
 * 
 */
public class RockTile implements Tile {
	@Override
	public boolean isRocky() {
		return true;
	}

	@Override
	public Object clone() {
		return new RockTile();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
