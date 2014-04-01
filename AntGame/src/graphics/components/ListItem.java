package graphics.components;
import graphics.screens.TournamentSelection;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import model.*;

public class ListItem extends JPanel{
	private JLabel leftTextLabel;
	private JLabel centerTextLabel;
	private ImageButton button;
	private JComponent parent;
	private Player player;
	private JPanel screen;
	
	public ListItem(String leftText, String centerText, JComponent parent) {
		this.parent = parent;
		
		this.setLayout(new BorderLayout());
		leftTextLabel = new JLabel(leftText);
		leftTextLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		leftTextLabel.setForeground(Color.BLACK);
		leftTextLabel.setFont(new Font("Helvetica", 0, 25));
		
		centerTextLabel = new JLabel("|     " + centerText);
		centerTextLabel.setBorder(BorderFactory.createEmptyBorder(0, 35, 0, 0));
		centerTextLabel.setForeground(Color.BLACK);
		centerTextLabel.setFont(new Font("Helvetica", 0, 25));
		
		this.addLeft(leftTextLabel);
		this.addCentre(centerTextLabel);
		this.setBackground(new Color(181,181,181));
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
	}
	
	public void addRight(JComponent comp) {
		this.add(comp, BorderLayout.EAST);
	}

	public void addCentre(JComponent comp) {
		this.add(comp, BorderLayout.CENTER);
	}

	public void addLeft(JComponent comp) {
		this.add(comp, BorderLayout.WEST);
	}

	public void setPlayer(Player player){
		this.player = player;
	}
	
	public void setRemoveButton(BufferedImage normal, BufferedImage hover, JPanel screenPanel){
		this.screen = screenPanel;
		ImageButton button = new ImageButton(normal, hover){
			public void mouseClicked(MouseEvent e) {
				parent.remove(ListItem.this);
				parent.revalidate();
				parent.repaint();
				
				if (screen instanceof TournamentSelection) {
					TournamentSelection ts = (TournamentSelection)screen;
					ts.removePlayer(player);
				}
			}
		};
		this.button = button;
		addRight(button);
	}
	
	public void changeSize(int width, int height){
		Dimension d = new Dimension(width, height);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
	}
	
}
