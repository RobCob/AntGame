package model;

import graphics.components.HexGrid;
import graphics.components.Hexagon;
import graphics.screens.MainMenuPanel;
import graphics.screens.MatchPanel;
import graphics.screens.MatchResultsPanel;
import graphics.screens.MatchBrainSelectionPanel;
import graphics.screens.Screen;
import graphics.screens.TournamentResultsPanel;
import graphics.screens.TournamentSelectionPanel;
import graphics.screens.CustomWorldSelectionPanel;
import graphics.screens.MatchWorldSelectionPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	public static final boolean GUI_DEBUG = true; // GUI debugging print statements on/off.
	public static final boolean DEBUG = true;
	
	private CardLayout cardLayout = new CardLayout(); // Allows JPanels to be changed in and out.
	private JPanel screens = new JPanel(cardLayout);  // Holds all of the different screens.
	private static final int WIDTH = 1024;
	private static final int HEIGHT = (WIDTH/16)*9;
	
	private boolean runningMatch = false;
	private Thread modelThread;
	
	private int frames = 0; // for the FPS counter.
	
	private Timer displayTimer;
	private Random rand = new Random();
	
	// Initialise all of the games screens.
	private MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
	public static final String MAIN_MENU_SCREEN = "Main Menu";
	
	private MatchPanel matchPanel = new MatchPanel(this);
	public static final String MATCH_SCREEN = "Match";
	
	private MatchResultsPanel nonTournamentResults = new MatchResultsPanel(this);
	public static final String MATCH_RESULTS_SCREEN = "Match Results";
	
	private MatchBrainSelectionPanel nonTournamentPanel = new MatchBrainSelectionPanel(this);
	public static final String MATCH_BRAIN_SELECTION_SCREEN = "Match Brain Select";
	
	private TournamentResultsPanel tournamentResults = new TournamentResultsPanel(this);
	public static final String TOURNAMENT_RESULTS_SCREEN = "Tournament Results";
	
	private MatchWorldSelectionPanel worldSelectionPanel = new MatchWorldSelectionPanel(this);
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
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void switchScreen(String panelName){
		screenMap.get(panelName).update();
		cardLayout.show(screens, panelName);
	}
	
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
					}

					if (System.currentTimeMillis() - fpsTimer > 1000) { // Happens once every 'roundsPerSec'
						fpsTimer += 1000;
						setTitle("Ant Game  |  " + getWidth() + "x" + getHeight()  + "  |  UPDATES: " + updates + "/sec  |  ROUND: " + currentMatch.getRoundNumber() + "  |  FPS: " + frames);
						frames = 0; // reset number of frames per sec.
						updates = 0; // reset number of updates per sec.
					}
				}
				if (currentMatch.getRoundNumber() == Match.MAX_ROUNDS) {
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
	            }
				frames++;
				updateMatchScreen();
			}
		});
		displayTimer.start(); // Start updating the screen periodically.
		if (Game.DEBUG) System.out.println("DEBUG | Game:runDisplay() Updates started!");
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
			gridBuffer[x][y].setFillColor(getTileColor(tile));
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
	
	public static Color getTileColor(Tile tile){
		if(tile.isRocky()){
			return Hexagon.ROCK_COLOR;
		}else{
			if(((ClearTile)tile).hasAnt()){
				if(((ClearTile)tile).getAnt().getColour().equals(Colour.RED)){
					return Hexagon.RED_ANT_COLOR;
				}else{
					return Hexagon.BLACK_ANT_COLOR;
				}
			}else{
				if(((ClearTile)tile).getFood()>0){
					return Hexagon.FOOD_COLOR;
				}else{
					if(((ClearTile)tile).isAnthill()){
						if(((AntHillTile)tile).getColour().equals(Colour.RED)){
							return Hexagon.RED_ANTHILL_COLOR;
						}else{
							return Hexagon.BLACK_ANTHILL_COLOR;
						}
					}else{
						return Hexagon.EMPTY_CELL_COLOR;
					}
				}
			}
		}
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
		System.out.println("WATTHEFUCK");
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
}
