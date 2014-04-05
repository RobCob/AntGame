package model.state;

import model.Ant;
import model.tile.AntHillTile;
import model.tile.ClearTile;
import model.tile.Tile;

/**
 * A public class to represent the condition for which we are sensing.
 * 
 * @author 108069
 * 
 */
public enum Condition {
	FRIEND, FOE, FRIENDWITHFOOD, FOEWITHFOOD, FOOD, ROCK, MARKER, FOEMARKER, HOME, FOEHOME;

	/**
	 * Tests the truth of the given condition
	 * 
	 * @param target
	 *            The tile which we are testing.
	 * @param ant
	 *            The ant which is performing the testing.
	 * @param scent
	 *            The scent to sense for (only used if sensing a marker).
	 * @return if the condition is true.
	 */
	public boolean isTrue(Tile target, Ant ant, int scent) {
		boolean success;
		switch (this) {
		case FOE:
			success = (!target.isRocky()) && ((ClearTile) target).hasAnt() && !((ClearTile) target).getAnt().getColour().equals(ant.getColour());
			break;
		case FOEHOME:
			success = (!target.isRocky()) && ((ClearTile) target).isAntHill() && !((AntHillTile) target).getColour().equals(ant.getColour());
			break;
		case FOEMARKER:
			success = (!target.isRocky()) && ((ClearTile) target).getMarker(ant.getColour().getEnemy(), scent);
			break;
		case FOEWITHFOOD:
			success = (!target.isRocky()) && ((ClearTile) target).hasAnt() && !((ClearTile) target).getAnt().getColour().equals(ant.getColour())
					&& ((ClearTile) target).getAnt().hasFood();
			break;
		case FOOD:
			success = (!target.isRocky()) && ((ClearTile) target).getFood() > 0;
			break;
		case FRIEND:
			success = (!target.isRocky()) && ((ClearTile) target).hasAnt() && ((ClearTile) target).getAnt().getColour().equals(ant.getColour());
			break;
		case FRIENDWITHFOOD:
			success = (!target.isRocky()) && ((ClearTile) target).hasAnt() && ((ClearTile) target).getAnt().getColour().equals(ant.getColour())
					&& ((ClearTile) target).getAnt().hasFood();
			break;
		case HOME:
			success = (!target.isRocky()) && ((ClearTile) target).isAntHill() && ((AntHillTile) target).getColour().equals(ant.getColour());
			break;
		case MARKER:
			success = (!target.isRocky()) && ((ClearTile) target).getMarker(ant.getColour(), scent);
			break;
		case ROCK:
			success = (target.isRocky());
			break;
		default:
			success = false;
			break;
		}
		return success;
	}
}