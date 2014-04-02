package graphics.screens;

import graphics.components.*;
import graphics.utilities.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Game;
import model.World;

public class WorldEditorPanel extends JPanel{
	
	private static final BufferedImage PLUS_IMAGE = ImageLoader.loadImage("/WorldEditorImages/plusButton.png");
	private static final BufferedImage PLUS_HOVER_IMAGE = ImageLoader.loadImage("/WorldEditorImages/plusButtonHover.png");
	private static final BufferedImage MINUS_IMAGE = ImageLoader.loadImage("/WorldEditorImages/minusButton.png");
	private static final BufferedImage MINUS_HOVER_IMAGE = ImageLoader.loadImage("/WorldEditorImages/minusButtonHover.png");
	private static final BufferedImage ROCKS_IMAGE = ImageLoader.loadImage("/WorldEditorImages/numberOfRocks.png");
	private static final BufferedImage FOOD_IMAGE = ImageLoader.loadImage("/WorldEditorImages/amountOfFood.png");
	private static final BufferedImage ANTHILL_IMAGE = ImageLoader.loadImage("/WorldEditorImages/sizeOfAnthill.png");
	private static final BufferedImage CREATE_BUTTON = ImageLoader.loadImage("/WorldEditorImages/createButton.png");
	private static final BufferedImage CREATE_HOVER_BUTTON = ImageLoader.loadImage("/WorldEditorImages/createButtonHover.png");
	private static final BufferedImage BACK_BUTTON = ImageLoader.loadImage("/WorldEditorImages/backButton.png");
	private static final BufferedImage BACK_HOVER_BUTTON = ImageLoader.loadImage("/WorldEditorImages/backButtonHover.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/WorldEditorImages/worldEditorTitle.png");
	
	private Game game;
	private JLabel rocksLabel;
	private JLabel foodLabel;
	private JLabel sizeOfAnthillLabel;
	private WorldSelectionPanel parentPanel;
	
