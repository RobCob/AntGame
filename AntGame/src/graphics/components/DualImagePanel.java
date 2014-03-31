package graphics.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DualImagePanel extends JPanel{
	private BufferedImage firstImage;
	private BufferedImage secondImage;
	private BufferedImage currentImage;
	
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

	@Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(currentImage, 0, 0, null);
    }
	
	public void switchImage() {
		if (currentImage.equals(firstImage)) {
			currentImage = secondImage;
		} else {
			currentImage = firstImage;
		}
		this.repaint();
	}
	
	public void displayFirst(){
		
		if (!currentImage.equals(firstImage)) {
			currentImage = firstImage;
			this.repaint();
		} 
	}
	
	public void displaySecond(){
		if (!currentImage.equals(secondImage)) {
			currentImage = secondImage;
			this.repaint();
		} 
	}
	
	public boolean isFirstShown(){
		return currentImage == firstImage;
	}
}
