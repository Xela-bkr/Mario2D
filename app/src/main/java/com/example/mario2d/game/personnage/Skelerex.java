package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Skelerex extends Koopa{
    private Bitmap demi_mort_droite, demi_mort_gauche, mort_droite, mort_gauche;
    public Skelerex(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isWalking = true;
        setBitmaps();
    }
    @Override
    public void setBitmaps(){
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_droite"));
        arret_droite = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_gauche"));
        arret_gauche = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_droite"));
        marche_droite = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_gauche"));
        marche_gauche = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);

        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_demi_mort_droite"));
        demi_mort_droite = Bitmap.createScaledBitmap(b5, getWidth(), getHeight(), true);

        Bitmap b6 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_demi_mort_gauche"));
        demi_mort_gauche = Bitmap.createScaledBitmap(b6, getWidth(), getHeight(), true);

        Bitmap b7 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort_droite"));
        mort_droite = Bitmap.createScaledBitmap(b7, getWidth(),getHeight(), true);

        Bitmap b8 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort_gauche"));
        mort_gauche = Bitmap.createScaledBitmap(b8, getWidth(), getHeight(), true);
    }
    @Override
    public void invincible(){
        rest();
    }

    @Override
    public void rest(){
        if(isWalking){isWalking = false;}
        if(!isResting){isResting = true;}
        if(restCompteur < 20){
            if(isRight && this.bitmap != demi_mort_droite){this.bitmap = demi_mort_droite;}
            else if(!isRight && this.bitmap!=demi_mort_gauche){this.bitmap = demi_mort_gauche;}
            restCompteur++;
        }
        else if(restCompteur < 200){
            if(isRight && this.bitmap != mort_droite){this.bitmap = mort_droite;}
            else if(!isRight && this.bitmap!=mort_gauche){this.bitmap = mort_gauche;}
            restCompteur++;
        }
        else if(restCompteur < 220){
            if(isRight && this.bitmap != demi_mort_droite){this.bitmap = demi_mort_droite;}
            else if(!isRight && this.bitmap!=demi_mort_gauche){this.bitmap = demi_mort_gauche;}
            restCompteur++;
        }
        else {
            restCompteur = 0;
            isWalking = true;
            isResting = false;
        }
    }
    @Override
    public void dead(){}
}
