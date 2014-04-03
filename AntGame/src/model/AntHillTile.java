package model;

public class AntHillTile extends ClearTile {
	private Colour colour;
	
	public AntHillTile(Colour colour){
		this.colour = colour;
	}
	public Colour getColour(){
		return colour;
	}
	public boolean isAnthill(){
		return true;
	}
	
	@Override
	public Object clone() {
		return new AntHillTile(colour);
	}
}
