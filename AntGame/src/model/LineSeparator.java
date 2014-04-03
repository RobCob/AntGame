package model;

public class LineSeparator implements Tile{
	
	//empty class merely used as a Token to indicate line separation
	//Used in parsing the ant world
	
	@Override
	public boolean isRocky() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object clone() {
		return new LineSeparator();
	}
}
