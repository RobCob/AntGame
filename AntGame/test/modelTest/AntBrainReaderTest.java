package modelTest;

import static org.junit.Assert.*;
import model.AntBrain;
import model.AntBrainReader;
import model.state.Flip;
import model.state.State;

import org.junit.Test;

public class AntBrainReaderTest {

	@Test
	public void validReadTest() {
		AntBrain readBrain = AntBrainReader.readBrain("ass.ant");
		State[] stateList = new State[10];
		stateList[0] = new Flip(1, 2, 3);
		stateList[1] = new Flip(1, 2, 3);
		stateList[2] = new Flip(1, 2, 3);
		AntBrain oracle = new AntBrain(stateList);
	}

}
