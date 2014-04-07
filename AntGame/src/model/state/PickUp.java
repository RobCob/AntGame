package model.state;

import model.Ant;
import model.World;
import model.tile.ClearTile;

/**
 * A state used to pick up food from a world tile.
 * 
 * @author 108069
 * 
 */
public class PickUp extends State {
	int state1, state2;

	/**
	 * A simple constructer.
	 * 
	 * @param state1
	 *            The state to go to if food is picked up.
	 * @param state2
	 *            The state to go to if food is not picked up.
	 */
	public PickUp(int state1, int state2) {
		this.state1 = state1;
		this.state2 = state2;
	}

	@Override
	public void execute(Ant ant, World world) {
		ClearTile tile = ((ClearTile) (world.getTile(ant.getX(), ant.getY())));
		if(tile.getFood() > 0){
			if (!ant.hasFood()) {
				ant.setFood(tile.takeFood());
				world.setChange(ant.getY() * world.sizeX + ant.getX());
				ant.setState(state1);
			}else{
				ant.setState(state2);
			}
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
		PickUp other = (PickUp) obj;
		if (state1 != other.state1)
			return false;
		if (state2 != other.state2)
			return false;
		return true;
	}
}