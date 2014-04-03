package graphics.screens;

import model.*;
import graphics.components.*;
import graphics.utilities.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * NonTournamentSelection: A screen for the AntGame. This screen allows both players
 * to enter a nickname and provide an ant-brain. Validation is performed on both.
 */
public class SingleMatchPlayerPanel  extends JPanel implements Screen{
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/SingleMatchPlayerPanelImages/playerSelectTitle.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage TICK_IMAGE = ImageLoader.loadImage("/GlobalImages/tick.png");
	private static final BufferedImage CROSS_IMAGE = ImageLoader.loadImage("/GlobalImages/cross.png");
	private static final BufferedImage UPLOAD_IMAGE = ImageLoader.loadImage("/SingleMatchPlayerPanelImages/uploadButtonImage.png");
	private static final BufferedImage UPLOAD_ROLL_IMAGE = ImageLoader.loadImage("/SingleMatchPlayerPanelImages/uploadButtonImageHover.png");
	private static final BufferedImage NEXT_BUTTON_IMAGE = ImageLoader.loadImage("/SingleMatchPlayerPanelImages/nextButtonImage.png");
	private static final BufferedImage NEXT_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/SingleMatchPlayerPanelImages/nextButtonImageHover.png");
	private static final BufferedImage BACK_BUTTON_IMAGE = ImageLoader.loadImage("/GlobalImages/backButton.png");
	private static final BufferedImage BACK_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/GlobalImages/backButtonHover.png");

	private Game game;
	private AntBrain player1Brain;
	private AntBrain player2Brain;
	private Player player1;
	private Player player2;

	private JFileChooser fc; // Default OS file chooser.
	private JTextField p1NickField; // Player 2 nickname entry field.
	private JTextField p2NickField; // Player 2 nickname entry field.
	private DualImagePanel p1NickValidate; // Tick/Cross for player 1 nickname
	private DualImagePanel p2NickValidate; // Tick/Cross for player 2 nickname
	private DualImagePanel p1BrainValidate; // Tick/Cross for player 1 brain
	private DualImagePanel p2BrainValidate; // Tick/Cross for player 2 brain

	/**
	 * Initialises the screen that allows players to be chosen for a non-tournament match
	 * This screen allows users to 
	 * @param game the game model that this screen is a part of.
	 */
	public SingleMatchPlayerPanel(Game game) {
		this.game = game;
		this.setLayout(new BorderLayout());
		fc = new JFileChooser(); // Default OS file chooser.

		/////////////////////////////////////////////////////////////////////////////
		//////////////////////////// PLAYER 1 SIDE //////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////
		// Player 1 heading label
		JLabel p1Label = new JLabel("Player 1");
		p1Label.setForeground(Color.WHITE);
		p1Label.setFont(new Font("Helvetica", 0, 35));
		p1Label.setAlignmentX(CENTER_ALIGNMENT);

		/////////////////////////////////////////////////////////////////////////////
		//////////// Player 1 Nickname panel (label, text-field, tick/cross image) //
		/////////////////////////////////////////////////////////////////////////////
		JPanel p1NickPanel = new JPanel();
		p1NickPanel.setOpaque(false);
		p1NickPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		p1NickPanel.setMaximumSize(new Dimension(400, 100));

		JLabel p1NickLabel = new JLabel("Nickname:");
		p1NickLabel.setFont(new Font("Helvetica", 0, 25));
		p1NickLabel.setForeground(Color.WHITE);
		p1NickLabel.setAlignmentX(CENTER_ALIGNMENT);

		p1NickField = new JTextField("Player1", 11);
		p1NickField.setPreferredSize(new Dimension(200, 30));
		p1NickField.setFont(new Font("Helvetica", 0, 25));
		p1NickField.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		p1NickField.setOpaque(false);
		p1NickField.setHorizontalAlignment(JTextField.CENTER);
		p1NickField.setCaretColor(Color.WHITE);
		p1NickField.setForeground(Color.WHITE);
		p1NickField.setBackground(new Color(255,255,255,0));
		p1NickField.setAlignmentX(CENTER_ALIGNMENT);

		p1NickPanel.add(p1NickLabel);
		p1NickPanel.add(p1NickField);

		p1NickValidate = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
		p1NickField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				validateNicknames();
			}

