package model;

import graphics.components.Hexagon;
import graphics.screens.MainMenuPanel;
import graphics.screens.MatchPanel;
import graphics.screens.MatchResultsPanel;
import graphics.screens.SingleMatchPlayerPanel;
import graphics.screens.Screen;
import graphics.screens.TournamentResultsPanel;
import graphics.screens.TournamentSelectionPanel;
import graphics.screens.CustomWorldSelectionPanel;
import graphics.screens.SingleMatchWorldPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

import javax.swing.*;

/**
 * Game: The main game.
 * Uses collection of JPanels that are switched to the front of the screen when needed.
 */
public class Game extends JFrame{
	
	public static int seed = 0;
	private static int count = 0;
	/**
	 * Generates a pseudo-random number between 0 (inclusive) and n (exclusive).
	 * @param n The upper limit.
	 * @return A pseudo-random number.
	 */
	public static int randomInt(int n){
		for(; count < 3; count++){
			seed = seed * 22695477 + 1;
		}
		seed = seed * 22695477 + 1;
		count++;
		int value = (((seed/65536) % 16384) + 16384) % 16384;
		return ((value % n) + n) % n;
	}
	
	public static final boolean GUI_DEBUG = true; // GUI debugging print statements on/off.
	public static final boolean DEBUG = true;
	
	private CardLayout cardLayout = new CardLayout(); // Allows JPanels to be changed in and out.
	private JPanel screens = new JPanel(cardLayout);  // Holds all of the different screens.
	private static final int WIDTH = 1024;
	private static final int HEIGHT = (WIDTH/16)*9;
	
	private boolean runningMatch = false;
	private Thread modelThread;
	
	private int frames = 0; // for the FPS counter.
	
	private Timer displayTimer; // update the match screens grid
	private Timer statsTimer;   // update the stats for the players.

	private Random rand = new Random();
	
	// Initialise all of the games screens.
	private MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
	public static final String MAIN_MENU_SCREEN = "Main Menu";
	
	private MatchPanel matchPanel = new MatchPanel(this);
	public static final String MATCH_SCREEN = "Match";
	
	private MatchResultsPanel nonTournamentResults = new MatchResultsPanel(this);
	public static final String MATCH_RESULTS_SCREEN = "Match Results";
	
	private SingleMatchPlayerPanel nonTournamentPanel = new SingleMatchPlayerPanel(this);
	public static final String MATCH_BRAIN_SELECTION_SCREEN = "Match Brain Select";
	
	private TournamentResultsPanel tournamentResults = new TournamentResultsPanel(this);
	public static final String TOURNAMENT_RESULTS_SCREEN = "Tournament Results";
	
	private SingleMatchWorldPanel worldSelectionPanel = new SingleMatchWorldPanel(this);
	public static final String WORLD_SELECTION_SCREEN = "World Selection Screen";

	private CustomWorldSelectionPanel worldEditorPanel = new CustomWorldSelectionPanel(this, worldSelectionPanel);
	public static final String WORLD_EDITOR_SCREEN = "World Editor Screen";
	
	private TournamentSelectionPanel tournamentSelectionPanel = new TournamentSelectionPanel(this);
	public static final String TOURNAMENT_SELECTION_SCREEN = "Tournament Selection Screen";
	
	
	// Stack of previous windows. (MAY NOT USE)
	Stack<String> panelHistory = new Stack<String>();
	
	private Tournament currentTournament = null;
	private Match currentMatch = new Match(World.generateWorld(150, 150, 7, 14, 11), new Player("BLACKP1", AntBrainReader.readBrain("cleverbrain1.brain")), new Player("REDP2", AntBrainReader.readBrain("cleverbrain4.brain")));
	private int roundsPerSec = 10; // Number of rounds to perform every second
	private int lastSpeed = 10;
	private double roundTime = 1000000000.0 / roundsPerSec; //number of times to run update per second
	private HashMap<String, Screen> screenMap;
	
