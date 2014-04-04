package graphics.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * DualImagePanel: A JPanel that can switch between two images. On creation it
 * displays the firstImage, the can be switched to the second image by calling 
 * switchImage().
 */
public class DualImagePanel extends JPanel{
	private static final long serialVersionUID = -437128134824859235L;
	private BufferedImage firstImage; // First image to be shown.
	private BufferedImage secondImage; // Second image to be shown.
	private BufferedImage currentImage; // Current image being shown.
	
	/**
	 * Constructor.
	 * @param firstImage The first BufferedImage.
	 * @param secondImage The second BufferedImage.
	 */
	public DualImagePanel(BufferedImage firstImage, BufferedImage secondImage) {
    	this.setLayout(new GridLayout(1,1));
    	
    	int width = firstImage.getWidth();
    	int height = secondImage.getHeight();
    	Dimension dim = new Dimension(width, height);
    	this.setPreferredSize(dim);
    	this.setMinimumSize(dim);
    	this.setMaximumSize(dim);
 
    	this.firstImage = firstImage;
    	this.secondImage = secondImage;
    	this.currentImage = firstImage;
    	this.setOpaque(false);
    } 

	/**
	 * Paints the current image to the background.
	 */
	@Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(currentImage, 0, 0, null);
    }
	
	/**
	 * Switch the background image to the image that is not currently being displayed.
	 */
	public void switchImage() {
		if (currentImage.equals(firstImage)) {
			currentImage = secondImage;
		} else {
			currentImage = firstImage;
		}
		this.repaint();
	}
	
	/**
	 * Change the background image to be the first image.
	 */
	public void displayFirst(){
		if (!currentImage.equals(firstImage)) {
			currentImage = firstImage;
			this.repaint();
		} 
	}
	
	/**
	 * Change the background image to be the second image.
	 */
	public void displaySecond(){
		if (!currentImage.equals(secondImage)) {
			currentImage = secondImage;
			this.repaint();
		} 
	}
	
	/**
	 * Returns true if the first image is currently being displayed.
	 * @return true if the first image is currently being displayed.
	 */
	public boolean isFirstShown(){
		return currentImage == firstImage;
	}
}
