package com.example.mario2d.game.objet;

import android.content.Context;

public class YellowBloc extends Objet{
    private boolean hasBeenUsed;
    public YellowBloc(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.hasBeenUsed = false;
    }
    public boolean getUsedState(){return this.hasBeenUsed;}
    public void setUsed(boolean u){this.hasBeenUsed = u;}
}
