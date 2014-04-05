package model.state;

import controller.Game;
import model.Ant;
import model.World;

/**
 * A state used to make random decisions.
 * 
 * @author 108069
 * 
 */
public class Flip extends State {
	int value, state1, state2;

	/**
	 * A simple constructor.
	 * 
	 * @param value
	 *            The generated random number will be between 0 (inclusive) and this value (exclusive).
	 * @param state1
	 *            The state to go to if the random value is equal to 0.
	 * @param state2
	 *            The state to go to otherwise.
	 */
	public Flip(int value, int state1, int state2) {
		this.value = value;
		this.state1 = state1;
		this.state2 = state2;
	}

	@Override
	public void execute(Ant ant, World world) {
		if (Game.randomInt(value) == 0) {
			ant.setState(state1);
		} else {
			ant.setState(state2);
		}
	}
}
