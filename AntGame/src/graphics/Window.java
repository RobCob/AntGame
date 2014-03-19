package graphics;

import java.awt.*;
import java.util.Stack;
import javax.swing.*;

public class Window extends JFrame{
	private CardLayout cardLayout = new CardLayout(); // Allows JPanels to be changed in and out.
	private JPanel screens = new JPanel(cardLayout);  // Holds all of the different screens.
	
	// Initialise all of the games screens.
	private JPanel mainMenuPanel = new MainMenuPanel(this);
	private final String mainMenu = "Main Menu";
			
	private JPanel matchPanel = new MatchPanel(this);
	private final String match = "Match";

	// Stack of previous windows. (MAY NOT USE)
	Stack<String> panelHistory = new Stack<String>();
	
	public Window() {
		//Add all screens used within the game.
		addScreen(mainMenuPanel, mainMenu);
		addScreen(matchPanel, match);
		
		// JFrame properties 
		this.add(screens);
		this.setTitle("Ant Game");
		this.setSize(640, 420);
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
	
}
