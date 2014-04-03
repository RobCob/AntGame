package model;

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
