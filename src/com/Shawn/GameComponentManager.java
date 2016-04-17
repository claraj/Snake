package com.Shawn;

/**
 * Created by Clara. Manages game components such as the Snake, Kibble... and their interactions.
 */
public class GameComponentManager {

    private Kibble kibble;
    private Kibble badKibble;
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
        if (snake.didEatKibble(kibble)) {
			//tell kibble to update
            kibble.moveKibble(snake);
            Score.increaseScore();
		}
        if (snake.didEatBadKibble(badKibble)){
            //update the kibble
            badKibble.moveKibble(snake);
            Score.decreaseScore();
        }
    }

    public void newGame() {
        snake.reset();
    }


    public void addKibble(Kibble kibble) {
        this.kibble = kibble;
    }
    public void addBadKibble(Kibble badKibble){
        this.badKibble = badKibble;
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
    public Kibble getBadKibble(){
        return badKibble;
    }

    public Snake getSnake() {
        return snake;
    }



}
