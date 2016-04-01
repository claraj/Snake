package com.clara;

import java.util.Random;

/* In this game, Snakes eat Kibble. Feel free to rename to SnakeFood or Prize or Treats or Cake or whatever. */


public class Kibble {

	/** Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments in it. 
	 * There is only one Kibble that knows where it is on the screen. When the snake eats the kibble, it doesn't disapear and
	 * get recreated, instead it moves, and then will be drawn in the new location. 
	 */
	
	private int kibbleX; //This is the square number (not pixel)
	private int kibbleY;  //This is the square number (not pixel)
	
	public Kibble(){

		moveKibble();
	}
	
	protected Square moveKibble(){
		
		Random rng = new Random();
		kibbleX = rng.nextInt(SnakeGame.xSquares);
		kibbleY = rng.nextInt(SnakeGame.ySquares);
		return new Square(kibbleX, kibbleY);
		
	}

	public int getKibbleX() {
		return kibbleX;
	}

	public int getKibbleY() {
		return kibbleY;
	}


	public Square getSquare() {

		return new Square(kibbleX, kibbleY);
	}
}
