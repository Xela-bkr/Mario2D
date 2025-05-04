package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;

import com.example.mario2d.game.loop.GameView;

public class Ennemy extends Personnage {
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
    private int deadWidth, deadHeight;
    protected boolean topIsHurting;
    public Ennemy(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        topIsHurting = false;
    }

    public int getDeadWidth() {
        return deadWidth;
    }

    public void setDeadWidth(int deadWidth) {
        this.deadWidth = deadWidth;
    }

    public int getDeadHeight() {
        return deadHeight;
    }

    public void setDeadHeight(int deadHeight) {
        this.deadHeight = deadHeight;
    }
    @Override
    public void update(){
        if(activated){
            boolean[] tab = player.detectCollision(this);
            if(tab[0]){
                if(player.getInvincible()){
                    dead();
                }
                decreaseLife();
            }
            if(tab[1] || tab[2] || tab[3]){
                if(player.getInvincible()){
                    dead();
                }
                else{
                    if(!player.getResting()){
                        player.decreaseLife();
                    }
                }
            }
            if(isResting){rest();}
            else if(isInvincible){invincible();}
            else if(isWalking){walk(frequenceMarche);}
            else{dead();}
        }
    }
    public void setTopIsHurting(boolean t)
    {
        this.topIsHurting = t;
    }
    public boolean getTopIsHurting()
    {
        return this.topIsHurting;
    }

}
