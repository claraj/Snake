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
				this.cancel();
				//stops the timer in case you went to the options menu and back, thus starting the options menu timer.
				break;
			}
			case SnakeGame.OPTIONS:{
				break;
			}
			case SnakeGame.DURING_GAME: {
					// stops any timer from previous games that somehow wasn't stopped when the game restarted.
				if(componentManager.lingeringTimer){
					this.cancel();
					componentManager.update();
					componentManager.lingeringTimer = false;
					break;
				} else {
					//Game is running. Ask componentManager to tell components to update.
					componentManager.update();
					break;
				}
			}
			case SnakeGame.GAME_OVER: {
				this.cancel();		// stop the Timer and records that there is no longer a lingering timer.
				componentManager.lingeringTimer = false;
				break;	
			}
			case SnakeGame.GAME_WON: {
				this.cancel();   // stop the Timer and records that there is no longer a lingering timer.
				componentManager.lingeringTimer = false;
				break;
			}

		}
				
		gamePanel.repaint();		//In every circumstance, must update screen
		
	}
}
