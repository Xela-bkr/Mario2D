package com.example.mario2d.game.loop;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mario2d.game.objet.BrownBloc;
import com.example.mario2d.game.objet.Castle;
import com.example.mario2d.game.objet.Floor;
import com.example.mario2d.game.objet.Item;
import com.example.mario2d.game.objet.Objet;
import com.example.mario2d.game.objet.Piece;
import com.example.mario2d.game.objet.Pipe;
import com.example.mario2d.game.objet.YellowBloc;
import com.example.mario2d.game.personnage.Goomba;
import com.example.mario2d.game.personnage.Personnage;
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

        ArrayList<Personnage> persos = new ArrayList<Personnage>();
        final int GOOMBA_WIDTH = (int) (displayWidth*0.05);
        final int GOOMBA_HEIGHT = GOOMBA_WIDTH;

        final int GOOMBAY = displayHeight - FLOOR_HEIGHT - GOOMBA_HEIGHT - 30;
        int[] goombaX = new int[]{1500, 2700};

        for(int x : goombaX){
            Goomba g = new Goomba(this, "goomba", x, GOOMBAY, GOOMBA_WIDTH, GOOMBA_HEIGHT);
            persos.add(g);
        }
        System.out.printf("floor height : %d, floor width : %d", FLOOR_HEIGHT, FLOOR_WIDTH);
        //setCharacter(persos);

        //TODO add extra extractions for user's history data
        setContentView(new GameView(this, displayWidth, displayHeight,leftHandMode, LEVEL_SELECTED,
                player, objets, persos));
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
        String pieceKey = "piece";

        final int castleY = displayHeight - FLOOR_HEIGHT - CASTLE_HEIGHT;
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int staticBlocY = mysteryBlocY;
        final int pipeY = displayHeight - FLOOR_HEIGHT - PIPE_HEIGHT;
        final int floorY = displayHeight - FLOOR_HEIGHT;

        int[] castleX = {0, 5000};
        int[] staticBlocX = {600, 600+BLOC_WIDTH*2, 2500, 2500+BLOC_WIDTH*2};
        int[] mysteryBlocX = {600+BLOC_WIDTH, 2500 + BLOC_WIDTH};
        int[] pipeX = {2000, 3000};

        final int PIECE_WIDTH = (int)(displayWidth*0.04);
        final int PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1378);
        final int pieceY = displayHeight - FLOOR_HEIGHT - PIECE_HEIGHT;

        final int ITEM_WIDTH = PIECE_WIDTH;
        final int ITEM_HEIGHT = ITEM_WIDTH;

        int[][] pieces = new int[][]{{3000, pieceY}, {3000+PIECE_WIDTH, pieceY}, {3000+PIECE_WIDTH*2, pieceY}};

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
        int compteurMB = 0;
        for(int x : mysteryBlocX){
            YellowBloc yb = new YellowBloc(this, mysteryBlocKey, x, mysteryBlocY, BLOC_WIDTH, BLOC_HEIGHT);
            int itemX = (int)(yb.getX() + (yb.getWidth() - ITEM_WIDTH)/2);
            int itemY = (int)(yb.getY() + (yb.getHeight() - ITEM_HEIGHT)/2);

            String itemName = "piece";
            if(compteurMB == 0){itemName = "champignon";}
            if(compteurMB == 1){itemName = "etoile";}

            Item item = new Item(this, itemName, itemX, itemY, ITEM_WIDTH, ITEM_HEIGHT);
            item.setActivated(false);

            yb.setItem(item);

            objets.add(item);
            objets.add(yb);

            compteurMB++;
        }
        for(int x : pipeX){
            Pipe p = new Pipe(this, pipeKey, x, pipeY, PIPE_WIDTH, PIPE_HEIGHT);
            objets.add(p);
        }
        FLOOR_RATE = (int) ((castleX[1]+CASTLE_WIDTH)/FLOOR_WIDTH) +1;
        for(int i = -1; i<FLOOR_RATE; i++){
            Floor fl = new Floor(this, floorKey, i*FLOOR_WIDTH, floorY, FLOOR_WIDTH, FLOOR_HEIGHT);
            objets.add(fl);
        }
        for(int[] coord : pieces){
            Piece piece = new Piece(this, pieceKey, coord[0], coord[1], PIECE_WIDTH, PIECE_HEIGHT);
            objets.add(piece);
        }
    }
    public void setCharacter(ArrayList<Personnage> persos){

        final int GOOMBA_WIDTH = (int) (displayWidth*0.5);
        final int GOOMBA_HEIGHT = GOOMBA_WIDTH;

        final int GOOMBAY = displayHeight - FLOOR_HEIGHT - GOOMBA_HEIGHT - 30;
        int[] goombaX = new int[]{1500, 2700};

        for(int x : goombaX){
            Goomba g = new Goomba(this, "goomba", x, GOOMBAY, GOOMBA_WIDTH, GOOMBA_HEIGHT);
            persos.add(g);
        }
    }
}
