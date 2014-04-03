package graphics.screens;

import graphics.components.*;
import graphics.utilities.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Game;
import model.World;

public class CustomWorldSelectionPanel extends JPanel implements Screen{
	
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
	private static final BufferedImage WORLD_EDITOR_IMAGE = ImageLoader.loadImage("/WorldEditorImages/worldDimensions.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/WorldEditorImages/worldEditorTitle.png");
	
	
	private Game game;
	private JLabel rocksLabel;
	private JLabel foodLabel;
	private JLabel sizeOfAnthillLabel;
	private JTextField firstDimension;
	private JTextField secondDimension;
	private SingleMatchWorldPanel parentPanel;
	
	public CustomWorldSelectionPanel(Game game, SingleMatchWorldPanel parentPanel){
		
		this.game = game;
		this.parentPanel = parentPanel;
		this.setLayout(new BorderLayout());
		
		Dimension labelDimension = new Dimension(100, 40); 
		
		// Title Panel
		JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 40));
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
		rocksLabel = new JLabel("Medium", SwingConstants.CENTER);
		rocksLabel.setForeground(Color.WHITE);
		rocksLabel.setFont(new Font("Helvetica", 0, 25));
		rocksLabel.setMinimumSize(labelDimension);
		rocksLabel.setMaximumSize(labelDimension);
		rocksLabel.setPreferredSize(labelDimension);

		
		//ImageButtons
		ImageButton rocksMinusButton = new ImageButton(MINUS_IMAGE, MINUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(rocksLabel.getText().equals("Medium")){
					rocksLabel.setText("Low");
				}
				else if(rocksLabel.getText().equals("High")){
					rocksLabel.setText("Medium");
				}
				else{
					//do nothing
				}
			}
		};
		ImageButton rocksPlusButton = new ImageButton(PLUS_IMAGE, PLUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(rocksLabel.getText().equals("Low")){
					rocksLabel.setText("Medium");
				}
				else if(rocksLabel.getText().equals("Medium")){
					rocksLabel.setText("High");
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
		rocksTitleContainer.add(new ImagePanel(ROCKS_IMAGE));
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
		foodLabel = new JLabel("Medium", SwingConstants.CENTER);
		foodLabel.setForeground(Color.WHITE);
		foodLabel.setFont(new Font("Helvetica", 0, 25));
		foodLabel.setMinimumSize(labelDimension);
		foodLabel.setMaximumSize(labelDimension);
		foodLabel.setPreferredSize(labelDimension);
		
		//ImageButtons
		ImageButton foodMinusButton = new ImageButton(MINUS_IMAGE, MINUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(foodLabel.getText().equals("Medium")){
					foodLabel.setText("Low");
				}
				else if(foodLabel.getText().equals("High")){
					foodLabel.setText("Medium");
				}
				else{
					//do nothing
				}
			}
		};
		ImageButton foodPlusButton = new ImageButton(PLUS_IMAGE, PLUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(foodLabel.getText().equals("Low")){
					foodLabel.setText("Medium");
				}
				else if(foodLabel.getText().equals("Medium")){
					foodLabel.setText("High");
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
		foodTitleContainer.add(new ImagePanel(FOOD_IMAGE));
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
		sizeOfAnthillLabel = new JLabel("Medium", SwingConstants.CENTER);
		sizeOfAnthillLabel.setForeground(Color.WHITE);
		sizeOfAnthillLabel.setFont(new Font("Helvetica", 0, 25));
		sizeOfAnthillLabel.setMinimumSize(labelDimension);
		sizeOfAnthillLabel.setMaximumSize(labelDimension);
		sizeOfAnthillLabel.setPreferredSize(labelDimension);
		
		//ImageButtons
		ImageButton sizeOfAnthillMinusButton = new ImageButton(MINUS_IMAGE, MINUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(sizeOfAnthillLabel.getText().equals("Medium")){
					sizeOfAnthillLabel.setText("Small");
				}
				else if(sizeOfAnthillLabel.getText().equals("Large")){
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
					sizeOfAnthillLabel.setText("Large");
				}
				else{
					//do nothing
				}
			}
		};
		
		//Anthill title
		JPanel anthillTitleContainer = new JPanel();
		BoxLayout anthillTitleLayout = new BoxLayout(anthillTitleContainer, BoxLayout.Y_AXIS);
		anthillTitleContainer.setLayout(anthillTitleLayout);
		anthillTitleContainer.add(new ImagePanel(ANTHILL_IMAGE));
		anthillTitleContainer.setOpaque(false);

		//Add the elements to the panel
		sizeOfAnthillPanel.add(anthillTitleContainer);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(100, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillMinusButton);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(50, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillLabel);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(50, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillPlusButton);
		sizeOfAnthillPanel.setOpaque(false);
		
		//WorldSize chooser is simply two text areas that allow the user to input
		//the desired world dimensions
		JPanel worldSizeChooser = new JPanel();
		BoxLayout worldSizeLayout = new BoxLayout(worldSizeChooser, BoxLayout.X_AXIS);
		worldSizeChooser.setLayout(worldSizeLayout);
		
		//First JText Area
		firstDimension = new JTextField("150", 4);
		firstDimension.setFont(new Font("Helvetica", 0, 25));
		firstDimension.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		firstDimension.setOpaque(false);
		firstDimension.setHorizontalAlignment(JTextField.CENTER);
		firstDimension.setCaretColor(Color.WHITE);
		firstDimension.setForeground(Color.WHITE);
		firstDimension.setBackground(new Color(255,255,255,0));
		firstDimension.setMaximumSize(new Dimension(87,30));

		
		//MiddleJLabel
		JLabel plusLabel = new JLabel("X", SwingConstants.CENTER);
		plusLabel.setForeground(Color.WHITE);
		plusLabel.setFont(new Font("Helvetica", 0, 25));
		plusLabel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		
		//Second text area
		secondDimension = new JTextField("150", 4);
		secondDimension.setFont(new Font("Helvetica", 0, 25));
		secondDimension.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		secondDimension.setOpaque(false);
		secondDimension.setHorizontalAlignment(JTextField.CENTER);
		secondDimension.setCaretColor(Color.WHITE);
		secondDimension.setForeground(Color.WHITE);
		secondDimension.setBackground(new Color(255,255,255,0));
		secondDimension.setMaximumSize(new Dimension(87,30));
		
		//WorldSize title
		JPanel worldSizeTitleContainer = new JPanel();
		BoxLayout worldSizeTitleLayout = new BoxLayout(worldSizeTitleContainer, BoxLayout.Y_AXIS);
		worldSizeTitleContainer.setLayout(worldSizeTitleLayout);
		worldSizeTitleContainer.add(new ImagePanel(WORLD_EDITOR_IMAGE));
		worldSizeTitleContainer.setOpaque(false);
		
		//add the elements to the panel
		worldSizeChooser.add(worldSizeTitleContainer);
		worldSizeChooser.add(new FixedSpacerPanel(58, 0));
		worldSizeChooser.add(firstDimension);
		worldSizeChooser.add(plusLabel);
		worldSizeChooser.add(secondDimension);
		worldSizeChooser.add(new FixedSpacerPanel(27, 0));
		worldSizeChooser.setOpaque(false);
		
		//add the elements to the selector Panel
		selectorPanel.add(rocksPanel);
		selectorPanel.add(new FixedSpacerPanel(140, 40));
		selectorPanel.add(foodPanel);
		selectorPanel.add(new FixedSpacerPanel(140, 40));
		selectorPanel.add(sizeOfAnthillPanel);
		selectorPanel.add(new FixedSpacerPanel(140, 40));
		selectorPanel.add(worldSizeChooser);
		selectorPanel.add(new FixedSpacerPanel(140, 0));
		selectorPanel.setOpaque(false);
		
		//Bottom buttons
		JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottomButtons.setOpaque(false);
		
		//Create button
		ImageButton createButton = new ImageButton(CREATE_BUTTON, CREATE_HOVER_BUTTON){
			public void mouseClicked(MouseEvent e) {
				String antHillString = sizeOfAnthillLabel.getText();
				int antHillSize = 6;
				if(antHillString.equals("Large")){
					antHillSize = 9;
				}else{
					if(antHillString.equals("Medium")){
						antHillSize = 6;
					}else{
						if(antHillString.equals("Small")){
							antHillSize = 4;
						}
					}
				}
				String rockCountString = rocksLabel.getText();
				int rockCount = 0;
				if(rockCountString.equals("High")){
					rockCount = 20;
				}else{
					if(rockCountString.equals("Medium")){
						rockCount = 14;
					}else{
						if(rockCountString.equals("Low")){
							rockCount = 8;
						}
					}
				}
				String foodPileString = foodLabel.getText();
				int foodPileCount = 0;
				if(foodPileString.equals("High")){
					foodPileCount = 14;
				}else{
					if(foodPileString.equals("Medium")){
						foodPileCount = 6;
					}else{
						if(foodPileString.equals("Low")){
							foodPileCount = 3;
						}
					}
				}
				World antWorld = World.generateWorld(Integer.parseInt(firstDimension.getText()), Integer.parseInt(secondDimension.getText()), antHillSize, rockCount, foodPileCount); 
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
		bottomButtons.add(backButton);
		bottomButtons.add(new FixedSpacerPanel(80, 20));
		bottomButtons.add(createButton);
		bottomButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		add(titleContainer, BorderLayout.NORTH);
		add(selectorPanel, BorderLayout.CENTER);
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
		rocksLabel.setText("Low");
		foodLabel.setText("Low");
		sizeOfAnthillLabel.setText("Small");
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
	public SingleMatchWorldPanel getWorldScreen() {
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
		frame.add(new CustomWorldSelectionPanel(null, null));
		frame.setResizable(false);

		//Display the window.
		frame.setVisible(true);
	}

	@Override
	public void update() {
		reset();
	}

	@Override
	public void reset() {
		rocksLabel.setText("Medium");
		foodLabel.setText("Medium");
		sizeOfAnthillLabel.setText("Medium");
		firstDimension.setText("150");
		secondDimension.setText("150");
	}
}
