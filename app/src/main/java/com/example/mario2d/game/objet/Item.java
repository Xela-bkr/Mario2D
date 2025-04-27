package com.example.mario2d.game.objet;

import android.content.Context;

public class Item extends Objet{
    boolean isCollected, isUsing, isPerimed;
    public Item(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.isCollected = false;
        this.isUsing = false;
        this.minWidth = 20;
        this.minHeight = 20;
        System.out.printf("Width and height : %d & %d\n", width, height);
        //if(spriteBank.containsKey(name)){this.setBitmap(name);}
        this.isPerimed = false;
    }
    public boolean getIsCollected() {return isCollected;}
    public boolean isUsing() {return isUsing;}
    public boolean getIsPerimed() {return isPerimed;}
    public void setCollected(boolean collected) {isCollected = collected;}
    public void setUsing(boolean using) {isUsing = using;}
    public void setPerimed(boolean perimed) {isPerimed = perimed;}
    public void slideAnimation(int limit, int step){
        if(getY()<limit + step){translateY(-step);}
        else{setIsPickabe(true);}
    }
}
