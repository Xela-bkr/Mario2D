package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.mario2d.game.objet.Item;

public class Player extends Personnage{
    private float agrCoeff = 1.86f;
    private Item item;
    private int piecesCount;
    private int life;
    private int initialWidth, initialHeight;
    private Bitmap arret_droite, arret_gauche, marche_droite, marche_gauche;
    public Player(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.initialHeight = height; this.initialWidth = width;
        this.piecesCount = 0;
        this.life = 2;
        if(!name.equals("mario") && !name.equals("luigi")){name="mario";}
        setBitmap(arret_droite, name+"_arret_droite");
        setBitmap(arret_gauche, name+"_arret_gauche");
        setBitmap(marche_droite, name+"_marche_droite");
        setBitmap(marche_gauche, name+"_marche_gauche");

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
        }
        if(life == 0){setAlive(false);}
    }
    @Override
    public void increaseLife(){
        if(life == 1){
            life ++;
            setWidth(initialWidth);
            setHeight(initialHeight);
            setY(getY()-getHeight());
        }
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
}
