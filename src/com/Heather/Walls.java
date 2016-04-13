package com.Heather;
import java.util.*;
/**
 * Created by cryst on 4/12/2016.
 */
public class Walls {


    private int wallX;
    private int wallY;

    public Walls(Snake s){
        makeWalls(s);
    }

    protected void makeWalls(Snake s){
        Random r=new Random();
        for (int i=0;i<5;i++){
            wallX=r.nextInt(SnakeGame.xSquares);
            wallY=r.nextInt(SnakeGame.ySquares);
        }

    }

    public int getWallX(){
        return wallX;
    }
    public int getWallY() {
        return wallY;
    }
}
