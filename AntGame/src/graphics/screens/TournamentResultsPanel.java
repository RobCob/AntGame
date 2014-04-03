package graphics.screens;

import graphics.components.FixedSpacerPanel;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.components.ListItem;
import graphics.utilities.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Game;
import model.Player;

/**
 * The final results of the tournament.
 */
public class TournamentResultsPanel extends JPanel implements Screen{
	
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/MatchResultsPanelImages/scoresTitle.png");
	private static final BufferedImage WINNER_IMAGE = ImageLoader.loadImage("/MatchResultsPanelImages/winner.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage STATS_IMAGE = ImageLoader.loadImage("/MatchResultsPanelImages/stats.png");
	private static final BufferedImage MAIN_MENU_BUTTON_IMAGE = ImageLoader.loadImage("/MatchResultsPanelImages/mainMenu.png");
	private static final BufferedImage MAIN_MENU_BUTTON_HOVER = ImageLoader.loadImage("/MatchResultsPanelImages/mainMenuHover.png");
	
	private Game game;
	
	private JPanel itemPanel;
	
	public TournamentResultsPanel(Game game) {
		this.game = game;
		this.setLayout(new BorderLayout());
		
		// Create title container
        JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.setOpaque(false);
		
		
		// The ranking list
		itemPanel = new JPanel();
		BoxLayout itemPanelLayout = new BoxLayout(itemPanel, BoxLayout.Y_AXIS);
		itemPanel.setLayout(itemPanelLayout);

		
		// Panel that will be added to the centre of the screen;
		JPanel centrePanel = new JPanel();
		centrePanel.setOpaque(false);
		
		JPanel mainMenuButtonPanel = new JPanel();
		mainMenuButtonPanel.setOpaque(false);
		
        ImageButton mainMenuButton = new ImageButton(MAIN_MENU_BUTTON_IMAGE, MAIN_MENU_BUTTON_HOVER){
        	public void mouseClicked(MouseEvent e) {
        		getGame().switchScreen(Game.MAIN_MENU_SCREEN);
        	}
        };
        
        mainMenuButtonPanel.add(mainMenuButton);
        mainMenuButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
		
		// Add the panels to the main panel
		this.add(titleContainer, BorderLayout.NORTH);
		this.add(centrePanel, BorderLayout.CENTER);
		this.add(mainMenuButtonPanel, BorderLayout.SOUTH);
	}

	public Game getGame() {
		return this.game;
	}
	
	@Override
	public void update() {
		// get players by score (ascending order)
		ArrayList<Player> players = getGame().getCurrentTournament().getOrderedPlayers(true);

		// Add players to list in ascending order.
		for (Player p: players) {
			// Create a list item and ad in the new player
			ListItem newPlayerItem = new ListItem(p.getNickname(), "", itemPanel);
			JLabel score = new JLabel("" + getGame().getCurrentTournament().getScore(p));
			newPlayerItem.changeSize(885, 50);
			newPlayerItem.addRight(score);
			newPlayerItem.setPlayer(p);
			
			// Add the item to the item panel and refresh the scrollpane.
			itemPanel.add(newPlayerItem);
		}
		
		this.revalidate();
		this.repaint();
	}

	@Override
	public void reset() {
		
	}
	
	 @Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	 }
	 
	public static void main(String[] args){
		//Add content to the window.
        JFrame frame = new JFrame("Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 576);
        frame.add(new TournamentResultsPanel(null));
        frame.setResizable(false);
        
        //Display the window.
        frame.setVisible(true);
	}
}
