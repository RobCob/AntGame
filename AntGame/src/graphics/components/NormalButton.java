package graphics.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public abstract class NormalButton extends CustomButton {

	public static final int BLUE_THEME = 0;
	public static final int RED_THEME = 1;
	public static final int GREEN_THEME = 2;
	
	private int width;
	private int height;
	private Color backgroundColour;
	private Color hoverColour;
	private Border normalBorder;
	private Border hoverBorder;
	private Font normalFont;
	private Font hoverFont;
	private String text;
	private Color normalFontColour;
	private Color hoverFontColour;
	
	public NormalButton(String text, int theme) {
		this.text = text;
		setTheme(theme);
		updateChoice();
	}
	
	public void setTheme(int theme) {
		// Set the buttons settings based on the theme number.
		switch (theme) {
		case BLUE_THEME:
			this.backgroundColour = Color.BLUE;
			this.hoverColour = Color.CYAN;
			this.normalBorder = BorderFactory.createLineBorder(new Color(0,0,0,170),7);
			this.hoverBorder = BorderFactory.createLineBorder(new Color(0,0,0,130),7);
			this.normalFont =  new Font("Helvetica", Font.BOLD, 16);
			this.hoverFont =  new Font("Helvetica", Font.BOLD, 16);
			this.normalFontColour = new Color(220, 250, 220);
			this.hoverFontColour = Color.WHITE;
			this.width = 200;
			this.height = 40;
			break;
		case RED_THEME:
			this.backgroundColour = Color.RED;
			this.hoverColour = Color.PINK;
			this.normalBorder = BorderFactory.createLineBorder(new Color(0,0,0,170),7);
			this.hoverBorder = BorderFactory.createLineBorder(new Color(0,0,0,130),7);
			this.normalFont =  new Font("Helvetica", Font.BOLD, 16);
			this.hoverFont =  new Font("Helvetica", Font.BOLD, 16);
			this.normalFontColour = new Color(220, 250, 220);
			this.hoverFontColour = Color.WHITE;
			this.width = 200;
			this.height = 40;
			break;
		case GREEN_THEME:
			this.backgroundColour = new Color(42, 88, 40);
			this.hoverColour = new Color(55, 115, 55);
			this.normalBorder = BorderFactory.createLineBorder(new Color(0,0,0,170),7);
			this.hoverBorder = BorderFactory.createLineBorder(new Color(0,0,0,130),7);
			this.normalFont =  new Font("Helvetica", Font.BOLD, 16);
			this.hoverFont =  new Font("Helvetica", Font.BOLD, 16);
			this.normalFontColour = new Color(220, 250, 220);
			this.hoverFontColour = Color.WHITE;
			this.width = 200;
			this.height = 40;
			break;
		default:
			break;
		} 
	}
	private void updateChoice() {
		setBackgroundColour(backgroundColour);
		setHoverColour(hoverColour);
		setNormalBorder(normalBorder);
		setHoverBorder(hoverBorder);
		setNormalFont(normalFont);
		setHoverFont(hoverFont);
		setNormalFontColour(normalFontColour);
		setHoverFontColour(hoverFontColour);
		setWidth(width);
		setHeight(height);
		setButtonText(text);
		update();
	}
	

	public NormalButton(String text, int theme, int width, int height) {
		this.text = text;
		this.width = width;
		this.height = height;
		setTheme(theme);
		updateChoice();
	}


}
