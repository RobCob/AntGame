package model;

/**
 * A class to represent a single state of an ant brain.
 * @author 108069
 * 
 */
public abstract class State{
	
	/**
	 * A method used to execute any state.
	 * @param ant The ant which is executing the state.
	 * @param world The world in which the state is executed.
	 */
	public abstract void execute(Ant ant, World world);
}

/**
 * A class to represent the direction in which an ant is sensing.
 * @author 108069
 *
 */
enum SenseDir{
	HERE(0),
    AHEAD(0),
    LEFTAHEAD(-1),
    RIGHTAHEAD(1);
	
	private int value;
	
	/**
	 * A basic constructor to initialize enum values.
	 * @param value
	 */
	private SenseDir(int value){
		this.value = value;
	}
	
	/**
	 * Returns a value used to modify the direction in which the ant will sense.
	 * @return -1, 0, or 1.
	 */
	public int getValue(){
		return value;
	}
}

/**
 * A class to represent the condition for which we are sensing.
 * @author 108069
 *
 */
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
	
	/**
	 * Tests the truth of the given condition
	 * @param target The tile which we are testing.
	 * @param ant The ant which is performing the testing.
	 * @param scent The scent to sense for (only used if sensing a marker).
	 * @return if the condition is true.
	 */
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

/**
 * A class to represent the direction in which an ant is turning.
 * @author 108069
 *
 */
enum TurnDir{
	LEFT(-1),
	RIGHT(1);
	
	private int value;
	/**
	 * A basic constructor used to initialize enum values.
	 * @param value
	 */
	TurnDir(int value){
		this.value = value;
	}
	
	/**
	 * Returns a value used to determine the direction in which the ant will turn.
	 * @return -1, or 1.
	 */
	public int getValue(){
		return value;
	}
}

/**
 * A state used by an ant to sense the world around it.
 * @author 108069
 *
 */
class Sense extends State{
	SenseDir senseDirection;
	int st1, st2, scent;
	Condition condition;
	
	/**
	 * A simple constructer for non-marker senses.
	 * @param direction The direction in which to sense.
	 * @param trueState The state to go to if the condition is true.
	 * @param falseState The state to go to if the condition is false.
	 * @param condition The condition to check for.
	 */
	public Sense(SenseDir direction, int trueState, int falseState, Condition condition){
		this(direction, trueState, falseState, condition, 0);
	}
	
	/**
	 * A simple constructer for marker specific senses.
	 * @param direction The direction in which to sense.
	 * @param trueState The state to go to if the condition is true.
	 * @param falseState The state to go to if the condition is false.
	 * @param condition The condition to check for.
	 * @param scent The scent to sense for.
	 */
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

/**
 * A state used to place a marker in a world tile.
 * @author 108069
 *
 */
class Mark extends State{
	int scent, state;
	
	/**
	 * A simple constructor.
	 * @param scent Which marker to place.
	 * @param state Which state to go to afterwards.
	 */
	public Mark(int scent, int state){
		this.scent = scent;
		this.state = state;
	}
	
	@Override
	public void execute(Ant ant, World world) {
		((ClearTile)(world.getTile(ant.getX(), ant.getY()))).placeMarker(ant.getColour(), scent);
		ant.setState(state);
	}
}

/**
 * A state used to remove a marker from a world tile.
 * @author 108069
 *
 */
class Unmark extends State{
	int scent, state;
	
	/**
	 * A simple constructor.
	 * @param scent Which marker to remove.
	 * @param state Which state to go to afterwards.
	 */
	public Unmark(int scent, int state){
		this.scent = scent;
		this.state = state;
	}
	@Override
	public void execute(Ant ant, World world) {
		((ClearTile)(world.getTile(ant.getX(), ant.getY()))).removeMarker(ant.getColour(), scent);
		world.setChange(ant.getY()*world.sizeX + ant.getX());
		ant.setState(state);
	}
}

/**
 * A state used to pick up food from a world tile.
 * @author 108069
 *
 */
class PickUp extends State{
	int state1, state2;
	
	/**
	 * A simple constructer.
	 * @param state1 The state to go to if food is picked up.
	 * @param state2 The state to go to if food is not picked up.
	 */
	public PickUp(int state1, int state2){
		this.state1 = state1;
		this.state2 = state2;
	}
	
	@Override
	public void execute(Ant ant, World world) {
		ClearTile tile = ((ClearTile)(world.getTile(ant.getX(), ant.getY())));
		ant.setFood(tile.takeFood());
		if(ant.hasFood()){
			ant.setState(state1);
			world.setChange(ant.getY()*world.sizeX + ant.getX());
		}else{
			ant.setState(state2);
		}
	}
}

/**
 * A state used to drop food on a world tile.
 * @author 108069
 *
 */
class Drop extends State{
	int state;
	
	/**
	 * A simple constructor.
	 * @param state The state to go to after this executes.
	 */
	public Drop(int state){
		this.state = state;
	}
	
	@Override
	public void execute(Ant ant, World world) {
		if(ant.hasFood()){
			((ClearTile)(world.getTile(ant.getX(), ant.getY()))).addFood();
			world.setChange(ant.getY()*world.sizeX + ant.getX());
			ant.setState(state);
		}
	}
}

/**
 * A state used to turn an ant left or right.
 * @author 108069
 *
 */
class Turn extends State{
	TurnDir direction;
	int state;
	
	/**
	 * 
	 * @param direction The direction in which to turn
	 * @param state The state to go to after turning
	 */
	public Turn(TurnDir direction, int state){
		this.direction = direction;
		this.state = state;
	}
	
	@Override
	public void execute(Ant ant, World world) {
		ant.setDirection(ant.getDirection() + direction.getValue());
		world.setChange(ant.getY()*world.sizeX + ant.getX());
		ant.setState(state);
	}
}

/**
 * A state used to move an ant from one tile to another.
 * @author 108069
 *
 */
class Move extends State{
	int state1, state2;
	
	/**
	 * A simple constructor.
	 * @param state1 The state to go to if movement is completed.
	 * @param state2 The state to go to if the ant failed to move.
	 */
	public Move(int state1, int state2){
		this.state1 = state1;
		this.state2 = state2;
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
			ant.setState(state1);
			world.setChange(ant.getY()*world.sizeX + ant.getX());
			world.triggerUpdates(tileNumber);
		}else{
			ant.setState(state2);
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
		if(Game.randomInt(p) == 0){
			ant.setState(st1);
		}else{
			ant.setState(st2);
		}
	}
}

