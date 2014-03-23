package model;

public class ClearTile implements Tile {
	private int food;
	private Ant ant;
	
	public ClearTile(){
		this(0);
	}
	
	public ClearTile(int food){
		this.food = food;
		this.ant = null;
	}
	
	@Override
	public boolean isRocky() {
		return false;
	}
	
	public boolean hasAnt(){
		return false;
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
	
	public void setMarker(int x){
		
	}
	
	public boolean getMarker(int x){
		return false;
	}
	
	public int getFood(){
		return food;
	}
	
	public void setFood(int n){
		this.food = n;
	}
}
