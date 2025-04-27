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
import com.example.mario2d.game.personnage.Koopa;
import com.example.mario2d.game.personnage.Parakoopa;
import com.example.mario2d.game.personnage.Personnage;
import com.example.mario2d.game.personnage.Player;

import java.util.ArrayList;

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
        final int GOOMBA_WIDTH = (int) (displayWidth*0.03);
        final int GOOMBA_HEIGHT = GOOMBA_WIDTH;

        final int GOOMBAY = displayHeight - FLOOR_HEIGHT - GOOMBA_HEIGHT - 30;
        int[] goombaX = new int[]{1500, 2700};

        for(int x : goombaX){
            Goomba g = new Goomba(this, "goomba", x, GOOMBAY, GOOMBA_WIDTH, GOOMBA_HEIGHT);
            persos.add(g);
        }
        final int KOOPA_WIDTH = (int) (displayWidth*0.03);
        final int KOOPA_HEIGHT = (int) (KOOPA_WIDTH*1.7458);
        final int KOOPAY = displayHeight - FLOOR_HEIGHT - KOOPA_HEIGHT - 30;
        int[] koopaX = new int[]{4000};

        for(int x : koopaX){
            Koopa koopa = new Koopa(this, "greenkoopa", x, KOOPAY, KOOPA_WIDTH, KOOPA_HEIGHT);
            persos.add(koopa);
        }

        Parakoopa pk = new Parakoopa(this, "greenpatrakoopa", 500, 100, KOOPA_WIDTH + 30, KOOPA_HEIGHT);
        persos.add(pk);
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
        String pieceKey = "piece_jaune";

        final int castleY = displayHeight - FLOOR_HEIGHT - CASTLE_HEIGHT;
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int staticBlocY = mysteryBlocY;
        final int pipeY = displayHeight - FLOOR_HEIGHT - PIPE_HEIGHT;
        final int floorY = displayHeight - FLOOR_HEIGHT;

        final int PIECE_WIDTH = (int)(displayWidth*0.03);
        final int PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1354);
        final int pieceY = displayHeight - FLOOR_HEIGHT - PIECE_HEIGHT;

        final int ITEM_WIDTH = (int) (BLOC_WIDTH*2/3);
        final int ITEM_HEIGHT = ITEM_WIDTH;

        final int nuageSimpeWidth = (int) (displayWidth*0.076);
        final int nuageSimpleHeight = (int) (0.9465*nuageSimpeWidth);

        final int petiteCollineWidth = (int) (displayWidth*0.06);
        final int petiteCollineHeight = (int) (petiteCollineWidth*0.398);
        final int petiteCollineY = displayHeight - FLOOR_HEIGHT - petiteCollineHeight;

        final int grandeCollineWidth = (int) (displayWidth * 0.15);
        final int grandeCollineHeight = (int) (grandeCollineWidth*0.4424);
        final int grandeCollineY = displayHeight - FLOOR_HEIGHT - grandeCollineHeight;

        int[] castleX = {0, 7000};
        int[] staticBlocX = {600, 600+BLOC_WIDTH*2, 2500, 2500+BLOC_WIDTH*2};
        int[] mysteryBlocX = {600+BLOC_WIDTH, 2500 + BLOC_WIDTH};
        int[] pipeX = {2000, 3000};
        int[][] pieces = new int[][]{{3300, pieceY}, {3300+PIECE_WIDTH, pieceY}, {3300+PIECE_WIDTH*2, pieceY}};

        switch (LEVEL_SELECTED){
            case 1 :
                final int platformChampiFooterWidth = (int) (displayWidth*0.06);
                final int platformChampiFooterHeight = (int) (platformChampiFooterWidth*4.3882);
                final int platformChampiFooterY = displayHeight - FLOOR_HEIGHT - platformChampiFooterHeight;

                final int platformChampiHeaderWidth = (int) (displayWidth * 0.2);
                final int platformChampiHeaderHeight = (int) (platformChampiHeaderWidth*0.1941);
                final int platformChampiHeaderY = platformChampiFooterY - platformChampiHeaderHeight;

                final int hardSquareY = displayHeight - FLOOR_HEIGHT - BLOC_HEIGHT;

                staticBlocX = new int[]{900, 900+BLOC_WIDTH*2, 2700, 2700+BLOC_WIDTH*2};
                mysteryBlocX = new int[]{900+BLOC_WIDTH, 2700+BLOC_WIDTH};
                pipeX = new int[]{};
                int[] platformChampiFooterX = new int[]{1700, 1700+platformChampiHeaderWidth+1000};
                int[] hardSquare = new int[]{3500, 3500+BLOC_WIDTH, 3500+BLOC_WIDTH*2, 3500+BLOC_WIDTH*3, 3500+BLOC_WIDTH*4};
                int[][] nuageSimpleCoord = new int[][]{{100, 100}, {300, 200}, {469, 356}, {999, 372}, {2782, 20},
                        {1033, 255}, {1477, 122}, {2000, 222}, {2400, 80}, {2888, 75}, {3468, 160}, {4024, 280}, {3700, 230},
                        {4280, 163}};
                int[] petiteCollineX = new int[]{800, 2800, 3400, 5000};
                int[] grandeCollineX = new int[]{1900, 4000};

                for(int x : petiteCollineX){
                    objets.add(new Objet(this, "colline_petite", x, petiteCollineY, petiteCollineWidth, petiteCollineHeight));
                }
                for(int x : grandeCollineX){
                    objets.add(new Objet(this, "colline_grande", x, grandeCollineY, grandeCollineWidth, grandeCollineHeight));
                }
                for(int i = 0; i<nuageSimpleCoord.length; i++){
                    objets.add(new Objet(this, "nuage", nuageSimpleCoord[i][0], nuageSimpleCoord[i][1], nuageSimpeWidth, nuageSimpleHeight));
                }
                for(int x : platformChampiFooterX){
                    BrownBloc footer = new BrownBloc(this, "pillier_champi_platforme", x, platformChampiFooterY, platformChampiFooterWidth, platformChampiFooterHeight);
                    BrownBloc header = new BrownBloc(this, "champi_platforme", x-platformChampiHeaderWidth/2 + platformChampiFooterWidth/2, platformChampiHeaderY, platformChampiHeaderWidth, platformChampiHeaderHeight);
                    objets.add(footer);
                    objets.add(header);
                }
                for(int x : hardSquare){
                    BrownBloc hs = new BrownBloc(this, "hardbloc", x, hardSquareY, BLOC_WIDTH, BLOC_HEIGHT);
                    objets.add(hs);
                }
                break;
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

        final int GOOMBA_WIDTH = (int) (displayWidth*0.05);
        final int GOOMBA_HEIGHT = GOOMBA_WIDTH;

        final int GOOMBAY = displayHeight - FLOOR_HEIGHT - GOOMBA_HEIGHT - 30;
        int[] goombaX = new int[]{1500, 2700};

        for(int x : goombaX){
            Goomba g = new Goomba(this, "goomba", x, GOOMBAY, GOOMBA_WIDTH, GOOMBA_HEIGHT);
            persos.add(g);
        }
        final int KOOPA_WIDTH = (int) (displayWidth*0.05);
        final int KOOPA_HEIGHT = (int) (KOOPA_WIDTH*1.7458);
        final int KOOPAY = displayHeight - FLOOR_HEIGHT - KOOPA_HEIGHT - 30;
        int[] koopaX = new int[]{4000};

        for(int x : koopaX){
            Koopa koopa = new Koopa(this, "greenkoopa", x, KOOPAY, KOOPA_WIDTH, KOOPA_HEIGHT);
            persos.add(koopa);
        }
    }
}
