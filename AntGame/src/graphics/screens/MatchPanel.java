package graphics.screens;

import graphics.components.FixedSpacerPanel;
import graphics.components.HexGrid;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.utilities.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import model.Match;
import model.Player;
import controller.Game;

/**
 * MatchPanel: the screen which displays the current match in play.
 * 
 * @author 105957
 */
public class MatchPanel extends JPanel implements Screen {

	private static final long serialVersionUID = -7289563643215100218L;
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage CONTROLS_IMAGE = ImageLoader.loadImage("/MatchPanelImages/controlsImage.png");
	private static final BufferedImage PLAYERS_IMAGE = ImageLoader.loadImage("/MatchPanelImages/playersImage.png");
	private static final BufferedImage PLUS_BUTTON_IMAGE = ImageLoader.loadImage("/GlobalImages/plusButton.png");
	private static final BufferedImage PLUS_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/GlobalImages/plusButtonHover.png");
	private static final BufferedImage MINUS_BUTTON_IMAGE = ImageLoader.loadImage("/GlobalImages/minusButton.png");
	private static final BufferedImage MINUS_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/GlobalImages/minusButtonHover.png");
	private static final BufferedImage PAUSE_BUTTON_IMAGE = ImageLoader.loadImage("/MatchPanelImages/pauseButton.png");
	private static final BufferedImage PAUSE_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/MatchPanelImages/pauseButtonHover.png");
	private static final BufferedImage RESUME_BUTTON_IMAGE = ImageLoader.loadImage("/MatchPanelImages/resumeButton.png");
	private static final BufferedImage RESUME_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/MatchPanelImages/resumeButtonHover.png");
	private static final BufferedImage SKIP_BUTTON_IMAGE = ImageLoader.loadImage("/MatchPanelImages/skipButton.png");
	private static final BufferedImage SKIP_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/MatchPanelImages/skipButtonHover.png");

	private static final int SLOW_SPEED = 10;
	private static final int MED_SPEED = 100;
	private static final int FAST_SPEED = 1000;
	private static final int FASTEST_SPEED = 10000;

	private Game game;
	private HexGrid grid;
	private JScrollPane scrollPane;

	private JPanel player1InfoPanel;
	private JPanel player2InfoPanel;

	private JLabel p1NicknameLabel;
	private JLabel p2NicknameLabel;

	private JLabel p1FoodValueLabel;
	private JLabel p1AliveValueLabel;
	private JLabel p1DeathsValueLabel;

	private JLabel p2FoodValueLabel;
	private JLabel p2AliveValueLabel;
	private JLabel p2DeathsValueLabel;

	private ImageButton currentPauseResumeButton;
	private ImageButton pauseButton;
	private ImageButton resumeButton;
	private JLabel currentZoomLabel;
	private JLabel currentSpeedLabel;

	private int currentZoomLevel = 1;
	private int previousSpeed;

