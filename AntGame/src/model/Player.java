package model;

/**
 * A class that represents a Player in a Match or Tournament.
 * The Player stores the following:
 * A Player name as a String.
 * An AntBrain used to crontol Ant's belonging to the Player's team.
 * The Colour of the Player, used to decide it's team in a match.
 * @author 108069
 *
 */
public class Player {
	private String nickname;
	private AntBrain brain;
	private Colour colour;
	
	/**
	 * Creates a Player with the given nickname and AntBrain.
	 * @param nickname The name of the Player.
	 * @param brain The AntBrain used by the Player.
	 */
	public Player(String nickname, AntBrain brain){
		this.nickname = nickname;
		this.brain = brain;
	}
	
	/**
	 * Gets the nickname of the Player.
	 * @return The Player's name.
	 */
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * Sets the nickname of the Player.
	 * @param nickname The Player's new name.
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	/**
	 * Gets the AntBrain of the Player.
	 * @return The Player's AntBrain.
	 */
	public AntBrain getBrain() {
		return brain;
	}
	
	/**
	 * Gets the AntBrain of the Player.
	 * @param brain The Player's new AntBrain.
	 */
	public void setBrain(AntBrain brain) {
		this.brain = brain;
	}
	
	/**
	 * Gets the Colour of the Player.
	 * @return The Player's Colour.
	 */
	public Colour getColour() {
		return colour;
	}
	
	/**
	 * Sets the Colour of the Player.
	 * @param colour The Player's new Colour.
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}
	
	@Override
	protected Object clone() {
		return new Player(nickname, brain);
	}
}
