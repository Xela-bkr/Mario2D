package com.example.mario2d.game.loop;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mario2d.game.objet.BrownBloc;
import com.example.mario2d.game.objet.Castle;
import com.example.mario2d.game.objet.Floor;
import com.example.mario2d.game.objet.Objet;
import com.example.mario2d.game.objet.Pipe;
import com.example.mario2d.game.objet.YellowBloc;
import com.example.mario2d.game.personnage.Player;

import java.util.ArrayList;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    private int displayWidth, displayHeight, CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_RATE,
            CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT, LEVEL_SELECTED, CHARACTER_SELECTED;

    private Boolean leftHandMode, soundEffect, music;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        this.setExtraData(extras);

        String name = CHARACTER_SELECTED == 1 ? "mario" : "luigi";
        int playerX = (int)(displayWidth/2 - CHARACTER_WIDTH/2);
        int playerY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - 1;
        Player player = new Player(this, name, playerX, playerY, CHARACTER_WIDTH, CHARACTER_HEIGHT);

        ArrayList<Objet> objets = new ArrayList<Objet>();
        setObjets(objets);
        //TODO add extra extractions for user's history data
        setContentView(new GameView(this, displayWidth, displayHeight,leftHandMode, LEVEL_SELECTED,
                player, objets));
    }
    public void setExtraData(Bundle extras){
        displayWidth = extras.getInt("displayWidth");
        displayHeight = extras.getInt("displayHeight");
        CHARACTER_WIDTH = extras.getInt("characterWidth");
        CHARACTER_HEIGHT = extras.getInt("characterHeight");
        FLOOR_WIDTH = extras.getInt("floorWidth");
        FLOOR_HEIGHT = extras.getInt("floorHeight");
        FLOOR_RATE = extras.getInt("floorRate");
        CASTLE_WIDTH = extras.getInt("castleWidth");
        CASTLE_HEIGHT = extras.getInt("castleHeight");
        BLOC_WIDTH = extras.getInt("blocWidth");
        BLOC_HEIGHT = extras.getInt("blocHeight");
        PIPE_WIDTH = extras.getInt("pipeWidth");
        PIPE_HEIGHT = extras.getInt("pipeHeight");
        LEVEL_SELECTED = extras.getInt("levelSelected");
        CHARACTER_SELECTED = extras.getInt("selectedCharacter");
        soundEffect = extras.getBoolean("soundEffect");
        leftHandMode = extras.getBoolean("leftHandMode");
        music = extras.getBoolean("music");
    }
    public void setObjets(ArrayList<Objet> objets){

        String castleKey = "castle";
        String mysteryBlocKey = "bloc";
        String staticBlocKey = "brownbloc";
        String pipeKey = "greenpipe";
        String floorKey = "redbrick";

        final int castleY = displayHeight - FLOOR_HEIGHT - CASTLE_HEIGHT;
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int staticBlocY = mysteryBlocY;
        final int pipeY = displayHeight - FLOOR_HEIGHT - PIPE_HEIGHT;
        final int floorY = displayHeight - FLOOR_HEIGHT;

        int[] castleX = {0, 5000};
        int[] staticBlocX = {600, 600+BLOC_WIDTH*2};
        int[] mysteryBlocX = {600+BLOC_WIDTH};
        int[] pipeX = {2000, 3000};

        switch (LEVEL_SELECTED){
            case 2 :
                castleKey = "greencastle";
                staticBlocKey = "greenbloc";
                floorKey = "greenbrick";
                break;
            case 3 :
                staticBlocKey = "goldenbloc";
                floorKey = "yellowbrick";
                break;
            case 4 :
                staticBlocKey = "darkbloc";
                floorKey = "darkbrick";
                break;
            case 5 :
                floorKey = "nuageplatform";
                break;
        }

        for(int x : castleX){
            Castle c = new Castle(this, castleKey, x, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);
            objets.add(c);
        }
        for(int x : staticBlocX){
            BrownBloc b = new BrownBloc(this, staticBlocKey, x, staticBlocY, BLOC_WIDTH, BLOC_HEIGHT);
            objets.add(b);
        }
        for(int x : mysteryBlocX){
            YellowBloc yb = new YellowBloc(this, mysteryBlocKey, x, mysteryBlocY, BLOC_WIDTH, BLOC_HEIGHT);
            objets.add(yb);
        }
        for(int x : pipeX){
            Pipe p = new Pipe(this, pipeKey, x, pipeY, PIPE_WIDTH, PIPE_HEIGHT);
            objets.add(p);
        }
        for(int i = 0; i<FLOOR_RATE; i++){
            Floor fl = new Floor(this, floorKey, i*FLOOR_WIDTH, floorY, FLOOR_WIDTH, FLOOR_HEIGHT);
            objets.add(fl);
        }
    }
}
