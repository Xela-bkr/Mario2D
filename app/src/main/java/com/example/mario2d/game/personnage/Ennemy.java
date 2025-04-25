package com.example.mario2d.game.personnage;

import android.content.Context;

public class Ennemy extends Personnage{
    /**
     * Constructor
     *
     * @param context
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     */
    protected double deadTimer;
    public Ennemy(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        deadTimer = 0;
    }
    @Override
    public void walk(int frequence){}
    public void dead(){
        double deltaTime = 0;
        if(deadTimer == 0){this.deadTimer = deadTimer;}
        else{deltaTime = deadTimer - this.deadTimer;}
        if(!getAlive()){setAlive(false);}

        if(deltaTime > 500_000){setActivated(false);}
        //setBitmap(dead);
    }
    public boolean collisionOnTopOfFloor(int floorY, int margin){
        return getY()+getHeight() >= floorY - margin;
    }
}
