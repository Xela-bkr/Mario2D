package com.example.mario2d.game.objet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Piece extends Objet{
    private boolean isTaken, shrinkMode, animationTaken;
    private int compteurAnimation, compteurRotation, initX;
    private float takenTime;
    private Bitmap face, rotate1, rotate2, rotate3;
    public Piece(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.initX = x;
        this.isTaken = false;
        this.shrinkMode = true;
        this.animationTaken = false;
        this.compteurAnimation = 0;
        this.initWidth = width;
        this.compteurRotation = 0;
    }
    public void rotate(int frequence, int...compteur){

        int width1_3 = (int) (initHeight*0.6284);
        int width2 = (int) (initHeight*0.3716);

        if(compteurRotation < frequence){
            this.bitmap = rotate1;
            setWidth(width1_3);
            setX(initX + getWidth()/2 - width1_3/2);
        }
        else if(compteurRotation >= frequence && compteurRotation < 2*frequence){
            this.bitmap = rotate2;
            setWidth(width2);

        }
        else if(compteurRotation >= 2*frequence && compteurRotation < 3*frequence){
            this.bitmap = rotate3;
            setWidth(width1_3);
        }
        else{
            this.bitmap = face;
            setWidth(initWidth);
        }
        compteurRotation ++;
        if(compteurRotation >= 4*frequence){compteurRotation = 0;}

        if(compteur.length >0){
            if(isTaken && compteurAnimation < compteur[0]) {translateY(-5);}
            else{setActivated(false);}
        }
        compteurAnimation ++;
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
    @Override
    public void setBitmaps(){

        int width1_3 = (int) (initHeight*0.6284);
        int width2 = (int) (initHeight*0.3716);

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_face"));
        this.face = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_tourne_1"));
        this.rotate1 = Bitmap.createScaledBitmap(b2, width1_3, getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_tourne_2"));
        this.rotate2 = Bitmap.createScaledBitmap(b3, width2, getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_tourne_3"));
        this.rotate3 = Bitmap.createScaledBitmap(b4, width1_3, getHeight(), true);
    }

}
