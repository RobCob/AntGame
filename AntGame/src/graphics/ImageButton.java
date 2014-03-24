package graphics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;


public abstract class ImageButton extends JPanel implements MouseListener{
    private BufferedImage hoverImage;
    private BufferedImage backgroundImage;
    
    private Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    private Cursor POINTER_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    
	private BufferedImage currentImage;
    
    public ImageButton(BufferedImage backgroundImage, BufferedImage hoverImage) {
    	this.addMouseListener(this);
    	this.setLayout(new GridLayout(1,1));
    	
    	int width = backgroundImage.getWidth();
    	int height = backgroundImage.getHeight();
    	Dimension dim = new Dimension(width, height);
    	this.setPreferredSize(dim);
    	this.setMinimumSize(dim);
    	this.setMaximumSize(dim);
 
    	this.backgroundImage = backgroundImage;
    	this.hoverImage = hoverImage;
    	this.currentImage = backgroundImage;
    	
    	this.setOpaque(false);
    } 

    public abstract void mouseClicked(java.awt.event.MouseEvent e);

    public void mouseEntered(java.awt.event.MouseEvent e) {
    	this.currentImage = hoverImage;
    	this.revalidate();
    	this.setCursor(HAND_CURSOR);
    	repaint();
    }

    public void mouseExited(java.awt.event.MouseEvent e) {
    	this.currentImage = backgroundImage;
    	this.revalidate();
    	this.setCursor(POINTER_CURSOR);
    	repaint();
    }

    public void mousePressed(java.awt.event.MouseEvent e) {}

    public void mouseReleased(java.awt.event.MouseEvent e) {}

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(currentImage, 0, 0, null);
    }

}