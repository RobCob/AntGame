public class Ant {
	int id, state, x, y, direction, resting;
	boolean has_food;
	AntBrain brain;
	
	public Ant(AntBrain brain){
		this.brain = brain;
		this.state = 0;
		this.x = 0;
		this.y = 0;
		this.has_food = false;
	}
	
	public int getState(){
		return state;
	}
	
	public void setState(int state){
		this.state = state;
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

	public boolean isFood() {
		return has_food;
	}

	public void setFood(boolean food) {
		this.has_food = food;
	}

	public void simulate(){
		brain.execute(this);
	}
}
