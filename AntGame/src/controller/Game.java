package controller;

import graphics.components.Hexagon;
import graphics.screens.MainMenuPanel;
import graphics.screens.MatchPanel;
import graphics.screens.MatchResultsPanel;
import graphics.screens.SingleMatchPlayerPanel;
import graphics.screens.Screen;
import graphics.screens.TournamentResultsPanel;
import graphics.screens.TournamentSelectionPanel;
import graphics.screens.WorldEditorPanel;
import graphics.screens.SingleMatchWorldPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import javax.swing.*;

import model.AntBrainReader;
import model.Colour;
import model.Match;
import model.Player;
import model.Tournament;
import model.World;
import model.tile.AntHillTile;
import model.tile.ClearTile;
import model.tile.Tile;

/**
 * Game: The main game. Uses collection of JPanels that are switched to the front of the screen when needed.
 */
public class Game extends JFrame {

	private static final long serialVersionUID = -3977845427044676630L;
	public static int seed = 0;
	public static int count = 0;

	/**
	 * Generates a pseudo-random number between 0 (inclusive) and n (exclusive).
	 * 
	 * @param n
	 *            The upper limit.
	 * @return A pseudo-random number.
	 */
	public static int randomInt(int n) {
		for (; count < 3; count++) {
			seed = seed * 22695477 + 1;
		}
		seed = seed * 22695477 + 1;
		count++;
		int value = (int) ((Math.floor((float)seed / 65536)) % 16384);
		if (value < 0) {
			value = (value + 16384) % 16384;
		}
		int output = value % n;
		if (output < 0)
			output = (output + n) % n ;
		return output;
		
//		int value = ((seed / 65536) % 16384);
//		if (output < 0)
//			output += n - 1;
		
		//int value = ((((seed / 65536) % 16384) + 16384) % 16384);
		//return (((value % n) + n) % n);
	}

	public static void main(String[] args) {
		seed = 12345;
		String rand = "";
		String legit = "7193, 2932, 10386, 5575, 100, 15976, 430, 9740, 9449, 1636, 11030, 9848, 13965, 16051, 14483, 6708, 5184, 15931, 7014, 461, 11371, 5856, 2136, 9139, 1684, 15900, 10236, 13297, 1364, 6876, 15687, 14127, 11387, 13469, 11860, 15589, 14209, 16327, 7024, 3297, 3120, 842, 12397, 9212, 5520, 4983, 7205, 7193, 4883, 7712, 6732, 7006, 10241, 1012, 15227, 9910, 14119, 15124, 6010, 13191, 5820, 14074, 5582, 5297, 10387, 4492, 14468, 7879, 8839, 12668, 5436, 8081, 4900, 10723, 10360, 1218, 11923, 3870, 12071, 3574, 12232, 15592, 12909, 9711, 6638, 2488, 12725, 16145, 9746, 9053, 5881, 3867, 10512, 4312, 8529, 1576, 15803, 5498, 12730, 7397, ";
		for (int i = 0; i < 100; i++) {
			rand += "" + randomInt(213980471) + ", ";
		}
		System.out.println(rand.equals(legit));
		System.out.println(rand);
		System.out.println(legit);
	}

	public static final boolean GUI_DEBUG = false; // GUI debugging print statements on/off.
	public static final boolean DEBUG = false;

	private CardLayout cardLayout = new CardLayout(); // Allows JPanels to be changed in and out.
	private JPanel screens = new JPanel(cardLayout); // Holds all of the different screens.
	private static final int WIDTH = 1024;
	private static final int HEIGHT = (WIDTH / 16) * 9;

	private boolean runningMatch = false;
	private Thread modelThread;

	private int frames = 0; // for the FPS counter.

	private Timer displayTimer; // update the match screens grid
	private Timer statsTimer; // update the stats for the players.

	// Initialise all of the games screens.
	private MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
	public static final String MAIN_MENU_SCREEN = "Main Menu";

	private MatchPanel matchPanel = new MatchPanel(this);
	public static final String MATCH_SCREEN = "Match";

	private MatchResultsPanel matchResults = new MatchResultsPanel(this);
	public static final String MATCH_RESULTS_SCREEN = "Match Results";

