package graphics.components;

import java.awt.Dimension;
import javax.swing.JPanel;

public class FixedSpacerPanel extends JPanel{

	public FixedSpacerPanel(int width, int height) {
		this.setOpaque(false);
		Dimension d = new Dimension(width, height);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
	}
}
