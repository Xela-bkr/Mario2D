package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.tool.Audio;

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
        life = 1;
        isInvincible = false;
        isResting = false;
        isAlive = true;
        isWalking = true;
        setBitmaps();
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
        isWalking = false;
        isAlive = false;
        if(deadCompteur < 40){
            if(this.bitmap != dead) this.bitmap = dead;
            deadCompteur ++;
        }
        else setActivated(false);
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
    public void invincible(){ dead(); }
    @Override
    public void decreaseLife(){
        life --;
        if(life <= 0){
            dead();
        }
    }
    @Override
    public void update(){
        if(activated){
            if(collisionWithObject(1) || collisionWithObject(3)){reverseDirection();}
            if(isResting){rest();}
            else if(isInvincible){invincible();}
            else if(isWalking){walk(frequenceMarche);}
            else{dead();}
        }
    }
}
