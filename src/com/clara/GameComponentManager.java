package com.clara;

/**
 * Created by Clara. Manages game components such as the Snake, Kibble... and their interactions.
 */
public class GameComponentManager {

    private Kibble kibble;
    private Snake snake;
    private Score score;
    private Wall wall;

    protected static boolean lingeringTimer = false; // a variable to keep track of if there is a unstopped timer from a previous game session.

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
            //that the new square is not inside the snake or a wall.
            Square kibbleLoc;
            do {
                kibbleLoc = kibble.moveKibble();
                isKibbleOutOfWall();
            } while (snake.isThisInSnake(kibbleLoc));

            score.increaseScore();
		}

        // checks to see if the snake is any wall and then ends the game if so.
        for (Wall wall : SnakeGame.wallList) {
            if (snake.isThisInSnake(wall.getSquare())) {
                lingeringTimer = true;
                SnakeGame.setGameStage(SnakeGame.GAME_OVER);
                return;
            }
        }
    }

    public void newGame() {
        score.resetScore();
        snake.createStartSnake();
        kibble.moveKibble();
        isKibbleOutOfWall();
        for (Wall wall : SnakeGame.wallList) {
            wall.moveWall();
        }
    }

    // a method to check if the kibble is in a wall and moves it if so.
    private void isKibbleOutOfWall(){
        int i = 0;
        while(i <= SnakeGame.gameDifficulty){
            for (Wall wall : SnakeGame.wallList) {
                if (kibble.isThisInKibble(wall.getSquare())) {
                    kibble.moveKibble();
                    i = 0;
                } else {
                    i += 1;
                }
            }
        }
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

    public void addWall(Wall wall) {this.wall = wall;}

    public Score getScore() {
        return score;
    }

    public Kibble getKibble() {
        return kibble;
    }

    public Snake getSnake() {
        return snake;
    }

    public Wall getWall() { return wall;}

}
