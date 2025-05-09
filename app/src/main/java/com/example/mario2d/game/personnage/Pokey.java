package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.dx;
import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.tool.Audio;

public class Pokey extends Ennemy{
    Bitmap gauche, droite;
    public Pokey(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isInvincible = false;
        isWalking = true;
        isResting = false;
        this.life = 4;
        gravityConstant = dx/3;
        setBitmaps();
    }
    @Override
    public void setBitmaps() {

        String keyRight = String.format("%s_droite_%d", name, life-1);
        String keyLeft = String.format("%s_gauche_%d", name, life-1);

        if(life == 1){
            keyLeft = String.format("%s_head_gauche", name);
            keyRight = String.format("%s_head_droite", name);
        }

        System.out.println(keyRight);
        System.out.println(keyLeft);

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(keyRight));
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(keyLeft));

        gauche = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
        droite = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
    }
    @Override
    public void update() {
        if (activated) {
            updateCollisions();
            if (isResting) rest();
            else if (isWalking) deplacer();
        }
    }
    public void deplacer(){
        updateImage();
        translateX(isRight ? 1 : -1);
    }
    @Override
    public void rest() {
        if(restCompteur < 40) {
            isWalking = false;
            if (restCompteur%3==0) {
                bitmap = null;
            } else {
                updateImage();
            }
        } else {
            isWalking = true;
            isResting = false;
        }
        restCompteur++;
    }
    @Override
    public void invincible() {

    }
    @Override
    public void dead() {
        setAlive(false);
        setActivated(false);
        waitingLineForRemoving.add(this);
    }
    public void updateCollisions() {
        boolean[] tab = player.detectCollision(this, 0, (int) (getWidth()*0.1), 0, (int) (getWidth()*0.1));
        if (tab[0]) {
            if(!isInvincible) {
                if(!isResting) {
                    Audio.playSound(context, R.raw.kick_2);
                    decreaseLife();
                    isResting = true;
                    isWalking = false;
                }
            }
            player.jump2();
        } else if (tab[1] || tab[2] || tab[3]) {
            if (!player.getInvincible()) {
                if (!player.getResting()) {
                    Audio.playSound(context, R.raw.kick_2);
                    player.decreaseLife();
                }
            } else {
                dead();
            }
        }
        if(collisionWithObject(1) || collisionWithObject(3)) reverseDirection();
    }
    private void updateImage() {
        if (compteurMarche < 20) {
            this.bitmap = gauche;
        } else if (compteurMarche < 40) {
            this.bitmap = droite;
        } else { compteurMarche = 0; }
        compteurMarche++;
    }
    @Override
    public void decreaseLife() {
        life --;
        if (life <= 0) {
            dead();
            return;
        }
        setHeight(getHeight() - initHeight/4);
        setBitmaps();
    }
}
