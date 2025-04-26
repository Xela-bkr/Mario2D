package com.example.mario2d.game.loop;

import android.annotation.SuppressLint;
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
import com.example.mario2d.game.objet.Floor;
import com.example.mario2d.game.objet.Item;
import com.example.mario2d.game.objet.Objet;
import com.example.mario2d.game.objet.Piece;
import com.example.mario2d.game.objet.Pipe;
import com.example.mario2d.game.objet.YellowBloc;
import com.example.mario2d.game.personnage.Ennemy;
import com.example.mario2d.game.personnage.Koopa;
import com.example.mario2d.game.personnage.Personnage;
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
    public boolean exit, gravity, gameOver, win;
    public Joystick joystick;
    public Player player;
    private GameLoop gameLoop;
    private int dx, joystickPointerId, jumpPointerId, menuPointerId, pausePointerId, retryPointerId, exitPointerId,
            displayWidth, displayHeight, LEVEL_SELECTED;
    private Boolean leftHandMode, soundEffect, music, afficherMenuLateral;
    private AbstractButton menuButton, pauseButton, retryButton, exitButton;
    private ArrayList<Objet> objets;
    private ArrayList<Floor> floor = new ArrayList<Floor>();
    private ArrayList<Castle> castles = new ArrayList<Castle>();
    private ArrayList<BrownBloc> brownBlocs = new ArrayList<BrownBloc>();
    private ArrayList<YellowBloc> yellowBlocs = new ArrayList<YellowBloc>();
    private ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<Ennemy> ennemies = new ArrayList<Ennemy>();

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
    private Bitmap pieceBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.piece);
    @RequiresApi(api = Build.VERSION_CODES.O)
    public GameView(Context context, int displayWidth, int displayHeight, boolean leftHandMode, int LEVEL_SELECTED, Player player, ArrayList<Objet> objets, ArrayList<Personnage> persos){
        super(context);
        getHolder().addCallback(this);

        this.dx = 6;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;
        this.leftHandMode = leftHandMode;
        this.LEVEL_SELECTED = LEVEL_SELECTED;
        this.afficherMenuLateral = false;

        this.player = player;
        this.objets = objets;

        for(Objet objet : objets){
            if(objet instanceof Castle){castles.add((Castle) objet);}
            if(objet instanceof YellowBloc){yellowBlocs.add((YellowBloc) objet);}
            if(objet instanceof BrownBloc){brownBlocs.add((BrownBloc) objet);}
            if(objet instanceof Pipe){pipes.add((Pipe) objet);}
            if(objet instanceof Floor){floor.add((Floor) objet);}
            if(objet instanceof Piece){pieces.add((Piece) objet);}
            if(objet instanceof Item){items.add((Item) objet);}
        }
        for(Personnage perso : persos){if(perso instanceof Ennemy){ennemies.add((Ennemy)perso);}}

        this.exit = false;
        this.gravity = true;

        System.out.println("nombre de persos : "+ persos.size());
        System.out.println("nombre d'ennemis : " + ennemies.size());

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
            setDx(joystick.orientedInRight() ? -8 : 8);
            for(Objet obj : objets){obj.translateX(dx);}
            for(Ennemy en : ennemies){en.translateX(dx);}
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
                if(this.player.getBitmap()!=null){canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);}

                for(Item item : items) {
                    if (item.getActivated() && onScreen(item, 10, 10)) {
                        canvas.drawBitmap(item.getBitmap(), item.getX(), item.getY(), paint);
                    }
                }
                for(Objet obj : objets) if(obj.getActivated() && onScreen(obj, 10, 10)) canvas.drawBitmap(obj.getBitmap(), obj.getX(), obj.getY(), paint);
                for(Ennemy en : ennemies) if(en.getActivated() && onScreen(en, 10, 10)) canvas.drawBitmap(en.getBitmap(), en.getX(), en.getY(), paint);

                joystick.draw(canvas);
                if(menuButton.getIsPressed()) afficherMenuLateral(canvas);
                menuButton.draw(canvas);
                afficherScore(canvas, paint);
                drawLife(canvas, paint);
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
        joystick.update();
        player.pushPositions(new int[]{player.getX(), player.getY()});
        updateCollision(this.player);
        updateEnnemyMovement();
        gravity();
        player.setWalkingSate(false);
        if(joystick.getIsPressed()){
            boolean move = false;
            player.setDirectionRight(joystick.orientedInRight());
            if(player.getRightState() && !player.collisionInLeftWithObject()){move = true;}
            if(!player.getRightState() && !player.collisionInRightWithObject()){move = true;}

            player.setWalkingSate(move);
            if(player.getWalkingState()){moveWorld();}
        }
        updatePayerMove();
        updateAnimations();
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
    public void reset(){}
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

        boolean play = player == this.player;

        /*int[] brownBlocOffset = new int[]{play && player.getJumping() ? 2 : 5, play && player.getWalkingState() ? this.dx : 0};
        int[] yellowBlocOffset = new int[]{play && player.getJumping() ? 2 : 5, play && player.getWalkingState() ? this.dx : 0};
        int[] floorOffset = new int[]{play && player.getJumping() ? player.getSpeedVectorY() : 0};
        int[] pipeOffset = new int[]{play && player.getJumping() ? 2 : 5, play && player.getWalkingState() ? this.dx : 0};
        int[] castleOffset = new int[]{play && player.getJumping() ? player.getSpeedVectorY() : 5, play && player.getWalkingState() ? this.dx : 2};*/

        int[] brownBlocOffset = new int[]{5, Math.abs(this.dx)+2};
        int[] yellowBlocOffset = new int[]{5, Math.abs(this.dx)+2};
        int[] pipeOffset = new int[]{5, Math.abs(this.dx)+2};
        int[] castleOffset = new int[]{5, Math.abs(this.dx)+2};

        //Collision avec châteaux;
        for(Castle castle : castles){
            boolean[] tab = player.detectCollision(castle, castleOffset);
            player.setCollisionMatrix("castle", tab);
            if (tab[0] || tab[1] || tab[2] || tab[3]) {break;}
        }

        // Collision avec le sol -> Particulier
        boolean[] tabl = player.detectCollisionWithFloor(floor.get(0), new int[]{0,0});
        player.setCollisionMatrix("floor", tabl);
        if(tabl[0]){player.recalibrerY(floor.get(0));}

        //Collision avec bocs marrons
        for(BrownBloc bb : brownBlocs){
            boolean[] tab = player.detectCollision(bb, brownBlocOffset);
            player.setCollisionMatrix("brownbloc", tab);
            if(tab[0] && !player.getJumping()){player.recalibrerY(bb); break;}
            else if(tab[1] || tab[2] || tab[3]){break;}
        }

        //collision avec blocs jaunes
        for(YellowBloc yb : yellowBlocs){
            boolean[] tab = player.detectCollision(yb, yellowBlocOffset);
            player.setCollisionMatrix("yellowbloc", tab);
            if(tab[2]){
                if(!yb.getUsedState() && play){
                    yb.getItem().setActivated(true);
                    yb.getItem().setInMotion(true);
                    yb.setUsed(true);
                    yb.setBitmap("blocvide");
                }
                break;
            }
            else if(tab[0] && !player.getJumping()){player.recalibrerY(yb);break;}
            else if(tab[1] || tab[3]){break;}
        }

        //collision avec tubes
        for(Pipe pipe : pipes){
            boolean[] tab = player.detectCollision(pipe, pipeOffset);
            player.setCollisionMatrix("pipe", tab);
            if(tab[0]&& !player.getJumping()){player.recalibrerY(pipe); break;}
            else if(tab[1] || tab[2] || tab[3]){break;}
        }
        if(player == this.player){ updatePlayerSpecificCollision(); }
    }
    public void updatePlayerSpecificCollision(){
        for(Piece piece : pieces){
            if(onScreen(piece, 0, 0) && piece.getActivated()){
                boolean[] tab = player.detectCollision(piece, 0, 0);
                player.setCollisionMatrix("piece", tab);
                if(tab[0] || tab[1] || tab[2] || tab[3]){
                    if(!piece.getIsTaken()){
                        player.increasePieceCount();
                        piece.setIsTaken(true);
                        piece.setCompteurAnimation(0);
                    }
                    break;
                }
            }
        }
        for(Item item : items){
            if(onScreen(item, 0, 0) && onScreen(player, 0, 0)){
                boolean[] tab = player.detectCollision(item, new int[]{0, 0});
                player.setCollisionMatrix("item", tab);
                if(tab[0] || tab[1] || tab[2] || tab[3]){
                    if(item.getName().equals("piece") && item.getPickabe()){player.increasePieceCount();}
                    if(item.getName().equals("champignon") && item.getPickabe()){player.increaseLife();}
                    if(item.getName().equals("etoile") && item.getPickabe()){
                        player.setIsInvincible(true);
                        player.setInvincibleCompteurEtoile(1000);
                    }
                    item.setActivated(false);
                    item.setIsPickabe(false);
                    break;
                }
            }
        }
        for(Ennemy ennemy : ennemies){
            if(onScreen(ennemy, 0, 0) && ennemy.getActivated() && ennemy.getAlive()){
                boolean[] tab = player.detectCollision(ennemy);
                player.setCollisionMatrix("ennemy", tab);
                if(tab[0]){
                    if(ennemy instanceof Koopa){
                        ((Koopa) ennemy).update(0, this.player);
                    }
                    else{
                        ennemy.setAlive(false);
                        ennemy.dead();
                        player.setInvincibleCompteur(10);
                    }
                    break;
                }
                if(tab[1] || tab[2] || tab[3]){
                    if(ennemy instanceof Koopa){
                        ((Koopa) ennemy).update(1, this.player);
                    }
                    else if(!player.getIsInvincible() ){
                        player.decreaseLife();
                        player.setInvincibleCompteur(100);
                        player.setIsInvincible(true);
                    }
                    else {
                        if(player.getInvincibleCompteurEtoile() > 0){
                            ennemy.setAlive(false);
                            ennemy.dead();
                        }
                        else if(player.getInvincibleCompteur() > 0){}
                    }
                    break;
                }
            }
        }
    }
    public void updatePayerMove(){
        long ascentTime = 1_000_000_000/3;
        if(player.getJumping()){
            long currentTime = System.nanoTime();
            long deltaTime = currentTime - player.getJumpTime();
            player.jump();
            if(deltaTime <= ascentTime && !player.collisionOnBottomWithObject()){
                int dy = -(player.getJumpImpulse() - player.getGravityConstant()*player.getCompteurSaut());
                player.translateY(dy);
                player.increaseCompteurSaut();
            }
            else{
                player.setJumpingState(false);
                player.setCompteurSaut(0);
                gravity = true;
            }
        }
        else if(player.getWalkingState()){
            player.walk(10);
        }
        if(player.getIsInvincible()){
            if(player.getInvincibleCompteur()>0){player.setInvincibleCompteur(player.getInvincibleCompteur() - 1);}
            if(player.getInvincibleCompteurEtoile()>0)player.setInvincibleCompteurEtoile(player.getInvincibleCompteurEtoile() - 1);

            if(player.getInvincibleCompteur() <= 0 && player.getInvincibleCompteurEtoile() <= 0){player.setIsInvincible(false);}
        }
    }
    public void gravity(){
        for(Ennemy en : ennemies){
            if(!en.collisionOnTopWithObject() && !en.getCollisionMatrix().get("floor")[0]){en.translateY(5);}
        }
        if(gravity){
            boolean collisionOnTopOfFloor = player.getCollisionMatrix().get("floor")[0];
            if(!player.collisionOnTopWithObject() && !collisionOnTopOfFloor && gravity){
                int dy = -player.getGravityConstant()*(int)(player.getCompteurSaut()*0.9);
                player.translateY(dy);
                player.decreaseCompteurSaut();
            }
            else{
                player.setCompteurSaut(0);
            }
        }
    }
    public void updateAnimations(){
        for(Piece piece : pieces){
            if(piece.getActivated()){
                if(piece.getIsTaken()){piece.rotate(4, 50);}
                else{piece.rotate(16);}
            }
        }
        for(YellowBloc yb : yellowBlocs){
            if(yb.getItem().getInMotion() && yb.getItem().getY() > yb.getY() - yb.getWidth()){yb.getItem().translateY(-2);}
            else if(!yb.getItem().getIsCollected() && yb.getItem().getActivated()){yb.getItem().setIsPickabe(true);}
            if(yb.getItem().getPickabe() && !yb.getItem().getPicked()){yb.getItem().animer();}
        }
    }
    public void afficherScore(Canvas canvas, Paint paint){

        final int PIECE_WIDTH = (int)(displayWidth*0.04);
        final int PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1378);

        final float textSize = (float) (displayHeight * 0.05);

        float stringX = (float)(displayWidth*0.9);
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
    public void updateEnnemyMovement(){
        for(Ennemy en : ennemies){
            if(en instanceof Koopa){
                if(!((Koopa) en).getCarapaceMode() && !((Koopa) en).getLauchingMode() && en.getActivated()){
                    updateCollision(en);
                    if(en.collisionInLeftWithObject()){en.setDirectionRight(false);}
                    if(en.collisionInRightWithObject()){en.setDirectionRight(true);}
                    int dx = en.getRightState() ? 5 : -5;
                    en.translateX(dx);
                    en.walk(15);
                }
                if(((Koopa) en).getCarapaceMode()){((Koopa) en).carapaceMode();}
                if(((Koopa) en).getLauchingMode()){((Koopa) en).launch(100, 8);}
                if(!en.getAlive() && en.getActivated() && !((Koopa) en).getLauchingMode()){en.dead();}
            }
            else if(!en.getAlive() && en.getActivated()){
                en.dead();
            }
            else if(en.getAlive() && en.getActivated()){
                updateCollision(en);
                if(en.collisionInLeftWithObject()){en.setDirectionRight(false);}
                if(en.collisionInRightWithObject()){en.setDirectionRight(true);}
                int dx = en.getRightState() ? 5 : -5;
                en.translateX(dx);
                en.walk(15);
            }
        }
    }
    public boolean onScreen(Origin truc, int marginLeft, int marginRight){
        boolean gauche = truc.getX() + truc.getWidth() > -marginLeft;
        boolean droite = truc.getX() < displayWidth + marginRight;
        return gauche && droite;
    }
    public void drawLife(Canvas canvas, Paint paint){
        String life = "Vies : "+player.getLife();
        if(player.getIsInvincible()) life="Invincible";

        final float textSize = (float) (displayHeight * 0.05);

        int StringX = (int) (displayWidth/2 - textSize*life.length()/2);
        int StringY = (int) (displayHeight * 0.05 + textSize*3/2);

        paint.setTextSize(textSize);

        if(LEVEL_SELECTED == 1 || LEVEL_SELECTED == 5){paint.setColor(Color.BLACK);}
        else{paint.setColor(Color.WHITE);}

        Typeface font = ResourcesCompat.getFont(this.getContext(), R.font.dogicapixelbold);
        paint.setTypeface(font);

        canvas.drawText(life, StringX, StringY, paint);
    }

}
