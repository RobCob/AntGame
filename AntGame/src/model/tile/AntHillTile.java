package model.tile;

import model.Colour;

/**
 * A Tile implementation that extends ClearTile functionality as it also represents an AntHill.
 * 
 * @author 108069
 * 
 */
public class AntHillTile extends ClearTile {
	private Colour colour;

	/**
	 * Constructs an Ant Hill with the given Colour.
	 * 
	 * @param colour
	 *            The Colour of the Ant Hill.
	 */
	public AntHillTile(Colour colour) {
		this.colour = colour;
	}

	/**
	 * Gets the Colour of the Ant Hill.
	 * 
	 * @return The Colour of the AntHillTile.
	 */
	public Colour getColour() {
		return colour;
	}

	@Override
	public boolean isAntHill() {
		return true;
	}

	@Override
	public Object clone() {
		return new AntHillTile(colour);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AntHillTile other = (AntHillTile) obj;
		if (colour != other.colour)
			return false;
		return true;
	}
}
