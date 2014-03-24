package graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private BufferedImage image;

	public ImagePanel(BufferedImage image) {
    	this.setLayout(new GridLayout(1,1));
    	
    	int width = image.getWidth();
    	int height = image.getHeight();
    	Dimension dim = new Dimension(width, height);
    	this.setPreferredSize(dim);
    	this.setMinimumSize(dim);
    	this.setMaximumSize(dim);
 
    	this.image = image;
    	this.setOpaque(false);
    } 

	@Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(image, 0, 0, null);
    }
}
