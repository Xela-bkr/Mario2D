package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.castles;
import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.tool.Audio;

public class BillBoum extends Ennemy{
    private Bitmap etat1, etat2;
    public BillBoum(Context context, String name, int x, int y, int width, int height, boolean right) {
        super(context, name, x, y, width, height);
        isRight = right;
        gravity = false;
        life = 500;
        setBitmaps();
    }
    @Override
    public void setBitmaps() {

        String key1 = isRight ? "_droite_1" : "_gauche_1";
        String key2 = isRight ? "_droite_2" : "_gauche_2";

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+key1));
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+key2));

        etat1 = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
        etat2 = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
    }
    @Override
    public void update() {
        if (activated) {
            if (isRight) {
                translateX(6);
            } else {
                translateX(-6);
            }
            walk();
            updateCollisions();
            if(getX() +getWidth() <= castles.get(0).getX() ||
                    getX() >= castles.get(1).getX() + castles.get(1).getWidth()
                    || life <= 0) {
                waitingLineForRemoving.add(this);
            }
            life --;
        }
    }
    private void updateCollisions() {
        boolean[] tab = player.detectCollision(this, 5, -10);
        if (tab[0]) {
            dead();
            Audio.playSound(context, R.raw.kick_2);
        }
        if (tab[1] || tab[2] || tab[3]) {
            if(!player.getResting()) {
                if (!player.getInvincible()) player.decreaseLife();
                else dead(); Audio.playSound(context, R.raw.kick_2);
            }
        }
    }
    @Override
    public void dead() {
        setAlive(false);
        setActivated(false);
        waitingLineForRemoving.add(this);
    }
    @Override
    public void rest() {
        dead();
    }
    @Override
    public void invincible() {
        dead();
    }
    private void walk() {
        if (compteurMarche < 10) {
            if (this.bitmap != etat2) {
                this.bitmap = etat2;
            }
        } else if (compteurMarche < 20) {
            if (this.bitmap != etat1) {
                this.bitmap = etat1;
            }
        } else {
            compteurMarche = 0;
        }
        compteurMarche ++;
    }
}
