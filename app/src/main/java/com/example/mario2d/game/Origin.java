package com.example.mario2d.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;

import java.util.HashMap;

/**
 * Classe contenant les attributs communs à tous les composants du jeu :
 * coordonnées, dimentions, contexte, nom, image (bitmap) et la spriteBank
 */
public class Origin {
    /**
     * SpriteBank : permet d'associer chaque nom d'image (string) à sa resource R.drawable.*
     */
    protected HashMap<String, Integer> spriteBank = new HashMap<String, Integer>();
    protected int x, y, width, height;
    protected Bitmap bitmap;
    protected String name;
    protected Context context;

    /**
     * Constructeur
     * @param context
     * @param name
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Origin(Context context, String name, int x, int y, int width, int height){
        this.name = name; this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.context = context;
        this.initSpriteBank();
        System.out.println("SpriteBank length : " + spriteBank.size());
    }
    /**
     * @param x
     */
    //----SETTERS----//
    public void setX(int x){this.x = x;}
    /**
     * @param y
     */
    public void setY(int y){this.y = y;}
    /**
     * @param width
     */
    public void setWidth(int width){this.width = width;}
    /**
     * @param height
     */
    public void setHeight(int height){this.height = height;}

    /**
     * Défini l'attribut bitmap.
     * @param key
     * ce paramètre est la clé du spriteBank donnat accès à la ressource associée
     */
    public void setBitmap(String key){
        if(spriteBank.get(key)!=null){
            Bitmap bit = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key));
            this.bitmap = Bitmap.createScaledBitmap(bit, getWidth(), getHeight(), true);
        }
        else{
            Bitmap bit = BitmapFactory.decodeResource(context.getResources(), spriteBank.get("bloc"));
            this.bitmap = Bitmap.createScaledBitmap(bit, getWidth(), getHeight(), true);
        }
    }
    /**
     * @param context
     */
    public void setContext(Context context){this.context = context;}
    /**
     * @param name
     */
    public void setName(String name){this.name = name;}
    //----GETTERS----//
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public String getName(){return this.name;}
    public HashMap<String, Integer> getSpriteBank() {return spriteBank;}
    public Bitmap getBitmap(){
        return this.bitmap;
    }
    public void addResourceToSpriteBank(String key, int resource){spriteBank.put(key, resource);}

    /**
     * Initialise l'ensemble des données du spriteBank.
     * C'est cette fonction qu'il faut modifier de préférence pour ajouter/supprimer/modifier une ressource
     */
    public void initSpriteBank(){
        // Mario
        //TODO Add dead mario resource when time 'll come
        spriteBank.put("mario_arret_droite", R.drawable.mario_arret_droite);
        spriteBank.put("mario_arret_gauche", R.drawable.mario_arret_gauche);
        spriteBank.put("mario_marche_droite", R.drawable.mario_marche_droite);
        spriteBank.put("mario_marche_gauche", R.drawable.mario_marche_gauche);
        spriteBank.put("mario_saute_droite", R.drawable.mario_saute_droite);
        spriteBank.put("mario_saute_gauche", R.drawable.mario_saute_gauche);

        //Luigi
        //TODO Add dead luigi resource when time 'll come
        spriteBank.put("luigi_arret_droite", R.drawable.luigi_arret_droite);
        spriteBank.put("luigi_arret_gauche", R.drawable.luigi_arret_gauche);
        spriteBank.put("luigi_marche_droite", R.drawable.luigi_marche_droite);
        spriteBank.put("luigi_marche_gauche", R.drawable.luigi_marche_gauche);
        spriteBank.put("luigi_saute_droite", R.drawable.luigi_saute_droite);
        spriteBank.put("luigi_saute_gauche", R.drawable.luigi_saute_gauche);

        //Items
        spriteBank.put("bloc", R.drawable.bloc);
        spriteBank.put("brownbloc", R.drawable.brownbloc);
        spriteBank.put("greenbloc", R.drawable.greenbloc);
        spriteBank.put("goldenbloc", R.drawable.goldenbloc);
        spriteBank.put("darkbloc", R.drawable.darkbloc);
        spriteBank.put("castle", R.drawable.castle);
        spriteBank.put("greencastle", R.drawable.greencastle);
        spriteBank.put("darkbrick", R.drawable.darkbrick);
        spriteBank.put("greenbrick", R.drawable.greenbrick);
        spriteBank.put("greenpipe", R.drawable.greenpipe);
        spriteBank.put("nuage", R.drawable.nuage);
        spriteBank.put("nuageplatform", R.drawable.nuageplatform);
        spriteBank.put("piece", R.drawable.piece);
        spriteBank.put("redbrick", R.drawable.redbrick);
        spriteBank.put("yellowbrick", R.drawable.yellowbrick);
    }
    public void translateX(int dx){this.setX(this.getX()+dx);}
    public void translateY(int dy){this.setY(this.getY()+dy);}
}
