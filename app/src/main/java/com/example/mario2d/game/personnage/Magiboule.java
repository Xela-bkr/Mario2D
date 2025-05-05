package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.tool.Audio;

public class Magiboule extends Ennemy{
    Bitmap mag1, mag2, mag3, mag4;
    public Magiboule(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        setBitmaps();
        isResting = true;
        isRight = true;
        gravity = false;
        life = 200;
    }
    @Override
    public void setBitmaps() {
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.magiboule_1);
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.magiboule_2);
        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.magiboule_3);
        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.magiboule_4);

        mag1 = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
        mag2 = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
        mag3 = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);
        mag4 = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);
    }
    public void update(){
        if(activated) {
            animer();
            updateCollisions();
            if (restCompteur < 30) {
                restCompteur ++;
            } else {
                deplacer();
            }
            if (life <= 0) {
                dead();
            } else {
                life --;
            }
        }
    }
    private void animer(){
        if(compteurMarche < 8) {
            this.bitmap = mag1;
        } else if (compteurMarche < 16) {
            this.bitmap = mag2;
        } else if (compteurMarche < 24) {
            this.bitmap = mag3;
        } else if (compteurMarche < 32) {
            this.bitmap = mag4;
        } else {
            compteurMarche = 0;
        }
        compteurMarche ++;
    }
    private void deplacer() {
        if (player.getX() < getX() - getWidth()) {
            isRight = false;
            translateX(-5);
        } else if (player.getX() > getX() +getWidth()) {
            isRight = true;
            translateX(5);
        }
        if(player.getY() + player.getHeight()/2 < getY() + getHeight()/2) {
            translateY(-2);
        } else if (player.getY() + player.getHeight()/2 > getY() + getHeight()/2) {
            translateY(2);
        }
    }
    @Override
    public void dead() {
        setAlive(false);
        setActivated(false);
        waitingLineForRemoving.add(this);
    }
    public void updateCollisions() {
        boolean[] tab = player.detectCollision(this);
        if (tab[0] || tab[1] || tab[2] || tab[3]) {
            if (!player.getInvincible()) {
                if(!player.getResting()) {
                    player.decreaseLife();
                }
            } else {
                Audio.playSound(context, R.raw.kick_2);
                dead();
            }
        }
    }
}
