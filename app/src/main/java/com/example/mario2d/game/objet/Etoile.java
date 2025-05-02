package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;

public class Etoile extends Item{

    private int compteur, compteurPerime;
    private boolean isRight;
    public Etoile(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        gravity = false;
        this.compteur = 0;
        this.compteurPerime = 0;
        isRight = true;
    }
    @Override
    public void transition(){
        gravity = false;
        isPickable = false;
        if(getY() > initY - getHeight()*2 ){
            translateY(-5);
        }
        else{
            gravity = true;
            isPickable = true;
        }
    }
    @Override
    public void update(){
        if(activated){
            if(!isPickable){transition();}
            else if(isPickable){
                boolean[] tab = player.detectCollision(this);
                if(tab[0] || tab[1] || tab[2] || tab[3]){
                    player.setInvincibleCompteur(1000);
                    player.setInvincible(true);
                    isPickable = false;
                    activated = false;
                }
                if(collisionMatrix.get("objet")[1] || collisionMatrix.get("objet")[3]){
                    if(isRight){isRight = false;}
                    else{isRight = true;}
                }
                animer();
            }
        }
    }
    public void animer(){
        gravity = true;
        if(compteur < 1200){
            if(isRight){translateX(4);}
            else{translateX(-4);}
            if(compteur>800){
                if(this.compteurPerime < 25){
                    this.bitmap = null;
                }
                else {this.bitmap = b;}
                compteurPerime ++;
                if(compteurPerime >= 50){compteurPerime = 0;}
            }
            compteur ++;
        }
        else {
            this.activated = false;
        }
    }
}
