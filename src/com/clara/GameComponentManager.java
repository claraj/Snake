package com.clara;

/**
 * Created by Clara. Manages game components such as the Snake, Kibble... and their interactions.
 */
public class GameComponentManager {

    private Kibble kibble;
    private Snake snake;
    private Score score;

    /** Called every clock tick. Tell components to interact/update,
     * manage interactions, update score etc.
     * If there were more components - e.g walls, mazes,
     * different types of kibble/prizes, different scoring systems...
     * they could be managed here too
     */
    public void update() {

        snake.moveSnake();

        //Ask the snake if it is on top of the kibble
        if (snake.isThisInSnake(kibble.getSquare())) {
			//If so, tell the snake that it ate the kibble
			snake.youAteKibble();
            //And, update the kibble - move it to a new square. Got to check to make sure
            //that the new square is not inside the snake.
            Square kibbleLoc;
            do {
                kibbleLoc = kibble.moveKibble();
            } while (snake.isThisInSnake(kibbleLoc));

            Score.increaseScore();
		}
    }

    public void newGame() {
        Score.resetScore();
        snake.createStartSnake();
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

    public Score getScore() {
        return score;
    }

    public Kibble getKibble() {
        return kibble;
    }

    public Snake getSnake() {
        return snake;
    }

}
