package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;

public class FleurFeu extends Item{
    public FleurFeu(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        isPickable = false;
        isPicked = false;
        activated = false;
        gravity = false;
    }
    @Override
    public void update() {
        if (activated) {
            if(!isPickable) {
                transition();
            } else {
                gravity = true;
                boolean[] tab = player.detectCollision(this);
                if(tab[0] || tab[1] || tab[2] || tab[3]) {
                    player.setSkin();
                    player.setShotCount(14);
                    setActivated(false);
                    isPicked = true;
                }
            }
        }
    }
    public void transition() {
        if (getY() >= initY - getHeight() * 2) {
            translateY(-4);
        } else {
            isPickable = true;
        }
    }
}
