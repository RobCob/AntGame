package graphics.screens;

import graphics.components.FixedSpacerPanel;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.utilities.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.Ant;
import model.AntHillTile;
import model.Colour;
import model.Game;
import model.Match;
import model.Player;

/**
 * Results given after a non-tournament match.
 */
public class MatchResultsPanel extends JPanel implements Screen{
	
	//images
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/MatchResultsPanelImages/scoresTitle.png");
	private static final BufferedImage WINNER_IMAGE = ImageLoader.loadImage("/MatchResultsPanelImages/winner.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage STATS_IMAGE = ImageLoader.loadImage("/MatchResultsPanelImages/stats.png");
	private static final BufferedImage MAIN_MENU_BUTTON_IMAGE = ImageLoader.loadImage("/MatchResultsPanelImages/mainMenu.png");
	private static final BufferedImage MAIN_MENU_BUTTON_HOVER = ImageLoader.loadImage("/MatchResultsPanelImages/mainMenuHover.png");
	private static final BufferedImage NEXT_MATCH_IMAGE = ImageLoader.loadImage("/MatchResultsPanelImages/nextMatch.png");
	private static final BufferedImage NEXT_MATCH_IMAGE_HOVER = ImageLoader.loadImage("/MatchResultsPanelImages/nextMatchHover.png");
	
	//other parameters
	private Game game;
	private Player winner; //the winner of the game that just went on, no need for further distinction
	private Player loser;
	
	private JPanel goPanel;
	private ImageButton goButton;
	private JLabel winnerLabel;
	
	private JLabel blackLabel;
	private JLabel blackFoodCollected;
	private JLabel blackKillCount;
	private JLabel blackAntDeaths;
	
	private JLabel redLabel;
	private JLabel redFoodCollected;
	private JLabel redKillCount;
	private JLabel redAntDeaths;
	
	public MatchResultsPanel(Game game) {
		this.game = game;
		this.setLayout(new BorderLayout());
		// get results from previously played match
		// display the results.
		// display back to main-menu button.
		// display re-match button?
		//only use for a rematch button if allow them to reupload the brains, otherwise no point
		
        // Create title container
        JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.setOpaque(false);
		
//		//this label simply display the text "Winner"
//		JPanel winnerContainer = new JPanel();
//		BoxLayout winnerLayout = new BoxLayout(winnerContainer, BoxLayout.Y_AXIS);
//		winnerContainer.setLayout(winnerLayout);
//		winnerContainer.add(new FixedSpacerPanel(100, 20));
//		winnerContainer.add(new ImagePanel(WINNER_IMAGE));
//		winnerContainer.add(new FixedSpacerPanel(100, 70)); 
//		winnerContainer.setOpaque(false);
		
		//Create the text to diplay the game's winner
		//just for testing purposes, on final version should be winner.name()
		winnerLabel = new JLabel("Player 1");
		winnerLabel.setForeground(Color.WHITE);
		winnerLabel.setFont(new Font("Helvetica", 0, 35));
		winnerLabel.setAlignmentX(CENTER_ALIGNMENT);
		winnerLabel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));

		//Panel for winner info
        JPanel winnerPanel = new JPanel();
        BoxLayout winnerPanelLayout = new BoxLayout(winnerPanel, BoxLayout.Y_AXIS);
        winnerPanel.setLayout(winnerPanelLayout);
        winnerPanel.add(new ImagePanel(WINNER_IMAGE));
        winnerPanel.add(winnerLabel);
        winnerPanel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        winnerPanel.setOpaque(false);
        
        //Winning player statistics
        JPanel blackStats = new JPanel();
        BoxLayout winnerStatsLayout = new BoxLayout(blackStats, BoxLayout.Y_AXIS);
        blackStats.setLayout(winnerStatsLayout);

