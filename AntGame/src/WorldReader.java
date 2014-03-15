import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WorldReader {
	
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
            String worldSize = inputBuffer.readLine();
            int x = Integer.parseInt(worldSize.split(",")[0]);
            int y = Integer.parseInt(worldSize.split(",")[1]);
            Tile[][] grid = new Tile[x][y];
//            int c;
//            while ((c = inputBuffer.read()) != -1) {
//                output += (char)c;
//            }
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
	public String[] separateLines(String input){
		String[] stateList = input.trim().split("\n");
		return stateList;
	}
	
	/**
	 * Iterates through each string in the stateList matching it to a State token
	 * @param stateList
	 * @return An array of States
	 */
	public Tile[] createStateList(String worldLine){
		Tile[] output = new Tile[worldLine.length()];
		String[] tiles = worldLine.trim().split("");
		// For each tile in a line
		for(int i = 0; i < tiles.length; i++){
			String tile = tiles[i];
			try{
				if(tile.matches("[1-9]")){
//					output[i] = new ClearTile(NUMBER OF FOOD HERE)
				}else{
					switch(tile){
					case ".":
						//clear tile
						break;
						
					case "#":
						// rocky
						break;
						
					case "+":
						// red ant hill
						break;
						
					case "-":
						// black ant hill
						break;
						
					default:
						throw new Exception("Invalid tile");
					}
				}
			}catch(Exception e){
				System.out.println("Error on string: " + tile);
				System.out.println(e.getMessage());
			}
		}
		return output;
	}
	
	public static void main(String[] args) {
		// Testing
		WorldReader wr = new WorldReader();
		String input = "sampleant.brain";
//		State[] s =  br.createStateList(br.separateLines(br.readFromFile(input)));
//		for(int i = 0; i < s.length; i++){
//			System.out.println(s[i].getClass().getName());
//		}
	}
}
