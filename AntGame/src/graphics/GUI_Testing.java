package graphics;
import java.awt.*;
import javax.swing.*;


public class GUI_Testing{
	public static void main(String args[]) {
		Window window = new Window();
		window.createMatch(150, 150, 4, 1);
		window.switchScreen(Window.MATCH_SCREEN);
		window.startMatch(); // NEEDS THREADING
		
		
		// Updating the HexGrid probably needs to be in a worker thread. (Swings timer will do here);
		// Likewise with updating the ant game model 
	}
}