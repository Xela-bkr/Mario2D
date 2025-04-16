package com.example.mario2d.game.objet;

import android.content.Context;
import com.example.mario2d.game.Origin;

public class Objet extends Origin{

    protected Boolean isPickable, isPicked;
    public Objet(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.setBitmap(name);
    }
    public void animer(){}
    public void setIsPickabe(Boolean b){this.isPickable=b;}
    public void setIsPicked(Boolean b){this.isPicked=b;}

    public Boolean getPickabe() {return isPickable;}
    public Boolean getPicked() {return isPicked;}
}
