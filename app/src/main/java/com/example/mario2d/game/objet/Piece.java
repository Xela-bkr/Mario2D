package com.example.mario2d.game.objet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Piece extends Objet{
    private boolean isTaken, shrinkMode, animationTaken;
    private int compteurAnimation, initWidth, rotationAngle;
    private float takenTime;
    public Piece(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.isTaken = false;
        this.shrinkMode = true;
        this.animationTaken = false;
        this.compteurAnimation = 0;
        this.initWidth = width;
        this.rotationAngle = 0;
    }
    public void rotate(){
        /*if(shrinkMode){shrinkWidth(1);}
        else{increaseWidth(1);}

        if(rotationAngle >= 360) rotationAngle = 0;
        if(getWidth()<10){shrinkMode = false;}
        if(getWidth()>initWidth){setWidth(initWidth);shrinkMode = true;}

        Matrix matrix = new Matrix();
        matrix.preRotate(rotationAngle);
        Bitmap b = Bitmap.createBitmap(getBitmap(), 0, 0, getWidth(), getHeight(), matrix, true);

        rotationAngle ++;*/
    }
    public void animationTaken(){
        if(shrinkMode){shrinkWidth(50);}
        else{increaseWidth(50);}
        shrinkMode = getWidth()<10 ? false : true;
        if(getWidth()>initWidth){setWidth(initWidth);shrinkMode = true;}
        translateY(-5);
    }
    public void setCompteurAnimation(int i){this.compteurAnimation = i;}
    public void setAnimationTaken(boolean b){this.animationTaken = b;}
    public void setIsTaken(boolean taken){this.isTaken = taken;}
    public void setTakenTime(float takenTime){this.takenTime = takenTime;}
    public int getCompteurAnimation(){return this.compteurAnimation;}
    public boolean getAnimationTaken(){return this.animationTaken;}
    public boolean getIsTaken(){return this.isTaken;}
    public float getTakenTime(){return this.takenTime;}

}
