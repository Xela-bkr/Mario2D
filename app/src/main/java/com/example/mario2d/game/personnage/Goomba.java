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
    private int compteurMort;
    Bitmap marche1, marche2, dead;
    public Goomba(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        setActivated(true);
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_1"));
        marche1 = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_2"));
        marche2 = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort"));
        dead = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        compteurMort = 0;
    }
    @Override
    public void walk(int frequence){
        setBitmap(compteurMarche<frequence ? marche1 : marche2);
        if(compteurMarche >= 2*frequence){compteurMarche = 0;}
        compteurMarche ++;
    }
    @Override
    public void dead() {
        if (!getAlive()) {setAlive(false);}
        if(compteurMort < 20){setBitmap(dead);}
        else{setActivated(false);}
        compteurMort ++;
    }

    @Override
    public void decreaseLife() {dead();}
}
