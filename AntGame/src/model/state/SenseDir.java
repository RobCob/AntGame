package model.state;

/**
 * A public class to represent the direction in which an ant is sensing.
 * 
 * @author 108069
 * 
 */
public enum SenseDir {
	HERE(0), AHEAD(0), LEFTAHEAD(-1), RIGHTAHEAD(1);

	private int value;

	/**
	 * A basic constructor to initialize enum values.
	 * 
	 * @param value
	 */
	private SenseDir(int value) {
		this.value = value;
	}

	/**
	 * Returns a value used to modify the direction in which the ant will sense.
	 * 
	 * @return -1, 0, or 1.
	 */
	public int getValue() {
		return value;
	}
}