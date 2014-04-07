package model.state;

import model.Ant;
import model.World;

/**
 * A public class to represent a single state of an ant brain.
 * 
 * @author 108069
 * 
 */
public abstract class State {

	/**
	 * A method used to execute any state.
	 * 
	 * @param ant
	 *            The ant which is executing the state.
	 * @param world
	 *            The world in which the state is executed.
	 */
	public abstract void execute(Ant ant, World world);
	
	public abstract boolean equals(Object obj);
	public abstract int hashCode();
}
