import java.awt.*;
import javax.swing.*;

public class GUI_Testing{
	public static void main(String args[]) {
		int rows = 40;
		int cols = 30;
		int size = 8;
		int strokeWidth = 1;
		HexGrid grid = new HexGrid(rows, cols, size, strokeWidth);
		
		JFrame window = new JFrame();
		window.setSize(640, 420);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(grid);
		window.setVisible(true);
		
		
		//EDIT THE HEX GRID, AFTER CREATION.
		grid.getHexagon(0, 0).setFillColor(Hexagon.BLACK_ANT_COLOR);
		grid.getHexagon(3, 6).setFillColor(Hexagon.RED_ANT_COLOR);
		grid.getHexagon(8, 7).setFillColor(Hexagon.BLACK_ANT_COLOR);
		grid.getHexagon(10, 1).setFillColor(Hexagon.BLACK_ANT_COLOR);
		grid.getHexagon(8, 7).setFillColor(Hexagon.EMPTY_CELL_COLOR);

	}
}

class HexGrid extends JPanel {
	Hexagon[][] grid;
	
	public HexGrid(int rows, int cols, int size, int strokeWidth) {
		grid = new Hexagon[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[row][col] = new Hexagon(row, col, size, strokeWidth);
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


class Hexagon extends Polygon {
	public static final Color RED_ANT_COLOR = Color.RED; 
	public static final Color BLACK_ANT_COLOR = Color.BLACK;
	public static final Color EMPTY_CELL_COLOR = Color.WHITE;

	private Color fillColor;
	private Color outlineColor;
	private int strokeWidth;
	private int centerX; // Centre x co-ord (px)
	private int centerY; // Centre y co-ord (px)
	private final int SIZE; // Size of hexagon (similar to the radius)
	
	public Hexagon(int x, int y, int size, int strokeWidth) {
		this.SIZE = size;
		this.fillColor = Color.WHITE;
		this.outlineColor = Color.BLACK;
		this.strokeWidth = strokeWidth;
		
		int xOffset = 0; // Used to interleave hexagons 
		int yOffset = 0; // Used to interleave hexagons
		
		int height = SIZE * 2 + strokeWidth/2;
		int width = (int) (Math.sqrt(3)/2 * height)+ strokeWidth/2; 
		
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
	
	public int getStrokeWidth() {
		return this.strokeWidth;
	}

	public Color getFillColor() {
		return this.fillColor;
	}
	
	public Color getOutlineColor() {
		return this.outlineColor;
	}
	
	public void setFillColor(Color fill) {
		this.fillColor = fill;
	}
	
	public void setOutlineColor(Color outline) {
		this.outlineColor = outline;
	}
}