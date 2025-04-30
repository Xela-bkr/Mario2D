package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Boo extends Ennemy{
    private Bitmap arret_droite, arret_gauche, demibrick_droite, demibrick_gauche, brick;
    private boolean brickMode, demiBrickMode, up;
    private int deltaX, deltaY;
    public Boo(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        deltaX = 0;
        deltaY = 0;
        up = true;
    }
    @Override
    public void setBitmaps(){

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_droite"));
        arret_droite = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_gauche"));
        arret_gauche = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_demi_brick_gauche"));
        demibrick_gauche = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_demi_brick_droite"));
        demibrick_droite = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);

        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"greybrick"));
        brick = Bitmap.createScaledBitmap(b5, getWidth(), getHeight(), true);
    }
    @Override
    public void update(){
    }
    public void fly(){
        if(gravity){
            gravity = false;
        }
        if(deltaX < 500){
            if(isRight){
                if(this.bitmap != arret_droite){this.bitmap = arret_droite;}
                translateX(5);
                deltaX += 5;
            }
            else{
                if(this.bitmap != arret_gauche){this.bitmap = arret_gauche;}
                translateX(-5);
                deltaX -= 5;
            }
        }
        else{
            deltaX = 0;
        }
    }
    public void brick(){
        if(this.bitmap != brick){this.bitmap = brick;}
    }
    public void demiBrick(){
        if(isRight){if(this.bitmap != demibrick_droite){this.bitmap = demibrick_droite;}}
        else{if(this.bitmap != demibrick_gauche){this.bitmap = demibrick_gauche;}}
    }
}

