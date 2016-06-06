package com.Heather;

import java.util.*;

/**
 * Created by cryst on 4/12/2016.
 */
public class Mazes {//

    private static int mazeX;
    private static int mazeY;
    private static HashMap<Integer, Integer> mazeCoordinates = new HashMap<>();

    public Mazes(Snake s){
        makeMazes(s);
    }

    protected static HashMap<Integer, Integer> makeMazes(Snake s){
        Random r=new Random();

        for (int i=0;i<5;i++){
            mazeX=r.nextInt(SnakeGame.xSquares);
            mazeY=r.nextInt(SnakeGame.ySquares);
            mazeCoordinates.put(mazeX,mazeY);
        }
        return mazeCoordinates;
    }
    public static boolean isMazeSegment(int kibbleX, int kibbleY) {
        for (Integer x:mazeCoordinates.keySet()) {
            int MazeX = x;
            int MazeY = mazeCoordinates.get(x);
        	if(((50*MazeX)==kibbleX ) && ((50 *MazeY)==kibbleY))
                return true;

        	}
        return false;
    }
    protected void snakeMaze(Snake s){
        boolean mazeInSnake=false;//get snake position
        if (mazeInSnake) {

    }
}

    public static HashMap<Integer, Integer> getMazeCoordinates(){
        return mazeCoordinates;
    }

    public static int getMazeX(){
        return mazeX;
    }
    public static int getMazeY() {
        return mazeY;
    }
}
