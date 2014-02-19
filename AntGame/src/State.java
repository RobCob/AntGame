abstract class State{
	public abstract void execute(Ant ant);
}
enum SenseDir{
	HERE,
    AHEAD,
    LEFTAHEAD,
    RIGHTAHEAD
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
    FOEHOME
}

enum TurnDir{
	LEFT,
	RIGHT
}

class Sense extends State{
	SenseDir senseDirection;
	int st1, st2, scent;
	Condition condition;
	public Sense(SenseDir direction, int trueState, int falseState, Condition condition){
		this.senseDirection = direction;
		this.st1 = trueState;
		this.st2 = falseState;
		this.condition = condition;
	}
	public Sense(SenseDir direction, int trueState, int falseState, Condition condition, int scent) {
		this(direction, trueState, falseState, condition);
		this.scent = scent;
	}
	@Override
	public void execute(Ant ant) {
		//TODO: Sense if condition is true, then st1 else st2.
	}
}

class Mark extends State{
	int i, st;
	public Mark(int i, int st){
		this.i = i;
		this.st = st;
	}
	@Override
	public void execute(Ant ant) {
		//TODO: Place scent i on current tile, go to st.		
	}
}

class Unmark extends State{
	int i, st;
	public Unmark(int i, int st){
		this.i = i;
		this.st = st;
	}
	@Override
	public void execute(Ant ant) {
		//TODO: Remove scent i on current tile, go to st.
		
	}
}

class PickUp extends State{
	int st1, st2;
	public PickUp(int st1, int st2){
		this.st1 = st1;
		this.st2 = st2;
	}
	@Override
	public void execute(Ant ant) {
		//TODO: check for food, if food, st1 else st2
	}
}

class Drop extends State{
	int st;
	public Drop(int st){
		this.st = st;
	}
	@Override
	public void execute(Ant ant) {
		//TODO: Place food, change state.
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
	public void execute(Ant ant) {
		//TODO: Change direction.
		
	}
}

class Move extends State{
	int st1, st2;
	public Move(int st1, int st2){
		this.st1 = st1;
		this.st2 = st2;
	}
	@Override
	public void execute(Ant ant) {
		//TODO: Move to st1, failing that, st2
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
	public void execute(Ant ant) {
		//TODO: P random then st1 else st2
	}
}

