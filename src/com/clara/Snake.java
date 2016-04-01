package com.clara;

import java.util.LinkedList;

public class Snake {

	final int[] DIRECTION_UP =    { 0, -1};
	final int[] DIRECTION_DOWN =  { 0,  1};
	final int[] DIRECTION_LEFT =  {-1,  0};
	final int[] DIRECTION_RIGHT = { 1,  0};  //Amount to move x and y of snake head to make new snake head

	private LinkedList<Square> snakeSquares;  //List of snake squares or segments. Square class is simply an int x and y value.

	private int[] currentHeading;  //Direction snake is going in, not direction user is telling snake to go
	private int[] lastHeading;    //Last confirmed movement of snake. See moveSnake method

	private int growthIncrement = 2; //how many squares the snake grows after it eats a kibble

	private int growThisManySquares = 0;  //Snake grows 1 square at a time, one per clock tick. This tracks how many squares are left to add over a number of clock ticks.

	private int maxX, maxY;

	public Snake(int maxX, int maxY){
		this.maxX = maxX;
		this.maxY = maxY;
		createStartSnake();
	}

	protected void createStartSnake(){

		//snake starts as 3 horizontal squares in the center of the screen, moving left.
		//Figure out where center is,
		int screenXCenter = maxX/2;
		int screenYCenter = maxY/2;

		//Create empty list
		snakeSquares = new LinkedList<>();
		snakeSquares.add(new Square(screenXCenter, screenYCenter));
		snakeSquares.add(new Square(screenXCenter+1, screenYCenter));
		snakeSquares.add(new Square(screenXCenter+2, screenYCenter));

		currentHeading = DIRECTION_LEFT;
		lastHeading = DIRECTION_LEFT;
		
		growThisManySquares = 0;
	}


	/* Make a copy of the list of squares and return it. Can't return the actual list in
	case something else modifies it. When an object is returned, it's actually a reference
	to that object, not a copy. And then anything that modifies that returned reference
	also modifies the original. */
	public LinkedList<Square> getSnakeSquares(){

		LinkedList<Square> segmentSquares = new LinkedList<Square>();

		for (Square s : snakeSquares) {
			//make a Square for this segment's coordinates and add to list
			Square sq = new Square(s.x, s.y);
			segmentSquares.add(sq);
		}
		return segmentSquares;

	}

	public void snakeUp(){
		if (currentHeading == DIRECTION_UP || currentHeading == DIRECTION_DOWN) { return; }
		currentHeading = DIRECTION_UP;
	}
	public void snakeDown(){
		if (currentHeading == DIRECTION_DOWN || currentHeading == DIRECTION_UP) { return; }
		currentHeading = DIRECTION_DOWN;
	}
	public void snakeLeft(){
		if (currentHeading == DIRECTION_LEFT || currentHeading == DIRECTION_RIGHT) { return; }
		currentHeading = DIRECTION_LEFT;
	}
	public void snakeRight(){
		if (currentHeading == DIRECTION_RIGHT || currentHeading == DIRECTION_LEFT) { return; }
		currentHeading = DIRECTION_RIGHT;
	}


	protected void moveSnake(){
		//Called every clock tick
		
		//Must check that the direction snake is being sent in is not contrary to current heading
		//So if current heading is down, and snake is being sent up, then should ignore.
		//Without this code, if the snake is heading up, and the user presses left then down quickly, the snake will back into itself.
		if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
			currentHeading = DIRECTION_UP; //keep going the same way
		}
		if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
			currentHeading = DIRECTION_DOWN; //keep going the same way
		}
		if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
			currentHeading = DIRECTION_RIGHT; //keep going the same way
		}
		if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
			currentHeading = DIRECTION_LEFT; //keep going the same way
		}
		
		//Did you hit the wall, snake? 
		//Or eat your tail? Don't move. 

//		q}

		if (wonGame()) {
			SnakeGame.setGameStage(SnakeGame.GAME_WON);
			return;
		}

		//Make new head, and current heading, to move snake

		Square currentHead = snakeSquares.getFirst();
		int headX = currentHead.x;
		int headY = currentHead.y;

		Square newHead = new Square(headX + currentHeading[0], headY + currentHeading[1]);

		//Does this make snake hit the wall?
		if (headX >= maxX || headX < 0 || headY >= maxY || headY < 0 ) {
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		//Does this make the snake eat its tail?
		if (isThisInSnake(newHead)) {
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		//Otherwise, game is still on. Add new head
		snakeSquares.add(0, newHead);

		//If snake did not just eat, then remove tail segment
		//to keep snake the same length.
		if (growThisManySquares == 0) {
			snakeSquares.removeLast();
		}
		else {
			//Snake has just eaten. leave tail as is.  Decrease growThisManySquares variable by 1.
			growThisManySquares-- ;
		}
		
		lastHeading = currentHeading; //Update last confirmed heading

	}


	/* Game controller calls this to notify snake */
	public void youAteKibble() {
		growThisManySquares += growthIncrement;
	}


	public String toString(){
		String textSnake = "";
		int segment = 0;
		for (Square s : snakeSquares) {
			textSnake += String.format("Segment %d [%d, %d]", segment, s.x, s.y);
			segment++;
		}

		return textSnake;
	}

	public boolean wonGame() {

		//If all of the squares have snake segments in, the snake has eaten so much kibble 
		//that it has filled the screen. Win!.

		//Number of squares on screen = x squares * y squares

		if (snakeSquares.size() == (maxX * maxY)) {
			return true;
		}
		return false;
	}


	/* Convenience method for testing if a square is one of the snake squares.
	* This is helpful to decide if a kibble and snake are in the same place i.e. snake
	* has eaten the kibble. Could also be useful to test if the snake has hit a wall/maze. */
	public boolean isThisInSnake(Square testSquare) {

		for (Square s : snakeSquares) {
			if (s.x == testSquare.x && s.y == testSquare.y) {
				return true;
			}
		}
		return false;

	}

}


