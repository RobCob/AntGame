package model.tile;

import java.util.HashMap;
import java.util.HashSet;

import model.Ant;
import model.Colour;

/**
 * A Tile implementation that represents a Clear tile in a world. It can hold the following: - Any number of food. - Any number of scent markers (represented as
 * integer IDs) for each Colour. - An ant object.
 * 
 * @author 108069
 * 
 */
public class ClearTile implements Tile {
	private HashMap<Colour, HashSet<Integer>> markers;
	private int food;
	private Ant ant;

	/**
	 * Constructs a ClearTile with 0 food.
	 */
	public ClearTile() {
		this(0);
	}

	/**
	 * Constructs a ClearTile with food equal to the given value.
	 * 
	 * @param food
	 *            The amount of food to be in the tile.
	 */
	public ClearTile(int food) {
		this.markers = new HashMap<Colour, HashSet<Integer>>();
		markers.put(Colour.RED, new HashSet<Integer>());
		markers.put(Colour.BLACK, new HashSet<Integer>());
		setFood(food);
		setAnt(null);
	}

	@Override
	public boolean isRocky() {
		return false;
	}

	/**
	 * Does the tile contain an ant / is the ant equal to null?
	 * 
	 * @return Is the ant not null.
	 */
	public boolean hasAnt() {
		return ant != null;
	}

	/**
	 * Returns the ant in the tile.
	 * 
	 * @return The stored Ant object.
	 */
	public Ant getAnt() {
		return ant;
	}

	/**
	 * Sets the ant stored in the tile.
	 * 
	 * @param ant
	 *            The ant to be stored.
	 */
	public void setAnt(Ant ant) {
		this.ant = ant;
	}

	/**
	 * Sets the ant stored in the tile to null.
	 */
	public void removeAnt() {
		this.ant = null;
	}

	/**
	 * Is the tile an instance of AntHillTile.
	 * 
	 * @return True or False
	 */
	public boolean isAntHill() {
		return false;
	}

	/**
	 * Checks the tile for a marker of the given colour and ID.
	 * 
	 * @param colour
	 *            The colour of the marker to be checked.
	 * @param scent
	 *            The integer ID of the marker to be checked.
	 * @return True if found, False if not.
	 */
	public boolean getMarker(Colour colour, int scent) {
		return markers.get(colour).contains(scent);
	}

	/**
	 * Places a marker in the current tile.
	 * 
	 * @param colour
	 *            The colour of the marker.
	 * @param scent
	 *            The ID of the marker.
	 */
	public void placeMarker(Colour colour, int scent) {
		markers.get(colour).add(scent);
	}

	/**
	 * Removes a marker in the current tile.
	 * 
	 * @param colour
	 *            The colour of the marker.
	 * @param scent
	 *            The ID of the marker.
	 */
	public void removeMarker(Colour colour, int scent) {
		markers.get(colour).remove(scent);
	}

	/**
	 * How much food does the tile hold?
	 * 
	 * @return The integer value of how much food the tile holds.
	 */
	public int getFood() {
		return food;
	}

	/**
	 * Sets the value of the food stored in the tile
	 * 
	 * @param food
	 *            How much food.
	 */
	public void setFood(int food) {
		this.food = food;
	}

	/**
	 * Increases the amount of food by 1.
	 */
	public void addFood() {
		this.food++;
	}

	/**
	 * Reduces the amount of food by 1.
	 * 
	 * @return True if possible, False if there is 0 food.
	 */
	public boolean takeFood() {
		if (food > 0) {
			food--;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object clone() {
		ClearTile tile = new ClearTile(food);
		tile.setAnt(getAnt());
		for (int i = 0; i < 6; i++) {
			if (getMarker(Colour.RED, i)) {
				tile.placeMarker(Colour.RED, i);
			}
			if (getMarker(Colour.BLACK, i)) {
				tile.placeMarker(Colour.BLACK, i);
			}
		}
		return tile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ant == null) ? 0 : ant.hashCode());
		result = prime * result + food;
		result = prime * result + ((markers == null) ? 0 : markers.hashCode());
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
		ClearTile other = (ClearTile) obj;
		if (ant == null) {
			if (other.ant != null)
				return false;
		} else if (!ant.equals(other.ant))
			return false;
		if (food != other.food)
			return false;
		if (markers == null) {
			if (other.markers != null)
				return false;
		} else if (!markers.equals(other.markers))
			return false;
		return true;
	}
}
