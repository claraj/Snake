package com.Shawn;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
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
    private Kibble badKibble; //this will be used for the bad food
	private Score score;
	
	DrawSnakeGamePanel(GameComponentManager components){
		this.snake = components.getSnake();
		this.kibble = components.getKibble();
        this.badKibble = components.getBadKibble();
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
		g.clearRect(100,100,350,350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);
		
	}
	private void displayGameOver(Graphics g) {

		g.clearRect(100,100,350,350);
		g.drawString("GAME OVER", 150, 150);
		
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();
		
		g.drawString("SCORE = " + textScore, 150, 250);
		
		g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
		g.drawString(newHighScore, 150, 400);
		
		g.drawString("press a key to play again", 150, 350);
		g.drawString("Press q to quit the game",150,400);		
    			
	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
        displayBadKibble(g);
		if (SnakeGame.showTrees == true){
            displayWalls(g);
        }
	}

	private void displayGameGrid(Graphics g) {

		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;
		
		//g.clearRect(0, 0, maxX, maxY);
		try {
			BufferedImage img = ImageIO.read(new File("Grass Tile2.jpg")); //apply a base image for the snake field
			g.drawImage(img, 0, 0, maxX, maxY, Color.GREEN,this); //draw the image in.
		}catch (IOException e){
			System.out.println("Image not found!"); //if the image is not found throw this message.  This is not likely.
		}

		g.setColor(Color.DARK_GRAY);

		//Draw grid - horizontal lines
		for (int y=0; y <= maxY ; y+= squareSize){			
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x=0; x <= maxX ; x+= squareSize){			
			g.drawLine(x, 0, x, maxY);
		}

        if (SnakeGame.showWalls == true){
            g.setColor(Color.red);
            g.drawRect(1, 1, maxX-3, maxY-3);
            g.drawRect(2, 2, maxX-5, maxY-5);
            g.drawRect(3, 3, maxX-7, maxY-7);
        }
	}

	private void displayKibble(Graphics g) {

		//Draw the kibble in green
		g.setColor(Color.GREEN);

		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;

		g.fillRect(x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2);
		try {
			BufferedImage img = ImageIO.read(new File("Snake Food4.jpg"));
			g.drawImage(img, x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2, Color.GREEN, this);
		} catch (IOException e){
			System.out.println("Snake food image not found!"); //throw exception if image is missing/not found.
		}
	}

    private void displayBadKibble(Graphics g){
        //place a bad kibble in the green.
        g.setColor(Color.green);

        int x = badKibble.getKibbleX() * SnakeGame.squareSize;
        int y = badKibble.getKibbleY() * SnakeGame.squareSize;

        g.fillRect(x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2);
        try {
            BufferedImage img = ImageIO.read(new File("Snake Food1.jpg"));
            g.drawImage(img, x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2, Color.GREEN, this);
        } catch (IOException e){
            System.out.println("Snake food image not found!"); //throw exception if image is missing/not found.
        }
    }

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();
		
		//Draw head in grey
		g.setColor(Color.LIGHT_GRAY);
		Point head = coordinates.pop();
		g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		
		//Draw rest of snake in black
		g.setColor(Color.BLACK);
		for (Point p : coordinates) {
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}
	}

	private  void  displayWalls(Graphics g){
		LinkedList<Point> coordinates = snake.wallsToDraw();

		g.setColor(Color.black);
		for (Point p : coordinates){
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
			//add image for the kibble

			//add in the image for the walls.
			try {
				BufferedImage img = ImageIO.read(new File("Wall of Trees.png"));
				g.drawImage(img,(int)p.getX(), (int)p.getY(), SnakeGame.squareSize-2, SnakeGame.squareSize-2, Color.GREEN, this);
			} catch (IOException e){
				System.out.println("Snake food image not found!"); //throw exception if image is missing/not found.
			}
		}
	}

	private void displayInstructions(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.BOLD, 36));
        g.drawString("Press any key to begin!",100,200);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 36));
        g.drawString("Press q to quit the game",100,260);

        g.drawString("Press w to toggle warp walls", 100, 400); //adjust walls to be warp walls
        g.drawString("Press s to toggle corners", 100, 440); //add feature to let the corners be added.
    	}
}

