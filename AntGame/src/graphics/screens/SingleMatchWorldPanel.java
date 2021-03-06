package graphics.screens;

import graphics.components.DualImagePanel;
import graphics.components.FixedSpacerPanel;
import graphics.components.HexGrid;
import graphics.components.Hexagon;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.utilities.ImageLoader;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import model.World;
import model.WorldReader;
import controller.Game;

/**
 * SingleMatchWorldPanel: A screen for the AntGame. This screen allows the players to select a world to use for their match.
 */
public class SingleMatchWorldPanel extends JPanel implements Screen {

	private static final long serialVersionUID = -3081399218987980563L;
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
	private static final BufferedImage BACK_BUTTON = ImageLoader.loadImage("/GlobalImages/backButton.png");
	private static final BufferedImage BACK_HOVER_BUTTON = ImageLoader.loadImage("/GlobalImages/backButtonHover.png");

	private Game game;
	private World antWorld;

	private HexGrid grid;

	private JScrollPane scrollPane;
	private JFileChooser fc; // This is the file chooser
	private DualImagePanel worldValidateImage;

	/**
	 * Constructor: Initialises the screen that allows players to choose a world to play on.
	 * 
	 * @param game
	 *            the ant-game controller that this screen is a part of.
	 */
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

		// add the scollPane at the centre
		gridPanel.add(scrollPane, BorderLayout.CENTER);
		gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 55, 55, 50));
		gridPanel.setMaximumSize(new Dimension(600, 350));
		gridPanel.setPreferredSize(new Dimension(600, 350));
		gridPanel.setMinimumSize(new Dimension(600, 350));

		// Upload buttons
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

		buttonsPanel.setOpaque(false);
		BoxLayout buttonsLayout = new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS);
		buttonsPanel.setLayout(buttonsLayout);

		// ImageButtons
		ImageButton randomWorldButton = new ImageButton(RANDOM_BUTTON, RANDOM_ROLL_BUTTON) {
			private static final long serialVersionUID = 1849706846636524649L;

			public void mouseClicked(MouseEvent e) {
				int antHillSize = 0;
				while (antHillSize < 5 | antHillSize > 12) {
					antHillSize = Game.randomInt(12);
				}
				int rockCount = 0;
				while (rockCount < 5 | rockCount > 20) {
					rockCount = Game.randomInt(20);
				}
				int foodPileCount = 0;
				while (foodPileCount < 4 | foodPileCount > 15) {
					foodPileCount = Game.randomInt(15);
				}
				World world = World.generateWorld(100, 100, antHillSize, rockCount, foodPileCount);
				setWorld(world);
				previewWorld();
			}
		};
		randomWorldButton.setAlignmentY(LEFT_ALIGNMENT);
		ImageButton uploadWorldButton = new ImageButton(UPLOAD_BUTTON, UPLOAD_ROLL_BUTTON) {
			private static final long serialVersionUID = 6938802958972379166L;

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

		ImageButton createWorldButton = new ImageButton(CREATE_BUTTON, CREATE_ROLL_BUTTON) {
			private static final long serialVersionUID = 5737850965800088545L;

			public void mouseClicked(MouseEvent e) {
				getGame().switchScreen(Game.WORLD_EDITOR_SCREEN);
			}
		};
		createWorldButton.setAlignmentY(LEFT_ALIGNMENT);

		// Validate label
		JLabel validateLabel = new JLabel("Valid : ");
		validateLabel.setForeground(Color.WHITE);
		validateLabel.setFont(new Font("Helvetica", 0, 25));
		validateLabel.setAlignmentX(CENTER_ALIGNMENT);
		validateLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		// Ticks and crosses
		JPanel uploadValidatePanel = new JPanel();
		JPanel uploadValidateImagePanel = new JPanel();
		worldValidateImage = new DualImagePanel(TICK_IMAGE, CROSS_IMAGE);
		uploadValidateImagePanel.setOpaque(false);
		uploadValidateImagePanel.add(worldValidateImage);
		uploadValidatePanel.setOpaque(false);
		uploadValidateImagePanel.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
		worldValidateImage.displaySecond();
		// Add the upload button and the validation to a common panel
		uploadValidatePanel.add(validateLabel);
		uploadValidatePanel.add(uploadValidateImagePanel);
		uploadValidatePanel.setOpaque(false);

		// add randomize button#
		buttonsPanel.add(new FixedSpacerPanel(0, 5));
		buttonsPanel.add(randomWorldButton);
		buttonsPanel.add(new FixedSpacerPanel(0, 25));
		// add upload button
		buttonsPanel.add(uploadWorldButton); // the upload and tick/cross
		buttonsPanel.add(new FixedSpacerPanel(0, 25));
		// add create button
		buttonsPanel.add(createWorldButton);
		buttonsPanel.add(new FixedSpacerPanel(0, 5));
		// add validate part
		buttonsPanel.add(uploadValidatePanel);
		// add play button

		// JPanel bottomPanel = new JPanel(new BorderLayout());
		// bottomPanel.setOpaque(false);
		// bottomPanel.add(buttonsPanel, BorderLayout.NORTH);

		// Add the buttons to the grid panel
		gridPanel.add(buttonsPanel, BorderLayout.EAST);

		// Create the Play button
		ImageButton playButton = new ImageButton(PLAY_BUTTON, PLAY_ROLL_BUTTON) {
			private static final long serialVersionUID = 2140902110903730904L;

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

		ImageButton backButton = new ImageButton(BACK_BUTTON, BACK_HOVER_BUTTON) {
			private static final long serialVersionUID = -8415103429015408099L;

			public void mouseClicked(MouseEvent e) {
				getGame().switchScreen(Game.MATCH_BRAIN_SELECTION_SCREEN);
			}
		};

		JPanel playPanel = new JPanel();
		BoxLayout playLayout = new BoxLayout(playPanel, BoxLayout.Y_AXIS);
		playPanel.setLayout(playLayout);
		playPanel.setOpaque(false);
		playPanel.add(new FixedSpacerPanel(5, 15));
		playPanel.add(playButton);
		playPanel.add(new FixedSpacerPanel(80, 22));
		playPanel.add(backButton);
		playPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
		buttonsPanel.add(playPanel);

		this.setLayout(new BorderLayout());
		// Adds the titleContainer
		this.add(titleContainer, BorderLayout.NORTH);
		// Adds the world grid and buttons
		this.add(gridPanel, BorderLayout.CENTER);

	}

	/**
	 * Displays the world held in the screen on the preview grid. If no world is stored, it will preview an empty, 150x150 world.
	 */
	public void previewWorld() {
		if (antWorld != null) {
			Hexagon[][] gridBuffer = new Hexagon[antWorld.sizeX][antWorld.sizeY];

			int cols = gridBuffer.length;
			int rows = gridBuffer[0].length;

			for (int x = 0; x < cols; x++) {
				for (int y = 0; y < rows; y++) {
					Hexagon h = new Hexagon(x, y, 6, 1);
					Game.setTileColor(h, antWorld.getTile(x, y));
					gridBuffer[x][y] = h;
				}
			}
			grid.setHexagonGrid(gridBuffer);
			grid.refreshDimensions();
		} else {
			grid.newGrid(150, 150, 6, 1);
			grid.refreshDimensions();
			if (Game.GUI_DEBUG) {
				System.out.println("THE GRID SHOULD BE EMPTY!");
			}
		}

		scrollPane.revalidate();
		scrollPane.repaint();
	}

	/**
	 * Finds and returns the reasons why the players can not proceed to the next screen.
	 * 
	 * @return a string of reasons why the players can not proceed to the next screen.
	 */
	public String getErrorMessage() {
		String output = "";
		if (!worldValidateImage.isFirstShown()) {
			output += "The world is invalid!";
		}
		if (output.equals("")) {
			output = null;
		}
		return output;
	}

	/**
	 * Gets the Game model linked with screen.
	 * 
	 * @return the Game model linked with screen.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Sets the grid to display on the screen.
	 * 
	 * @param grid
	 *            the grid to display on the screen.
	 */
	public void setGrid(HexGrid grid) {
		this.grid = grid;
		this.scrollPane.revalidate();
		this.scrollPane.repaint();
	}

	/**
	 * Sets the world to use in the match.
	 * 
	 * @param world
	 *            the world to use in the match.
	 */
	public void setWorld(World world) {
		if (world == null) {
			worldValidateImage.displaySecond();
		} else {
			worldValidateImage.displayFirst();
		}
		this.antWorld = world;
	}

	/**
	 * Overridden to paint the background image.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	}

	@Override
	public void update() {
		previewWorld();
	}

	@Override
	public void reset() {
		setWorld(null);
		previewWorld();
	}

	/**
	 * Used to test this particular screen without the need for a Game model.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Add content to the window.
		JFrame frame = new JFrame("World Selection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 576);
		frame.add(new SingleMatchWorldPanel(null));
		frame.setResizable(false);

		// Display the window.
		frame.setVisible(true);
	}

}
