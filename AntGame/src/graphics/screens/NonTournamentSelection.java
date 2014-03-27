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

import testing.WorldFileChooser;

import model.BrainReader;

public class NonTournamentSelection  extends JPanel implements ActionListener {
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/NormalTest.png");
	private static final BufferedImage TOURNAMENT_BUTTON_BACKGROUND_IMAGE = ImageLoader.loadImage("/NormalTest.png");
	private static final BufferedImage TOURNAMENT_BUTTON_HOVER_IMAGE = ImageLoader.loadImage("/HoverTest.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/gradientBackground.jpg");
	private static final BufferedImage TICK_IMAGE = ImageLoader.loadImage("/tick_test.png");
	private static final BufferedImage CROSS_IMAGE = ImageLoader.loadImage("/cross_test.png");
	
	private JButton openButton1;
	private JButton openButton2;

	private JFileChooser fc; //This is the file chooser
	private BrainReader reader;

    public NonTournamentSelection() {
        super(new BorderLayout());
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
        JLabel p1Nicklabel = new JLabel("Nickname:");
        p1Label.setForeground(Color.WHITE);
        p1Label.setAlignmentX(CENTER_ALIGNMENT);
        //TextField
        JPanel p1NickLabel = new JPanel();
        JTextField nick1 = new JTextField("Player1", 10);
        nick1.setMaximumSize(new Dimension(200,30));
        nick1.setAlignmentX(CENTER_ALIGNMENT);
        p1NickLabel.add(nick1);
       
        //Add components to right panel
        JLabel p2Label = new JLabel("Player 2");
        p2Label.setForeground(Color.WHITE);
        p2Label.setFont(new Font("Helvetica", 0, 30));
        p2Label.setAlignmentX(CENTER_ALIGNMENT);
        //Nick
        JTextField nick2 = new JTextField("Player2", 10);
        nick2.setMaximumSize(new Dimension(200,30));
        nick2.setAlignmentX(CENTER_ALIGNMENT);
        
        //Label
        JLabel p1AntBrainLabel = new JLabel("Ant-Brain:");
        p1AntBrainLabel.setForeground(Color.WHITE);
        
        // Add player 1 components to left side panel.
        JPanel leftPanel = new JPanel();
        BoxLayout leftLayout = new BoxLayout(leftPanel, BoxLayout.Y_AXIS);
		leftPanel.setLayout(leftLayout);
        leftPanel.setOpaque(false);
        leftPanel.add(p1Label);
        leftPanel.add(p1NickLabel);
        leftPanel.add(openButton1);
        
        // Add player 2 components to right side panel.
        JPanel rightPanel = new JPanel();
        BoxLayout rightLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
		rightPanel.setLayout(rightLayout);
        rightPanel.setOpaque(false);
        rightPanel.add(p2Label);
        rightPanel.add(nick2);
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
    
    public static void main(String[] args) {
    	//Add content to the window.
        JFrame frame = new JFrame("World File Chooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 576);
        frame.add(new NonTournamentSelection());
        frame.setResizable(false);
        
        //Display the window.
        frame.setVisible(true);
    }
}

