package graphics.screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import graphics.components.DualImagePanel;
import graphics.components.FixedSpacerPanel;
import graphics.components.HexGrid;
import graphics.components.Hexagon;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.utilities.ImageLoader;
import model.AntBrain;
import model.AntBrainReader;
import model.Game;
import model.Player;
import model.State;
import model.World;
import model.WorldReader;

public class SingleMatchWorldPanel extends JPanel implements Screen{
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/SingleMatchWorldPanelImages/selectWorldTitle.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage TICK_IMAGE = ImageLoader.loadImage("/GlobalImages/tick.png");
	private static final BufferedImage CROSS_IMAGE = ImageLoader.loadImage("/GlobalImages/cross.png");
	private static final BufferedImage UPLOAD_BUTTON = ImageLoader.loadImage("/SingleMatchWorldPanelImages/uploadWorldButton.png");
	private static final BufferedImage UPLOAD_ROLL_BUTTON = ImageLoader.loadImage("/SingleMatchWorldPanelImages/uploadWorldButtonHover.png");
	private static final BufferedImage CREATE_BUTTON = ImageLoader.loadImage("/SingleMatchWorldPanelImages/createWorldButton.png");
	private static final BufferedImage CREATE_ROLL_BUTTON = ImageLoader.loadImage("/SingleMatchWorldPanelImages/createWorldButtonHover.png");
	private static final BufferedImage RANDOM_BUTTON = ImageLoader.loadImage("/SingleMatchWorldPanelImages/randomWorldButton.png");
	private static final BufferedImage RANDOM_ROLL_BUTTON = ImageLoader.loadImage("/SingleMatchWorldPanelImages/randomWorldButtonHover.png");
	private static final BufferedImage PLAY_BUTTON = ImageLoader.loadImage("/SingleMatchWorldPanelImages/playButton.png");
	private static final BufferedImage PLAY_ROLL_BUTTON = ImageLoader.loadImage("/SingleMatchWorldPanelImages/playButtonHover.png");
	
	private Game game; 
	private World antWorld;

	private HexGrid grid;
	
	private JScrollPane scrollPane;
	private JFileChooser fc; //This is the file chooser
	private DualImagePanel worldValidateImage;
	
	public SingleMatchWorldPanel(Game game) {
		this.game = game;
		this.grid = new HexGrid(100, 100, 6, 1);
		this.fc = new JFileChooser(); // Default OS file chooser.
		
		// Title Panel
		JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 20)); 
		titleContainer.setOpaque(false);
		
		// Grid Panel, has BorderLayout, grid at CENTER, Buttons EAST
		JPanel gridPanel = new JPanel(new BorderLayout());
		gridPanel.setOpaque(false);
		this.scrollPane = new JScrollPane(grid);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		
		//add the scollPane at the centre
		gridPanel.add(scrollPane, BorderLayout.CENTER);
		gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 55, 55, 50));
		gridPanel.setMaximumSize(new Dimension(600,350));
		gridPanel.setPreferredSize(new Dimension(600,350));
		gridPanel.setMinimumSize(new Dimension(600,350));
		
		//Upload buttons
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0,30, 0, 0));

		buttonsPanel.setOpaque(false);
		BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS);
		buttonsPanel.setLayout(buttonsLayout);
		
		//ImageButtons
		ImageButton randomWorldButton = new ImageButton(RANDOM_BUTTON, RANDOM_ROLL_BUTTON){
			public void mouseClicked(MouseEvent e) {
				int antHillSize = 0;
				while(antHillSize < 5 | antHillSize > 12){
					antHillSize = State.randomInt(12);
				}
				int rockCount = 0;
				while(rockCount < 5 | rockCount > 20){
					rockCount = State.randomInt(20);
				}
				int foodPileCount = 0;
				while(foodPileCount < 4 | foodPileCount > 15){
					foodPileCount = State.randomInt(15);
				}
				World world = World.generateWorld(100, 100, antHillSize, rockCount, foodPileCount);
                setWorld(world);
            	previewWorld();
			}
		};
		randomWorldButton.setAlignmentY(LEFT_ALIGNMENT);
		ImageButton uploadWorldButton = new ImageButton(UPLOAD_BUTTON, UPLOAD_ROLL_BUTTON){
			public void mouseClicked(MouseEvent e) {
				int returnVal = fc.showOpenDialog(SingleMatchWorldPanel.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File worldFile = fc.getSelectedFile();
	                World world = WorldReader.readWorld(worldFile);
	                setWorld(world);
                	previewWorld();
	            }
			}

			
		};
		uploadWorldButton.setAlignmentY(LEFT_ALIGNMENT);
		
		ImageButton createWorldButton = new ImageButton(CREATE_BUTTON, CREATE_ROLL_BUTTON){
			public void mouseClicked(MouseEvent e) {
				getGame().switchScreen(Game.WORLD_EDITOR_SCREEN);
			}
		};
		createWorldButton.setAlignmentY(LEFT_ALIGNMENT);
		
		//Validate label
		JLabel validateLabel = new JLabel("Valid : ");
		validateLabel.setForeground(Color.WHITE);
		validateLabel.setFont(new Font("Helvetica", 0, 25));
		validateLabel.setAlignmentX(CENTER_ALIGNMENT);
		validateLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		
		//Ticks and crosses
		JPanel uploadValidatePanel = new JPanel();
		JPanel uploadValidateImagePanel = new JPanel();
        worldValidateImage = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
        uploadValidateImagePanel.setOpaque(false);
        uploadValidateImagePanel.add(worldValidateImage);
		uploadValidatePanel.setOpaque(false);
		uploadValidateImagePanel.setBorder(BorderFactory.createEmptyBorder(13,0,0,0));
        worldValidateImage.displaySecond();
        //Add the upload button and the validation to a common panel
        uploadValidatePanel.add(validateLabel);
        uploadValidatePanel.add(uploadValidateImagePanel);
        uploadValidatePanel.setOpaque(false);
        
        //add randomize button#
        buttonsPanel.add(new FixedSpacerPanel(0,35));
        buttonsPanel.add(randomWorldButton);
        buttonsPanel.add(new FixedSpacerPanel(0,25));
        //add upload button
        buttonsPanel.add(uploadWorldButton); // the upload and tick/cross
        buttonsPanel.add(new FixedSpacerPanel(0,25));
        //add create button
        buttonsPanel.add(createWorldButton);
        buttonsPanel.add(new FixedSpacerPanel(0,5));
        //add validate part
        buttonsPanel.add(uploadValidatePanel);
        //add play button
        
		
