package modelTest;

import static org.junit.Assert.*;
import model.AntBrain;
import model.state.State;

import org.junit.Test;

public class AntBrainTest {

	@Test
	public void stateListTest() {
		fail("Not yet implemented");
		State[] stateList = { null, null, null };
		AntBrain brain = new AntBrain(stateList);
		assertEquals("Supplied state should be stored", true, false);
	}

	@Test
	public void filenameConstructorTest() {
		String fileName = "A File Name";
		AntBrain brain = new AntBrain(null, fileName);
		assertEquals("Supplied file name should be stored", fileName, brain.getFileName());
	}

	@Test
	public void cloneTest() {
		AntBrain brain = new AntBrain(new State[10], "Another File Name");
		Object clone = brain.clone();
		assertEquals("Clone should be equal to original", clone, brain);
	}
}
