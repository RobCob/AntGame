package graphics.screens;
import graphics.components.DualImagePanel;
import graphics.components.ImageButton;
import graphics.utilities.ImageLoader;
import model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class TournamentSelection extends JPanel{
	
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage TICK_IMAGE = ImageLoader.loadImage("/GlobalImages/tick.png");
	private static final BufferedImage CROSS_IMAGE = ImageLoader.loadImage("/GlobalImages/cross.png");
	private static final BufferedImage UPLOAD_IMAGE = ImageLoader.loadImage("/NonTournamentSelectionImages/uploadButtonImage.png");
	private static final BufferedImage UPLOAD_ROLL_IMAGE = ImageLoader.loadImage("/NonTournamentSelectionImages/uploadButtonImageHover.png");
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<String> playerNames = new ArrayList<String>();
	
	private JTextField playername;
	private DualImagePanel validate;
	private DualImagePanel brainValidate;
	private int nameGen = 0;
	private JFileChooser fc;
	private AntBrain currentBrain;
	private String currentName;
	JList<String> playerList;
	
	
	public TournamentSelection(){
		this.setLayout(new FlowLayout());
		fc = new JFileChooser();
		
		JLabel title = new JLabel("Tournament - Brain Selection");
		// CHANGE THE ABOVE WHEN ADDING OUR NEW FONTS
		
		JPanel titlepanel = new JPanel();
		titlepanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		titlepanel.setPreferredSize(new Dimension(1024, 76));
		titlepanel.add(title);
		titlepanel.setOpaque(false);
		
		//Panel to contain a row with nickname, the brain to be associated with it, an ant brain to uploader and an add player button.
		JPanel nameAndUpload = new JPanel();
		nameAndUpload.setLayout(new FlowLayout(FlowLayout.CENTER));
		nameAndUpload.setPreferredSize(new Dimension(1024, 76));
		nameAndUpload.setOpaque(false);
		
		JLabel nickname = new JLabel("Nickname:");
		nickname.setPreferredSize(new Dimension(135, 76));
		nickname.setForeground(Color.WHITE);
		nickname.setFont(new Font("Helvetica", 0, 25));
		nickname.setAlignmentX(CENTER_ALIGNMENT);
		
		playername = new JTextField(generateName(), 11);
		playername.setPreferredSize(new Dimension(200, 30));
		playername.setFont(new Font("Helvetica", 0, 25));
		playername.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		playername.setOpaque(false);
		playername.setHorizontalAlignment(JTextField.CENTER);
		playername.setCaretColor(Color.WHITE);
		playername.setForeground(Color.WHITE);
		playername.setBackground(new Color(255,255,255,0));
		playername.setAlignmentX(CENTER_ALIGNMENT);
		
		validate = new DualImagePanel(TICK_IMAGE, CROSS_IMAGE);
		validate.displayFirst();
		playername.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				validatePlayerName();
				currentName = playername.getText();
			}

			public void insertUpdate(DocumentEvent arg0) {
				validatePlayerName();
				currentName = playername.getText();
			}

			public void removeUpdate(DocumentEvent arg0) {
				validatePlayerName();
				currentName = playername.getText();
			}
		});
		
		JPanel smallPadder = new JPanel();
		smallPadder.setPreferredSize(new Dimension(50, 76));
		smallPadder.setOpaque(false);
		JPanel smallPadder2 = new JPanel();
		smallPadder2.setPreferredSize(new Dimension(25, 76));
		smallPadder2.setOpaque(false);
		
		JLabel brainLabel = new JLabel("Ant-Brain:");
		brainLabel.setForeground(Color.WHITE);
		brainLabel.setFont(new Font("Helvetica", 0, 25));
		brainLabel.setPreferredSize(new Dimension(125, 76));
		
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
		
		JButton addPlayer = new JButton("+");
		addPlayer.setFont(new Font("Helvetica", 0, 25));
		
		String[] FAKEDATA = {"1","2","3","4","5","6","7","8","9","10"};
		playerList = new JList<String>(FAKEDATA);
		playerList.setPreferredSize(new Dimension(700, 285));
		playerList.setOpaque(false);                         // Try commenting this out, not sure if better with or without
		JPanel listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(1024, 285));
		listPanel.setOpaque(false);
		JScrollPane listHolder = new JScrollPane(playerList);
		listHolder.getVerticalScrollBar().setOpaque(false);
		listHolder.setPreferredSize(new Dimension(725, 285));
		listHolder.setOpaque(false);
		JPanel padder1 = new JPanel();
		padder1.setPreferredSize(new Dimension(130, 285));
		padder1.setOpaque(false);
		JPanel padder2 = new JPanel();
		padder2.setPreferredSize(new Dimension(150, 285));
		padder2.setOpaque(false);
		listPanel.add(padder1);
		listPanel.add(listHolder);
		listPanel.add(padder2);
		
		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.CENTER));
		temp2.setPreferredSize(new Dimension(1024, 76));
		temp2.setOpaque(false);
		
		nameAndUpload.add(nickname);
		nameAndUpload.add(playername);
		nameAndUpload.add(validate);
		nameAndUpload.add(smallPadder);
		nameAndUpload.add(brainLabel);
		nameAndUpload.add(openBrain);
		nameAndUpload.add(brainValidate);
		nameAndUpload.add(smallPadder2);
		nameAndUpload.add(addPlayer);
		this.add(titlepanel);
		this.add(nameAndUpload);
		this.add(listPanel);
		this.add(temp2);
	}
	
	
	@Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	 }
	
	public void validatePlayerName(){
		String name = playername.getText().trim();

		// Rule for nickname
		boolean validname = name.length() <= 20 && !name.equals("") && !playerNames.contains(name);

		// Update validate image
		if(validname){
			validate.displayFirst();
		} else {
			validate.displaySecond();
		}
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
