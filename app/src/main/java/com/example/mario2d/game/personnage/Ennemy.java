package com.example.mario2d.game.personnage;

import android.content.Context;

public class Ennemy extends Personnage {
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
    private int deadWidth, deadHeight;
    public Ennemy(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
    }

    public int getDeadWidth() {
        return deadWidth;
    }

    public void setDeadWidth(int deadWidth) {
        this.deadWidth = deadWidth;
    }

    public int getDeadHeight() {
        return deadHeight;
    }

    public void setDeadHeight(int deadHeight) {
        this.deadHeight = deadHeight;
    }
}
