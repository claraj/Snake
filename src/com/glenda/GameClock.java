package com.glenda;

import java.util.TimerTask;

public class GameClock extends TimerTask {

	Snake snake; // cobrinha
	Kibble kibble; // comiga
	Score score;
	DrawSnakeGamePanel gamePanel; // chama a classe principal


	//Criando o construtor.
		
	public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel){
		this.snake = snake;
		this.kibble = kibble;
		//this.score = score;
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void run() {
		// This method will be called every clock tick

		//Este será chamado toda vez que o relogio bater.
		// -Nao precisa se preocupar em esperar para que o usuario aperte uma tecla.
		// -
		int stage = SnakeGame.getGameStage();

		switch (stage) {
			case SnakeGame.BEFORE_GAME: {
				//don't do anything, waiting for user to press a key to start
				break;
			}
			case SnakeGame.DURING_GAME: {

				snake.moveSnake(); //esse paramentro faz com que ao iniciar o jogo a cobra esteja em movimento


				if (snake.didEatKibble(kibble) == true) {		
					//tell kibble to update

					//Atualiza a comida apos cobrinha comer.
					kibble.moveKibble(snake);
					Score.increaseScore();
				}
				break;
			}
			case SnakeGame.GAME_OVER: {
				//Para aqui
				this.cancel();
				break;	
			}
			case SnakeGame.GAME_WON: {
				//Para o timer
				this.cancel();
				break;
			}
		}
		//Esse metodo vai atualiza a tela em qualquem circunstancias.
		gamePanel.repaint();		//In every circumstance, must update screen
		
	}
}
