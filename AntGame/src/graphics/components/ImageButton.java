package graphics.components;

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

/**
 * ImageButton: A JPanel that simulates a button. The button has two states, hovered and not-hovered.
 * When hovered the button will display the BufferedImage specified for hovering, likewise when not
 * hovered the button will display the BufferedImage specified for normal use.
 */
public abstract class ImageButton extends JPanel implements MouseListener{
    private BufferedImage hoverImage; // Background image when not being hovered over.
    private BufferedImage backgroundImage; // Background image when being hovered over.
    
    private Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR); // OS 'hand pointer'
    private Cursor POINTER_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR); // OS 'default pointer'
    
	private BufferedImage currentImage;
    
	/**
	 * @param backgroundImage the background of the button when not hovered.
	 * @param hoverImage the background of the button when the mouse is hovering over it.
	 */
    public ImageButton(BufferedImage backgroundImage, BufferedImage hoverImage) {
    	this.addMouseListener(this);
    	this.setLayout(new GridLayout(1,1)); // Just a single component that fills the entire JPanel.
    	
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
    
    /**
     * Happens when the 'button' is clicked.
     */
    public abstract void mouseClicked(java.awt.event.MouseEvent e);

    /**
     * Change the background image to the hover image specified.
     * Change the pointer to the 'hand' pointer.
     */
    public void mouseEntered(java.awt.event.MouseEvent e) {
    	this.currentImage = hoverImage;
    	this.revalidate();
    	this.setCursor(HAND_CURSOR);
    	repaint();
    }

    /**
     * Change back the cursor and background image.
     */
    public void mouseExited(java.awt.event.MouseEvent e) {
    	this.currentImage = backgroundImage;
    	this.revalidate();
    	this.setCursor(POINTER_CURSOR);
    	repaint();
    }

    // Unused
    public void mousePressed(java.awt.event.MouseEvent e) {}
    
    // Unused
    public void mouseReleased(java.awt.event.MouseEvent e) {}

   
    /**
     * Paint the image as background of the JPanel.
     */
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(currentImage, 0, 0, null);
    }

}