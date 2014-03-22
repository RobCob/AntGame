package graphics;
import java.awt.*;
import javax.swing.*;


public class GUI_Testing{
	public static void main(String args[]) {
		Window window = new Window();
		window.createMatch(150, 150, 4, 1);
		window.startMatch(); // NEEDS THREADING
		
		
		// Updating the HexGrid probably needs to be in a worker thred.
		// Likewise with updating the ant game model
		// The GUI will probably need to use SwingUtilities.invokeLater
	}
}