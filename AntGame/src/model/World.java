package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import controller.Game;
import model.tile.AntHillTile;
import model.tile.ClearTile;
import model.tile.RockTile;
import model.tile.Tile;

/**
 * A class to represent the ant world in which the game is simulated. This class makes use of tile IDs, an ID is equal to the number of rows (y coordinate)
 * multipled by the maximum X coordinate (sizeX) and then added to the number of columns (x coordinate).
 * 
 * @author 108069
 * 
 */
public class World {

	private Tile[][] grid;
	private HashSet<Integer> changes;
	private HashMap<Integer, Ant> ants;
	private ArrayList<AntHillTile> antHills;
	public final int sizeX, sizeY;

	/**
	 * Generates a valid World with the given parameters.
	 * 
	 * @param sizeX
	 *            The number of columns in the returned World.
	 * @param sizeY
	 *            The number of rows in the returned World.
	 * @param antHillSize
	 *            The length of each side of the generated ant hills.
	 * @param rockCount
	 *            The number of individual rocks generated in the world.
	 * @param foodPileCount
	 *            The number of 5x5 food rectangles generated in the world, with 5 food in each tile.
	 * @return The generated World.
	 */
	public static World generateWorld(int sizeX, int sizeY, int antHillSize, int rockCount, int foodPileCount) {
		Tile[][] grid = new Tile[sizeX][sizeY];
		// Fill grid with clear tiles
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				grid[i][j] = new ClearTile();
			}
		}
		// Fill top and bottom rock boarders
		for (int i = 0; i < sizeX; i++) {
			grid[i][0] = new RockTile();
			grid[i][sizeY - 1] = new RockTile();
		}
		// Fill left and right rock boarders
		for (int i = 0; i < sizeY; i++) {
			grid[0][i] = new RockTile();
			grid[sizeX - 1][i] = new RockTile();
		}
		// create Ant hills
		int count = 0;
		boolean redAntHillPlaced = false;
		boolean blackAntHillPlaced = false;
		while (!redAntHillPlaced || !blackAntHillPlaced) {
			ArrayList<Integer> tiles = new ArrayList<Integer>();
			int randX = 1 + Game.randomInt(sizeX - 2);
			int randY = 1 + Game.randomInt(sizeY - 2);
			boolean obstructed = false;
			if (grid[randX][randY].isRocky() || ((ClearTile) grid[randX][randY]).isAntHill()) {
				obstructed = true;
			}
			tiles.add((randY * sizeX) + randX);
			for (int i = 1; i <= antHillSize & !obstructed; i++) {
				int currentID = randY * sizeX + randX + i;
				for (int j = 0; j < 6 & !obstructed; j++) {
					for (int k = 0; k < i & !obstructed; k++) {
						int x = ((currentID % sizeX) + sizeX) % sizeX;
						int y = currentID / sizeX;
						if (grid[x][y].isRocky() || ((ClearTile) grid[x][y]).isAntHill()) {
							obstructed = true;
						}
						if (i < antHillSize) {
							tiles.add(currentID);
						}
						currentID = getAhead((((j + 2) % 6) + 6) % 6, currentID, sizeX);
					}
				}
			}
			if (!obstructed) {
				if (!redAntHillPlaced) {
					for (int i = 0; i < tiles.size(); i++) {
						int x = ((tiles.get(i) % sizeX) + sizeX) % sizeX;
						int y = tiles.get(i) / sizeX;
						grid[x][y] = new AntHillTile(Colour.RED);
					}
					redAntHillPlaced = true;
				} else {
					if (!blackAntHillPlaced) {
						for (int i = 0; i < tiles.size(); i++) {
							int x = ((tiles.get(i) % sizeX) + sizeX) % sizeX;
							int y = tiles.get(i) / sizeX;
							grid[x][y] = new AntHillTile(Colour.BLACK);
						}
					}
					blackAntHillPlaced = true;
				}
			} else {
				count++;
			}
			if (count > 1000) {
				break;
			}
		}
		// Place rocks
		int placedRocks = 0;
		while (placedRocks < rockCount) {
			int randX = 1 + Game.randomInt(sizeX - 2);
			int randY = 1 + Game.randomInt(sizeY - 2);
			int currentID = randY * sizeX + randX;
			boolean obstructed = false;
			for (int i = 0; i < 6 && !obstructed; i++) {
				int newTile = getAhead(i, currentID, sizeX);
				int x = ((newTile % sizeX) + sizeX) % sizeX;
				int y = newTile / sizeX;
				if (grid[x][y].isRocky() || ((ClearTile) grid[x][y]).isAntHill()) {
					obstructed = true;
				}
			}
			if (!obstructed) {
				grid[randX][randY] = new RockTile();
				placedRocks++;
			} else {
				count++;
			}
			if (count > 1000) {
				break;
			}
		}
		// Place food
		int foodSize = 3;
		int placedFood = 0;
		while (placedFood < foodPileCount) {
			ArrayList<Integer> tiles = new ArrayList<Integer>();
			int randX = 1 + Game.randomInt(sizeX - 2);
			int randY = 1 + Game.randomInt(sizeY - 2);
			int randOrientation = Game.randomInt(2);
			boolean obstructed = false;
			int startID = randY * sizeX + randX;
			if (grid[randX][randY].isRocky() || ((ClearTile) grid[randX][randY]).isAntHill()) {
				obstructed = true;
			} else {
				tiles.add(startID);
			}
			for (int i = 1; i <= foodSize & !obstructed; i++) {
				startID = getAhead(4 + randOrientation, startID, sizeX);
				int currentID = startID;
				int increment = 1;
				for (int j = 0; j < 6 & !obstructed; j += increment) {
					increment = (increment % 2) + 1;
					for (int k = 0; k < i * 2 & !obstructed; k++) {
						int x = ((currentID % sizeX) + sizeX) % sizeX;
						int y = currentID / sizeX;
						if (grid[x][y].isRocky() || ((ClearTile) grid[x][y]).isAntHill()) {
							obstructed = true;
						}
						if (i < foodSize) {
							tiles.add(currentID);
						}
						currentID = getAhead((((j + randOrientation) % 6) + 6) % 6, currentID, sizeX);
					}
				}
			}
			if (!obstructed) {
				for (int i = 0; i < tiles.size(); i++) {
					int x = ((tiles.get(i) % sizeX) + sizeX) % sizeX;
					int y = tiles.get(i) / sizeX;
					((ClearTile) grid[x][y]).setFood(((ClearTile) grid[x][y]).getFood() + 5);
				}
				placedFood++;
			} else {
				count++;
			}
			if (count > 1000) {
				break;
			}
		}
		if (!redAntHillPlaced || !blackAntHillPlaced || (placedRocks < rockCount) || (placedFood < foodPileCount)) {
			return null;
		} else {
			return new World(grid);
		}
	}

	/**
	 * Constructs a World from the given 2d Array of Tile objects.
	 * 
	 * @param grid
	 *            The 2d Array.
	 */
	public World(Tile[][] grid) {
		this.grid = grid;
		sizeX = grid.length;
		sizeY = grid[0].length;
		antHills = new ArrayList<AntHillTile>();
		ants = new HashMap<Integer, Ant>();
		changes = new HashSet<Integer>();
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				setChange((y * sizeX) + x); // Disable for fog of war
			}
		}
	}

	/**
	 * Goes through the entire world and places an Ant on each AntHillTile with the respective Colour.
	 * 
	 * @param player1
	 *            The player used to determine which ant brain is used for the ants of one Colour.
	 * @param player2
	 *            The player used to determine which ant brain is used for the ants of the other Colour
	 */
	public void populate(Player player1, Player player2) {
		int idCounter = 0;
		for (int y = 0; y < sizeY; y++) {
			for (int x = 0; x < sizeX; x++) {
				Tile tile = getTile(x, y);
				if (!tile.isRocky() && ((ClearTile) tile).isAntHill()) {
					AntHillTile aHill = (AntHillTile) tile;
					antHills.add(aHill);
					if (aHill.getColour().equals(player1.getColour())) {
						Ant ant = new Ant(player1, idCounter);
						ant.setX(x);
						ant.setY(y);
						aHill.setAnt(ant);
						ants.put(ant.getID(), ant);
					} else {
						Ant ant = new Ant(player2, idCounter);
						ant.setX(x);
						ant.setY(y);
						aHill.setAnt(ant);
						ants.put(ant.getID(), ant);
					}
					idCounter++;
				}
			}
		}
		if (Game.DEBUG) {
			System.out.println("DEBUG | " + ants.size() + " Ants Added to world");
		}
	}

	/**
	 * Returns a HashSet of tile IDs of tiles that have changed
	 * 
	 * @return A HashSet of tile IDs (Integers).
	 */
	public HashSet<Integer> getChanges() {
		return changes;
	}

	/**
	 * Adds the tileID to the set of changes.
	 * 
	 * @param tileID
	 *            The tileID that is now marked as changed.
	 * @return True if it was already in the set, False if otherwise.
	 */
	public boolean setChange(int tileID) {
		return changes.add(tileID);
	}

	/**
	 * Clears the set of changes. Used after the changes have been processed.
	 */
	public void resetChanges() {
		changes = new HashSet<Integer>();
	}

	/**
	 * Triggers updates in the given tile and the tiles surrounding the given tile ID
	 * 
	 * @param tileID
	 *            The tile which is triggering updates.
	 */
	public void triggerUpdates(int tileID) {
		update(tileID);
		for (int i = 0; i < Ant.POSSIBLE_DIRECTIONS; i++) {
			update(getAhead(i, tileID, sizeX));
		}
	}

	/**
	 * Checks if the tile can be updated for events such as ant death.
	 * 
	 * @param tileID
	 *            The ID of the tile to be updated.
	 */
	private void update(int tileID) {
		// changes.add(tileID); // Enable for fog of war
		int x1 = ((tileID % sizeX) + sizeX) % sizeX;
		int y1 = tileID / sizeX;
		Tile currentTile = getTile(x1, y1);
		if ((!currentTile.isRocky()) && ((ClearTile) currentTile).hasAnt()) {
			int antCount = 0;
			for (int i = 0; i < Ant.POSSIBLE_DIRECTIONS; i++) {
				int newTileID = getAhead(i, tileID, sizeX);
				int x2 = ((newTileID % sizeX) + sizeX) % sizeX;
				int y2 = newTileID / sizeX;
				Tile newTile = getTile(x2, y2);
				if ((!newTile.isRocky()) && ((ClearTile) newTile).hasAnt()
						&& (!((ClearTile) newTile).getAnt().getColour().equals(((ClearTile) currentTile).getAnt().getColour()))) {
					antCount++;
				}
			}
			if (antCount >= 5) {
				if (Game.DEBUG) {
					System.out.println("DEBUG | ANT DEATH: " + ((ClearTile) currentTile).getAnt().getColour() + " AntDied "
							+ ((ClearTile) currentTile).getAnt().getID());
				}
				ants.remove(((ClearTile) currentTile).getAnt().getID());
				setChange(tileID);
				((ClearTile) currentTile).removeAnt();
				((ClearTile) currentTile).setFood(3);
			}
		}
	}

	/**
	 * Replaces the tile at the coordinate with the given tile.
	 * 
	 * @param x
	 *            The X coordinate.
	 * @param y
	 *            The Y coordinate.
	 * @param tile
	 *            The new Tile.
	 */
	public void setTile(int x, int y, Tile tile) {
		grid[x][y] = tile;
	}

	/**
	 * Gets the tile at the coordinate.
	 * 
	 * @param x
	 *            The X coordinate.
	 * @param y
	 *            The Y coordinate.
	 * @return The tile at that coordinate.
	 */
	public Tile getTile(int x, int y) {
		return grid[x][y];
	}

	/**
	 * Gets a HashMap of Ants mapping from AntID to Ant.
	 * 
	 * @return A HashMap of Ants.
	 */
	public HashMap<Integer, Ant> getAnts() {
		return ants;
	}

	/**
	 * Gets an ArrayList of all AntHillTiles in the World.
	 * 
	 * @return An ArrayList of AntHillTile objects.
	 */
	public ArrayList<AntHillTile> getAntHills() {
		return antHills;
	}

	/**
	 * Gets the tile neighboring to the given tile in the specified direction.
	 * 
	 * @param direction
	 *            The direction from the original tile in which to get.
	 * @param currentTile
	 *            The original tile ID
	 * @param sizeX
	 *            The maximum X coordinate of the world.
	 * @return The tile ID of the required neighbor.
	 */
	public static int getAhead(int direction, int currentTile, int sizeX) {
		int x = ((currentTile % sizeX) + sizeX) % sizeX;
		int y = currentTile / sizeX;
		switch (direction) {
		case 0:
			x = x + 1;
			break;
		case 1:
			if (y % 2 == 0) {
				y = y + 1;
			} else {
				x = x + 1;
				y = y + 1;
			}
			break;
		case 2:
			if (y % 2 == 0) {
				x = x - 1;
				y = y + 1;
			} else {
				y = y + 1;
			}
			break;
		case 3:
			x = x - 1;
			break;
		case 4:
			if (y % 2 == 0) {
				x = x - 1;
				y = y - 1;
			} else {
				y = y - 1;
			}
			break;
		case 5:
			if (y % 2 == 0) {
				y = y - 1;
			} else {
				x = x + 1;
				y = y - 1;
			}
			break;
		default:
			x = 0;
			y = 0;
			break;
		}
		return (y * sizeX) + x;
	}

	@Override
	protected Object clone() {
		Tile[][] newGrid = new Tile[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				newGrid[i][j] = (Tile) grid[i][j].clone();
			}
		}
		World output = new World(newGrid);
		return output;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((antHills == null) ? 0 : antHills.hashCode());
		result = prime * result + ((ants == null) ? 0 : ants.hashCode());
		result = prime * result + ((changes == null) ? 0 : changes.hashCode());
		result = prime * result + Arrays.hashCode(grid);
		result = prime * result + sizeX;
		result = prime * result + sizeY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		World other = (World) obj;
		if (antHills == null) {
			if (other.antHills != null)
				return false;
		} else if (!antHills.equals(other.antHills))
			return false;
		if (ants == null) {
			if (other.ants != null)
				return false;
		} else if (!ants.equals(other.ants))
			return false;
		if (changes == null) {
			if (other.changes != null)
				return false;
		} else if (!changes.equals(other.changes))
			return false;
		if (!Arrays.deepEquals(grid, other.grid))
			return false;
		if (sizeX != other.sizeX)
			return false;
		if (sizeY != other.sizeY)
			return false;
		return true;
	}
}
