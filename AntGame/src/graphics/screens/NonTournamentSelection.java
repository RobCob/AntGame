package graphics.screens;

import graphics.components.FixedSpacerPanel;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.utilities.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import testing.WorldFileChooser;

import model.BrainReader;
import model.Game;

import graphics.components.*;

public class NonTournamentSelection  extends JPanel implements ActionListener {
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/NormalTest.png");
	private static final BufferedImage TOURNAMENT_BUTTON_BACKGROUND_IMAGE = ImageLoader.loadImage("/NormalTest.png");
	private static final BufferedImage TOURNAMENT_BUTTON_HOVER_IMAGE = ImageLoader.loadImage("/HoverTest.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/gradientBackground.jpg");
	private static final BufferedImage TICK_IMAGE = ImageLoader.loadImage("/tick_test.png");
	private static final BufferedImage CROSS_IMAGE = ImageLoader.loadImage("/cross_test.png");
	
	private Game game;

	private JButton openButton1;
	private JButton openButton2;

	private JFileChooser fc; //This is the file chooser
	private BrainReader reader;
	
	private JTextField p1NickField;
	private JTextField p2NickField;
	private DualImagePanel p1nickValidate;
	private DualImagePanel p2nickValidate;

	

    public NonTournamentSelection(Game game) {
    	this.game = game;
        this.setLayout(new BorderLayout());
        
        reader = new BrainReader();
        
        //Create a file chooser
        fc = new JFileChooser();
        
        //Create the Upload file button for player 1
        openButton1 = new JButton("Upload Player 1 AntBrain");
        openButton1.addActionListener(this);
        openButton1.setAlignmentX(CENTER_ALIGNMENT);
        
        //Create the Upload file button for player 2
        openButton2 = new JButton("Upload Player 2 AntBrain");
        openButton2.addActionListener(this);
        openButton2.setAlignmentX(CENTER_ALIGNMENT);

        //Add components to left panel
        JLabel p1Label = new JLabel("Player 1");
        p1Label.setForeground(Color.WHITE);
        p1Label.setFont(new Font("Helvetica", 0, 30));
        JLabel p1NickLabel = new JLabel("Nickname:");
        p1Label.setForeground(Color.WHITE);
        p1Label.setAlignmentX(CENTER_ALIGNMENT);
        //TextField
        
        
        JPanel p1NickPanel = new JPanel();
        p1NickPanel.setOpaque(false);
        
        p1NickField = new JTextField("Player1", 10);
       
        p1NickField.setMaximumSize(new Dimension(200,30));
        p1NickField.setAlignmentX(CENTER_ALIGNMENT);
        p1NickPanel.add(p1NickLabel);
        p1NickPanel.add(p1NickField);
        p1nickValidate = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
        p1NickField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (correctNickname(p1NickField.getText())) {
					p1nickValidate.displayFirst();
				} else {
					p1nickValidate.displaySecond();
				}
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (correctNickname(p1NickField.getText())) {
					p1nickValidate.displayFirst();
				} else {
					p1nickValidate.displaySecond();
				}
				
			}
        });
        p1NickPanel.add(p1nickValidate);
       
        //Add components to right panel
        
        JPanel p2NickPanel = new JPanel();
        p2NickPanel.setOpaque(false);
        
        JLabel p2Label = new JLabel("Player 2");
        p2Label.setForeground(Color.WHITE);
        p2Label.setFont(new Font("Helvetica", 0, 30));
        p2Label.setAlignmentX(CENTER_ALIGNMENT);
        
        JLabel p2NickLabel = new JLabel("Nickname:");
        
        //Nick
        p2NickField = new JTextField("Player2", 10);
        p2NickField.setMaximumSize(new Dimension(200,30));
        p2NickField.setAlignmentX(CENTER_ALIGNMENT);
        
        p2nickValidate = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
        p2NickField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (correctNickname(p2NickField.getText())) {
					p2nickValidate.displayFirst();
				} else {
					p2nickValidate.displaySecond();
				}
				
			}
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (correctNickname(p2NickField.getText())) {
					p2nickValidate.displayFirst();
				} else {
					p2nickValidate.displaySecond();
				}
			}
        });
        p2NickPanel.add(p2NickLabel);
        p2NickPanel.add(p2NickField);
        p2NickPanel.add(p2nickValidate);
        
        //Label
        JLabel p1AntBrainLabel = new JLabel("Ant-Brain:");
        p1AntBrainLabel.setForeground(Color.WHITE);
        
        // Add player 1 components to left side panel.
        JPanel leftPanel = new JPanel();
        BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(leftLayout);
        leftPanel.setOpaque(false);
        leftPanel.add(p1Label);
        leftPanel.add(p1NickPanel);
        leftPanel.add(openButton1);
        
        // Add player 2 components to right side panel.
        JPanel rightPanel = new JPanel();
        BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		rightPanel.setLayout(rightLayout);
        rightPanel.setOpaque(false);
        rightPanel.add(p2Label);
        rightPanel.add(p2NickPanel);
        rightPanel.add(openButton2);
        
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
		titleContainer.add(new FixedSpacerPanel(100, 20)); 
		titleContainer.setOpaque(false);
		
		//Create Go button
		JButton goButton = new JButton("GO");
		goButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getGame().createMatch(150, 150, 4, 1);
				getGame().switchScreen(Game.MATCH_SCREEN);
				getGame().startMatch();		
			}
			
		});
		JPanel goPanel = new JPanel();
		goPanel.setOpaque(false);
		goPanel.add(goButton);
		
		//Add containers
        add(titleContainer, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(goPanel, BorderLayout.SOUTH);
    }
    
	 @Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	 }
    
    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == openButton1) {
            int returnVal = fc.showOpenDialog(NonTournamentSelection.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
         	
            }
        }
        else if (e.getSource() == openButton2) {
            int returnVal = fc.showOpenDialog(NonTournamentSelection.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
         	
            }
        }
    }
    
    public boolean correctNickname(String nick){
    	//check p1 and p2 don't have the same name
    	//check if string is not empty
    	//not over 20 characters
    	
    	//HACK
    	if(p1NickField.getText().equals((p2NickField).getText())){
			p2nickValidate.displaySecond();
			p1nickValidate.displaySecond();
		} else {
			p2nickValidate.displayFirst();
			p1nickValidate.displayFirst();
		}
    	
    	if(p1NickField.getText().equals((p2NickField).getText())){
    		return false;
    	}
    	if(nick.trim().equals("")){
    		return false;
    	}
    	if(nick.length()>20){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    public Game getGame(){
    	return game;
    }
    public static void main(String[] args) {
    	//Add content to the window.
        JFrame frame = new JFrame("World File Chooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 576);
        frame.setResizable(false);
        
        //Display the window.
        frame.setVisible(true);
    }
}

