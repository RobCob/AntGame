package graphics.components;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * FixedSpacerPanel: A clear panel that has a fixed size. Used for formatting.
 * 
 * @author 105957
 */
public class FixedSpacerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            The width of the panel.
	 * @param height
	 *            The height of the panel.
	 */
	public FixedSpacerPanel(int width, int height) {
		this.setOpaque(false);
		Dimension d = new Dimension(width, height);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
	}
}
