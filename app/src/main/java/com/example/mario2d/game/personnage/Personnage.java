package com.example.mario2d.game.personnage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;

import com.example.mario2d.R;
import com.example.mario2d.game.Origin;

import java.util.HashMap;

//TODO Add "item" Object attribute
//TODO Implement character's Skin
public class Personnage extends Origin{

    //----VARIABLES----//
    protected Boolean isJumping, isRight, isWalking;
    protected int compteurMarche;

    //----CONSTRUCTEUR----//
    /**
     * Constructor
     * @param context
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Personnage(Context context, String name, int x, int y, int width, int height){
        super(context, name, x, y, width, height);
        this.isJumping = false; this.isRight = false; this.isWalking = false;
        this.setBitmap(name+"_arret_droite");
    }
    //----SETTERS----//
    public void setCompteurMarche(int i){this.compteurMarche = i;}
    public void setWalkingSate(Boolean walkingState){this.isWalking = walkingState;}
    public void setJumpingState(Boolean jumpingState){this.isJumping = jumpingState;}

    /**
     *  setDirection
     * @param b -> Boolean
     * @implNote True = character is on the right
     * @implNote False = character is on the left
     */
    public void setDirection(Boolean b){this.isRight = b;}
    //----GETTERS----//
    public boolean getWalkState(){return this.isWalking;}
    public Boolean getJumping() {return isJumping;}
    public Boolean getRightState() {return isRight;}
    public Boolean getWalkingState() {return isWalking;}
    public int getCompteurMarche() {return compteurMarche;}

    //----METHODES----//
    public void addResourceToSpriteBank(String key, int resource){spriteBank.put(key, resource);}

    /**
     * Walk function : set the current bitmap attribute
     * @param frequence
     * @implNote the frequency is a value that determines how fast the character's bitmap is changing between two possibilities.
     */
    public void walk(int frequence){
        String key = this.getName();
        if(compteurMarche>=0 && compteurMarche<frequence){
            key+="_marche";
            if(isRight){key+="_droite";}
            else{key+="_gauche";}
        }
        if(compteurMarche>=frequence && compteurMarche<2*frequence){
            key+="_arret";
            if(isRight){key+="_droite";}
            else{key+="_gauche";}
        }
        if(compteurMarche>=2*frequence){setCompteurMarche(0);}

        if(spriteBank.get(key)!=null){
            setBitmap(key);
        }
    }
    public void jump(){
        String key = getName()+"_saute";
        if(isRight){key+="_droite";}
        else{key+="_gauche";}
        setBitmap(key);
    }
}
