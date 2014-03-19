package graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class MatchPanel extends JPanel {
	
	public MatchPanel(Window parent){
		
		int cols = 150;
		int rows = 150;
		int size = 6;
		int strokeWidth = 1;
		
		// GRID
		HexGrid grid = new HexGrid(cols, rows, size, strokeWidth);
		grid.setMinimumSize(new Dimension(cols*size, rows*size*2));
		grid.setPreferredSize(new Dimension((int)(cols*size*1.74), (int)(rows*size*1.51)));
		
		JScrollPane scrollPane = new JScrollPane(grid);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		scrollPane.setMinimumSize(new Dimension(400, 400));
		scrollPane.setPreferredSize(new Dimension(400, 400));

		// HUD
		JPanel hud = new JPanel();
		hud.add(new JButton("PLACEHOLDER"));
		hud.add(new JLabel("TESTING"));
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(hud, BorderLayout.EAST);
	}
}
