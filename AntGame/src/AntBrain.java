public class AntBrain {
	State[] stateList;
	
	public AntBrain(State[] stateList){
		this.stateList = stateList;
	}
	
	public void execute(Ant a){
		stateList[a.getState()].execute(a);
	}
}
