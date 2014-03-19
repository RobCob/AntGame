package graphics;
import java.awt.*;
import javax.swing.*;

public  class HexGrid extends JPanel {
	private Hexagon[][] grid;
	
	public HexGrid(int rows, int cols, int size, int strokeWidth) {
		this.setOpaque(true);
		this.setBackground(Color.black);
		grid = new Hexagon[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[row][col] = new Hexagon(row, col, size, strokeWidth);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		int rows = grid.length;
		int cols = grid[0].length;
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Hexagon h = grid[row][col];
			    drawHexagon(g, h);
			}
		}
	}
	
	public void drawHexagon(Graphics g, Hexagon h) {
		super.paintComponents(g);

		Graphics2D g2 = (Graphics2D) g; // Graphics2D has a load of useful methods.
		
		// Draw the inside (fill)
		g2.setColor(h.getFillColor());
		g2.fillPolygon(h);
		
		// Draw the outline.
		g2.setStroke(new BasicStroke(h.getStrokeWidth()));
		g2.setColor(h.getOutlineColor());
		g2.drawPolygon(h);
	}
	
	public void setHexagon(int x, int y, Hexagon h) {
		grid[x][y] = h;
	}
	
	public Hexagon getHexagon(int x, int y) {
		return grid[x][y];
	}
}


