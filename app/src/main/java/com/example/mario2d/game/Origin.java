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
public abstract class Origin {
    /**
     * SpriteBank : permet d'associer chaque nom d'image (string) à sa resource R.drawable.*
     */
    protected HashMap<String, Integer> spriteBank = new HashMap<String, Integer>();
    protected int x, y, width, height, initWidth, initHeight, initY, initX, minWidth, minHeight;
    protected Bitmap bitmap;
    protected String name;
    protected Context context;
    protected boolean activated;

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
        this.minHeight = 1;
        this.minWidth = 1;
        this.context = context;
        this.activated = true;
        this.initSpriteBank();
        this.initX = x;
        this.initY = y;
        this.initWidth = width;
        this.initHeight = height;
        //setBitmaps();
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
            this.bitmap = Bitmap.createScaledBitmap(bit, Math.max(1, getWidth()), Math.max(1, getHeight()), true);
        }
        else{
            Bitmap bit = BitmapFactory.decodeResource(context.getResources(), spriteBank.get("bloc"));
            this.bitmap = Bitmap.createScaledBitmap(bit, getWidth(), getHeight(), true);
        }
    }
    public void setBitmap(Bitmap bitmap, String key){
        if(spriteBank.get(key)!=null){
            Bitmap bit = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(key));
            bitmap = Bitmap.createScaledBitmap(bit, getWidth(), getHeight(), true);
        }
        else{
            Bitmap bit = BitmapFactory.decodeResource(context.getResources(), spriteBank.get("bloc"));
            bitmap = Bitmap.createScaledBitmap(bit, getWidth(), getHeight(), true);
        }
    }
    public void setBitmap(Bitmap b){this.bitmap = b;}
    /**
     * @param context
     */
    public void setContext(Context context){this.context = context;}
    /**
     * @param name
     */
    public void setName(String name){this.name = name;}
    public void setActivated(boolean b){this.activated = b;}
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
    public boolean getActivated(){return this.activated;}
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
        spriteBank.put("mario_mort_gauche", R.drawable.mario_mort_gauche);
        spriteBank.put("mario_mort_droite", R.drawable.mario_mort_droite);
        spriteBank.put("mario_victoire", R.drawable.mario_victoire);

        spriteBank.put("mario_feu_arret_droite", R.drawable.mario_feu_arret_droite);
        spriteBank.put("mario_feu_arret_gauche", R.drawable.mario_feu_arret_gauche);
        spriteBank.put("mario_feu_marche_droite", R.drawable.mario_feu_marche_droite);
        spriteBank.put("mario_feu_marche_gauche", R.drawable.mario_feu_marche_gauche);
        spriteBank.put("mario_feu_saute_droite", R.drawable.mario_feu_saute_droite);
        spriteBank.put("mario_feu_saute_gauche", R.drawable.mario_feu_saute_gauche);
        spriteBank.put("mario_feu_lance_droite_1", R.drawable.mario_feu_lance_droite_1);
        spriteBank.put("mario_feu_lance_droite_2", R.drawable.mario_feu_lance_droite_2);
        spriteBank.put("mario_feu_lance_gauche_1", R.drawable.mario_feu_lance_gauche_1);
        spriteBank.put("mario_feu_lance_gauche_2", R.drawable.mario_feu_lance_gauche_2);
        spriteBank.put("mario_feu_victoire", R.drawable.mario_feu_victoire);

        //Luigi
        //TODO Add dead luigi resource when time 'll come
        spriteBank.put("luigi_arret_droite", R.drawable.luigi_arret_droite);
        spriteBank.put("luigi_arret_gauche", R.drawable.luigi_arret_gauche);
        spriteBank.put("luigi_marche_droite", R.drawable.luigi_marche_droite);
        spriteBank.put("luigi_marche_gauche", R.drawable.luigi_marche_gauche);
        spriteBank.put("luigi_saute_droite", R.drawable.luigi_saute_droite);
        spriteBank.put("luigi_saute_gauche", R.drawable.luigi_saute_gauche);
        spriteBank.put("luigi_mort_gauche", R.drawable.luigi_mort_gauche);
        spriteBank.put("luigi_mort_droite", R.drawable.luigi_mort_droite);
        spriteBank.put("luigi_victoire", R.drawable.luigi_victoire);

        spriteBank.put("luigi_feu_arret_droite", R.drawable.luigi_feu_arret_droite);
        spriteBank.put("luigi_feu_arret_gauche", R.drawable.luigi_feu_arret_gauche);
        spriteBank.put("luigi_feu_marche_droite", R.drawable.luigi_feu_marche_droite);
        spriteBank.put("luigi_feu_marche_gauche", R.drawable.luigi_feu_marche_gauche);
        spriteBank.put("luigi_feu_saute_droite", R.drawable.luigi_feu_saute_droite);
        spriteBank.put("luigi_feu_saute_gauche", R.drawable.luigi_feu_saute_gauche);
        spriteBank.put("luigi_feu_lance_droite_1", R.drawable.luigi_feu_lance_droite_1);
        spriteBank.put("luigi_feu_lance_droite_2", R.drawable.luigi_feu_lance_droite_2);
        spriteBank.put("luigi_feu_lance_gauche_1", R.drawable.luigi_feu_lance_gauche_1);
        spriteBank.put("luigi_feu_lance_gauche_2", R.drawable.luigi_feu_lance_gauche_2);
        spriteBank.put("luigi_feu_victoire", R.drawable.luigi_feu_victoire);

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
        spriteBank.put("greybrick", R.drawable.greybrick);
        spriteBank.put("nuage", R.drawable.nuage);
        spriteBank.put("nuageplatform", R.drawable.nuageplatform);
        spriteBank.put("redbrick", R.drawable.redbrick);
        spriteBank.put("yellowbrick", R.drawable.yellowbrick);
        spriteBank.put("champignon", R.drawable.champignon);
        spriteBank.put("etoile", R.drawable.etoile);
        spriteBank.put("blocvide", R.drawable.blocvide);
        spriteBank.put("piece_jaune_face", R.drawable.piece_jaune_face);
        spriteBank.put("piece_jaune_tourne_1", R.drawable.piece_jaune_tourne_1);
        spriteBank.put("piece_jaune_tourne_2", R.drawable.piece_jaune_tourne_2);
        spriteBank.put("piece_jaune_tourne_3", R.drawable.piece_jaune_tourne_3);
        spriteBank.put("bloc1", R.drawable.bloc1);
        spriteBank.put("bloc2", R.drawable.bloc2);
        spriteBank.put("hardbloc", R.drawable.hardbloc);
        spriteBank.put("champi_platforme", R.drawable.champi_platforme);
        spriteBank.put("colline_grande", R.drawable.colline_grande);
        spriteBank.put("colline_petite", R.drawable.colline_petite);
        spriteBank.put("pillier_terre", R.drawable.pillier_terre);
        spriteBank.put("pillier_champi_platforme", R.drawable.pillier_champi_platforme);
        spriteBank.put("herbe_platforme", R.drawable.herbe_platforme);
        spriteBank.put("hill2", R.drawable.hill2);
        spriteBank.put("hills1", R.drawable.hills1);
        spriteBank.put("nuageplatform2", R.drawable.nuageplatform2);
        spriteBank.put("forest_platform", R.drawable.forest_platform);
        spriteBank.put("bloc_jaune", R.drawable.bloc_jaune);
        /*
        spriteBank.put("", R.drawable.);
        spriteBank.put("", R.drawable.);*/

        // Ennemies
        spriteBank.put("goomba_marche_1", R.drawable.goomba_marche_1);
        spriteBank.put("goomba_marche_2", R.drawable.goomba_marche_2);
        spriteBank.put("goomba_mort", R.drawable.goomba_mort);
        spriteBank.put("greenkoopa_arret_droite", R.drawable.greenkoopa_arret_droite);
        spriteBank.put("greenkoopa_arret_gauche", R.drawable.greenkoopa_arret_gauche);
        spriteBank.put("greenkoopa_marche_droite", R.drawable.greenkoopa_marche_droite);
        spriteBank.put("greenkoopa_marche_gauche", R.drawable.greenkoopa_marche_gauche);
        spriteBank.put("greenkoopa_carapace_face", R.drawable.greenkoopa_carapace_face);
        spriteBank.put("greenkoopa_carapace_tourne_1", R.drawable.greenkoopa_carapace_tourne_1);
        spriteBank.put("greenkoopa_carapace_tourne_2", R.drawable.greenkoopa_carapace_tourne_2);
        spriteBank.put("greenkoopa_carapace_tourne_3", R.drawable.greenkoopa_carapace_tourne_3);
        spriteBank.put("greenparakoopa_down_droite", R.drawable.greenparakoopa_down_droite);
        spriteBank.put("greenparakoopa_down_gauche", R.drawable.greenparakoopa_down_gauche);
        spriteBank.put("greenparakoopa_up_droite", R.drawable.greenparakoopa_up_droite);
        spriteBank.put("greenparakoopa_up_gauche", R.drawable.greenparakoopa_up_gauche);

        spriteBank.put("redkoopa_arret_droite", R.drawable.redkoopa_arret_droite);
        spriteBank.put("redkoopa_arret_gauche", R.drawable.redkoopa_arret_gauche);
        spriteBank.put("redkoopa_marche_droite", R.drawable.redkoopa_marche_droite);
        spriteBank.put("redkoopa_marche_gauche", R.drawable.redkoopa_marche_gauche);
        spriteBank.put("redkoopa_carapace_face", R.drawable.redkoopa_carapace_face);
        spriteBank.put("redkoopa_carapace_tourne_1", R.drawable.redkoopa_carapace_tourne_1);
        spriteBank.put("redkoopa_carapace_tourne_2", R.drawable.redkoopa_carapace_tourne_2);
        spriteBank.put("redkoopa_carapace_tourne_3", R.drawable.redkoopa_carapace_tourne_3);
        spriteBank.put("redparakoopa_down_droite", R.drawable.redparakoopa_down_droite);
        spriteBank.put("redparakoopa_down_gauche", R.drawable.redparakoopa_down_gauche);
        spriteBank.put("redparakoopa_up_droite", R.drawable.redparakoopa_up_droite);
        spriteBank.put("redparakoopa_up_gauche", R.drawable.redparakoopa_up_gauche);

        spriteBank.put("boo_arret_droite", R.drawable.boo_arret_droite);
        spriteBank.put("boo_arret_gauche", R.drawable.boo_arret_gauche);
        spriteBank.put("boo_cache_gauche", R.drawable.boo_cache_gauche);
        spriteBank.put("boo_cache_droite", R.drawable.boo_cache_droite);
        spriteBank.put("skelerex_arret_droite", R.drawable.skelerex_arret_droite);
        spriteBank.put("skelerex_arret_gauche", R.drawable.skelerex_arret_gauche);
        spriteBank.put("skelerex_marche_droite", R.drawable.skelerex_marche_droite);
        spriteBank.put("skelerex_marche_gauche", R.drawable.skelerex_marche_gauche);
        spriteBank.put("skelerex_demi_mort_droite", R.drawable.skelerex_demi_mort_droite);
        spriteBank.put("skelerex_demi_mort_gauche", R.drawable.skelerex_demi_mort_gauche);
        spriteBank.put("skelerex_mort_droite", R.drawable.skelerex_mort_droite);
        spriteBank.put("skelerex_mort_gauche", R.drawable.skelerex_mort_gauche);

        spriteBank.put("plante_pirhana_1", R.drawable.plante_pirhana_1);
        spriteBank.put("plante_pirhana_2", R.drawable.plante_pirhana_2);
        spriteBank.put("longreenpipe", R.drawable.longreenpipe);

        spriteBank.put("window", R.drawable.window);
        spriteBank.put("pillier_pierre", R.drawable.pillier_pierre);
        spriteBank.put("greybrick2", R.drawable.greybrick2);
        spriteBank.put("platforme", R.drawable.platforme);

        spriteBank.put("podoboo_down_1", R.drawable.podoboo_down_1);
        spriteBank.put("podoboo_down_2", R.drawable.podoboo_down_2);
        spriteBank.put("podoboo_down_3", R.drawable.podoboo_down_3);
        spriteBank.put("podoboo_up_1", R.drawable.podoboo_up_1);
        spriteBank.put("podoboo_up_2", R.drawable.podoboo_up_2);
        spriteBank.put("podoboo_up_3", R.drawable.podoboo_up_3);

        spriteBank.put("boule_feu_1", R.drawable.boule_feu_1);
        spriteBank.put("boule_feu_2", R.drawable.boule_feu_2);
        spriteBank.put("boule_feu_3", R.drawable.boule_feu_3);

        spriteBank.put("fleurfeu", R.drawable.fleurfeu);
        spriteBank.put("tile1_1", R.drawable.tile1_1);
        spriteBank.put("tile1_2", R.drawable.tile1_2);

        spriteBank.put("volcan_decor", R.drawable.volcan_decor);
        spriteBank.put("platforme_rouge", R.drawable.platforme_rouge);
        spriteBank.put("greybrick3", R.drawable.greybrick3);
        spriteBank.put("briquestriee", R.drawable.briquestriee);
        spriteBank.put("brique_jaune", R.drawable.brique_jaune);

        spriteBank.put("billboum_gauche_1", R.drawable.billboum_gauche_1);
        spriteBank.put("billboum_gauche_2", R.drawable.billboum_gauche_2);
        spriteBank.put("billboum_droite_1", R.drawable.billboum_droite_1);
        spriteBank.put("billboum_droite_2", R.drawable.billboum_droite_2);
        spriteBank.put("canon", R.drawable.canon);

        spriteBank.put("champi_platforme_jaune", R.drawable.champi_platforme_jaune);

        spriteBank.put("magikoopa_lance_droite", R.drawable.magikoopa_lance_droite);
        spriteBank.put("magikoopa_lance_gauche", R.drawable.magikoopa_lance_gauche);
        spriteBank.put("magikoopa_prepare_droite_1", R.drawable.magikoopa_prepare_droite_1);
        spriteBank.put("magikoopa_prepare_droite_2", R.drawable.magikoopa_prepare_droite_2);
        spriteBank.put("magikoopa_prepare_droite_3", R.drawable.magikoopa_prepare_droite_3);
        spriteBank.put("magikoopa_prepare_gauche_1", R.drawable.magikoopa_prepare_gauche_1);
        spriteBank.put("magikoopa_prepare_gauche_2", R.drawable.magikoopa_prepare_gauche_2);
        spriteBank.put("magikoopa_prepare_gauche_3", R.drawable.magikoopa_prepare_gauche_3);
        spriteBank.put("magikoopa_wait_droite", R.drawable.magikoopa_wait_droite);
        spriteBank.put("magikoopa_wait_gauche", R.drawable.magikoopa_wait_gauche);
        spriteBank.put("magiboule_1", R.drawable.magiboule_1);
        spriteBank.put("magiboule_2", R.drawable.magiboule_2);
        spriteBank.put("magiboule_3", R.drawable.magiboule_3);
        spriteBank.put("magiboule_4", R.drawable.magiboule_4);
        spriteBank.put("magikoopa_mort_gauche", R.drawable.magikoopa_mort_gauche);
        spriteBank.put("magikoopa_mort_droite", R.drawable.magikoopa_mort_droite);


        spriteBank.put("brique_marron", R.drawable.brique_marron);
        spriteBank.put("pyramides", R.drawable.pyramides);
        spriteBank.put("sable", R.drawable.sable);

    }
    public void translateX(int dx){this.setX(this.getX()+dx);}
    public void translateY(int dy){this.setY(this.getY()+dy);}
    public void shrinkWidth(int i){setWidth(getWidth() - i);}
    public void increaseWidth(int i){setWidth(getWidth() + i);}
    public void setBitmaps(){}
    public void update(){}
    public void reset() {

    }
}
