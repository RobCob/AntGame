package model;

/**
 * A finite state brain used to manage and control a team of Ants.
 * Uses a list of State objects for a group of Ants.
 * @author 108069
 *
 */
public class AntBrain {
	State[] stateList;
	String filename = "unknown";
	
	/**
	 * Creates an AntBrain from a list of State Objects.
	 * @param stateList A finite state machine represented as State Objects.
	 */
	public AntBrain(State[] stateList){
		this(stateList, "unknown");
	}
	
	/**
	 * Creates an AntBrain from a list of State Objects.
	 * @param stateList A finite state machine represented as State Objects.
	 * @param filename The name of the file used to create the brain.
	 */
	public AntBrain(State[] stateList, String filename){
		this.stateList = stateList;
		this.filename = filename;
	}
	
	/**
	 * Gets the filename used to create the ant.
	 * @return The filename used to create the AntBrain, "unknown" if it cannot be found.
	 */
	public String getFileName() {
		return this.filename;
	}
	
	/**
	 * Simulates the given ant with the state represented by
	 * the state ID stored in the ant.
	 * @param ant The Ant to be simulated.
	 * @param world The world in which the Ant is simulated.
	 */
	public void simulate(Ant ant, World world){
		stateList[ant.getState()].execute(ant, world);
	}
	
	@Override
	protected Object clone() {
		return new AntBrain(stateList.clone());
	}
}
