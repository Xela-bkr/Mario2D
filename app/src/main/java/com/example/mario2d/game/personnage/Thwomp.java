package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;

public class Thwomp extends Ennemy{
    private boolean isUp, upAuthor;
    private Bitmap up, down;
    private int deltaY;
    public Thwomp(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isUp = true;
        deltaY = 0;
        isInvincible = true;
        isResting = false;
        isWalking = false;
        gravity = false;
        compteurSaut = 0;
        upAuthor = true;
    }
    @Override
    public void setBitmaps() {
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.thwomp_up);
        up = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.thwomp_down);
        down = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
    }
    @Override
    public void invincible(){
        if(!isInvincible){isInvincible = true;}
        if(isUp){
            if(this.bitmap != up){this.bitmap = up;}
            if(upAuthor) translateY(-7);
        }
        else {
            if(this.bitmap != down){this.bitmap = down;}
            if(!gravity){gravity = true;}
        }
        if(getY()+getHeight() >= initY+getHeight()){
            if(compteurSaut > 100){
                isUp = true;
                upAuthor = true;
                gravity = false;
                compteurSaut = 0;
            }
            else{
                compteurSaut ++;
            }
        }
        if(getY() < 0){
            if(compteurSaut > 100){
                isUp = false;
                gravity = true;
                upAuthor = true;
                compteurSaut = 0;
            }
            else{
                upAuthor = false;
                compteurSaut ++;
            }
        }
    }
}
