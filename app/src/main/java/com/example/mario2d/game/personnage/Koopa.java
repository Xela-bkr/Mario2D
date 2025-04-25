package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Koopa extends Ennemy {
    private int compteurMort, compteurRotate;
    private boolean carapaceMode, lauchingMode;
    private Bitmap arret_droite, arret_gauche, marche_droite, marche_gauche, carapace_face, rotate_1, rotate_2, rotate_3;
    public Koopa(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        setBitmaps();
        compteurMort = 0;
        carapaceMode = false;
        lauchingMode = false;
    }
    public void setBitmaps(){
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_droite"));
        this.arret_droite= Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_gauche"));
        this.arret_gauche= Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_droite"));
        this.marche_droite = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_gauche"));
        this.marche_gauche = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);

        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_carapace_face"));
        this.carapace_face = Bitmap.createScaledBitmap(b5, getWidth()/2, getHeight()/2, true);

        Bitmap b6 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_carapace_tourne_1"));
        this.rotate_1 = Bitmap.createScaledBitmap(b6, getWidth()/2, getHeight()/2, true);

        Bitmap b7 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_carapace_tourne_2"));
        this.rotate_2 = Bitmap.createScaledBitmap(b7, getWidth()/2, getHeight()/2, true);

        Bitmap b8 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_carapace_tourne_3"));
        this.rotate_3 = Bitmap.createScaledBitmap(b8, getWidth()/2, getHeight()/2, true);
    }
    @Override
    public void walk(int frequence){
        if(compteurMarche < frequence){
            if(isRight){this.bitmap = marche_droite;}
            else{this.bitmap = marche_gauche;}
        }
        if(compteurMarche >= frequence && compteurMarche < 2*frequence){
            if(isRight){this.bitmap = arret_droite;}
            else{this.bitmap = arret_gauche;}
        }
        compteurMarche ++;
        if(compteurMarche >= 2*frequence){compteurMarche = 0;}
    }
    @Override
    public void dead() {
        if (!getAlive()) {setAlive(false);}
        if(compteurMort < 20){setBitmap(carapace_face);}
        else{setActivated(false);}
        compteurMort ++;
    }
    public void carapaceMode(){
        int height = getHeight();
        setHeight((int) (getHeight()/2));
        setWidth(getWidth()/2);
        setY(getY() + height/2);
        carapaceMode = true;
    }
    public void launch(int compteur, int frequence){
        if(compteurMort < compteur){
            if(carapaceMode){carapaceMode = false;}
            if(!lauchingMode){lauchingMode = true;}
            if(compteurRotate < frequence){this.bitmap = rotate_1;}
            else if(compteurRotate >= frequence && compteurRotate < 2*frequence){this.bitmap = rotate_2;}
            else if (compteurRotate >= 2*frequence && compteurRotate < 3*frequence){this.bitmap = rotate_3;}
            else if(compteurRotate <= 3*frequence && compteurRotate < 4*frequence){this.bitmap = carapace_face;}

            compteurRotate ++;
            if(compteurRotate >= 4*frequence){compteurRotate = 0;}

            if(isRight){ translateX(8); }
            else { translateX(-8); }

            compteurMort ++;
        }
        else{
            lauchingMode = false;
            isAlive = false;
            activated = false;
        }
    }
    public boolean getCarapaceMode(){return this.carapaceMode;}
    public boolean getLauchingMode(){return this.lauchingMode;}
    public void setCompteurMort(int compteurMort){this.compteurMort = compteurMort;}
}