	private SingleMatchPlayerPanel matchBrainSelection = new SingleMatchPlayerPanel(this);
	public static final String MATCH_BRAIN_SELECTION_SCREEN = "Match Brain Select";

	private TournamentResultsPanel tournamentResults = new TournamentResultsPanel(this);
	public static final String TOURNAMENT_RESULTS_SCREEN = "Tournament Results";

	private SingleMatchWorldPanel worldSelection = new SingleMatchWorldPanel(this);
	public static final String WORLD_SELECTION_SCREEN = "World Selection Screen";

	private WorldEditorPanel worldEditor = new WorldEditorPanel(this, worldSelection);
	public static final String WORLD_EDITOR_SCREEN = "World Editor Screen";

	private TournamentSelectionPanel tournamentSelection = new TournamentSelectionPanel(this);
	public static final String TOURNAMENT_SELECTION_SCREEN = "Tournament Selection Screen";

	// Stack of previous windows. (MAY NOT USE)
	Stack<String> panelHistory = new Stack<String>();

	private Tournament currentTournament = null;
	private Match currentMatch = null;
	private int roundsPerSec = 10; // Number of rounds to perform every second
	private int lastSpeed = 10;
	private double roundTime = 1000000000.0 / roundsPerSec; // number of times to run update per second
	private HashMap<String, Screen> screenMap;

