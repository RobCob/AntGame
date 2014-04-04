package graphics.screens;

/**
 * Screen: implemented by each screen in the Ant-Game to allow polymorphism.
 */
public interface Screen {
	
	/**
	 * Called when the screen is switched into view.
	 */
	public void update();
	
	/**
	 * Resets the screen back to its default values.
	 */
	public void reset();
}
