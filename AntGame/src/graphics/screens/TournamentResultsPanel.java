package graphics.screens;

import graphics.components.FixedSpacerPanel;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.components.ListItem;
import graphics.utilities.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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
	
	private JPanel itemPanel; // list of players (ranked)
	private JScrollPane listHolder; // scrollPane
	
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

		listHolder = new JScrollPane(itemPanel);
		listHolder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listHolder.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listHolder.setPreferredSize(new Dimension(900, 330));
		listHolder.setOpaque(true);
		
		// Panel that will be added to the centre of the screen;
		JPanel centrePanel = new JPanel();
		centrePanel.setOpaque(false);
		centrePanel.add(listHolder);
		
		
		JPanel mainMenuButtonPanel = new JPanel();
		mainMenuButtonPanel.setOpaque(false);
		
        ImageButton mainMenuButton = new ImageButton(MAIN_MENU_BUTTON_IMAGE, MAIN_MENU_BUTTON_HOVER){
        	public void mouseClicked(MouseEvent e) {
        		getGame().switchScreen(Game.MAIN_MENU_SCREEN);
        	}
        };
        
        mainMenuButtonPanel.add(mainMenuButton);
        mainMenuButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
		
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
		reset();
		// get players by score (ascending order)
		ArrayList<Player> players = getGame().getCurrentTournament().getOrderedPlayers(true);

		// Add players to list in ascending order.
		for (Player p: players) {
			// Create a list item and ad in the new player
			ListItem newPlayerItem = new ListItem(p.getNickname(), "(" + p.getBrain().getName() + ")", itemPanel);
			
			JLabel scoreLabel = new JLabel("Score: " + getGame().getCurrentTournament().getScore(p));
			scoreLabel.setForeground(Color.BLACK);
			scoreLabel.setFont(new Font("Helvetica", 0, 25));
			
			JPanel scorePanel = new JPanel();
			scorePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
			scorePanel.setOpaque(false);
			scorePanel.add(scoreLabel);
			
			newPlayerItem.changeSize(885, 50);
			newPlayerItem.addRight(scorePanel);
			newPlayerItem.setPlayer(p);
			
			// Add the item to the item panel and refresh the scrollpane.
			itemPanel.add(newPlayerItem);
		}
		
		this.revalidate();
		this.repaint();
	}

	@Override
	public void reset() {
		itemPanel.removeAll();
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
