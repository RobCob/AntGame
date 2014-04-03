package model;
import java.util.HashMap;
import java.util.HashSet;

public class ClearTile implements Tile {
	private HashMap<Colour, HashSet<Integer>> markers;
	private int food;
	private Ant ant;
	
	public ClearTile(){
		this(0);
	}
	
	public ClearTile(int food){
		this.food = food;
		this.ant = null;
		this.markers = new HashMap<Colour, HashSet<Integer>>();
	}
	
	@Override
	public boolean isRocky() {
		return false;
	}
	
	public boolean hasAnt(){
		return ant != null;
	}
	
	public Ant getAnt(){
		return ant;
	}
	
	public void setAnt(Ant ant){
		this.ant = ant;
	}
	
	public void removeAnt(){
		this.ant = null;
	}
	
	public boolean isAnthill(){
		return false;
	}
	
	public void placeMarker(Colour c, int x){
		if(markers.containsKey(c)){
			markers.get(c).add(x);
		}else{
			HashSet<Integer> set = new HashSet<Integer>();
			set.add(x);
			markers.put(c, set);
		}
	}
	
	public void removeMarker(Colour c, int x){
		if(markers.containsKey(c)){
			markers.get(c).remove(x);
		}
	}
	
	public boolean getMarker(Colour c, int x){
		return markers.containsKey(c) && markers.get(c).contains(x);
	}
	
	public int getFood(){
		return food;
	}
	
	public void setFood(int i){
		food = i;
	}
	
	public void addFood(){
		this.food++;
	}
	
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
		return new ClearTile(food);
	}
}
