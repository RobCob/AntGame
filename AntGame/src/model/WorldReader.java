package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import controller.Game;
import model.tile.AntHillTile;
import model.tile.ClearTile;
import model.tile.LineSeparator;
import model.tile.RockTile;
import model.tile.Tile;

/**
 * A file reader used to read and parse World files, converting them into World objects.
 * 
 * @author 109195
 * 
 */
public class WorldReader {

	/**
	 * Reads the given file and returns the World in the file.
	 * 
	 * @param file
	 * @return The World if it is valid, and null if it is not.
	 */
	public static World readWorld(File file) {
		try {
			String[] map = readFromFile(file);
			int sizeX = Integer.parseInt(map[0]);
			int sizeY = Integer.parseInt(map[1]);
			String mapString = map[2];
			Tile[] tiles = createTileList(mapString);
			if (Game.DEBUG) {
				System.out.println("DEBUG | World Tile List Created.");
			}
			boolean correct = checkWorldSemantics(tiles, sizeX, sizeY);
			if (Game.DEBUG) {
				System.out.println("DEBUG | World Semantics Valid.");
			}
			Tile[] tmp = removeLineSeparators(tiles);
			Tile[][] toReturn = convertTileTo2DArray(tmp, sizeX, sizeY);
			if (Game.DEBUG) {
				System.out.println("DEBUG | World Conversion Complete.");
			}
			if (correct) {
				return new World(toReturn);
			} else {
			}
		} catch (Exception e) {
			if (Game.DEBUG) {
				System.out.println("DEBUG | World Parsing failed." + e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Reads the given file from filepath and returns the World in the file.
	 * 
	 * @param filePath
	 *            The path of the file to be loaded
	 * @return The World if it is valid, and null if it is not.
	 */
	public static World readWorld(String filePath) {
		File file = new File(filePath);
		return readWorld(file);
	}

	/**
	 * Load world as a String from file
	 * 
	 * @param path
	 *            The filepath of the file.
	 * @return The contents of the file as a continuous string with new lines replaced with the line separator character.
	 */
	private static String[] readFromFile(File file) {
		String mapOutput = "";
		String[] output = new String[3];
		BufferedReader inputBuffer = null;

		try {
			inputBuffer = new BufferedReader(new FileReader(file));
			String line = null;
			output[0] = "" + Integer.parseInt(inputBuffer.readLine()); // Read the first line and set its value as the x value
			output[1] = "" + Integer.parseInt(inputBuffer.readLine()); // Read the first line and set its value as the y value
			while ((line = inputBuffer.readLine()) != null) {
				mapOutput += line + "$"; // use $ as a line separator to be used later for semantic analysis
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			if (inputBuffer != null) {
				try {
					inputBuffer.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		output[2] = mapOutput.replaceAll("\\s+", ""); // simply remove any whitespace that might have been read in
		return output;
	}

	/**
	 * Iterates through each character in the world String.
	 * 
	 * @param worldString
	 *            The entire world as a String.
	 * @return An array of Tiles
	 * @throws Exception 
	 */
	private static Tile[] createTileList(String worldString) throws Exception {
		Tile[] output = new Tile[worldString.length()];
		String[] tiles = new String[worldString.length()];
		// have to use for each as split() creates on undesired character at the start
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = "" + worldString.charAt(i);
		}
		// For each tile in a line
		// Start at 1, as the trim() method creates an invisible character at the start of the string
		for (int i = 0; i < tiles.length; i++) {
			String tile = tiles[i];
			if (tile.matches("[1-9]")) {
				output[i] = new ClearTile(Integer.parseInt(tile)); // Simply parse the int contained in the String
			} else {
				switch (tile) {
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
		}
		return output;
	}

	/**
	 * Goes through the ant-world tiles and checks if it conforms to the specified x and y dimensions and has a solid rock boarder.
	 * 
	 * @param tiles
	 * @return True if the map is semantically correct, false otherwise
	 */
	private static boolean checkWorldSemantics(Tile[] tiles, int sizeX, int sizeY) {
		int countx = 0, county = 0, i = 0;
		int redCount = 0;
		int blackCount = 0;
		boolean isCorrect = true;
		while (isCorrect && i < tiles.length) {
			if (!(tiles[i] instanceof LineSeparator)) {
				if (countx == 0 || county == 0 || countx == sizeX - 1 || county == sizeY - 1) {
					if (!(tiles[i].isRocky())) {
						isCorrect = false;
					}
				}
				if(tiles[i] instanceof AntHillTile){
					if(((AntHillTile)tiles[i]).getColour().equals(Colour.RED)){
						redCount++;
					}else{
						blackCount++;
					}
				}
				countx++;
			} else {
				// if there are x tiles in a line
				if (countx == sizeX) {
					countx = 0;
					county++; // increment the count of y dimension
				} else {
					isCorrect = false; // not a semantically correct world
				}
			}
			i++;
		}
		// check if the the y dimensions of the world are correct
		if (county != sizeY || redCount != blackCount) {
			isCorrect = false;
		}
		return isCorrect;
	}

	/**
	 * This method is to be used post-parsing. It simply removes LineSeparator tokens, as they are no longer needed post-parsing.
	 * 
	 * @param tiles
	 * @return input array without LineSeparators
	 */
	private static Tile[] removeLineSeparators(Tile[] tiles) {
		Tile[] output = new Tile[tiles.length - countLineSeparators(tiles)];
		int outputPointer = 0;
		for (int i = 0; i < tiles.length; i++) {
			if (!(tiles[i] instanceof LineSeparator)) {
				output[outputPointer++] = tiles[i];
			}
		}
		return output;
	}

	/**
	 * This method simply counts the number of line Separators in an array of tiles
	 * 
	 * @param tiles
	 * @return number of line separators
	 */
	private static int countLineSeparators(Tile[] tiles) {
		int i = 0;
		for (Tile t : tiles) {
			if (t instanceof LineSeparator) {
				i++;
			}
		}
		return i;
	}

	/**
	 * Just converts a 1-D array to a 2-D array. Throws an exception if the tile is not a valid size.
	 * 
	 * @param tile
	 * @return 2-D array representing the tiles for the world map.
	 * @throws IllegalArgumentException
	 */
	private static Tile[][] convertTileTo2DArray(Tile[] tile, int sizeX, int sizeY) throws IllegalArgumentException {
		if (tile.length != sizeX * sizeY) {
			throw new IllegalArgumentException("Not a valid array size");
		} else {
			Tile[][] toReturn = new Tile[sizeX][sizeY];
			for (int i = 0; i < sizeX; i++) {
				for (int j = 0; j < sizeY; j++) {
					toReturn[i][j] = tile[(j * sizeX) + i];
				}
			}
			return toReturn;
		}
	}

	public static void main(String[] args) {
		// Testing
		World world = readWorld("a.world");
		if (world == null) {
			System.out.println("Not a valid world map");
		} else {
			System.out.println("Valid world map");
			System.out.println("X size: " + world.sizeX);
			System.out.println("Y size: " + world.sizeY);
		}
	}
}