//        JPanel bottomPanel = new JPanel(new BorderLayout());
//        bottomPanel.setOpaque(false);
//        bottomPanel.add(buttonsPanel, BorderLayout.NORTH);
        
        //Add the buttons to the grid panel
        gridPanel.add(buttonsPanel, BorderLayout.EAST);
        
        //Create the Play button
        ImageButton playButton = new ImageButton(PLAY_BUTTON, PLAY_ROLL_BUTTON){
			public void mouseClicked(MouseEvent e) {
				String errorMessage = getErrorMessage();
				boolean valid = (errorMessage == null);
				if (!valid) {
					JOptionPane.showMessageDialog(SingleMatchWorldPanel.this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					getGame().getCurrentMatch().setWorld(antWorld);
					getGame().createMatchPanelGrid(antWorld.sizeX, antWorld.sizeY, 2, 1);
					getGame().switchScreen(Game.MATCH_SCREEN);
					getGame().startMatch();
				}
			}
		};
	    
        JPanel playPanel = new JPanel();
        playPanel.setOpaque(false);
        playPanel.add(playButton);
        playPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        buttonsPanel.add(playPanel);
		
		this.setLayout(new BorderLayout());
		//Adds the titleContainer
		this.add(titleContainer, BorderLayout.NORTH);
		//Adds the world grid and buttons
		this.add(gridPanel, BorderLayout.CENTER);
		
	}
	
	public void previewWorld() {
		if (antWorld != null) {
			Hexagon[][] gridBuffer = new Hexagon[antWorld.sizeX][antWorld.sizeY];
			
			int cols = gridBuffer.length;
			int rows = gridBuffer[0].length;
			
			for (int x = 0; x < cols; x++) {
				for (int y = 0; y < rows; y++) {
					Hexagon h = new Hexagon(x, y, 6, 1);
					h.setFillColor(Game.getTileColor(antWorld.getTile(x, y)));
					gridBuffer[x][y] = h;
				}
			}
			grid.setHexagonGrid(gridBuffer);
			grid.refreshDimensions();
		} else {
			grid.newGrid(150, 150, 6, 1);
			grid.refreshDimensions();
			System.out.println("THE GRID SHOULD BE EMPTY!");
		}
		
		scrollPane.revalidate();
		scrollPane.repaint();
	}
	
	public String getErrorMessage(){
		String output = "";
		if(!worldValidateImage.isFirstShown()){
			output += "The world is invalid!";
		}
		if(output.equals("")){
			output = null;
		}
		return output;
	}
	
	public Game getGame(){
		return game;
	}
	
	public void setGrid(HexGrid grid) {
		this.grid = grid;
		this.scrollPane.revalidate();
		this.scrollPane.repaint();
	}
	
	public void setWorld(World world) {
		if (world == null) {
        	worldValidateImage.displaySecond();
        } else {
        	this.antWorld = world;
        	worldValidateImage.displayFirst();
        }
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	}

	public static void main(String[] args) {
		//Add content to the window.
		JFrame frame = new JFrame("World Selection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 576);
		frame.add(new SingleMatchWorldPanel(null));
		frame.setResizable(false);

		//Display the window.
		frame.setVisible(true);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		antWorld = null;
		//have the grid go back to its original state
		this.grid = new HexGrid(150, 150, 6, 1);
		//the default validation for the uploaded world file is a cross
		this.worldValidateImage.displaySecond();
	}
}


