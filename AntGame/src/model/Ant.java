package model;
public class Ant {
	private static int ID_COUNTER = 0;
	private int id, state, x, y, direction, resting;
	private boolean hasFood;
	private AntBrain brain;
	private Colour colour;
	
	public Ant(Player player){
		this.id = ID_COUNTER++;
		this.brain = player.getBrain();
		this.state = 0;
		this.x = 0;
		this.y = 0;
		this.direction = 0;
		this.resting = 0;
		this.colour = player.getColour();
		this.hasFood = false;
	}
	
	public int getID() {
		return id;
	}
	
	public int getState(){
		return state;
	}
	
	public void setState(int state){
		this.state = state;
	}
	
	public Colour getColour(){
		return colour;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int n) {
		direction = ((n % 6) + 6) % 6;
	}

	public boolean hasFood() {
		return hasFood;
	}

	public void setFood(boolean food) {
		this.hasFood = food;
	}

	public void simulate(World w){
		if(resting > 0){
			resting--;
		}else{
			brain.simulate(this, w);
		}
	}
}
