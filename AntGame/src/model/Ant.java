package model;
/**
 * This class is a representation of an ant. It is used to
 * simulate a ant character in a World and it's actions
 * are determined by the AntBrain that it holds. 
 * @author 108069
 *
 */
public class Ant {
	public static final int POSSIBLE_DIRECTIONS = 6;
	protected static int ID_COUNTER = 0;
	private int id, state, x, y, direction, resting;
	private boolean hasFood;
	private AntBrain brain;
	private Colour colour;
	
	/**
	 * Constructs an ant with the Colour and brain of the given player.
	 * @param player The 'owner' of the ant.
	 */
	public Ant(Player player){
		this.id = ID_COUNTER++;
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
	 * @return The Unique ID of the ant.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Returns an integer representing the current State of the Ant's brain. 
	 * @return The current brain State of the Ant.
	 */
	public int getState(){
		return state;
	}
	
	/**
	 * Sets the State number of the Ant.
	 * @param state An integer representing the new State ID.
	 */
	public void setState(int state){
		this.state = state;
	}
	
	/**
	 * Gets the Colour of the Ant.
	 * @return The Colour of the Ant.
	 */
	public Colour getColour(){
		return colour;
	}
	
	/**
	 * Gets the X coordinate of the Ant.
	 * @return An integer representing the X coordinate of the Ant in a 2d World.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the X coordinate of the Ant
	 * @param x An integer representing the new X coordinate of the Ant in a 2d World.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Gets the Y coordinate of the Ant.
	 * @return An integer representing the Y coordinate of the Ant in a 2d World.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the Y coordinate of the Ant
	 * @param y An integer representing the new Y coordinate of the Ant in a 2d World.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * How long the Ant will rest before it's next action.
	 * @return The resting value of the Ant.
	 */
	public int getResting() {
		return resting;
	}
	
	/**
	 * Sets the resting value of the Ant.
	 * @param resting How long the Ant will need to rest before it's next action.
	 */
	public void setResting(int resting) {
		this.resting = resting;
	}

	/**
	 * Gets the direction in which the Ant is facing.
	 * @return The Ant's current direction.
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Sets the Ant's direction to a value between 0 and the Ant's maximum possible directions (6).
	 * @param direction The new direction of the Ant.
	 */
	public void setDirection(int direction) {
		this.direction = ((direction % POSSIBLE_DIRECTIONS) + POSSIBLE_DIRECTIONS) % POSSIBLE_DIRECTIONS;
	}
	
	/**
	 * Is the Ant holding food?
	 * @return True if yes, False if no.
	 */
	public boolean hasFood() {
		return hasFood;
	}
	
	/**
	 * Sets whether the Ant is holding food or not.
	 * @param food True if yes, False if no.
	 */
	public void setFood(boolean food) {
		this.hasFood = food;
	}
	
	/**
	 * Simulates the ant.
	 * If the ant needs to rest, rest once and reduce the rest count.
	 * otherwise the ant does not need to rest and so execute the state
	 * represented in the brain by the state ID of the ant.
	 * @param world The world in which the ant is simulated.
	 */
	public void simulate(World world){
		if(resting > 0){
			resting--;
		}else{
			brain.simulate(this, world);
		}
	}
}
