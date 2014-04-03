package graphics.screens;
import graphics.components.DualImagePanel;
import graphics.components.FixedSpacerPanel;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.components.ListItem;
import graphics.utilities.ImageLoader;
import model.*;

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
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TournamentSelectionPanel extends JPanel implements Screen{
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/TournamentSelectionImages/playerSelectTitle.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage TICK_IMAGE = ImageLoader.loadImage("/GlobalImages/tick.png");
	private static final BufferedImage CROSS_IMAGE = ImageLoader.loadImage("/GlobalImages/cross.png");
	private static final BufferedImage UPLOAD_IMAGE = ImageLoader.loadImage("/TournamentSelectionImages/uploadButtonImage.png");
	private static final BufferedImage UPLOAD_ROLL_IMAGE = ImageLoader.loadImage("/TournamentSelectionImages/uploadButtonImageHover.png");
	private static final BufferedImage ADD_IMAGE = ImageLoader.loadImage("/TournamentSelectionImages/addButtonImage.png");
	private static final BufferedImage ADD_IMAGE_HOVER = ImageLoader.loadImage("/TournamentSelectionImages/addButtonImageHover.png");
	private static final BufferedImage PLAY_IMAGE = ImageLoader.loadImage("/TournamentSelectionImages/playButton.png");
	private static final BufferedImage PLAY_IMAGE_HOVER = ImageLoader.loadImage("/TournamentSelectionImages/playButtonHover.png");
	private static final BufferedImage DELETE_IMAGE = ImageLoader.loadImage("/TournamentSelectionImages/crossButton.png");
	private static final BufferedImage DELETE_IMAGE_HOVER = ImageLoader.loadImage("/TournamentSelectionImages/crossButtonBrightHover.png");
	private static final BufferedImage BACK_BUTTON = ImageLoader.loadImage("/GlobalImages/backButton.png");
	private static final BufferedImage BACK_HOVER_BUTTON = ImageLoader.loadImage("/GlobalImages/backButtonHover.png");
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<String> playerNames = new ArrayList<String>();
	
	private Game game;
	
	private JTextField playerName;
	private JTextField worldNumberField;
	private DualImagePanel nameValidate;
	private DualImagePanel brainValidate;
	private int nameGen = 0;
	private JFileChooser fc;
	private AntBrain currentBrain;
	private String currentBrainName;
	private JScrollPane listHolder;
	private JPanel itemPanel;
	
	public TournamentSelectionPanel(Game game){
		this.setLayout(new FlowLayout());
		fc = new JFileChooser();
		this.game = game;
		
		JPanel titlepanel = new JPanel();
		titlepanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		titlepanel.add(new ImagePanel(TITLE_IMAGE));
		titlepanel.setOpaque(false);
		
		//Panel to contain a row with nickname, the brain to be associated with it, an ant brain to uploader and an add player button.
		final JPanel nameAndUpload = new JPanel();
		nameAndUpload.setLayout(new FlowLayout(FlowLayout.CENTER));
		nameAndUpload.setOpaque(false);
		
		JLabel nickname = new JLabel("Nickname:");
		nickname.setPreferredSize(new Dimension(135, 76));
		nickname.setForeground(Color.WHITE);
		nickname.setFont(new Font("Helvetica", 0, 25));
		nickname.setAlignmentX(CENTER_ALIGNMENT);
		
		playerName = new JTextField(generateName(), 11);
		playerName.setFont(new Font("Helvetica", 0, 25));
		playerName.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		playerName.setOpaque(false);
		playerName.setHorizontalAlignment(JTextField.CENTER);
		playerName.setCaretColor(Color.WHITE);
		playerName.setForeground(Color.WHITE);
		playerName.setBackground(new Color(255,255,255,0));
		
		nameValidate = new DualImagePanel(TICK_IMAGE, CROSS_IMAGE);
		nameValidate.displayFirst();
		playerName.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				validatePlayerName();
			}

			public void insertUpdate(DocumentEvent arg0) {
				validatePlayerName();
			}

			public void removeUpdate(DocumentEvent arg0) {
				validatePlayerName();
			}
		});
		
		JLabel brainLabel = new JLabel("Ant-Brain:");
		brainLabel.setForeground(Color.WHITE);
		brainLabel.setFont(new Font("Helvetica", 0, 25));
		//brainLabel.setPreferredSize(new Dimension(125, 76));
		
		ImageButton openBrain = new ImageButton(UPLOAD_IMAGE, UPLOAD_ROLL_IMAGE) {
			public void mouseClicked(MouseEvent e) {
				int returnVal = fc.showOpenDialog(TournamentSelectionPanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File brainFile = fc.getSelectedFile();
					AntBrain brain = AntBrainReader.readBrain(brainFile);
					if (brain == null) {
						brainValidate.displaySecond();
					} else {
						brainValidate.displayFirst();
					}
					currentBrain = brain;
					currentBrainName = brainFile.getName();
				}
			}
		};
		
		brainValidate = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
		brainValidate.displaySecond();
		
		ImageButton addPlayer = new ImageButton(ADD_IMAGE, ADD_IMAGE_HOVER) {
			public void mouseClicked(MouseEvent e) {
				String errorMessage = getErrorMessage();
				boolean valid = errorMessage == null;
				// TODO REMOVE LINE BELOW AFTER DEBUGGING!
				valid = true;
				if (!valid) {
					JOptionPane.showMessageDialog(TournamentSelectionPanel.this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					// Create a new player.
					Player player = new Player(playerName.getText().trim(), currentBrain);
					players.add(player);
					playerNames.add(playerName.getText().trim());
					
					// Create a list item and ad in the new player
					ListItem newPlayerItem = new ListItem(playerName.getText().trim(), currentBrainName, itemPanel);
					newPlayerItem.changeSize(885, 50);
					newPlayerItem.setRemoveButton(DELETE_IMAGE, DELETE_IMAGE_HOVER, TournamentSelectionPanel.this);
					newPlayerItem.setPlayer(player);
					
					// Add the item to the item panel and refresh the scrollpane.
					itemPanel.add(newPlayerItem);
					itemPanel.revalidate();
					itemPanel.repaint();
					listHolder.revalidate();
					listHolder.repaint();
					
					//TODO AUTO SCROLL THE SCROLL BAR?
					
					// Get the fields ready for the next player.
					playerName.setText(generateName());
					currentBrain = null;
					brainValidate.displaySecond();
					nameAndUpload.repaint();
				}
			}
		};
		
		itemPanel = new JPanel();
		BoxLayout itemPanelLayout = new BoxLayout(itemPanel, BoxLayout.Y_AXIS);
		itemPanel.setLayout(itemPanelLayout);
		
		//listHolder = new JPanel();
		listHolder = new JScrollPane(itemPanel);
		listHolder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listHolder.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listHolder.setPreferredSize(new Dimension(900, 275));
		listHolder.setOpaque(true);
		
		ImageButton playButton = new ImageButton(PLAY_IMAGE, PLAY_IMAGE_HOVER) {
			public void mouseClicked(MouseEvent e) {
				boolean valid = true;
				if(valid){
					System.out.println(getGame());
					getGame().createTournament();
					getGame().getCurrentTournament().setPlayers(players);
					ArrayList<World> worlds = new ArrayList<World>();
					for(int i = 0; i < Integer.parseInt(worldNumberField.getText()); i++){
						worlds.add(World.generateWorld(150, 150, 7, 14, 11));
					}
					getGame().getCurrentTournament().setWorlds(worlds);
					getGame().setCurrentMatch(getGame().getCurrentTournament().getCurrentMatch());
					getGame().createMatchPanelGrid(getGame().getCurrentMatch().getWorld().sizeX, getGame().getCurrentMatch().getWorld().sizeY, 2, 1);
					getGame().switchScreen(Game.MATCH_SCREEN);
					getGame().startMatch();
				}
			}
		};
		
		//Back button
		ImageButton backButton = new ImageButton(BACK_BUTTON, BACK_HOVER_BUTTON){
			public void mouseClicked(MouseEvent e) {
				//reset();
				getGame().switchScreen(Game.MAIN_MENU_SCREEN);
			}
		};
		
		nameAndUpload.add(nickname);
		nameAndUpload.add(playerName);
		nameAndUpload.add(nameValidate);
		nameAndUpload.add(new FixedSpacerPanel(50, 76));
		nameAndUpload.add(brainLabel);
		nameAndUpload.add(openBrain);
		nameAndUpload.add(brainValidate);
		nameAndUpload.add(new FixedSpacerPanel(25, 76));
		nameAndUpload.add(addPlayer);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(nameAndUpload, BorderLayout.NORTH);
		centerPanel.add(listHolder, BorderLayout.CENTER);
		centerPanel.setOpaque(false);
		
		JLabel worldNumberLabel = new JLabel("Number of Worlds:");
		worldNumberLabel.setForeground(Color.WHITE);
		worldNumberLabel.setFont(new Font("Helvetica", 0, 25));
		
		worldNumberField = new JTextField("1", 3);
		worldNumberField.setFont(new Font("Helvetica", 0, 25));
		worldNumberField.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		worldNumberField.setOpaque(false);
		worldNumberField.setHorizontalAlignment(JTextField.CENTER);
		worldNumberField.setCaretColor(Color.WHITE);
		worldNumberField.setForeground(Color.WHITE);
		worldNumberField.setBackground(new Color(255,255,255,0));
		
		JPanel playButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		playButtonPanel.setOpaque(false);
		playButtonPanel.add(backButton);
		playButtonPanel.add(new FixedSpacerPanel(320, 20));
		playButtonPanel.add(worldNumberLabel);
		playButtonPanel.add(worldNumberField);
		playButtonPanel.add(new FixedSpacerPanel(20, 20));
		playButtonPanel.add(playButton);
		
		this.add(titlepanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(playButtonPanel, BorderLayout.SOUTH);
	}
	
	
	@Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	 }
	
	public void validatePlayerName(){
		String name = playerName.getText().trim();

		// Rule for nickname
		boolean validname = name.length() <= 20 && !name.equals("") && !playerNames.contains(name);

		// Update validate image
		if(validname){
			nameValidate.displayFirst();
		} else {
			nameValidate.displaySecond();
		}
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
		playerNames.remove(player.getNickname());
		if (Game.GUI_DEBUG) {
			System.out.println("TournamentSelection Players = " + players );
			System.out.println("TournamentSelection playerNames = " + playerNames);
		}
	}
	
	public String getErrorMessage(){
		String output = "";

		// New Player nickname validation
		if(!nameValidate.isFirstShown()){
			output += "New player's nickname is invalid!\n";
		}

		// New Player ant-brain validation
		if(!brainValidate.isFirstShown()){
			output += "New player's ant-brain is invalid!\n";
		}
		
		int worldNumber;
		
		try{
			worldNumber = Integer.parseInt(worldNumberField.getText());
			if(worldNumber <0){
				output += "The number of worlds should be an integer bigger than 1.\n";
			}
			else if(worldNumber > 100){
				output += "The number of worlds should be an integer smaller than 100.\n";
			}
		}catch(NumberFormatException e){
			output += "The number of worlds should be an integer. \n";
		}

		if(output.equals("")){
			output = null;
		}

		return output;
	}
	
	private String generateName(){
		nameGen++;
		return "Player" + nameGen;
	}
	
	public Game getGame() {
		return game;
	}
	
	/**
	 * Used to test this particular screen without the need for a Game model.
	 * @param args
	 */
	public static void main(String args[]){
		JFrame jframe = new JFrame();
		jframe.add(new TournamentSelectionPanel(null));
		jframe.setVisible(true);
		jframe.setSize(1024, 576);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}


	@Override
	public void update() {
		//reset();
	}


	@Override
	public void reset() {
		// TODO: stuff
	}
}

