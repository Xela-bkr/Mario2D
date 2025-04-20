package com.example.mario2d.game.personnage;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;

import com.example.mario2d.R;
import com.example.mario2d.game.Origin;

import java.util.HashMap;

/**
 * Classe Personnage implémentant l'ensemble des attibuts dont les joueur et ennemis ont besoin
 * @see Player
 */

//TODO Add "item" Object attribute
//TODO Implement character's Skin
public class Personnage extends Origin{

    //----VARIABLES----//
    /**
     * Variable isJumping (bool) pour déternimer si le personnage st en train de sauter ou non
     * Variable isRight (bool) pour déterminer si le personnage est orienté à droite ou non
     * Variable isWalking (bool) pour déternimer si le personnage est en train de marcher ou non TODO add reference to collision management
     */
    protected Boolean isJumping, isRight, isWalking;
    /**
     * Variable compteurMarche : utile à la fonction de marche du personnage
     */
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
        this.isJumping = false; this.isRight = true; this.isWalking = false;
        this.setBitmap(name+"_arret_droite");
    }
    //----SETTERS----//
    public void setCompteurMarche(int i){this.compteurMarche = i;}
    public void setWalkingSate(Boolean walkingState){this.isWalking = walkingState;}
    public void setJumpingState(Boolean jumpingState){this.isJumping = jumpingState;}

    /**
     *  setDirection
     * @param b -> Boolean
     * True = character is on the right
     * False = character is on the left
     */
    public void setDirectionRight(Boolean b){this.isRight = b;}
    //----GETTERS----//
    public boolean getWalkState(){return this.isWalking;}
    public Boolean getJumping() {return isJumping;}
    public Boolean getRightState() {return isRight;}
    public Boolean getWalkingState() {return isWalking;}
    public int getCompteurMarche() {return compteurMarche;}

    //----METHODES----//
    /**
     * Fonction walk() permet de faire marcher le personage en modifiant son image (attribut)
     * @see Origin
     * @param frequence
     * La fréquence détermine à quelle vitesse les images simulant la marche alternent
     * Plus la fréquence est haute, plus la marche sera lente.
     */
    public void walk(int frequence){
        if(name==null){name="mario";}
        String key = this.getName();

        if(compteurMarche>=0 && compteurMarche<frequence){
            key+="_marche";
            if(isRight){key+="_droite";}
            else{key+="_gauche";}
        }
        else if(compteurMarche>=frequence && compteurMarche<=2*frequence){
            key+="_arret";
            if(isRight){key+="_droite";}
            else{key+="_gauche";}
        }
        if(compteurMarche>=2*frequence){setCompteurMarche(0);}
        if(spriteBank.get(key)!=null){
            setBitmap(key);
        } else{setBitmap("bloc");}
        compteurMarche++;
    }

    /**
     * Fonction jump modifiant l'image (attribut) du personnage
     */
    public void jump(){
        String key = getName()+"_saute";
        if(isRight){key+="_droite";}
        else{key+="_gauche";}
        setBitmap(key);
    }
}
