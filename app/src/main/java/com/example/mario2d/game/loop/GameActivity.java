package com.example.mario2d.game.loop;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mario2d.game.objet.BrownBloc;
import com.example.mario2d.game.objet.Castle;
import com.example.mario2d.game.objet.Floor;
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

        Player player;
        int x = CASTLE_WIDTH + 10;
        int y = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - 1;
        String name;
        if(CHARACTER_SELECTED==1){name = "mario";}
        else{name="luigi";}
        player = new Player(this, name, x, y, CHARACTER_WIDTH, CHARACTER_HEIGHT);

        ArrayList<Floor> floor = new ArrayList<Floor>();
        setFloor(floor);

        ArrayList<Castle> castles = new ArrayList<Castle>();
        setCastles(castles);

        ArrayList<BrownBloc> brownBlocs = new ArrayList<BrownBloc>();
        ArrayList<YellowBloc> yellowBlocs = new ArrayList<YellowBloc>();
        setBlocs(brownBlocs, yellowBlocs);

        ArrayList<Pipe> pipes = new ArrayList<Pipe>();
        setPipes(pipes);
        //TODO add extra extractions for user's history data
        setContentView(new GameView(this, displayWidth, displayHeight,leftHandMode, LEVEL_SELECTED,
                player, floor, castles, brownBlocs, yellowBlocs, pipes));
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
    public void setFloor(ArrayList<Floor> floor){
        int y = displayHeight - FLOOR_HEIGHT;
        String key;
        switch (LEVEL_SELECTED){
            case 1 :
                key="redbrick"; break;
            case 2 :
                key="greenbrick"; break;
            case 3 :
                key="yellowbrick"; break;
            case 4 :
                key="darkbrick"; break;
            case 5 :
                key="nuageplatform"; break;
            default :
                key="bloc"; break;
        }
        for(int i = 0; i<FLOOR_RATE+1; i++){
            int x = i*FLOOR_WIDTH;
            Floor f = new Floor(this, key, x, y, FLOOR_WIDTH, FLOOR_HEIGHT);
            floor.add(f);
        }
    }
    public void setCastles(ArrayList<Castle> castles){
        final int castleStartX = 0;
        final int castleEndX = displayWidth*10;
        final int castleY = displayHeight - FLOOR_HEIGHT - CASTLE_HEIGHT;

        String name = "castle";
        if(LEVEL_SELECTED == 2){name="greencastle";}

        Castle castleStart = new Castle(this, name, castleStartX, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);
        Castle castleEnd = new Castle(this, name, castleEndX, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);

        castles.add(castleStart);
        castles.add(castleEnd);
    }
    public void setBlocs(ArrayList<BrownBloc> brownBlocs, ArrayList<YellowBloc> yellowBlocs){

        String blocName = "bloc";

        int Y_COORD = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;

        int[] BX = new int[1];
        int[] YX = new int[1];

        switch (LEVEL_SELECTED){
            case 1 :
                blocName = "brownbloc";
                BX = new int[]{600, 600 + BLOC_WIDTH * 2};
                YX = new int[]{600+BLOC_WIDTH};
                break;
            case 2 :
                BX = new int[]{600, 600+BLOC_WIDTH*2, 600+BLOC_WIDTH*4};
                YX = new int[]{600+BLOC_WIDTH, 600+BLOC_WIDTH*3};
                blocName = "greenbloc";
                break;
            case 3 :
                BX = new int[]{600, 600+BLOC_WIDTH*2, 600+BLOC_WIDTH*4, 600+BLOC_WIDTH*6};
                YX = new int[]{600+BLOC_WIDTH, 600+BLOC_WIDTH*3, 600+BLOC_WIDTH*5};
                blocName = "goldenbloc";
                break;
            case 4 :
                BX = new int[]{600, 600+BLOC_WIDTH*2, 600+BLOC_WIDTH*4, 600+BLOC_WIDTH*6, 600+BLOC_WIDTH*8};
                YX = new int[]{600+BLOC_WIDTH, 600+BLOC_WIDTH*3, 600+BLOC_WIDTH*5, 600+BLOC_WIDTH*7};
                blocName = "darkbloc";
                break;
            case 5 :
                BX = new int[]{600, 600+BLOC_WIDTH*2, 600+BLOC_WIDTH*4, 600+BLOC_WIDTH*6, 600+BLOC_WIDTH*8, 600+BLOC_WIDTH*10};
                YX = new int[]{600+BLOC_WIDTH, 600+BLOC_WIDTH*3, 600+BLOC_WIDTH*5, 600+BLOC_WIDTH*7, 600+BLOC_WIDTH*9};
                blocName = "brownbloc";
                break;

        }
        for(int i = 0; i<BX.length; i++){
            BrownBloc b = new BrownBloc(this, blocName, BX[i], Y_COORD, BLOC_WIDTH, BLOC_HEIGHT);
            brownBlocs.add(b);
        }
        for(int i = 0; i<YX.length; i++){
            YellowBloc b = new YellowBloc(this, "bloc", YX[i], Y_COORD, BLOC_WIDTH, BLOC_HEIGHT);
            yellowBlocs.add(b);
        }
    }
    public void setPipes(ArrayList<Pipe> pipes){

        int Y = displayHeight - FLOOR_HEIGHT - PIPE_HEIGHT;
        int[] X  = new int[0];

        switch (LEVEL_SELECTED){
            case 1 :
                X = new int[]{1500, 2500};
                break;
            case 2 :
                X = new int[]{1500, 2500};
                break;
            case 3 :
                X = new int[]{1500, 2500};
                break;
            case 4 :
                X = new int[]{1500, 2500};
                break;
            case 5 :
                X = new int[]{1500, 2500};
                break;
        }
        for(int i = 0 ; i<X.length; i++){
            Pipe p = new Pipe(this, "greenpipe", X[i], Y, PIPE_WIDTH, PIPE_HEIGHT);
            pipes.add(p);
        }
    }
}
