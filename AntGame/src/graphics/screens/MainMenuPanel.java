package graphics.screens;

import graphics.components.FixedSpacerPanel;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.utilities.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Game;

/**
 * MainMenuPanel: JPanel that represents the main menu for the AntGame.
 * 
 * @author 105957
 * @author 109195
 */
public class MainMenuPanel extends JPanel implements Screen {

	private static final long serialVersionUID = -1901224187935863599L;
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/MainMenuPanelImages/titleLarge.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage TOURNAMENT_BUTTON_BACKGROUND_IMAGE = ImageLoader.loadImage("/MainMenuPanelImages/tournamentButton.png");
	private static final BufferedImage TOURNAMENT_BUTTON_HOVER_IMAGE = ImageLoader.loadImage("/MainMenuPanelImages/tournamentButtonHover.png");
	private static final BufferedImage SINGLE_MATCH_IMAGE = ImageLoader.loadImage("/MainMenuPanelImages/singleMatchImage.png");
	private static final BufferedImage SINGLE_MATCH_IMAGE_HOVER = ImageLoader.loadImage("/MainMenuPanelImages/singleMatchImageHover.png");

	private Game game;

	public MainMenuPanel(Game game) {
		this.game = game;

		ImageButton tournamentButton = new ImageButton(TOURNAMENT_BUTTON_BACKGROUND_IMAGE, TOURNAMENT_BUTTON_HOVER_IMAGE) {
			private static final long serialVersionUID = -8575918955467990177L;

			public void mouseClicked(java.awt.event.MouseEvent e) {
				getGame().switchScreen(Game.TOURNAMENT_SELECTION_SCREEN);
			}
		};

		ImageButton nonTournamentButton = new ImageButton(SINGLE_MATCH_IMAGE, SINGLE_MATCH_IMAGE_HOVER) {
			private static final long serialVersionUID = 7231176950840496113L;

			public void mouseClicked(java.awt.event.MouseEvent e) {
				getGame().switchScreen(Game.MATCH_BRAIN_SELECTION_SCREEN);
			}
		};

		JButton debugButton = new JButton("Debug Button");
		debugButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGame().createMatchPanelGrid(getGame().getCurrentMatch().getWorld().sizeX, getGame().getCurrentMatch().getWorld().sizeY, 4, 1);
				getGame().switchScreen(Game.MATCH_SCREEN);
				getGame().startMatch();
			}
		});

		// Wrap up the title.
		JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 70));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 0));
		titleContainer.setOpaque(false);

		// Wrap up the two buttons.
		JPanel buttonContainer = new JPanel();
		BoxLayout buttonLayout = new BoxLayout(buttonContainer, BoxLayout.Y_AXIS);
		buttonContainer.setLayout(buttonLayout);
		buttonContainer.add(new FixedSpacerPanel(100, 60));
		buttonContainer.add(tournamentButton);
		buttonContainer.add(new FixedSpacerPanel(100, 40));
		buttonContainer.add(nonTournamentButton);
		buttonContainer.setOpaque(false);

		this.setLayout(new BorderLayout());
		this.add(titleContainer, BorderLayout.NORTH);
		this.add(buttonContainer, BorderLayout.CENTER);
		if (Game.GUI_DEBUG)
			this.add(debugButton, BorderLayout.SOUTH);
	}

	public Game getGame() {
		return this.game;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	}

	@Override
	public void update() {
		getGame().resetScreens();
	}

	@Override
	public void reset() {
		getGame().setCurrentMatch(null);
		getGame().setCurrentTournament(null);
	}
}
