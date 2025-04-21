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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.mario2d.R;
import com.example.mario2d.game.objet.Castle;
import com.example.mario2d.game.objet.Floor;
import com.example.mario2d.game.objet.Objet;
import com.example.mario2d.game.personnage.Player;
import com.example.mario2d.menu.MainActivity;
import com.example.mario2d.tool.AbstractButton;
import com.example.mario2d.tool.Joystick;

import java.util.ArrayList;

@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public boolean exit;
    public DisplayMetrics dm;
    private GameLoop gameLoop;
    public Joystick joystick;
    private int compteurMarche;
    private Bitmap characterBitmap;
    private int dx;
    private int displayWidth, displayHeight, CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_RATE,
            CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT, LEVEL_SELECTED, CHARACTER_SELECTED;

    private Boolean leftHandMode, soundEffect, music, afficherMenuLateral;
    private AbstractButton menuButton, pauseButton, retryButton, exitButton;
    public Player player;
    private ArrayList<Floor> floor;
    private ArrayList<Castle> castles;
    private float menuWidth = (float)(displayWidth*0.2);

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GameView(Context context, int displayWidth, int displayHeight, boolean leftHandMode, int LEVEL_SELECTED, Player player,
                    ArrayList<Floor> floor, ArrayList<Castle> castles){
        super(context);
        getHolder().addCallback(this);

        this.dx = 5;
        this.displayHeight = displayHeight;
        this.displayWidth = displayWidth;
        this.compteurMarche = 1;
        this.leftHandMode = leftHandMode;
        this.LEVEL_SELECTED = LEVEL_SELECTED;
        this.afficherMenuLateral = false;

        this.player = player;
        this.floor = floor;
        this.castles = castles;

        this.exit = false;

        float joystickExternalRadius = 200;
        float joystickInternalRadius = 100;
        float joystickCenterX = 300;
        float joystickCenterY = 300;
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

        setMenuButton();
        setpauseButton();
        setExitButton();
        setretryButton();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                if(joystick.isPressed(event.getX(), event.getY())){
                    joystick.setIsPressed(true);
                }
                if(pauseButton.isInside(event.getX(), event.getY())){
                    pauseButton.setPressed(true);
                }
                if(retryButton.isInside(event.getX(), event.getY())){
                    retryButton.setPressed(true);
                }
                if(exitButton.isInside(event.getX(), event.getY())){
                    exitButton.setPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()){
                    joystick.setActuator(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuatorXY();
                if(menuButton.isInside(event.getX(), event.getY())){
                    if(menuButton.getIsPressed()){
                        menuButton.setPressed(false);
                        if(!this.gameLoop.isRunning()){
                            gameLoop.start();
                        }
                    }
                    else{
                        menuButton.setPressed(true);
                        gameLoop.stop();
                    }
                }
                if(pauseButton.getIsPressed()){
                    if(!gameLoop.isRunning()){
                        gameLoop.start();
                    }
                    pauseButton.setPressed(false);
                    menuButton.setPressed(false);
                }
                if(retryButton.getIsPressed()){
                    reset();
                }
                if(exitButton.getIsPressed()){
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}
    public void moveWorld(){
        if(joystick.getIsPressed()){
            if(joystick.orientedInRight()){
                for(Floor fl : floor){
                    fl.translateX(-dx);
                    if(fl.getX()+fl.getWidth()<=0){
                        fl.setX(this.displayWidth+1);
                    }
                }
                for(Castle c : castles){c.translateX(-dx);}
            }
            else{
                for(Floor fl : floor){
                    fl.translateX(dx);
                    if(fl.getX()>=this.displayWidth){
                        fl.setX(-fl.getWidth());
                    }
                }
                for(Castle c : castles){c.translateX(dx);}
            }
        }
    }
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
    public void update(){
        joystick.update();
        if(joystick.getIsPressed()){
            if(joystick.orientedInRight()){
                player.setDirectionRight(true);
                player.walk(10);
                moveWorld();
                if(player.getX()<(int)(displayWidth/3 - 3)){
                    player.translateX(3);
                }
                if(player.getX()>(int)(displayWidth/3 + 3)){
                    player.translateX(-3);
                }
            }
            else{
                player.setDirectionRight(false);
                player.walk(10);
                moveWorld();

                player.walk(10);
                if(player.getX()<(int)(displayWidth/2 - 3)){
                    player.translateX(3);
                }
                if(player.getX()>(int)(displayWidth/2 + 3)){
                    player.translateX(-3);
                }
            }
        }
    }
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
    public void reset(){
        //TODO reset the level
    }
}