        //Winner name -- clone, because you can only reference a JPanel in one place at a time
        blackLabel = new JLabel("Player 1");
        blackLabel.setForeground(Color.WHITE);
        blackLabel.setFont(new Font("Helvetica", 0, 31));
        blackLabel.setAlignmentX(CENTER_ALIGNMENT);
        blackLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        //Food Collected
        blackFoodCollected = new JLabel("Food collected: 31"); //just to test, should have actual values
        blackFoodCollected.setForeground(Color.WHITE);
        blackFoodCollected.setFont(new Font("Helvetica", 0, 20));
        blackFoodCollected.setAlignmentX(CENTER_ALIGNMENT);
        
		//Ants killed
        blackKillCount = new JLabel("Ants killed: 7"); //just to test, should have actual values
        blackKillCount.setForeground(Color.WHITE);
        blackKillCount.setFont(new Font("Helvetica", 0, 20));
        blackKillCount.setAlignmentX(CENTER_ALIGNMENT);
        //Deaths in team
        blackAntDeaths = new JLabel("Number of team deaths: 3"); //just to test, should have actual values
        blackAntDeaths.setForeground(Color.WHITE);
        blackAntDeaths.setFont(new Font("Helvetica", 0, 20));
        blackAntDeaths.setAlignmentX(CENTER_ALIGNMENT);
        //add values to panel
        blackStats.add(blackLabel);
        blackStats.add(blackFoodCollected);
        blackStats.add(blackKillCount);
        blackStats.add(blackAntDeaths);
        blackStats.setOpaque(false);
        
        //Losing player statistics
        JPanel redStats = new JPanel();
        BoxLayout loserStatsLayout = new BoxLayout(redStats, BoxLayout.Y_AXIS);
        redStats.setLayout(loserStatsLayout);
        //Loser name
        redLabel = new JLabel("Player 2");
        redLabel.setForeground(Color.WHITE);
        redLabel.setFont(new Font("Helvetica", 0, 32));
        redLabel.setAlignmentX(CENTER_ALIGNMENT);
        redLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        //Food Collected
        redFoodCollected = new JLabel("Food collected: 22"); //just to test, should have actual values
        redFoodCollected.setForeground(Color.WHITE);
        redFoodCollected.setFont(new Font("Helvetica", 0, 20));
        redFoodCollected.setAlignmentX(CENTER_ALIGNMENT);
		//Ants killed
        redKillCount = new JLabel("Ants killed: 5"); //just to test, should have actual values
        redKillCount.setForeground(Color.WHITE);
        redKillCount.setFont(new Font("Helvetica", 0, 20));
        redKillCount.setAlignmentX(CENTER_ALIGNMENT);
        //Deaths in team
        redAntDeaths = new JLabel("Number of team deaths: 11"); //just to test, should have actual values
        redAntDeaths.setForeground(Color.WHITE);
        redAntDeaths.setFont(new Font("Helvetica", 0, 20));
        redAntDeaths.setAlignmentX(CENTER_ALIGNMENT);
        //add values to panel
        redStats.add(redLabel);
        redStats.add(redFoodCollected);
        redStats.add(redKillCount);
        redStats.add(redAntDeaths);
        redStats.setOpaque(false); //TODO
        
