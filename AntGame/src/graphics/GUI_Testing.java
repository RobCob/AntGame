package graphics;
import java.awt.*;
import javax.swing.*;

public class GUI_Testing{
	public static void main(String args[]) {
		int cols = 15;
		int rows = 20;
		int size = 30;
		int strokeWidth = 1;
		HexGrid grid = new HexGrid(cols, rows, size, strokeWidth);
		grid.setMinimumSize(new Dimension(cols*size, rows*size*2));
		grid.setPreferredSize(new Dimension(cols*size*2, rows*size*2));

		JScrollPane scrollPane = new JScrollPane(grid,
		        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
		        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JFrame window = new JFrame();
		window.setSize(640, 420);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(scrollPane);
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