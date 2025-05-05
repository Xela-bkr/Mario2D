package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.game.loop.GameActivity;
import com.example.mario2d.game.loop.GameView;
import com.example.mario2d.tool.Audio;

public class Boo extends Ennemy{
    private Bitmap arret_droite, arret_gauche, cache_droite, cache_gauche, brick;
    private boolean demiBrickMode;
    private String camouflage;
    public Boo(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isWalking = false;
        isResting = true;
        isInvincible = false;
        isRecalibrable = false;
        isRight = true;
        gravity = false;
        demiBrickMode = false;
        camouflage = "greybrick";
        setBitmaps();
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

        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(camouflage));
        brick = Bitmap.createScaledBitmap(b5, getWidth(), getHeight(), true);
    }
    @Override
    public void update(){
        if (activated)
        {
            if(isResting){rest();}
            if(isWalking){fly();}
            if(isInvincible){invincible();}
        }
    }
    public void fly(){
        gravity = false;
        if(player.getRight()){
            if(getX()< player.getX()){
                if(this.bitmap!=arret_droite){this.bitmap = arret_droite;}
                translateX(4);
                if(getY() + getHeight()/2 < player.getY() + player.getHeight()/2 + 4){
                    translateY(4);
                }
                else if(getY() + getHeight()/2 > player.getY() + player.getHeight()/2 - 4){
                    translateY(-4);
                }
            }
            else{
                if (this.bitmap != cache_gauche) {this.bitmap = cache_gauche;}
            }
        }
        else {
            if (getX() < player.getX()) {
                if (this.bitmap != cache_droite) {
                    this.bitmap = cache_droite;
                }
            }
            else {
                if (this.bitmap != arret_gauche) {this.bitmap = arret_gauche;}
                translateX(-4);
                if(getY() + getHeight()/2 < player.getY() + player.getHeight()/2 + 4){
                    translateY(4);
                }
                else if(getY() + getHeight()/2 > player.getY() + player.getHeight()/2 - 4){
                    translateY(-4);
                }
            }
        }
    }
    @Override
    public void invincible() {}
    @Override
    public void rest(){
        if (!isResting) {
            dead();
            return;
        }
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
        waitingLineForRemoving.add(this);
    }
    private void updateMode(){
        pushPositions(new int[]{getX(), getY()});
    }
    public void changeCamouflage(String key){
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key));
        brick = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
    }
}

