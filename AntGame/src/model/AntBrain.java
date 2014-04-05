package model;

import java.util.Arrays;

import model.state.State;

/**
 * A finite state brain used to manage and control a team of Ants. Uses a list of State objects for a group of Ants.
 * 
 * @author 108069
 * 
 */
public class AntBrain {
	State[] stateList;
	String fileName = "unknown";

	/**
	 * Creates an AntBrain from a list of State Objects.
	 * 
	 * @param stateList
	 *            A finite state machine represented as State Objects.
	 */
	public AntBrain(State[] stateList) {
		this(stateList, "unknown");
	}

	/**
	 * Creates an AntBrain from a list of State Objects.
	 * 
	 * @param stateList
	 *            A finite state machine represented as State Objects.
	 * @param filename
	 *            The name of the file used to create the brain.
	 */
	public AntBrain(State[] stateList, String filename) {
		this.stateList = stateList;
		this.fileName = filename;
	}

	/**
	 * Gets the filename used to create the ant.
	 * 
	 * @return The filename used to create the AntBrain, "unknown" if it cannot be found.
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Simulates the given ant with the state represented by the state ID stored in the ant.
	 * 
	 * @param ant
	 *            The Ant to be simulated.
	 * @param world
	 *            The world in which the Ant is simulated.
	 */
	public void simulate(Ant ant, World world) {
		stateList[ant.getState()].execute(ant, world);
	}

	@Override
	public Object clone() {
		return new AntBrain(stateList.clone(), fileName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + Arrays.hashCode(stateList);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AntBrain other = (AntBrain) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (!Arrays.equals(stateList, other.stateList))
			return false;
		return true;
	}
}
