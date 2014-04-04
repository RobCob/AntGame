package model;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A Tile implementation that represents a Clear tile in a world.
 * It can hold the following:
 *  - Any number of food.
 *  - Any number of scent markers (represented as integer IDs) for each Colour.
 *  - An ant object.
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
	public ClearTile(){
		this(0);
	}
	
	/**
	 * Constructs a ClearTile with food equal to the given value.
	 * @param food The amount of food to be in the tile.
	 */
	public ClearTile(int food){
		this.food = food;
		this.ant = null;
		this.markers = new HashMap<Colour, HashSet<Integer>>();
	}
	
	@Override
	public boolean isRocky() {
		return false;
	}
	
	/**
	 * Does the tile contain an ant / is the ant equal to null?
	 * @return Is the ant not null.
	 */
	public boolean hasAnt(){
		return ant != null;
	}
	
	/**
	 * Returns the ant in the tile. 
	 * @return The stored Ant object.
	 */
	public Ant getAnt(){
		return ant;
	}
	
	/**
	 * Sets the ant stored in the tile.
	 * @param ant The ant to be stored.
	 */
	public void setAnt(Ant ant){
		this.ant = ant;
	}
	
	/**
	 * Sets the ant stored in the tile to null.
	 */
	public void removeAnt(){
		this.ant = null;
	}
	
	/**
	 * Is the tile an instance of AntHillTile.
	 * @return True or False
	 */
	public boolean isAnthill(){
		return false;
	}
	
	/**
	 * Places a marker in the current tile.
	 * @param colour The colour of the marker.
	 * @param scent The ID of the marker.
	 */
	public void placeMarker(Colour colour, int scent){
		if(markers.containsKey(colour)){
			markers.get(colour).add(scent);
		}else{
			HashSet<Integer> set = new HashSet<Integer>();
			set.add(scent);
			markers.put(colour, set);
		}
	}
	
	/**
	 * Removes a marker in the current tile.
	 * @param colour The colour of the marker.
	 * @param scent The ID of the marker.
	 */
	public void removeMarker(Colour colour, int scent){
		if(markers.containsKey(colour)){
			markers.get(colour).remove(scent);
		}
	}
	
	/**
	 * Checks the tile for a marker of the given colour and ID.
	 * @param colour The colour of the marker to be checked.
	 * @param scent The integer ID of the marker to be checked.
	 * @return True if found, False if not.
	 */
	public boolean getMarker(Colour colour, int scent){
		return markers.containsKey(colour) && markers.get(colour).contains(scent);
	}
	
	/**
	 * How much food does the tile hold?
	 * @return The integer value of how much food the tile holds.
	 */
	public int getFood(){
		return food;
	}
	
	/**
	 * Sets the value of the food stored in the tile
	 * @param food How much food.
	 */
	public void setFood(int food){
		this.food = food;
	}
	
	/**
	 * Increases the amount of food by 1.
	 */
	public void addFood(){
		this.food++;
	}
	
	/**
	 * Reduces the amount of food by 1.
	 * @return True if possible, False if there is 0 food.
	 */
	public boolean takeFood(){
		if(food > 0){
			food--;
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public Object clone() {
		ClearTile tile = new ClearTile(food);
		tile.setAnt(getAnt());
		for(int i = 0; i < 6; i++){
			if(getMarker(Colour.RED, i)){
				tile.placeMarker(Colour.RED, i);
			}
			if(getMarker(Colour.BLACK, i)){
				tile.placeMarker(Colour.BLACK, i);
			}
		}
		return tile;
	}
}
