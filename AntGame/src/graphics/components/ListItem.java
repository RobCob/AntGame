package graphics.components;

import graphics.screens.TournamentSelectionPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import model.*;

/**
 * ListItem: A panel that represents a list item. It has three sections left, right and center. It has the ability to remove itself from the JComponent that it
 * is contained in.
 */
public class ListItem extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel leftTextLabel;
	private JLabel centerTextLabel;
	private JPanel imageButtonPanel;
	private JComponent parent;
	private Player player;
	private JPanel screen;

	/**
	 * ListItem constructor.
	 * 
	 * @param leftText
	 *            The text to be displayed at the left side of the list.
	 * @param centerText
	 *            The text to be displayed at the right side of the list.
	 * @param parent
	 *            The JComponent that the list item is contained in.
	 */
	public ListItem(String leftText, String centerText, JComponent parent) {
		this.parent = parent;

		this.setLayout(new BorderLayout());

		// Create left label.
		leftTextLabel = new JLabel(leftText);
		leftTextLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		leftTextLabel.setForeground(Color.BLACK);
		leftTextLabel.setFont(new Font("Helvetica", 0, 25));

		// Create center label.
		centerTextLabel = new JLabel(centerText);
		centerTextLabel.setBorder(BorderFactory.createEmptyBorder(0, 35, 0, 0));
		centerTextLabel.setForeground(Color.BLACK);
		centerTextLabel.setFont(new Font("Helvetica", 0, 25));

		this.addLeft(leftTextLabel); // add left components
		this.addCentre(centerTextLabel); // add center components
		this.setBackground(new Color(181, 181, 181)); // set background colour.
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK)); // Set bottom border.
	}

	/**
	 * Adds a JComponent to the right of the list item.
	 * 
	 * @param comp
	 *            JComponent to add to the right of the list item.
	 */
	public void addRight(JComponent comp) {
		this.add(comp, BorderLayout.EAST);
	}

	public void addCentre(JComponent comp) {
		this.add(comp, BorderLayout.CENTER);
	}

	public void addLeft(JComponent comp) {
		this.add(comp, BorderLayout.WEST);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setRemoveButton(BufferedImage normal, BufferedImage hover, JPanel screenPanel) {
		this.screen = screenPanel;
		JPanel buttonPanel = new JPanel();
		ImageButton button = new ImageButton(normal, hover) {
			public void mouseClicked(MouseEvent e) {
				parent.remove(ListItem.this);
				parent.revalidate();
				parent.repaint();

				if (screen instanceof TournamentSelectionPanel) {
					TournamentSelectionPanel ts = (TournamentSelectionPanel) screen;
					ts.removePlayer(player);
				}
			}
		};
		buttonPanel.add(button);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		buttonPanel.setOpaque(false);
		this.imageButtonPanel = buttonPanel;
		addRight(imageButtonPanel);
	}

	public void changeSize(int width, int height) {
		Dimension d = new Dimension(width, height);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
	}

}
