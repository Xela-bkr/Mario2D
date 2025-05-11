package com.example.mario2d.game.loop;

import static com.example.mario2d.game.loop.GameActivity.castles;
import static com.example.mario2d.game.loop.GameActivity.ennemies;
import static com.example.mario2d.game.loop.GameActivity.etoiles;
import static com.example.mario2d.game.loop.GameActivity.fleursfeu;
import static com.example.mario2d.game.loop.GameActivity.platformes;
import static com.example.mario2d.game.loop.GameActivity.waitingLine;
import static com.example.mario2d.game.loop.GameActivity.waitingLineBrownBlocs;
import static com.example.mario2d.game.loop.GameActivity.waitingLineBrownBlocsForRemoving;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;
import static com.example.mario2d.game.loop.GameActivity.yellowBlocs;
import static com.example.mario2d.game.loop.GameActivity.brownBlocs;
import static com.example.mario2d.game.loop.GameActivity.pieces;
import static com.example.mario2d.game.loop.GameActivity.champis;
import static com.example.mario2d.game.loop.GameActivity.pipes;
import static com.example.mario2d.game.loop.GameActivity.objets;
import static com.example.mario2d.game.loop.GameActivity.persos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.mario2d.R;
import com.example.mario2d.game.Origin;
import com.example.mario2d.game.objet.BrownBloc;
import com.example.mario2d.game.objet.Castle;
import com.example.mario2d.game.objet.Champignon;
import com.example.mario2d.game.objet.Etoile;
import com.example.mario2d.game.objet.FleurFeu;
import com.example.mario2d.game.objet.Floor;
import com.example.mario2d.game.objet.Item;
import com.example.mario2d.game.objet.Objet;
import com.example.mario2d.game.objet.Piece;
import com.example.mario2d.game.objet.Pipe;
import com.example.mario2d.game.objet.Platforme;
import com.example.mario2d.game.objet.YellowBloc;
import com.example.mario2d.game.personnage.Ennemy;
import com.example.mario2d.game.personnage.Koopa;
import com.example.mario2d.game.personnage.Personnage;
import com.example.mario2d.game.personnage.Player;
import com.example.mario2d.menu.MainActivity;
import com.example.mario2d.tool.AbstractButton;
import com.example.mario2d.tool.Audio;
import com.example.mario2d.tool.Joystick;
import com.example.mario2d.tool.RoundButton;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    //----------VARIABLES-----------//
    public boolean exit, gravity, gameOver, win;
    public Joystick joystick;
    public  Player player;
    private GameLoop gameLoop;
    public static Audio theme;
    private int dx, joystickPointerId, jumpPointerId, menuPointerId, pausePointerId, retryPointerId, exitPointerId,
            displayWidth, displayHeight, LEVEL_SELECTED, gameCompteur, firePointerId;
    private Boolean leftHandMode, soundEffect, music, afficherMenuLateral;
    private AbstractButton menuButton, pauseButton, retryButton, exitButton;
    private RoundButton fireButton;

    /**
     * Largeur du menuLatéral initial à afficher si le jeu est en pause
     * Abndonné au profit d'un menu central.
     */
    private float menuWidth = (float)(displayWidth*0.2);
    /**
     * Constructeur principal.
     * Prend en peramètres l'ensemble des arrayList des objets à dessiner dans le jeu
     * => paramétrage des éléments dans le GameActivity.
     * @param context
     * @param displayWidth
     * @param displayHeight
     * @param leftHandMode
     * @param LEVEL_SELECTED
     * @param player
     * @param objets
     */
    private Bitmap pieceBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.piece_jaune_face);
    @RequiresApi(api = Build.VERSION_CODES.O)
    public GameView(Context context, int displayWidth, int displayHeight, boolean leftHandMode, int LEVEL_SELECTED){
        super(context);
        getHolder().addCallback(this);

        this.dx = 6;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;
        this.leftHandMode = leftHandMode;
        this.LEVEL_SELECTED = LEVEL_SELECTED;
        this.afficherMenuLateral = false;
        this.gameOver = false;
        this.win = false;
        gameCompteur = 0;

        this.player = GameActivity.player;

        this.exit = false;
        this.gravity = true;

        /**
         * valeurs par défaut des dimentions du joystick
         */
        float joystickExternalRadius = (float) ((displayWidth*0.05));
        float joystickInternalRadius = (float)(0.7*joystickExternalRadius);
        float joystickCenterX = (float) (displayWidth*0.1);
        float joystickCenterY = displayHeight - joystickExternalRadius*2;

        /**
         * Paramétrage du joystick en fonction du sol et du leftHandMode
         * @see #leftHandMode
         */
        if(leftHandMode){
            joystickCenterX = (float) ( displayWidth - joystickExternalRadius - displayWidth*0.1);
        }
        else{
            joystickCenterX = (float) (joystickExternalRadius + displayWidth*0.1);
        }
        this.joystick = new Joystick(joystickCenterX, joystickCenterY, joystickInternalRadius, joystickExternalRadius);
        setFocusable(true);

        /**
         * Initialisation des boutons du menu interne au jeu.
         */
        setMenuButton();
        setpauseButton();
        setExitButton();
        setretryButton();
        setFireButton();

        joystickPointerId = -1; jumpPointerId = -1; menuPointerId = -1; pausePointerId = -1;
        retryPointerId = -1; exitPointerId = -1; firePointerId = -1;
        setTheme();
    }
    /**
     * Listener pour gérer les évènements tacliles
     * @param event The motion event.
     * @return
     */
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event){

        if(gameOver || win){return true;}

        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        int action = event.getActionMasked();

        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        switch(action){
            case MotionEvent.ACTION_DOWN :
            case MotionEvent.ACTION_POINTER_DOWN :

                boolean joystickPressed = joystick.isPressed(x, y);
                boolean menuButtonPressed = menuButton.getIsPressed();
                boolean insideMenuButton = menuButton.isInside(x, y);
                boolean insidePauseButton = pauseButton.isInside(x, y);
                boolean insideRetryButton = retryButton.isInside(x, y);
                boolean insideExitButton = exitButton.isInside(x, y);

                if(player.fire) {
                    boolean insideFireButton = fireButton.pointerIn(x, y);
                    if(insideFireButton) {
                        Audio.playSound(getContext(), R.raw.fireball);
                        firePointerId = pointerId;
                        player.dropFireBowl();
                        return true;
                    }
                }
                if(joystickPressed){
                    joystick.setIsPressed(true);
                    joystickPointerId = pointerId;
                }
                if(!joystickPressed && !menuButtonPressed && !insideMenuButton && !insideExitButton &&
                        !insidePauseButton && !insideRetryButton){
                    jumpPointerId = pointerId;
                    if(!player.getJumping() && (player.collisionWithObject(0) || player.getCollisionMatrix().get("ennemy")[0])){
                        player.setJumping(true);
                        player.setWalking(false);
                        gravity = false;
                        player.setJumpTime(System.nanoTime());
                        Audio.playSound(getContext(), R.raw.jump_2);
                    }
                }
                if(insideMenuButton){
                    if(menuButton.getIsPressed()){
                        menuButton.setPressed(false);
                        menuPointerId = pointerId;
                    }
                    else{
                        menuButton.setPressed(true);
                        menuPointerId = pointerId;
                    }
                    Audio.playSound(getContext(), R.raw.pause);
                }
                if(insidePauseButton && menuButtonPressed){
                    pauseButton.setPressed(true);
                    pausePointerId = pointerId;
                    Audio.playSound(getContext(), R.raw.open_close);
                }
                if(insideRetryButton && menuButtonPressed){
                    retryPointerId = pointerId;
                    retryButton.setPressed(true);
                    Audio.playSound(getContext(), R.raw.open_close);
                }
                if(insideExitButton && menuButtonPressed){
                    exitPointerId = pointerId;
                    exitButton.setPressed(true);
                    Audio.playSound(getContext(), R.raw.open_close);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()) {
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (event.getPointerId(i) == joystickPointerId) {
                            joystick.setActuator(event.getX(i), event.getY(i));
                            break;
                        }
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP :

                if(pointerId == jumpPointerId){
                    jumpPointerId = -1;
                }
                if(pointerId == joystickPointerId){
                    joystick.setIsPressed(false);
                    joystick.resetActuatorXY();
                    joystickPointerId = -1;
                }
                if(pointerId == menuPointerId){
                    if(menuButton.getIsPressed()){
                        if(gameLoop.isRunning()){
                            gameLoop.stop();
                        }
                        else{
                            gameLoop.start();
                            menuButton.setPressed(false);
                        }
                    }
                    menuPointerId = -1;
                }
                if(pointerId == firePointerId) {
                    firePointerId = -1;
                }
                if(pointerId == pausePointerId){
                    if(pauseButton.getIsPressed()){
                        if(!gameLoop.isRunning()){
                            gameLoop.start();
                        }
                        pauseButton.setPressed(false);
                        menuButton.setPressed(false);
                    }
                    pausePointerId = -1;
                }
                if(pointerId == retryPointerId){
                    if(retryButton.getIsPressed()){

                    }
                    retryPointerId = -1;
                }
                if(pointerId == exitPointerId){
                    if(exitButton.getIsPressed()){
                        quitter();
                    }
                    exitPointerId = -1;
                }
                return true;
        }
        return super.onTouchEvent(event);
    }
    /**
     * Sert à initialiser le GameLoop
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}
    /**
     * Fonction essentielle pour faire varier la position de tous les éléments du jeu.
     * Permet aussi d'avoir un sol infiniment grand
     */
    public void moveWorld(){
        if(joystick.getIsPressed()){
            dx = 0;
            if (joystick.orientedInRight()) {
                dx = -GameActivity.dx/3;
                if(castles.get(1).getX() <= displayWidth - castles.get(1).getWidth()) {
                    player.translateX(-dx);
                    return;
                }
                if(player.getX() < displayWidth/2 - player.getWidth()/2 - dx/2) {
                    player.translateX(-dx);
                    return;
                }
            }
            if (!joystick.orientedInRight()) {
                dx = GameActivity.dx/3;
                if(castles.get(0).getX() >= 0) {
                    player.translateX(-dx);
                    return;
                }
                if(player.getX() > displayWidth/2 + player.getWidth()/2 + dx) {
                    player.translateX(-dx);
                    return;
                }
            }
            for(Objet obj : objets){obj.translateX(dx);}
            for(Ennemy en : ennemies){en.translateX(dx);}
            for(Castle c : castles){c.translateX(dx);}
            for(BrownBloc bb : brownBlocs){bb.translateX(dx);}
            for(Champignon c : champis){c.translateX(dx);}
            for(Etoile c : etoiles){c.translateX(dx);}
            for(FleurFeu ff : fleursfeu){ff.translateX(dx);}
            for(YellowBloc yb : yellowBlocs){yb.translateX(dx);}
            for(Piece p : pieces){p.translateX(dx);}
            for(Pipe pipe : pipes){pipe.translateX(dx);}
            for(Platforme p : platformes){p.translateX(dx);}
        }

    }
    /**
     * Fonction de mise à jour graphique du GemeView
     * Appelé par le gameLoop à chaque Loop
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void render() {
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            if (canvas != null) {
                setBackgroundColor(LEVEL_SELECTED, canvas);
                Paint paint = new Paint();
                for(Objet obj : objets) {
                    if (obj.getActivated() && onScreen(obj, 10, 10) && obj.getBitmap() != null) {
                        canvas.drawBitmap(obj.getBitmap(), obj.getX(), obj.getY(), paint);
                    }
                }
                for(Champignon c : champis){
                    if (c.getActivated() && onScreen(c, 10, 10) && c.getBitmap() != null) {
                        canvas.drawBitmap(c.getBitmap(), c.getX(), c.getY(), paint);
                    }
                }
                for(Etoile c : etoiles){
                    if (c.getActivated() && onScreen(c, 10, 10) && c.getBitmap() != null) {
                        canvas.drawBitmap(c.getBitmap(), c.getX(), c.getY(), paint);
                    }
                }
                for(FleurFeu ff : fleursfeu) {
                    if (ff.getActivated() && onScreen(ff, 10, 10) && ff.getBitmap() != null) {
                        canvas.drawBitmap(ff.getBitmap(), ff.getX(), ff.getY(), paint);
                    }
                }
                for(YellowBloc yb : yellowBlocs){
                    if (yb.getActivated() && onScreen(yb, 10, 10) && yb.getBitmap() != null) {
                        canvas.drawBitmap(yb.getBitmap(), yb.getX(), yb.getY(), paint);
                    }
                }
                for(Castle c : castles){
                    if (c.getActivated() && onScreen(c, 10, 10) && c.getBitmap() != null) {
                        canvas.drawBitmap(c.getBitmap(), c.getX(), c.getY(), paint);
                    }
                }
                for(Piece p : pieces){
                    if (p.getActivated() && onScreen(p, 10, 10) && p.getBitmap() != null) {
                        canvas.drawBitmap(p.getBitmap(), p.getX(), p.getY(), paint);
                    }
                }
                for(BrownBloc bb : brownBlocs){
                    if (bb.getActivated() && onScreen(bb, 10, 10) && bb.getBitmap() != null) {
                        canvas.drawBitmap(bb.getBitmap(), bb.getX(), bb.getY(), paint);
                    }
                }
                for(Platforme p : platformes){
                    if(p.getActivated() && onScreen(p, 10, 10) && p.getBitmap()!=null){
                        canvas.drawBitmap(p.getBitmap(), p.getX(), p.getY(), paint);
                    }
                }
                for(Ennemy en : ennemies) {
                    if(en.getActivated() && onScreen(en, 10, 10) && en.getBitmap()!=null) {
                        canvas.drawBitmap(en.getBitmap(), en.getX(), en.getY(), paint);
                    }
                }
                for(Pipe pipe : pipes){
                    if (pipe.getActivated() && onScreen(pipe, 10, 10) && pipe.getBitmap() != null) {
                        canvas.drawBitmap(pipe.getBitmap(), pipe.getX(), pipe.getY(), paint);
                    }
                }
                if(player.getBitmap()!=null){
                    canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
                }
                joystick.draw(canvas);
                if(menuButton.getIsPressed()) {
                    afficherMenuLateral(canvas);
                }
                menuButton.draw(canvas);
                drawLife(canvas, paint);

                if(player.fire) {
                    fireButton.draw(canvas);
                }
                if(gameOver){
                    gameOver(canvas, paint);
                }
                if(win){
                    win();
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally { if(canvas != null) getHolder().unlockCanvasAndPost(canvas); }
    }
    /**
     * Fonction de mise à jour logique du jeu
     * Appelé par le gameLoop à chaque loop.
     */
    public void update(){
        if(!theme.getIsRunning()) {setTheme();}
        for(Ennemy en : waitingLine) {
            ennemies.add(en);
        }
        waitingLine.clear();
        for(Ennemy en : waitingLineForRemoving) {
            ennemies.remove(en);
        }
        for(BrownBloc b : waitingLineBrownBlocs) {
            brownBlocs.add(b);
        }
        waitingLineBrownBlocs.clear();
        for(BrownBloc b : waitingLineBrownBlocsForRemoving) {
            brownBlocs.remove(b);
        }
        if(player.getX() + player.getWidth() >= castles.get(1).getX() - player.getWidth()*0.05){
            if (theme.isRunning) theme.stop();
            win = true;
        }
        if(!player.getAlive()){
            if (theme.isRunning) theme.stop();
            gameOver = true;
        }
        else{
            if(win){
                win();
                return;
            }
            joystick.update();
            player.pushPositions(new int[]{player.getX(), player.getY()});
            updateCollision(this.player);
            player.setWalking(false);
            if(joystick.getIsPressed()){
                boolean move = false;
                player.setRight(joystick.orientedInRight());
                if(player.getRight() && !player.collisionWithObject(3)){move = true;}
                if(!player.getRight() && !player.collisionWithObject(1)){move = true;}

                player.setWalking(move);
                if(player.getWalking()){moveWorld();}
            }
            player.update();
            updateEnnemy();
            updateAnimations();
            gravity();
        }
    }
    /**
     * Défini la constante de dérivation de tous les éléments du jeu
     * @param i
     */
    public void setDx(int i){this.dx = i;}
    @SuppressLint("ResourceAsColor")
    public void setBackgroundColor(int level, Canvas canvas){
        switch(level){
            case 1 :
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.blue_sky));
                break;
            case 2 :
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                break;
            case 3 :
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.blue_sky));
                break;
            case 4 :
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.volcan200));
                break;
            case 5 :
                canvas.drawColor(ContextCompat.getColor(getContext(), R.color.blue_sky));
                break;
            default :
                canvas.drawColor(Color.BLACK);
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void afficherMenuLateral(Canvas canvas){
        if (this.theme.getIsRunning()) {
            this.theme.stop();
        }
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.jaune_poussin));
        canvas.drawRect(0, 0, menuWidth, displayHeight, paint);
        pauseButton.draw(canvas);
        retryButton.draw(canvas);
        exitButton.draw(canvas);
    }
    /**
     * Paramétrage du bouton pause du jeu.
     * Une fois appuyé, ce bouton permet d'afficher le menu central
     *
     * @see #afficherMenuLateral
     * @see #render()
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setMenuButton(){

        int menuButtonWidth = (int)(displayWidth*0.04);
        int menuButtonHeight = menuButtonWidth;
        int menuX = 100;
        int menuY = (int)(displayHeight*0.07);

        Color[] colors = {Color.valueOf(Color.BLACK), Color.valueOf(Color.WHITE)};
        menuButton = new AbstractButton(getContext(), menuX, menuY, menuButtonWidth, menuButtonHeight, true, colors, null);
        menuButton.setBorderWidth(10);
        menuButton.setTextSize(50);
        menuButton.setTexte("||");
        menuButton.setFont(R.font.dogicapixelbold);
        menuButton.setTextColor(Color.valueOf(Color.WHITE));
        menuButton.setRectangleRoundRadius(20);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setpauseButton(){
        int buttonWidth = (int) (displayWidth*0.2);
        int buttonHeight = (int) (displayHeight*0.2);
        int buttonX = (int) (displayWidth/2 - buttonWidth/2);
        int buttonY = (int) (displayHeight*0.15);

        int col1 = ContextCompat.getColor(getContext(), R.color.blue102);
        int col2 = ContextCompat.getColor(getContext(), R.color.volcan200);

        Color[] colors = {Color.valueOf(col1), Color.valueOf(col2)};
        this.pauseButton = new AbstractButton(getContext(), buttonX, buttonY, buttonWidth, buttonHeight, true, colors, null);
        pauseButton.setBorderWidth(20);
        pauseButton.setTexte("Resume");
        pauseButton.setTextSize(50);
        pauseButton.setFont(R.font.dogicapixel);
        pauseButton.setTextColor(Color.valueOf(Color.WHITE));
        pauseButton.setRectangleRoundRadius(20);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setretryButton(){
        int buttonWidth = (int) (displayWidth*0.2);
        int buttonHeight = (int) (displayHeight*0.2);
        int buttonX = (int) (displayWidth/2 - buttonWidth/2);
        int buttonY = (int) (displayHeight*0.40);

        int col1 = ContextCompat.getColor(getContext(), R.color.blue102);
        int col2 = ContextCompat.getColor(getContext(), R.color.volcan200);

        Color[] colors = {Color.valueOf(col1), Color.valueOf(col2)};
        this.retryButton = new AbstractButton(getContext(), buttonX, buttonY, buttonWidth, buttonHeight, true, colors, null);
        retryButton.setBorderWidth(20);
        retryButton.setTexte("Retry");
        retryButton.setTextSize(50);
        retryButton.setFont(R.font.dogicapixel);
        retryButton.setTextColor(Color.valueOf(Color.WHITE));
        retryButton.setRectangleRoundRadius(20);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setExitButton(){
        int buttonWidth = (int) (displayWidth*0.2);
        int buttonHeight = (int) (displayHeight*0.2);
        int buttonX = (int) (displayWidth/2 - buttonWidth/2);
        int buttonY = (int) (displayHeight*0.65);

        int col1 = ContextCompat.getColor(getContext(), R.color.blue102);
        int col2 = ContextCompat.getColor(getContext(), R.color.volcan200);

        Color[] colors = {Color.valueOf(col1), Color.valueOf(col2)};
        this.exitButton = new AbstractButton(getContext(), buttonX, buttonY, buttonWidth, buttonHeight, true, colors, null);
        exitButton.setBorderWidth(20);
        exitButton.setTexte("Exit");
        exitButton.setTextSize(50);
        exitButton.setFont(R.font.dogicapixel);
        exitButton.setTextColor(Color.valueOf(Color.WHITE));
        exitButton.setRectangleRoundRadius(20);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setFireButton() {

        int rayon = (int) (displayWidth * 0.06);
        int buttonX = (int) (displayWidth*0.9);
        int buttonY = displayHeight - rayon*2;

        if (leftHandMode) {
            buttonX = (int) (displayWidth*0.1);
        }
        fireButton = new RoundButton(getContext(), buttonX, buttonY, rayon, Color.WHITE, R.drawable.fleurfeu);
    }
    /**
     * fonction appelée à chaque loop pour vérifier les collisions relatives à chaque objet.
     *
     * @see #update()
     * @see com.example.mario2d.game.personnage.Personnage
     *
     * TODO ajouter les futur vérifications de collisions
     * pour chaque type d'objet, (yellowbloc, pipe etc...) vérifier les collisions.
     * TODO implémenter les cas de collisions multiples
     */
    public void updateCollision(Personnage player){

        player.setCollisionMatrixToFalse();

        int[] brownBlocOffset = new int[]{5, Math.abs(this.dx)+2};
        int[] yellowBlocOffset = new int[]{5, Math.abs(this.dx)+2};
        int[] pipeOffset = new int[]{5, Math.abs(this.dx)+2};
        int[] castleOffset = new int[]{5, Math.abs(this.dx)+2};

        //Collision avec châteaux;
        for(Castle castle : castles){
            boolean[] tab = player.detectCollision(castle, castleOffset);
            if(tab[0]){player.addCollisionValue("castle", 0, true);}
            if(tab[1]){player.addCollisionValue("castle", 1, true);}
            if(tab[2]){player.addCollisionValue("castle", 2, true);}
            if(tab[3]){player.addCollisionValue("castle", 3, true);}
        }
        //Collision avec bocs marrons
        for(BrownBloc bb : brownBlocs){
            boolean[] tab = player.detectCollision(bb, brownBlocOffset);
            if(tab[0]){player.addCollisionValue("brownbloc", 0, true);}
            if(tab[1]){player.addCollisionValue("brownbloc", 1, true);}
            if(tab[2]){player.addCollisionValue("brownbloc", 2, true);}
            if(tab[3]){player.addCollisionValue("brownbloc", 3, true);}
        }
        //collision avec blocs jaunes
        for(YellowBloc yb : yellowBlocs){
            boolean[] tab = player.detectCollision(yb, yellowBlocOffset);
            if(tab[0]){player.addCollisionValue("yellowbloc", 0, true);}
            if(tab[1]){player.addCollisionValue("yellowbloc", 1, true);}
            if(tab[2]){player.addCollisionValue("yellowbloc", 2, true);}
            if(tab[3]){player.addCollisionValue("yellowbloc", 3, true);}
        }
        //collision avec tubes
        for(Pipe pipe : pipes){
            boolean[] tab = player.detectCollision(pipe, pipeOffset);
            if(tab[0]){player.addCollisionValue("pipe", 0, true);}
            if(tab[1]){player.addCollisionValue("pipe", 1, true);}
            if(tab[2]){player.addCollisionValue("pipe", 2, true);}
            if(tab[3]){player.addCollisionValue("pipe", 3, true);}
        }
        for(Platforme pl : platformes) {
            boolean[] tab = player.detectCollision(pl, pipeOffset);
            if(tab[0]){player.addCollisionValue("brownbloc", 0, true);}
        }
        /*for (Ennemy en : ennemies) {
            if (en == player) {continue;}
            boolean[] tab = player.detectCollision(en, 5, -5);
            if(tab[0]){player.addCollisionValue("ennemy", 0, true);}
            if(tab[1]){player.addCollisionValue("ennemy", 1, true);}
            if(tab[2]){player.addCollisionValue("ennemy", 2, true);}
            if(tab[3]){player.addCollisionValue("ennemy", 3, true);}
        }*/
    }
    public void updateCollision(Item item){

        item.getCollisionMatrix().put("objet", new boolean[]{false, false, false, false});

        int[] brownBlocOffset = new int[]{5, Math.abs(this.dx)+2};
        int[] yellowBlocOffset = new int[]{5, Math.abs(this.dx)+2};
        int[] pipeOffset = new int[]{5, Math.abs(this.dx)+2};
        int[] castleOffset = new int[]{5, Math.abs(this.dx)+2};

        //Collision avec châteaux;
        for(Castle castle : castles){
            boolean[] tab = item.detectCollision(castle, castleOffset);
            if(tab[0]){item.addCollisionValue("objet", 0, true);}
            if(tab[1]){item.addCollisionValue("objet", 1, true);}
            if(tab[2]){item.addCollisionValue("objet", 2, true);}
            if(tab[3]){item.addCollisionValue("objet", 3, true);}
        }

        //Collision avec bocs marrons
        for(BrownBloc bb : brownBlocs){
            boolean[] tab = item.detectCollision(bb, brownBlocOffset);
            if(tab[0]){item.addCollisionValue("objet", 0, true);}
            if(tab[1]){item.addCollisionValue("objet", 1, true);}
            if(tab[2]){item.addCollisionValue("objet", 2, true);}
            if(tab[3]){item.addCollisionValue("objet", 3, true);}
        }
        //collision avec blocs jaunes
        for(YellowBloc yb : yellowBlocs){
            boolean[] tab = item.detectCollision(yb, yellowBlocOffset);
            if(tab[2]){item.addCollisionValue("objet", 2, true);}
            if(tab[0]){item.addCollisionValue("objet", 0, true);item.recalibrerY(yb);}
            if(tab[1]){item.addCollisionValue("objet", 1, true);}
            if(tab[3]){item.addCollisionValue("objet", 3, true);}
        }

        //collision avec tubes
        for(Pipe pipe : pipes){
            boolean[] tab = item.detectCollision(pipe, pipeOffset);
            if(tab[0]){item.addCollisionValue("objet", 0, true);}
            if(tab[1]){item.addCollisionValue("objet", 1, true);}
            if(tab[2]){item.addCollisionValue("objet", 2, true);}
            if(tab[3]){item.addCollisionValue("objet", 3, true);}
        }
        for(Platforme pl : platformes) {
            boolean[] tab = item.detectCollision(pl, pipeOffset);
            if(tab[0]){item.addCollisionValue("objet", 0, true);}
        }
    }
    public void gravity(){
        for(Ennemy en : ennemies){
            if(!en.collisionWithObject(0) && en.getGravity()){
                en.translateY(en.getGravityConstant()*en.getGraviteCompteur()/ en.getJumpImpulse());
                en.increaseGravityCompteur();
            }
            else{en.setGraviteCompteur(1);}
        }
        if(player.getGravity()){
            boolean collisionOnTopOfFloor = player.getCollisionMatrix().get("floor")[0];
            if(!player.collisionWithObject(0) && !collisionOnTopOfFloor && !player.getCollisionMatrix().get("ennemy")[0]){
                int dy = -player.getGravityConstant()*(int)(player.getCompteurSaut()*0.9);
                player.translateY(dy);
                player.decreaseCompteurSaut();
            }
            else{
                player.setCompteurSaut(0);
            }
        }
        for(Champignon champi : champis){
            if(champi.isGravity() && !champi.getCollisionMatrix().get("objet")[0]){
                System.out.println("gravite champignon");
                champi.translateY(5);
            }
        }
        for(Etoile etoile : etoiles){
            if(etoile.isGravity() && !etoile.getCollisionMatrix().get("objet")[0]){
                etoile.translateY(5);
            }
        }
        for(FleurFeu etoile : fleursfeu){
            if(etoile.isGravity() && !etoile.getCollisionMatrix().get("objet")[0]){
                etoile.translateY(5);
            }
        }
    }
    public void updateAnimations(){

        for(Champignon item : champis){
            if(item.isGravity()){
                updateCollision(item);
            }
            item.update();
        }
        for(BrownBloc bb : brownBlocs){
            bb.update();
        }
        for(YellowBloc yb : yellowBlocs){
            yb.update();
        }
        for(Pipe pipe : pipes){
            pipe.update();
        }
        for(Piece piece : pieces){
            piece.update();
        }
        for(Etoile etoile : etoiles){
            if(etoile.isGravity()){
                updateCollision(etoile);
            }
            etoile.update();
        }
        for(Platforme p : platformes){
            p.update();
        }
        for (FleurFeu ff : fleursfeu) {
            if(ff.isGravity()){
                updateCollision(ff);
            }
            ff.update();
        }
    }
    @Deprecated
    public void afficherScore(Canvas canvas, Paint paint){

        final int PIECE_WIDTH = (int) (displayWidth*0.03);
        final int PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1378);

        final float textSize = (float) (displayHeight * 0.05);

        float stringX = (float)(displayWidth*3/4);
        float iconX = stringX - PIECE_WIDTH;

        float iconY = (float)(displayHeight * 0.07);
        float stringY = (float) (iconY + textSize*3/2);

        Bitmap finalPieceBitmap = Bitmap.createScaledBitmap(pieceBitmap, PIECE_WIDTH, PIECE_HEIGHT, true);
        String toDraw = " : "+ Integer.toString(player.getPiecesCount());

        canvas.drawBitmap(finalPieceBitmap, iconX, iconY, paint);

        paint.setTextSize(textSize);
        switch (LEVEL_SELECTED){
            case 1 : paint.setColor(Color.BLACK); break;
            case 2 : paint.setColor(Color.WHITE); break;
            case 3 : paint.setColor(Color.WHITE); break;
            case 4 : paint.setColor(Color.WHITE); break;
            case 5 : paint.setColor(Color.BLACK); break;
        }
        Typeface font = ResourcesCompat.getFont(this.getContext(), R.font.dogicapixelbold);
        paint.setTypeface(font);

        canvas.drawText(toDraw, stringX, stringY, paint);
    }
    public void updateEnnemy(){
        for(Ennemy en : ennemies){
            updateCollision(en);
            en.update();
        }
    }
    public boolean onScreen(Origin truc, int marginLeft, int marginRight){
        boolean gauche = truc.getX() + truc.getWidth() > -marginLeft;
        boolean droite = truc.getX() < displayWidth + marginRight;
        return gauche && droite;
    }
    public void drawLife(Canvas canvas, Paint paint){

        String life = " ";
        if (player.getResting()) {
            life = "Resting";
        } else if (player.getInvincible()) {
            life = "Invincible";
        } else {
            life = "Vies : " + player.getLife();
        }

        final float textSize = (float) (displayHeight * 0.05);

        int StringX = (int) (displayWidth/2 - textSize*life.length()/2);
        int StringY = (int) (displayHeight * 0.05 + textSize*3/2);

        paint.setTextSize(textSize);

        if(LEVEL_SELECTED == 1 || LEVEL_SELECTED == 5){paint.setColor(Color.BLACK);}
        else{paint.setColor(Color.WHITE);}

        Typeface font = ResourcesCompat.getFont(this.getContext(), R.font.dogicapixelbold);
        paint.setTypeface(font);

        canvas.drawText(life, StringX, StringY, paint);

        String score = String.format("Score : %d", player.score);
        int scoreX = (int) (displayWidth/4 - textSize*score.length()/2);
        canvas.drawText(score, scoreX, StringY, paint);

        String piece = String.format("Pieces : %d", player.getPiecesCount());
        int pieceX = (int) (displayWidth*3/4 - textSize*piece.length()/2);
        canvas.drawText(piece, pieceX, StringY, paint);
    }
    public void gameOver(Canvas canvas, Paint paint){
        gameOver = true;
        if(gameCompteur <= 100) {
            if(gameCompteur == 0){
                Audio.playSound(getContext(), R.raw.mario_dead);
            }
            player.dead();

            String texte = "Game Over";
            paint.setTextSize(200);
            paint.setColor(Color.BLACK);

            Typeface font = ResourcesCompat.getFont(this.getContext(), R.font.title_font);
            paint.setTypeface(font);

            float textWidth = paint.measureText(texte);
            float x = (displayWidth - textWidth) / 2;
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float y = (displayHeight - (fontMetrics.ascent + fontMetrics.descent)) / 2;
            canvas.drawText(texte, x, y, paint);
        }
        else{
            System.out.println("quitter");
            new Handler(Looper.getMainLooper()).post(() -> quitter());
        }
        gameCompteur++;
    }
    private void quitter(){
        if (theme.isRunning) { theme.stop(); }
        System.out.println("quitter function");
        Intent intent = new Intent(getContext(), MainActivity.class);
        gameLoop.setRunning(false);
        try {
            gameLoop.getThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        getContext().startActivity(intent);
    }
    private void win(){
        if(gameCompteur == 0){
            Audio.playSound(getContext(), R.raw.world_clear);
            writeData();
            gameCompteur ++;
        } else if (gameCompteur < 100) {
            player.win();
            gameCompteur ++;
        } else if (gameCompteur < 350) {
            if (player.getX() + player.getWidth()/2 < castles.get(1).getX() + castles.get(1).getWidth()/2) {
                player.walk(8);
                player.translateX(2);
            }
            gameCompteur ++;
        }
        else if(gameCompteur < 420){
            player.win();
            gameCompteur++;
        }
        else{
            new Handler(Looper.getMainLooper()).post(() -> quitter());
        }
    }
    private void writeData(){
        try {
            String filename = String.format("data.json", LEVEL_SELECTED);
            File file = new File(getContext().getFilesDir(), filename);

            if (!file.exists()) {
                JSONObject defaultData = new JSONObject();
                JSONArray levelArray = new JSONArray();
                JSONObject initialEntry = new JSONObject();
                initialEntry.put("piece", 0);
                initialEntry.put("point", 0);
                initialEntry.put("temps", 0);
                levelArray.put(initialEntry);

                for(int i = 1; i<6; i++){
                    defaultData.put(String.format("Niveau%d", i), levelArray);
                }

                FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                OutputStreamWriter writer = new OutputStreamWriter(fos);
                writer.write(defaultData.toString(4));
                writer.close();
            }

            FileInputStream fileInputStream = getContext().openFileInput("data.json");
            InputStreamReader inputStremReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStremReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            bufferedReader.close();

            String data = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(data);

            JSONObject newData = new JSONObject();
            newData.put("piece", player.getPiecesCount());
            newData.put("point", player.score);
            newData.put("temps", 0);

            String key = String.format("Niveau%d", LEVEL_SELECTED);
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            jsonArray.put(newData);
            jsonObject.put(key, jsonArray);

            FileOutputStream fileOutputStream = getContext().openFileOutput("data.json", Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(jsonObject.toString(4));
            outputStreamWriter.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTheme() {
        int[] themes = {R.raw.theme1, R.raw.theme2, R.raw.theme3, R.raw.theme4, R.raw.theme5};
        theme = new Audio(getContext(), themes[LEVEL_SELECTED-1]);
        theme.setLoop(true);
        theme.play();
    }

}
