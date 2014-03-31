package graphics.screens;
import graphics.utilities.ImageLoader;
import model.Player;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class TournamentSelection extends JPanel{
	
	private static final BufferedImage BACKGROUND_IMAGE = ImageLoader.loadImage("/gradientBackground.jpg");
	private Player[] players = new Player[8];
	
	public TournamentSelection(){
		this.setLayout(new FlowLayout());
		
		JLabel title = new JLabel("Tournament - Brain Selection");
		// CHANGE THE ABOVE WHEN ADDING OUR NEW FONTS
		
		JPanel titlepanel = new JPanel();
		titlepanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		titlepanel.setPreferredSize(new Dimension(1024, 76));
		titlepanel.add(title);
		
		JPanel nameAndUpload = new JPanel();
		nameAndUpload.setLayout(new FlowLayout(FlowLayout.CENTER));
		nameAndUpload.setPreferredSize(new Dimension(1024, 76));
		
		JLabel nickname = new JLabel("Nickname:");
		nickname.setPreferredSize(new Dimension(90, 76));
		// CHANGE THE ABOVE WHEN ADDING OUR NEW FONTS
		
		JTextArea nameToAdd = new JTextArea(1, 10);
		nameToAdd.setLineWrap(true);
		JScrollPane name = new JScrollPane(nameToAdd);
		name.setPreferredSize(new Dimension(300, 67));
		JButton upload = new JButton("Upload Ant-Brain");
		
		JTextArea playerList = new JTextArea();
		playerList.setLineWrap(true);
		JScrollPane temp = new JScrollPane(playerList);
		temp.setPreferredSize(new Dimension(1024, 285));
		//THE ABOVE TEMP IS FOR LAYOUT PLANNING PURPOSES
		
		JPanel temp2 = new JPanel();
		temp2.setLayout(new FlowLayout(FlowLayout.CENTER));
		temp2.setPreferredSize(new Dimension(1024, 76));
		
		nameAndUpload.add(nickname);
		nameAndUpload.add(name);
		nameAndUpload.add(upload);
		this.add(titlepanel);
		this.add(nameAndUpload);
		this.add(temp);
		this.add(temp2);
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
