package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Stack;
import javax.swing.*;

/**
 * Window: The main window of the game.
 * Uses collection of JPanels that are switched to the front of the screen when needed.
 * 
 */
public class Window extends JFrame{
	private CardLayout cardLayout = new CardLayout(); // Allows JPanels to be changed in and out.
	private JPanel screens = new JPanel(cardLayout);  // Holds all of the different screens.
	private static final int WIDTH = 1024;
	private static final int HEIGHT = (WIDTH/16)*9;
	
	private boolean runningMatch = false;
	private int screenNo = 0;
	private int[][] antsTest; // TEST!
	
	// Initialise all of the games screens.
	private MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
	public static final String MAIN_MENU_SCREEN = "Main Menu";
			
	private HexGrid grid = new HexGrid(0,0,0,0);
	private MatchPanel matchPanel = new MatchPanel(this, grid);
	public static final String MATCH_SCREEN = "Match";

	// Stack of previous windows. (MAY NOT USE)
	Stack<String> panelHistory = new Stack<String>();
	private Random rand = new Random();
	private Timer displayTimer;
	
	public Window() {
		//Add all screens used within the game.
		addScreen(mainMenuPanel, MAIN_MENU_SCREEN);
		addScreen(matchPanel, MATCH_SCREEN);
		
		// JFrame properties 
		this.add(screens);
		this.setTitle("Ant Game " + "(" + WIDTH + "x" + HEIGHT + ")");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(true);
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
	
	// REFACTOR INTO Game.java?
	public void startMatch() {
		runningMatch = true;
		displayUpdates();
		
		long lastTime = System.nanoTime(); //Computer's current time (in nano seconds)
		long fpsTimer = System.currentTimeMillis();
		int roundsPerSec = 1000;
		final double ns = 1000000000.0 / roundsPerSec; //number of times to run update per second
		
		double screenDelta = 0.0;
		double modelDelta = 0.0;
		
		int round = 0;
		int maxRounds = 300000;
		
		int frames = 0;
		int updates = 0;

		// Game loop
		while (round <= maxRounds && runningMatch) {
			long now = System.nanoTime();
			screenDelta += (now - lastTime) / ns;
			modelDelta += (now - lastTime) / ns;
			lastTime = now;

			while (modelDelta >= 1) { // happens 60 times a seconds.
				updateModel();
				round++;
				updates++;
				modelDelta--;
			}
			frames++;

			if (System.currentTimeMillis() - fpsTimer > 1000) {
				fpsTimer += 1000;
				this.setTitle("Ant Game " + "(" + WIDTH + "x" + HEIGHT + ")" + " UPDATES: " + updates + "/sec ROUND: " + round + " SCREEN: " + screenNo);

				// Reset the stats
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void displayUpdates() {
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
	private void updateMatchScreen() {
		matchPanel.getGrid().refresh();
	}

	private void updateModel() {
		int width = matchPanel.getGrid().getColumns();
		int height = matchPanel.getGrid().getRows();
		
		// Redraw 30 'ants' every update.
		for (int i = 0; i < 30; i++) {
			matchPanel.getGrid().getHexagon(rand.nextInt(height), rand.nextInt(width)).setFillColor(Color.RED);
		}
	}

	public void stopMatch() {
		runningMatch = false;
	}
	
}
