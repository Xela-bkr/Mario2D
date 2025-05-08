package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.brownBlocs;
import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.mario2d.R;
import com.example.mario2d.game.objet.BrownBloc;
import com.example.mario2d.tool.Audio;

public class Spiny extends Ennemy{
    private Bitmap arret_gauche, marche_gauche, arret_droite, marche_droite;
    public Spiny(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isWalking = true;
        isInvincible = false;
        gravity = true;
        gravityConstant = 8;
        topIsHurting = true;
        setBitmaps();
    }
    @Override
    public void setBitmaps(){

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.spiny_arret_gauche);
        arret_gauche = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.spiny_arret_droite);
        arret_droite = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.spiny_marche_gauche);
        marche_gauche = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.spiny_marche_droite);
        marche_droite = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);
    }
    @Override
    public void update(){
        if(activated) {
            if(collisionWithObject(1)){isRight = true;}
            if(collisionWithObject(3)){isRight = false;}
            if(!gravityFall){
                if(!collisionWithObject(0)){reverseDirection();}
            }
            walk(20);
            if(isRight){translateX(4);}
            else{translateX(-4);}

            boolean[] tab = player.detectCollision(this, (int) (getHeight()*0.2), (int) (getWidth()*0.1), (int) (getWidth()*0.1), (int) (getWidth()*0.05));

            if(tab[0] || tab[1] || tab[2] || tab[3]){
                if(!player.getResting()) {
                    if(!player.getInvincible()) {
                        player.decreaseLife();
                    } else {
                        Audio.playSound(context, R.raw.kick_2);
                        dead();
                    }
                }
            }
            if(tab[0]){
                player.jump2();
            }
        }
    }
    @Override
    public void walk(int frequence){
        if(compteurMarche < frequence){
            if(!isRight){
                if(this.bitmap != marche_gauche){this.bitmap = marche_gauche;}
            }
            else{
                if(this.bitmap != marche_droite){this.bitmap = marche_droite;}
            }
        }
        else if(compteurMarche >= frequence && compteurMarche < 2*frequence){
            if(!isRight){
                if(this.bitmap != arret_gauche){this.bitmap = arret_gauche;}
            }
            else{
                if(this.bitmap != arret_droite){this.bitmap = arret_droite;}
            }
        }
        else{compteurMarche = 0;}
        compteurMarche ++;
    }
    @Override
    public void dead() {
        setAlive(false);
        setActivated(false);
        waitingLineForRemoving.add(this);
    }
}
