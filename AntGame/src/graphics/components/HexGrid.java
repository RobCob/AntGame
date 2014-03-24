package graphics.components;

import java.awt.*;

import javax.swing.*;

/**
 * A JPanel containing the Hexagon grid.
 */
public  class HexGrid extends JPanel {
	private Hexagon[][] grid;
	private int rows;
	private int cols;
	private int size;
	private int strokeWidth;
	
	public HexGrid(int rows, int cols, int size, int strokeWidth) {
		this.size = size;
		this.strokeWidth = strokeWidth;
		this.rows = rows;
		this.cols = cols;
		newGrid(rows, cols, size, strokeWidth);
	}
	
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
	
	// Override method above to do nothing
	// Invoke all painting from a thread using this method?
	public void customPaintComponent(Graphics g){};
	
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

	public void refresh() {
		this.paintComponent(this.getGraphics());
	}
	
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
	
	public void setDimensions(int width, int height){
		Dimension dimension = new Dimension(width, height);
		this.setMinimumSize(dimension);
		this.setPreferredSize(dimension);
		this.setMaximumSize(dimension);
		this.revalidate();
	}
	
	public void clearAll() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[col][row].setFillColor(Hexagon.EMPTY_CELL_COLOR);
			}
		}
	}

	public void increaseSize() {
		resize(size+=2);
	}

	public void decreaseSize() {
		if (size > 2) {
			resize(size-=2);
		}
	}
	
	public void addDefaultOutlines() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[col][row].setStrokeWidth(1);
				grid[col][row].setOutlineColor(Hexagon.DEFAULT_OUTLINE_COLOR);
			}
		}
	}
	
	public void removeOutlines() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				grid[col][row].setStrokeWidth(1);
				grid[col][row].setOutlineColor(Hexagon.EMPTY_CELL_COLOR);
			}
		}
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getColumns() {
		return this.cols;
	}
	
	public Hexagon[][] getHexagonGrid() {
		return this.grid;
	}

	public void setHexagonGrid(Hexagon[][] hexagons) {
		this.grid  = hexagons;		
	}
}

