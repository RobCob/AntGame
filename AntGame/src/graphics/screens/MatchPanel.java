package graphics.screens;

import graphics.components.FixedSpacerPanel;
import graphics.components.HexGrid;
import graphics.components.ImageButton;
import graphics.components.ImagePanel;
import graphics.components.NormalButton;
import graphics.utilities.ImageLoader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import model.Game;

public class MatchPanel extends JPanel {
	
	//images
	private static final BufferedImage LOW_BUTTON_IMAGE = ImageLoader.loadImage("/MatchPanelImages/lowSpeedButton.png");
	private static final BufferedImage LOW_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/MatchPanelImages/lowSpeedButtonHover.png");
	private static final BufferedImage MEDIUM_BUTTON_IMAGE = ImageLoader.loadImage("/MatchPanelImages/mediumSpeedButton.png");
	private static final BufferedImage MEDIUM_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/MatchPanelImages/mediumSpeedButtonHover.png");
	private static final BufferedImage HIGH_BUTTON_IMAGE = ImageLoader.loadImage("/MatchPanelImages/highSpeedButton.png");
	private static final BufferedImage HIGH_BUTTON_IMAGE_HOVER = ImageLoader.loadImage("/MatchPanelImages/highSpeedButtonHover.png");
	private static final BufferedImage GAME_SPEED_IMAGE = ImageLoader.loadImage("/MatchPanelImages/gameSpeedImage.png");
	
	
	private Random rand = new Random();
	private Game game;
	private HexGrid grid;
	private HexGrid gridBuffer;
	private JScrollPane scrollPane;
	
