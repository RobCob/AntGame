package graphics;

import java.awt.Color;
import java.awt.Polygon;

/**
 * Hexagon: A regular hexagon formed from a AWT Polygon.
 */
public class Hexagon extends Polygon {
	public static final Color RED_ANT_COLOR = Color.RED; 
	public static final Color BLACK_ANT_COLOR = Color.GRAY;
	public static final Color EMPTY_CELL_COLOR = Color.WHITE;
	public static final Color DEFAULT_OUTLINE_COLOR = Color.BLACK; 
	public static final Color RED_ANTHILL_COLOR = Color.ORANGE;
	public static final Color BLACK_ANTHILL_COLOR = Color.BLACK;
	public static final Color ROCK_COLOR = Color.DARK_GRAY;
	public static final Color FOOD_COLOR = Color.MAGENTA; 

	private Color fillColor;
	private Color outlineColor;
	private int strokeWidth;
	private double centerX; // Centre x co-ord (px)
	private double centerY; // Centre y co-ord (px)
	private int x;
	private int y;
	private int size; // Size of hexagon (similar to the radius)
	
	/**
	 * Creates a new AWT Polygon as a regular hexagon.
	 * Sets the fill to be initially 'empty'.
	 * @param x the x position of the hexagon (in the grid, not pixels)
	 * @param y the y position of the hexagon (in the grid, not pixels)
	 * @param size the distance from the center of the hexagon to a perpendicular side.
	 * @param strokeWidth width of the outline in pixels.
	 */
	public Hexagon(int x, int y, int size, int strokeWidth) {
		this.size = size;
		this.fillColor = EMPTY_CELL_COLOR;
		this.outlineColor = DEFAULT_OUTLINE_COLOR;
		this.strokeWidth = strokeWidth;
		this.x = x;
		this.y = y;
		setSize(size);
		// Set the points of the hexagon, corresponding to the centre points and size.
	    setPoints();
	}
	
	/**
	 * Sets the points of the Polygon to be a hexagon at the correct (x,y) position.
	 */
	private void setPoints(){
		this.reset();
		for (int i = 0; i < 6; i++) {
	    	double angle = 2 * Math.PI /6* (i+0.5);
	    	int xPoint = (int)Math.round(centerX + (size-strokeWidth/2)*Math.cos(angle));
	    	int yPoint = (int)Math.round(centerY + (size-strokeWidth/2)*Math.sin(angle));
	    	this.addPoint(xPoint, yPoint);
	    }
	}
	
	/**
	 * Sets (and changes) the size of the hexagon.
	 * @param size the distance from the center of the hexagon to a perpendicular side.
	 */
	public void setSize(int size) {
		this.size = size;
		double xOffset = 0; // Used to interleave hexagons 
		double yOffset = 0; // Used to interleave hexagons
		
		int height = size * 2;
		double width = (Math.sqrt(3)/2 * height);
		
		// If odd row, shift right.
		if (y % 2 != 0 && y != 0) {
			xOffset = width/2;
		}
		
		// Shift hexagons up, so they interleave.
		yOffset = -y*height/4;
		
		// Set the centre x and y co-ords.
		centerX = width*x + size + xOffset;
		centerY = height*y + size + yOffset;
		setPoints();
	}
	
	/**
	 * Gets the hexagon's current stroke width.
	 * @return the width of the hexagon's current stroke.
	 */
	public int getStrokeWidth() {
		return this.strokeWidth;
	}
	
	/**
	 * Changes the stroke width and updates the points of the hexagons.
	 * @param strokeWidth the stroke width for the hexagon.
	 */
	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
		setPoints();
	}

	/**
	 * Gets the fill colour of the hexagon.
	 * @return the fill Color of the Hexagon.
	 */
	public Color getFillColor() {
		return this.fillColor;
	}
	
	/**
	 * Gets the outline colour of the hexagon.
	 * @return the Color of the Hexagon's outline.
	 */
	public Color getOutlineColor() {
		return this.outlineColor;
	}
	
	/**
	 * Set the fill colour of the hexagon.
	 * @param fill the fill Color of the Hexagon.
	 */
	public void setFillColor(Color fill) {
		this.fillColor = fill;
	}
	
	/**
	 * Set the stroke colour of the hexagon.
	 * @param outline the outline Color of the Hexagon.
	 */
	public void setOutlineColor(Color outline) {
		this.outlineColor = outline;
	}
}