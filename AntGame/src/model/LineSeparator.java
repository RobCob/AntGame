package model;

/**
 * An empty Tile implementation used in the WorldReader for validity parsing.
 * It is used to indicate line separation.
 * @author 109195
 *
 */
public class LineSeparator implements Tile{
	
	@Override
	public boolean isRocky() {
		return false;
	}

	@Override
	public Object clone() {
		return new LineSeparator();
	}
}
