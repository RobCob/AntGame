package model;

import java.io.File;

public class AntBrain {
	State[] stateList;
	String filename = "unknown";
			
	public AntBrain(State[] stateList){
		this.stateList = stateList;
	}
	
	public AntBrain(State[] stateList, String filename){
		this.stateList = stateList;
		this.filename = filename;
	}
	
	public void simulate(Ant a, World w){
		stateList[a.getState()].execute(a, w);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new AntBrain(stateList.clone());
	}
	
	public String getName() {
		return this.filename;
	}
}