	/**
	 * Constructor: Initialises the screen that allows players see and control the current match.
	 * 
	 * @param game
	 *            the ant-game controller that this screen is a part of.
	 */
	public MatchPanel(Game game) {
		this.game = game;
		this.grid = new HexGrid(0, 0, 0, 0);
		this.setLayout(new BorderLayout());

		// SCROLL PANE HOLDING GRID
		this.scrollPane = new JScrollPane(grid);
		// scrollPane.setViewportView(grid);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(Color.WHITE);

		// COMMON VALUES:
		Dimension hudDimension = new Dimension(300, 576);
		Dimension valueLabelDimension = new Dimension(100, 20);
		Dimension statLabelDimension = new Dimension(90, 20);
		Dimension labelPanelDimension = new Dimension(500, 35);
		Dimension controlValueDimension = new Dimension(100, 40);

		Font valueLabelFont = new Font("Helvetica", 0, 18);
		Font statLabelFont = new Font("Helvetica", 0, 18);
		Font controlLabelFont = new Font("Helvetica", 0, 22);

		// HUD
		JPanel hud = new JPanel();
		BoxLayout hudLayout = new BoxLayout(hud, BoxLayout.Y_AXIS);
		hud.setLayout(hudLayout);
		hud.setMaximumSize(hudDimension);
		hud.setPreferredSize(hudDimension);
		hud.setMinimumSize(hudDimension);
		hud.setOpaque(false);

		// CREATE TITLE AND ADD TO HUD
		ImagePanel playersTitle = new ImagePanel(PLAYERS_IMAGE);

		// CREATE PLAYER 1 INFO PANEL
		player1InfoPanel = new JPanel();
		BoxLayout p1Layout = new BoxLayout(player1InfoPanel, BoxLayout.Y_AXIS);
		player1InfoPanel.setLayout(p1Layout);
		player1InfoPanel.setBackground(new Color(0, 0, 0, 80));
		player1InfoPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.black));

		// CREATE PLAYER 2 INFO PANEL
		player2InfoPanel = new JPanel();
		BoxLayout p2Layout = new BoxLayout(player2InfoPanel, BoxLayout.Y_AXIS);
		player2InfoPanel.setLayout(p2Layout);
		player2InfoPanel.setBackground(new Color(200, 20, 20, 40));
		player2InfoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.black));

		// CREATE CONTROL PANEL
		JPanel controlPanel = new JPanel();
		BoxLayout controlPanelLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
		controlPanel.setLayout(controlPanelLayout);
		controlPanel.setOpaque(false);

		// CREATE P1 ELEMENTS
		p1NicknameLabel = new JLabel("Player 1");
		p1NicknameLabel.setAlignmentX(CENTER_ALIGNMENT);
		p1NicknameLabel.setForeground(Color.WHITE);
		p1NicknameLabel.setFont(new Font("Helvetica", 0, 20));

		JLabel p1FoodLabel = new JLabel("Food: ", JLabel.LEFT);
		p1FoodLabel.setForeground(Color.WHITE);
		p1FoodLabel.setFont(statLabelFont);
		p1FoodLabel.setMinimumSize(statLabelDimension);
		p1FoodLabel.setMaximumSize(statLabelDimension);
		p1FoodLabel.setPreferredSize(statLabelDimension);

		JLabel p1AliveLabel = new JLabel("Alive: ", JLabel.LEFT);
		p1AliveLabel.setForeground(Color.WHITE);
		p1AliveLabel.setFont(statLabelFont);
		p1AliveLabel.setMinimumSize(statLabelDimension);
		p1AliveLabel.setMaximumSize(statLabelDimension);
		p1AliveLabel.setPreferredSize(statLabelDimension);

		JLabel p1DeathsLabel = new JLabel("Deaths: ", JLabel.LEFT);
		p1DeathsLabel.setForeground(Color.WHITE);
		p1DeathsLabel.setFont(statLabelFont);
		p1DeathsLabel.setMinimumSize(statLabelDimension);
		p1DeathsLabel.setMaximumSize(statLabelDimension);
		p1DeathsLabel.setPreferredSize(statLabelDimension);

		p1FoodValueLabel = new JLabel("0", JLabel.RIGHT);
		p1FoodValueLabel.setForeground(Color.WHITE);
		p1FoodValueLabel.setFont(valueLabelFont);
		p1FoodValueLabel.setMinimumSize(valueLabelDimension);
		p1FoodValueLabel.setMaximumSize(valueLabelDimension);
		p1FoodValueLabel.setPreferredSize(valueLabelDimension);

		p1AliveValueLabel = new JLabel("0", JLabel.RIGHT);
		p1AliveValueLabel.setForeground(Color.WHITE);
		p1AliveValueLabel.setFont(valueLabelFont);
		p1AliveValueLabel.setMinimumSize(valueLabelDimension);
		p1AliveValueLabel.setMaximumSize(valueLabelDimension);
		p1AliveValueLabel.setPreferredSize(valueLabelDimension);

		p1DeathsValueLabel = new JLabel("0", JLabel.RIGHT);
		p1DeathsValueLabel.setForeground(Color.WHITE);
		p1DeathsValueLabel.setFont(valueLabelFont);
		p1DeathsValueLabel.setMinimumSize(valueLabelDimension);
		p1DeathsValueLabel.setMaximumSize(valueLabelDimension);
		p1DeathsValueLabel.setPreferredSize(valueLabelDimension);

		JPanel p1FoodPanel = new JPanel();
		p1FoodPanel.setMaximumSize(labelPanelDimension);
		p1FoodPanel.setMinimumSize(labelPanelDimension);
		p1FoodPanel.setPreferredSize(labelPanelDimension);

		JPanel p1DeathsPanel = new JPanel();
		p1DeathsPanel.setMaximumSize(labelPanelDimension);
		p1DeathsPanel.setMinimumSize(labelPanelDimension);
		p1DeathsPanel.setPreferredSize(labelPanelDimension);

		JPanel p1KillsPanel = new JPanel();
		p1KillsPanel.setMaximumSize(labelPanelDimension);
		p1KillsPanel.setMinimumSize(labelPanelDimension);
		p1KillsPanel.setPreferredSize(labelPanelDimension);

		p1FoodPanel.setOpaque(false);
		p1DeathsPanel.setOpaque(false);
		p1KillsPanel.setOpaque(false);

		p1FoodPanel.add(p1FoodLabel);
		p1FoodPanel.add(p1FoodValueLabel);
		p1DeathsPanel.add(p1AliveLabel);
		p1DeathsPanel.add(p1AliveValueLabel);
		p1KillsPanel.add(p1DeathsLabel);
		p1KillsPanel.add(p1DeathsValueLabel);

		// CREATE P2 ELEMENTS
		p2NicknameLabel = new JLabel("Player 2");
		p2NicknameLabel.setForeground(Color.WHITE);
		p2NicknameLabel.setFont(new Font("Helvetica", 0, 20));
		p2NicknameLabel.setAlignmentX(CENTER_ALIGNMENT);

		JLabel p2FoodLabel = new JLabel("Food: ", JLabel.LEFT);
		p2FoodLabel.setForeground(Color.WHITE);
		p2FoodLabel.setFont(statLabelFont);
		p2FoodLabel.setForeground(Color.WHITE);
		p2FoodLabel.setFont(statLabelFont);
		p2FoodLabel.setMinimumSize(statLabelDimension);
		p2FoodLabel.setMaximumSize(statLabelDimension);
		p2FoodLabel.setPreferredSize(statLabelDimension);
		p2FoodLabel.setOpaque(false);

		JLabel p2AliveLabel = new JLabel("Alive: ", JLabel.LEFT);
		p2AliveLabel.setForeground(Color.WHITE);
		p2AliveLabel.setFont(statLabelFont);
		p2AliveLabel.setForeground(Color.WHITE);
		p2AliveLabel.setFont(statLabelFont);
		p2AliveLabel.setMinimumSize(statLabelDimension);
		p2AliveLabel.setMaximumSize(statLabelDimension);
		p2AliveLabel.setPreferredSize(statLabelDimension);

		JLabel p2DeathsLabel = new JLabel("Deaths: ", JLabel.LEFT);
		p2DeathsLabel.setForeground(Color.WHITE);
		p2DeathsLabel.setFont(statLabelFont);
		p2DeathsLabel.setForeground(Color.WHITE);
		p2DeathsLabel.setFont(statLabelFont);
		p2DeathsLabel.setMinimumSize(statLabelDimension);
		p2DeathsLabel.setMaximumSize(statLabelDimension);
		p2DeathsLabel.setPreferredSize(statLabelDimension);

		p2FoodValueLabel = new JLabel("0", JLabel.RIGHT);
		p2FoodValueLabel.setForeground(Color.WHITE);
		p2FoodValueLabel.setFont(valueLabelFont);
		p2FoodValueLabel.setMinimumSize(valueLabelDimension);
		p2FoodValueLabel.setMaximumSize(valueLabelDimension);
		p2FoodValueLabel.setPreferredSize(valueLabelDimension);

		p2AliveValueLabel = new JLabel("0", JLabel.RIGHT);
		p2AliveValueLabel.setForeground(Color.WHITE);
		p2AliveValueLabel.setFont(valueLabelFont);
		p2AliveValueLabel.setMinimumSize(valueLabelDimension);
		p2AliveValueLabel.setMaximumSize(valueLabelDimension);
		p2AliveValueLabel.setPreferredSize(valueLabelDimension);

		p2DeathsValueLabel = new JLabel("0", JLabel.RIGHT);
		p2DeathsValueLabel.setForeground(Color.WHITE);
		p2DeathsValueLabel.setFont(valueLabelFont);
		p2DeathsValueLabel.setMinimumSize(valueLabelDimension);
		p2DeathsValueLabel.setMaximumSize(valueLabelDimension);
		p2DeathsValueLabel.setPreferredSize(valueLabelDimension);

		JPanel p2FoodPanel = new JPanel();
		p2FoodPanel.setMaximumSize(labelPanelDimension);
		p2FoodPanel.setMinimumSize(labelPanelDimension);
		p2FoodPanel.setPreferredSize(labelPanelDimension);

		JPanel p2DeathsPanel = new JPanel();
		p2DeathsPanel.setMaximumSize(labelPanelDimension);
		p2DeathsPanel.setMinimumSize(labelPanelDimension);
		p2DeathsPanel.setPreferredSize(labelPanelDimension);

		JPanel p2KillsPanel = new JPanel();
		p2KillsPanel.setMaximumSize(labelPanelDimension);
		p2KillsPanel.setMinimumSize(labelPanelDimension);
		p2KillsPanel.setPreferredSize(labelPanelDimension);

		p2FoodPanel.setOpaque(false);
		p2DeathsPanel.setOpaque(false);
		p2KillsPanel.setOpaque(false);

		p2FoodPanel.add(p2FoodLabel);
		p2FoodPanel.add(p2FoodValueLabel);
		p2DeathsPanel.add(p2AliveLabel);
		p2DeathsPanel.add(p2AliveValueLabel);
		p2KillsPanel.add(p2DeathsLabel);
		p2KillsPanel.add(p2DeathsValueLabel);

		// CREATE CONTROL ELEMENTS
		ImagePanel controlsTitle = new ImagePanel(CONTROLS_IMAGE);

		JLabel speedLabel = new JLabel("Speed:");
		speedLabel.setFont(controlLabelFont);
		speedLabel.setForeground(Color.WHITE);

		JLabel zoomLabel = new JLabel("Zoom:");
		zoomLabel.setFont(controlLabelFont);
		zoomLabel.setForeground(Color.WHITE);

		currentZoomLabel = new JLabel("+" + currentZoomLevel, JLabel.CENTER);
		currentZoomLabel.setFont(controlLabelFont);
		currentZoomLabel.setForeground(Color.WHITE);
		currentZoomLabel.setMinimumSize(controlValueDimension);
		currentZoomLabel.setMaximumSize(controlValueDimension);
		currentZoomLabel.setPreferredSize(controlValueDimension);

		currentSpeedLabel = new JLabel("Medium", JLabel.CENTER);
		currentSpeedLabel.setFont(controlLabelFont);
		currentSpeedLabel.setForeground(Color.WHITE);
		currentSpeedLabel.setMinimumSize(controlValueDimension);
		currentSpeedLabel.setMaximumSize(controlValueDimension);
		currentSpeedLabel.setPreferredSize(controlValueDimension);

		JPanel zoomPanel = new JPanel();
		zoomPanel.setOpaque(false);

		JPanel speedPanel = new JPanel();
		speedPanel.setOpaque(false);

		final JPanel pauseAndSkipPanel = new JPanel();
		pauseAndSkipPanel.setOpaque(false);

		JPanel gridlinesPanel = new JPanel();
		gridlinesPanel.setOpaque(false);

		ImageButton zoomOutButton = new ImageButton(MINUS_BUTTON_IMAGE, MINUS_BUTTON_IMAGE_HOVER) {
			private static final long serialVersionUID = 2627591414500660953L;

			public void mouseClicked(MouseEvent e) {
				if (getGrid().decreaseSize()) {
					getGrid().revalidate();
					getScrollPane().revalidate();
					currentZoomLevel--;
					if (currentZoomLevel > 0) {
						currentZoomLabel.setText("+" + currentZoomLevel);
					} else if (currentZoomLevel < 0) {
						currentZoomLabel.setText("-" + currentZoomLevel);
					} else {
						currentZoomLabel.setText("" + currentZoomLevel);
					}
				}
			}
		};

		ImageButton zoomInButton = new ImageButton(PLUS_BUTTON_IMAGE, PLUS_BUTTON_IMAGE_HOVER) {
			private static final long serialVersionUID = 7851413784782094128L;

			public void mouseClicked(MouseEvent e) {
				if (getGrid().increaseSize()) {
					getGrid().revalidate();
					getScrollPane().revalidate();
					currentZoomLevel++;
					if (currentZoomLevel > 0) {
						currentZoomLabel.setText("+" + currentZoomLevel);
					} else if (currentZoomLevel < 0) {
						currentZoomLabel.setText("-" + currentZoomLevel);
					} else {
						currentZoomLabel.setText("" + currentZoomLevel);
					}
				}
			}
		};

		ImageButton slowDownButton = new ImageButton(MINUS_BUTTON_IMAGE, MINUS_BUTTON_IMAGE_HOVER) {
			private static final long serialVersionUID = 7314330519641171406L;

			public void mouseClicked(MouseEvent e) {
				if (!getGame().isPaused()) {
					if (currentSpeedLabel.getText().equals("Medium")) {
						currentSpeedLabel.setText("Slow");
						getGame().setRoundsPerSecond(SLOW_SPEED);
					} else if (currentSpeedLabel.getText().equals("Fast")) {
						currentSpeedLabel.setText("Medium");
						getGame().setRoundsPerSecond(MED_SPEED);
					} else if (currentSpeedLabel.getText().equals("Fastest")) {
						currentSpeedLabel.setText("Fast");
						getGame().setRoundsPerSecond(FAST_SPEED);
					}
				}
			}
		};

		ImageButton speedUpButton = new ImageButton(PLUS_BUTTON_IMAGE, PLUS_BUTTON_IMAGE_HOVER) {
			private static final long serialVersionUID = -4214685482836890062L;

			public void mouseClicked(MouseEvent e) {
				if (!getGame().isPaused()) {
					if (currentSpeedLabel.getText().equals("Slow")) {
						currentSpeedLabel.setText("Medium");
						getGame().setRoundsPerSecond(MED_SPEED);
					} else if (currentSpeedLabel.getText().equals("Medium")) {
						currentSpeedLabel.setText("Fast");
						getGame().setRoundsPerSecond(FAST_SPEED);
					} else if (currentSpeedLabel.getText().equals("Fast")) {
						currentSpeedLabel.setText("Fastest");
						getGame().setRoundsPerSecond(FASTEST_SPEED);
					}
				}
			}
		};

		ImageButton skipButton = new ImageButton(SKIP_BUTTON_IMAGE, SKIP_BUTTON_IMAGE_HOVER) {
			private static final long serialVersionUID = -8968425837117243812L;

			public void mouseClicked(MouseEvent e) {
				if (!getGame().isPaused()) {
					getGame().setRoundsPerSecond(100000);
				} else {
					currentPauseResumeButton = resumeButton;
					pauseAndSkipPanel.removeAll();
					pauseAndSkipPanel.add(currentPauseResumeButton);
					MatchPanel.this.revalidate();
					MatchPanel.this.repaint();
					getGame().setRoundsPerSecond(100000);
				}
			}
		};

		ImageButton s = new ImageButton(PLUS_BUTTON_IMAGE, SKIP_BUTTON_IMAGE_HOVER) {
			private static final long serialVersionUID = -4607361831692403202L;

			public void mouseClicked(MouseEvent e) {
			}
		};

		pauseButton = new ImageButton(PAUSE_BUTTON_IMAGE, PAUSE_BUTTON_IMAGE_HOVER) {
			private static final long serialVersionUID = 5475620339532697982L;

			public void mouseClicked(MouseEvent e) {
				currentPauseResumeButton = resumeButton;
				pauseAndSkipPanel.removeAll();
				pauseAndSkipPanel.add(currentPauseResumeButton);
				MatchPanel.this.revalidate();
				MatchPanel.this.repaint();
				previousSpeed = getGame().getRoundsPerSecond();
				getGame().setRoundsPerSecond(0);
			}
		};

		resumeButton = new ImageButton(RESUME_BUTTON_IMAGE, RESUME_BUTTON_IMAGE_HOVER) {
			private static final long serialVersionUID = -6205224946829692369L;

			public void mouseClicked(MouseEvent e) {
				currentPauseResumeButton = pauseButton;
				pauseAndSkipPanel.removeAll();
				pauseAndSkipPanel.add(currentPauseResumeButton);
				MatchPanel.this.revalidate();
				MatchPanel.this.repaint();
				getGame().setRoundsPerSecond(previousSpeed);
			}
		};

		zoomPanel.add(zoomLabel);
		zoomPanel.add(new FixedSpacerPanel(1, 1));
		zoomPanel.add(zoomOutButton);
		zoomPanel.add(currentZoomLabel);
		zoomPanel.add(zoomInButton);

		speedPanel.add(speedLabel);
		speedPanel.add(slowDownButton);
		speedPanel.add(currentSpeedLabel);
		speedPanel.add(speedUpButton);

		currentPauseResumeButton = pauseButton;
		pauseAndSkipPanel.add(currentPauseResumeButton);
		// pauseAndSkipPanel.add(skipButton);

		// ADD CONTROL ELEMENTS TO CONTROL PANEL
		controlPanel.add(new FixedSpacerPanel(10, 5));
		controlPanel.add(controlsTitle);
		controlPanel.add(new FixedSpacerPanel(10, 5));
		controlPanel.add(zoomPanel);
		controlPanel.add(speedPanel);
		controlPanel.add(pauseAndSkipPanel);

		// ADD P1 ELEMENTS TO P1 INFO PANEL
		player1InfoPanel.add(new FixedSpacerPanel(10, 5));
		player1InfoPanel.add(p1NicknameLabel);
		player1InfoPanel.add(p1FoodPanel);
		player1InfoPanel.add(p1DeathsPanel);
		player1InfoPanel.add(p1KillsPanel);

		// ADD P2 ELEMENTS TO P2 INFO PANEL
		player2InfoPanel.add(new FixedSpacerPanel(10, 5));
		player2InfoPanel.add(p2NicknameLabel);
		player2InfoPanel.add(p2FoodPanel);
		player2InfoPanel.add(p2DeathsPanel);
		player2InfoPanel.add(p2KillsPanel);

		// ADD P1 AND P2 INFO TO HUD
		hud.add(new FixedSpacerPanel(10, 5));
		hud.add(playersTitle);
		hud.add(new FixedSpacerPanel(10, 5));
		hud.add(player1InfoPanel);
		hud.add(player2InfoPanel);

		// ADD CONTROL ELEMENTS TO HUD
		hud.add(controlPanel);

		// DEBUG ELEMENTS
		JButton refreshScreenButton = new JButton("Decrease Game Speed");
		refreshScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getGame().setRoundsPerSecond(getGame().getRoundsPerSecond() - (getGame().getRoundsPerSecond() / 5));
			}
		});

		JButton addAntTestButton = new JButton("Decrease Game Speed");
		refreshScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getGame().setRoundsPerSecond(getGame().getRoundsPerSecond() + (getGame().getRoundsPerSecond() / 5));
			}
		});

		JButton removeAllButton = new JButton("remove all");
		refreshScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getGrid().clearAll();
				getGrid().refresh();
			}
		});

		JButton increaseSizeButton = new JButton("increase size");
		refreshScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getGrid().increaseSize();
				getGrid().revalidate();
				getScrollPane().revalidate();
			}
		});

		JButton decreaseSizeButton = new JButton("Decrease size");
		refreshScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getGrid().increaseSize();
				getGrid().revalidate();
				getScrollPane().revalidate();
			}
		});

		JButton stopGameButton = new JButton("Pause/Play Match");
		refreshScreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getGame().togglePause();

			}
		});

		JButton gridLinesCheckBox = new JButton("gridLinesCheckBox");
		refreshScreenButton.addActionListener(new ActionListener() {
			boolean gridlines = true;

			public void actionPerformed(ActionEvent e) {
				if (gridlines) {
					getGrid().removeOutlines();
					getGrid().refresh();
					gridlines = false;
				} else {
					getGrid().addDefaultOutlines();
					getGrid().refresh();
					gridlines = true;
				}
			}
		});

		JLabel debugLabel = new JLabel("Debugging Tools", JLabel.CENTER);
		debugLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
		debugLabel.setForeground(new Color(220, 250, 220));

		JPanel debugHUD = new JPanel(new GridLayout(10, 1));
		debugHUD.add(debugLabel);
		debugHUD.add(refreshScreenButton);
		debugHUD.add(addAntTestButton);
		debugHUD.add(removeAllButton);
		debugHUD.add(increaseSizeButton);
		debugHUD.add(decreaseSizeButton);
		debugHUD.add(stopGameButton);
		debugHUD.add(gridLinesCheckBox);
		debugHUD.setBackground(new Color(42, 88, 40));

		this.add(scrollPane, BorderLayout.CENTER);
		this.add(hud, BorderLayout.EAST);
		if (Game.GUI_DEBUG)
			this.add(debugHUD, BorderLayout.WEST);
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
	 * 
	 * @return the Game model linked with screen.
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * Gets the hex grid stored in the panel.
	 * 
	 * @return the hex grid stored in the panel.
	 */
	public HexGrid getGrid() {
		return this.grid;
	}

	/**
	 * Sets the hex grid stored in the panel.
	 * 
	 * @param grid
	 *            the hex grid to display.
	 */
	public void setGrid(HexGrid grid) {
		this.grid = grid;
		// this.scrollPane.removeAll();
		// this.scrollPane.add(grid);
		scrollPane.revalidate();
		scrollPane.repaint();
	}

	/**
	 * Gets the scroll pane that the grid is contained in.
	 * 
	 * @return the scroll pane that the grid is contained in.
	 */
	public JScrollPane getScrollPane() {
		return this.scrollPane;
	}

	/**
	 * Updates both players statistics.
	 */
	public void updatePlayerStats() {
		Player p1 = getGame().getCurrentMatch().getPlayer1();
		Player p2 = getGame().getCurrentMatch().getPlayer2();

		Match curMatch = getGame().getCurrentMatch();
		curMatch.updateScores();

		p1FoodValueLabel.setText("" + curMatch.getFoodScore(p1));
		p1AliveValueLabel.setText("" + curMatch.getAliveCount(p1));
		p1DeathsValueLabel.setText("" + curMatch.getDeadCount(p1));

		p2FoodValueLabel.setText("" + curMatch.getFoodScore(p2));
		p2AliveValueLabel.setText("" + curMatch.getAliveCount(p2));
		p2DeathsValueLabel.setText("" + curMatch.getDeadCount(p2));

		this.repaint();
	}

	@Override
	public void update() {
		reset();
		p1NicknameLabel.setText(getGame().getCurrentMatch().getPlayer1().getNickname());
		p2NicknameLabel.setText(getGame().getCurrentMatch().getPlayer2().getNickname());
		this.revalidate();
	}

	@Override
	public void reset() {
		getGame().setRoundsPerSecond(100);
		currentZoomLevel = 1;
		currentZoomLabel.setText("+" + currentZoomLevel);
		currentSpeedLabel.setText("Medium");
	}
}
