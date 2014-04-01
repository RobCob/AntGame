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

@SuppressWarnings("serial")
public class TournamentSelection extends JPanel{
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
	private static final BufferedImage DELETE_IMAGE = ImageLoader.loadImage("/TournamentSelectionImages/deleteButton.png");
	private static final BufferedImage DELETE_IMAGE_HOVER = ImageLoader.loadImage("/TournamentSelectionImages/deleteButtonHover.png");
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<String> playerNames = new ArrayList<String>();
	
	private JTextField playerName;
	private JTextField worldNumberField;
	private DualImagePanel nameValidate;
	private DualImagePanel brainValidate;
	private int nameGen = 0;
	private JFileChooser fc;
	private AntBrain currentBrain;
	private String currentName;
	private JScrollPane listHolder;
	JPanel itemPanel;
	
	
	public TournamentSelection(){
		this.setLayout(new FlowLayout());
		fc = new JFileChooser();
		
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
		playerName.setPreferredSize(new Dimension(200, 30));
		playerName.setFont(new Font("Helvetica", 0, 25));
		playerName.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		playerName.setOpaque(false);
		playerName.setHorizontalAlignment(JTextField.CENTER);
		playerName.setCaretColor(Color.WHITE);
		playerName.setForeground(Color.WHITE);
		playerName.setBackground(new Color(255,255,255,0));
		playerName.setAlignmentX(CENTER_ALIGNMENT);
		
		nameValidate = new DualImagePanel(TICK_IMAGE, CROSS_IMAGE);
		nameValidate.displayFirst();
		playerName.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				validatePlayerName();
				currentName = playerName.getText().trim();
			}

			public void insertUpdate(DocumentEvent arg0) {
				validatePlayerName();
				currentName = playerName.getText().trim();
			}

			public void removeUpdate(DocumentEvent arg0) {
				validatePlayerName();
				currentName = playerName.getText().trim();
			}
		});
		
		JLabel brainLabel = new JLabel("Ant-Brain:");
		brainLabel.setForeground(Color.WHITE);
		brainLabel.setFont(new Font("Helvetica", 0, 25));
		//brainLabel.setPreferredSize(new Dimension(125, 76));
		
		ImageButton openBrain = new ImageButton(UPLOAD_IMAGE, UPLOAD_ROLL_IMAGE) {
			public void mouseClicked(MouseEvent e) {
				int returnVal = fc.showOpenDialog(TournamentSelection.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File brainFile = fc.getSelectedFile();
					AntBrain brain = AntBrainReader.readBrain(brainFile);
					if (brain == null) {
						brainValidate.displaySecond();
					} else {
						brainValidate.displayFirst();
					}
					currentBrain = brain;
				}
			}
		};
		
		brainValidate = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
		brainValidate.displaySecond();
		
		ImageButton addPlayer = new ImageButton(ADD_IMAGE, ADD_IMAGE_HOVER) {
			public void mouseClicked(MouseEvent e) {
				String errorMessage = getErrorMessage();
				boolean valid = errorMessage == null;
				if (!valid) {
					JOptionPane.showMessageDialog(TournamentSelection.this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					players.add(new Player(playerName.getText().trim(), currentBrain));
					playerNames.add(playerName.getText().trim());
					ListItem newPlayerItem = new ListItem(playerName.getText().trim(), "", listHolder);
					newPlayerItem.changeSize(300, 30);
					itemPanel.add(newPlayerItem);
					listHolder.revalidate();
					listHolder.repaint();
					playerName.setText(generateName());
					currentBrain = null;
					brainValidate.displaySecond();
					nameAndUpload.repaint();
				}
			}
		};
		
		itemPanel = new JPanel(new GridLayout(5,1));
		
		JPanel listPanel = new JPanel();
		listPanel.setOpaque(false);
		listHolder = new JScrollPane();
		listHolder.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		listHolder.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listHolder.setPreferredSize(new Dimension(900, 275));
		listHolder.setOpaque(false);
		listHolder.add(itemPanel);
		listPanel.add(new FixedSpacerPanel(130, 285));
		listPanel.add(listHolder);
		listPanel.add(new FixedSpacerPanel(150, 285));
		
		
		ImageButton playButton = new ImageButton(PLAY_IMAGE, PLAY_IMAGE_HOVER) {
			public void mouseClicked(MouseEvent e) {
				
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
		centerPanel.add(listPanel, BorderLayout.CENTER);
		centerPanel.setOpaque(false);
		
		JLabel worldNumberLabel = new JLabel("Number of Worlds:");
		worldNumberLabel.setPreferredSize(new Dimension(135, 76));
		worldNumberLabel.setForeground(Color.WHITE);
		worldNumberLabel.setFont(new Font("Helvetica", 0, 25));
		worldNumberLabel.setAlignmentX(CENTER_ALIGNMENT);
		//worldNumberLabel.setPreferredSize(new Dimension(300, 76));
		
		worldNumberField = new JTextField("1", 11);
		worldNumberField.setPreferredSize(new Dimension(200, 30));
		worldNumberField.setFont(new Font("Helvetica", 0, 25));
		worldNumberField.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		worldNumberField.setOpaque(false);
		worldNumberField.setHorizontalAlignment(JTextField.CENTER);
		worldNumberField.setCaretColor(Color.WHITE);
		worldNumberField.setForeground(Color.WHITE);
		worldNumberField.setBackground(new Color(255,255,255,0));
		worldNumberField.setAlignmentX(CENTER_ALIGNMENT);
		
		JPanel playButtonPanel = new JPanel();
		playButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		playButtonPanel.setPreferredSize(new Dimension(1024, 76));
		playButtonPanel.setOpaque(false);
		playButtonPanel.add(worldNumberLabel);
		playButtonPanel.add(worldNumberField);
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

		if(output.equals("")){
			output = null;
		}

		return output;
	}
	
	private String generateName(){
		nameGen++;
		return "Player" + nameGen;
	}
	
	
	
	// Leave this in for testing this page by itself
	public static void main(String args[]){
		JFrame jframe = new JFrame();
		jframe.add(new TournamentSelection());
		jframe.setVisible(true);
		jframe.setSize(1024, 576);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
