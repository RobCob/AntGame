package graphics;
import java.awt.*;
import javax.swing.*;

public class GUI_Testing{
	public static void main(String args[]) {
		int rows = 20;
		int cols = 15;
		int size =  20;
		int strokeWidth = 2;
		HexGrid grid = new HexGrid(rows, cols, size, strokeWidth);
		
		JFrame window = new JFrame();
		window.setSize(640, 420);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(grid);
		window.setVisible(true);
		
		
		//EDIT THE HEX GRID, AFTER CREATION.
		grid.getHexagon(0, 0).setFillColor(Hexagon.BLACK_ANT_COLOR);
		//grid.getHexagon(3, 6).setOutlineColor(Hexagon.RED_ANT_COLOR);
		//grid.getHexagon(4, 4).setOutlineColor(Hexagon.RED_ANT_COLOR);
		grid.getHexagon(8, 7).setFillColor(Hexagon.BLACK_ANT_COLOR);
		grid.getHexagon(10, 1).setFillColor(Hexagon.RED_ANT_COLOR);
		grid.getHexagon(8, 7).setFillColor(Hexagon.EMPTY_CELL_COLOR);

	}
}