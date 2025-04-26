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
    private int life;
    private int initialWidth, initialHeight;
    private Bitmap arret_droite, arret_gauche, marche_droite, marche_gauche, mort_droite, mort_gauche;
    public Player(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.initialHeight = height; this.initialWidth = width;
        this.piecesCount = 0;
        this.life = 2;
        if(!name.equals("mario") && !name.equals("luigi")){name="mario";}

        setBitmaps();

        collisionMatrix.put("ennemy", new boolean[]{false, false, false, false});
    }
    @Override
    public void decreaseLife(){
        life --;
        if (life == 1){
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
        if(life == 0){setAlive(false);}
    }
    @Override
    public void increaseLife(){
        if(life == 1){
            life ++;
            setWidth(initialWidth);
            setHeight(initialHeight);
            setBitmaps();
            setY(getY()-getHeight());
        }
    }
    @Override
    public void walk(int frequence){
        if(compteurMarche < frequence){
            if(isRight){this.bitmap = marche_droite;}
            else{this.bitmap = marche_gauche;}
        }
        else if(compteurMarche >= frequence && compteurMarche < 2*frequence){
            if(isRight){this.bitmap = arret_droite;}
            else{this.bitmap = arret_gauche;}
        }
        compteurMarche ++;

        if(compteurMarche >= 2*frequence){compteurMarche = 0;}
    }
    public int getLife(){return this.life;}
    public void setLife(int x){this.life = x;}
    public void setPiecesCount(int x){this.piecesCount = x;}
    @Override
    public void increasePieceCount(){this.piecesCount ++;}
    public void decreasePieceCount(){this.piecesCount --;}
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
    }
}
