package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;

import com.example.mario2d.game.loop.GameActivity;
import com.example.mario2d.game.personnage.Personnage;

public class Pipe extends Objet{
    private boolean isPassage;

    public Pipe(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.isPassage = false;
    }
    public boolean isPassage() {return isPassage;}
    public void setPassage(boolean passage) {isPassage = passage;}
    @Override
    public void update(){
        boolean[] t = player.detectCollision(this);
        if(t[0]){
            player.recalibrerY(this);
            if (isPassage) {}
        }
        for(Personnage perso : GameActivity.persos){
            if(perso.getActivated()){
                boolean[] tab = perso.detectCollision(this);
                if(tab[0]){perso.recalibrerY(this);}
                if(tab[2] && perso.getJumping()){perso.setJumping(false);}
                if(tab[1]){perso.setRight(false);}
                if(tab[3]){perso.setRight(true);}
            }
        }
    }

}
