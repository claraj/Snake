package com.Heather;

/**
 * Created by Clara. Manages game components such as the Snake, Kibble... and their interactions.
 */
public class GameComponentManager {

    private Kibble kibble;
    private static Snake snake;
    private Score score;

    /** Called every clock tick. Tell components to interact/update,
     * manage interactions, update score etc.
     * If there were more components - e.g walls, mazes,
     * different types of kibble/prizes, different scoring systems...
     * they could be managed here too
     */
    public void update() {
        snake.moveSnake();
        if (snake.didEatKibble(kibble)) {
			//tell kibble to update
            kibble.moveKibble(snake);
            Score.increaseScore();
		}
    }

    public static void newGame() {
        snake.reset();
    }

    public void addKibble(Kibble kibble) {
        this.kibble = kibble;
    }

    public void addSnake(Snake snake) {
        this.snake = snake;
    }

    public void addScore(Score score) {
        this.score = score;
    }

    //public void addWalls (Walls walls) {this.walls=walls;}

    //public void addMaze (Maze mazes) {this.maze=maze;}

    public Score getScore() {
        return score;
    }

    public Kibble getKibble() {
        return kibble;
    }

    public Snake getSnake() {
        return snake;
    }

    //public Walls getWalls(){return walls;}

    //public Maze getMazes(){return maze;}

}