			public void insertUpdate(DocumentEvent arg0) {
				validateNicknames();
			}

			public void removeUpdate(DocumentEvent arg0) {
				validateNicknames();
			}
		});
		p1NickPanel.add(p1NickValidate);

		/////////////////////////////////////////////////////////////////////////////
		///////////////////////// Player 1 Ant-Brain panel.//////////////////////////
		/////////////////////////////////////////////////////////////////////////////
		JPanel p1BrainPanel = new JPanel();
		p1BrainPanel.setOpaque(false);
		p1BrainPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		p1BrainPanel.setMaximumSize(new Dimension(400, 100));

		// Player 1 Ant-Brain label
		JLabel p1AntBrainLabel = new JLabel("Ant-Brain:");
		p1AntBrainLabel.setForeground(Color.WHITE);
		p1AntBrainLabel.setFont(new Font("Helvetica", 0, 25));

		ImageButton openButton1 = new ImageButton(UPLOAD_IMAGE, UPLOAD_ROLL_IMAGE) {
			public void mouseClicked(MouseEvent e) {
				int returnVal = fc.showOpenDialog(SingleMatchPlayerPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File brainFile = fc.getSelectedFile();
					AntBrain brain = AntBrainReader.readBrain(brainFile);
					if (brain == null) {
						p1BrainValidate.displaySecond();
					} else {
						p1BrainValidate.displayFirst();
					}
					player1Brain = brain;
				}
			}
		};

		// Tick/Cross for player 1 ant brain
		p1BrainValidate = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
		p1BrainValidate.displaySecond();

		// Add components to player 1 brain panel
		p1BrainPanel.add(p1AntBrainLabel);
		p1BrainPanel.add(openButton1);
		p1BrainPanel.add(p1BrainValidate);

		//////////////////////////////////////////////////////////////////////////
		///////////////////////////  PLAYER 2 SIDE  //////////////////////////////
		//////////////////////////////////////////////////////////////////////////
		JLabel p2Label = new JLabel("Player 2");
		p2Label.setForeground(Color.WHITE);
		p2Label.setFont(new Font("Helvetica", 0, 35));
		p2Label.setAlignmentX(CENTER_ALIGNMENT);

		JLabel p2NickLabel = new JLabel("Nickname:");
		p2NickLabel.setFont(new Font("Helvetica", 0, 25));
		p2NickLabel.setForeground(Color.WHITE);
		p2NickLabel.setAlignmentX(CENTER_ALIGNMENT);

		// Nickname field for player 2.
		p2NickField = new JTextField("Player2", 11);
		p2NickField.setPreferredSize(new Dimension(200, 30));
		p2NickField.setFont(new Font("Helvetica", 0, 25));
		p2NickField.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		p2NickField.setOpaque(false);
		p2NickField.setHorizontalAlignment(JTextField.CENTER);
		p2NickField.setCaretColor(Color.WHITE);
		p2NickField.setForeground(Color.WHITE);
		p2NickField.setBackground(new Color(255,255,255,0));
		p2NickField.setAlignmentX(CENTER_ALIGNMENT);
		p2NickValidate = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
		p2NickField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				validateNicknames();
			}

			public void insertUpdate(DocumentEvent arg0) {
				validateNicknames();
			}

			public void removeUpdate(DocumentEvent arg0) {
				validateNicknames();
			}
		});

		/////////////////////////////////////////////////////////////////////////////
		////////// Player 2 Nickname panel (label, textfield, tick/cross image) /////
		/////////////////////////////////////////////////////////////////////////////
		JPanel p2NickPanel = new JPanel();
		p2NickPanel.setOpaque(false);
		p2NickPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		p2NickPanel.setMaximumSize(new Dimension(400, 100));
		p2NickPanel.add(p2NickLabel);
		p2NickPanel.add(p2NickField);
		p2NickPanel.add(p2NickValidate);

		/////////////////////////////////////////////////////////////////////////////
		///////////////////////// Player 2 Ant-Brain panel.//////////////////////////
		/////////////////////////////////////////////////////////////////////////////
		JPanel p2BrainPanel = new JPanel();
		p2BrainPanel.setOpaque(false);
		p2BrainPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		p2BrainPanel.setMaximumSize(new Dimension(400, 100));

		// Player 1 Ant-Brain label
		JLabel p2AntBrainLabel = new JLabel("Ant-Brain:");
		p2AntBrainLabel.setForeground(Color.WHITE);
		p2AntBrainLabel.setFont(new Font("Helvetica", 0, 25));

		ImageButton openButton2 = new ImageButton(UPLOAD_IMAGE, UPLOAD_ROLL_IMAGE) {
			public void mouseClicked(MouseEvent e) {
				int returnVal = fc.showOpenDialog(SingleMatchPlayerPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File brainFile = fc.getSelectedFile();
					AntBrain brain = AntBrainReader.readBrain(brainFile); 
					if (brain == null) {
						p2BrainValidate.displaySecond();
					} else {
						p2BrainValidate.displayFirst();
					}
					player2Brain = brain;
				}
			}
		};

		// Tick/Cross for player 1 ant brain
		p2BrainValidate = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
		p2BrainValidate.displaySecond();

		// Add components to player 1 brain panel
		p2BrainPanel.add(p2AntBrainLabel);
		p2BrainPanel.add(openButton2);
		p2BrainPanel.add(p2BrainValidate);

		// Add player 1 components to left side panel.
		JPanel leftPanel = new JPanel();
		BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(leftLayout);
		leftPanel.setOpaque(false);
		leftPanel.add(p1Label);
		leftPanel.add(p1NickPanel);
		leftPanel.add(p1BrainPanel);

		// Add player 2 components to right side panel.
		JPanel rightPanel = new JPanel();
		BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		rightPanel.setLayout(rightLayout);
		rightPanel.setOpaque(false);
		rightPanel.add(p2Label);
		rightPanel.add(p2NickPanel);
		rightPanel.add(p2BrainPanel);

		// Create and set up the split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); //add split pane
		splitPane.setDividerLocation(1000/2); // HACK -- (half the width of the screen)
		splitPane.setEnabled(false); // Stops it being re-sizable.
		splitPane.setOpaque(false); // display the background through it.
		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		splitPane.setBorder(null);
		splitPane.setDividerSize(0);

		// Create title container
		JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 70)); 
		titleContainer.setOpaque(false);

		//Create Go button
		ImageButton nextButton = new ImageButton(NEXT_BUTTON_IMAGE, NEXT_BUTTON_IMAGE_HOVER) {
			public void mouseClicked(MouseEvent e) {
				String errorMessage = getErrorMessage();
				boolean valid = errorMessage == null;
				if (!valid) {
					JOptionPane.showMessageDialog(SingleMatchPlayerPanel.this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					player1 = new Player(p1NickField.getText().trim(), player1Brain);
					player2 = new Player(p2NickField.getText().trim(), player2Brain);
					getGame().createMatch();
					getGame().getCurrentMatch().setPlayer1(player1);
					getGame().getCurrentMatch().setPlayer2(player2);
					getGame().switchScreen(Game.WORLD_SELECTION_SCREEN);
				}
			}
		};
		
		//Create Back button
		ImageButton backButton = new ImageButton(BACK_BUTTON_IMAGE, BACK_BUTTON_IMAGE_HOVER) {
			public void mouseClicked(MouseEvent e) {
				getGame().switchScreen(Game.MAIN_MENU_SCREEN);
				reset();
				
			}
		};
		
		JPanel nextButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		nextButtonPanel.add(backButton);
		nextButtonPanel.add(new FixedSpacerPanel(80, 20));
		nextButtonPanel.setOpaque(false);
		nextButtonPanel.add(nextButton);
		nextButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

		//Add containers
		add(titleContainer, BorderLayout.NORTH);
		add(splitPane, BorderLayout.CENTER);
		add(nextButtonPanel, BorderLayout.SOUTH);
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
	 * gets the messages associated with validation errors. 
	 * @return a string of errors that need correcting by the user, null if no problems exist.
	 */
	public String getErrorMessage(){
		String output = "";

		// Player 1 nickname validation
		if(!p1NickValidate.isFirstShown()){
			output += "Player 1's nickname is invalid!\n";
		}

		// Player 2 nickname validation
		if(!p2NickValidate.isFirstShown()){
			output += "Player 2's nickname is invalid!\n";
		}

		// Player 1 ant-brain validation
		if(!p1BrainValidate.isFirstShown()){
			output += "Player 1's ant-brain is invalid!\n";
		}

		// Player 1 ant-brain validation
		if(!p2BrainValidate.isFirstShown()){
			output += "Player 2's ant-brain is invalid!\n";
		}

		if(output.equals("")){
			output = null;
		}

		return output;
	}

	/**
	 * Changes the nickname images depending on the nickname field's contents.
	 * 
	 * Checks that the:
	 * 	- Nicknames aren't greater than 20 characters.
	 * 	- Nicknames aren't just whitespace.
	 * 	- Nicknames aren't the same as each other.
	 */
	public void validateNicknames(){
		String p1nickText = p1NickField.getText().trim();
		String p2nickText = p2NickField.getText().trim();

		// Rule for player 1 nickname
		boolean nick1valid = p1nickText.length() <= 20
				&& !p1nickText.equals("")
				&& !p1nickText.equals(p2nickText);

		// Rule for player 2 nickname
		boolean nick2valid = p2nickText.length() <= 20
				&& !p2nickText.equals("")
				&& !p2nickText.equals(p1nickText);

		// Update validate image for player 1
		if(nick1valid){
			p1NickValidate.displayFirst();
		} else {
			p1NickValidate.displaySecond();
		}

		// Update validate image for player 2
		if(nick2valid){
			p2NickValidate.displayFirst();
		} else {
			p2NickValidate.displaySecond();
		}
	}

	/**
	 * Get the Game model linked with screen.
	 * @return the Game model linked with screen.
	 */
	public Game getGame(){
		return game;
	}

	/**
	 * This method simply resets the values of various parameters
	 * back to their default values.
	 * To be used when switching screen.
	 * No need for a button that calls the method, as it is only called on code-level.
	 */
	public void resetScreen() {
		player1Brain = null;
		player2Brain = null;
		player1 = null;
		player2 = null;
		p1NickField.setText("Player 1");
		p2NickField.setText("Player 2");
		//reset cross/tick to their original value
		//nicks have a tick by default
		p1NickValidate.displayFirst(); 
		p2NickValidate.displayFirst(); 
		//brains have a cross by default
		p1BrainValidate.displaySecond(); 
		p2BrainValidate.displaySecond(); 
	}

	/**
	 * Used to test this particular screen without the need for a Game model.
	 * @param args
	 */
	public static void main(String[] args) {
		//Add content to the window.
		JFrame frame = new JFrame("World File Chooser");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 576);
		frame.add(new SingleMatchPlayerPanel(null));
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
		player1Brain = null;
		player2Brain = null;
		player1 = null;
		player2 = null;
		
		p1NickField.setText("Player1");
		p2NickField.setText("Player2");
		p1NickValidate.displayFirst();
		p2NickValidate.displayFirst();
		p1BrainValidate.displaySecond();
		p2BrainValidate.displaySecond();
	}
}

