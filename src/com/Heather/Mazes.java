package com.Heather;

import java.util.Random;

/**
 * Created by cryst on 4/12/2016.
 */
public class Mazes {//

    private static int mazeX;
    private static int mazeY;

    public Mazes(Snake s){
        makeMazes(s);
    }

    protected void makeMazes(Snake s){
        Random r=new Random();
        for (int i=0;i<5;i++){
            mazeX=r.nextInt(SnakeGame.xSquares);
            mazeY=r.nextInt(SnakeGame.ySquares);
        }

    }
    protected void snakeMaze(Snake s){
        boolean mazeInSnake=false;//get snake position
        if (mazeInSnake) {

    }
}



    public static int getMazeX(){
        return mazeX;
    }
    public static int getMazeY() {
        return mazeY;
    }
}
