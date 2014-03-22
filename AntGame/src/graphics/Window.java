package graphics;

import java.awt.*;
import java.util.Random;
import java.util.Stack;
import javax.swing.*;

/**
 * The main window of the game.
 */
public class Window extends JFrame{
	private CardLayout cardLayout = new CardLayout(); // Allows JPanels to be changed in and out.
	private JPanel screens = new JPanel(cardLayout);  // Holds all of the different screens.
	private static final int WIDTH = 1024;
	private static final int HEIGHT = (WIDTH/16)*9;
	
	private boolean runningMatch = false;
	
	private int[][] antsTest; // TEST!
	
	// Initialise all of the games screens.
	private MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
	private final String mainMenu = "Main Menu";
			
	private HexGrid grid = new HexGrid(0,0,0,0);
	private MatchPanel matchPanel = new MatchPanel(this, grid);
	private final String match = "Match";

	// Stack of previous windows. (MAY NOT USE)
	Stack<String> panelHistory = new Stack<String>();
	private Random rand = new Random();
	
	public Window() {
		//Add all screens used within the game.
		addScreen(mainMenuPanel, mainMenu);
		addScreen(matchPanel, match);
		
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
	
	public void createMatch(int cols, int rows, int size, int strokeWidth) {
		matchPanel.getGrid().newGrid(rows, cols, size, strokeWidth);
		//addScreen(new MatchPanel(this, new HexGrid(cols, rows, size, strokeWidth)), match);
	}
	
	public void startMatch() {
		runningMatch = true;
		long lastTime = System.nanoTime(); //Computer's current time (in nano seconds)
		long fpsTimer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //number of times to run update per second
		double screenDelta = 0.0;
		double modelDelta = 0.0;

		int frames = 0;
		int updates = 0;

		//Game loop
		while (runningMatch) {
			long now = System.nanoTime();
			screenDelta += (now - lastTime) / ns;
			modelDelta += (now - lastTime) / ns;
			lastTime = now;

			while (screenDelta >= 1) { // happens 60 times a seconds.
				updateMatchScreen();
				updates++;
				screenDelta--;
			}
			frames++;

			if (System.currentTimeMillis() - fpsTimer > 1000) {
				fpsTimer += 1000;
				this.setTitle("Ant Game " + "(" + WIDTH + "x" + HEIGHT + ")" + " UPDATES: " + updates);

				// Reset the stats
				updates = 0;
				frames = 0;
			}
		}
	}
	
	private void updateMatchScreen() {
		int width = matchPanel.getGrid().getColumns();
		int height = matchPanel.getGrid().getRows();
		matchPanel.getGrid().getHexagon(rand.nextInt(height), rand.nextInt(width)).setFillColor(Color.RED);
		//matchPanel.getGrid().refresh();
	}

	private void updateModel() {
		
	}

	public void stopMatch() {
		runningMatch = false;
	}
	
}
