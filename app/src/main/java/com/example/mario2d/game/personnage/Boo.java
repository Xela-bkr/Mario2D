package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.game.loop.GameView;

public class Boo extends Ennemy{
    private Bitmap arret_droite, arret_gauche, cache_droite, cache_gauche, brick;
    private boolean demiBrickMode;
    public Boo(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isWalking = false;
        isResting = true;
        isInvincible = false;
        isRight = true;
        gravity = false;
        demiBrickMode = false;
    }
    @Override
    public void setBitmaps(){

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_droite"));
        arret_droite = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_gauche"));
        arret_gauche = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_cache_gauche"));
        cache_gauche = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_cache_droite"));
        cache_droite = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);

        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get("greybrick"));
        brick = Bitmap.createScaledBitmap(b5, getWidth(), getHeight(), true);
    }
    @Override
    public void update(){
        updateMode();
        if(isWalking){fly();}
        if(isResting){rest();}
        if(isInvincible){invincible();}

    }
    public void fly(){

        if(GameView.player.getRight()){
            if(getX()<GameView.player.getX()){
                if(this.bitmap!=arret_droite){this.bitmap = arret_droite;}
                translateX(4);
                if(getY() + getHeight()/2 < GameView.player.getY() + GameView.player.getHeight()/2 + 4){
                    translateY(4);
                }
                else if(getY() + getHeight()/2 > GameView.player.getY() + GameView.player.getHeight()/2 - 4){
                    translateY(-4);
                }
            }
            else{
                if (this.bitmap != cache_gauche) {this.bitmap = cache_gauche;}
            }
        }
        else {
            if (getX() < GameView.player.getX()) {
                if (this.bitmap != cache_droite) {
                    this.bitmap = cache_droite;
                }
            }
            else {
                if (this.bitmap != arret_gauche) {this.bitmap = arret_gauche;}
                translateX(-4);
                if(getY() + getHeight()/2 < GameView.player.getY() + GameView.player.getHeight()/2 + 4){
                    translateY(4);
                }
                else if(getY() + getHeight()/2 > GameView.player.getY() + GameView.player.getHeight()/2 - 4){
                    translateY(-4);
                }
            }
        }
    }
    @Override
    public void invincible(){}
    @Override
    public void rest(){
        if(!isResting){dead();}
        if(restCompteur == 0){
            if(this.bitmap!=brick){this.bitmap = brick;}
        }
        if(getX() < (getWidth()/0.03)/2 - 3*getWidth() && !demiBrickMode){
            restCompteur = 1;
            translateY(-2);
            demiBrickMode = true;
        }
        if(restCompteur >= 1 && restCompteur < 60){
            if(this.bitmap!=brick){this.bitmap = brick;}
            translateY(-3);
            restCompteur++;
        }
        else if(restCompteur >= 60 && restCompteur < 100){
            if(this.bitmap!= arret_droite){this.bitmap = arret_droite;}
            translateY(-3);
            restCompteur++;
        }
        else if(restCompteur >=100){
            restCompteur = 0;
            isResting = false;
            isWalking = true;
        }
    }
    @Override
    public void dead(){
        setAlive(false);
        setActivated(false);
    }
    private void updateMode(){
        pushPositions(new int[]{getX(), getY()});
    }
}

