package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.displayHeight;
import static com.example.mario2d.game.loop.GameActivity.ennemies;
import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineBrownBlocsForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.game.personnage.Ennemy;

public class PlateformeEphemere extends Platforme{
    private int compteur, vie;
    Bitmap etat1, etat2;
    public PlateformeEphemere(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        compteur = 0;
        vie = 300;
        movable = false;
        up = false;
        setBitmaps();
    }
    @Override
    public void setBitmaps() {

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_1"));
        etat1 = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_2"));
        etat2 = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
    }
    public void update() {
        if (activated) {
            if (compteur < vie*2/3) {
                if (this.bitmap != etat1) { this.bitmap = etat1; }
            } else if (this.compteur < vie) {
                if (this.bitmap != etat2) { this.bitmap = etat2; }
                if (movable) {
                    if (up) {
                        translateY(-3);
                    } else {
                        translateY(3);
                    }
                }
            } else {
                if (getY() < displayHeight*2) {
                    translateY(10);
                } else {
                    setActivated(false);
                }
            }
            boolean[] tab = player.detectCollision(this);
            if(tab[0]){
                player.recalibrerY(this);
                player.addCollisionValue("brownbloc", 0, true);
                compteur ++;
            }
            for(Ennemy en : ennemies){
                if(en.getActivated()){
                    boolean[] tab2 = en.detectCollision(this);
                    if(tab2[0]){
                        en.addCollisionValue("objet", 0, true);
                        en.recalibrerY(this);
                    }
                }
            }
        }
    }
    public void setVie(int vie) {this.vie = vie;}
    public void setMovable(boolean movable) {this.movable = true;}
}
