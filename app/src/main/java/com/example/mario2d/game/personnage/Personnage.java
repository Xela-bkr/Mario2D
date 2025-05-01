package com.example.mario2d.game.personnage;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;

import com.example.mario2d.R;
import com.example.mario2d.game.Origin;
import com.example.mario2d.game.objet.Floor;
import com.example.mario2d.game.objet.Objet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import kotlin.RequiresOptIn;

/**
 * Classe Personnage implémentant l'ensemble des attibuts dont les joueur et ennemis ont besoin
 */

//TODO Implement character's Skin
public abstract class Personnage extends Origin{

    //----VARIABLES----//
    /**
     * Variable isJumping (bool) pour déternimer si le personnage st en train de sauter ou non
     * Variable isRight (bool) pour déterminer si le personnage est orienté à droite ou non
     * Variable isWalking (bool) pour déternimer si le personnage est en train de marcher ou non
     */
    protected Boolean isJumping, isRight, isWalking, isAlive, isInvincible, isResting, isEnnemy, gravity, gravityFall;
    /**
     * Variable compteurMarche : utile à la fonction de marche du personnage
     */
    protected int compteurMarche, gravityConstant, jumpImpulse, compteurSaut, invincibleCompteur, restCompteur, life, frequenceMarche, deadCompteur, graviteCompteur;

    public Boolean getGravityFall() {
        return gravityFall;
    }

    public void setGravityFall(Boolean gravityFall) {
        this.gravityFall = gravityFall;
    }

