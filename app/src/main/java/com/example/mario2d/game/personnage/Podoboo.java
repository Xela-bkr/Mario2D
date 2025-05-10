package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Podoboo extends Ennemy {
    private boolean up;
    private Bitmap up1, up2, up3, down1, down2, down3;
    private int upLimit, downLimit;
    public Podoboo(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        up = true;
        upLimit = getY() - 3*getHeight();
        downLimit = getY() + getHeight()*4;
        gravityConstant = 1;
        isResting = false;
        isInvincible = true;
        topIsHurting = true;
        gravity = false;
        jumpImpulse = (int) (getHeight()*0.15);
        setBitmaps();
    }
    @Override
    public void setBitmaps(){

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_down_1"));
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_down_2"));
        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_down_3"));

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_up_1"));
        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_up_2"));
        Bitmap b6 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_up_3"));

        up1 = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);
        up2 = Bitmap.createScaledBitmap(b5, getWidth(), getHeight(), true);
        up3 = Bitmap.createScaledBitmap(b6, getWidth(), getHeight(), true);

        down1 = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
        down2 = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
        down3 = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

    }
    public void update()
    {
       if (activated) {
           updateImage();
           updateMovement();
           updatePlayerCollision();
       }

    }
    public void updateImage()
    {
        if (compteurSaut < 10)
        {
            if (up && this.bitmap != up1)
            {
                this.bitmap = up1;
            } else {
                if(this.bitmap != down1)
                {
                    this.bitmap = down1;
                }
            }
        } else if (compteurSaut < 20)
        {
            if (up && this.bitmap != up2)
            {
                this.bitmap = up2;
            } else {
                if(this.bitmap != down2)
                {
                    this.bitmap = down2;
                }
            }
        } else if (compteurSaut < 30)
        {
            if (up && this.bitmap != up3) {
                this.bitmap = up3;
            } else {
                if(this.bitmap != down3)
                {
                    this.bitmap = down3;
                }
            }
        } else {
            compteurSaut = 0;
        }
        compteurSaut++;
    }
    private void updateMovement()
    {
        if (up)
        {
            if (getY() < upLimit)
            {
                up = false;
            } else {
                translateY();
            }
        } else {
            if (getY() > downLimit)
            {
                up = true;
            } else {
                translateY();
            }
        }
    }
    private void translateY()
    {
        int dy = - (jumpImpulse - graviteCompteur*gravityConstant);
        if (up)
        {
            translateY(dy);
            graviteCompteur ++;
        } else {
            translateY(-dy);
        }
    }
    @Override
    public void rest() {}
    @Override public void invincible() {}
    @Override
    public void dead()
    {
        setActivated(false);
        setAlive(false);
        waitingLineForRemoving.add(this);
    }
    public void updatePlayerCollision() {
        boolean[] tab = player.detectCollision(this);
        if (tab[0] || tab[1] || tab[2] || tab[3]) {
            if(!player.getResting()) {
                if(!player.getInvincible()) {
                    player.decreaseLife();
                } else {
                    dead();
                }
            }
        }
    }
}
