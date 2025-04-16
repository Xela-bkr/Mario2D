package com.example.mario2d.game.loop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.mario2d.game.objet.Floor;
import com.example.mario2d.game.personnage.Player;
import com.example.mario2d.R;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop gameLoop;
    private Bitmap characterBitmap;
    private int displayWidth, displayHeight, CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_RATE,
            CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT, LEVEL_SELECTED, CHARACTER_SELECTED;

    private Boolean leftHandMode, soundEffect, music;
    private Player player;
    private ArrayList<Floor> floor;
    private int x, y;

    public GameView(Context context, int displayWidth, int displayHeight, int CHARACTER_WIDTH, int CHARACTER_HEIGHT, int FLOOR_WIDTH,
                    int FLOOR_HEIGHT, int FLOOR_RATE, int CASTLE_WIDTH, int CASTLE_HEIGHT, int BLOC_WIDTH, int BLOC_HEIGHT,
                    int PIPE_WIDTH, int PIPE_HEIGHT, int LEVEL_SELECTED, int CHARACTER_SELECTED, boolean leftHandMode,
                    boolean soundEffect, boolean music) {
        super(context);
        getHolder().addCallback(this);

        this.displayHeight = displayHeight; this.displayWidth = displayWidth; this.CHARACTER_HEIGHT = CHARACTER_HEIGHT;
        this.CHARACTER_WIDTH = CHARACTER_WIDTH;this.FLOOR_HEIGHT = FLOOR_HEIGHT; this.FLOOR_WIDTH = FLOOR_WIDTH;
        this.FLOOR_RATE = FLOOR_RATE; this.CASTLE_WIDTH = CASTLE_WIDTH;this.CASTLE_HEIGHT = CASTLE_HEIGHT;
        this.BLOC_HEIGHT = BLOC_HEIGHT; this.BLOC_WIDTH = BLOC_WIDTH; this.PIPE_HEIGHT = PIPE_HEIGHT;this.PIPE_WIDTH = PIPE_WIDTH;
        this.LEVEL_SELECTED = LEVEL_SELECTED; this.CHARACTER_SELECTED = CHARACTER_SELECTED;this.leftHandMode = leftHandMode;
        this.soundEffect = soundEffect; this.music=music;

        this.x = 0; this.y = 100;
        this.characterBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mario_arret_droite);

        if(CHARACTER_SELECTED==1){
            this.player = new Player(getContext(), "mario", 100, 100, CHARACTER_WIDTH, CHARACTER_HEIGHT);
            player.setBitmap("mario_arret_droite");
        }
        else{
            this.player = new Player(getContext(), "luigi", 100, 100, CHARACTER_WIDTH, CHARACTER_HEIGHT);
            player.setBitmap("luigi_arret_droite");
        }
    }
    public GameView(Context context, Player player, ArrayList<Floor> floor){
        super(context);
        this.player = player;
        this.floor = floor;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Log.d("GameView", "surfaceCreated called");
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}
    public void render() {
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            if (canvas != null) {
                Paint paint = new Paint();
                canvas.drawColor(Color.BLACK);
                for(int i = 0; i<floor.size(); i++){
                    int x = floor.get(i).getX();
                    int y = floor.get(i).getY();
                    Bitmap b = floor.get(i).getBitmap();
                    canvas.drawBitmap(b, x, y, paint);
                }
                if(this.player.getBitmap()!=null){
                    canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
                }
                //canvas.drawBitmap(character, x, y, paint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }
    public void update(){}
    public void setAllResource(){

    }
}
