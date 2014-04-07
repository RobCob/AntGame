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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state1;
		result = prime * result + state2;
		result = prime * result + value;
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
		Flip other = (Flip) obj;
		if (state1 != other.state1)
			return false;
		if (state2 != other.state2)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
}
