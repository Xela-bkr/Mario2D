package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.dx;
import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.tool.Audio;

public class Coconut extends Ennemy{
    private Bitmap b;
    public Coconut(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name));
        this.bitmap = Bitmap.createScaledBitmap(b, getWidth(), getHeight(), true);
        b = this.bitmap;
        gravityConstant = dx;
        graviteCompteur = gravityConstant;
        activated = false;
        gravity = false;
    }
    @Override
    public void update() {
        if (activated) {
            gravity = true;
            collisions();
            if(isResting) {
                transiter();
            }
            if (!isAlive) {
                dead();
            }
        } else {
            if (getX() <= player.getX() + player.getWidth()*3/2) {
                activated = true;
            }
        }
    }
    public void collisions() {
        boolean[] tab = player.detectCollision(this, (int) (getHeight()*0.2), (int) (getWidth()*0.2), (int) (getHeight()*0.2), (int) (getWidth()*0.2));
        if (tab[0]) {
            if (isAlive) {
                Audio.playSound(context, R.raw.kick_2);
                isAlive = false;
            }
            player.jump2();
        } else if (tab[2]) {
            isResting = true;
            compteurSaut = 0;
            if (!player.getInvincible()) {
                if (!player.getResting()) {
                    player.decreaseLife();
                }
            } else {
                if (isAlive) {
                    Audio.playSound(context, R.raw.kick_2);
                    isAlive = false;
                }
            }
        }
    }
    public void dead() {
        if (deadCompteur%3==0){
            this.bitmap = null;
        } else {
            this.bitmap = b;
        }
        if (deadCompteur > 40) {
            activated = false;
            waitingLineForRemoving.add(this);
        }
        deadCompteur ++;
    }
    public void transiter() {
        int dy = compteurSaut - gravityConstant;
        if (dy >= 0) {
            compteurSaut = 0;
            isResting = false;
            gravity = true;
        } else {
            translateY(dy);
            compteurSaut ++;
        }
    }
}
