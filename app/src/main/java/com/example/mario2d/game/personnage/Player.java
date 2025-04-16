package com.example.mario2d.game.personnage;

import android.content.Context;

public class Player extends Personnage{
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
    public Player(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
    }
}
