package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.tool.Audio;

public class Champignon extends Item{
    private int compteur, compteurPerime;
    private boolean isRight;
    public Champignon(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.compteur = 0;
        this.compteurPerime = 0;
        this.isRight = true;
        activated = false;
        gravity = false;
        isPicked = false;
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name));
        b = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
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
    @Override
    public void transition(){
        //Audio.playSound(context, R.raw.powerup_appears);
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
            if(!isPickable){
                transition();
            }
            else {
                boolean[] tab = player.detectCollision(this);
                if(tab[0] || tab[1] || tab[2] || tab[3]){
                    if(!isPicked){
                        Audio.playSound(context, R.raw.powerup);
                        player.increaseLife();
                    }
                    isPicked = true;
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
}
