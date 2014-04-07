package modelTest;

import static org.junit.Assert.*;
import model.AntBrain;
import model.AntBrainReader;
import model.state.Condition;
import model.state.Drop;
import model.state.Flip;
import model.state.Mark;
import model.state.Move;
import model.state.PickUp;
import model.state.Sense;
import model.state.SenseDir;
import model.state.State;
import model.state.Turn;
import model.state.TurnDir;
import model.state.Unmark;

import org.junit.Test;

public class AntBrainReaderTest {

	private State[] createStateList(int x){
		State[] stateList = new State[8];
		stateList[0] = new Drop(x);
		stateList[1] = new Flip(1, x, 3);
		stateList[2] = new Mark(1, x);
		stateList[3] = new Move(x, 2);
		stateList[4] = new PickUp(x, 2);
		stateList[5] = new Sense(SenseDir.AHEAD, x, 2, Condition.FOE);
		stateList[6] = new Turn(TurnDir.LEFT,x);
		stateList[7] = new Unmark(1, x);
		return stateList;
	}
	@Test
	public void validReadTest() {
		AntBrain readBrain = AntBrainReader.readBrain("assTest.brain");
		AntBrain oracle = new AntBrain(createStateList(1), "assTest.brain");
		assertTrue("Is the test brain equal to the oracle: ",readBrain.equals(oracle));
	}
//	@Test
//	public void validRead1Test() {
//		AntBrain readBrain = AntBrainReader.readBrain("assTest1.brain");
//		assertNull("Does the test fail for -1: ",readBrain);
//	}
//	@Test
//	public void validRead2Test() {
//		AntBrain readBrain = AntBrainReader.readBrain("assTest2.brain");
//		assertNull("Does the test fail for 10000: ",readBrain);
//	}

}
