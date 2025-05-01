package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Goomba extends Ennemy{
    /**
     * Constructor
     *
     * @param context
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     */
    Bitmap marche1, marche2, dead;
    public Goomba(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        setActivated(true);
        deadCompteur = 0;
        graviteCompteur = 1;
        gravityConstant = 15;
    }
    @Override
    public void walk(int frequence){
        setBitmap(compteurMarche<frequence ? marche1 : marche2);

        if(isRight){
            if(!gravityFall){
                if(!collisionWithObject(0) && !collisionMatrix.get("floor")[0]){
                    isRight = false;
                    translateX(-5);
                }
                else{
                    translateX(5);
                }
            }
            else{
                translateX(5);
            }
        }
        else{
            if(!gravityFall){
                if(!collisionWithObject(0) && !collisionMatrix.get("floor")[0]){
                    isRight = true;
                    translateX(5);
                }
                else{
                    translateX(-5);
                }
            }
            else{
                translateX(-5);
            }
        }

        if(compteurMarche >= 2*frequence){compteurMarche = 0;}
        compteurMarche ++;
    }
    @Override
    public void dead() {
        isInvincible = false;
        isResting = false;
        isAlive = false;
        if(deadCompteur < 40){
            if(this.bitmap != dead) this.bitmap = dead;
        }
        else setActivated(false);
        deadCompteur ++;
    }
    @Override
    public void setBitmaps(){
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_1"));
        marche1 = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_2"));
        marche2 = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort"));
        dead = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);
    }
    @Override
    public void rest(){
        dead();
    }
    @Override
    public void update(){
        if(activated){
            if(isResting){rest();}
            else if(isInvincible){invincible();}
            else if(isAlive){walk(frequenceMarche);}
            else{dead();}
        }
    }
    @Override
    public void invincible(){ dead(); }
}
