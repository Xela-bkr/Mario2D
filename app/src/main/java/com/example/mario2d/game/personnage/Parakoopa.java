package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.ennemies;
import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Parakoopa extends Koopa {

    private Bitmap up_droite, up_gauche, down_droite, down_gauche;
    private int deltaX, deltaY;
    private boolean up, koopaMode;
    /**
     * Constructor
     *
     * @param context
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Parakoopa(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.up = true;
        this.deltaX = 0;
        this.deltaY = 0;
        this.gravity = false;
        this.koopaMode = false;
        setBitmaps();
    }
    @Override
    public void setBitmaps() {

        String key = name.replace("para", "");
        System.out.println(key);

        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_arret_droite"));
        this.arret_droite= Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_arret_gauche"));
        this.arret_gauche= Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_marche_droite"));
        this.marche_droite = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_marche_gauche"));
        this.marche_gauche = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);

        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_carapace_face"));
        this.carapace_face = Bitmap.createScaledBitmap(b5, getWidth()/2, getHeight()/2, true);

        Bitmap b6 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_carapace_tourne_1"));
        this.rotate_1 = Bitmap.createScaledBitmap(b6, getWidth()/2, getHeight()/2, true);

        Bitmap b7 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_carapace_tourne_2"));
        this.rotate_2 = Bitmap.createScaledBitmap(b7, getWidth()/2, getHeight()/2, true);

        Bitmap b8 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_carapace_tourne_3"));
        this.rotate_3 = Bitmap.createScaledBitmap(b8, getWidth()/2, getHeight()/2, true);

        Bitmap b9 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_down_droite"));
        this.down_droite = Bitmap.createScaledBitmap(b9, getWidth(), getHeight(), true);

        Bitmap b10 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_down_gauche"));
        this.down_gauche = Bitmap.createScaledBitmap(b10, getWidth(), getHeight(), true);

        Bitmap b11 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_up_droite"));
        this.up_droite = Bitmap.createScaledBitmap(b11, getWidth(), getHeight(), true);

        Bitmap b12 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_up_gauche"));
        this.up_gauche = Bitmap.createScaledBitmap(b12, getWidth(), getHeight(), true);
    }
    public void fly() {
        if(gravity){
            gravity = false;
        }
        if(! (Math.abs(deltaX) <= 500)){
            if(isRight){isRight = false;}
            else{isRight = true;}
            deltaX = 0;
        }
        int dx = isRight ? 5 : -5;
        translateX(dx);
        deltaX += dx;

        if(!(Math.abs(deltaY)<100)){
            if(up){up = false;}
            else{up = true;}
            deltaY = 0;
        }
        if(up){
            if(isRight){if(this.bitmap != up_droite){this.bitmap = up_droite;}}
            else{if(this.bitmap != up_gauche){this.bitmap = up_gauche;}}
            translateY(-4);
        }
        else{
            if(isRight){if(this.bitmap != down_droite){this.bitmap = down_droite;}}
            else{if(this.bitmap != down_gauche){this.bitmap = down_gauche;}}
            translateY(4);
        }
        deltaY += 4;
    }
    @Override public void update() {
        if(activated){
            boolean[] tab = player.detectCollision(this);
            if(tab[0]){
                if(isResting){
                    isResting = false;
                    isWalking = false;
                    isInvincible = true;
                    player.rest();
                    player.jump2();
                }
                if(!isResting && !isInvincible && !koopaMode){
                    rest();
                    player.rest();
                    player.jump2();
                }
                if(!isResting && !isInvincible && koopaMode){
                    rest();
                    player.rest();
                    player.jump2();
                }
                if(isInvincible){
                    player.recalibrerY(this);
                }
            }
            if(tab[1] || tab[2] || tab[3]){
                if(player.getInvincible()){
                    dead();
                }
                else{
                    if(!player.getResting()){
                        player.decreaseLife();
                        player.rest();
                    }
                }
            }
            if(collisionWithObject(1) || collisionWithObject(3)){reverseDirection();}
            if (!koopaMode) {
                fly();
            } else {
                if (isResting) {
                    gravity = true;
                    rest();
                } else if (isInvincible) {
                    gravity = true;
                    gravityFall = true;
                    invincible();
                } else if (isAlive) {
                    walk(frequenceMarche);
                } else {
                    dead();
                }
            }
        }
        for(Ennemy en : ennemies){
            boolean tab[] = detectCollision(en);
            if(tab[0] || tab[1] || tab[2] || tab[3]){
                if(isInvincible && !en.getInvincible()){en.dead();}
            }
        }
    }
    @Override public void rest(){
        if(!isResting){
            if(!koopaMode){
                translateY(-getHeight());
                koopaMode = true;
                gravity =  true;
                isResting = false;
            }
            else {
                if (!(getHeight() < initHeight) && !(getWidth() < initWidth)) {
                    int height = getHeight();
                    setHeight(getDeadHeight());
                    setWidth(getDeadWidth());
                    setY(getY() + getDeadHeight());
                }
                this.bitmap = carapace_face;
                isResting = true;
                isInvincible = false;
                restCompteur = 0;
            }
        }
        else if(restCompteur >= 100){
            restCompteur = 0;
            isResting = false;
            setHeight(initHeight);
            setWidth(initWidth - 30);
        }
        else{
            restCompteur++;
        }
    }
}
