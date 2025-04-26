package com.example.mario2d.game.objet;

import android.content.Context;
import com.example.mario2d.game.Origin;
import com.example.mario2d.game.personnage.Ennemy;
import com.example.mario2d.game.personnage.Personnage;

public abstract class Objet extends Origin{
    protected Boolean isPickable, isPicked, inMotion;
    public Objet(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        if(spriteBank.containsKey(name)){this.setBitmap(name);}
        setBitmaps();
        this.isPickable = false;
        this.isPicked = false;
        this.inMotion = false;
    }
    public void animer(){}
    public void setIsPickabe(Boolean b){this.isPickable=b;}
    public void setIsPicked(Boolean b){this.isPicked=b;}
    public Boolean getPickabe() {return isPickable;}
    public Boolean getPicked() {return isPicked;}
    public void setInMotion(boolean m){this.inMotion = m;}
    public boolean getInMotion(){return this.inMotion;}
    public void setBitmaps(){}
}
