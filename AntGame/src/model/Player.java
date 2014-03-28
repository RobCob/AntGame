package model;

public class Player {
	private String nickname;
	private AntBrain brain;
	private Colour colour;
	
	public Player(String nickname, AntBrain brain){
		this.nickname = nickname;
		this.brain = brain;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public AntBrain getBrain() {
		return brain;
	}
	
	public void setBrain(AntBrain brain) {
		this.brain = brain;
	}
	
	public Colour getColour() {
		return colour;
	}
	
	public void setColour(Colour colour) {
		this.colour = colour;
	}
}
