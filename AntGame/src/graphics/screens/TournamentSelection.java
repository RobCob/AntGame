package graphics.screens;
import graphics.utilities.ImageLoader;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class TournamentSelection extends JPanel{
	
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/gradientBackground.jpg");
	
	public TournamentSelection(){
		JLabel title = new JLabel("Tournament - Brain Selection");
		title.setSize(1024, 76);
		title.setForeground(Color.CYAN);
		this.add(title);
		// CHANGE THE ABOVE WHEN ADDING OUR NEW FONTS
		
		JPanel nameAndUpload = new JPanel();
		nameAndUpload.setLayout(new FlowLayout(FlowLayout.CENTER));
		
	}
	
	
	@Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	 }
	
	
	
	// Leave this in for testing this page by itself
	public static void main(String args[]){
		JFrame jframe = new JFrame();
		jframe.add(new TournamentSelection());
		jframe.setVisible(true);
		jframe.setSize(1024, 576);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
