package com.Shawn;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControls implements KeyListener{

	public void keyPressed(KeyEvent ev) {
		//keyPressed events are for catching events like function keys, enter, arrow keys
		//We want to listen for arrow keys to move snake
		//Has to id if user pressed arrow key, and if so, send info to Snake object

		//is game running? No? tell the game to draw grid, start, etc.
		
		//Get the component which generated this event
		//Hopefully, a DrawSnakeGamePanel object.

		DrawSnakeGamePanel panel = (DrawSnakeGamePanel)ev.getComponent();

		if (SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME){
			//Start the game
			SnakeGame.setGameStage(SnakeGame.DURING_GAME);
			SnakeGame.newGame();
			panel.repaint();
            //when the game starts run the first sound here
			return;
		}
		
		if (SnakeGame.getGameStage() == SnakeGame.GAME_OVER){
			Score.resetScore();

			//Need to start the timer and start the game again
			SnakeGame.newGame();
			SnakeGame.setGameStage(SnakeGame.DURING_GAME);
			panel.repaint();
			return;
		}

        if (ev.getKeyChar() == 'w' || ev.getKeyChar() == 'W'){
            SnakeGame.showWalls = !SnakeGame.showWalls;
        }

        if (ev.getKeyChar() == 't' || ev.getKeyChar() == 'T'){
            SnakeGame.showTrees = !SnakeGame.showTrees;
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
		char Q = 'Q'; //added Q incase user uses Q instead of Q or if caps lock+ q is pressed.
		if( keyPressed == q || keyPressed == Q){
			System.exit(0);    //quit if user presses the q key.
		}
	}
}
