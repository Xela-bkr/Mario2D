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
    public Ennemy(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        setBitmap(name+"_marche_1");
    }
    @Override
    public void walk(int frequence){}
    public void dead(){setAlive(false);}
    public boolean collisionOnTopOfFloor(int floorY, int margin){
        return getY()+getHeight() >= floorY - margin;
    }
}
