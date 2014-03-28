package graphics.screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import graphics.components.DualImagePanel;
import graphics.components.FixedSpacerPanel;
import graphics.components.HexGrid;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.utilities.ImageLoader;
import model.Game;

public class WorldSelectionPanel extends JPanel{
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/NormalTest.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/MainMenuPanelImages/background.jpg");
	private static final BufferedImage TICK_IMAGE = ImageLoader.loadImage("/tick_test.png");
	private static final BufferedImage CROSS_IMAGE = ImageLoader.loadImage("/cross_test.png");
	private static final BufferedImage UPLOAD_BUTTON = ImageLoader.loadImage("/WorldSelectionPanelImages/uploadFileButton.png");
	private static final BufferedImage UPLOAD_ROLL_BUTTON = ImageLoader.loadImage("/WorldSelectionPanelImages/uploadFileButtonHover.png");
	private static final BufferedImage CREATE_BUTTON = ImageLoader.loadImage("/WorldSelectionPanelImages/createWorldButton.png");
	private static final BufferedImage CREATE_ROLL_BUTTON = ImageLoader.loadImage("/WorldSelectionPanelImages/createWorldButtonHover.png");
	private static final BufferedImage RANDOM_BUTTON = ImageLoader.loadImage("/WorldSelectionPanelImages/randomizeWorldButton.png");
	private static final BufferedImage RANDOM_ROLL_BUTTON = ImageLoader.loadImage("/WorldSelectionPanelImages/randomizeWorldButtonHover.png");
	private static final BufferedImage PLAY_BUTTON = ImageLoader.loadImage("/WorldSelectionPanelImages/playButton.png");
	private static final BufferedImage PLAY_ROLL_BUTTON = ImageLoader.loadImage("/WorldSelectionPanelImages/playButtonHover.png");
	
	private Game game; 
	private HexGrid grid;
	private HexGrid gridBuffer;
	private JScrollPane scrollPane;
	private DualImagePanel worldValidateImage;
	
	public WorldSelectionPanel(Game game) {
		this.game = game;
		this.grid = new HexGrid(150, 150, 6, 1);
		
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
		scrollPane.setMinimumSize(new Dimension(600, 350));
		scrollPane.setPreferredSize(new Dimension(600, 350));
		scrollPane.setMaximumSize(new Dimension(600, 350));
		scrollPane.setAlignmentX(CENTER_ALIGNMENT);
		
		//add the scollPane at the centre
		gridPanel.add(scrollPane, BorderLayout.CENTER);
		gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 15, 50));
		gridPanel.setMaximumSize(new Dimension(600,350));
		gridPanel.setPreferredSize(new Dimension(600,350));
		gridPanel.setMinimumSize(new Dimension(600,350));
		
		
		//Upload buttons
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.setOpaque(false);
//		BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.X_AXIS);
//		buttonsPanel.setLayout(buttonsLayout);
		
		//ImageButtons
		ImageButton randomWorldButton = new ImageButton(RANDOM_BUTTON, RANDOM_ROLL_BUTTON){
			public void mouseClicked(MouseEvent e) {
				//sads
			}
		};
//		randomWorldButton.setAlignmentY(CENTER_ALIGNMENT);
		ImageButton uploadWorldButton = new ImageButton(UPLOAD_BUTTON, UPLOAD_ROLL_BUTTON){
			public void mouseClicked(MouseEvent e) {
				//sads
			}
		};
//		uploadWorldButton.setAlignmentY(CENTER_ALIGNMENT);
		ImageButton createWorldButton = new ImageButton(CREATE_BUTTON, CREATE_ROLL_BUTTON){
			public void mouseClicked(MouseEvent e) {
				//sads
			}
		};
//		createWorldButton.setAlignmentY(CENTER_ALIGNMENT);
		
		//Ticks and crosses
		JPanel uploadValidatePanel = new JPanel();
		uploadValidatePanel.setOpaque(false);
        worldValidateImage = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
        worldValidateImage.displaySecond();
        //Add the upload button and the validation to a common panel
        uploadValidatePanel.add(uploadWorldButton);
        uploadValidatePanel.add(worldValidateImage);
        
        buttonsPanel.add(randomWorldButton);
        buttonsPanel.add(new FixedSpacerPanel(100,0));
        buttonsPanel.add(uploadValidatePanel); // the upload and tick/cross
        buttonsPanel.add(new FixedSpacerPanel(100,0));
        buttonsPanel.add(createWorldButton);
		
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(buttonsPanel, BorderLayout.NORTH);
        
        //Create the Play button
        ImageButton playButton = new ImageButton(PLAY_BUTTON, PLAY_ROLL_BUTTON){
			public void mouseClicked(MouseEvent e) {
				 getGame().createMatch(150, 150, 4, 1);
				 getGame().switchScreen(Game.MATCH_SCREEN);
				 getGame().startMatch();
			}
		};
	       	
        JPanel playPanel = new JPanel();
        playPanel.setOpaque(false);
        playPanel.add(playButton);
        playPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        bottomPanel.add(playPanel, BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		//Adds the titleContainer
		this.add(titleContainer, BorderLayout.NORTH);
		//Adds the world grid
		this.add(gridPanel, BorderLayout.CENTER);
		//Adds the upload button
		this.add(bottomPanel, BorderLayout.SOUTH);
		
	}

	public Game getGame(){
		return game;
	}
	
	public void setGrid(HexGrid grid) {
		this.grid = grid;
		this.scrollPane.revalidate();
		this.scrollPane.repaint();
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
		frame.add(new WorldSelectionPanel(null));
		frame.setResizable(false);

		//Display the window.
		frame.setVisible(true);
	}
}


