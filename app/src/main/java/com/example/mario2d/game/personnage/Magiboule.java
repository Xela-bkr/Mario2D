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
    private int[] vectors;
    public Magiboule(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        setBitmaps();
        isResting = true;
        isRight = true;
        gravity = false;
        life = 200;
        vectors = calculerDxDy();
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
            if (life <= 0) {
                dead();
                return;
            } else {
                life --;
            }
            if (restCompteur < 30) {
                restCompteur ++;
            } else {
                deplacer();
            }
        }
    }
    private void animer(){
        if(compteurMarche < 3) {
            this.bitmap = mag1;
        } else if (compteurMarche < 6) {
            this.bitmap = mag2;
        } else if (compteurMarche < 9) {
            this.bitmap = mag3;
        } else if (compteurMarche < 12) {
            this.bitmap = mag4;
        } else {
            compteurMarche = 0;
        }
        compteurMarche ++;
    }
    private void deplacer() {
        translateX(vectors[0]);
        translateY(vectors[1]);
    }
    @Override
    public void dead() {
        setAlive(false);
        setActivated(false);
        waitingLineForRemoving.add(this);
    }
    public void updateCollisions() {
        boolean[] tab = player.detectCollision(this, 0, (int) (getWidth()*0.2), 0, (int) (getWidth()*0.2));
        if(tab[0]) {
            Audio.playSound(context, R.raw.kick_2);
            dead();
        }
        else if (tab[1] || tab[2] || tab[3]) {
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
    private int[] calculerDxDy() {
        int deltaX = player.getX() + player.getWidth() / 2 - (getX() + getWidth() / 2);
        int deltaY = player.getY() + player.getHeight() / 2 - (getY() + getHeight() / 2);

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (distance == 0) distance = 1; // éviter division par zéro

        double vitesse = 10.0;

        int incrementX = (int) (vitesse * deltaX / distance);
        int incrementY = (int) (vitesse * deltaY / distance);

        return new int[] { incrementX, incrementY };
    }
}
