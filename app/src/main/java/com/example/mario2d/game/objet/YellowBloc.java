package com.example.mario2d.game.objet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class YellowBloc extends Objet{
    private Item item;
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
    public Item getItem(){return this.item;}
    public void setUsed(boolean u){this.hasBeenUsed = u;}
    public void setItem(Item item){this.item = item;}
    public void slideItem(int dx){item.translateY(dx);}
    public void setToStaticBloc(String blocKey){
        this.hasBeenUsed = true;
        this.setBitmap(blocKey);
    }
    public void update(){
        if(!hasBeenUsed){animer();}
        else{
            if(this.bitmap != staticBloc){this.bitmap = staticBloc;}
            if(item.getInMotion() && item.getY() > getY() - getWidth()){item.translateY(-2);}
            else if(!item.getIsCollected() && item.getActivated()){item.setIsPickabe(true);}
            if(item.getPickabe() && !item.getPicked()){item.animer();}
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
}
