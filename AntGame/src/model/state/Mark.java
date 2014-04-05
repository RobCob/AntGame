package model.state;

import model.Ant;
import model.World;
import model.tile.ClearTile;

/**
 * A state used to place a marker in a world tile.
 * 
 * @author 108069
 * 
 */
public class Mark extends State {
	int scent, state;

	/**
	 * A simple constructor.
	 * 
	 * @param scent
	 *            Which marker to place.
	 * @param state
	 *            Which state to go to afterwards.
	 */
	public Mark(int scent, int state) {
		this.scent = scent;
		this.state = state;
	}

	@Override
	public void execute(Ant ant, World world) {
		((ClearTile) (world.getTile(ant.getX(), ant.getY()))).placeMarker(ant.getColour(), scent);
		ant.setState(state);
	}
}