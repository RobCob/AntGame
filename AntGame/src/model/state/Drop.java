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
			world.setChange(ant.getY() * world.sizeX + ant.getX());
			ant.setState(state);
		}
	}
}
