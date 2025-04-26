package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Arrays;

public class Koopa extends Ennemy {
    private Bitmap arret_droite, arret_gauche, marche_droite, marche_gauche, carapace_face, rotate_1, rotate_2, rotate_3;
    private int frequenceRotation, compteurRotation;
    public Koopa(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        setBitmaps();
        this.isJumping = false; this.isRight = true; this.isWalking = false;
        this.gravityConstant = 2;
        this.jumpImpulse = 16;
        this.compteurSaut = 0;
        this.positions = new int[2][2];
        Arrays.fill(positions, new int[]{0, 0});
        this.isAlive = true;
        this.isInvincible = false;
        this.isResting = false;
        this.invincibleCompteur = 0;
        this.restCompteur = 0;
        this.life = 2;
        this.deadCompteur = 100;
        this.frequenceMarche = 20;
        this.frequenceRotation = 16;
        this.compteurRotation = 0;

        this.setDeadWidth(width/2);
        this.setDeadHeight(height/2);
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
            if(isRight){
                this.bitmap = marche_droite;
                this.translateX(5);
            }
            else{
                this.bitmap = marche_gauche;
                this.translateX(-5);
            }
        }
        if(compteurMarche >= frequence && compteurMarche < 2*frequence){
            if(isRight){
                this.bitmap = arret_droite;
                this.translateX(5);
            }
            else{
                this.bitmap = arret_gauche;
                this.translateX(-5);
            }
        }
        compteurMarche ++;
        if(compteurMarche >= 2*frequence){compteurMarche = 0;}
    }
    @Override
    public void dead() {
        isInvincible = false;
        isResting = false;
        if (getAlive()) {
            this.isAlive = false;
            this.bitmap = carapace_face;
        }
        else if(deadCompteur > 100){setActivated(false);}
        else{this.bitmap = carapace_face;}

        deadCompteur ++;
    }
    @Override
    public void rest(){
        if(!isResting){
            if(! (getHeight() < initHeight) && !(getWidth() < initWidth)){
                int height = getHeight();
                setHeight(getDeadHeight());
                setWidth(getDeadWidth());
                setY(getY() + getDeadHeight());
            }
            this.bitmap = carapace_face;
            isResting = true;
            isInvincible = false;
            restCompteur = 0;
        }
        else if(restCompteur >= 100){
            isResting = false;
            setY(initY);
            setHeight(initHeight);
            setWidth(initWidth);
        }
        restCompteur++;
    }
    @Override
    public void invincible(){
        isInvincible = true;
        isResting = false;
        if(invincibleCompteur < 300){
            if(compteurRotation < frequenceRotation) this.bitmap = rotate_1;
            else if(compteurRotation >= frequenceRotation && compteurRotation < 2*frequenceRotation) this.bitmap = rotate_2;
            else if(compteurRotation >= 2*frequenceRotation && compteurRotation < 3*frequenceRotation) this.bitmap = rotate_3;
            else this.bitmap = carapace_face;

            translateX( isRight ? 5 : -5);

            invincibleCompteur ++;
            compteurRotation ++;
            if(compteurRotation >= 4*frequenceRotation){
                compteurRotation = 0;
            }
        }
        else{
            setInvincibleCompteur(0);
            dead();
        }
    }
    @Override
    public void update(){
        if(activated){
            if(isResting){rest();}
            else if(isInvincible){invincible();}
            else if(isAlive){walk(frequenceMarche);}
            else{
                dead();
            }
        }
    }
}
