package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import controller.Game;
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

/**
 * A file reader used to read and parse AntBrain files, converting them into AntBrains.
 * 
 * @author 108069
 * 
 */
public class AntBrainReader {

	/**
	 * Reads and parses an AntBrain from a file.
	 * 
	 * @param file
	 *            The file to be read.
	 * @return The AntBrain parsed from file, null if the brain is not syntactically correct.
	 */
	public static AntBrain readBrain(File file) {
		try {
			String[] stringList = separateLines(readFromFile(file));
			State[] stateList = new State[stringList.length];
			if (stateList.length > 9999) {
				throw new Exception("Too many lines in Ant Brain.");
			}
			for (int i = 0; i < stateList.length; i++) {
				stateList[i] = classifyState(stringList[i], stateList.length);
			}
			return new AntBrain(stateList, file.getName());
		} catch (Exception e) {
			if (Game.DEBUG) {
				System.out.println("DEBUG | BRAIN READER: " + e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Reads and parses an AntBrain from a filepath.
	 * 
	 * @param filePath
	 *            The path of the file to be read.
	 * @return The AntBrain parsed from file, null if the brain is not syntactically correct.
	 */
	public static AntBrain readBrain(String filePath) {
		try {
			File file = new File(filePath);
			return readBrain(file);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Read states as a string from file used to create a brain once parsed.
	 * 
	 * @param file
	 *            The file in which the brain is stored.
	 * @return The contents of the file as a continuous string with new line characters between states.
	 */
	private static String readFromFile(File file) {
		String output = "";
		BufferedReader inputBuffer = null;

		try {
			inputBuffer = new BufferedReader(new FileReader(file));

			String line;
			while ((line = inputBuffer.readLine()) != null) {
				output += line + "\n";
			}
		} catch (Exception e) {
			output = null;
			System.out.println(e.getMessage());
		} finally {
			if (inputBuffer != null) {
				try {
					inputBuffer.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return output;
	}

	/**
	 * A simple method to separate each line of the input string into it's own array index.
	 * 
	 * @param input
	 *            The input string.
	 * @return An array of Strings with each entry representing a new line.
	 */
	private static String[] separateLines(String input) {
		String[] stateList = input.trim().split("\n");
		return stateList;
	}

	/**
	 * Classifies a string into a state.
	 * 
	 * @param state
	 *            The state as a string.
	 * @param totalStateCount
	 *            The total number of states in the states being parsed. Used to check if a state is valid
	 * @return A State object.
	 * @throws Exception
	 *             if the state fails to be classified.
	 */
	private static State classifyState(String state, int totalStateCount) throws Exception {
		// Trim input sentence and then split into 'words' (strings separated by whitespace)
		// TODO: Decide how strict input checking should be. Should I trim or should it cause an error? eg " Drop 2 "
		// Try matching the first word in the line to each of the instructions.
		// Check the appropriate words and convert them to ints/enums, throwing exceptions on each error.
		// If there are no errors, create appropriate state and output it.
		State output = null;
		String[] stateTokens = state.trim().split(" ");
		switch (stateTokens[0].toUpperCase()) {
		case "SENSE":
			SenseDir direction = checkSenseDirection(stateTokens[1]);
			int st1 = checkStateNumber(stateTokens[2], totalStateCount);
			int st2 = checkStateNumber(stateTokens[3], totalStateCount);
			Condition condition = checkCondition(stateTokens[4]);
			// Extra variable needed if the state is sensing for a Marker
			if (condition == Condition.MARKER) {
				int scent = checkMark(stateTokens[5]);
				output = new Sense(direction, st1, st2, condition, scent);
			} else {
				output = new Sense(direction, st1, st2, condition);
			}
			break;

		case "MARK":
			int scent = checkMark(stateTokens[1]);
			st1 = checkStateNumber(stateTokens[2], totalStateCount);
			output = new Mark(scent, st1);
			break;

		case "UNMARK":
			scent = checkMark(stateTokens[1]);
			st1 = checkStateNumber(stateTokens[2], totalStateCount);
			output = new Unmark(scent, st1);
			break;

		case "PICKUP":
			st1 = checkStateNumber(stateTokens[1], totalStateCount);
			st2 = checkStateNumber(stateTokens[2], totalStateCount);
			output = new PickUp(st1, st2);
			break;

		case "DROP":
			st1 = checkStateNumber(stateTokens[1], totalStateCount);
			output = new Drop(st1);
			break;

		case "TURN":
			TurnDir turnDirection = checkTurnDirection(stateTokens[1]);
			st1 = checkStateNumber(stateTokens[2], totalStateCount);
			output = new Turn(turnDirection, st1);
			break;

		case "MOVE":
			st1 = checkStateNumber(stateTokens[1], totalStateCount);
			st2 = checkStateNumber(stateTokens[2], totalStateCount);
			output = new Move(st1, st2);
			break;

		case "FLIP":
			int p = Integer.parseInt(stateTokens[1]);
			st1 = checkStateNumber(stateTokens[2], totalStateCount);
			st2 = checkStateNumber(stateTokens[3], totalStateCount);
			output = new Flip(p, st1, st2);
			break;

		default:
			throw new Exception("Invalid state");
		}
		return output;
	}

	/**
	 * Private method to check if the supplied state ID is acceptable.
	 * 
	 * @param stringStateID
	 *            The string containing the state.
	 * @param max
	 *            The total state count in the read file.
	 * @return The state as an int.
	 * @throws Exception
	 *             If Integer.parseInt(String) fails or if the state is not a value from 0 to 9999.
	 */
	private static int checkStateNumber(String stringStateID, int max) throws Exception {
		int stateID = Integer.parseInt(stringStateID);
		if ((stateID < 0) || stateID > 9999) {
			throw new Exception("False state: " + stringStateID);
		}
		return stateID;
	}

	/**
	 * Private method to check if the supplied marker scent is acceptable.
	 * 
	 * @param stringScent
	 *            The string containing the marker id.
	 * @return The scent as an int.
	 * @throws Exception
	 *             If Integer.parseInt(String) fails or the the scent is not a value from 0 to 5.
	 */
	private static int checkMark(String stringScent) throws Exception {
		int scent = Integer.parseInt(stringScent);
		if ((scent < 0) || scent > 5) {
			throw new Exception("False mark: " + stringScent);
		}
		return scent;
	}

	/**
	 * Private method to check if the supplied sense direction is acceptable.
	 * 
	 * @param stringDirection
	 *            - The string containing the sense direction.
	 * @return The sense direction as a SenseDir enum.
	 * @throws Exception
	 *             - If the string doesn't match the possible directions.
	 */
	private static SenseDir checkSenseDirection(String stringDirection) throws Exception {
		SenseDir direction;
		switch (stringDirection.toUpperCase()) {
		case "HERE":
			direction = SenseDir.HERE;
			break;
		case "AHEAD":
			direction = SenseDir.AHEAD;
			break;
		case "LEFTAHEAD":
			direction = SenseDir.LEFTAHEAD;
			break;
		case "RIGHTAHEAD":
			direction = SenseDir.RIGHTAHEAD;
			break;
		default:
			throw new Exception("False direction: " + stringDirection);
		}
		return direction;
	}

	/**
	 * Private method to check if the supplied turn direction is acceptable.
	 * 
	 * @param stringDirection
	 *            The string containing the turn direction.
	 * @return The turn direction as a TurnDir enum.
	 * @throws Exception
	 *             If the string doesn't match the possible directions.
	 */
	private static TurnDir checkTurnDirection(String stringDirection) throws Exception {
		TurnDir direction;
		switch (stringDirection.toUpperCase()) {
		case "LEFT":
			direction = TurnDir.LEFT;
			break;
		case "RIGHT":
			direction = TurnDir.RIGHT;
			break;
		default:
			throw new Exception("False turn direction: " + stringDirection);
		}
		return direction;
	}

	/**
	 * Private method to check if the supplied sense condition is acceptable.
	 * 
	 * @param s
	 *            The string containing the sense condition.
	 * @return The condition as a Condition enum.
	 * @throws Exception
	 *             If the string doesn't match the possible directions.
	 */
	private static Condition checkCondition(String s) throws Exception {
		Condition condition;
		switch (s.toUpperCase()) {
		case "FRIEND":
			condition = Condition.FRIEND;
			break;
		case "FOE":
			condition = Condition.FOE;
			break;
		case "FRIENDWITHFOOD":
			condition = Condition.FRIENDWITHFOOD;
			break;
		case "FOEWITHFOOD":
			condition = Condition.FOEWITHFOOD;
			break;
		case "FOOD":
			condition = Condition.FOOD;
			break;
		case "ROCK":
			condition = Condition.ROCK;
			break;
		case "MARKER":
			condition = Condition.MARKER;
			break;
		case "FOEMARKER":
			condition = Condition.FOEMARKER;
			break;
		case "HOME":
			condition = Condition.HOME;
			break;
		case "FOEHOME":
			condition = Condition.FOEHOME;
			break;
		default:
			throw new Exception("False Condition: " + s);
		}
		return condition;
	}
}
