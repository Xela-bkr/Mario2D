package com.example.mario2d.game.objet;

import android.content.Context;

public class BrownBloc extends Objet{
    private boolean isHit;
    public BrownBloc(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.isHit = false;
    }
    public boolean getIsHit(){return this.isHit;}
    public void setHitState(boolean b){this.isHit = b;}
}
