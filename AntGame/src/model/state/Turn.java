package model.state;

import model.Ant;
import model.World;

/**
 * A state used to turn an ant left or right.
 * 
 * @author 108069
 * 
 */
public class Turn extends State {
	TurnDir direction;
	int state;

	/**
	 * A simple constructor.
	 * 
	 * @param direction
	 *            The direction in which to turn
	 * @param state
	 *            The state to go to after turning
	 */
	public Turn(TurnDir direction, int state) {
		this.direction = direction;
		this.state = state;
	}

	@Override
	public void execute(Ant ant, World world) {
		ant.setDirection(ant.getDirection() + direction.getValue());
		world.setChange(ant.getY() * world.sizeX + ant.getX());
		ant.setState(state);
	}
}