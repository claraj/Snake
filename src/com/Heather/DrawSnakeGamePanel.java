package com.Heather;

import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 * 
 * @author Clara
 *
 */



public class DrawSnakeGamePanel extends JPanel {
	
	private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint
	
	private Snake snake;
	private Kibble kibble;
	private Score score;

	private String textScore;
	private String textHighScore;
	private String newHighScore;
	private int dimension=GameOptionsGui.getClearRectangle();

	DrawSnakeGamePanel(GameComponentManager components){
		this.snake = components.getSnake();
		this.kibble = components.getKibble();
		this.score = components.getScore();
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();
        
        switch (gameStage) {
			case SnakeGame.BEFORE_GAME: {
				displayInstructions(g);
				//new GameOptionsGui();
				break;
			}
			case SnakeGame.DURING_GAME: {
				displayGame(g);
				break;
			}
			case SnakeGame.GAME_OVER: {
				displayGameOver(g);
				break;
			}
			case SnakeGame.GAME_WON: {
				displayGameWon(g);
				break;
        	}
        }
    }

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
		g.clearRect(10,10,dimension, dimension);
		g.setColor(Color.BLUE);
		g.drawString("YOU WON SNAKE!!!", ((dimension/3)-8), (dimension/2));
		g.setColor(Color.black);

		textScore = score.getStringScore();
		textHighScore = score.getStringHighScore();
		newHighScore = score.newHighScore();

		g.drawString("SCORE = " + textScore, ((dimension/3)-4), 40);

		g.drawString("HIGH SCORE = " + textHighScore, ((dimension/3)-7), 60);
		g.drawString (newHighScore, ((dimension/3)-10), 80);//TODO test this

		g.drawString("Press a key to play again.", ((dimension/3)-13), ((dimension/2)+80));
		g.drawString("Press q to quit the game.",((dimension/3)-13),((dimension/2)+100));

	}
	private void displayGameOver(Graphics g) {
		g.clearRect(10,10,dimension, dimension);
		g.setColor(Color.magenta);
		g.drawString("GAME OVER", ((dimension/3)-5), (dimension/2));
		g.setColor(Color.black);

		textScore = score.getStringScore();
		textHighScore = score.getStringHighScore();
		newHighScore = score.newHighScore();

		g.drawString("SCORE = " + textScore, ((dimension/3)-4), 40);

		g.drawString("HIGH SCORE = " + textHighScore, ((dimension/3)-7), 60);


		g.drawString("Press a key to play again.", ((dimension/3)-13), ((dimension/2)+80));
		g.drawString("Press q to quit the game.",((dimension/3)-13),((dimension/2)+100));

	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
		displayMazes(g);
	}

	private void displayGameGrid(Graphics g) {

		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;
		
		g.clearRect(0, 0, maxX, maxY);

		g.setColor(Color.BLACK);

		//Draw grid - horizontal lines
		for (int y=0; y <= maxY ; y+= squareSize){			
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x=0; x <= maxX ; x+= squareSize){			
			g.drawLine(x, 0, x, maxY);
		}
	}

	private void displayKibble(Graphics g) {

		//Draw the kibble in green
		g.setColor(Color.GREEN);

		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;
		g.fillOval(x+20, y+1, SnakeGame.squareSize-40, SnakeGame.squareSize-1);
	}

	/*private void displayMazes(Graphics g) {
		g.setColor(Color.BLACK);
		int x=Mazes.getMaze() * SnakeGame.squareSize;

	}*/

	private void displayMazes(Graphics g){//what does this do?
		g.setColor(Color.BLACK);
		if(GameOptionsGui.getMaze()){
			HashMap<Integer, Integer> mazeCoordinates=Mazes.makeMazes(snake);
			for(Integer c:mazeCoordinates.keySet()){
				int x=c;
				int y=mazeCoordinates.get(c);
				g.fillRect(x,y,SnakeGame.squareSize, SnakeGame.squareSize);
			}
		}
	}

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();
		
		//Draw head in Red
		g.setColor(new Color(255, 90, 80));
		Point head = coordinates.pop();
		g.fillOval((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		
		//Draw rest of snake in forest green
		g.setColor((new Color(80,142,68)));
		for (Point p : coordinates) {
			g.fillRoundRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize,15,15);
		}
	}


	private void displayInstructions(Graphics g) {
        g.drawString("Press any key to begin!",50,50);
        g.drawString("Press q to quit the game.",50,70);
    	}

}

