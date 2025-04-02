package com.personnage;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

public class personnage extends View {

    //****VARIABLES****//
    public int x, y, largeur, hauteur, marcheCompteur;
    public Boolean isRight, isJumping;
    public String name, resourceName;

    //****METHODES****//
    public personnage(Context context, int x, int y, int largeur, int hauteur) {
        super(context);

        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.isRight = true;
        this.isJumping = false;
        this.marcheCompteur = 0;
        this.resourceName=name+"_arret_droite";
    }
    public void marcher(){
        if(name.isEmpty()){return;}
    }
    public void sauter(){}

    //****GETTERS****//

    //****SETTERS****//
}
