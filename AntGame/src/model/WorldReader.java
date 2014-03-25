package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class WorldReader {
	
	int x; // x dimension
	int y; // y dimension
	
	/**
	 * Load world from file
	 * @param path - The filepath of the file.
	 * @return The contents of the file as a continuous string.
	 */
	public String readFromFile(String path){
		String output = "";
		BufferedReader inputBuffer = null;

        try {
            inputBuffer = new BufferedReader(new FileReader(path));
            x = Integer.parseInt(inputBuffer.readLine()); // Read the first line and set its value as the x value
            y = Integer.parseInt(inputBuffer.readLine()); // Read the first line and set its value as the y value
            String line = null;
            while ((line = inputBuffer.readLine()) != null) {
                output += line + "$"; //use $ as a line separator to be used later for semantic analysis 
            }

        }catch(Exception e){
        	System.out.println(e.getMessage());
        }finally {
        
            if (inputBuffer != null) {
                try {
					inputBuffer.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
            }
        }
        output = output.replaceAll("\\s+",""); //simply remove any whitespace that might have been read in
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
	public Tile[] createTileList(String worldLine){
		Tile[] output = new Tile[worldLine.length()];
		String[] tiles = new String[worldLine.length()];
		//have to use for each as split() creates on undesired character at the start
		for(int i = 0; i < tiles.length; i++){
			tiles[i] = "" + worldLine.charAt(i);
		}
		// For each tile in a line
		// Start at 1, as the trim() method creates an invisible character at the start of the string
		for(int i = 0; i < tiles.length; i++){
			String tile = tiles[i];
			try{
				if(tile.matches("[1-9]")){
					output[i] = new ClearTile(Integer.parseInt(tile)); // Simply parse the int contained in the String
				}else{
					switch(tile){
					case ".":
							output[i] = new ClearTile();
						break;
						
					case "#":
							output[i] = new RockTile();
						break;
						
					case "+":
							output[i] = new AntHillTile(Colour.RED);
						break;
						
					case "-":
							output[i] = new AntHillTile(Colour.BLACK);
						break;
					
					case "$":
							output[i] = new LineSeparator();
						break;
					
					default:
						throw new Exception("Invalid tile");
					}
				}
			}catch(Exception e){
				System.err.println("Error on string: " + tile);
				System.err.println(e.getMessage());
			}
		}
		return output;
	}
	
	/**
	 * Goes through the ant-world tiles and checks if it conforms to the specified x and y dimensions
	 * @param tiles
	 * @return True if the map is semantically correct, false otherwise
	 */
	public boolean checkWorldSemantics(Tile[] tiles){
		int countx = 0,county = 0,i = 0;
		boolean isCorrect = true;
		while(isCorrect && i < tiles.length){
			if(!(tiles[i] instanceof LineSeparator)){
				countx++;
			}else{
				//if there are x tiles in a line
				if(countx == x){
					countx = 0;
					county++; //increment the count of y dimension
				}else{
					isCorrect = false; //not a semantically correct world
				}
			}
			i++;
		}
		//check if the the y dimensions of the world are correct
		if(county != y){isCorrect = false;}
		return isCorrect;
	}
	
	/**
	 * This method is to be used post-parsing. It simply removes LineSeparator tokens, as they are no longer needed post-parsing
	 * @param tiles
	 * @return input array without LineSeparators
	 */
	public Tile[] removeLineSeparators(Tile[] tiles){
		Tile[] output = new Tile[tiles.length - countLineSeparators(tiles)];
		int outputPointer = 0;
		for(int i=0; i < tiles.length;i++){
			if(!(tiles[i] instanceof LineSeparator)){
				output[outputPointer++] = tiles[i];
			}
		}
		return output;
	}
	
	/**
	 * This method simply counts the number of line Separators in an array of tiles
	 * @param tiles
	 * @return number of line separators
	 */
	private int countLineSeparators(Tile[] tiles){
		int i = 0;
		for(Tile t: tiles){
			if(t instanceof LineSeparator){i++;}
		}
		return i;
	}
	
	/**
	 * Simply parses an inputted world file. Returns null if the file is not valid, returns a Tile[] otherwise.
	 * @return
	 */
	public Tile[] parse(String path){
		String map = this.readFromFile(path);
		Tile[] tiles = this.createTileList(map);
		boolean correct = this.checkWorldSemantics(tiles);
		Tile[] tmp = this.removeLineSeparators(tiles);
		if(correct){
			return tmp;
		}
		else{
			return null;
		}
	}
	public static void main(String[] args) {
		// Testing
		WorldReader wr = new WorldReader();
		Tile[] tiles = wr.parse("//smbhome.uscs.susx.ac.uk/gcp20/Desktop/Software Engineering/test.txt");
		if(tiles == null){
			System.out.println("Not a valid world map");
		}
		else{
			System.out.println("Valid world map");
		}
	}
}
