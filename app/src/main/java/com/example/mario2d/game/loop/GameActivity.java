package com.example.mario2d.game.loop;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mario2d.game.objet.Floor;
import com.example.mario2d.game.personnage.Player;

import java.util.ArrayList;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    private int displayWidth, displayHeight, CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_RATE,
            CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT, LEVEL_SELECTED, CHARACTER_SELECTED;

    private Boolean leftHandMode, soundEffect, music;
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

        //TODO add extra extractions for user's history data

        //setContentView(new GameView(this, displayWidth, displayHeight, CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH,
                //FLOOR_HEIGHT, FLOOR_RATE, CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT,
                //LEVEL_SELECTED, CHARACTER_SELECTED, leftHandMode, soundEffect, music));
        setContentView(new GameView(this, player, floor));
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
                key="redBrick"; break;
            case 2 :
                key="greenBrick"; break;
            case 3 :
                key="yellowBrick"; break;
            case 4 :
                key="darkBrick"; break;
            case 5 : key="nuagePlatform"; break;
            default : key="bloc"; break;
        }
        for(int i = 0; i<FLOOR_RATE+1; i++){
            int x = i*FLOOR_WIDTH;
            Floor f = new Floor(this, key, x, y, FLOOR_WIDTH, FLOOR_HEIGHT);
            floor.add(f);
        }
    }
}
