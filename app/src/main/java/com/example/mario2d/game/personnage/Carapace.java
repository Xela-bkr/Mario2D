package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.tool.Audio;

public class Carapace extends Ennemy{
    private Bitmap face, tourne1, tourne2, tourne3;
    private int[] vectors;
    public Carapace(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isResting = true;
        life = 400;
        System.out.println("carapace créée");
        setBitmaps();
        vectors = calculerDxDy();
    }
    @Override
    public void setBitmaps() {
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.redkoopa_carapace_face);
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.redkoopa_carapace_tourne_1);
        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.redkoopa_carapace_tourne_2);
        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.redkoopa_carapace_tourne_3);

        face = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
        tourne1 = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
        tourne2 = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);
        tourne3 = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);
    }
    @Override
    public void update() {
        if(activated) {
            updateCollisions();
            animer();
            if (isResting) {
                rest();
            } else if (isWalking) {
                deplacer();
            }
            if(life <= 0) {
                dead();
            }
            life --;
        }
    }
    private void deplacer() {
        translateX(vectors[0]);
        translateY(vectors[1]);
    }
    @Override
    public void rest() {
        if (restCompteur < 30) {
            restCompteur ++;
        } else {
            isResting = false;
            isInvincible = true;
            isWalking = true;
        }
    }
    public void updateCollisions() {
        boolean[] tab = player.detectCollision(this, 0, (int) (getWidth()*0.1), 0, (int) (getWidth()*0.1));
        if(tab[0]) {
            Audio.playSound(context, R.raw.kick_2);
            dead();
        }
        else if(tab[1] || tab[2] || tab[3]) {
            if(!player.getInvincible()) {
                if(!player.getResting()) {
                    player.decreaseLife();
                    dead();
                }
            } else {
                dead();
            }
        }
    }
    @Override
    public void dead() {
        player.score += 40;
        setAlive(false);
        setActivated(false);
        waitingLineForRemoving.add(this);
    }
    public void animer() {
        if(compteurMarche < 2) {
            this.bitmap = tourne1;
        } else if (compteurMarche < 4) {
            this.bitmap = tourne2;
        } else if (compteurMarche < 6) {
            this.bitmap = tourne3;
        } else if(compteurMarche < 8) {
            this.bitmap = face;
        } else {
            compteurMarche = 0;
        }
        compteurMarche ++;
    }
    private int[] calculerDxDy() {
        int deltaX = player.getX() + player.getWidth() / 2 - (getX() + getWidth() / 2);
        int deltaY = player.getY() + player.getHeight() / 2 - (getY() + getHeight() / 2);

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (distance == 0) distance = 1; // éviter division par zéro

        double vitesse = 15.0;

        int incrementX = (int) (vitesse * deltaX / distance);
        int incrementY = (int) (vitesse * deltaY / distance);

        return new int[] {incrementX, incrementY};
    }
}

