package model;


import graphics.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Stack;

import javax.swing.*;

/**
 * Game: The main game.
 * Uses collection of JPanels that are switched to the front of the screen when needed.
 * 
 */
public class Game extends JFrame{
	private CardLayout cardLayout = new CardLayout(); // Allows JPanels to be changed in and out.
	private JPanel screens = new JPanel(cardLayout);  // Holds all of the different screens.
	private static final int WIDTH = 1024;
	private static final int HEIGHT = (WIDTH/16)*9;
	
	private boolean runningMatch = false;
	private Thread modelThread;
	private int screenNo = 0;
	private Timer displayTimer;
	private Random rand = new Random();
	

	
	// Initialise all of the games screens.
	private MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
	public static final String MAIN_MENU_SCREEN = "Main Menu";
			
	private HexGrid grid = new HexGrid(0,0,0,0);
	private MatchPanel matchPanel = new MatchPanel(this, grid);
	public static final String MATCH_SCREEN = "Match";

	// Stack of previous windows. (MAY NOT USE)
	Stack<String> panelHistory = new Stack<String>();
	
	private int[][] worldWithAnts = new int[150][150]; // TEST!
	
	public Game() {
		//Add all screens used within the game.
		addScreen(mainMenuPanel, MAIN_MENU_SCREEN);
		addScreen(matchPanel, MATCH_SCREEN);
		
		// JFrame properties 
		this.add(screens);
		this.setTitle("Ant Game " + "(" + WIDTH + "x" + HEIGHT + ")");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void switchScreen(String panelName) {
		cardLayout.show(screens, panelName);
	}
	
	public void addScreen(JPanel screen, String screenName) {
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
	public void createMatch(int cols, int rows, int size, int strokeWidth) {
		matchPanel.getGrid().newGrid(rows, cols, size, strokeWidth);
		//addScreen(new MatchPanel(this, new HexGrid(cols, rows, size, strokeWidth)), match);
	}
	
	
	public void runModel() {
		modelThread = new Thread(new Runnable() {
			public void run() {
				System.out.println("THREAD TEST");
				long lastTime = System.nanoTime(); //Computer's current time (in nano seconds)
				long fpsTimer = System.currentTimeMillis();
				int roundsPerSec = 500; // Number of rounds to perform every second
				final double ns = 1000000000.0 / roundsPerSec; //number of times to run update per second
				
				double modelDelta = 0.0;
				
				int round = 0;
				int maxRounds = 300000;
				
				int frames = 0;
				int updates = 0;

				// Game loop
				while (round <= maxRounds && runningMatch) {
					long now = System.nanoTime();
					modelDelta += (now - lastTime) / ns;
					lastTime = now;

					while (modelDelta >= 1) { // Happens 'roundsPerSec' times a second.
						updateModel();
						round++;
						updates++;
						modelDelta--;
					}
					frames++;

					if (System.currentTimeMillis() - fpsTimer > 1000) { // Happens once every 'roundsPerSec'
						fpsTimer += 1000;
						setTitle("Ant Game " + "(" + WIDTH + "x" + HEIGHT + ")" + ", FPS: " + frames/roundsPerSec +", UPDATES: " + updates + "/sec, ROUND: " + round + ", SCREEN: " + screenNo);

						// Reset the stats
						updates = 0;
						frames = 0;
					}
				}
				 if (round > maxRounds) {
					 runningMatch = false;
				 }
			}
		});
		modelThread.start();
	}
	
	/**
	 * Start running the match loaded in, and display it to the screen.
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
	 * Start refreshing the match screen.
	 */
	public void runDisplay() {
		screenNo = 0;
		int fps = 60;
		displayTimer = new Timer(1000/fps, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!runningMatch) {
	            	displayTimer.stop();
	            }
				screenNo++;
				updateMatchScreen();
			}
		});
		displayTimer.start(); 
	}
	
	/**
	 * WILL GET THE CONTENTS OF THE 2D WORLD, UPDATE THE GRID TO MATCH IT
	 * AND DRAW IT TO THE SCREEN.
	 */
	private void updateMatchScreen() {
		// To prevent displaying midway updates
		// HexGrid gridBuffer = matchPanel.getGrid();
		Hexagon[][] gridBuffer = matchPanel.getGrid().getHexagonGrid();
		
		int cols = gridBuffer.length;
		int rows = gridBuffer[0].length;
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				if (worldWithAnts[i][j] == 0) {
					gridBuffer[i][j].setFillColor(Hexagon.RED_ANT_COLOR);
				} else if (worldWithAnts[i][j] == 1) {
					gridBuffer[i][j].setFillColor(Hexagon.BLACK_ANT_COLOR);
				} else {
					gridBuffer[i][j].setFillColor(Hexagon.EMPTY_CELL_COLOR);
				}
			}
		}
		
		matchPanel.getGrid().setHexagonGrid(gridBuffer);
		matchPanel.getScrollPane().repaint();
	}

	private  void updateModel() {
		// To prevent displaying midway updates
		int[][] worldBuffer = new int[worldWithAnts.length][worldWithAnts[0].length]; 
		
		// Randomly modify world.
		for (int i = 0; i < worldBuffer.length; i++) {
			for (int j = 0; j < worldBuffer[0].length; j++) {
				worldBuffer[i][j] = rand.nextInt(10); //0 for red, 1 for black, other for empty.
			}
		}
		
		// Update stored world.
		worldWithAnts = worldBuffer;
	}
}
