package model.state;

/**
 * A public class to represent the direction in which an ant is turning.
 * 
 * @author 108069
 * 
 */
public enum TurnDir {
	LEFT(-1), RIGHT(1);

	private int value;

	/**
	 * A basic constructor used to initialize enum values.
	 * 
	 * @param value
	 */
	TurnDir(int value) {
		this.value = value;
	}

	/**
	 * Returns a value used to determine the direction in which the ant will turn.
	 * 
	 * @return -1, or 1.
	 */
	public int getValue() {
		return value;
	}
}
