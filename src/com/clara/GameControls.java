package com.clara;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControls implements KeyListener{
	

	public void keyPressed(KeyEvent ev) {
		//keyPressed events are for catching events like function keys, enter, arrow keys

		//Is game running? No? tell the game to draw grid, start, etc.
		if (SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME){
			SnakeGame.newGame();
			return;
		}

		// This is the same at the above if-statement, but it's possible you might want to
		// do something different depending on where you are in the game.
		if (SnakeGame.getGameStage() == SnakeGame.GAME_OVER){
			SnakeGame.newGame();
			return;
		}

	}


	@Override
	public void keyReleased(KeyEvent ev) {
		//We don't care about keyReleased events, but are required to implement this method anyway.		
	}


	@Override
	public void keyTyped(KeyEvent ev) {
		//keyTyped events are for user typing letters on the keyboard, anything that makes a character display on the screen
		char keyPressed = ev.getKeyChar();
		char q = 'q';
		if( keyPressed == q){
			System.exit(0);    //quit if user presses the q key.
		}
	}

}
