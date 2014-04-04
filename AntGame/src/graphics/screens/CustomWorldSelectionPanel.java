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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Game;
import model.Player;
import model.World;

/**
 * CustomWorldSelectionPanel: A screen for the AntGame. This screen allows players
 * to create a world (by setting various parameters) to play their match on.
 * @author 105957
 * @author 109195
 */
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
	private static final BufferedImage BACK_BUTTON = ImageLoader.loadImage("/GlobalImages/backButton.png");
	private static final BufferedImage BACK_HOVER_BUTTON = ImageLoader.loadImage("/GlobalImages/backButtonHover.png");
	private static final BufferedImage WORLD_EDITOR_IMAGE = ImageLoader.loadImage("/WorldEditorImages/worldDimensions.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/WorldEditorImages/worldEditorTitle.png");
	private static final BufferedImage TICK_IMAGE = ImageLoader.loadImage("/GlobalImages/tick.png");
	private static final BufferedImage CROSS_IMAGE = ImageLoader.loadImage("/GlobalImages/cross.png");
	
	private Game game;
	private JLabel rocksLabel;
	private JLabel foodLabel;
	private JLabel sizeOfAnthillLabel;
	private JTextField firstDimension;
	private JTextField secondDimension;
	private SingleMatchWorldPanel parentPanel;
	private DualImagePanel worldDimensionValidate;
	
	/**
	 * Constructor: Initialises the screen that allows players to create a world
	 * to play their match on.
	 * @param game the ant-game controller that this screen is a part of.
	 * @param parentpanel the SingleMatchWorldPanel that will open this screen.
	 */
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
		
		// Rocks value label
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
		
		// Rocks title
		JPanel rocksTitleContainer = new JPanel();
		BoxLayout rocksTitleLayout = new BoxLayout(rocksTitleContainer, BoxLayout.Y_AXIS);
		rocksTitleContainer.setLayout(rocksTitleLayout);
		rocksTitleContainer.add(new ImagePanel(ROCKS_IMAGE));
		rocksTitleContainer.setOpaque(false);
		
		// Add the elements to the panel
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
				} else if(foodLabel.getText().equals("High")){
					foodLabel.setText("Medium");
				}
			}
		};
		ImageButton foodPlusButton = new ImageButton(PLUS_IMAGE, PLUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(foodLabel.getText().equals("Low")){
					foodLabel.setText("Medium");
				} else if(foodLabel.getText().equals("Medium")){
					foodLabel.setText("High");
				}
			}
		};
		
		// Food title
		JPanel foodTitleContainer = new JPanel();
		BoxLayout foodTitleLayout = new BoxLayout(foodTitleContainer, BoxLayout.Y_AXIS);
		foodTitleContainer.setLayout(foodTitleLayout);
		foodTitleContainer.add(new ImagePanel(FOOD_IMAGE));
		foodTitleContainer.setOpaque(false);
		
		// Add the elements to the panel
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
		
		// Anthill size value label
		sizeOfAnthillLabel = new JLabel("Medium", SwingConstants.CENTER);
		sizeOfAnthillLabel.setForeground(Color.WHITE);
		sizeOfAnthillLabel.setFont(new Font("Helvetica", 0, 25));
		sizeOfAnthillLabel.setMinimumSize(labelDimension);
		sizeOfAnthillLabel.setMaximumSize(labelDimension);
		sizeOfAnthillLabel.setPreferredSize(labelDimension);
		
		// ImageButtons
		ImageButton sizeOfAnthillMinusButton = new ImageButton(MINUS_IMAGE, MINUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(sizeOfAnthillLabel.getText().equals("Medium")){
					sizeOfAnthillLabel.setText("Small");
				} else if(sizeOfAnthillLabel.getText().equals("Large")){
					sizeOfAnthillLabel.setText("Medium");
				}
			}
		};
		
		ImageButton sizeOfAnthillPlusButton = new ImageButton(PLUS_IMAGE, PLUS_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(sizeOfAnthillLabel.getText().equals("Small")){
					sizeOfAnthillLabel.setText("Medium");
				} else if(sizeOfAnthillLabel.getText().equals("Medium")){
					sizeOfAnthillLabel.setText("Large");
				}
			}
		};
		
		// Ant hill title
		JPanel anthillTitleContainer = new JPanel();
		BoxLayout anthillTitleLayout = new BoxLayout(anthillTitleContainer, BoxLayout.Y_AXIS);
		anthillTitleContainer.setLayout(anthillTitleLayout);
		anthillTitleContainer.add(new ImagePanel(ANTHILL_IMAGE));
		anthillTitleContainer.setOpaque(false);

		// Add the elements to the panel
		sizeOfAnthillPanel.add(anthillTitleContainer);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(100, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillMinusButton);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(50, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillLabel);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(50, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillPlusButton);
		sizeOfAnthillPanel.setOpaque(false);
		
		// WorldSize chooser is simply two text areas that allow the user to input
		// the desired world dimensions.
		JPanel worldSizeChooser = new JPanel();
		BoxLayout worldSizeLayout = new BoxLayout(worldSizeChooser, BoxLayout.X_AXIS);
		worldSizeChooser.setLayout(worldSizeLayout);
		
		// First JText SingleMatchWorldPanel
		firstDimension = new JTextField("150", 4);
		firstDimension.setFont(new Font("Helvetica", 0, 25));
		firstDimension.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		firstDimension.setOpaque(false);
		firstDimension.setHorizontalAlignment(JTextField.CENTER);
		firstDimension.setCaretColor(Color.WHITE);
		firstDimension.setForeground(Color.WHITE);
		firstDimension.setBackground(new Color(255,255,255,0));
		firstDimension.setMaximumSize(new Dimension(87,30));
		
		// Middle JLabel
		JLabel plusLabel = new JLabel("X", SwingConstants.CENTER);
		plusLabel.setForeground(Color.WHITE);
		plusLabel.setFont(new Font("Helvetica", 0, 25));
		plusLabel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
		
		// Second text field
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
		
		//WorldDimension validate
		worldDimensionValidate = new DualImagePanel(TICK_IMAGE, CROSS_IMAGE);
		firstDimension.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				validateDimensions();
			}

			public void insertUpdate(DocumentEvent arg0) {
				validateDimensions();
			}

			public void removeUpdate(DocumentEvent arg0) {
				validateDimensions();
			}
		});
		secondDimension.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				validateDimensions();
			}

			public void insertUpdate(DocumentEvent arg0) {
				validateDimensions();
			}

			public void removeUpdate(DocumentEvent arg0) {
				validateDimensions();
			}
		});
		
		
		//add the elements to the panel
		worldSizeChooser.add(worldSizeTitleContainer);
		worldSizeChooser.add(new FixedSpacerPanel(58, 0));
		worldSizeChooser.add(firstDimension);
		worldSizeChooser.add(plusLabel);
		worldSizeChooser.add(secondDimension);
		worldSizeChooser.add(new FixedSpacerPanel(27, 0));
		worldSizeChooser.add(worldDimensionValidate);
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
				String errorMessage = getErrorMessage();
				boolean valid = errorMessage == null;
				if (!valid) {
					JOptionPane.showMessageDialog(CustomWorldSelectionPanel.this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				} else {
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
			}
		};
		
		//Back button
		ImageButton backButton = new ImageButton(BACK_BUTTON, BACK_HOVER_BUTTON){
			public void mouseClicked(MouseEvent e) {
				reset();
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
	
	@Override
	public void update() {
		reset();
	}
	
	/**
	 * Changes the validate image for the dimensions, based on the dimensions validity.
	 */
	private void validateDimensions(){
		String dimension1 = firstDimension.getText().trim();
		String dimension2 = secondDimension.getText().trim();
		int d1;
		int d2;
		try{
			d1 = Integer.parseInt(dimension1);
			d2 = Integer.parseInt(dimension2);
			if(d1<30 || d2 <30){
				worldDimensionValidate.displaySecond();
			}
			else if(d1 >300 || d2>300){
				worldDimensionValidate.displaySecond();
			}
			else{
				worldDimensionValidate.displayFirst();
			}

		}
		catch(NumberFormatException e){
			worldDimensionValidate.displaySecond();
		}
	}
	
	/**
	 * gets the messages associated with validation errors. 
	 * @return a string of errors that need correcting by the user, null if no problems exist.
	 */
	public String getErrorMessage(){
		String output = "";
		String dimension1 = firstDimension.getText().trim();
		String dimension2 = secondDimension.getText().trim();
		int d1;
		int d2;
		
		try{
			d1 = Integer.parseInt(dimension1);
			if(d1 < 30){
				output += "- Width of world too small, needs to be at least 30.\n";
			}
			else if(d1 > 300){
				output += "- Width of world too big, needs to be smaller than 300.\n";
			}
		}
		catch(NumberFormatException e){
			output += "- The width must be an integer.\n";
		}
		try{
			d2 = Integer.parseInt(dimension2);
			if(d2 < 30){
				output += "- Height of world too small, needs to be at least 30.\n";
			}
			else if(d2 > 300){
				output += "- Height of world too big, needs to be smaller than 300.\n";
			}
		}
		catch(NumberFormatException e){
			output += "- The height must be an integer.\n";
		}
		
		if(output.equals("")){
			output = null;
		}
		
		return output;
	}
	
	
	@Override
	public void reset() {
		rocksLabel.setText("Medium");
		foodLabel.setText("Medium");
		sizeOfAnthillLabel.setText("Medium");
		firstDimension.setText("150");
		secondDimension.setText("150");
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
}
