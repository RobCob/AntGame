import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BrainReader {
	
	/**
	 * Load ant brain from file
	 * @param path - The filepath of the file.
	 * @return The contents of the file as a continuous string.
	 */
	public String readFromFile(String path){
		String output = "";
		FileReader inputStream = null;

        try {
            inputStream = new FileReader(path);

            int c;
            while ((c = inputStream.read()) != -1) {
                output += (char)c;
            }
        }catch(Exception e){
        	System.out.println(e.getMessage());
        }finally {
        
            if (inputStream != null) {
                try {
					inputStream.close();
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
	public String[] separateLines(String input){
		String[] stateList = input.trim().split("\n");
		return stateList;
	}
	
	/**
	 * Iterates through each string in the stateList matching it to a State token
	 * @param stateList
	 * @return An array of States
	 */
	public State[] createStateList(String[] stateList){
		State[] output = new State[stateList.length];
		// For each line
		for(int i = 0; i<stateList.length;i++) {
			// Trim input sentence and then split into 'words' (strings separated by whitespace)
			//TODO: Decide how strict input checking should be. Should I trim or should it cause an error? eg " Drop 2 "
			String[] currentState = stateList[i].trim().split(" ");
			// Try matching the first word in the line to each of the instructions. 
			// Check the appropriate words and convert them to ints/enums, throwing exceptions on each error.
			// If there are no errors, create appropriate state and add it to the output list of states.
			// Catch all exceptions thrown.
			try{
				switch(currentState[0]){
				case "Sense":
					SenseDir direction = checkSenseDirection(currentState[1]);
					int st1 = checkState(currentState[2]);
					int st2 = checkState(currentState[3]);
					Condition condition = checkCondition(currentState[4]);
					// Extra variable needed if the state is sensing for a Marker
					if(condition == Condition.Marker){
						int scent = checkMark(currentState[5]);						
						output[i] = new Sense(direction, st1, st2, condition, scent);
					}else{
						output[i] = new Sense(direction, st1, st2, condition);
					}
					continue;
					
				case "Mark":
						int scent = checkMark(currentState[1]);
						st1 = checkState(currentState[2]);
						output[i] = new Mark(scent, st1);
					continue;
					
				case "Unmark":
						scent = checkMark(currentState[1]);
						st1 = checkState(currentState[2]);
						output[i] = new Mark(scent, st1);
					continue;
					
				case "PickUp":
						st1 = checkState(currentState[1]);
						st2 = checkState(currentState[2]);
						output[i] = new PickUp(st1, st2);
					continue;
					
				case "Drop":
						st1 = checkState(currentState[1]);
						output[i] = new Drop(st1);
					continue;
					
				case "Turn":
						TurnDir turnDirection = checkTurnDirection(currentState[1]);
						st1 = checkState(currentState[2]);
						output[i] = new Turn(turnDirection, st1);
					continue;
					
				case "Move":
						st1 = checkState(currentState[1]);
						st2 = checkState(currentState[2]);
						output[i] = new Move(st1, st2);
					continue;
					
				case "Flip":
						int p = Integer.parseInt(currentState[1]);
						st1 = checkState(currentState[2]);
						st2 = checkState(currentState[3]);
						output[i] = new Flip(p, st1, st2);
					continue;
					
				default:
					throw new Exception("Invalid state");
					
				}
			}catch(Exception e){
				System.out.println("Error on string: " + stateList[i] +"\nLine number " + i);
				System.out.println(e.getMessage());
			}
		}
		return output;
	}
	
	/**
	 * Private method to check if the supplied state is acceptable.
	 * @param s - The string containing the state.
	 * @return The state as an int.
	 * @throws Exception - If Integer.parseInt(String) fails or if the state is not a value from 0 to 999.
	 */
	private int checkState(String s) throws Exception{
		int state = Integer.parseInt(s);
		if((state < 0)||state > 999){
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
	private int checkMark(String s) throws Exception{
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
	private SenseDir checkSenseDirection(String s) throws Exception{
		SenseDir direction;
		switch(s)	{
		case "Here":
			direction = SenseDir.Here;
			break;
		case "Ahead":
			direction = SenseDir.Ahead;
			break;
		case "LeftAhead":
			direction = SenseDir.LeftAhead;
			break;
		case "RightAhead":
			direction = SenseDir.RightAhead;
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
	private TurnDir checkTurnDirection(String s) throws Exception {
		TurnDir direction;
		switch(s)	{
		case "Left":
			direction = TurnDir.Left;
			break;
		case "Right":
			direction = TurnDir.Right;
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
	private Condition checkCondition(String s) throws Exception{
		Condition condition;
		switch(s){
		case "Friend":
			condition = Condition.Friend;
			break;
		case "Foe":
			condition = Condition.Foe;
			break;
		case "FriendWithFood":
			condition = Condition.FriendWithFood;
			break;
		case "FoeWithFood":
			condition = Condition.FoeWithFood;
			break;
		case "Food":
			condition = Condition.Food;
			break;
		case "Rock":
			condition = Condition.Rock;
			break;
		case "Marker":
			condition = Condition.Marker;
			break;
		case "FoeMarker":
			condition = Condition.FoeMarker;
			break;
		case "Home":
			condition = Condition.Home;
			break;
		case "FoeHome":
			condition = Condition.FoeHome;
			break;
		default:
			throw new Exception("False Condition: " + s);
		}
		return condition;
	}
	
	public static void main(String[] args) {
		BrainReader br = new BrainReader();
		String input = "sampleant.brain";
		State[] s =  br.createStateList(br.separateLines(br.readFromFile(input)));
		for(int i = 0; i < s.length; i++){
			System.out.println(s[i].getClass().getName());
		}
	}
}
