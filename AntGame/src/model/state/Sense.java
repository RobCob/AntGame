package model.state;

import model.Ant;
import model.World;
import model.tile.Tile;

/**
 * A state used by an ant to sense the world around it.
 * 
 * @author 108069
 * 
 */
public class Sense extends State {
	SenseDir senseDirection;
	int st1, st2, scent;
	Condition condition;

	/**
	 * A simple constructer for non-marker senses.
	 * 
	 * @param direction
	 *            The direction in which to sense.
	 * @param trueState
	 *            The state to go to if the condition is true.
	 * @param falseState
	 *            The state to go to if the condition is false.
	 * @param condition
	 *            The condition to check for.
	 */
	public Sense(SenseDir direction, int trueState, int falseState, Condition condition) {
		this(direction, trueState, falseState, condition, 0);
	}

	/**
	 * A simple constructer for marker specific senses.
	 * 
	 * @param direction
	 *            The direction in which to sense.
	 * @param trueState
	 *            The state to go to if the condition is true.
	 * @param falseState
	 *            The state to go to if the condition is false.
	 * @param condition
	 *            The condition to check for.
	 * @param scent
	 *            The scent to sense for.
	 */
	public Sense(SenseDir direction, int trueState, int falseState, Condition condition, int scent) {
		this.senseDirection = direction;
		this.st1 = trueState;
		this.st2 = falseState;
		this.condition = condition;
		this.scent = scent;
	}

	@Override
	public void execute(Ant ant, World world) {
		Tile target;
		boolean success = false;
		switch (senseDirection) {
		case AHEAD:
		case LEFTAHEAD:
		case RIGHTAHEAD:
			int direction = (((ant.getDirection() + senseDirection.getValue()) % 6) + 6) % 6;
			int tileNumber = World.getAhead(direction, (ant.getY() * world.sizeX) + ant.getX(), world.sizeX);
			int x = ((tileNumber % world.sizeX) + world.sizeX) % world.sizeX;
			int y = tileNumber / world.sizeX;
			target = world.getTile(x, y);
			break;
		case HERE:
			target = world.getTile(ant.getX(), ant.getY());
			break;
		default:
			target = null;
			break;
		}
		success = condition.isTrue(target, ant, scent);
		if (success) {
			ant.setState(st1);
		} else {
			ant.setState(st2);
		}
	}
}