package com.example.mario2d.game.loop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import com.example.mario2d.R;
import com.example.mario2d.game.objet.BrownBloc;
import com.example.mario2d.game.objet.Castle;
import com.example.mario2d.game.objet.Floor;
import com.example.mario2d.game.objet.Objet;
import com.example.mario2d.game.objet.Pipe;
import com.example.mario2d.game.objet.YellowBloc;
import com.example.mario2d.game.personnage.Player;
import com.example.mario2d.menu.MainActivity;
import com.example.mario2d.tool.AbstractButton;
import com.example.mario2d.tool.Joystick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    //----------VARIABLES-----------//
    public boolean exit, gravity;
    public DisplayMetrics dm;
    private GameLoop gameLoop;
    public Joystick joystick;
    private int compteurMarche;
    private Bitmap characterBitmap;
    private int dx;
    private int joystickPointerId, jumpPointerId, menuPointerId, pausePointerId, retryPointerId, exitPointerId;
    private int displayWidth, displayHeight, CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_RATE,
            CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT, LEVEL_SELECTED, CHARACTER_SELECTED;

    private Boolean leftHandMode, soundEffect, music, afficherMenuLateral;
    /**
     * Ensemble des boutons affichés lorsque le jeu est en pause
     * @see #setMenuButton()
     * @see #setpauseButton()
     * @see #setretryButton()
     * @see #setExitButton()
     */
    private AbstractButton menuButton, pauseButton, retryButton, exitButton;
    public Player player;
    private ArrayList<Floor> floor;
    private ArrayList<Castle> castles;
    private ArrayList<BrownBloc> brownBlocs;
    private ArrayList<YellowBloc> yellowBlocs;
    private ArrayList<Pipe> pipes;

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
     * @param floor
     * @param castles
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public GameView(Context context, int displayWidth, int displayHeight, boolean leftHandMode, int LEVEL_SELECTED, Player player,
                    ArrayList<Floor> floor, ArrayList<Castle> castles, ArrayList<BrownBloc> brownBlocs,
                    ArrayList<YellowBloc> yellowBlocs, ArrayList<Pipe> pipes){
        super(context);
        getHolder().addCallback(this);

        this.dx = 6;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;
        this.compteurMarche = 1;
        this.leftHandMode = leftHandMode;
        this.LEVEL_SELECTED = LEVEL_SELECTED;
        this.afficherMenuLateral = false;

        this.player = player;
        this.floor = floor;
        this.castles = castles;
        this.brownBlocs = brownBlocs;
        this.yellowBlocs = yellowBlocs;
        this.pipes = pipes;

        this.exit = false;
        this.gravity = true;

        /**
         * valeurs par défaut des dimentions du joystick
         */
        float joystickExternalRadius = 200;
        float joystickInternalRadius = 100;
        float joystickCenterX = 300;
        float joystickCenterY = 300;

        /**
         * Paramétrage du joystick en fonction du sol et du leftHandMode
         * @see #leftHandMode
         */
        if(this.floor!=null){
            if(leftHandMode){
                joystickExternalRadius = (floor.get(1).getWidth()/3) + 10 ;
                joystickInternalRadius = (float)(0.7*joystickExternalRadius);
                joystickCenterX = (float) (displayWidth * 4) /5;
                joystickCenterY = displayHeight - floor.get(1).getHeight()/2;
            }
            else{
                joystickExternalRadius = (floor.get(1).getWidth()/3) + 10 ;
                joystickInternalRadius = (float)(0.7*joystickExternalRadius);
                joystickCenterX = floor.get(1).getX() + floor.get(1).getWidth();
                joystickCenterY = floor.get(1).getY() + (floor.get(1).getHeight() / 2);
            }
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

        joystickPointerId = -1; jumpPointerId = -1; menuPointerId = -1; pausePointerId = -1;
        retryPointerId = -1; exitPointerId = -1;

        System.out.println(String.format("Player X : %d | Player Y : %d", player.getX(), player.getY()));
        System.out.println(String.format("Player Width : %d | Player Height : %d", player.getWidth(), player.getHeight()));
        System.out.println(String.format("display dimansions : %d   %d", displayWidth, displayHeight));
    }

    /**
     * Listener pour gérer les évènements tacliles
     * TODO implémenter un Listener multitouch
     * @param event The motion event.
     * @return
     */
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event){

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

                if(joystickPressed){
                    joystick.setIsPressed(true);
                    joystickPointerId = pointerId;
                }
                if(insideMenuButton){
                    if(menuButton.getIsPressed()){
                        menuButton.setPressed(false);
                        System.out.println("menuButton setting to false");
                    }
                    else{
                        menuButton.setPressed(true);
                        menuPointerId = pointerId;
                        System.out.println("menuButton setting to true");
                    }
                }
                if(insidePauseButton){
                    pauseButton.setPressed(true);
                    pausePointerId = pointerId;
                    System.out.println("pauseButton setting to true");
                }
                if(insideRetryButton){
                    retryPointerId = pointerId;
                    retryButton.setPressed(true);
                    System.out.println("retryButton setting to true");
                }
                if(insideExitButton){
                    exitPointerId = pointerId;
                    exitButton.setPressed(true);
                    System.out.println("exitButton setting to true");
                }
                if(!joystickPressed && !menuButtonPressed && !insideMenuButton && !insideExitButton &&
                        !insidePauseButton && !insideRetryButton){
                    jumpPointerId = pointerId;
                    if(!player.getJumping()){
                        player.setJumpingState(true);
                        player.setWalkingSate(false);
                        gravity = false;
                        player.setJumpTime(System.nanoTime());
                    }
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
                        if(!this.gameLoop.isRunning()){
                            gameLoop.start();
                        }
                    }
                    else{
                        gameLoop.stop();
                    }
                    menuPointerId = -1;
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
                        reset();
                    }
                    retryPointerId = -1;
                }
                if(pointerId == exitPointerId){
                    if(exitButton.getIsPressed()){
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        getContext().startActivity(intent);
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
            if(joystick.orientedInRight()){
                for(Floor fl : floor){
                    fl.translateX(-dx);
                    if(fl.getX()+fl.getWidth()<=0){fl.setX(this.displayWidth+1);}
                }
                for(Castle c : castles){c.translateX(-dx);}
                for(BrownBloc bb : brownBlocs){bb.translateX(-dx);}
                for(YellowBloc yb : yellowBlocs){yb.translateX(-dx);}
                for(Pipe pipe : pipes){pipe.translateX(-dx);}
            }
            else{
                for(Floor fl : floor){
                    fl.translateX(dx);
                    if(fl.getX()>=this.displayWidth){
                        fl.setX(-fl.getWidth());
                    }
                }
                for(Castle c : castles){c.translateX(dx);}
                for(BrownBloc bb : brownBlocs){bb.translateX(dx);}
                for(YellowBloc yb : yellowBlocs){yb.translateX(dx);}
                for(Pipe pipe : pipes){pipe.translateX(dx);}
            }
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
                for(int i = 0; i<floor.size(); i++){
                    int x = floor.get(i).getX();
                    int y = floor.get(i).getY();
                    Bitmap b = floor.get(i).getBitmap();
                    canvas.drawBitmap(b, x, y, paint);
                }
                if(this.player.getBitmap()!=null){
                    canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
                }
                joystick.draw(canvas);
                for(Castle c : castles){
                    if(c.getBitmap()!=null){
                        canvas.drawBitmap(c.getBitmap(), c.getX(), c.getY(), paint);
                    }
                }
                for(BrownBloc bb : brownBlocs){
                    if(bb.getBitmap()!=null){
                        canvas.drawBitmap(bb.getBitmap(), bb.getX(), bb.getY(), paint);
                    }
                }
                for(YellowBloc yb : yellowBlocs){
                    if(yb.getBitmap()!=null){
                        canvas.drawBitmap(yb.getBitmap(), yb.getX(), yb.getY(), paint);
                    }
                }
                for(Pipe pipe : pipes){
                    if(pipe.getBitmap()!=null){
                        canvas.drawBitmap(pipe.getBitmap(), pipe.getX(), pipe.getY(), paint);
                    }
                }
                if(menuButton.getIsPressed()){
                    afficherMenuLateral(canvas);}
                menuButton.draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * Fonction de mise à jour logique du jeu
     * Appelé par le gameLoop à chaque loop.
     */
    public void update(){
        joystick.update();
        updateCollision();
        gravity();
        if(joystick.getIsPressed()){
            if(joystick.orientedInRight()){
                player.setDirectionRight(true);
                if(!player.collisionInLeftWithObject()){
                    player.setWalkingSate(true);
                    updatePayerMove();
                    moveWorld();
                }
                if(player.getX()<(int)(displayWidth/3 - 3)){
                    player.translateX(3);
                }
                if(player.getX()>(int)(displayWidth/3 + 3)){
                    player.translateX(-3);
                }
            }
            else{
                player.setDirectionRight(false);
                if(!player.collisionInRightWithObject()){
                    player.setWalkingSate(true);
                    updatePayerMove();
                    moveWorld();
                }
                player.walk(10);
                if(player.getX()<(int)(displayWidth/2 - 3)){
                    player.translateX(3);
                }
                if(player.getX()>(int)(displayWidth/2 + 3)){
                    player.translateX(-3);
                }
            }
        }
        else {
            player.setWalkingSate(false);
            updatePayerMove();
        }
    }

    /**
     * Défini la constante de dérivation de tous les éléments du jeu
     * @param i
     */
    public void setDx(int i){this.dx = i;}
    /**
     * Dessine le fond d'écran du jeu en fonction du niveau de jeu sur le canvas
     * Appelé dans la méthode render()
     *
     * @see #render()
     *
     * @param level
     * @param canvas
     */
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

    /**
     * Menu Latéral devenu le menu central car plus pratique
     * Dessine les boutons du menu sur le canvas
     *
     * @see #render()
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void afficherMenuLateral(Canvas canvas){
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

        //int col1 = ContextCompat.getColor(getContext(), R.color.egyptian_floor200);
        //int col2 = ContextCompat.getColor(getContext(), R.color.jaune_poussin);
        Color[] colors = {Color.valueOf(Color.BLACK), Color.valueOf(Color.WHITE)};
        menuButton = new AbstractButton(getContext(), menuX, menuY, menuButtonWidth, menuButtonHeight, true, colors, null);
        menuButton.setBorderWidth(10);
        menuButton.setTextSize(50);
        menuButton.setTexte("||");
        menuButton.setFont(R.font.dogicapixelbold);
        menuButton.setTextColor(Color.valueOf(Color.WHITE));
        menuButton.setRectangleRoundRadius(20);
    }

    /**
     * pauseButton est en réalité le bouton permettant de reprendre le jeu
     * lorsque le menu latéral est affiché.
     *
     * @see #afficherMenuLateral
     */
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

    /**
     * Fonction permettant de recommencer la partie sans passer par le menu principal.
     * TODO paramétrer la fonction reset associée
     *
     * @see #reset()
     */
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

    /**
     * Bouton permettant d'abandonner la partie en cours et de revenir au menu principal.
     */
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
    public void reset(){
        //TODO reset the level
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
    public void updateCollision(){
        for(Castle castle : castles){
            boolean[] tab = player.detectCollision(castle);
            player.setCollisionMatrix("castle", tab);
            if(tab[0] || tab[1] || tab[2] || tab[3]){break;}
        }
        for(Floor floor : floor){
            boolean[] tab = player.detectCollision(floor);
            player.setCollisionMatrix("floor", tab);
            if(tab[0] || tab[1] || tab[2] || tab[3]){break;}
        }
        for(BrownBloc bb : brownBlocs){
            boolean[] tab = player.detectCollision(bb);
            player.setCollisionMatrix("brownbloc", tab);
            if(tab[0] || tab[1] || tab[2] || tab[3]){break;}
        }
        for(YellowBloc yb : yellowBlocs){
            boolean[] tab = player.detectCollision(yb);
            player.setCollisionMatrix("yellowbloc", tab);
            if(tab[0] || tab[1] || tab[2] || tab[3]){break;}
        }
        for(Pipe pipe : pipes){
            boolean[] tab = player.detectCollision(pipe);
            player.setCollisionMatrix("pipe", tab);
            if(tab[0] || tab[1] || tab[2] || tab[3]){break;}
        }
    }
    public void updatePayerMove(){
        long ascentTime = 1_000_000_000/3;
        if(player.getJumping()){
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - player.getJumpTime();
            player.jump();
            if(deltaTime <= ascentTime){
                player.translateY(-12);
            }
            else{
                player.setJumpingState(false);
                gravity = true;
            }
        }
        else if(player.getWalkingState()){
            player.walk(10);
        }
    }
    public void gravity(){
        if(!gravity){return;}
        boolean collisionOnTopOfFloor = player.getCollisionMatrix().get("floor")[0];
        if(!player.collisionOnTopWithObject() && !collisionOnTopOfFloor){
            player.translateY(5);
        }

    }
}
