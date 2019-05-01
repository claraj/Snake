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
	public void keyReleased(KeyEvent ke) {
		// exits options menu. In keyReleased because when it was in keyTyped it would leave options while the esc was still being pressed and then end the game.
		if(ke.getKeyChar() == KeyEvent.VK_ESCAPE && SnakeGame.getGameStage() == SnakeGame.OPTIONS){
			SnakeGame.setGameStage(SnakeGame.BEFORE_GAME);
		}
	}


	@Override
	public void keyTyped(KeyEvent ev) {
		//keyTyped events are for user typing letters on the keyboard, anything that makes a character display on the screen
		char keyPressed = ev.getKeyChar();
		char q = 'q';
		char o ='o';
		char w = 'w';

		if( keyPressed == o){
			SnakeGame.setGameStage(SnakeGame.OPTIONS);    // opens the options menu.
		}

		// sets up the functionality of the options menu.
		if(keyPressed == w && SnakeGame.getGameStage() == SnakeGame.OPTIONS){
			if(!SnakeGame.warpWalls){
				SnakeGame.warpWalls = true;
			} else {
				SnakeGame.warpWalls = false;
			}
		}

		if( keyPressed == q){
			System.exit(0);    //quit if user presses the q key.
		}

		//makes the escape key also exit the game because that's what I always try to press.
		if(ev.getKeyChar() == KeyEvent.VK_ESCAPE && SnakeGame.getGameStage() != SnakeGame.OPTIONS){
			System.exit(0);
		}
	}



}
