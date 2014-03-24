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
    
    private Color hoverColour;
    private Color backgroundColour;
    
    private Border spaceBorder = null;//BorderFactory.createEmptyBorder(10,55,0,0); //Border to space the image
    private Border hoverBorder = null;//BorderFactory.createLineBorder(new Color(0,0,0,170),7);
    private Border normalBorder = null;//BorderFactory.createLineBorder(new Color(0,0,0,130),7);
    
    private Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    private Cursor POINTER_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    
	private BufferedImage currentImage;
    
    public ImageButton(BufferedImage backgroundImage, Color backgroundColour, BufferedImage hoverImage, Color hoverColor, int width, int height) {
    	this.addMouseListener(this);
    	this.setLayout(new GridLayout(1,1));
    	
    	Dimension dim = new Dimension(width, height);
    	this.setPreferredSize(dim);
    	this.setMinimumSize(dim);
    	this.setMaximumSize(dim);
 
    	this.backgroundImage = backgroundImage;
    	this.hoverImage = hoverImage;
    	
    	this.backgroundColour = backgroundColour;
    	this.hoverColour = hoverColor;

    	this.currentImage = backgroundImage;
    	
    	this.setOpaque(false);
    } 

    public abstract void mouseClicked(java.awt.event.MouseEvent e);

    public void mouseEntered(java.awt.event.MouseEvent e) {
    	this.setBorder(BorderFactory.createCompoundBorder(hoverBorder, spaceBorder));
    	this.currentImage = hoverImage;
    	this.setBackground(hoverColour);
    	this.revalidate();
    	this.setCursor(HAND_CURSOR);
    	repaint();
    }

    public void mouseExited(java.awt.event.MouseEvent e) {
    	this.setBorder(normalBorder);
    	this.currentImage = backgroundImage;
    	this.setBackground(backgroundColour);
    	this.revalidate();
    	this.setCursor(POINTER_CURSOR);
    	repaint();
    }

    public void mousePressed(java.awt.event.MouseEvent e) {}

    public void mouseReleased(java.awt.event.MouseEvent e) {}

    public void setBackgroundImage(BufferedImage i) {
    	this.backgroundImage = i;
    }

    public void setHoverImage(BufferedImage i) {
    	this.hoverImage = i;
    }

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(currentImage, 0, 0, null);
    }

}