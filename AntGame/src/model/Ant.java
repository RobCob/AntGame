package model;

import controller.Game;
import model.state.TurnDir;

/**
 * This class is a representation of an ant. It is used to simulate a ant character in a World and it's actions are determined by the AntBrain that it holds.
 * 
 * @author 108069
 * 
 */
public class Ant {
	public static final int POSSIBLE_DIRECTIONS = 6;
	private int id, state, x, y, direction, resting;
	private boolean hasFood;
	private AntBrain brain;
	private Colour colour;

	/**
	 * Constructs an ant with the Colour and brain of the given player.
	 * 
	 * @param player
	 *            The 'owner' of the ant.
	 */
	public Ant(Player player, int id) {
		this.id = id;
		this.brain = player.getBrain();
		this.state = 0;
		this.x = 0;
		this.y = 0;
		this.direction = 0;
		this.resting = 0;
		this.colour = player.getColour();
		this.hasFood = false;
	}

	/**
	 * Returns the ID of the ant.
	 * 
	 * @return The Unique ID of the ant.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns the AntBrain which is used to control the ant.
	 * 
	 * @return The Unique ID of the ant.
	 */
	public AntBrain getAntBrain() {
		return brain;
	}

	/**
	 * Returns an integer representing the current State of the Ant's brain.
	 * 
	 * @return The current brain State of the Ant.
	 */
	public int getState() {
		return state;
	}

	/**
	 * Sets the State number of the Ant.
	 * 
	 * @param state
	 *            An integer representing the new State ID.
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * Gets the Colour of the Ant.
	 * 
	 * @return The Colour of the Ant.
	 */
	public Colour getColour() {
		return colour;
	}

	/**
	 * Gets the X coordinate of the Ant.
	 * 
	 * @return An integer representing the X coordinate of the Ant in a 2d World.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the X coordinate of the Ant
	 * 
	 * @param x
	 *            An integer representing the new X coordinate of the Ant in a 2d World.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the Y coordinate of the Ant.
	 * 
	 * @return An integer representing the Y coordinate of the Ant in a 2d World.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the Y coordinate of the Ant
	 * 
	 * @param y
	 *            An integer representing the new Y coordinate of the Ant in a 2d World.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * How long the Ant will rest before it's next action.
	 * 
	 * @return The resting value of the Ant.
	 */
	public int getResting() {
		return resting;
	}

	/**
	 * Sets the resting value of the Ant.
	 * 
	 * @param resting
	 *            How long the Ant will need to rest before it's next action.
	 */
	public void setResting(int resting) {
		this.resting = resting;
	}

	/**
	 * Gets the direction in which the Ant is facing.
	 * 
	 * @return The Ant's current direction.
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Sets the Ant's direction to a value between 0 and the Ant's maximum possible directions (6).
	 * 
	 * @param direction
	 *            The new direction of the Ant.
	 */
	public void setDirection(int direction) {
		this.direction = ((direction % POSSIBLE_DIRECTIONS) + POSSIBLE_DIRECTIONS) % POSSIBLE_DIRECTIONS;
	}

	/**
	 * Is the Ant holding food?
	 * 
	 * @return True if yes, False if no.
	 */
	public boolean hasFood() {
		return hasFood;
	}

	/**
	 * Sets whether the Ant is holding food or not.
	 * 
	 * @param food
	 *            True if yes, False if no.
	 */
	public void setFood(boolean food) {
		this.hasFood = food;
	}

	/**
	 * Simulates the ant. If the ant needs to rest, rest once and reduce the rest count. otherwise the ant does not need to rest and so execute the state
	 * represented in the brain by the state ID of the ant.
	 * 
	 * @param world
	 *            The world in which the ant is simulated.
	 */
	public void simulate(World world) {
		if (resting > 0) {
			resting--;
		} else {
//			int oldState = state;
			brain.simulate(this, world);
//			if(id == 21){
//				System.out.println(colour.name() + ": " + oldState + ", " + state);
//				int value = (Game.seed / 65536) % 16384;
//				if (value < 0) {
//					value += 16384 - 1;
//				}
//				System.out.println(Game.count);
//				System.out.println(Game.seed);
//				System.out.println(value);
//			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brain == null) ? 0 : brain.hashCode());
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		result = prime * result + direction;
		result = prime * result + (hasFood ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + resting;
		result = prime * result + state;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ant other = (Ant) obj;
		if (brain == null) {
			if (other.brain != null)
				return false;
		} else if (!brain.equals(other.brain))
			return false;
		if (colour != other.colour)
			return false;
		if (direction != other.direction)
			return false;
		if (hasFood != other.hasFood)
			return false;
		if (id != other.id)
			return false;
		if (resting != other.resting)
			return false;
		if (state != other.state)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	public static void main(String[] args) {
		Player p1 = new Player("Player 1", null);
		p1.setColour(Colour.BLACK);
		Ant ant = new Ant(p1, 1);
		System.out.println(ant.getDirection());
		ant.setDirection(ant.getDirection() + TurnDir.RIGHT.getValue());
		System.out.println(ant.getDirection());
	}
}
