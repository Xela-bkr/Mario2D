package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.tool.Audio;


public class PlantePirhana extends Ennemy{

    private Bitmap open, close;
    private Boolean top, bottom, right, left, up, down;
    public PlantePirhana(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        up = true;
        gravity = false;
        down = false;
        activated = false;
        isRecalibrable = false;
        isResting = false;
        isWalking = true;
        restCompteur = 0;
        System.out.println("Constructeur");
        setBitmaps();
    }

    @Override
    public void setBitmaps(){

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.plante_pirhana_1);
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.plante_pirhana_2);

        open = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
        close = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
        System.out.println("Bitmaps are setted");
    }
    @Override
    public void update(){
        if(activated) {
            boolean[] tab = player.detectCollision(this, (int) (-getHeight() * 0.1), (int) (-getWidth() * 0.1));
            if (tab[0] || tab[1] || tab[2] || tab[3]) {
                if (!player.getResting()) {
                    if(!player.getInvincible()) {
                        player.decreaseLife();
                        player.rest();
                    } else {
                        dead();
                    }
                }
            }
            if (tab[0]) {
                player.setJumping(true);
                player.recalibrerY(this);
            }
            if(isResting){
                rest();
            }
            if(isWalking){
                if(up){
                    walk(4);
                    translateY(-4);
                }
                else{
                    walk(8);
                    translateY(1);
                }
            }
        }
        else{
            if(player.getX() + player.getWidth()*3 >= getX()){
                activated = true;
                up = true;
                down = false;
            }
        }
    }
    @Override
    public void walk(int frequence){

        if(compteurMarche < frequence){
            if(this.bitmap != close){this.bitmap = close;}
        }
        else if (compteurMarche < 2*frequence){
            if(this.bitmap != open){this.bitmap = open;}
        }
        else{compteurMarche = 0;}
        compteurMarche++;

        if(getY() + getHeight() < initY){
            System.out.println("getY() + getHeight() < initY");
            up = false;
        }
        else if(getY() > initY){
            System.out.println("getY() > initY");
            setY(initY+1);
            isResting = true;
        }
    }
    @Override
    public void rest(){
        System.out.println("Rest()" + restCompteur);
        if(restCompteur < 100){
            restCompteur++;

        }
        else{
            restCompteur = 0;
            isResting = false;
            up = true;
        }
    }
    @Override
    public void dead() {
        this.activated = false;
        this.isAlive = false;
        waitingLineForRemoving.add(this);
    }
    public void updateCollisions() {
        boolean[] tab = player.detectCollision(this);
        if(tab[0]) {
            if(!player.getInvincible()) {
                if(!player.getResting()) {player.decreaseLife();}
            } else {
                    dead();
                }
        } else if (tab[1] || tab[2] || tab[3]) {
            if (!player.getResting()) {
                if (!player.getInvincible()) {
                    player.decreaseLife();
                } else {
                    Audio.playSound(context, R.raw.kick_2);
                    dead();
                }
            }
        }
    }
}
