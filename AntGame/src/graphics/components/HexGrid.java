package graphics.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * A JPanel containing a Hexagon grid.
 * @author 105957
 */
public  class HexGrid extends JPanel {
	private Hexagon[][] grid;
	private int rows;
	private int cols;
	private int size;
	private int strokeWidth;
	
	/**
	 * @param rows the number of rows for the grid.
	 * @param cols the number of columns for the grid.
	 * @param size the size of each hexagon.
	 * @param strokeWidth the width of the stroke for each hexagon.
	 */
	public HexGrid(int rows, int cols, int size, int strokeWidth) {
		this.size = size;
		this.strokeWidth = strokeWidth;
		this.rows = rows;
		this.cols = cols;
		newGrid(rows, cols, size, strokeWidth);
	}
	
	/**
	 * Paints the grid of hexagon to the background of the HexGrid.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		int cols = grid.length;
		int rows = grid[0].length;
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				Hexagon h = grid[col][row];
			    drawHexagon(g, h);
			}
		}
	}
	
	/**
	 * Paints. an individual hexagon to the HexGrid.
	 * @param g graphics.
	 * @param h the Hexagon to paint.
	 */
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
	
	/**
	 * Assign a Hexagon to a position in the grid.
	 * @param x the x position for the Hexagon (column).
	 * @param y the y position for the Hexagon (row).
	 * @param h the hexagon to assign.
	 */
	public void setHexagon(int x, int y, Hexagon h) {
		grid[x][y] = h;
	}
	
	/**
	 * Get a hexagon from the grid.
	 * @param x the x position of the Hexagon.
	 * @param y the y position of the Hexagon.
	 * @return the Hexagon contained at (x, y) of the hexgrid.
	 */
	public Hexagon getHexagon(int x, int y) {
		return grid[x][y];
	}

	/**
	 * Repaint the grid of Hexagons to the background.
	 */
	public void refresh() {
		this.paintComponent(this.getGraphics());
	}
	
	/**
	 * Assign a new hexgrid to the panel.
	 * @param cols 
	 * @param rows
	 * @param size
	 * @param strokeWidth
	 */
	public void newGrid(int cols, int rows, int size, int strokeWidth) {
		this.rows = rows;
		this.cols = cols;
		this.size = size;
		this.strokeWidth = strokeWidth;
		this.setOpaque(true);
		this.setBackground(Color.black);
		grid = new Hexagon[cols][rows];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[col][row] = new Hexagon(col, row, size, strokeWidth);
			}
		}
		
		// TODO SORT OUT DIMENSION.
		setDimensions((int)(cols*size*1.74) + size, (int)(rows*size*1.51) + size);
	}
	
	/**
	 * Resize all of the hexagons in the grid.
	 * @param size the new size of each of the hexagons.
	 */
	public void resize(int size){
		this.size = size;
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[col][row].setSize(size);
			}
		}
		
		// TODO - SORT OUT DIMENSION.
		setDimensions((int)(cols*size*1.74) + size, (int)(rows*size*1.51) + (int)(size/2));
	}
	
	/**
	 * Set the dimension of the HexGrid panel.
	 * @param width width of the HexGrid panel.
	 * @param height height of the HexGrid panel.
	 */
	public void setDimensions(int width, int height){
		Dimension dimension = new Dimension(width, height);
		this.setMinimumSize(dimension);
		this.setPreferredSize(dimension);
		this.setMaximumSize(dimension);
		this.revalidate();
	}
	
	/**
	 * Reset all of the hexagon's fill to be an empty cell colour.
	 */
	public void clearAll() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[col][row].setFillColor(Hexagon.EMPTY_CELL_COLOR);
			}
		}
	}

	/**
	 * Increase the size of all of the Hexagons by 2.
	 * @return true if hexagons can be resized, false otherwise.
	 */
	public boolean increaseSize() {
		resize(size+=2);
		return true;
	}
	/**
	 * Decrease the size of all of the Hexagons by 2.
	 * @return true if hexagons can be resized, false otherwise.
	 */
	public boolean decreaseSize() {
		if (size > 2) {
			resize(size-=2);
			return true;
		}
		return false;
	}
	
	/**
	 * Sets each Hexagon to have the default outline (colour and width).
	 */
	public void addDefaultOutlines() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[col][row].setStrokeWidth(1);
				grid[col][row].setOutlineColor(Hexagon.DEFAULT_OUTLINE_COLOR);
			}
		}
	}
	
	/**
	 * Sets each Hexagon to have the default outline (colour and width).
	 */
	public void removeOutlines() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[col][row].setStrokeWidth(1);
				grid[col][row].setOutlineColor(Hexagon.EMPTY_CELL_COLOR);
			}
		}
	}
	
	/**
	 * Get the number of rows in the grid.
	 * @return the number of rows in the grid.
	 */
	public int getRows() {
		return this.rows;
	}
	
	/**
	 * Get the number of columns in the grid.
	 * @return the number of columns in the grid.
	 */
	public int getColumns() {
		return this.cols;
	}
	
	/**
	 * Returns a grid a Hexagons represented as a 2D array.
	 * @return a grid a Hexagons represented as a 2D array.
	 */
	public Hexagon[][] getHexagonGrid() {
		return this.grid;
	}

	/**
	 * Sets the hexagon grid.
	 * @param hexagons a 2D array of Hexagons ([column][row])
	 */
	public void setHexagonGrid(Hexagon[][] hexagons) {
		this.grid = hexagons;		
		this.cols = hexagons.length;
		this.rows = hexagons[0].length;
	}
	
	/**
	 * Set the dimensions of the HexGrid panel to fit the entire grid of Hexagons.
	 */
	public void refreshDimensions() {
		setDimensions((int)(cols*size*1.74) + size, (int)(rows*size*1.51) + size);
		this.revalidate();
	}
}


