package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.ennemies;
import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.mario2d.game.personnage.Ennemy;

public class Platforme extends Objet{

    private boolean movable, up;
    private int Ylimit;
    public Platforme(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        movable = false;
        up = true;
        Ylimit = initY;
    }
    @Override
    public void update(){
        for(Ennemy en : ennemies){
            if(en.getActivated()){
                boolean[] tab = en.detectCollision(this);
                if(tab[0]){
                    en.addCollisionValue("objet", 0, true);
                    en.recalibrerY(this);
                }
                if(tab[1]){en.addCollisionValue("objet", 1, true);}
                if(tab[2]){en.addCollisionValue("objet", 2, true);}
                if(tab[3]){en.addCollisionValue("objet", 3, true);}
            }
        }
        if(movable){move();}

        boolean[] tab = player.detectCollision(this);
        if(tab[0]){
            player.recalibrerY(this);
            player.addCollisionValue("brownbloc", 0, true);
        }
    }
    public void setMovable(boolean b){this.movable = b;}
    protected void move(){
        if(up){
            if(getY() > Ylimit){translateY(-2);}
            else{up = false;}
        }
        else{
            if(getY() < initY){translateY(2);}
            else{up = true;}
        }
    }
    public void setYlimit(int l){this.Ylimit = l;}
}
