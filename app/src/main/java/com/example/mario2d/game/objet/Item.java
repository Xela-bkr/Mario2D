package com.example.mario2d.game.objet;

import android.content.Context;

public class Item extends Objet{
    boolean isCollected, isUsing, isPerimed;
    public Item(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.isCollected = false;
        this.isUsing = false;
        this.isPerimed = false;
    }
    public boolean isCollected() {return isCollected;}
    public boolean isUsing() {return isUsing;}
    public boolean isPerimed() {return isPerimed;}
    public void setCollected(boolean collected) {isCollected = collected;}
    public void setUsing(boolean using) {isUsing = using;}
    public void setPerimed(boolean perimed) {isPerimed = perimed;}
}
