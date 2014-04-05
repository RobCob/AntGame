package model;

/**
 * An enumerator used to define different Colours for teams in an Ant Game.
 * 
 * @author 108069
 * 
 */
public enum Colour {
	RED, BLACK;

	/**
	 * Gets the opposite colour to the one that called the method.
	 * 
	 * @return BLACK if RED called the method and RED if BLACK called the method.
	 */
	public Colour getEnemy() {
		if (this == RED) {
			return BLACK;
		} else {
			return RED;
		}
	}
}