	public MatchPanel(Game game, HexGrid grid){
		
		this.game = game;
		this.grid = grid;
		
		this.scrollPane = new JScrollPane(grid);
		//scrollPane.setViewportView(grid);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		//scrollPane.setMinimumSize(new Dimension(400, 400));
		//scrollPane.setPreferredSize(new Dimension(400, 400));
		//scrollPane.setMaximumSize(new Dimension(400, 400));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(Color.WHITE);
		
		// HUD
		JPanel hud = new JPanel(new GridLayout(8,1));
				
		NormalButton refreshScreenButton = new NormalButton("Decrease Game Speed", NormalButton.GREEN_THEME) {
			public void mouseClicked(MouseEvent e) {
				getGame().setRoundsPerSecond(getGame().getRoundsPerSecond()-(getGame().getRoundsPerSecond()/5));			
			}
		};
		
		NormalButton addAntTestButton = new NormalButton("Increase Game Speed", NormalButton.GREEN_THEME) {
			public void mouseClicked(MouseEvent e) {
				getGame().setRoundsPerSecond(getGame().getRoundsPerSecond()+(getGame().getRoundsPerSecond()/5));
			}
		};
		
		NormalButton removeAllButton = new NormalButton("Clear Grid", NormalButton.GREEN_THEME) {
			public void mouseClicked(MouseEvent e) {
				getGrid().clearAll();
				getGrid().refresh();	
			}
		};
		
		NormalButton increaseSizeButton = new NormalButton("Zoom In (+)", NormalButton.GREEN_THEME) {
			public void mouseClicked(MouseEvent e) {
				getGrid().increaseSize();
				getGrid().revalidate();
				getScrollPane().revalidate();
			}
		};
		
		NormalButton decreaseSizeButton = new NormalButton("Zoom Out (-)", NormalButton.GREEN_THEME) {
			public void mouseClicked(MouseEvent e) {
				getGrid().decreaseSize();
				getGrid().revalidate();
				getScrollPane().revalidate();
			}
		};
		
		NormalButton stopGameButton = new NormalButton("Stop Match",  NormalButton.GREEN_THEME) {
			public void mouseClicked(MouseEvent e) {
				getGame().stopMatch();

			}
		};
		
		NormalButton gridLinesCheckBox = new NormalButton("Toggle Gridlines",  NormalButton.GREEN_THEME) {
			boolean gridlines = true;
			public void mouseClicked(MouseEvent e) {
				if (gridlines) {
					getGrid().removeOutlines();
					getGrid().refresh();
					gridlines = false;
				} else {
					getGrid().addDefaultOutlines();
					getGrid().refresh();
					gridlines = true;
				}

			}
		};
		/*
		JButton refreshScreenButton = new JButton("Refresh Screen");
		refreshScreenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGrid().refresh();
			}
		});
		
		JButton addAntTestButton = new JButton("Add Ant Test");
		addAntTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGrid().getHexagon(rand.nextInt(getGrid().getColumns()), rand.nextInt(getGrid().getRows())).setFillColor(Color.RED);
				getGrid().refresh();
			}
		});
		
		JButton removeAllButton = new JButton("Clear Grid");
		removeAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGrid().clearAll();
				getGrid().refresh();
			}
		});
		
		JButton increaseSizeButton = new JButton("Zoom In (+)");
		increaseSizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGrid().increaseSize();
				getGrid().revalidate();
				getScrollPane().revalidate();
				//getScrollPane().paintAll(scrollPane.getGraphics());
				//getGrid().refresh();
			}
		});
	
		
		JButton decreaseSizeButton = new JButton("Zoom Out (-)");
		decreaseSizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGrid().decreaseSize();
				getGrid().revalidate();
				getScrollPane().revalidate();
				//getScrollPane().paintAll(scrollPane.getGraphics());
				//getGrid().refresh();
			}
		});
		
		JButton stopGameButton = new JButton("Stop Match");
		stopGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGame().stopMatch();
			}
		});
			
		
		JCheckBox gridLinesCheckBox = new JCheckBox("Gridlines ");
		gridLinesCheckBox.setSelected(true);
		gridLinesCheckBox.setOpaque(false);
		gridLinesCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					getGrid().removeOutlines();
					getGrid().refresh();
				} else {
					getGrid().addDefaultOutlines();
					getGrid().refresh();
				}
			}
		});
		
		*/
		
		JLabel debugLabel = new JLabel("Debugging Tools", JLabel.CENTER);
		debugLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
		debugLabel.setForeground(new Color(220, 250, 220));
		
		hud.add(debugLabel);
		hud.add(refreshScreenButton);
		hud.add(addAntTestButton);
		hud.add(removeAllButton);
		hud.add(increaseSizeButton);
		hud.add(decreaseSizeButton);
		hud.add(stopGameButton);
		hud.add(gridLinesCheckBox);
		hud.setBackground( new Color(42, 88, 40));
		
		//Horizontal HUD
		JPanel horizontalHUD = new JPanel();
		BoxLayout HUDLayout = new BoxLayout(horizontalHUD, BoxLayout.X_AXIS);
		horizontalHUD.setLayout(HUDLayout);
		
		//Game Speed Label
		JPanel gameSpeedTitle = new JPanel();
		BoxLayout gameSpeedLayout = new BoxLayout(gameSpeedTitle, BoxLayout.Y_AXIS);
		gameSpeedTitle.setLayout(gameSpeedLayout);
		gameSpeedTitle.add(new FixedSpacerPanel(100, 20));
		gameSpeedTitle.add(new ImagePanel(GAME_SPEED_IMAGE));
		gameSpeedTitle.add(new FixedSpacerPanel(100, 40));
		gameSpeedTitle.setOpaque(false);
		gameSpeedTitle.setAlignmentX(CENTER_ALIGNMENT);
		gameSpeedTitle.setAlignmentY(CENTER_ALIGNMENT);
		
		//Low Speed button
		ImageButton lowSpeed = new ImageButton(LOW_BUTTON_IMAGE, LOW_BUTTON_IMAGE_HOVER) {
        	public void mouseClicked(MouseEvent e) {
        		getGame().setRoundsPerSecond(10);
        	}
        };
        lowSpeed.setAlignmentX(CENTER_ALIGNMENT);
        lowSpeed.setAlignmentY(CENTER_ALIGNMENT);

        //Medium Speed button
        ImageButton mediumSpeed = new ImageButton(MEDIUM_BUTTON_IMAGE, MEDIUM_BUTTON_IMAGE_HOVER) {
        	public void mouseClicked(MouseEvent e) {
        		getGame().setRoundsPerSecond(1000);
        	}
        };
        mediumSpeed.setAlignmentX(CENTER_ALIGNMENT);
        mediumSpeed.setAlignmentY(CENTER_ALIGNMENT);
        
        //High Speed button
        ImageButton highSpeed = new ImageButton(HIGH_BUTTON_IMAGE, HIGH_BUTTON_IMAGE_HOVER) {
        	public void mouseClicked(MouseEvent e) {
        		getGame().setRoundsPerSecond(10000);
        	}
        };
        highSpeed.setAlignmentX(CENTER_ALIGNMENT);
        highSpeed.setAlignmentY(CENTER_ALIGNMENT);
		
        horizontalHUD.add(gameSpeedTitle);
        horizontalHUD.add(new FixedSpacerPanel(68, 0));
        horizontalHUD.add(lowSpeed);
        horizontalHUD.add(new FixedSpacerPanel(68, 0));
        horizontalHUD.add(mediumSpeed);
        horizontalHUD.add(new FixedSpacerPanel(68, 0));
        horizontalHUD.add(highSpeed);
        
        horizontalHUD.setAlignmentX(CENTER_ALIGNMENT);
        horizontalHUD.setAlignmentY(CENTER_ALIGNMENT);
        
        //JPanel used to format the HUD
//        JPanel formatHUD = new JPanel(new BorderLayout());
//        formatHUD.setMaximumSize(new Dimension(100,100));
//        formatHUD.add(new FixedSpacerPanel(0, 20), BorderLayout.NORTH);
//        formatHUD.add(horizontalHUD, BorderLayout.CENTER);
//        formatHUD.add(new FixedSpacerPanel(0, 20), BorderLayout.SOUTH);
        
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(hud, BorderLayout.EAST);
		this.add(horizontalHUD, BorderLayout.SOUTH);
	}
	
	public Game getGame(){
		return this.game;
	}
	
	public HexGrid getGrid(){
		return this.grid;
	}
	
	public void setGrid(HexGrid grid) {
		this.grid = grid;
		//this.scrollPane.removeAll();
		//this.scrollPane.add(grid);
		scrollPane.revalidate();
		scrollPane.repaint();
	}
		
	public JScrollPane getScrollPane() {
		return this.scrollPane;
	}
	
	
	//Used for debug
	public static void main(String[] args){
		//Add content to the window.
        JFrame frame = new JFrame("Debug");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 576);
        frame.add(new MatchPanel(null,null));
        frame.setResizable(false);
        
        //Display the window.
        frame.setVisible(true);
	}
}
