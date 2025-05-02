package com.example.mario2d.game.objet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.game.Origin;

import java.util.Arrays;
import java.util.HashMap;

public class Item extends Objet{
    protected boolean isPickable, gravity;
    protected Bitmap b;
    protected HashMap<String, boolean[]> collisionMatrix = new HashMap<String, boolean[]>();
    public Item(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        collisionMatrix.put("objet", new boolean[]{false, false, false, false});
        collisionMatrix.put("personnage", new boolean[]{false, false, false, false});
        isPickable = false;
        gravity = true;

    }
    @Override
    public void update(){
        if(activated){
            if(!isPickable){transition();}
            else{animer();}
        }
    }
    public boolean[] detectCollision(Origin objet, int...error){

        // [haut, droite, bas, gauche] -> en haut de l'objet, à droite de l'objet ...
        boolean[] result = new boolean[4];
        Arrays.fill(result, false);

        int verticalError = 5;
        int lateralError = 4;

        if(error.length == 2){
            verticalError = error[0];
            lateralError = error[1];
        }

        boolean segment_vertical_gauche = getX() + getWidth() > objet.getX() + lateralError;
        boolean segment_vertical_droit = getX() < objet.getX() + objet.getWidth() - lateralError;
        boolean segment_horizontal_haut = getY() + getHeight() > objet.getY() + lateralError;
        boolean segment_horizontal_bas = getY() < objet.getY() + objet.getHeight() - lateralError;
        // collision en haut :
        boolean ch3 = getY() + getHeight() >= objet.getY() - verticalError;
        boolean ch4 = getY() < objet.getY();
        boolean ch5 = getY() + getHeight() <= objet.getY() + objet.getHeight()/2;
        result[0] = segment_vertical_droit && segment_vertical_gauche && ch3 && ch4 && ch5;

        //collision à droite
        boolean cd3 = getX() + lateralError >= objet.getX() + objet.getWidth()/2;
        boolean cd4 = getX() < objet.getX() + objet.getWidth();
        result[1] = segment_horizontal_bas && segment_horizontal_haut && cd3 && cd4;

        // collision en bas :
        boolean cb1 = getY() <= objet.getY() + objet.getHeight() + verticalError;
        boolean cb2 = getY() >= objet.getY() + objet.getHeight()/2;
        result[2] = cb1 && cb2 && segment_vertical_droit && segment_vertical_gauche;

        // collision à gauche :
        boolean cg3 = getX() + getWidth() >= objet.getX();
        boolean cg4 = getX() + getWidth() +lateralError <= objet.getX() + objet.getWidth()/2;
        result[3] = segment_horizontal_bas && segment_horizontal_haut && cg3 && cg4;

        return result;
    }

    public void addCollisionValue(String key, int index, boolean b){
        boolean[] tab = collisionMatrix.get(key);
        tab[index] = b;
        collisionMatrix.put(key, tab);
    }
    public void recalibrerY(Objet objet){
        this.setY(objet.getY() - getHeight());
    }
    protected void transition(){}
    public boolean[] detectCollisionWithFloor(Floor floor, int...margin){
        boolean[] result = new boolean[4];
        Arrays.fill(result, false);
        int marge = margin.length>0 ? margin[0] : 0;
        result[0] = getY() + getHeight() + marge >= floor.getY();
        return result;
    }
    public HashMap<String, boolean[]> getCollisionMatrix(){return this.collisionMatrix;}

    public boolean isGravity() {
        return gravity;
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }
    public void reverseDirection(){
    }
}