        // Create and set up the split pane.
        JSplitPane statsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); //add split pane
        statsSplitPane.setDividerLocation(1000/2); // HACK -- (half the width of the screen)
        statsSplitPane.setEnabled(false); // Stops it being re-sizable.
        statsSplitPane.setOpaque(false); // display the background through it.
        statsSplitPane.setLeftComponent(blackStats);
        statsSplitPane.setRightComponent(redStats);
        statsSplitPane.setBorder(null);
        statsSplitPane.setDividerSize(0);
        
        goButton = new ImageButton(MAIN_MENU_BUTTON_IMAGE, MAIN_MENU_BUTTON_HOVER){
        	public void mouseClicked(MouseEvent e) {
        		getGame().switchScreen(Game.MAIN_MENU_SCREEN);
        	}
        };
        //Button for replaying

        goPanel = new JPanel();
        goPanel.setOpaque(false);
        goPanel.add(goButton);
        goPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        JPanel statsImagePanel = new JPanel();
        BoxLayout statsPanelLayout = new BoxLayout(statsImagePanel, BoxLayout.Y_AXIS);
        statsImagePanel.setLayout(statsPanelLayout);
        statsImagePanel.add(new ImagePanel(STATS_IMAGE));
        statsImagePanel.setOpaque(false);
        
        JPanel entireStatsPanel = new JPanel(new BorderLayout());
        entireStatsPanel.add(statsImagePanel, BorderLayout.NORTH);
        entireStatsPanel.add(statsSplitPane, BorderLayout.CENTER);
        entireStatsPanel.setOpaque(false);
        
        // Centre container
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(winnerPanel, BorderLayout.NORTH);
        centerPanel.add(entireStatsPanel, BorderLayout.CENTER);
        centerPanel.setOpaque(false);

        //Add containers
        this.add(titleContainer, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
		this.add(goPanel, BorderLayout.SOUTH);
	}
	
	public void setValues(Match match){
		
	}
	
	 @Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	 }
	 
	 public Game getGame(){
		return game; 
	 }
	 
	public static void main(String[] args){
		//Add content to the window.
        JFrame frame = new JFrame("Results");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 576);
        frame.add(new MatchResultsPanel(null));
        frame.setResizable(false);
        
        //Display the window.
        frame.setVisible(true);
	}

	@Override
	public void update() {
		Match match = getGame().getCurrentMatch();
		Player player1 = match.getPlayer1();
		Player player2 = match.getPlayer2();
		if(match.getWinner() == null){
			winnerLabel.setText(player1.getNickname() + " draws with " + player2.getNickname());
		}else{
			winnerLabel.setText(match.getWinner().getNickname());
		}
		blackLabel.setText("Black Team: " +player1.getNickname());
		blackFoodCollected.setText("Food collected: "+match.getScore(player1, 0));
		blackKillCount.setText("Ants killed: "+ match.getScore(player1, 1));
		blackAntDeaths.setText("Ants Died: "+ match.getScore(player2, 1));
		
		redLabel.setText("Red Team: " + player2.getNickname());
		redFoodCollected.setText("Food collected: "+match.getScore(player2, 0));
		redKillCount.setText("Ants killed: " + match.getScore(player2, 1));
		redAntDeaths.setText("Ants Died: " + match.getScore(player1, 1));
		
		if(getGame().getCurrentTournament() == null){
	        goButton = new ImageButton(MAIN_MENU_BUTTON_IMAGE, MAIN_MENU_BUTTON_HOVER) {
	        	public void mouseClicked(MouseEvent e) {
	        		getGame().switchScreen(Game.MAIN_MENU_SCREEN);
	        	}
	        };
        }else{
        	if(!getGame().getCurrentTournament().isLastMatch()){
	        	goButton = new ImageButton(NEXT_MATCH_IMAGE, NEXT_MATCH_IMAGE_HOVER) {
		        	public void mouseClicked(MouseEvent e) {
		        		getGame().getCurrentTournament().nextMatch();
		        		getGame().setCurrentMatch(getGame().getCurrentTournament().getCurrentMatch());
		        		getGame().createMatchPanelGrid(getGame().getCurrentMatch().getWorld().sizeX, getGame().getCurrentMatch().getWorld().sizeY, 2, 1);
		        		getGame().switchScreen(Game.MATCH_SCREEN);
		        		getGame().startMatch();
		        	}
		        };
        	}else{
        		goButton = new ImageButton(WINNER_IMAGE, STATS_IMAGE){
					@Override
					public void mouseClicked(MouseEvent e) {
						getGame().getCurrentTournament().nextMatch();
						getGame().switchScreen(Game.TOURNAMENT_RESULTS_SCREEN);
					}
        		};
        	}
	        goPanel.removeAll();
	        goPanel.add(goButton);
        }
	}

	@Override
	public void reset() {
		winnerLabel.setText("Null");
		blackLabel.setText("null");
		blackFoodCollected.setText("Food collected: null");
		blackKillCount.setText("Ants killed: null");
		blackAntDeaths.setText("Ants Died: null");
		
		redLabel.setText("null");
		redFoodCollected.setText("Food collected: null");
		redKillCount.setText("Ants killed: null");
		redAntDeaths.setText("Ants Died: null");
	}
	
}