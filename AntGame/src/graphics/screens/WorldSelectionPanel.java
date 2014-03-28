package graphics.screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import graphics.components.DualImagePanel;
import graphics.components.FixedSpacerPanel;
import graphics.components.HexGrid;
import graphics.components.ImagePanel;
import graphics.utilities.ImageLoader;
import model.Game;

public class WorldSelectionPanel extends JPanel{
	private static final BufferedImage TITLE_IMAGE = ImageLoader.loadImage("/NormalTest.png");
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/gradientBackground.jpg");
	private static final BufferedImage TICK_IMAGE = ImageLoader.loadImage("/tick_test.png");
	private static final BufferedImage CROSS_IMAGE = ImageLoader.loadImage("/cross_test.png");
	
	private Game game; 
	private HexGrid grid;
	private HexGrid gridBuffer;
	private JScrollPane scrollPane;
	private DualImagePanel worldValidateImage;
	
	public WorldSelectionPanel(Game game) {
		this.game = game;
		this.grid = new HexGrid(150, 150, 4, 1);
		
		// Title Panel
		JPanel titleContainer = new JPanel();
		BoxLayout titleLayout = new BoxLayout(titleContainer, BoxLayout.Y_AXIS);
		titleContainer.setLayout(titleLayout);
		titleContainer.add(new FixedSpacerPanel(100, 20));
		titleContainer.add(new ImagePanel(TITLE_IMAGE));
		titleContainer.add(new FixedSpacerPanel(100, 20)); 
		titleContainer.setOpaque(false);
		
		// Grid Panel
		JPanel gridPanel = new JPanel(new BorderLayout());
		gridPanel.setOpaque(false);
		this.scrollPane = new JScrollPane(grid);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPane.setMinimumSize(new Dimension(800, 350));
		scrollPane.setPreferredSize(new Dimension(800, 350));
		scrollPane.setMaximumSize(new Dimension(800, 350));
		scrollPane.setAlignmentX(CENTER_ALIGNMENT);
		
		JPanel uploadButtonPanel = new JPanel();
		JButton uploadWorldButton = new JButton();
		
        worldValidateImage = new DualImagePanel(TICK_IMAGE,CROSS_IMAGE);
        worldValidateImage.displaySecond();
        uploadButtonPanel.add(uploadWorldButton);
        uploadButtonPanel.add(worldValidateImage);
		
		gridPanel.add(scrollPane, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(titleContainer, BorderLayout.NORTH);
		this.add(gridPanel, BorderLayout.CENTER);
	}

	public Game getGame(){
		return game;
	}
	
	public void setGrid(HexGrid grid) {
		this.grid = grid;
		this.scrollPane.revalidate();
		this.scrollPane.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	}

	public static void main(String[] args) {
		//Add content to the window.
		JFrame frame = new JFrame("World Selection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 576);
		frame.add(new WorldSelectionPanel(null));
		frame.setResizable(false);

		//Display the window.
		frame.setVisible(true);
	}
}


