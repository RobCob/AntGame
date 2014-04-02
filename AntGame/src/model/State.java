package model;

public abstract class State{
	public static int seed = 0;
	private static int count = 0;
	public abstract void execute(Ant ant, World w);
	
	public static int randomInt(int n){
		for(; count < 3; count++){
			seed = seed * 22695477 + 1;
		}
		seed = seed * 22695477 + 1;
		count++;
		int value = (((seed/65536) % 16384) + 16384) % 16384;
		return ((value % n) + n) % n;
	}
	public static void main(String[] args) {
		seed = 8008135;
		for(int i = 0; i < 100; i++){
			System.out.println(randomInt(2));
		}
	}
}

enum SenseDir{
	HERE(0),
    AHEAD(0),
    LEFTAHEAD(-1),
    RIGHTAHEAD(1);
	
	private int value;
	
	private SenseDir(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}

enum Condition{
	FRIEND,
    FOE,
    FRIENDWITHFOOD,
    FOEWITHFOOD,
    FOOD,
    ROCK,
    MARKER,
    FOEMARKER,
    HOME,
    FOEHOME;
	
	public boolean isTrue(Tile target, Ant ant, int scent){
		boolean success;
		switch(this){
			case FOE:
				success = (!target.isRocky()) && ((ClearTile)target).hasAnt() && !((ClearTile)target).getAnt().getColour().equals(ant.getColour());
				break;
			case FOEHOME:
				success = (!target.isRocky()) && ((ClearTile)target).isAnthill() && !((AntHillTile)target).getColour().equals(ant.getColour());
				break;
			case FOEMARKER:
				success = (!target.isRocky()) && ((ClearTile)target).getMarker(ant.getColour().getEnemy(), scent);
				break;
			case FOEWITHFOOD:
				success = (!target.isRocky()) && ((ClearTile)target).hasAnt() && !((ClearTile)target).getAnt().getColour().equals(ant.getColour()) && ((ClearTile)target).getAnt().hasFood();
				break;
			case FOOD:
				success = (!target.isRocky()) && ((ClearTile)target).getFood() > 0;
				break;
			case FRIEND:
				success = (!target.isRocky()) && ((ClearTile)target).hasAnt() && ((ClearTile)target).getAnt().getColour().equals(ant.getColour());
				break;
			case FRIENDWITHFOOD:
				success = (!target.isRocky()) && ((ClearTile)target).hasAnt() && ((ClearTile)target).getAnt().getColour().equals(ant.getColour()) && ((ClearTile)target).getAnt().hasFood();
				break;
			case HOME:
				success = (!target.isRocky()) && ((ClearTile)target).isAnthill() && ((AntHillTile)target).getColour().equals(ant.getColour());
				break;
			case MARKER:
				success = (!target.isRocky()) && ((ClearTile)target).getMarker(ant.getColour(), scent);
				break;
			case ROCK:
				success = (target.isRocky());
				break;
			default:
				success = false;
				break;
		}
		return success;
	}
}

enum TurnDir{
	LEFT(-1),
	RIGHT(1);
	
	private int value;
	
	TurnDir(int i){
		this.value = i;
	}
	
