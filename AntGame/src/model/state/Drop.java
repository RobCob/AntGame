package model.state;

import model.Ant;
import model.World;
import model.tile.ClearTile;

/**
 * A state used to drop food on a world tile.
 * 
 * @author 108069
 * 
 */
public class Drop extends State {
	int state;

	/**
	 * A simple constructor.
	 * 
	 * @param state
	 *            The state to go to after this executes.
	 */
	public Drop(int state) {
		this.state = state;
	}

	@Override
	public void execute(Ant ant, World world) {
		if (ant.hasFood()) {
			((ClearTile) (world.getTile(ant.getX(), ant.getY()))).addFood();
			ant.setFood(false);
			world.setChange(ant.getY() * world.sizeX + ant.getX());
		}
		ant.setState(state);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state;
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
		Drop other = (Drop) obj;
		if (state != other.state)
			return false;
		return true;
	}
}
