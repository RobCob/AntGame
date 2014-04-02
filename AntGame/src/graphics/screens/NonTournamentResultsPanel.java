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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.Game;
import model.Player;

/**
 * Results given after a non-tournament match.
 */
public class NonTournamentResultsPanel extends JPanel{
	
	//images
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/NonTournamentResultsPanelImages/scoresTitle.png");
	private static final BufferedImage WINNER_IMAGE = ImageLoader.loadImage("/NonTournamentResultsPanelImages/winner.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage STATS_IMAGE = ImageLoader.loadImage("/NonTournamentResultsPanelImages/stats.png");
	private static final BufferedImage NEXT_BUTTON_IMAGE = ImageLoader.loadImage("/NonTournamentSelectionImages/nextButtonImage.png");
	private static final BufferedImage NEXT_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/NonTournamentSelectionImages/nextButtonImageHover.png");
	
	//other parameters
	private Game game;
	Player winner; //the winner of the game that just went on, no need for further distinction
	Player loser;
	
	
	public NonTournamentResultsPanel(Game game) {
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
		titleContainer.add(new FixedSpacerPanel(100, 40));
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
		JLabel winnerLabel = new JLabel("Player 1");
		winnerLabel.setForeground(Color.WHITE);
		winnerLabel.setFont(new Font("Helvetica", 0, 35));
		winnerLabel.setAlignmentX(CENTER_ALIGNMENT);
		winnerLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

		//Panel for winner info
        JPanel winnerPanel = new JPanel();
        BoxLayout winnerPanelLayout = new BoxLayout(winnerPanel, BoxLayout.Y_AXIS);
        winnerPanel.setLayout(winnerPanelLayout);
        winnerPanel.add(new ImagePanel(WINNER_IMAGE));
        winnerPanel.add(winnerLabel);
        winnerPanel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        winnerPanel.setOpaque(false);
        
        //Winning player statistics
        JPanel winnerStats = new JPanel();
        BoxLayout winnerStatsLayout = new BoxLayout(winnerStats, BoxLayout.Y_AXIS);
        winnerStats.setLayout(winnerStatsLayout);

        //Winner name -- clone, because you can only reference a JPanel in one place at a time
        JLabel winnerLabelClone = new JLabel("Player 1");
        winnerLabelClone.setForeground(Color.WHITE);
        winnerLabelClone.setFont(new Font("Helvetica", 0, 28));
        winnerLabelClone.setAlignmentX(CENTER_ALIGNMENT);
        winnerLabelClone.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        //Food Collected
        JLabel winnerFoodCollected = new JLabel("Food collected: 31"); //just to test, should have actual values
        winnerFoodCollected.setForeground(Color.WHITE);
        winnerFoodCollected.setFont(new Font("Helvetica", 0, 17));
        winnerFoodCollected.setAlignmentX(CENTER_ALIGNMENT);
		//Ants killed
        JLabel winnerAntsKilled = new JLabel("Ants killed: 7"); //just to test, should have actual values
        winnerAntsKilled.setForeground(Color.WHITE);
        winnerAntsKilled.setFont(new Font("Helvetica", 0, 17));
        winnerAntsKilled.setAlignmentX(CENTER_ALIGNMENT);
        //Deaths in team
        JLabel winnerAntsDied = new JLabel("Number of team deaths: 3"); //just to test, should have actual values
        winnerAntsDied.setForeground(Color.WHITE);
        winnerAntsDied.setFont(new Font("Helvetica", 0, 17));
        winnerAntsDied.setAlignmentX(CENTER_ALIGNMENT);
        //add values to panel
        winnerStats.add(winnerLabelClone);
        winnerStats.add(winnerFoodCollected);
        winnerStats.add(winnerAntsKilled);
        winnerStats.add(winnerAntsDied);
        winnerStats.setOpaque(false);
        
        //Losing player statistics
        JPanel loserStats = new JPanel();
        BoxLayout loserStatsLayout = new BoxLayout(loserStats, BoxLayout.Y_AXIS);
        loserStats.setLayout(loserStatsLayout);
        //Loser name
        JLabel loserLabel = new JLabel("Player 2");
        loserLabel.setForeground(Color.WHITE);
        loserLabel.setFont(new Font("Helvetica", 0, 28));
        loserLabel.setAlignmentX(CENTER_ALIGNMENT);
        loserLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        //Food Collected
        JLabel loserFoodCollected = new JLabel("Food collected: 22"); //just to test, should have actual values
        loserFoodCollected.setForeground(Color.WHITE);
        loserFoodCollected.setFont(new Font("Helvetica", 0, 17));
        loserFoodCollected.setAlignmentX(CENTER_ALIGNMENT);
		//Ants killed
        JLabel loserAntsKilled = new JLabel("Ants killed: 5"); //just to test, should have actual values
        loserAntsKilled.setForeground(Color.WHITE);
        loserAntsKilled.setFont(new Font("Helvetica", 0, 17));
        loserAntsKilled.setAlignmentX(CENTER_ALIGNMENT);
        //Deaths in team
        JLabel loserAntsDied = new JLabel("Number of team deaths: 11"); //just to test, should have actual values
        loserAntsDied.setForeground(Color.WHITE);
        loserAntsDied.setFont(new Font("Helvetica", 0, 17));
        loserAntsDied.setAlignmentX(CENTER_ALIGNMENT);
        //add values to panel
        loserStats.add(loserLabel);
        loserStats.add(loserFoodCollected);
        loserStats.add(loserAntsKilled);
        loserStats.add(loserAntsDied);
        loserStats.setOpaque(false); //TODO
        
        // Create and set up the split pane.
        JSplitPane statsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); //add split pane
        statsSplitPane.setDividerLocation(1000/2); // HACK -- (half the width of the screen)
        statsSplitPane.setEnabled(false); // Stops it being re-sizable.
        statsSplitPane.setOpaque(false); // display the background through it.
        statsSplitPane.setLeftComponent(winnerStats);
        statsSplitPane.setRightComponent(loserStats);
        statsSplitPane.setBorder(null);
        statsSplitPane.setDividerSize(0);
        
        //Button for replaying
        //needs to be changed to actual image, this one is just for testing
        ImageButton goButton = new ImageButton(NEXT_BUTTON_IMAGE, NEXT_BUTTON_IMAGE_HOVER) {
        	public void mouseClicked(MouseEvent e) {
        		// DOES NOTHING RIGHT NOW
        	}
        };

        JPanel goPanel = new JPanel();
        goPanel.setOpaque(false);
        goPanel.add(goButton);
        goPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 40, 0));
        
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
        frame.add(new NonTournamentResultsPanel(null));
        frame.setResizable(false);
        
        //Display the window.
        frame.setVisible(true);
	}
	
}