	public int getValue(){
		return value;
	}
}

class Sense extends State{
	SenseDir senseDirection;
	int st1, st2, scent;
	Condition condition;
	public Sense(SenseDir direction, int trueState, int falseState, Condition condition){
		this(direction, trueState, falseState, condition, 0);
	}
	public Sense(SenseDir direction, int trueState, int falseState, Condition condition, int scent) {
		this.senseDirection = direction;
		this.st1 = trueState;
		this.st2 = falseState;
		this.condition = condition;
		this.scent = scent;
	}
	@Override
	public void execute(Ant ant, World world) {
		Tile target;
		boolean success = false;
		switch(senseDirection){
		case AHEAD:
		case LEFTAHEAD:
		case RIGHTAHEAD:
			int direction = (((ant.getDirection() + senseDirection.getValue()) % 6) + 6) % 6;
			int tileNumber = World.getAhead(direction, (ant.getY()*world.sizeX) + ant.getX(), world.sizeX);
			int x = ((tileNumber % world.sizeX) + world.sizeX) % world.sizeX;
			int y = tileNumber / world.sizeX;
			target = world.getTile(x, y);
			break;
		case HERE:
			target = world.getTile(ant.getX(), ant.getY());
			break;
		default:
			target = null;
			break;
		}
		success = condition.isTrue(target, ant, scent);
		if(success){
			ant.setState(st1);
		}else{
			ant.setState(st2);
		}
	}
}

class Mark extends State{
	int i, st;
	public Mark(int i, int st){
		this.i = i;
		this.st = st;
	}
	@Override
	public void execute(Ant ant, World world) {
		((ClearTile)(world.getTile(ant.getX(), ant.getY()))).placeMarker(ant.getColour(), i);
		ant.setState(st);
	}
}

class Unmark extends State{
	int i, st;
	public Unmark(int i, int st){
		this.i = i;
		this.st = st;
	}
	@Override
	public void execute(Ant ant, World world) {
		((ClearTile)(world.getTile(ant.getX(), ant.getY()))).removeMarker(ant.getColour(), i);
		world.setChange(ant.getY()*world.sizeX + ant.getX());
		ant.setState(st);
	}
}

class PickUp extends State{
	int st1, st2;
	public PickUp(int st1, int st2){
		this.st1 = st1;
		this.st2 = st2;
	}
	@Override
	public void execute(Ant ant, World world) {
		ClearTile tile = ((ClearTile)(world.getTile(ant.getX(), ant.getY())));
		ant.setFood(tile.takeFood());
		if(ant.hasFood()){
			ant.setState(st1);
			world.setChange(ant.getY()*world.sizeX + ant.getX());
		}else{
			ant.setState(st2);
		}
	}
}

class Drop extends State{
	int st;
	public Drop(int st){
		this.st = st;
	}
	@Override
	public void execute(Ant ant, World world) {
		if(ant.hasFood()){
			((ClearTile)(world.getTile(ant.getX(), ant.getY()))).addFood();
			world.setChange(ant.getY()*world.sizeX + ant.getX());
			ant.setState(st);
		}
	}
}

class Turn extends State{
	TurnDir direction;
	int st;
	public Turn(TurnDir dir, int st){
		this.direction = dir;
		this.st = st;
	}
	@Override
	public void execute(Ant ant, World world) {
		ant.setDirection(ant.getDirection() + direction.getValue());
		world.setChange(ant.getY()*world.sizeX + ant.getX());
		ant.setState(st);
	}
}

class Move extends State{
	int st1, st2;
	public Move(int st1, int st2){
		this.st1 = st1;
		this.st2 = st2;
	}
	@Override
	public void execute(Ant ant, World world) {
		boolean success = false;
		int tileNumber = World.getAhead(ant.getDirection(), (ant.getY()*world.sizeX) + ant.getX(), world.sizeX);
		int x = ((tileNumber % world.sizeX) + world.sizeX) % world.sizeX;
		int y = tileNumber / world.sizeX;
		Tile target = world.getTile(x, y);
		success = !target.isRocky();
		if(success && !((ClearTile)target).hasAnt()){
			world.setChange(ant.getY()*world.sizeX + ant.getX());
			((ClearTile)(world.getTile(ant.getX(), ant.getY()))).removeAnt();
			((ClearTile)target).setAnt(ant);
			ant.setX(x);
			ant.setY(y);
			ant.setResting(14);
			ant.setState(st1);
			world.setChange(ant.getY()*world.sizeX + ant.getX());
			world.triggerUpdates(tileNumber);
		}else{
			ant.setState(st2);
		}
	}
}

class Flip extends State{
	int p, st1, st2;
	public Flip(int p, int st1, int st2){
		this.p = p;
		this.st1 = st1;
		this.st2 = st2;
	}
	@Override
	public void execute(Ant ant, World world) {
		if(State.randomInt(p) == 0){
			ant.setState(st1);
		}else{
			ant.setState(st2);
		}
	}
}

