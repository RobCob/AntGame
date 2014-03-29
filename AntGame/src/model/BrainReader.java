package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BrainReader {
	
	public static State[] readBrain(String filepath){
		try{
			String[] stringList = separateLines(readFromFile(filepath));
			State[] stateList = new State[stringList.length];
			for(int i = 0; i < stateList.length; i++){
				stateList[i] = classifyState(stringList[i]);
			}
			return stateList;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	/**
	 * Load ant brain from file
	 * @param path - The filepath of the file.
	 * @return The contents of the file as a continuous string.
	 */
	private static String readFromFile(String path){
		String output = "";
		BufferedReader inputBuffer = null;

        try {
            inputBuffer = new BufferedReader(new FileReader(path));

            String line;
            while (( line = inputBuffer.readLine() ) != null) {
                output += line + "\n";
            }
        }catch(Exception e){
        	System.out.println(e.getMessage());
        }finally {
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
	 * @param input - The input string.
	 * @return An array of Strings with each entry representing a new line.
	 */
	private static String[] separateLines(String input){
		String[] stateList = input.trim().split("\n");
		return stateList;
	}
	
	/**
	 * Iterates through each string in the stateList matching it to a State token
	 * @param state
	 * @return An array of States
	 */
	private static State classifyState(String state){
		State output = null;
		// For each line
		
		// Trim input sentence and then split into 'words' (strings separated by whitespace)
		//TODO: Decide how strict input checking should be. Should I trim or should it cause an error? eg " Drop 2 "
		String[] stateTokens = state.trim().split(" ");
		// Try matching the first word in the line to each of the instructions. 
		// Check the appropriate words and convert them to ints/enums, throwing exceptions on each error.
		// If there are no errors, create appropriate state and add it to the output list of states.
		// Catch all exceptions thrown.
		try{
			switch(stateTokens[0].toUpperCase()){
			case "SENSE":
				SenseDir direction = checkSenseDirection(stateTokens[1]);
				int st1 = checkState(stateTokens[2]);
				int st2 = checkState(stateTokens[3]);
				Condition condition = checkCondition(stateTokens[4]);
				// Extra variable needed if the state is sensing for a Marker
				if(condition == Condition.MARKER){
					int scent = checkMark(stateTokens[5]);						
					output = new Sense(direction, st1, st2, condition, scent);
				}else{
					output = new Sense(direction, st1, st2, condition);
				}
				break;
				
			case "MARK":
					int scent = checkMark(stateTokens[1]);
					st1 = checkState(stateTokens[2]);
					output = new Mark(scent, st1);
				break;
				
			case "UNMARK":
					scent = checkMark(stateTokens[1]);
					st1 = checkState(stateTokens[2]);
					output = new Mark(scent, st1);
				break;
				
			case "PICKUP":
					st1 = checkState(stateTokens[1]);
					st2 = checkState(stateTokens[2]);
					output = new PickUp(st1, st2);
				break;
				
			case "DROP":
					st1 = checkState(stateTokens[1]);
					output = new Drop(st1);
				break;
				
			case "TURN":
					TurnDir turnDirection = checkTurnDirection(stateTokens[1]);
					st1 = checkState(stateTokens[2]);
					output = new Turn(turnDirection, st1);
				break;
				
			case "MOVE":
					st1 = checkState(stateTokens[1]);
					st2 = checkState(stateTokens[2]);
					output = new Move(st1, st2);
				break;
				
			case "FLIP":
					int p = Integer.parseInt(stateTokens[1]);
					st1 = checkState(stateTokens[2]);
					st2 = checkState(stateTokens[3]);
					output = new Flip(p, st1, st2);
				break;
				
			default:
				throw new Exception("Invalid state");
				
			}
		}catch(Exception e){
			System.out.println("Error on string: " + state);
			System.out.println(e.getMessage());
		}
		return output;
	}
	
	/**
	 * Private method to check if the supplied state is acceptable.
	 * @param s - The string containing the state.
	 * @return The state as an int.
	 * @throws Exception - If Integer.parseInt(String) fails or if the state is not a value from 0 to 999.
	 */
	private static int checkState(String s) throws Exception{
		int state = Integer.parseInt(s);
		if((state < 0)||state > 9999){
			throw new Exception("False state: "+ s);
		}
		return state;
	}
	
	/**
	 * Private method to check if the supplied marker scent is acceptable.
	 * @param s - The string containing the marker id.
	 * @return The scent as an int.
	 * @throws Exception - If Integer.parseInt(String) fails or the the scent is not a value from 0 to 5.
	 */
	private static int checkMark(String s) throws Exception{
		int state = Integer.parseInt(s);
		if((state < 0)||state > 5){
			throw new Exception("False mark: "+ s);
		}
		return state;
	}
	
	/**
	 * Private method to check if the supplied sense direction is acceptable.
	 * @param s - The string containing the sense direction.
	 * @return The sense direction as an enum.
	 * @throws Exception - If the string doesn't match the possible directions.
	 */
	private static SenseDir checkSenseDirection(String s) throws Exception{
		SenseDir direction;
		switch(s.toUpperCase()){
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
			throw new Exception("False direction: " + s);
		}
		return direction;
	}
	
	/**
	 * Private method to check if the supplied turn direction is acceptable.
	 * @param s - The string containing the turn direction.
	 * @return The turn direction as an enum.
	 * @throws Exception - If the string doesn't match the possible directions.
	 */
	private static TurnDir checkTurnDirection(String s) throws Exception {
		TurnDir direction;
		switch(s.toUpperCase())	{
		case "LEFT":
			direction = TurnDir.LEFT;
			break;
		case "RIGHT":
			direction = TurnDir.RIGHT;
			break;
		default:
			throw new Exception("False turn direction: " + s);
		}
		return direction;
	}
	
	/**
	 * Private method to check if the supplied sense condition is acceptable.
	 * @param s - The string containing the sense condition.
	 * @return The condition as an enum.
	 * @throws Exception - If the string doesn't match the possible directions.
	 */
	private static Condition checkCondition(String s) throws Exception{
		Condition condition;
		switch(s.toUpperCase()){
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
