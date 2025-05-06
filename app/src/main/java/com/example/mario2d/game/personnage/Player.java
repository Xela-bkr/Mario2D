package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.displayHeight;
import static com.example.mario2d.game.loop.GameActivity.dx;
import static com.example.mario2d.game.loop.GameActivity.ennemies;
import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mario2d.R;
import com.example.mario2d.game.objet.Item;
import com.example.mario2d.tool.Audio;

public class Player extends Personnage{
    private float agrCoeff = 1.86f;
    private int piecesCount, compteurPetitSaut, shotCount;
    private String skin;
    private Audio star;
    private int initialWidth, initialHeight;
    public boolean fire, droppingFire;
    private Bitmap arret_droite, arret_gauche, marche_droite, marche_gauche, saute_droite, saute_gauche, mort_droite, mort_gauche,
            victoire, feu_droite_1, feu_droite_2, feu_gauche_1, feu_gauche_2;
    private boolean smallJump;
    public Player(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.initialHeight = height; this.initialWidth = width;
        this.piecesCount = 0;
        this.life = 2;
        this.frequenceMarche = 8;
        this.gravityConstant = 1;
        this.jumpImpulse = 18;
        this.compteurSaut = 0;
        this.isResting = false;
        this.restCompteur = 0;
        this.smallJump = false;
        compteurPetitSaut = 0;
        shotCount = 0;
        droppingFire = false;
        if(!name.equals("mario") && !name.equals("luigi")){name="mario";}
        collisionMatrix.put("ennemy", new boolean[]{false, false, false, false});
        skin = "";
        fire = false;
        setBitmaps();
        this.bitmap = arret_droite;
    }
    @Override
    public void decreaseLife(){
        if (fire) {
            fire = false;
            skin = "";
            setBitmaps();
        } else if (life > 1) {
            isResting = true;
            life --;
        } else {
            life --;
            dead();
        }
        /*this.life --;
        isResting = true;
        if (this.life == 1){
            int newWidth = getWidth() / 2;
            int newHeight = (int) (agrCoeff*newWidth);
            setWidth(newWidth);
            setHeight(newHeight);
            setBitmaps();
            setY(getY()-getWidth());
            if(isWalking){
                if(isRight){this.bitmap = marche_droite;}
                else{this.bitmap = marche_gauche;}
            }
            else{
                if(isRight){this.bitmap = arret_droite;}
                else{this.bitmap = arret_gauche;}
            }
        }
        if(this.life == 0){dead();}*/
    }
    @Override
    public void increaseLife(){
        if (life == 1) {
            this.life ++;
            setWidth(initialWidth);
            setHeight(initialHeight);
            setBitmaps();
            setY(getY()-initialHeight/2);
            if(!isWalking){
                if(isRight){this.bitmap = arret_droite;}
                else{this.bitmap = arret_gauche;}
            }
        }
    }
    @Override
    public void walk(int frequence){
        if(compteurMarche < frequence){
            if(isRight){
                if (droppingFire) {this.bitmap = feu_droite_1;}
                else {this.bitmap = marche_droite;}
            }
            else{
                if (droppingFire) {this.bitmap = feu_gauche_1;}
                else {this.bitmap = marche_gauche;}
            }
        }
        else if(compteurMarche >= frequence && compteurMarche < 2*frequence){
            if (isRight){
                if(droppingFire) { this.bitmap = feu_droite_2;}
                else {this.bitmap = arret_droite;}
            }
            else{
                if(droppingFire) {this.bitmap = feu_gauche_2;}
                else {this.bitmap = arret_gauche;}
            }
        }
        compteurMarche ++;

        if(compteurMarche >= 2*frequence){
            if(droppingFire) {droppingFire = false;}
            compteurMarche = 0;
        }
    }
    public void decreasePiece(){this.piecesCount --;}
    public int getPiecesCount(){return this.piecesCount;}
    public void setBitmaps(){

        String key = name;
        if(skin.equals("_feu")){
            key += "_feu";
        }
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_arret_droite"));
        this.arret_droite= Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_arret_gauche"));
        this.arret_gauche= Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_marche_droite"));
        this.marche_droite = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_marche_gauche"));
        this.marche_gauche = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);

        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_saute_droite"));
        this.saute_droite = Bitmap.createScaledBitmap(b5, getWidth(), getHeight(), true);

        Bitmap b6 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_saute_gauche"));
        this.saute_gauche = Bitmap.createScaledBitmap(b6, getWidth(), getHeight(), true);

        Bitmap b7 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort_droite"));
        this.mort_droite = Bitmap.createScaledBitmap(b7, getWidth(), getHeight(), true);

        Bitmap b8 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort_gauche"));
        this.mort_gauche = Bitmap.createScaledBitmap(b8, getWidth(), getHeight(), true);

