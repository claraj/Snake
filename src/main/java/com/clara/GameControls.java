package com.clara;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControls implements KeyListener{


	public void keyPressed(KeyEvent ev) {
		//keyPressed events are for catching events like function keys, enter, arrow keys

		//Is game running? No? tell the game to draw grid, start, etc.
		if (ev.getKeyChar() != 'o' && SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME){
			SnakeGame.newGame();
			return;
		}

		// This is the same at the above if-statement, but it's possible you might want to
		// do something different depending on where you are in the game.
		if (ev.getKeyChar() != 'o' && SnakeGame.getGameStage() == SnakeGame.GAME_OVER){
			SnakeGame.newGame();
			return;
		}
	}


	@Override
	public void keyReleased(KeyEvent ke) {
		// exits options menu. In keyReleased because when it was in keyTyped it would leave options while the esc was still being pressed and then end the game.
		if(ke.getKeyChar() == KeyEvent.VK_ESCAPE && SnakeGame.getGameStage() == SnakeGame.OPTIONS){
			SnakeGame.setGameStage(SnakeGame.BEFORE_GAME);
			return;
		}
	}


	@Override
	public void keyTyped(KeyEvent ev) {
		//keyTyped events are for user typing letters on the keyboard, anything that makes a character display on the screen
		char keyPressed = ev.getKeyChar();
		char q = 'q';
		char o = 'o';
		char w = 'w';
		char d = 'd';

		if( keyPressed == o && (SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME || SnakeGame.getGameStage() == SnakeGame.GAME_OVER)){
			SnakeGame.setGameStage(SnakeGame.OPTIONS);    // opens the options menu.
			SnakeGame.optionsMenuTimer();
		}

		// sets up the functionality of the warp walls toggle.
		if(keyPressed == w && SnakeGame.getGameStage() == SnakeGame.OPTIONS){
			if(!SnakeGame.warpWalls){
				SnakeGame.warpWalls = true;
			} else {
				SnakeGame.warpWalls = false;
			}
		}

		// sets up the difficulty select feature.
		if(keyPressed == d && SnakeGame.getGameStage() == SnakeGame.OPTIONS){
			if (SnakeGame.gameDifficulty == SnakeGame.EASY){
				SnakeGame.gameDifficulty = SnakeGame.MID;
				SnakeGame.addWalls();
			} else if (SnakeGame.gameDifficulty == SnakeGame.MID){
				SnakeGame.gameDifficulty = SnakeGame.HARD;
				SnakeGame.addWalls();
			} else{
				SnakeGame.gameDifficulty = SnakeGame.EASY;
				SnakeGame.addWalls();
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
