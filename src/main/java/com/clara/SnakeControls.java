package com.clara;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by clara. Handles key presses that affect the snake.
 */
public class SnakeControls implements KeyListener {

    Snake snake;

    SnakeControls(Snake s){
        this.snake = s;
    }

    @Override
    public void keyPressed(KeyEvent ev) {

        if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
            snake.snakeDown();
        }
        if (ev.getKeyCode() == KeyEvent.VK_UP) {
            snake.snakeUp();
        }
        if (ev.getKeyCode() == KeyEvent.VK_LEFT) {
            snake.snakeLeft();
        }
        if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {
            snake.snakeRight();
        }

    }

    @Override
    public void keyTyped(KeyEvent ev) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