    /**
     * Variable pour contrôler le temps de saut.
     */
    protected long jumpTime;
    /**
     * Dictionnaire recensant des tableaux d'état de collision pour chaque type d'objet.
     */
    protected HashMap<String, boolean[]> collisionMatrix = new HashMap<String, boolean[]>();
    protected int[][] positions;

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
        this.graviteCompteur = 0;
        this.gravityConstant = 1;
        this.jumpImpulse = 25;
        this.compteurSaut = 0;
        this.positions = new int[2][2];
        Arrays.fill(positions, new int[]{0, 0});
        this.isAlive = true;
        this.isInvincible = false;
        this.isResting = false;
        this.invincibleCompteur = 0;
        this.restCompteur = 0;
        this.life = 2;
        this.deadCompteur = 50;
        this.gravity = true;
        this.jumpTime = 0;
        this.gravityFall = true;
        this.frequenceMarche = 20;
        setCollisionMatrixToFalse();
    }

    public Boolean getJumping() {
        return isJumping;
    }

    public void setJumping(Boolean jumping) {
        isJumping = jumping;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    public Boolean getWalking() {
        return isWalking;
    }

    public void setWalking(Boolean walking) {
        isWalking = walking;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public Boolean getInvincible() {
        return isInvincible;
    }

    public void setInvincible(Boolean invincible) {
        isInvincible = invincible;
    }

    public Boolean getResting() {
        return isResting;
    }

    public void setResting(Boolean resting) {
        isResting = resting;
    }

    public int getCompteurMarche() {
        return compteurMarche;
    }

    public void setCompteurMarche(int compteurMarche) {
        this.compteurMarche = compteurMarche;
    }

    public int getGravityConstant() {
        return gravityConstant;
    }

    public void setGravityConstant(int gravityConstant) {
        this.gravityConstant = gravityConstant;
    }

    public int getJumpImpulse() {
        return jumpImpulse;
    }

    public void setJumpImpulse(int jumpImpulse) {
        this.jumpImpulse = jumpImpulse;
    }

    public int getCompteurSaut() {
        return compteurSaut;
    }

    public void setCompteurSaut(int compteurSaut) {
        this.compteurSaut = compteurSaut;
    }

    public int getInvincibleCompteur() {
        return invincibleCompteur;
    }

    public void setInvincibleCompteur(int invincibleCompteur) {
        this.invincibleCompteur = invincibleCompteur;
    }

    public int getRestCompteur() {
        return restCompteur;
    }

    public void setRestCompteur(int restCompteur) {
        this.restCompteur = restCompteur;
    }

    public long getJumpTime() {
        return jumpTime;
    }

    public void setJumpTime(long jumpTime) {
        this.jumpTime = jumpTime;
    }

    public HashMap<String, boolean[]> getCollisionMatrix() {
        return collisionMatrix;
    }

    public void setCollisionMatrix(HashMap<String, boolean[]> collisionMatrix) {
        this.collisionMatrix = collisionMatrix;
    }

    public int[][] getPositions() {
        return positions;
    }

    public void setPositions(int[][] positions) {
        this.positions = positions;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getFrequenceMarche() {
        return frequenceMarche;
    }

    public void setFrequenceMarche(int frequenceMarche) {
        this.frequenceMarche = frequenceMarche;
    }

    public Boolean getEnnemy() {
        return isEnnemy;
    }

    public void setEnnemy(Boolean ennemy) {
        isEnnemy = ennemy;
    }

    public int getDeadCompteur() {
        return deadCompteur;
    }

    public void setDeadCompteur(int deadCompteur) {
        this.deadCompteur = deadCompteur;
    }

    public boolean getGravity() {
        return this.gravity;
    }

    public int getGraviteCompteur() {
        return graviteCompteur;
    }

    public void setGraviteCompteur(int graviteCompteur) {
        this.graviteCompteur = graviteCompteur;
    }
    public void increaseGravityCompteur(){this.graviteCompteur++;}
    public void setGravity(Boolean gravity) {
        this.gravity = gravity;
    }
    //----METHODES----//
    /**
     * Fonction walk() permet de faire marcher le personage en modifiant son image (attribut)
     * @see Origin
     * @param frequence
     * La fréquence détermine à quelle vitesse les images simulant la marche alternent
     * Plus la fréquence est haute, plus la marche sera lente.
     */
    public void walk(int frequence){}
    /**
     * Fonction jump modifiant l'image (attribut) du personnage
     */
    public void jump(){
        String key = getName()+"_saute";
        if(isRight){key+="_droite";}
        else{key+="_gauche";}
        setBitmap(key);
    }
    public void setCollisionMatrixToFalse(){
        boolean[] tab = new boolean[4];
        Arrays.fill(tab, false);
        if(collisionMatrix.isEmpty()){
            collisionMatrix.put("castle", tab);
            collisionMatrix.put("floor", tab);
            collisionMatrix.put("yellowbloc", tab);
            collisionMatrix.put("brownbloc", tab);
            collisionMatrix.put("pipe", tab);
            collisionMatrix.put("piece", tab);
            collisionMatrix.put("item", tab);
        }
        else{
            for(String key : collisionMatrix.keySet()){collisionMatrix.put(key, tab);}
        }
    }
    public void setCollisionMatrix(String key, boolean[]tab){collisionMatrix.put(key, tab);}
    /**
     * Detecter s'il y a collision avec un objet.
     *
     * @param objet
     * @param error optionnel : error[0] -> vertial error | error[1] -> horizontal error
     * @return
     */
    public boolean[] detectCollision(Origin objet, int...error){

        // [haut, droite, bas, gauche] -> en haut de l'objet, à droite de l'objet ...
        boolean[] result = new boolean[4];
        Arrays.fill(result, false);

        int verticalError = 5;
        int lateralError = 4;

        if(error.length == 2){
            verticalError = error[0];
            lateralError = error[1];
        }

        boolean segment_vertical_gauche = getX() + getWidth() > objet.getX() + lateralError;
        boolean segment_vertical_droit = getX() < objet.getX() + objet.getWidth() - lateralError;
        boolean segment_horizontal_haut = getY() + getHeight() > objet.getY() + lateralError;
        boolean segment_horizontal_bas = getY() < objet.getY() + objet.getHeight() - lateralError;
        // collision en haut :
        boolean ch3 = getY() + getHeight() >= objet.getY() - verticalError;
        boolean ch4 = getY() < objet.getY();
        boolean ch5 = getY() + getHeight() <= objet.getY() + objet.getHeight()/2;
        result[0] = segment_vertical_droit && segment_vertical_gauche && ch3 && ch4 && ch5;

        //collision à droite
        boolean cd3 = getX() + lateralError >= objet.getX() + objet.getWidth()/2;
        boolean cd4 = getX() < objet.getX() + objet.getWidth();
        result[1] = segment_horizontal_bas && segment_horizontal_haut && cd3 && cd4;

        // collision en bas :
        boolean cb1 = getY() <= objet.getY() + objet.getHeight() + verticalError;
        boolean cb2 = getY() >= objet.getY() + objet.getHeight()/2;
        result[2] = cb1 && cb2 && segment_vertical_droit && segment_vertical_gauche;

        // collision à gauche :
        boolean cg3 = getX() + getWidth() >= objet.getX();
        boolean cg4 = getX() + getWidth() +lateralError <= objet.getX() + objet.getWidth()/2;
        result[3] = segment_horizontal_bas && segment_horizontal_haut && cg3 && cg4;

        return result;
    }

    /**
     * Detecter si le personnage a une collision à la droite de n'importe quel objet
     * @return
     */
    @Deprecated
    public boolean collisionInRightWithObject(){
        String[] keys = {"castle", "yellowbloc", "brownbloc", "pipe"};
        boolean b = false;
        for(String key : keys)
            if((collisionMatrix.get(key))[1]) {
                b = true;
                break;
            }
        return b;
    }

    /**
     * Detecter si le personnage a une collision à la gauche de n'importe quel objet
     * @return
     */
    @Deprecated
    public boolean collisionInLeftWithObject(){
        String[] keys = {"castle", "yellowbloc", "brownbloc", "pipe"};
        boolean b = false;
        for(String key : keys)
            if((collisionMatrix.get(key))[3]) {
                b = true;
                break;
            }
        return b;
    }

    /**
     * Detecter si le personnage a une collision en haut de n'importe quel objet
     * @return
     */
    @Deprecated
    public boolean collisionOnTopWithObject(){
        String[] keys = {"castle", "yellowbloc", "brownbloc", "pipe"};
        boolean b = false;
        for(String key : keys)
            if((collisionMatrix.get(key))[0]) {
                b = true;
                break;
            }
        return b;
    }
    /**
     * Detecter si le personnage a une collision en bas de n'importe que objet.
     * @return
     */
    @Deprecated
    public boolean collisionOnBottomWithObject(){
        String[] keys = {"castle", "yellowbloc", "brownbloc", "pipe"};
        boolean b = false;
        for(String key : keys)
            if((collisionMatrix.get(key))[2]) {
                b = true;
                break;
            }
        return b;
    }
    public boolean collisionWithObject(int direction){
        String[] keys = {"castle", "yellowbloc", "brownbloc", "pipe"};
        boolean b = false;
        for(String key : keys){
            if(collisionMatrix.get(key)[direction]){
                b = true;
            }
        }
        return b;
    }
    public boolean[] detectCollisionWithFloor(Floor floor, int...margin){
        boolean[] result = new boolean[4];
        Arrays.fill(result, false);
        int marge = margin.length>0 ? margin[0] : 0;
        result[0] = getY() + getHeight() + marge >= floor.getY();
        return result;
    }
    public void increaseCompteurSaut(){this.compteurSaut ++;}
    public void decreaseCompteurSaut(){this.compteurSaut --;}
    public void pushPositions(int[] pos){
        int[] pos0 = positions[0];
        positions[0] = pos;
        positions[1] = pos0;
    }
    public int getSpeedVectorX(){return positions[0][0]-positions[1][0];}
    public int getSpeedVectorY(){return positions[0][1]-positions[1][1];}

    public void increasePieceCount() {}
    public void increaseLife(){this.life ++;}
    public void decreaseLife(){
        this.life --;
        if(life <= 0){dead();}
    }
    public void recalibrerY(Objet objet){
        this.setY(objet.getY() - getHeight());
    }
    public void update(){
        if(activated){
            if(isResting){rest();}
            else if(isInvincible){invincible();}
            else if(isAlive){walk(frequenceMarche);}
            else{dead();}
        }
    }
    public void dead(){}
    public void rest(){}
    public void invincible(){}
    public void addCollisionValue(String key, int index, boolean b){
        boolean[] tab = collisionMatrix.get(key);
        tab[index] = b;
        collisionMatrix.put(key, tab);
    }
    public void recalibrerY(Personnage personnage){
        setY(personnage.getY() - getHeight());
    }
}
