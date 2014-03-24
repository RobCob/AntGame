package graphics.screens;

import graphics.components.CustomButton;
import graphics.components.FixedSpacerPanel;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.components.NormalButton;
import graphics.utilities.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import model.Game;

public class MainMenuPanel extends JPanel{
	private Game game;
	
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/NormalTest.png");
	
	private static final BufferedImage TOURNAMENT_BUTTON_BACKGROUND_IMAGE = ImageLoader.loadImage("/NormalTest.png");
	private static final BufferedImage TOURNAMENT_BUTTON_HOVER_IMAGE = ImageLoader.loadImage("/HoverTest.png");
	
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/gradientBackground.jpg");
	//private static final Image NON_TOURNAMENT_BUTTON_BACKGROUND = ImageLoader.loadImage("test.png");
	//private static final Image NON_TOURNAMENT_BUTTON_HOVER = ImageLoader.loadImage("test.png");

	
	public MainMenuPanel(Game game){
		this.game = game;
		
		ImageButton tournamentButton = new ImageButton(TOURNAMENT_BUTTON_BACKGROUND_IMAGE, TOURNAMENT_BUTTON_HOVER_IMAGE) {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				getGame().createMatch(150, 150, 4, 1);
				getGame().switchScreen(Game.MATCH_SCREEN);
				getGame().startMatch();
			}
		};
		
		ImageButton nonTournamentButton = new ImageButton(TOURNAMENT_BUTTON_BACKGROUND_IMAGE, TOURNAMENT_BUTTON_HOVER_IMAGE) {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				getGame().createMatch(50, 50, 4, 1);
				getGame().switchScreen(Game.MATCH_SCREEN);
				getGame().startMatch();
			}
		};
		
		/*
		Border normBorder = BorderFactory.createLineBorder(new Color(0,0,0,170),7);
		Border hovBorder = BorderFactory.createLineBorder(new Color(0,0,0,130),7);
		CustomButton testButton = new CustomButton("TEST BUTTON!", new Color(70,200,220), new Color(70,200,220), normBorder, hovBorder, 200, 40) {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				System.out.println("Test");
			}
		};
		*/
		
		NormalButton testButton = new NormalButton("Add Player", NormalButton.GREEN_THEME) {
			public void mouseClicked(MouseEvent e) {
				System.out.println("Test");
			}
		};
		
		JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.setOpaque(false);
		
		JPanel buttonContainer = new JPanel();
		BoxLayout buttonLayout = new BoxLayout(buttonContainer, BoxLayout.Y_AXIS);
		buttonContainer.setLayout(buttonLayout);
		buttonContainer.add(new FixedSpacerPanel(100, 100));
		buttonContainer.add(tournamentButton);
		buttonContainer.add(new FixedSpacerPanel(100, 30));
		buttonContainer.add(nonTournamentButton);
		buttonContainer.setOpaque(false);
		
		this.setLayout(new BorderLayout());
		this.add(buttonContainer, BorderLayout.CENTER);
		this.add(titleContainer, BorderLayout.NORTH);
		//this.add(testButton, BorderLayout.WEST);
	}
	
	public Game getGame() {
		return this.game;
	}
	
	 @Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	 }
}
