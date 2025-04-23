package com.example.mario2d.game.objet;

import android.content.Context;

public class Pipe extends Objet{
    private boolean isPassage;

    public Pipe(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.isPassage = false;
    }
    public boolean isPassage() {return isPassage;}
    public void setPassage(boolean passage) {isPassage = passage;}
}
