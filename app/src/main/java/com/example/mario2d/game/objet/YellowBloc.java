package com.example.mario2d.game.objet;

import static com.example.mario2d.game.loop.GameActivity.etoiles;
import static com.example.mario2d.game.loop.GameActivity.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.game.loop.GameActivity;
import com.example.mario2d.game.personnage.Personnage;
import com.example.mario2d.tool.Audio;

public class YellowBloc extends Objet{
    private Champignon champi;
    private Piece piece;
    private Etoile etoile;
    private FleurFeu fleur;
    private boolean hasBeenUsed;
    private int compteurAnimation, frequenceAnimation;
    private Bitmap bloc,bloc1, bloc2, staticBloc;
    public YellowBloc(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.hasBeenUsed = false;
        this.compteurAnimation = 0;
        this.frequenceAnimation = 20;
        setBitmaps();
    }
    public boolean getUsedState(){return this.hasBeenUsed;}
    public Champignon getChampi(){return this.champi;}
    public void setUsed(boolean u){this.hasBeenUsed = u;}

    public void setChampi(Item item){this.champi = champi;}

    public void update(){

        if (activated) {
            boolean[] tab = player.detectCollision(this);

            if(tab[0]){player.recalibrerY(this);}
            else if(tab[2]){
                player.setY(getY()+getHeight());
                hasBeenUsed = true;
                if(player.getJumping()){player.setJumping(false);}
            }

            if(!hasBeenUsed){animer();}
            else{
                if(this.bitmap != staticBloc){this.bitmap = staticBloc;}
                if(champi!=null){
                    Audio.playSound(context, R.raw.powerup_appears);
                    champi.setActivated(true);
                    champi = null;
                }
                if(piece!=null){
                    piece.setActivated(true);
                    piece.setIsTaken(true);
                    piece = null;
                }
                if(etoile != null){
                    Audio.playSound(context, R.raw.powerup_appears);
                    etoile.setActivated(true);
                    etoile = null;
                }
                if(fleur != null) {
                    fleur.setActivated(true);
                    fleur = null;
                }
            }
        }
    }
    @Override
    public void animer(){
        if(compteurAnimation < frequenceAnimation){
            if(this.bitmap != bloc){this.bitmap = bloc;}
        }
        else if(compteurAnimation >= frequenceAnimation && compteurAnimation < 2*frequenceAnimation){
            if(this.bitmap != bloc1){this.bitmap = bloc1;}
        }
        else {
            if(this.bitmap != bloc2){this.bitmap = bloc2;}
        }
        compteurAnimation ++;
        if(this.compteurAnimation >= 3*frequenceAnimation){this.compteurAnimation = 0;}
    }
    @Override
    public void setBitmaps(){
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get("bloc"));
        this.bloc = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);

        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get("bloc1"));
        this.bloc1 = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);

        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get("bloc2"));
        this.bloc2 = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);

        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get("blocvide"));
        this.staticBloc = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setChampi(Champignon champi) {
        this.champi = champi;
    }

    public Etoile getEtoile() {
        return etoile;
    }

    public void setEtoile(Etoile etoile) {
        this.etoile = etoile;
    }
    public void setFleurFeu(FleurFeu fleur) {
        this.fleur = fleur;
    }
}
