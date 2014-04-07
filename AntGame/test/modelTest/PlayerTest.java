package modelTest;

import static org.junit.Assert.*;
import model.AntBrain;
import model.Colour;
import model.Player;
import model.state.State;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void playerNameTest() {
		String playerName = "TestName";
		Player player = new Player(playerName, null);
		assertEquals("Supplied name should match the player's nickname", playerName, player.getNickname());

		playerName = "SecondTestName";
		player.setNickname(playerName);
		assertEquals("Supplied name should persist", playerName, player.getNickname());
	}

	@Test
	public void playerBrainTest() {
		AntBrain initialBrain = new AntBrain(null);
		Player player = new Player(null, initialBrain);
		assertEquals("Supplied brain should persist", initialBrain, player.getBrain());
		State[] stateList = AntBrainReaderTest.createStateList(1);
		AntBrain newBrain = new AntBrain(new State[10]);
		player.setBrain(newBrain);
		assertEquals("Supplied brain should be stored", newBrain, player.getBrain());
	}

	@Test
	public void playerColourTest() {
		Player player = new Player(null, null);
		assertNull("Initial colour should equal null", player.getColour());

		player.setColour(Colour.RED);
		assertEquals("Supplied red colour should be stored", Colour.RED, player.getColour());

		player.setColour(Colour.BLACK);
		assertEquals("Supplied black colour should be stored", Colour.BLACK, player.getColour());
	}

	public void playerCloneTest() {
		Player player = new Player("TestName", new AntBrain(new State[10]));
		player.setColour(Colour.RED);
		Object clone = player.clone();
		assertEquals("Cloned object class should equal", clone.getClass(), player.getClass());
		assertNotSame("Cloned object should not be the same object", clone, player);
		assertEquals("Cloned object should equal", clone, player);
	}
}
