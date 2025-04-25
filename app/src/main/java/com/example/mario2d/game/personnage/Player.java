package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.mario2d.game.objet.Item;

public class Player extends Personnage{
    private Item item;
    private int piecesCount;
    private int life;
    private Bitmap arret_droite, arret_gauche, marche_droite, marche_gauche;
    public Player(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.piecesCount = 0;
        this.life = 2;
        if(!name.equals("mario") && !name.equals("luigi")){name="mario";}
        setBitmap(arret_droite, name+"_arret_droite");
        setBitmap(arret_gauche, name+"_arret_gauche");
        setBitmap(marche_droite, name+"_marche_droite");
        setBitmap(marche_gauche, name+"_marche_gauche");
    }
    /*
    @Override
    public void walk(int frequence){
        if(compteurMarche < frequence){
            if(getRightState()){setBitmap(marche_droite);}
            else{setBitmap(marche_gauche);}
            //setBitmap(getRightState() ? marche_droite : marche_gauche) ;
        }
        else if(compteurMarche > frequence && compteurMarche <= 2*frequence) {
            if (getRightState()) {setBitmap(marche_droite);}
            else {setBitmap(marche_gauche);}
        }
        compteurMarche++;
        if(compteurMarche > 2*frequence){compteurMarche = 0;}
    }*/
    public void increaseLife(int x){this.life ++;}
    public void decreaseLfe(){this.life --;}
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