        Bitmap b9 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key+"_victoire"));
        victoire = Bitmap.createScaledBitmap(b9, getWidth(), getHeight(), true);

        Bitmap b10 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_feu_lance_droite_1"));
        feu_droite_1 = Bitmap.createScaledBitmap(b10, getWidth(), getHeight(), true);

        Bitmap b11 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_feu_lance_gauche_1"));
        feu_gauche_1 = Bitmap.createScaledBitmap(b11, getWidth(), getHeight(), true);

        Bitmap b12 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_feu_lance_droite_2"));
        feu_droite_2 = Bitmap.createScaledBitmap(b12, getWidth(), getHeight(), true);

        Bitmap b13 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_feu_lance_gauche_2"));
        feu_gauche_2 = Bitmap.createScaledBitmap(b13, getWidth(), getHeight(), true);

    }
    @Override
    public void invincible(){
        if(this.invincibleCompteur > 0){
            if(!this.isInvincible)
            {
                setInvincible(true);
                star = new Audio(context, R.raw.star);
                star.setLoop(true);
                star.play();
            }
            /*if(star == null){
                star = new Audio(context, R.raw.staritem);
                star.setLoop(true);
            }*/

            invincibleCompteur --;
        }
        else{
            if(star != null)
            {
                star.stop();
            }
            this.setInvincible(false);
        }
    }
    @Override
    public void rest(){
        isResting = true;
        if(restCompteur < 100){
            if (!isResting) setResting(true);

        }
        else{
            restCompteur = 0;
            this.isResting = false;
        }
        restCompteur ++;
    }
    @Override
    public void update(){

        //updateEnnemyCollision();
        updateObjetCollision();

        if (shotCount <= 0) {
            if (fire) {
                fire = false;
                skin = "";
                setBitmaps();
            }
        }
        if (smallJump) {
            jump2();
        }
        if (isWalking) {
            walk(frequenceMarche);
        }
        if (getY() + getHeight() <= 0) {
            isJumping = false;
        }
        if(isJumping) {
            if(jumpTime == 0){jumpTime = System.nanoTime();}
            jump();
        }
        if (isResting) {
            rest();
        }
        if (isInvincible) {
            invincible();
        }
        if (getY() > displayHeight + getWidth()*3) {
            dead();
        }
    }
    public void jump(){

        long ascentTime = 1_000_000_000/3;

        long currentTime = System.nanoTime();
        long deltaTime = currentTime - getJumpTime();

        if (deltaTime <= ascentTime && !collisionWithObject(2))
        {
            int dy = -(getJumpImpulse() - getGravityConstant()*getCompteurSaut());
            translateY(dy);
            this.bitmap = isRight ? saute_droite : saute_gauche;
            increaseCompteurSaut();
        } else {
            setJumping(false);
            setCompteurSaut(0);
            gravity = true;
            this.bitmap = isRight ? arret_droite : arret_gauche;
        }
    }
    @Override
    public void increasePieceCount()
    {
        Audio.playSound(context, R.raw.coin_3);
        this.piecesCount ++;
    }
    public void jump2(){
        if(!smallJump) {smallJump = true;}
        if (compteurPetitSaut < 20)
        {
            gravity = false;
            int dy = compteurPetitSaut - 20;
            translateY(dy);
            if(isWalking) {
                if(isRight) translateX(2);
                else translateX(-2);
            }
            compteurPetitSaut ++;
        }
        else
        {
            compteurPetitSaut = 0;
            smallJump = false;
            gravity = true;
        }
    }
    @Override
    public void dead(){
        isAlive = false;
        if(isRight){
            if(this.bitmap != mort_droite){this.bitmap = mort_droite;}
        }
        else{
            if(this.bitmap != mort_gauche){this.bitmap = mort_gauche;}
        }
    }
    public void win(){
        if(this.bitmap != victoire)
        {
            this.bitmap = victoire;
        }
    }
    @Deprecated
    private void updateEnnemyCollision()
    {
        for(Ennemy en : ennemies)
        {
            if (en.getActivated() && en.getAlive())
            {
                boolean[] tab = detectCollision(en, 5, -5);
                if (tab[0])
                {
                    if (en.getResting())
                    {
                        Audio.playSound(context, R.raw.kick_2);
                        en.invincible();
                    } else if (en.getInvincible())
                    {
                        recalibrerY(en);
                        if (en.getTopIsHurting()) {
                            if (!isResting)
                            {
                                decreaseLife();
                            }
                        }
                    } else {
                        Audio.playSound(context, R.raw.kick_2);
                        en.rest();
                    }
                    smallJump = true;
                } else if (tab[1] || tab[2] || tab[3]) {
                    if (en.getResting())
                    {
                        en.invincible();
                    } else {
                        if (!isResting) {
                            decreaseLife();
                        }
                    }
                }
            }
        }

    }
    private void updateObjetCollision()
    {
        if(collisionWithObject(2))
        {
            if(isJumping)
            {
                isJumping = false;
            }
            if(!gravity){gravity = true;}
        }
        if(collisionWithObject(1) || collisionWithObject(3)){
            if(isWalking){isWalking = false;}
        }
    }
    public void setSkin() {
        skin = "_feu";
        setBitmaps();
        fire = true;
    }
    public void dropFireBowl(){
        droppingFire = true;
        shotCount --;
        int fbWidth = getWidth();
        int fbHeight = fbWidth;
        int fbX = getX() + getWidth() + 3*dx;
        if (!isRight) {
            fbX = getX() - getWidth() - 3*dx;
        }
        int fbY = getY() + getHeight()/2 - (getHeight()-fbHeight)/2;
        FireBowl fb = new FireBowl(context, "boule_feu",fbX, fbY, fbWidth, fbHeight );
        fb.setRight(isRight);
        waitingLine.add(fb);
    }
    public void setShotCount(int count) {this.shotCount = count;}
}
