package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.mario2d.R;
import com.example.mario2d.game.loop.GameActivity;
import com.example.mario2d.tool.Audio;

public class Piece extends Item{
    private boolean isTaken;
    private int compteurAnimation, compteurRotation;
    private Bitmap face, rotate1, rotate2, rotate3;
    public Piece(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.initX = x;
        this.isPickable = true;
        this.compteurAnimation = 0;
        this.compteurRotation = 0;
        gravity = false;
    }
    public void rotate(int frequence, int...compteur){

        int width1_3 = (int) (initHeight*0.6284);
        int width2 = (int) (initHeight*0.3716);

        if(compteurRotation < frequence){
            if(this.bitmap != rotate1){
                this.bitmap = rotate1;
                translateX((initWidth - width1_3)/2);
                setWidth(width1_3);
                //translateX(width1_3/2);
            }
        }
        else if(compteurRotation >= frequence && compteurRotation < 2*frequence){
           if(this.bitmap != rotate2){
               this.bitmap = rotate2;
               translateX((width1_3 - width2)/2);
               setWidth(width2);
               //translateX(width2/2);
           }

        }
        else if(compteurRotation >= 2*frequence && compteurRotation < 3*frequence){
            if(this.bitmap != rotate3){
                this.bitmap = rotate3;
                translateX(-(width1_3 - width2)/2);
                setWidth(width1_3);
                //translateX(width1_3/2);
            }
        }
        else{
            if(this.bitmap!=face){
                //setX(initX);
                this.bitmap = face;
                translateX(-(initWidth - width1_3)/2 );
                setWidth(initWidth);
                //translateX(initWidth/2);
            }
        }
        compteurRotation ++;
        if(compteurRotation >= 4*frequence){compteurRotation = 0;}

        if(compteur.length >0){
            if(isTaken && compteurAnimation < compteur[0]) {
                translateY(-5);
                compteurAnimation ++;
            }
            else{setActivated(false);}
        }
    }
    public void setCompteurAnimation(int i){this.compteurAnimation = i;}
    public void setIsTaken(boolean taken){this.isTaken = taken;}
    public int getCompteurAnimation(){return this.compteurAnimation;}
    public boolean getIsTaken(){return this.isTaken;}
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
    @Override
    public void update(){
        if(activated){
            boolean[] tab = player.detectCollision(this);
            if(tab[0] || tab[1] || tab[2] || tab[3]){
                isTaken = true;
            }
            if(!isTaken){rotate(12);}
            else{
                rotate(4, 50);
                if(isPickable){
                    player.increasePieceCount();
                    isPickable = false;
                }
            }
        }
    }
}
