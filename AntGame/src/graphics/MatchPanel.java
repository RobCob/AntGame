package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class MatchPanel extends JPanel {
	private Random rand = new Random();
	private HexGrid grid;
	private JScrollPane scrollPane;
	
	public MatchPanel(Window parent, HexGrid grid){
		this.grid = grid;
		
		this.scrollPane = new JScrollPane(grid);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPane.setMinimumSize(new Dimension(400, 400));
		scrollPane.setPreferredSize(new Dimension(400, 400));

		// HUD
		JPanel hud = new JPanel(new GridLayout(4,1));
		JButton addAntTestButton = new JButton("ADD ANT TEST");
		addAntTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGrid().getHexagon(rand.nextInt(30), rand.nextInt(30)).setFillColor(Color.RED);
				getGrid().refresh();
			}
		});
		
		JButton removeAllButton = new JButton("CLEAR GRID");
		removeAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGrid().clearAll();
				getGrid().refresh();
			}
		});
		
		JButton increaseSizeButton = new JButton("+");
		increaseSizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGrid().increaseSize();
				getGrid().revalidate();
				getScrollPane().paintAll(scrollPane.getGraphics());
				//getGrid().refresh();
			}
		});
		
		JButton decreaseSizeButton = new JButton("-");
		decreaseSizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getGrid().decreaseSize();
				getGrid().revalidate();
				getScrollPane().paintAll(scrollPane.getGraphics());
				//getGrid().refresh();
			}
		});
		
		hud.add(addAntTestButton);
		hud.add(removeAllButton);
		hud.add(increaseSizeButton);
		hud.add(decreaseSizeButton);
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(hud, BorderLayout.EAST);
	}
	
	public HexGrid getGrid(){
		return this.grid;
	}
	
	public void setGrid(HexGrid grid) {
		this.grid = grid;
		this.scrollPane.removeAll();
		this.scrollPane.add(grid);
		this.scrollPane.revalidate();
		this.scrollPane.repaint();
	}
		
	public JScrollPane getScrollPane() {
		return this.scrollPane;
	}
}
