package model.state;

import model.Ant;
import model.World;
import model.tile.ClearTile;
import model.tile.Tile;

/**
 * A state used to move an ant from one tile to another.
 * 
 * @author 108069
 * 
 */
public class Move extends State {
	int state1, state2;

	/**
	 * A simple constructor.
	 * 
	 * @param state1
	 *            The state to go to if movement is completed.
	 * @param state2
	 *            The state to go to if the ant failed to move.
	 */
	public Move(int state1, int state2) {
		this.state1 = state1;
		this.state2 = state2;
	}

	@Override
	public void execute(Ant ant, World world) {
		boolean success = false;
		int tileNumber = World.getAhead(ant.getDirection(), (ant.getY() * world.sizeX) + ant.getX(), world.sizeX);
		int x = ((tileNumber % world.sizeX) + world.sizeX) % world.sizeX;
		int y = tileNumber / world.sizeX;
		Tile target = world.getTile(x, y);
		success = !target.isRocky() && !((ClearTile) target).hasAnt();
		if (success) {
			world.setChange(ant.getY() * world.sizeX + ant.getX());
			((ClearTile) (world.getTile(ant.getX(), ant.getY()))).removeAnt();
			((ClearTile) target).setAnt(ant);
			ant.setX(x);
			ant.setY(y);
			ant.setResting(14);
			ant.setState(state1);
			world.setChange(ant.getY() * world.sizeX + ant.getX());
			world.triggerUpdates(tileNumber);
		} else {
			ant.setState(state2);
		}
	}
}