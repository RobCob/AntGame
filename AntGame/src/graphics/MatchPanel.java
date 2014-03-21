package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class MatchPanel extends JPanel {
	Random rand = new Random();
	
	public MatchPanel(Window parent){
		
		int cols = 150;
		int rows = 150;
<<<<<<< HEAD
		final int size = 2;
=======
		int size = 8;
>>>>>>> FETCH_HEAD
		int strokeWidth = 1;
		
		// GRID
		final HexGrid grid = new HexGrid(cols, rows, size, strokeWidth);
		
		final JScrollPane scrollPane = new JScrollPane(grid);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPane.setMinimumSize(new Dimension(400, 400));
		scrollPane.setPreferredSize(new Dimension(400, 400));

		// HUD
		JPanel hud = new JPanel(new GridLayout(4,1));
		JButton addAntTestButton = new JButton("ADD ANT TEST");
		addAntTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				grid.getHexagon(rand.nextInt(30), rand.nextInt(30)).setFillColor(Color.RED);
				grid.refresh();
			}
		});
		
		JButton removeAllButton = new JButton("CLEAR GRID");
		removeAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				grid.clearAll();
				grid.refresh();
			}
		});
		
		JButton changeGridTestButton = new JButton("+");
		changeGridTestButton.addActionListener(new ActionListener() {
			int hexSize = size;
			public void actionPerformed(ActionEvent arg0) {
				grid.resize(hexSize+2);
				hexSize += 2;
				//grid.newGrid(rand.nextInt(100)+30,rand.nextInt(100)+40, rand.nextInt(4)*2+4,1);
				scrollPane.paintAll(scrollPane.getGraphics());
				grid.refresh();
			}
		});
		
		
		
		hud.add(addAntTestButton);
		hud.add(removeAllButton);
		hud.add(changeGridTestButton);

		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(hud, BorderLayout.EAST);
	}
	
}
