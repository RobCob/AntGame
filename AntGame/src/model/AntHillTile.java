package model;

/**
 * A Tile implementation that extends ClearTile functionality as it also represents an AntHill.
 * @author 108069
 *
 */
public class AntHillTile extends ClearTile {
	private Colour colour;
	
	/**
	 * Constructs an Ant Hill with the given Colour.
	 * @param colour The Colour of the Ant Hill.
	 */
	public AntHillTile(Colour colour){
		this.colour = colour;
	}
	
	/**
	 * Gets the Colour of the Ant Hill.
	 * @return The Colour of the AntHillTile.
	 */
	public Colour getColour(){
		return colour;
	}
	
	@Override
	public boolean isAnthill(){
		return true;
	}
	
	@Override
	public Object clone() {
		return new AntHillTile(colour);
	}
}
