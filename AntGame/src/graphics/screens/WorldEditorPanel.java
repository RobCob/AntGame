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
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Game;

public class WorldEditorPanel extends JPanel{
	
	//images
	private static final BufferedImage ADD_IMAGE = ImageLoader.loadImage("/TournamentSelectionImages/addButtonImage.png");
	private static final BufferedImage ADD_HOVER_IMAGE = ImageLoader.loadImage("/TournamentSelectionImages/addButtonImage.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/GlobalImages/background.jpg");
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/WorldSelectionPanelImages/selectWorldTitle.png");
	private static final BufferedImage STATS_IMAGE = ImageLoader.loadImage("/NonTournamentResultsPanelImages/stats.png");
	
	public Game game;
	public JLabel rocksLabel;
	public JLabel foodLabel;
	public JLabel sizeOfAnthillLabel;
	
	public WorldEditorPanel(Game game){
		
		this.game = game;
		this.setLayout(new BorderLayout());
		
		// Title Panel
		JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 20)); 
		titleContainer.setOpaque(false);
		
		//Panel containing the selector (Rocks,Food and Anthill size)
		JPanel selectorPanel = new JPanel();
		BoxLayout selectorLayout = new BoxLayout(selectorPanel, BoxLayout.Y_AXIS);
		selectorPanel.setLayout(selectorLayout);
		
		//Number of Rocks Panel
		//Consists of a - button, a label with an int and a + button
		JPanel rocksPanel = new JPanel();
		BoxLayout rocksLayout = new BoxLayout(rocksPanel, BoxLayout.X_AXIS);
		rocksPanel.setLayout(rocksLayout);
		//Label
		rocksLabel = new JLabel("Small");
		rocksLabel.setForeground(Color.WHITE);
		rocksLabel.setFont(new Font("Helvetica", 0, 25));
		rocksLabel.setAlignmentX(CENTER_ALIGNMENT);
		rocksLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		rocksLabel.setMinimumSize(new Dimension(100,100));
		rocksLabel.setMaximumSize(new Dimension(100,100));
		
		//ImageButtons
		ImageButton rocksMinusButton = new ImageButton(ADD_IMAGE, ADD_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(rocksLabel.getText().equals("Medium")){
					rocksLabel.setText("Small");
				}
				else if(rocksLabel.getText().equals("Big")){
					rocksLabel.setText("Medium");
				}
				else{
					//do nothing
				}
			}
		};
		ImageButton rocksPlusButton = new ImageButton(ADD_IMAGE, ADD_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(rocksLabel.getText().equals("Small")){
					rocksLabel.setText("Medium");
				}
				else if(rocksLabel.getText().equals("Medium")){
					rocksLabel.setText("Big");
				}
				else{
					//do nothing
				}
			}
		};
		
		//rocks title
		JPanel rocksTitleContainer = new JPanel();
		BoxLayout rocksTitleLayout = new BoxLayout(rocksTitleContainer, BoxLayout.Y_AXIS);
		rocksTitleContainer.setLayout(rocksTitleLayout);
		rocksTitleContainer.add(new FixedSpacerPanel(100, 20));
		rocksTitleContainer.add(new ImagePanel(STATS_IMAGE));
		rocksTitleContainer.add(new FixedSpacerPanel(100, 20)); 
		rocksTitleContainer.setOpaque(false);
		
		//add the elements to the panel
		rocksPanel.add(rocksTitleContainer);
		rocksPanel.add(new FixedSpacerPanel(100, 0));
		rocksPanel.add(rocksMinusButton);
		rocksPanel.add(new FixedSpacerPanel(50, 0));
		rocksPanel.add(rocksLabel);
		rocksPanel.add(new FixedSpacerPanel(50, 0));
		rocksPanel.add(rocksPlusButton);
		
		rocksPanel.setOpaque(false);
		
		//Number of Food panel
		JPanel foodPanel = new JPanel(new BorderLayout());
		BoxLayout foodLayout = new BoxLayout(foodPanel, BoxLayout.X_AXIS);
		foodPanel.setLayout(foodLayout);
		
		//Label
		foodLabel = new JLabel("Small");
		foodLabel.setForeground(Color.WHITE);
		foodLabel.setFont(new Font("Helvetica", 0, 25));
		foodLabel.setAlignmentX(CENTER_ALIGNMENT);
		foodLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		foodLabel.setMinimumSize(new Dimension(100,100));
		foodLabel.setMaximumSize(new Dimension(100,100));
		
		//ImageButtons
		ImageButton foodMinusButton = new ImageButton(ADD_IMAGE, ADD_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(foodLabel.getText().equals("Medium")){
					foodLabel.setText("Small");
				}
				else if(foodLabel.getText().equals("Big")){
					foodLabel.setText("Medium");
				}
				else{
					//do nothing
				}
			}
		};
		ImageButton foodPlusButton = new ImageButton(ADD_IMAGE, ADD_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(foodLabel.getText().equals("Small")){
					foodLabel.setText("Medium");
				}
				else if(foodLabel.getText().equals("Medium")){
					foodLabel.setText("Big");
				}
				else{
					//do nothing
				}
			}
		};
		
		//food title
		JPanel foodTitleContainer = new JPanel();
		BoxLayout foodTitleLayout = new BoxLayout(foodTitleContainer, BoxLayout.Y_AXIS);
		foodTitleContainer.setLayout(foodTitleLayout);
		foodTitleContainer.add(new FixedSpacerPanel(100, 20));
		foodTitleContainer.add(new ImagePanel(STATS_IMAGE));
		foodTitleContainer.add(new FixedSpacerPanel(100, 20)); 
		foodTitleContainer.setOpaque(false);
		
		//add the elements to the panel
		foodPanel.add(foodTitleContainer);
		foodPanel.add(new FixedSpacerPanel(100, 0));
		foodPanel.add(foodMinusButton);
		foodPanel.add(new FixedSpacerPanel(50, 0));
		foodPanel.add(foodLabel);
		foodPanel.add(new FixedSpacerPanel(50, 0));
		foodPanel.add(foodPlusButton);
		
		foodPanel.setOpaque(false);
		
		//Size of Anthill panel
		JPanel sizeOfAnthillPanel = new JPanel();
		BoxLayout anthillLayout = new BoxLayout(sizeOfAnthillPanel, BoxLayout.X_AXIS);
		sizeOfAnthillPanel.setLayout(anthillLayout);
		
		//Label
		sizeOfAnthillLabel = new JLabel("Small");
		sizeOfAnthillLabel.setForeground(Color.WHITE);
		sizeOfAnthillLabel.setFont(new Font("Helvetica", 0, 25));
		sizeOfAnthillLabel.setAlignmentX(CENTER_ALIGNMENT);
		sizeOfAnthillLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
		sizeOfAnthillLabel.setMinimumSize(new Dimension(100,100));
		sizeOfAnthillLabel.setMaximumSize(new Dimension(100,100));
		
		//ImageButtons
		ImageButton sizeOfAnthillMinusButton = new ImageButton(ADD_IMAGE, ADD_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(sizeOfAnthillLabel.getText().equals("Medium")){
					sizeOfAnthillLabel.setText("Small");
				}
				else if(sizeOfAnthillLabel.getText().equals("Big")){
					sizeOfAnthillLabel.setText("Medium");
				}
				else{
					//do nothing
				}
			}
		};
		ImageButton sizeOfAnthillPlusButton = new ImageButton(ADD_IMAGE, ADD_HOVER_IMAGE){
			public void mouseClicked(MouseEvent e) {
				if(sizeOfAnthillLabel.getText().equals("Small")){
					sizeOfAnthillLabel.setText("Medium");
				}
				else if(sizeOfAnthillLabel.getText().equals("Medium")){
					sizeOfAnthillLabel.setText("Big");
				}
				else{
					//do nothing
				}
			}
		};
		
		//food title
		JPanel anthillTitleContainer = new JPanel();
		BoxLayout anthillTitleLayout = new BoxLayout(anthillTitleContainer, BoxLayout.Y_AXIS);
		anthillTitleContainer.setLayout(anthillTitleLayout);
		anthillTitleContainer.add(new FixedSpacerPanel(100, 20));
		anthillTitleContainer.add(new ImagePanel(STATS_IMAGE));
		anthillTitleContainer.add(new FixedSpacerPanel(100, 20)); 
		anthillTitleContainer.setOpaque(false);

		//add the elements to the panel
		sizeOfAnthillPanel.add(anthillTitleContainer);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(100, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillMinusButton);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(50, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillLabel);
		sizeOfAnthillPanel.add(new FixedSpacerPanel(50, 0));
		sizeOfAnthillPanel.add(sizeOfAnthillPlusButton);
		
		sizeOfAnthillPanel.setOpaque(false);
		
		//add the elements to the selector Panel
		selectorPanel.add(rocksPanel);
		selectorPanel.add(foodPanel);
		selectorPanel.add(sizeOfAnthillPanel);
		selectorPanel.setOpaque(false);
		
		add(titleContainer, BorderLayout.NORTH);
		add(selectorPanel, BorderLayout.CENTER);
		//add spacer panels for looking good
		add(new FixedSpacerPanel(200, 50), BorderLayout.WEST);
		add(new FixedSpacerPanel(200, 50), BorderLayout.EAST);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	}
	
	public static void main(String[] args){
		//Add content to the window.
		JFrame frame = new JFrame("World Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 576);
		frame.add(new WorldEditorPanel(null));
		frame.setResizable(false);

		//Display the window.
		frame.setVisible(true);
	}
}
