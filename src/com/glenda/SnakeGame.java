package com.glenda;

import java.awt.*;
import java.util.IntSummaryStatistics;
import java.util.Scanner;
import java.util.Timer;
import java.util.zip.Inflater;

import javax.swing.*;


public class SnakeGame {

	//Quantidade de pixel na janela
	public final static int xPixelMaxDimension = 601;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
	public final static int yPixelMaxDimension = 601;

	//Quantidade de squares na grid
	public static int xSquares ;
	public static int ySquares ;

	//Tamanho dos quadradinhos
	public final static int squareSize = 40;

	protected static Snake snake ;

	//**** NEW LINE - Adicionando imagens - Welcome snake game - Cabeça - Comida
	protected static JLabel pic = new JLabel(new ImageIcon("/Users/glendex/IdeaProjects/SnakeGame/welcome.png")); //Add logo to the main screen

	protected static JLabel food = new JLabel(new ImageIcon("/Users/glendex/Pictures/apple.png")); //Add logo to the main screen

	protected static Kibble kibble;

	protected static Score score;

	static final int BEFORE_GAME = 1;
	static final int DURING_GAME = 2;
	static final int GAME_OVER = 3;
	static final int GAME_WON = 4;
	//The values are not important. The important thing is to use the constants
	//instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	//Using constant names instead makes it easier to keep it straight. Refer to these variables 
	//using statements such as SnakeGame.GAME_OVER 

	private static int gameStage = BEFORE_GAME;
	//use this to figure out what should be happening.
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change its value


	protected static long clockInterval = 500;
	//controls game speed
	//Every time the clock ticks, the snake moves
	//This is the time between clock ticks, in milliseconds
	//1000 milliseconds = 1 second.

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	//Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	//http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	//http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html


	/*
		Aqui comeca a criaçāo da janela
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setVisible(true);
		snakeFrame.setResizable(false);
		snakeFrame.setBackground(Color.darkGray);

		snakePanel = new DrawSnakeGamePanel(snake, kibble, score);
		snakePanel.setFocusable(true);
		snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents
		snakeFrame.add(snakePanel);
		snakePanel.addKeyListener(new GameControls(snake));


		setGameStage(BEFORE_GAME);

		//Adiciona a imagem de apresentacao na janela
		//(imagem que esta sendo declarada)
		snakePanel.add(pic);


		snakeFrame.setVisible(true);
	}

	private static void initializeGame() {

		//set up score, snake and first kibble
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;

		snake = new Snake(xSquares, ySquares, squareSize);
		kibble = new Kibble(snake);
		score = new Score();


		gameStage = BEFORE_GAME;

	}

	protected static void newGame() {
		//Remove a imagem da janela assim que o jogo inicia.
		snakePanel.remove(pic);

		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, kibble, score, snakePanel);
		timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);

	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeGame();
				createAndShowGUI();
			}
		});
	}

	public static int getGameStage() {
		return gameStage;
	}

	public static void setGameStage(int gameStage) {
		SnakeGame.gameStage = gameStage;
	}

}