	/**
	 * Constructs the Game and initialises the screens.
	 */
	public Game() {
		if (DEBUG | GUI_DEBUG) {
			currentMatch = new Match(World.generateWorld(150, 150, 7, 14, 11), new Player("BLACKP1", AntBrainReader.readBrain("cleverbrain2.brain")),
					new Player("REDP2", AntBrainReader.readBrain("cleverbrain2.brain")));
		}
		screenMap = new HashMap<String, Screen>();
		// Add all screens used within the game.
		addScreen(mainMenuPanel, MAIN_MENU_SCREEN);
		addScreen(tournamentSelection, TOURNAMENT_SELECTION_SCREEN);
		addScreen(matchBrainSelection, MATCH_BRAIN_SELECTION_SCREEN);
		addScreen(worldSelection, WORLD_SELECTION_SCREEN);
		addScreen(worldEditor, WORLD_EDITOR_SCREEN);
		addScreen(matchPanel, MATCH_SCREEN);
		addScreen(matchResults, MATCH_RESULTS_SCREEN);
		addScreen(tournamentResults, TOURNAMENT_RESULTS_SCREEN);

		// JFrame properties
		this.add(screens);
		this.setTitle("Ant Game  |  " + WIDTH + "x" + HEIGHT);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Switch the screen that's being displayed to the specified screen.
	 * 
	 * @param panelName
	 *            screen to switch in.
	 */
	public void switchScreen(String panelName) {
		screenMap.get(panelName).update();
		cardLayout.show(screens, panelName);
	}

	/**
	 * Adds the screen to the game so that it can be swapped to when needed. Adds a mapping between a screen and it's name
	 * 
	 * @param screen
	 * @param screenName
	 */
	private void addScreen(JPanel screen, String screenName) {
		screenMap.put(screenName, (Screen) screen);
		screens.add(screen, screenName);
	}

	/**
	 * Clears the existing grid and changes the HexGrid to have the specified parameters.
	 * 
	 * @param cols
	 * @param rows
	 * @param size
	 * @param strokeWidth
	 */
	public void createMatchPanelGrid(int cols, int rows, int size, int strokeWidth) {
		matchPanel.getGrid().newGrid(cols, rows, size, strokeWidth);
	}

	/**
	 * Start running the match that is currently loaded in Start updating the hexagon grid to display the match.
	 */
	public void startMatch() {
		runningMatch = true;
		runModel();
		runDisplay();
	}

	/**
	 * Stop the current match.
	 */
	public void stopMatch() {
		runningMatch = false;
		try {
			modelThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main game loop for the model.
	 */
	private void runModel() {
		modelThread = new Thread(new Runnable() {
			public void run() {
				if (Game.DEBUG)
					System.out.println("DEBUG | Game:runModel() Thread Started!");
				// int roundsPerSec = 10; // Number of rounds to perform every second

				long lastTime = System.nanoTime(); // Computer's current time (in nano seconds)
				long fpsTimer = System.currentTimeMillis();

				double modelDelta = 0.0;
				int updates = 0;

				// Game loop
				while (currentMatch.getRoundNumber() < Match.MAX_ROUNDS && runningMatch) {

					long now = System.nanoTime();
					modelDelta += (now - lastTime) / roundTime;
					lastTime = now;

					while (modelDelta >= 1) { // Happens 'roundsPerSec' times a second.
						updateModel();
						updates++;
						modelDelta--;
						if (currentMatch.getRoundNumber() >= Match.MAX_ROUNDS || !runningMatch) {
							if (Game.DEBUG)
								setTitle("Ant Game  |  " + getWidth() + "x" + getHeight() + "  |  UPDATES: " + updates + "/sec  |  ROUND: "
										+ currentMatch.getRoundNumber() + "  |  FPS: " + frames);
							break;
						}
					}

					if (System.currentTimeMillis() - fpsTimer > 1000) { // Happens once every 'roundsPerSec'
						fpsTimer += 1000;
						if (Game.DEBUG)
							setTitle("Ant Game  |  " + getWidth() + "x" + getHeight() + "  |  UPDATES: " + updates + "/sec  |  ROUND: "
									+ currentMatch.getRoundNumber() + "  |  FPS: " + frames);
						frames = 0; // reset number of frames per sec.
						updates = 0; // reset number of updates per sec.
					}
				}
				if (currentMatch.getRoundNumber() >= Match.MAX_ROUNDS) {
					runningMatch = false;
					switchScreen(Game.MATCH_RESULTS_SCREEN);
				}
			}
		});
		modelThread.start(); // Start the match thread.
		System.gc();
	}

	/**
	 * The main game loop for the graphics.
	 * 
	 * Starts updating the hexagon grid to display the current state of the current match.
	 */
	private void runDisplay() {
		int maxFps = 60;
		displayTimer = new Timer(1000 / maxFps, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!runningMatch) {
					displayTimer.stop();
				} else {
					frames++;
					updateMatchScreen();
				}
			}
		});
		displayTimer.start(); // Start updating the screen periodically.
		if (Game.DEBUG)
			System.out.println("DEBUG | Game:runDisplay() displayTimer started!");

		// Periodically update the player stats.
		statsTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!runningMatch) {
					displayTimer.stop();
				} else {
					getMatchScreen().updatePlayerStats();
				}
			}
		});
		statsTimer.start(); // Start updating the screen periodically.
		if (Game.DEBUG)
			System.out.println("DEBUG | Game:runDisplay() statsTimer started!");
	}

	/**
	 * Updates the hexagon grid to be the current state of the current match.
	 */
	private void updateMatchScreen() {
		Hexagon[][] gridBuffer = matchPanel.getGrid().getHexagonGrid().clone();

		World drawnWorld = currentMatch.getWorld();
		HashSet<Integer> changes = drawnWorld.getChanges();
		drawnWorld.resetChanges(); // Comment out to redraw everything.
		Integer[] tileIDs = changes.toArray(new Integer[0]);
		for (int i = 0; i < tileIDs.length; i++) {
			int currentID = tileIDs[i];
			int x = ((currentID % drawnWorld.sizeX) + drawnWorld.sizeX) % drawnWorld.sizeX;
			int y = currentID / drawnWorld.sizeX;
			Tile tile = drawnWorld.getTile(x, y);
			setTileColor(gridBuffer[x][y], tile);
		}
		// for(int i = 0; i < drawnWorld.sizeX; i++){ // Leave this in for debugging
		// for(int j = 0; j < drawnWorld.sizeY; j++){
		// gridBuffer[i][j].setFillColor(getTileColor(drawnWorld.getTile(i, j)));
		// }
		// }

		matchPanel.getGrid().setHexagonGrid(gridBuffer);
		matchPanel.getScrollPane().repaint();
	}

	/**
	 * Progresses the model.
	 */
	private void updateModel() {
		currentMatch.nextRound();
	}

	/**
	 * modifies the given hexTile relative to the given normal tile to represent data about the tile such as is it a RockTile or AntHillTile or is there food in
	 * the tile.
	 * 
	 * @param hexTile
	 *            The tile to be modified.
	 * @param tile
	 *            The tile that needs to be represented.
	 */
	public static void setTileColor(Hexagon hexTile, Tile tile) {
		Color color = null;
		if (tile.isRocky()) {
			color = Hexagon.ROCK_COLOR;
		} else {
			if (((ClearTile) tile).hasAnt()) {
				if (((ClearTile) tile).getAnt().getColour().equals(Colour.RED)) {
					color = Hexagon.RED_ANT_COLOR;
				} else {
					color = Hexagon.BLACK_ANT_COLOR;
				}
			} else {
				int food = ((ClearTile) tile).getFood();
				if (food > 0) {
					color = Hexagon.FOOD_COLOR;
					// color = Hexagon.EMPTY_CELL_COLOR;
					// hexTile.setOutlineColor(Hexagon.FOOD_COLOR);
					// hexTile.setStrokeWidth(5);
				} else {
					// hexTile.setStrokeWidth(1);
					// hexTile.setOutlineColor(Color.BLACK);
					if (((ClearTile) tile).isAntHill()) {
						if (((AntHillTile) tile).getColour().equals(Colour.RED)) {
							color = Hexagon.RED_ANTHILL_COLOR;
						} else {
							color = Hexagon.BLACK_ANTHILL_COLOR;
						}
					} else {
						color = Hexagon.EMPTY_CELL_COLOR;
					}
				}
			}
		}
		hexTile.setFillColor(color);
	}

	/**
	 * Returns the screen displaying the current match.
	 * 
	 * @return the screen displaying the current match.
	 */
	private MatchPanel getMatchScreen() {
		return this.matchPanel;
	}

	/**
	 * Sets the number of rounds that the game will execute per second.
	 */
	public void setRoundsPerSecond(int value) {
		roundsPerSec = value;
		roundTime = 1000000000.0 / roundsPerSec;
	}

	/**
	 * Returns the number of rounds that the game will execute per second.
	 * 
	 * @return the number of rounds that the game will execute per second.
	 */
	public int getRoundsPerSecond() {
		return roundsPerSec;
	}

	/**
	 * Returns the current match that the game is storing.
	 * 
	 * @return the current match that the game is storing.
	 */
	public Match getCurrentMatch() {
		return this.currentMatch;
	}

	/**
	 * Returns the current match that the game is storing.
	 * 
	 * @return the current match that the game is storing.
	 */
	public void setCurrentMatch(Match match) {
		this.currentMatch = match;
	}

	/**
	 * Sets the current match to a new blank match, ready for its fields to be set.
	 */
	public void createMatch() {
		this.currentMatch = new Match();
	}

	public void setCurrentTournament(Tournament tournament) {
		this.currentTournament = tournament;
	}

	/**
	 * Returns the current match that the game is storing.
	 * 
	 * @return the current match that the game is storing.
	 */
	public Tournament getCurrentTournament() {
		return this.currentTournament;
	}

	/**
	 * Sets the current match to a new blank match, ready for its fields to be set.
	 */
	public void createTournament() {
		this.currentTournament = new Tournament();
	}

	/**
	 * Pauses the model updating if it is running and saves the current speed. If the game is not running, it sets the games speed to the previous speed.
	 */
	public void togglePause() {
		if (getRoundsPerSecond() == 0) {
			setRoundsPerSecond(lastSpeed);
		} else {
			lastSpeed = getRoundsPerSecond();
			setRoundsPerSecond(0);
		}
	}

	/**
	 * Calls the reset method on each screen.
	 */
	public void resetScreens() {
		String[] screens = screenMap.keySet().toArray(new String[0]);
		for (int i = 0; i < screens.length; i++) {
			if (Game.DEBUG) {
				System.out.println("DEBUG | Resetting screen: " + screens[i]);
			}
			screenMap.get(screens[i]).reset();
		}
	}

	/**
	 * Is the game not updating the model.
	 * 
	 * @return True if the game is paused.
	 */
	public boolean isPaused() {
		return roundsPerSec == 0;
	}
}
