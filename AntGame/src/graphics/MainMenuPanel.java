package graphics;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.w3c.dom.events.MouseEvent;

import model.Game;

public class MainMenuPanel extends JPanel{
	private Game game;
	
	private static final BufferedImage TOURNAMENT_BUTTON_BACKGROUND_IMAGE = ImageLoader.loadImage("/NormalTest.png");
	private static final Color TOURNAMENT_BUTTON_BACKGROUND_COLOR = null;//Color.BLUE;
	private static final BufferedImage TOURNAMENT_BUTTON_HOVER_IMAGE = ImageLoader.loadImage("/HoverTest.png");
	private static final Color TOURNAMENT_BUTTON_HOVER_COLOR = null;//Color.GREEN;

	//private static final Image NON_TOURNAMENT_BUTTON_BACKGROUND = ImageLoader.loadImage("test.png");
	//private static final Image NON_TOURNAMENT_BUTTON_HOVER = ImageLoader.loadImage("test.png");

	
	public MainMenuPanel(Game game){
		this.game = game;
		
		/*
		final JTextField colsField = new JTextField("150",3);
		final JTextField rowsField = new JTextField("100",3);
		final JTextField sizeField = new JTextField("4", 3);
		final JTextField strokeField = new JTextField("1", 3);
		
		JButton testButton = new JButton("Start Match!");
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				getGame().createMatch(Integer.parseInt(colsField.getText()), 
									  Integer.parseInt(rowsField.getText()),
									  Integer.parseInt(sizeField.getText()),
									  Integer.parseInt(strokeField.getText()));
				getGame().switchScreen(Game.MATCH_SCREEN);
				getGame().startMatch();
			}
		});
		
		this.add(colsField);
		this.add(rowsField);
		this.add(sizeField);
		this.add(strokeField);
		this.add(testButton);
		*/
		
		
		ImageButton tournamentButton = new ImageButton(TOURNAMENT_BUTTON_BACKGROUND_IMAGE, 
													   TOURNAMENT_BUTTON_BACKGROUND_COLOR,
													   TOURNAMENT_BUTTON_HOVER_IMAGE, 
													   TOURNAMENT_BUTTON_HOVER_COLOR,
													   TOURNAMENT_BUTTON_BACKGROUND_IMAGE.getWidth(),
													   TOURNAMENT_BUTTON_BACKGROUND_IMAGE.getHeight()) {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				getGame().createMatch(150, 150, 4, 1);
				getGame().switchScreen(Game.MATCH_SCREEN);
				getGame().startMatch();
			}
		};
		
		this.add(tournamentButton);
	}
	
	public Game getGame() {
		return this.game;
	}
}