	public WorldEditorPanel(Game game, WorldSelectionPanel parentPanel){
		
		this.game = game;
		this.parentPanel = parentPanel;
		this.setLayout(new BorderLayout());
		
		// Title Panel
		JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 20)); 
		titleContainer.setOpaque(false);
		
		//Panel containing the selector (Rocks,Food and Anthill size)
		JPanel selectorPanel = new JPanel();
		BoxLayout selectorLayout = new BoxLayout(selectorPanel, BoxLayout.Y_AXIS);
		selectorPanel.setLayout(selectorLayout);
		
		//Number of Rocks Panel
		//Consists of a - button, a label with an int and a + button
		JPanel rocksPanel = new JPanel();
		BoxLayout rocksLayout = new BoxLayout(rocksPanel, BoxLayout.X_AXIS);
		rocksPanel.setLayout(rocksLayout);
		//Label
		rocksLabel = new JLabel("Small",SwingConstants.CENTER);
		rocksLabel.setForeground(Color.WHITE);
		rocksLabel.setFont(new Font("Helvetica", 0, 25));
		rocksLabel.setAlignmentX(CENTER_ALIGNMENT);
		rocksLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		rocksLabel.setMinimumSize(new Dimension(100,100));
		rocksLabel.setMaximumSize(new Dimension(100,100));
		
		//ImageButtons
		ImageButton rocksMinusButton = new ImageButton(MINUS_IMAGE, MINUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(rocksLabel.getText().equals("Medium")){
					rocksLabel.setText("Small");
				}
				else if(rocksLabel.getText().equals("Big")){
					rocksLabel.setText("Medium");
				}
				else{
					//do nothing
				}
			}
		};
		ImageButton rocksPlusButton = new ImageButton(PLUS_IMAGE, PLUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(rocksLabel.getText().equals("Small")){
					rocksLabel.setText("Medium");
				}
				else if(rocksLabel.getText().equals("Medium")){
					rocksLabel.setText("Big");
				}
				else{
					//do nothing
				}
			}
		};
		
		//rocks title
		JPanel rocksTitleContainer = new JPanel();
		BoxLayout rocksTitleLayout = new BoxLayout(rocksTitleContainer, BoxLayout.Y_AXIS);
		rocksTitleContainer.setLayout(rocksTitleLayout);
		rocksTitleContainer.add(new FixedSpacerPanel(100, 20));
		rocksTitleContainer.add(new ImagePanel(ROCKS_IMAGE));
		rocksTitleContainer.add(new FixedSpacerPanel(100, 20)); 
		rocksTitleContainer.setOpaque(false);
		
		//add the elements to the panel
		rocksPanel.add(rocksTitleContainer);
		rocksPanel.add(new FixedSpacerPanel(54, 0));
		rocksPanel.add(rocksMinusButton);
		rocksPanel.add(new FixedSpacerPanel(50, 0));
		rocksPanel.add(rocksLabel);
		rocksPanel.add(new FixedSpacerPanel(50, 0));
		rocksPanel.add(rocksPlusButton);
		
		rocksPanel.setOpaque(false);
		
		//Number of Food panel
		JPanel foodPanel = new JPanel(new BorderLayout());
		BoxLayout foodLayout = new BoxLayout(foodPanel, BoxLayout.X_AXIS);
		foodPanel.setLayout(foodLayout);
		
		//Label
		foodLabel = new JLabel("Small", SwingConstants.CENTER);
		foodLabel.setForeground(Color.WHITE);
		foodLabel.setFont(new Font("Helvetica", 0, 25));
		foodLabel.setAlignmentX(CENTER_ALIGNMENT);
		foodLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		foodLabel.setMinimumSize(new Dimension(100,100));
		foodLabel.setMaximumSize(new Dimension(100,100));
		
		//ImageButtons
		ImageButton foodMinusButton = new ImageButton(MINUS_IMAGE, MINUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(foodLabel.getText().equals("Medium")){
					foodLabel.setText("Small");
				}
				else if(foodLabel.getText().equals("Big")){
					foodLabel.setText("Medium");
				}
				else{
					//do nothing
				}
			}
		};
		ImageButton foodPlusButton = new ImageButton(PLUS_IMAGE, PLUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(foodLabel.getText().equals("Small")){
					foodLabel.setText("Medium");
				}
				else if(foodLabel.getText().equals("Medium")){
					foodLabel.setText("Big");
				}
				else{
					//do nothing
				}
			}
		};
		
		//food title
		JPanel foodTitleContainer = new JPanel();
		BoxLayout foodTitleLayout = new BoxLayout(foodTitleContainer, BoxLayout.Y_AXIS);
		foodTitleContainer.setLayout(foodTitleLayout);
		foodTitleContainer.add(new FixedSpacerPanel(100, 20));
		foodTitleContainer.add(new ImagePanel(FOOD_IMAGE));
		foodTitleContainer.add(new FixedSpacerPanel(100, 20)); 
		foodTitleContainer.setOpaque(false);
		
		//add the elements to the panel
		foodPanel.add(foodTitleContainer);
		foodPanel.add(new FixedSpacerPanel(68, 0));
		foodPanel.add(foodMinusButton);
		foodPanel.add(new FixedSpacerPanel(50, 0));
		foodPanel.add(foodLabel);
		foodPanel.add(new FixedSpacerPanel(50, 0));
		foodPanel.add(foodPlusButton);
		
		foodPanel.setOpaque(false);
		
		//Size of Anthill panel
		JPanel sizeOfAnthillPanel = new JPanel();
		BoxLayout anthillLayout = new BoxLayout(sizeOfAnthillPanel, BoxLayout.X_AXIS);
		sizeOfAnthillPanel.setLayout(anthillLayout);
		
		//Label
		sizeOfAnthillLabel = new JLabel("Small", SwingConstants.CENTER);
		sizeOfAnthillLabel.setForeground(Color.WHITE);
		sizeOfAnthillLabel.setFont(new Font("Helvetica", 0, 25));
		sizeOfAnthillLabel.setAlignmentX(CENTER_ALIGNMENT);
		sizeOfAnthillLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		sizeOfAnthillLabel.setMinimumSize(new Dimension(100,100));
		sizeOfAnthillLabel.setMaximumSize(new Dimension(100,100));
		
		//ImageButtons
		ImageButton sizeOfAnthillMinusButton = new ImageButton(MINUS_IMAGE, MINUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(sizeOfAnthillLabel.getText().equals("Medium")){
					sizeOfAnthillLabel.setText("Small");
				}
				else if(sizeOfAnthillLabel.getText().equals("Big")){
					sizeOfAnthillLabel.setText("Medium");
				}
				else{
					//do nothing
				}
			}
		};
		ImageButton sizeOfAnthillPlusButton = new ImageButton(PLUS_IMAGE, PLUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(sizeOfAnthillLabel.getText().equals("Small")){
					sizeOfAnthillLabel.setText("Medium");
				}
				else if(sizeOfAnthillLabel.getText().equals("Medium")){
					sizeOfAnthillLabel.setText("Big");
				}
				else{
					//do nothing
				}
			}
		};
		
		//food title
		JPanel anthillTitleContainer = new JPanel();
		BoxLayout anthillTitleLayout = new BoxLayout(anthillTitleContainer, BoxLayout.Y_AXIS);
		anthillTitleContainer.setLayout(anthillTitleLayout);
		anthillTitleContainer.add(new FixedSpacerPanel(100, 20));
		anthillTitleContainer.add(new ImagePanel(ANTHILL_IMAGE));
		anthillTitleContainer.add(new FixedSpacerPanel(100, 20)); 
		anthillTitleContainer.setOpaque(false);

		//add the elements to the panel
		sizeOfAnthillPanel.add(anthillTitleContainer);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(100, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillMinusButton);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(50, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillLabel);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(50, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillPlusButton);
		
		sizeOfAnthillPanel.setOpaque(false);
		
		//add the elements to the selector Panel
		selectorPanel.add(rocksPanel);
		selectorPanel.add(foodPanel);
		selectorPanel.add(sizeOfAnthillPanel);
		selectorPanel.setOpaque(false);
		
		//Bottom buttons
		JPanel bottomButtons = new JPanel();
		BoxLayout bottomLayout = new BoxLayout(bottomButtons, BoxLayout.Y_AXIS);
		bottomButtons.setLayout(bottomLayout);
		bottomButtons.setOpaque(false);
		
		//Create button
		ImageButton createButton = new ImageButton(CREATE_BUTTON, CREATE_HOVER_BUTTON){
			public void mouseClicked(MouseEvent e) {
				// generate the world
				// World world = generateWorld(params)
				World antWorld = null; 
				getWorldScreen().setWorld(antWorld);
				getWorldScreen().previewWorld();
				getGame().switchScreen(Game.WORLD_SELECTION_SCREEN);
			}
		};
		
		//Back button
		ImageButton backButton = new ImageButton(BACK_BUTTON, BACK_HOVER_BUTTON){
			public void mouseClicked(MouseEvent e) {
				resetScreen();
				getGame().switchScreen(Game.WORLD_SELECTION_SCREEN);
			}
		};
		
		//add buttons to panel
		bottomButtons.add(createButton);
		bottomButtons.add(new FixedSpacerPanel(20, 20));
		bottomButtons.add(backButton);
		bottomButtons.add(new FixedSpacerPanel(20, 20));
		
		add(titleContainer, BorderLayout.NORTH);
		add(selectorPanel, BorderLayout.CENTER);
		//add spacer panels for looking good
		add(new FixedSpacerPanel(78, 50), BorderLayout.WEST);
		add(new FixedSpacerPanel(100, 50), BorderLayout.EAST);
		add(bottomButtons, BorderLayout.SOUTH);
	}
	
	/**
	 * Overridden to paint the background image.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	}
	
	/**
	 * This method simply resets the values of various parameters
	 * back to their default values.
	 * To be used when switching screen.
	 * No need for a button that calls the method, as it is only called on code-level.
	 */
	public void resetScreen() {
		//TODO
	}
	
	/**
	 * Get the Game model linked with screen.
	 * @return the Game model linked with screen.
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Returns the world selection screen that this panel is linked to.
	 * @return the world selection screen that this panel is linked to.
	 */
	public WorldSelectionPanel getWorldScreen() {
		return parentPanel;
	}
	
	/**
	 * Used to test this particular screen without the need for a Game model.
	 * @param args
	 */
	public static void main(String[] args){
		//Add content to the window.
		JFrame frame = new JFrame("World Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 576);
		frame.add(new WorldEditorPanel(null, null));
		frame.setResizable(false);

		//Display the window.
		frame.setVisible(true);
	}
}
