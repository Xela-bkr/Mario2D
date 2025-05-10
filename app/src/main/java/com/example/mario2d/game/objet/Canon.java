package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.displayWidth;
import static com.example.mario2d.game.loop.GameActivity.waitingLine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.game.personnage.BillBoum;

public class Canon extends BrownBloc{
    private boolean shoot, right;
    private int compteur;
    public Canon(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        shoot = false;
        compteur = 0;
        right = false;

        Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.canon);
        this.bitmap = Bitmap.createScaledBitmap(b, width, height, true);

    }
    public void update() {
        super.update();
        updateEtat();
        if (activated) {
            if (shoot) {
                dropEnnemy();
                shoot = false;
                compteur = 0;
            } else {
                if (compteur < 200) {
                    compteur ++;
                } else {
                    compteur = 0;
                    shoot = true;
                }
            }
        } else {
            if(getX() < displayWidth*3/2) {
                activated = true;
                right = false;
            }
        }
    }
    private void dropEnnemy() {
        int billboumX = getX();
        int billboumWidth = getWidth();
        int billboumHeight = getHeight()/3;
        int billboumY = getY() + (getHeight()/2 - billboumHeight)/2;
        BillBoum bb = new BillBoum(context, "billboum", billboumX, billboumY, billboumWidth, billboumHeight, right);
        waitingLine.add(bb);
    }
    public void setRight(boolean r) {this.right = r;}
    private void updateEtat() {
        if (getX() + getWidth() > - displayWidth*3/2) {
            right = true;
            activated = true;
        } else if (getX() < displayWidth*3/2) {
            activated = true;
            right = false;
        }
        if(getX() + getWidth()< displayWidth/2) {
            right = true ;
        } else {
            right = false;
        }
        if (getX() < - displayWidth*3/2) {
            activated = false;
        }
    }
}
