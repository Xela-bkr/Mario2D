package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mario2d.R;
import com.example.mario2d.game.objet.Item;
import com.example.mario2d.tool.Audio;

public class Player extends Personnage{
    private float agrCoeff = 1.86f;
    private int piecesCount;
    private Audio star;
    private int initialWidth, initialHeight;
    private Bitmap arret_droite, arret_gauche, marche_droite, marche_gauche, saute_droite, saute_gauche, mort_droite, mort_gauche, victoire;
    public Player(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.initialHeight = height; this.initialWidth = width;
        this.piecesCount = 0;
        this.life = 2;
        this.frequenceMarche = 8;
        this.gravityConstant = 1;
        this.jumpImpulse = 18;
        this.compteurSaut = 0;
        this.isResting = false;
        this.restCompteur = 0;
        if(!name.equals("mario") && !name.equals("luigi")){name="mario";}
        setBitmaps();
        this.bitmap = arret_droite;
        collisionMatrix.put("ennemy", new boolean[]{false, false, false, false});
    }
    @Override
    public void decreaseLife(){
        this.life --;
        isResting = true;
        /*if (this.life == 1){
            int newWidth = getWidth() / 2;
            int newHeight = (int) (agrCoeff*newWidth);
            setWidth(newWidth);
            setHeight(newHeight);
            setBitmaps();
            setY(getY()-getWidth());
            if(isWalking){
                if(isRight){this.bitmap = marche_droite;}
                else{this.bitmap = marche_gauche;}
            }
            else{
                if(isRight){this.bitmap = arret_droite;}
                else{this.bitmap = arret_gauche;}
            }
        }*/
        if(this.life == 0){dead();}
    }
    @Override
    public void increaseLife(){
        this.life ++;
        setWidth(initialWidth);
        setHeight(initialHeight);
        setBitmaps();
        setY(getY()-initialHeight/2);
        if(!isWalking){
            if(isRight){this.bitmap = arret_droite;}
            else{this.bitmap = arret_gauche;}
        }
    }
    @Override
    public void walk(int frequence){
        if(compteurMarche < frequence){
            if(isRight){
                this.bitmap = marche_droite;
            }
            else{this.bitmap = marche_gauche;}
        }
        else if(compteurMarche >= frequence && compteurMarche < 2*frequence){
            if(isRight){this.bitmap = arret_droite;}
            else{this.bitmap = arret_gauche;}
        }
        compteurMarche ++;

        if(compteurMarche >= 2*frequence){compteurMarche = 0;}
    }
    public void decreasePiece(){this.piecesCount --;}
    public int getPiecesCount(){return this.piecesCount;}
    public void setBitmaps(){
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_droite"));
        this.arret_droite= Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_arret_gauche"));
        this.arret_gauche= Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_droite"));
        this.marche_droite = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_marche_gauche"));
        this.marche_gauche = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);

        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_saute_droite"));
        this.saute_droite = Bitmap.createScaledBitmap(b5, getWidth(), getHeight(), true);

        Bitmap b6 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_saute_gauche"));
        this.saute_gauche = Bitmap.createScaledBitmap(b6, getWidth(), getHeight(), true);

        Bitmap b7 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort_droite"));
        this.mort_droite = Bitmap.createScaledBitmap(b7, getWidth(), getHeight(), true);

        Bitmap b8 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort_gauche"));
        this.mort_gauche = Bitmap.createScaledBitmap(b8, getWidth(), getHeight(), true);

        Bitmap b9 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_victoire"));
        victoire = Bitmap.createScaledBitmap(b9, getWidth(), getHeight(), true);

    }
    @Override
    public void invincible(){
        if(this.invincibleCompteur > 0){
            if(!this.isInvincible)
            {
                setInvincible(true);
                star = new Audio(context, R.raw.star);
                star.setLoop(true);
                star.play();
            }
            /*if(star == null){
                star = new Audio(context, R.raw.staritem);
                star.setLoop(true);
            }*/

            invincibleCompteur --;
        }
        else{
            if(star != null)
            {
                star.stop();
            }
            this.setInvincible(false);
        }
    }
    @Override
    public void rest(){
        isResting = true;
        if(restCompteur < 100){
            if(!isResting) setResting(true);

        }
        else{
            restCompteur = 0;
            this.isResting = false;
        }
        restCompteur ++;
    }
    @Override
    public void update(){

        if(collisionWithObject(2)){
            if(isJumping){isJumping = false;}
            if(!gravity){gravity = true;}
        }
        if(collisionWithObject(1) || collisionWithObject(3)){
            if(isWalking){isWalking = false;}
        }
        if(isWalking){walk(frequenceMarche);}
        if(isJumping){
            if(jumpTime == 0){jumpTime = System.nanoTime();}
            jump();
        }
        if(isResting){rest();}
        if(isInvincible){invincible();}
    }
    public void jump(){

        long ascentTime = 1_000_000_000/3;

        long currentTime = System.nanoTime();
        long deltaTime = currentTime - getJumpTime();
        if(deltaTime <= ascentTime && !collisionWithObject(2)){
            int dy = -(getJumpImpulse() - getGravityConstant()*getCompteurSaut());
            translateY(dy);
            this.bitmap = isRight ? saute_droite : saute_gauche;
            increaseCompteurSaut();
        }
        else{
            setJumping(false);
            setCompteurSaut(0);
            gravity = true;
            this.bitmap = isRight ? arret_droite : arret_gauche;
        }
    }
    @Override
    public void increasePieceCount()
    {
        Audio.playSound(context, R.raw.coin_3);
        this.piecesCount ++;
    }
    public void jump2(){
        translateY(-7);
    }
    @Override
    public void dead(){
        isAlive = false;
        if(isRight){
            if(this.bitmap != mort_droite){this.bitmap = mort_droite;}
        }
        else{
            if(this.bitmap != mort_gauche){this.bitmap = mort_gauche;}
        }
    }
    public void win(){
        if(this.bitmap != victoire){
            this.bitmap = victoire;
        }
    }
}
