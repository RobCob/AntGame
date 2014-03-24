package graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageLoader {
	
	public static BufferedImage loadImage(String path) {
		try{
			System.out.println(path);
			URL url = ImageLoader.class.getResource(path);
			System.out.println(url);
			return ImageIO.read(url);
       } catch (Exception e) {
    	   e.printStackTrace();
       }
		return null;
	}
}
