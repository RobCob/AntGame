import java.awt.*;

import javax.swing.*;

public class GUI_Testing{
	public static void main(String args[]) {
		int rows = 22;
		int cols = 14;
		int size = 16;
		HexGrid grid = new HexGrid(rows, cols, size);
		
		JFrame window = new JFrame();
		window.setSize(640, 420);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(grid);
		window.setVisible(true);
	}
}

class HexGrid extends JPanel {
	Hexagon[][] grid;
	
	public HexGrid(int rows, int cols, int size) {
		grid = new Hexagon[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[row][col] = new Hexagon(row, col, size);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
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
		Graphics2D g2 = (Graphics2D) g; // Graphics2D has a load of useful methods.
		
		// Draw the inside (fill)
		g2.setColor(h.getFillColor());
		g2.fillPolygon(h);
		
		// Draw the outline.
		g2.setStroke(new BasicStroke(1));
		g2.setColor(h.getOutlineColor());
		g2.drawPolygon(h);
	}
}


class Hexagon extends Polygon {
	public static final Color redAntColor = Color.RED; 
	public static final Color blackAntColor = Color.BLACK;
	
	private Color fill;
	private Color outline;
	private int centerX; // Centre x co-ord (px)
	private int centerY; // Centre y co-ord (px)
	private final int SIZE; // Size of hexagon (like the radius)
	
	public Hexagon(int x, int y, int size) {
		SIZE = size;
		this.fill = Color.WHITE;
		this.outline = Color.BLACK;
		
		int xOffset = 0; // Used to interleave hexagons 
		int yOffset = 0; // Used to interleave hexagons
		
		int height = SIZE * 2;
		int width = (int) (Math.sqrt(3)/2 * height); 
		
		// If odd row, shift right.
		if (y % 2 != 0 && y != 0) {
			xOffset = width/2 + 1;
		}
		
		// Shift hexagons up, so they interleave.
		yOffset = -y*height/4;
		
		// Set the centre x and y co-ords.
		int centerX = width*x + SIZE + xOffset;
		int centerY = height*y + SIZE + yOffset;
		
		
		// Set the points of the hexagon, corresponding to the centre points and size.
	    for (int i = 0; i < 6; i++) {
	    	double angle = 2 * Math.PI /6* (i+0.5);
	    	this.addPoint((int)(centerX + SIZE*Math.cos(angle)), (int)(centerY + SIZE*Math.sin(angle)));
	    }
	}
	
	public Color getFillColor() {
		return this.fill;
	}
	
	public Color getOutlineColor() {
		return this.outline;
	}
	
	public void setFillColor(Color fill) {
		this.fill = fill;
	}
	
	public void setOutlineColor(Color fill) {
		this.outline = outline;
	}
}