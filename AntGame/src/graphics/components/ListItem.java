package graphics.components;
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
	
	public ListItem(String leftText, String centerText, JComponent parent) {
		super();
		leftTextLabel = new JLabel(leftText);
		leftTextLabel.setPreferredSize(new Dimension(135, 76));
		leftTextLabel.setForeground(Color.BLACK);
		leftTextLabel.setFont(new Font("Helvetica", 0, 25));
		leftTextLabel.setAlignmentX(CENTER_ALIGNMENT);
		centerTextLabel = new JLabel (centerText);
		centerTextLabel.setPreferredSize(new Dimension(135, 76));
		centerTextLabel.setForeground(Color.BLACK);
		centerTextLabel.setFont(new Font("Helvetica", 0, 25));
		centerTextLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.parent = parent;
		this.setBackground(new Color(181,181,181));
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
	
	public void setRemoveButton(BufferedImage normal, BufferedImage hover){
		this.button = new ImageButton(normal, hover){
			public void mouseClicked(MouseEvent e) {
				parent.remove(ListItem.this);
			}
		};
	}
	
	public void changeSize(int width, int height){
		Dimension d = new Dimension(width, height);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
	}
	
}
