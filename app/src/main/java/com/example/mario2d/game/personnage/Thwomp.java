package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.displayWidth;
import static com.example.mario2d.game.loop.GameActivity.persos;
import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.tool.Audio;

public class Thwomp extends Ennemy{
    private boolean isUp, upAuthor, isDown;
    private Bitmap up, down;
    private int deltaY;
    public Thwomp(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isUp = false;
        isDown = true;
        deltaY = 0;
        isInvincible = true;
        isResting = false;
        isWalking = false;
        gravity = false;
        compteurSaut = 0;
        gravityConstant=15;
        upAuthor = true;
        activated = false;
        setCollisionMatrixToFalse();
        setBitmaps();
    }
    @Override
    public void setBitmaps() {
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.thwomp_up);
        up = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.thwomp_down);
        down = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
    }
    @Override
    public void invincible(){
        if (!isInvincible) {
            isInvincible = true;
        }
        if (isUp) {
            if(this.bitmap != up){this.bitmap = up;}
            translateY(-7);
        }
        else if(isDown){
            if (this.bitmap != down) {this.bitmap = down;}
            if (!gravity) {gravity = true;}
        }
        if (collisionWithObject(0)) {

            if(compteurSaut < 40){
                compteurSaut ++;
            }
            else{
                compteurSaut = 0;
                isUp = true;
                isDown = false;
                gravity = false;
            }
        }
        if(getY() <= 0){
            isUp = false;
            if(compteurSaut < 60){
                compteurSaut ++;
            }
            else{
                compteurSaut = 0;
                isDown = true;
                isUp = false;
                gravity = true;
            }
        }
    }
    public boolean getUp(){return this.isUp;}
    public void setIsUp(boolean isUp){this.isUp = isUp;}
    public void setUpAuthor(boolean upAuthor){this.upAuthor = upAuthor;}
    @Override
    public void update(){
        if(activated){
            boolean[] tab1 = player.detectCollision(this, 0, (int) (getWidth()*0.1), (int) (getHeight()*0.1), (int) (getWidth()*0.1));
            if(tab1[0]){
                player.recalibrerY(this);
            }
            else if(tab1[2]){
                if(!player.isResting){
                    player.decreaseLife();
                }
            } else if (tab1[1]) {player.addCollisionValue("brownbloc", 1, true);}
            else if (tab1[3]) {player.addCollisionValue("brownbloc", 3, true);}
            invincible();
            if (!onScreen()) {
                activated = false;
                compteurSaut = 0;
                gravity = false;
                setY(initY);
            }
        } else if (onScreen()) {
            if (onPlayerPlan()) {
                activated = true;
                gravity = true;
            }
        }
    }
    private boolean onScreen() {
        boolean droite = getX() < displayWidth;
        boolean gauche = getX() + getWidth() > 0;
        return droite && gauche;
    }
    private boolean onPlayerPlan() {
        if(getX() < player.getX() + player.getWidth() && getX() + getWidth() >= player.getX()) {
            setActivated(true);
            return true;
        }
        return false;
    }
}
