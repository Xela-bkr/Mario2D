package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.persos;
import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;

public class Thwomp extends Ennemy{
    private boolean isUp, upAuthor, isDown;
    private Bitmap up, down;
    private int deltaY;
    public Thwomp(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isUp = false;
        isDown = true;
        deltaY = 0;
        isInvincible = true;
        isResting = false;
        isWalking = false;
        gravity = false;
        compteurSaut = 0;
        gravityConstant=15;
        upAuthor = true;
        activated = false;
        setCollisionMatrixToFalse();
        setBitmaps();
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
            translateY(-7);
        }
        else if(isDown){
            if(this.bitmap != down){this.bitmap = down;}
            if(!gravity){gravity = true;}
        }
        if(collisionWithObject(0)){
            if(compteurSaut < 40){
                compteurSaut ++;
            }
            else{
                compteurSaut = 0;
                isUp = true;
                isDown = false;
                gravity = false;
            }
        }
        if(getY() <= 0){
            isUp = false;
            if(compteurSaut < 60){
                compteurSaut ++;
            }
            else{
                compteurSaut = 0;
                isDown = true;
                isUp = false;
                gravity = true;
            }
        }
    }
    public boolean getUp(){return this.isUp;}
    public void setIsUp(boolean isUp){this.isUp = isUp;}
    public void setUpAuthor(boolean upAuthor){this.upAuthor = upAuthor;}
    @Override
    public void update(){
        if(activated){
            boolean[] tab1 = player.detectCollision(this, (int) (getHeight()*0.05), (int) (-getWidth()*0.05));
            if(tab1[0]){
                player.setJumping(true);
                player.recalibrerY(this);
            }
            else if(tab1[2]){
                if(!player.isResting){
                    player.decreaseLife();
                    player.setResting(true);
                    player.rest();
                }
            }
            invincible();
        }
        else {if(getX() <= player.getX()){setActivated(true);}}
    }

}
