package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mario2d.game.objet.Item;

public class Player extends Personnage{
    private float agrCoeff = 1.86f;
    private Item item;
    private int piecesCount;
    private int initialWidth, initialHeight;
    private Bitmap arret_droite, arret_gauche, marche_droite, marche_gauche, saute_droite, saute_gauche, mort_droite, mort_gauche;
    public Player(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.initialHeight = height; this.initialWidth = width;
        this.piecesCount = 0;
        this.life = 2;
        this.frequenceMarche = 10;
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
        if (this.life == 1){
            int newWidth = getWidth() / 2;
            int newHeight = (int) (agrCoeff*newWidth);
            setWidth(newWidth);
            setHeight(newHeight);
            setBitmaps();
            if(isWalking){
                if(isRight){this.bitmap = marche_droite;}
                else{this.bitmap = marche_gauche;}
            }
            else{
                if(isRight){this.bitmap = arret_droite;}
                else{this.bitmap = arret_gauche;}
            }
        }
        if(this.life == 0){dead();}
    }
    @Override
    public void increaseLife(){
        this.life ++;
        setWidth(initialWidth);
        setHeight(initialHeight);
        setBitmaps();
        setY(getY()-getHeight()/2);
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
    public void setPiecesCount(int x){this.piecesCount = x;}
    public void increasePiece(){this.piecesCount ++;}
    public void decreasePiece(){this.piecesCount --;}
    public int getPiecesCount(){return this.piecesCount;}
    public void setItem(Item item){this.item = item;}
    public Item getItem(){return this.item;}
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

    }
    @Override
    public void invincible(){
        if(this.invincibleCompteur > 0){
            if(!this.isInvincible){setInvincible(true);}
            invincibleCompteur --;
        }
        else{
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
    public void increasePieceCount(){
        this.piecesCount ++;
    }
}
