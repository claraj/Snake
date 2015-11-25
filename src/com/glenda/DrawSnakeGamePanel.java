package com.glenda;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

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

	//private Image head_snake;
	public BufferedImage head_snake;


//You are never calling this method, so the file is never read, and the head_snake is not initalized.
	public void MyImage (String path) {     //Can you think of a better name for this method? :)
		try {
			head_snake = ImageIO.read(new File("/src/head.png"));  //Make sure this image exists, it's not in your repository
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}




	// Aqui e montado o contructor
	DrawSnakeGamePanel(Snake s, Kibble k, Score sc) {
		this.snake = s;
		this.kibble = k;
		this.score = sc;
		//Load head_snake image here
		MyImage("this argument doesn't do anything");  //do you need the argument? 
		
	}


	// Define o tamnho

	public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

	//Desenha os componetes
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
        case SnakeGame.BEFORE_GAME: { //Referente a BEFORE_GAME - ao inves de case 1 troquei chamamos o parametro oreferente.
        	displayInstructions(g);
        	break;
        } 
        case SnakeGame.DURING_GAME: { //Referente a URING_GAME -  ao inves de case 2 troquei chamamos o parametro oreferente.
        	displayGame(g);
        	break;
        }
        case SnakeGame.GAME_OVER: { //Referente a GAME_OVER - ao inves de case 3 troquei chamamos o parametro oreferente.
        	displayGameOver(g);
        	break;
        }
        case SnakeGame.GAME_WON: { //Referent a GAME_WON - ao inves de case 4 troquei chamamos o parametro oreferente.
        	displayGameWon(g);
        	break;
        }
        }

    }

	//Chama este metodo caso o jogador ganhe o jogo
	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!

		g.clearRect(100,100,350,350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);
		
	}

	//Chama este metodo quando o jogador perder
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
	}


	//Faz o desenho dos gradradinhos
	private void displayGameGrid(Graphics g) {

		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;
		
		g.clearRect(0, 0, maxX, maxY);

		//g.setColor(Color.RED);

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
		//g.drawImage()

		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;

		g.fillRect(x+1, y+1, SnakeGame.squareSize-3, SnakeGame.squareSize-3);
		
	}
	// Creating snake body head and body
	//Para altererar cares da cabeca e do corpo basta apagar a cor atual
	//E o ponto depois de color, digita o ponto novament e aparecera a lista de cores disponiveis.
	public void displaySnake(Graphics g) {
		//Desenhando a cobra
		LinkedList<Point> coordinates = snake.segmentsToDraw();

		//LoadImageHead();
		Graphics2D g1 = (Graphics2D) g;
		//Desenhando a cabeca em vermelho
		//g.setColor(Color.red);
		Point head = coordinates.pop();
		//head_snake = Toolkit.getDefaultToolkit().getImage("/src/head.png"); //Add logo to the main screen

		g.drawImage(head_snake, (int)head.getX(), (int)head.getY(), this);  //This should work now
		//g1.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		g.finalize();


		//Desenha o corpo da cobrinha na cor cinza.
		//Draw rest of snake in darkGray
		g.setColor(Color.darkGray);


		for (Point p : coordinates) {
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);

		}

	}

	/*private void LoadImageHead() {
		head_snake = new ImageIcon("/src/head.png").getImage();
	}*/

	private void snakeEats (Graphics g){ }

	private void displayUnpauseInstructions(Graphics g) {
		// Display instructions for restarting paused game
		int textX = (SnakeGame.xPixelMaxDimension / 2) - 40;
		int textY = (SnakeGame.yPixelMaxDimension / 2) - 15;

		g.drawString("GAME PAUSED", textX, textY);
		g.drawString("Press R to resume", textX - 10, textY + 25);
	}

	//Aparece por detras da imagem. Verificar o que e preciso fazer para mudar a localidade
	private void displayInstructions(Graphics g) {

		g.drawString("WELCOME TO THE SNAKE GAME!",150,470);
		g.drawString("Press any key to begin!",150,500);
		g.drawString("Press q to quit the game",150,550);
		g.setColor(Color.ORANGE);

	}
	
    
}
