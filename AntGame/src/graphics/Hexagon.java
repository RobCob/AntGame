package graphics;

import java.awt.Color;
import java.awt.Polygon;

public class Hexagon extends Polygon {
	public static final Color RED_ANT_COLOR = Color.RED; 
	public static final Color BLACK_ANT_COLOR = Color.GRAY;
	public static final Color EMPTY_CELL_COLOR = Color.WHITE;
	public static final Color RED_ANTHILL_COLOR = Color.ORANGE;
	public static final Color BLACK_ANTHILL_COLOR = Color.BLACK;
	public static final Color ROCK_COLOR = Color.DARK_GRAY;
	public static final Color FOOD_COLOR = Color.MAGENTA; 

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
		
		int height = SIZE*2;
		int width = (int) (Math.sqrt(3)/2 * height + strokeWidth/2)  ; 
		
		// If odd row, shift right.
		if (y % 2 != 0 && y != 0) {
			xOffset = width/2 +1;
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