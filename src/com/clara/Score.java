package com.clara;

/** Keeps track of, and display the user's score
 * 
 */


public class Score {

	protected static int score;
	protected static int highScore = 0;
	protected static int increment;
	
	public Score(){
		score = 0;
		increment = 1;  //how many points for eating a kibble
		//Possible TODO get more points for eating kibbles, the longer the snake gets?
		// TODO other ways to increase score?d
	}
	
	public static void resetScore() {
		score = 0;
	}


	public static void increaseScore() {
		
		score = score + increment;
		
	}

	//These methods are useful for displaying score at the end of the game
	
	public String getStringScore() {
		return Integer.toString(score);
	}

	public String newHighScore() {
		
		if (score > highScore) {
			highScore = score;
			return "New High Score!!";
		} else {
			return "";
	}
	}

	public String getStringHighScore() {
		return Integer.toString(highScore);
	}
	
}

