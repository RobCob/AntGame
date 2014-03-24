package graphics.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class CustomButton extends JPanel implements MouseListener{
	private String buttonText = "";
	private JLabel normalLabel;
	private JLabel hoverLabel;
	
	private Font normalFont =  new Font("Verdana", Font.BOLD, 12);
	private Font hoverFont =  new Font("Verdana", Font.BOLD, 12);

    private Color hoverColour;
    private Color backgroundColour;
    
    //private Border spaceBorder = BorderFactory.createEmptyBorder(10,55,0,0); //Border to space the image
    private Border hoverBorder = null;//BorderFactory.createLineBorder(new Color(0,0,0,170),7);
    private Border normalBorder = null;//BorderFactory.createLineBorder(new Color(0,0,0,130),7);
    
    private Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    private Cursor POINTER_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    
    public CustomButton(String text, Color backgroundColour, Color hoverColor, Border normalBorder, Border hoverBorder, int width, int height) {
    	this.addMouseListener(this);
    	this.setLayout(new GridLayout(1,1));
    	
    	Dimension dim = new Dimension(width, height);
    	this.setPreferredSize(dim);
    	this.setMinimumSize(dim);
    	this.setMaximumSize(dim);
 
    	this.normalBorder = normalBorder;
    	this.hoverBorder = hoverBorder;
    	
    	this.buttonText = text;
    	this.normalLabel = new JLabel(text);
    	normalLabel.setFont(normalFont);
    	normalLabel.setHorizontalAlignment(JLabel.CENTER);
    	this.setAlignmentY(JLabel.CENTER);
    	
    	this.hoverLabel = new JLabel(text);
    	hoverLabel.setFont(hoverFont);
    	hoverLabel.setHorizontalAlignment(JLabel.CENTER);
    	
    	this.backgroundColour = backgroundColour;
    	this.hoverColour = hoverColor;
    	
    	this.setBackground(backgroundColour);
    	this.setBorder(normalBorder);
    	this.setLayout(new GridLayout(1,1));
    	this.add(normalLabel);
    }

    public abstract void mouseClicked(java.awt.event.MouseEvent e);

    public void mouseEntered(java.awt.event.MouseEvent e) {
    	this.setBorder(hoverBorder);
    	this.setBackground(hoverColour);
    	this.removeAll();
    	this.add(hoverLabel);
    	this.revalidate();
    	this.setCursor(HAND_CURSOR);
    	repaint();
    }

    public void mouseExited(java.awt.event.MouseEvent e) {
    	this.setBorder(normalBorder);
    	this.setBackground(backgroundColour);
    	this.removeAll();
    	this.add(normalLabel);
    	this.revalidate();
    	this.setCursor(POINTER_CURSOR);
    	repaint();
    }

    public void setNormalFont(Font f) {
    	normalFont = f;
    	normalLabel.setFont(f);
    }
    
    public void setHoverFont(Font f) {
    	hoverFont = f;
    	hoverLabel.setFont(f);
    }
    
    public void mousePressed(java.awt.event.MouseEvent e) {}

    public void mouseReleased(java.awt.event.MouseEvent e) {}
}