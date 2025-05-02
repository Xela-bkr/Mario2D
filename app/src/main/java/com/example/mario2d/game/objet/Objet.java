package com.example.mario2d.game.objet;

import android.content.Context;
import com.example.mario2d.game.Origin;
import com.example.mario2d.game.personnage.Ennemy;
import com.example.mario2d.game.personnage.Personnage;

import java.util.Arrays;

public class Objet extends Origin{
    protected Boolean isPickable, isPicked, inMotion;
    public Objet(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        if(spriteBank.containsKey(name)){this.setBitmap(name);}
        setBitmaps();
        this.isPickable = false;
        this.isPicked = false;
        this.inMotion = false;
    }
    public void animer(){}
    public void setIsPickabe(Boolean b){this.isPickable=b;}
    public void setIsPicked(Boolean b){this.isPicked=b;}
    public Boolean getPickabe() {return isPickable;}
    public Boolean getPicked() {return isPicked;}
    public void setInMotion(boolean m){this.inMotion = m;}
    public boolean getInMotion(){return this.inMotion;}
    public void setBitmaps(){}
    public boolean[] collision(Origin objet){

        // [haut, droite, bas, gauche] -> en haut de l'objet, à droite de l'objet ...
        boolean[] result = new boolean[4];
        Arrays.fill(result, false);

        boolean segment_vertical_gauche = getX() + getWidth() > objet.getX();
        boolean segment_vertical_droit = getX() < objet.getX() + objet.getWidth();
        boolean segment_horizontal_haut = getY() + getHeight() > objet.getY();
        boolean segment_horizontal_bas = getY() < objet.getY() + objet.getHeight();
        // collision en haut :
        boolean ch3 = getY() + getHeight() >= objet.getY();
        boolean ch4 = getY() < objet.getY();
        boolean ch5 = getY() + getHeight() <= objet.getY() + objet.getHeight()/2;
        result[0] = segment_vertical_droit && segment_vertical_gauche && ch3 && ch4 && ch5;

        //collision à droite
        boolean cd3 = getX() >= objet.getX() + objet.getWidth()/2;
        boolean cd4 = getX() < objet.getX() + objet.getWidth();
        result[1] = segment_horizontal_bas && segment_horizontal_haut && cd3 && cd4;

        // collision en bas :
        boolean cb1 = getY() <= objet.getY() + objet.getHeight();
        boolean cb2 = getY() >= objet.getY() + objet.getHeight()/2;
        result[2] = cb1 && cb2 && segment_vertical_droit && segment_vertical_gauche;

        // collision à gauche :
        boolean cg3 = getX() + getWidth() >= objet.getX();
        boolean cg4 = getX() + getWidth() <= objet.getX() + objet.getWidth()/2;
        result[3] = segment_horizontal_bas && segment_horizontal_haut && cg3 && cg4;

        return result;
    }
}
