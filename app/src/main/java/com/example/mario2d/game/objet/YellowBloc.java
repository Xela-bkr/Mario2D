package com.example.mario2d.game.objet;

import android.content.Context;

public class YellowBloc extends Objet{
    private Objet item;
    private boolean hasBeenUsed;
    public YellowBloc(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.hasBeenUsed = false;
    }
    public boolean getUsedState(){return this.hasBeenUsed;}
    public Objet getItem(){return this.item;}
    public void setUsed(boolean u){this.hasBeenUsed = u;}
    public void setItem(Objet item){this.item = item;}
    public void slideItem(int dx){item.translateX(dx);}
    public void setToStaticBloc(String blocKey){
        this.hasBeenUsed = true;
        this.setBitmap(blocKey);
    }

}