	public Game() {
		screenMap = new HashMap<String, Screen>();
		//Add all screens used within the game.
		addScreen(mainMenuPanel, MAIN_MENU_SCREEN);
		addScreen(matchPanel, MATCH_SCREEN);
		addScreen(nonTournamentResults, MATCH_RESULTS_SCREEN);
		addScreen(tournamentResults, TOURNAMENT_RESULTS_SCREEN);
		addScreen(nonTournamentPanel, MATCH_BRAIN_SELECTION_SCREEN);
		addScreen(worldSelectionPanel, WORLD_SELECTION_SCREEN);
		addScreen(worldEditorPanel, WORLD_EDITOR_SCREEN);
		addScreen(tournamentSelectionPanel, TOURNAMENT_SELECTION_SCREEN);

		// JFrame properties 
		this.add(screens);
		this.setTitle("Ant Game  |  " + WIDTH + "x" + HEIGHT );
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Switch the screen that's being displayed to the specified screen.
	 * @param panelName screen to switch in.
	 */
	public void switchScreen(String panelName){
		screenMap.get(panelName).update();
		cardLayout.show(screens, panelName);
	}
	
	/**
	 * Adds the screen to the game so that it can be swapped to when needed.
	 * Adds a mapping between a screen and it's name
	 * @param screen
	 * @param screenName
	 */
	public void addScreen(JPanel screen, String screenName) {
		screenMap.put(screenName, (Screen) screen);
		screens.add(screen, screenName);
	}
	
	/**
	 * Clears the existing grid and changes the HexGrid to have the 
	 * specified parameters.
	 * @param cols
	 * @param rows
	 * @param size
	 * @param strokeWidth
	 */
	public void createMatchPanelGrid(int cols, int rows, int size, int strokeWidth) {
		matchPanel.getGrid().newGrid(cols, rows, size, strokeWidth);
	}
	
	/**
	 * Start running the match that is currently loaded in
	 * Start updating the hexagon grid to display the match.
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
	public void runModel() {
		modelThread = new Thread(new Runnable() {
			public void run() {
				if (Game.DEBUG) System.out.println("DEBUG | Game:runModel() Thread Started!");
//				int roundsPerSec = 10; // Number of rounds to perform every second

				long lastTime = System.nanoTime(); //Computer's current time (in nano seconds)
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
						if(currentMatch.getRoundNumber() >= Match.MAX_ROUNDS || !runningMatch){
							if (Game.DEBUG) setTitle("Ant Game  |  " + getWidth() + "x" + getHeight()  + "  |  UPDATES: " + updates + "/sec  |  ROUND: " + currentMatch.getRoundNumber() + "  |  FPS: " + frames);
							break;
						}
					}

					if (System.currentTimeMillis() - fpsTimer > 1000) { // Happens once every 'roundsPerSec'
						fpsTimer += 1000;
						if (Game.DEBUG) setTitle("Ant Game  |  " + getWidth() + "x" + getHeight()  + "  |  UPDATES: " + updates + "/sec  |  ROUND: " + currentMatch.getRoundNumber() + "  |  FPS: " + frames);
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
	 * Starts updating the hexagon grid to display the current state
	 * of the current match.
	 */
	public void runDisplay() {
		int maxFps = 60;
		displayTimer = new Timer(1000/maxFps, new ActionListener() {
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
		if (Game.DEBUG) System.out.println("DEBUG | Game:runDisplay() displayTimer started!");
		
		
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
		if (Game.DEBUG) System.out.println("DEBUG | Game:runDisplay() statsTimer started!");
	}
	
	/**
	 * Updates the hexagon grid to be the current state of the current match.
	 */
	private void updateMatchScreen() {
		Hexagon[][] gridBuffer = matchPanel.getGrid().getHexagonGrid().clone();
		
		World drawnWorld = currentMatch.getWorld();
		HashSet<Integer> changes = drawnWorld.getChanges();
		drawnWorld.resetChanges();	// Comment out to redraw everything.
		Integer[] tileIDs = changes.toArray(new Integer[0]);
		for(int i = 0; i < tileIDs.length; i++){
			int currentID = tileIDs[i];
			int x = ((currentID % drawnWorld.sizeX) + drawnWorld.sizeX) % drawnWorld.sizeX;
			int y = currentID / drawnWorld.sizeX;
			Tile tile = drawnWorld.getTile(x, y);
			setTileColor(gridBuffer[x][y], tile);
		}
//		for(int i = 0; i < drawnWorld.sizeX; i++){ // Leave this in for debugging
//			for(int j = 0; j < drawnWorld.sizeY; j++){
//				gridBuffer[i][j].setFillColor(getTileColor(drawnWorld.getTile(i, j)));
//			}
//		}
		
		matchPanel.getGrid().setHexagonGrid(gridBuffer);
		matchPanel.getScrollPane().repaint();
	}

	private  void updateModel() {
		currentMatch.nextRound();
	}
	
	public static void setTileColor(Hexagon hexTile, Tile tile){
		Color color = null;
		if(tile.isRocky()){
			color = Hexagon.ROCK_COLOR;
		}else{
			if(((ClearTile)tile).hasAnt()){
				if(((ClearTile)tile).getAnt().getColour().equals(Colour.RED)){
					color = Hexagon.RED_ANT_COLOR;
				}else{
					color = Hexagon.BLACK_ANT_COLOR;
				}
			}else{
				int food = ((ClearTile)tile).getFood();
				if(food > 0){
					color = Hexagon.FOOD_COLOR;
//					color = Hexagon.EMPTY_CELL_COLOR;
//					hexTile.setOutlineColor(Hexagon.FOOD_COLOR);
//					hexTile.setStrokeWidth(5);
				}else{
//					hexTile.setStrokeWidth(1);
//					hexTile.setOutlineColor(Color.BLACK);
					if(((ClearTile)tile).isAnthill()){
						if(((AntHillTile)tile).getColour().equals(Colour.RED)){
							color = Hexagon.RED_ANTHILL_COLOR;
						}else{
							color = Hexagon.BLACK_ANTHILL_COLOR;
						}
					}else{
						color = Hexagon.EMPTY_CELL_COLOR;
					}
				}
			}
		}
		hexTile.setFillColor(color);
	}
	
	/**
	 * Returns the screen displaying the current match.
	 * @return the screen displaying the current match.
	 */
	public MatchPanel getMatchScreen() {
		return this.matchPanel;
	}
	
	/**
	 * Sets the number of rounds that the game will execute per second.
	 */
	public void setRoundsPerSecond(int value){
		roundsPerSec = value;
		roundTime = 1000000000.0 / roundsPerSec;
	}
	
	/**
	 * Returns the number of rounds that the game will execute per second.
	 * @return the number of rounds that the game will execute per second.
	 */
	public int getRoundsPerSecond(){
		return roundsPerSec;
	}
	
	/**
	 * Returns the current match that the game is storing.
	 * @return the current match that the game is storing.
	 */
	public Match getCurrentMatch() {
		return this.currentMatch;
	}
	/**
	 * Returns the current match that the game is storing.
	 * @return the current match that the game is storing.
	 */
	public void setCurrentMatch(Match match) {
		this.currentMatch = match;
	}
	
	/**
	 * Sets the current match to a new blank match, ready for its fields to be set.
	 */
	public void createMatch(){
		this.currentMatch = new Match();
	}
	
	public void setCurrentTournament(Tournament tournament) {
		this.currentTournament = tournament;
	}
	/**
	 * Returns the current match that the game is storing.
	 * @return the current match that the game is storing.
	 */
	public Tournament getCurrentTournament() {
		return this.currentTournament;
	}
	
	/**
	 * Sets the current match to a new blank match, ready for its fields to be set.
	 */
	public void createTournament(){
		this.currentTournament = new Tournament();
	}

	public void togglePause() {
		if(getRoundsPerSecond() == 0){
			setRoundsPerSecond(lastSpeed);
		}else{
			lastSpeed = getRoundsPerSecond();
			setRoundsPerSecond(0);
		}
	}
	
	public void resetScreens(){
		String[] screens = screenMap.keySet().toArray(new String[0]);
		for(int i = 0; i < screens.length; i++){
			if(Game.DEBUG){
				System.out.println("DEBUG | Resetting screen: " + screens[i]);
			}
			screenMap.get(screens[i]).reset();
		}
	}

	public boolean isPaused() {
		return roundsPerSec == 0;
	}
}
