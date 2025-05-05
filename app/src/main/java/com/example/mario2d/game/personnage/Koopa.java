package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.ennemies;
import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.game.loop.GameActivity;
import com.example.mario2d.tool.Audio;

import java.util.Arrays;

public class Koopa extends Ennemy {
    protected Bitmap arret_droite, arret_gauche, marche_droite, marche_gauche, carapace_face, rotate_1, rotate_2, rotate_3;
    protected int frequenceRotation, compteurRotation;
    protected Audio invincibleAudio;
    protected boolean rotable;
    public Koopa(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        setBitmaps();
        this.isJumping = false;
        this.isRight = false;
        this.isWalking = true;
        this.isAlive = true;
        this.isInvincible = false;
        this.isResting = false;
        this.invincibleCompteur = 0;
        this.restCompteur = 0;
        this.deadCompteur = 100;
        this.frequenceMarche = 20;
        this.frequenceRotation = 16;
        this.compteurRotation = 0;
        this.gravityFall = true;
        this.rotable = false;
        gravity = true;
        graviteCompteur = 1;
        gravityConstant = 15;
        this.setDeadWidth(width/2);
        this.setDeadHeight(height/2);

        invincibleAudio = new Audio(context, R.raw.spin_jump);
        invincibleAudio.setLoop(true);
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
                if(!gravityFall){
                    if(!collisionWithObject(0) && !collisionMatrix.get("floor")[0]){
                        this.isRight = false;
                        this.bitmap = marche_gauche;
                        this.translateX(-5);
                    }
                    else{
                        this.bitmap = marche_droite;
                        this.translateX(5);
                    }
                }
                else{
                    this.bitmap = marche_droite;
                    this.translateX(5);
                }
            }
            else{
                if(!gravityFall){
                    if(!collisionWithObject(0) && !collisionMatrix.get("floor")[0]) {
                        this.isRight = true;
                        this.bitmap = marche_droite;
                        this.translateX(5);
                    }
                    else{
                        this.bitmap = marche_gauche;
                        this.translateX(-5);
                    }
                }
                else{
                    this.bitmap = marche_gauche;
                    this.translateX(-5);
                }
            }
        }
        if(compteurMarche >= frequence && compteurMarche < 2*frequence){
            if(isRight){
                if(!gravityFall){
                    if(!collisionWithObject(0) && !collisionMatrix.get("floor")[0]) {
                        this.isRight = false;
                        this.bitmap = arret_gauche;
                        this.translateX(-5);
                    }
                    else{
                        this.bitmap = arret_droite;
                        this.translateX(5);
                    }
                }
                else{
                    this.bitmap = arret_droite;
                    this.translateX(5);
                }
            }
            else{
                if(!gravityFall){
                    if(!collisionWithObject(0) && !collisionMatrix.get("floor")[0]) {
                        this.isRight = false;
                        this.bitmap = arret_gauche;
                        this.translateX(5);
                    }
                    else{
                        this.bitmap = arret_gauche;
                        this.translateX(-5);
                    }
                }
                else{
                    this.bitmap = arret_gauche;
                    this.translateX(-5);
                }
            }
        }
        compteurMarche ++;
        if(compteurMarche >= 2*frequence){compteurMarche = 0;}
    }
    @Override
    public void dead() {
        isInvincible = false;
        isResting = false;
        isAlive = false;
        activated = false;
        waitingLineForRemoving.add(this);
    }
    @Override
    public void rest(){
        if (restCompteur < 10) { player.translateY(-4); }
        else{
            player.gravity = true;
            rotable = true;
        }
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
            rotable = false;
            isResting = false;
            isWalking = true;
            setY(initY);
            setHeight(initHeight);
            setWidth(initWidth);
        }
        restCompteur++;
    }
    @Override
    public void invincible(){
        if(!isInvincible)
        {
            isInvincible = true;
            invincibleAudio.play();
        }
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
            invincibleAudio.stop();
            setInvincibleCompteur(0);
            dead();
        }
    }
    @Override
    public void decreaseLife(){
        life --;
        if(life == 1){
            isResting = true;
        }
        else if(life == 0){
            isInvincible = true;
        }
    }
    @Override
    public void update(){
        if(activated){

            if(collisionWithObject(1) || collisionWithObject(3)){reverseDirection();}
            updateCollisions();
            if (isResting) {
                rest();
            }  else if (isInvincible) {
                invincible();
            } else if (isWalking) {
                walk(frequenceMarche);
            } else {
                dead();
            }
            for(Ennemy en : ennemies) {
                boolean[] tab1 = detectCollision(en);
                if(tab1[0] || tab1[1] || tab1[2] || tab1[3]){
                    if(isInvincible && !en.getInvincible() && en.getAlive())
                    {
                        Audio.playSound(context, R.raw.kick_2);
                        en.dead();
                    }
                }
            }
        }
    }
    public void updateCollisions() {
        boolean[] tab = player.detectCollision(this);
        if (tab[0]) {
            if(!getInvincible()) {
                if(!getResting()) {
                    Audio.playSound(context, R.raw.kick_2);
                    rest();
                } else {
                    invincible();
                }
            }
            player.recalibrerY(this);
            player.jump2();
        } else if (tab[1] || tab[2] || tab[3]) {
            if(!isInvincible) {
                if (!isResting) {
                    if (!player.getInvincible()) {
                        if(!player.getResting()) {
                            player.decreaseLife();
                        } else {
                            invincible();
                        }
                    } else {
                        dead();
                    }
                } else {
                    invincible();
                }
            }
        }
    }
}
