package model;
public class AntBrain {
	Colour colour;
	State[] stateList;
	
	public AntBrain(State[] stateList, Colour colour){
		this.stateList = stateList;
		this.colour = colour;
	}
	
	public void execute(Ant a){
		stateList[a.getState()].execute(a);
	}
	
	public Colour getColour(){
		return colour;
	}
}
