package model;

import java.io.File;

public class AntBrain {
	State[] stateList;
	
	public AntBrain(State[] stateList){
		this.stateList = stateList;
	}
	
	public void simulate(Ant a, World w){
		stateList[a.getState()].execute(a, w);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new AntBrain(stateList.clone());
	}
}
