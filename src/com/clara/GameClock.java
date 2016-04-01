package com.clara;

import java.util.TimerTask;

public class GameClock extends TimerTask {

	GameComponentManager componentManager;
	DrawSnakeGamePanel gamePanel;
		
	public GameClock(GameComponentManager components, DrawSnakeGamePanel gamePanel){
		this.componentManager = components;
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void run() {
		// This method will be called every clock tick

		int stage = SnakeGame.getGameStage();

		
		switch (stage) {
			case SnakeGame.BEFORE_GAME: {
				//don't do anything, waiting for user to press a key to start
				break;
			}
			case SnakeGame.DURING_GAME: {
				//Game is running. Ask componentManager to tell components to update.
				componentManager.update();
				break;
			}
			case SnakeGame.GAME_OVER: {
				this.cancel();		//stop the Timer
				break;	
			}
			case SnakeGame.GAME_WON: {
				this.cancel();   //stop Timer
				break;
			}
		}
				
		gamePanel.repaint();		//In every circumstance, must update screen
		
	}
}
