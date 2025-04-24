package com.example.mario2d.game.personnage;

import android.content.Context;

import com.example.mario2d.game.objet.Item;

public class Player extends Personnage{
    private Item item;
    private int piecesCount;
    private int life;
    public Player(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.piecesCount = 0;
        this.life = 2;
    }
    public void increaseLife(int x){this.life ++;}
    public void decreaseLfe(){this.life --;}
    public int getLife(){return this.life;}
    public void setLife(int x){this.life = x;}
    public void setPiecesCount(int x){this.piecesCount = x;}
    public void increasePieceCount(){this.piecesCount ++;}
    public void decreasePieceCount(){this.piecesCount --;}
    public int getPiecesCount(){return this.piecesCount;}
    public void setItem(Item item){this.item = item;}
    public Item getItem(){return this.item;}
}
