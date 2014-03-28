package model;
public enum Colour {
	RED,
	BLACK;
	
	public Colour getEnemy(){
		if(this == RED){
			return BLACK;
		}else{
			return RED;
		}
	}
}
