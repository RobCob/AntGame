package graphics;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

import model.Game;

/**
 * ImageLoader: Load in an image (as a BufferedImage) that is stored in the res folder.
 */
public class ImageLoader {
	
	/**
	 * Get the image stored at the 'path' from the res folder.
	 * @param path the path of the file to retrieve from the 'res' folder...
	 * 		       E.g. for 'res/myimage.png' specify '/myimage.png'
	 * @return the BufferedImage if image is found, null otherwise.
	 */
	public static BufferedImage loadImage(String path) {
		try{
			if (Game.GUI_DEBUG) System.out.println("DEBUG | ImageLoader:loadImage(" + path + ") path = " + path);
			URL url = ImageLoader.class.getResource(path);
			if (Game.GUI_DEBUG) System.out.println("DEBUG | ImageLoader:loadImage(" + path + ") url = " + url);
			return ImageIO.read(url);
       } catch (Exception e) {
    	   e.printStackTrace();
       }
		return null;
	}
}
