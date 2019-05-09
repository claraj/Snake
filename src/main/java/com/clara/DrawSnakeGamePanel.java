package com.clara;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

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
    private Wall wall;

    private JTextField playerNameTextField = new JTextField("Enter Name Here");
    private JButton SubmitScoreButton = new JButton("Submit Score");


    DrawSnakeGamePanel(GameComponentManager components) {
        this.snake = components.getSnake();
        this.kibble = components.getKibble();
        this.score = components.getScore();
        this.wall = components.getWall();
    }

    public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Where are we at in the game? 5 phases..
         * 1. Before game starts
         * 1b. The Options menu
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
            case SnakeGame.OPTIONS: {
                displayOptions(g);
                break;
            }
            case SnakeGame.DURING_GAME: {
                displayGame(g);
                break;
            }
            case SnakeGame.GAME_OVER: {
                displayGameOver(g);
                listenTime();
                break;
            }
            case SnakeGame.GAME_WON: {
                displayGameWon(g);
                break;
            }
        }
    }

    // the UI of the options menu
    private void displayOptions(Graphics g) {
        remove(SubmitScoreButton);
        remove(playerNameTextField);
        g.drawString("Press W to enable or disable warpwalls.", 150, 100);
        g.drawString("Press D to adjust the difficulty.", 150, 125);
        g.drawString("Press esc to go back to the main menu.", 150, 150);
        if (SnakeGame.warpWalls) {
            g.drawString("Warpwalls: ON", 150, 200);
        } else {
            g.drawString("Warpwalls: OFF", 150, 200);
        }

        if (SnakeGame.gameDifficulty == SnakeGame.EASY){
            g.drawString("Difficulty: Easy",150, 225);
        } else if (SnakeGame.gameDifficulty == SnakeGame.MID){
            g.drawString("Difficulty: Medium",150, 225);
        } else{
            g.drawString("Difficulty: Hard",150, 225);
        }
    }

    private void displayGameWon(Graphics g) {
        // TODO Replace this with something really special!
        g.clearRect(100, 100, 350, 350);
        g.drawString("YOU WON SNAKE!!!", 150, 150);

    }

    private void displayGameOver(Graphics g) {

        g.clearRect(75, 75, 350, 350);
        g.drawString("GAME OVER", 150, 125);

        String textScore = score.getStringScore();
        String textHighScore = Integer.toString(HighScoreDatabase.getHighScore());
        String highPlayer = HighScoreDatabase.getHighPlayer();
        String newHighScore = score.newHighScore();

        g.drawString("SCORE = " + textScore, 150, 175);

        g.drawString("HIGH SCORE = " + highPlayer + ": " + textHighScore, 150, 200);
        g.drawString(newHighScore, 150, 225);

        g.drawString("press a key to play again", 150, 250);
        g.drawString("Press q to quit the game", 150, 275);

        playerNameTextField.setBounds(115,325,200,25);
        add(playerNameTextField);

        SubmitScoreButton.setBounds(150,350,115,25);
        add(SubmitScoreButton);

    }

    private void displayGame(Graphics g) {
        remove(SubmitScoreButton);
        remove(playerNameTextField);
        displayGameGrid(g);
        displaySnake(g);
        displayKibble(g);
        for(Wall wall: SnakeGame.wallList) {
            displayWall(g, wall);
        }
    }

    private void displayGameGrid(Graphics g) {

        int maxX = SnakeGame.xPixelMaxDimension;
        int maxY = SnakeGame.yPixelMaxDimension;
        int squareSize = SnakeGame.squareSize;

		String FloorTextureURL = "Resources/Floor.png";
		ImageIcon i1 = new ImageIcon(FloorTextureURL);

		g.clearRect(0, 0, maxX, maxY);

        LinkedList<Square> floor = new LinkedList<>();

        for (int x = 0; x <= maxX; x+= squareSize){
			for (int y = 0; y <= maxY; y+= squareSize){
				floor.add(new Square(x,y));
			}
		}
        for (Square s : floor){
//        	g.setColor(Color.LIGHT_GRAY);
			g.drawImage(i1.getImage(), s.x , s.y , squareSize, squareSize, Color.white,null);
		}

        g.setColor(Color.RED);

        //Draw grid - horizontal lines
        for (int y = 0; y <= maxY; y += squareSize) {
            g.drawLine(0, y, maxX, y);
        }

        //Draw grid - vertical lines
        for (int x = 0; x <= maxX; x += squareSize) {
            g.drawLine(x, 0, x, maxY);
        }
    }

    private void displayKibble(Graphics g) {

        //Draw the kibble in green
        g.setColor(Color.GREEN);


        int x = kibble.getKibbleX() * SnakeGame.squareSize;
        int y = kibble.getKibbleY() * SnakeGame.squareSize;

        g.fillRect(x + 1, y + 1, SnakeGame.squareSize - 2, SnakeGame.squareSize - 2);

    }

    private void displayWall(Graphics g, Wall theWall) {
        String WallTextureURL = "Resources/Wall.png";
        ImageIcon i1 = new ImageIcon(WallTextureURL);

        int size = SnakeGame.squareSize;

        int wallX = theWall.getWallX() * size;
        int wallY = theWall.getWallY() * size;

        g.drawImage(i1.getImage(),wallX, wallY, size, size,null);
    }

    private void displaySnake(Graphics g) {
        String SkinTextureURL = "Resources/Skin.png";
        ImageIcon i1 = new ImageIcon(SkinTextureURL);

        int size = SnakeGame.squareSize;

        LinkedList<Square> coordinates = snake.getSnakeSquares();

        //Draw's head texture
        drawSnakeHead(g,coordinates);

        //Draw rest of snake
        for (Square s : coordinates) {
            g.drawImage(i1.getImage(),s.x * size, s.y * size, size, size,null);
        }
    }

    private void displayInstructions(Graphics g) {
        g.drawString("Press any key to begin!", 150, 200);
        g.drawString("Press o for options!", 150, 250);
        g.drawString("Press q of esc to quit the game!", 150, 300);
    }

    // draws snakes head facing whatever direction the current heading is.
    private void drawSnakeHead(Graphics g, LinkedList<Square> coordinates){
        String HeadTextureURL = "Resources/Head.png";
        ImageIcon i2 = new ImageIcon(HeadTextureURL);
        int size = SnakeGame.squareSize;
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform old = g2.getTransform();
        AffineTransform trans = new AffineTransform();

        Square head = coordinates.pop();

        if(snake.getCurrentHeading() == snake.DIRECTION_LEFT){
            trans.rotate( Math.toRadians(0), head.x * size, head.y* size );
            g2.transform( trans );
            g2.drawImage(i2.getImage(),head.x * size, head.y * size, size, size,null);
        } else if(snake.getCurrentHeading() == snake.DIRECTION_UP){
            trans.rotate( Math.toRadians(90), head.x * size, head.y* size );
            g2.transform( trans );
            g2.drawImage(i2.getImage(),head.x * size, (head.y * size) - size, size, size,null);
        } else if(snake.getCurrentHeading() == snake.DIRECTION_RIGHT){
            trans.rotate( Math.toRadians(180), head.x * size, head.y* size );
            g2.transform( trans );
            g2.drawImage(i2.getImage(),(head.x * size) - size, (head.y * size) - size, size, size,null);
        } else {
            trans.rotate( Math.toRadians(270), head.x * size, head.y* size );
            g2.transform( trans );
            g2.drawImage(i2.getImage(),(head.x * size) - size, head.y * size, size, size,null);
        }
        g2.setTransform(old);
	}

	private void listenTime(){
        SubmitScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = playerNameTextField.getText();
                HighScoreDatabase.addScore(name, score.getScore());
            }
        });
    }
}

