package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;

import com.example.mario2d.game.loop.GameActivity;
import com.example.mario2d.game.personnage.Personnage;

public class BrownBloc extends Objet{
    private boolean isHit;
    public BrownBloc(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.isHit = false;
    }
    public boolean getIsHit(){return this.isHit;}
    public void setHitState(boolean b){this.isHit = b;}
    @Override
    public void update(){
        if(player.detectCollision(this)[0]){
            player.recalibrerY(this);
        }
        if(player.detectCollision(this)[2]){
                if(player.getJumping()){player.setJumping(false);}
                player.setY(getY()+getHeight());
        }
        for(Personnage perso : GameActivity.persos){
            if(perso.getActivated()){
                boolean[] tab = perso.detectCollision(this);
                if(tab[0] && perso.getGravity()){perso.recalibrerY(this);}
                if(tab[2] && perso.getJumping()){perso.setJumping(false);}
            }
        }
    }
}
