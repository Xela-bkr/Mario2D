package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.ennemies;
import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FireBowl extends Ennemy{
    private Bitmap boule1, boule2, boule3;
    public FireBowl(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isInvincible = true;
        gravity = false;
        life = 100;
        setBitmaps();
    }
    @Override
    public void setBitmaps(){
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_1"));
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_2"));
        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_3"));

        boule1 = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
        boule2 = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
        boule3 = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        if(boule1 == null || boule2 == null || boule3 == null){
            System.out.println("image nulle");
        }
    }
    @Override
    public void update(){
        if (activated) {
            render();
            move();
            updateCollision();
            if (life <=0) {
                setAlive(false);
                setActivated(false);
                waitingLineForRemoving.add(this);
            }
            life --;
        }
    }
    public void render(){
        if (compteurMarche < 5) {
            if (this.bitmap != boule1) {
                this.bitmap = boule1;
            }
        } else if (compteurMarche < 10) {
            if (this.bitmap != boule2) {
                this.bitmap = boule2;
            }
        } else if (compteurMarche < 15) {
            if (this.bitmap != boule3) {
                this.bitmap = boule3;
            }
        } else {
            compteurMarche = 0;
        }
        compteurMarche ++;
    }
    public void move(){
        int dx = isRight ? 9 : -9;
        translateX(dx);
    }
    public void updateCollision(){

        if (collisionWithObject(1)) {
            isRight = true;
        }
        if (collisionWithObject(3)) {
            isRight = false;
        }
        for ( Ennemy en : ennemies) {
            if(en.getActivated() && en.getAlive()) {
                boolean[] tab = en.detectCollision(this);
                if(tab[0] || tab[1] || tab[2] || tab[3]) {
                    if(!en.getInvincible()) {
                        en.dead();
                    }
                }
            }
        }
        boolean[] play = player.detectCollision(this);
        if(play[0] || play[1] || play[2] || play[3]) {
            if(!player.getInvincible() && !player.getResting()) {
                player.decreaseLife();
            }
        }
    }
}
