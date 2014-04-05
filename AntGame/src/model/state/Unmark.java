package model.state;

import model.Ant;
import model.World;
import model.tile.ClearTile;

/**
 * A state used to remove a marker from a world tile.
 * 
 * @author 108069
 * 
 */
public class Unmark extends State {
	int scent, state;

	/**
	 * A simple constructor.
	 * 
	 * @param scent
	 *            Which marker to remove.
	 * @param state
	 *            Which state to go to afterwards.
	 */
	public Unmark(int scent, int state) {
		this.scent = scent;
		this.state = state;
	}

	@Override
	public void execute(Ant ant, World world) {
		((ClearTile) (world.getTile(ant.getX(), ant.getY()))).removeMarker(ant.getColour(), scent);
		world.setChange(ant.getY() * world.sizeX + ant.getX());
		ant.setState(state);
	}
}