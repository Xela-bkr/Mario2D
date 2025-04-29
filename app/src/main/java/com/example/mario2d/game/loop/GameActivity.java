package com.example.mario2d.game.loop;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    private int displayWidth, displayHeight, CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_RATE,
            CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT, LEVEL_SELECTED,
            CHARACTER_SELECTED, PIECE_WIDTH, PIECE_HEIGHT, dx;

    private Boolean leftHandMode, soundEffect, music;
    private ArrayList<Objet> objets = new ArrayList<Objet>();
    private ArrayList<Personnage> persos = new ArrayList<Personnage>();

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
        final int KOOPAY = displayHeight - FLOOR_HEIGHT - KOOPA_HEIGHT - 3*dx;
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

        this.dx = (int) (displayWidth*0.01);

        final int castleY = displayHeight - FLOOR_HEIGHT - CASTLE_HEIGHT;
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int staticBlocY = mysteryBlocY;
        final int pipeY = displayHeight - FLOOR_HEIGHT - PIPE_HEIGHT;
        final int floorY = displayHeight - FLOOR_HEIGHT;

        this.PIECE_WIDTH = (int)(displayWidth*0.03);
        this.PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1354);
        final int pieceY = displayHeight - FLOOR_HEIGHT - PIECE_HEIGHT - dx;

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

        final int platformChampiFooterWidth = (int) (displayWidth*0.04);
        final int platformChampiFooterHeight = (int) (platformChampiFooterWidth*4);
        final int platformChampiFooterY = displayHeight - FLOOR_HEIGHT - platformChampiFooterHeight;

        final int platformChampiFooterWidthMedium = (int) (displayWidth*0.05);
        final int platformChampiFooterHeightMedium = (int) (platformChampiFooterWidthMedium*2.3);
        final int platformChampiFooterMediumY = displayHeight - FLOOR_HEIGHT - platformChampiFooterHeightMedium;

        final int platformChampiHeaderWidth = (int) (displayWidth * 0.2);
        final int platformChampiHeaderHeight = (int) (platformChampiHeaderWidth*0.1941);
        final int platformChampiHeaderY = platformChampiFooterY - platformChampiHeaderHeight;

        final int platformChampiHeaderWidthMedium = (int) (displayWidth * 0.15);
        final int platformChampiHeaderHeightMedium = (int) (platformChampiHeaderWidthMedium*0.1941);
        final int platformChampiHeaderYMedium = platformChampiFooterMediumY - platformChampiHeaderHeightMedium;

        final int hardsquareWidth = (int) (BLOC_WIDTH*0.8);
        final int hardSquareHeight = hardsquareWidth;
        final int hardSquareY = displayHeight - FLOOR_HEIGHT - hardSquareHeight;

        switch (LEVEL_SELECTED){
            case 1 :
                String[][] objetResource = {
                        {"Objet", "24", "0", "colline_petite"},
                        {"Objet", "80", "0", "colline_grande"},
                        {"Objet", "1", "2", "nuage"},
                        {"Objet", "1", "7", "nuage"},
                        {"Objet", "120", "0", "colline_grande"},
                        {"Objet", "100", "0", "hills1"},
                        {"BrownBloc","30", "4", "brownbloc", "piece_jaune"},
                        {"YellowBloc","34", "4", "bloc", "champignon"},
                        {"BrownBloc","38", "4", "brownbloc", "piece_jaune"},
                        {"BrownBloc", "74", "0", "pillier_champi_platforme"},
                        {"BrownBloc", "69", "0", "champi_platforme", "piece_jaune"},
                        {"Castle", "0", "0", "castle"},
                        {"Castle", "400", "0", "castle"},
                        {"Piece", "50", "0", "piece_jaune"},
                        {"BrownBloc", "100", "1", "pillier_champi_platforme"},
                        {"BrownBloc", "92", "1", "champi_platforme", "piece_jaune"},
                        {"BrownBloc", "125", "0", "pillier_champi_platforme"},
                        {"BrownBloc", "120", "0", "champi_platforme", "piece_jaune"},
                        {"BrownBloc","130", "1", "hardbloc", "piece_jaune"},
                        {"YellowBloc","160", "4", "bloc", "champignon"}};

                for(String[] info : objetResource){
                    int x = Integer.valueOf(info[1])*dx;
                    int y = Integer.valueOf(info[2]);
                    String name = info[3];
                    if(info[0].equals("BrownBloc")){
                        int width = BLOC_WIDTH;
                        int height = BLOC_HEIGHT;
                        if(name.equals("brownbloc")){
                            y = displayHeight - FLOOR_HEIGHT - y*BLOC_HEIGHT;
                        }
                        else if(name.equals("hardbloc")){
                            y = displayHeight - FLOOR_HEIGHT - y*hardSquareHeight;
                            width = hardsquareWidth;
                            height = hardSquareHeight;
                        }
                        else if(name.equals("pillier_champi_platforme")){
                            if(y==0){
                                y = platformChampiFooterMediumY;
                                width = platformChampiFooterWidthMedium;
                                height = platformChampiFooterHeightMedium;
                            }
                            else if(y == 1){
                                y = platformChampiFooterY;
                                width = platformChampiFooterWidth;
                                height = platformChampiFooterHeight;
                            }
                        }
                        else if(name.equals("champi_platforme")){
                            if(y==0){
                                y = platformChampiHeaderYMedium;
                                width = platformChampiHeaderWidthMedium;
                                height = platformChampiHeaderHeightMedium;
                            }
                            else if(y == 1){
                                y = platformChampiHeaderY;
                                width = platformChampiHeaderWidth;
                                height = platformChampiHeaderHeight;
                            }
                        }
                        BrownBloc bb = new BrownBloc(this, name, x, y, width, height);
                        if(info.length>4){
                            String itemName = info[4];
                            if(name.equals("brownbloc") || name.equals("hardbloc")){
                                int itemWidth = PIECE_WIDTH;
                                int itemHeight = itemWidth;
                                int itemX = x + (width - itemWidth)/2;
                                int itemY = y - itemHeight - dx;

                                Piece item = new Piece(this, itemName, itemX, itemY, itemWidth, itemHeight);
                                objets.add(item);
                            }
                            else if(name.equals("champi_platforme")){
                                int itemWidth = PIECE_WIDTH;
                                int itemHeight = itemWidth;
                                int itemRate = width/itemWidth;
                                int itemY = y - itemHeight - dx;
                                drawPieceLine(itemName, x, x+width, itemY, PIECE_WIDTH, PIECE_HEIGHT, objets);

                            }
                        }
                        objets.add(bb);
                    }
                    else if(info[0].equals("YellowBloc")){

                        int width = BLOC_WIDTH;
                        int height = BLOC_HEIGHT;
                        y = displayHeight - FLOOR_HEIGHT - y*BLOC_HEIGHT;

                        YellowBloc yb = new YellowBloc(this, name, x, y, BLOC_WIDTH, BLOC_HEIGHT);
                        if(info.length>4){
                            String item = info[4];
                            int itemWidth = width*2/3;
                            int itemHeight = itemWidth;
                            int itemX = x + (width - itemWidth)/2;
                            int itemY = y + (height - itemHeight)/2;

                            Item ite = new Item(this, item, itemX, itemY, itemWidth, itemHeight );
                            ite.setActivated(false);
                            yb.setItem(ite);
                            objets.add(ite);
                        }
                        objets.add(yb);
                    }
                    else if(info[0].equals("Castle")){
                        Castle castle = new Castle(this, name, x, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);
                        objets.add(castle);
                        if (x > 0) {
                            int floorRate = (x+CASTLE_WIDTH)/FLOOR_WIDTH +1;
                            for(int i = 0; i<floorRate; i++){

                                Floor floor = new Floor(this, "redbrick", i*FLOOR_WIDTH, floorY, FLOOR_WIDTH, FLOOR_HEIGHT);
                                objets.add(floor);
                            }
                        }
                    }
                    else if(info[0].equals("Pipe")){
                        Pipe pipe = new Pipe(this, name, x, pipeY, PIPE_WIDTH, PIPE_HEIGHT);
                        objets.add(pipe);
                    }
                    else if(info[0].equals("Piece")){
                        if(y == 0){y = displayHeight - FLOOR_HEIGHT - PIECE_HEIGHT - dx;}
                        Piece piece = new Piece(this, name, x, y, PIECE_WIDTH, PIECE_HEIGHT);
                        objets.add(piece);
                    }
                    else if(info[0].equals("Objet")){
                        int width = 0;
                        int height = 0;
                        if(name.equals("nuage")){
                            y = y*dx;
                            width = nuageSimpeWidth;
                            height = nuageSimpleHeight;
                        }
                        else if(name.equals("colline_petite")){
                            width = petiteCollineWidth;
                            height = petiteCollineHeight;
                            y = petiteCollineY;
                        }
                        else if(name.equals("colline_grande")){
                            width = grandeCollineWidth;
                            height = grandeCollineHeight;
                            y = grandeCollineY;
                        }
                        else if(name.equals("hills1")){
                            width = (int) (displayWidth*0.3);
                            height = width;
                            y = displayHeight - FLOOR_HEIGHT - height;
                        }
                        Objet objet = new Objet(this, name, x, y, width, height);
                        objets.add(objet);
                    }
                }
                drawLine("BrownBloc", "hardbloc", 130*dx, 190*dx, hardSquareY, hardsquareWidth, hardSquareHeight, false, objets);
                drawScale("BrownBloc", "hardbloc", 187*dx, 7, hardSquareY, hardsquareWidth, hardSquareHeight, objets);
                drawLine("BrownBloc", "hardbloc", 209*dx, 250*dx, floorY-7*hardSquareHeight, hardsquareWidth, hardSquareHeight, false, objets);
                drawPieceSquare(212*dx, pieceY, 5, objets);
                return;
            case 2 :
                String[][] objetResource2 = {
                        {"Objet", "24", "0", "colline_petite"},
                        {"Objet", "80", "0", "colline_grande"},
                        {"Objet", "1", "2", "nuage"},
                        {"Objet", "1", "7", "nuage"},
                        {"Objet", "120", "0", "colline_grande"},
                        {"Objet", "100", "0", "hills1"},
                        {"BrownBloc","30", "4", "greenbloc", "piece_jaune"},
                        {"YellowBloc","34", "4", "bloc", "champignon"},
                        {"BrownBloc","38", "4", "greenbloc", "piece_jaune"},
                        {"BrownBloc", "74", "0", "pillier_champi_platforme"},
                        {"BrownBloc", "69", "0", "champi_platforme", "piece_jaune"},
                        {"Castle", "0", "0", "greencastle"},
                        {"Castle", "400", "0", "greencastle"},
                        {"Piece", "50", "0", "piece_jaune"},
                        {"BrownBloc", "100", "1", "pillier_champi_platforme"},
                        {"BrownBloc", "92", "1", "champi_platforme", "piece_jaune"},
                        {"BrownBloc", "125", "0", "pillier_champi_platforme"},
                        {"BrownBloc", "120", "0", "champi_platforme", "piece_jaune"},
                        {"BrownBloc","130", "1", "hardbloc", "piece_jaune"},
                        {"YellowBloc","160", "4", "bloc", "champignon"}};

                for(String[] info : objetResource2){
                    int x = Integer.valueOf(info[1])*dx;
                    int y = Integer.valueOf(info[2]);
                    String name = info[3];
                    if(info[0].equals("BrownBloc")){
                        int width = BLOC_WIDTH;
                        int height = BLOC_HEIGHT;
                        if(name.equals("greenbloc")){
                            y = displayHeight - FLOOR_HEIGHT - y*BLOC_HEIGHT;
                        }
                        else if(name.equals("hardbloc")){
                            y = displayHeight - FLOOR_HEIGHT - y*hardSquareHeight;
                            width = hardsquareWidth;
                            height = hardSquareHeight;
                        }
                        else if(name.equals("pillier_champi_platforme")){
                            if(y==0){
                                y = platformChampiFooterMediumY;
                                width = platformChampiFooterWidthMedium;
                                height = platformChampiFooterHeightMedium;
                            }
                            else if(y == 1){
                                y = platformChampiFooterY;
                                width = platformChampiFooterWidth;
                                height = platformChampiFooterHeight;
                            }
                        }
                        else if(name.equals("champi_platforme")){
                            if(y==0){
                                y = platformChampiHeaderYMedium;
                                width = platformChampiHeaderWidthMedium;
                                height = platformChampiHeaderHeightMedium;
                            }
                            else if(y == 1){
                                y = platformChampiHeaderY;
                                width = platformChampiHeaderWidth;
                                height = platformChampiHeaderHeight;
                            }
                        }
                        BrownBloc bb = new BrownBloc(this, name, x, y, width, height);
                        if(info.length>4){
                            String itemName = info[4];
                            if(name.equals("greenbloc") || name.equals("hardbloc")){
                                int itemWidth = PIECE_WIDTH;
                                int itemHeight = itemWidth;
                                int itemX = x + (width - itemWidth)/2;
                                int itemY = y - itemHeight - dx;

                                Piece item = new Piece(this, itemName, itemX, itemY, itemWidth, itemHeight);
                                objets.add(item);
                            }
                            else if(name.equals("champi_platforme")){
                                int itemWidth = PIECE_WIDTH;
                                int itemHeight = itemWidth;
                                int itemRate = width/itemWidth;
                                int itemY = y - itemHeight - dx;
                                drawPieceLine(itemName, x, x+width, itemY, PIECE_WIDTH, PIECE_HEIGHT, objets);

                            }
                        }
                        objets.add(bb);
                    }
                    else if(info[0].equals("YellowBloc")){

                        int width = BLOC_WIDTH;
                        int height = BLOC_HEIGHT;
                        y = displayHeight - FLOOR_HEIGHT - y*BLOC_HEIGHT;

                        YellowBloc yb = new YellowBloc(this, name, x, y, BLOC_WIDTH, BLOC_HEIGHT);
                        if(info.length>4){
                            String item = info[4];
                            int itemWidth = width*2/3;
                            int itemHeight = itemWidth;
                            int itemX = x + (width - itemWidth)/2;
                            int itemY = y + (height - itemHeight)/2;

                            Item ite = new Item(this, item, itemX, itemY, itemWidth, itemHeight );
                            ite.setActivated(false);
                            yb.setItem(ite);
                            objets.add(ite);
                        }
                        objets.add(yb);
                    }
                    else if(info[0].equals("Castle")){
                        Castle castle = new Castle(this, name, x, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);
                        objets.add(castle);
                        if (x > 0) {
                            int floorRate = (x+CASTLE_WIDTH)/FLOOR_WIDTH +1;
                            for(int i = 0; i<floorRate; i++){

                                Floor floor = new Floor(this, "greenbrick", i*FLOOR_WIDTH, floorY, FLOOR_WIDTH, FLOOR_HEIGHT);
                                objets.add(floor);
                            }
                        }
                    }
                    else if(info[0].equals("Pipe")){
                        Pipe pipe = new Pipe(this, name, x, pipeY, PIPE_WIDTH, PIPE_HEIGHT);
                        objets.add(pipe);
                    }
                    else if(info[0].equals("Piece")){
                        if(y == 0){y = displayHeight - FLOOR_HEIGHT - PIECE_HEIGHT - dx;}
                        Piece piece = new Piece(this, name, x, y, PIECE_WIDTH, PIECE_HEIGHT);
                        objets.add(piece);
                    }
                    else if(info[0].equals("Objet")){
                        int width = 0;
                        int height = 0;
                        if(name.equals("nuage")){
                            y = y*dx;
                            width = nuageSimpeWidth;
                            height = nuageSimpleHeight;
                        }
                        else if(name.equals("colline_petite")){
                            width = petiteCollineWidth;
                            height = petiteCollineHeight;
                            y = petiteCollineY;
                        }
                        else if(name.equals("colline_grande")){
                            width = grandeCollineWidth;
                            height = grandeCollineHeight;
                            y = grandeCollineY;
                        }
                        else if(name.equals("hills1")){
                            width = (int) (displayWidth*0.3);
                            height = width;
                            y = displayHeight - FLOOR_HEIGHT - height;
                        }
                        Objet objet = new Objet(this, name, x, y, width, height);
                        objets.add(objet);
                    }
                }
                drawLine("BrownBloc", "hardbloc", 130*dx, 190*dx, hardSquareY, hardsquareWidth, hardSquareHeight, false, objets);
                drawScale("BrownBloc", "hardbloc", 187*dx, 7, hardSquareY, hardsquareWidth, hardSquareHeight, objets);
                drawLine("BrownBloc", "hardbloc", 209*dx, 250*dx, floorY-7*hardSquareHeight, hardsquareWidth, hardSquareHeight, false, objets);
                drawPieceSquare(212*dx, pieceY, 5, objets);
                break;
            case 3 :
                String[][] objetResource3 = {
                        {"Objet", "24", "0", "colline_petite"},
                        {"Objet", "80", "0", "colline_grande"},
                        {"Objet", "1", "2", "nuage"},
                        {"Objet", "1", "7", "nuage"},
                        {"Objet", "120", "0", "colline_grande"},
                        {"Objet", "100", "0", "hills1"},
                        {"BrownBloc","30", "4", "goldenbloc", "piece_jaune"},
                        {"YellowBloc","34", "4", "bloc", "champignon"},
                        {"BrownBloc","38", "4", "goldenbloc", "piece_jaune"},
                        {"BrownBloc", "74", "0", "pillier_champi_platforme"},
                        {"BrownBloc", "69", "0", "champi_platforme", "piece_jaune"},
                        {"Castle", "0", "0", "castle"},
                        {"Castle", "400", "0", "castle"},
                        {"Piece", "50", "0", "piece_jaune"},
                        {"BrownBloc", "100", "1", "pillier_champi_platforme"},
                        {"BrownBloc", "92", "1", "champi_platforme", "piece_jaune"},
                        {"BrownBloc", "125", "0", "pillier_champi_platforme"},
                        {"BrownBloc", "120", "0", "champi_platforme", "piece_jaune"},
                        {"BrownBloc","130", "1", "hardbloc", "piece_jaune"},
                        {"YellowBloc","160", "4", "bloc", "champignon"}};

                for(String[] info : objetResource3){
                    int x = Integer.valueOf(info[1])*dx;
                    int y = Integer.valueOf(info[2]);
                    String name = info[3];
                    if(info[0].equals("BrownBloc")){
                        int width = BLOC_WIDTH;
                        int height = BLOC_HEIGHT;
                        if(name.equals("brownbloc")){
                            y = displayHeight - FLOOR_HEIGHT - y*BLOC_HEIGHT;
                        }
                        else if(name.equals("hardbloc")){
                            y = displayHeight - FLOOR_HEIGHT - y*hardSquareHeight;
                            width = hardsquareWidth;
                            height = hardSquareHeight;
                        }
                        else if(name.equals("pillier_champi_platforme")){
                            if(y==0){
                                y = platformChampiFooterMediumY;
                                width = platformChampiFooterWidthMedium;
                                height = platformChampiFooterHeightMedium;
                            }
                            else if(y == 1){
                                y = platformChampiFooterY;
                                width = platformChampiFooterWidth;
                                height = platformChampiFooterHeight;
                            }
                        }
                        else if(name.equals("champi_platforme")){
                            if(y==0){
                                y = platformChampiHeaderYMedium;
                                width = platformChampiHeaderWidthMedium;
                                height = platformChampiHeaderHeightMedium;
                            }
                            else if(y == 1){
                                y = platformChampiHeaderY;
                                width = platformChampiHeaderWidth;
                                height = platformChampiHeaderHeight;
                            }
                        }
                        BrownBloc bb = new BrownBloc(this, name, x, y, width, height);
                        if(info.length>4){
                            String itemName = info[4];
                            if(name.equals("brownbloc") || name.equals("hardbloc")){
                                int itemWidth = PIECE_WIDTH;
                                int itemHeight = itemWidth;
                                int itemX = x + (width - itemWidth)/2;
                                int itemY = y - itemHeight - dx;

                                Piece item = new Piece(this, itemName, itemX, itemY, itemWidth, itemHeight);
                                objets.add(item);
                            }
                            else if(name.equals("champi_platforme")){
                                int itemWidth = PIECE_WIDTH;
                                int itemHeight = itemWidth;
                                int itemRate = width/itemWidth;
                                int itemY = y - itemHeight - dx;
                                drawPieceLine(itemName, x, x+width, itemY, PIECE_WIDTH, PIECE_HEIGHT, objets);

                            }
                        }
                        objets.add(bb);
                    }
                    else if(info[0].equals("YellowBloc")){

                        int width = BLOC_WIDTH;
                        int height = BLOC_HEIGHT;
                        y = displayHeight - FLOOR_HEIGHT - y*BLOC_HEIGHT;

                        YellowBloc yb = new YellowBloc(this, name, x, y, BLOC_WIDTH, BLOC_HEIGHT);
                        if(info.length>4){
                            String item = info[4];
                            int itemWidth = width*2/3;
                            int itemHeight = itemWidth;
                            int itemX = x + (width - itemWidth)/2;
                            int itemY = y + (height - itemHeight)/2;

                            Item ite = new Item(this, item, itemX, itemY, itemWidth, itemHeight );
                            ite.setActivated(false);
                            yb.setItem(ite);
                            objets.add(ite);
                        }
                        objets.add(yb);
                    }
                    else if(info[0].equals("Castle")){
                        Castle castle = new Castle(this, name, x, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);
                        objets.add(castle);
                        if (x > 0) {
                            int floorRate = (x+CASTLE_WIDTH)/FLOOR_WIDTH +1;
                            for(int i = 0; i<floorRate; i++){

                                Floor floor = new Floor(this, "yellowbrick", i*FLOOR_WIDTH, floorY, FLOOR_WIDTH, FLOOR_HEIGHT);
                                objets.add(floor);
                            }
                        }
                    }
                    else if(info[0].equals("Pipe")){
                        Pipe pipe = new Pipe(this, name, x, pipeY, PIPE_WIDTH, PIPE_HEIGHT);
                        objets.add(pipe);
                    }
                    else if(info[0].equals("Piece")){
                        if(y == 0){y = displayHeight - FLOOR_HEIGHT - PIECE_HEIGHT - dx;}
                        Piece piece = new Piece(this, name, x, y, PIECE_WIDTH, PIECE_HEIGHT);
                        objets.add(piece);
                    }
                    else if(info[0].equals("Objet")){
                        int width = 0;
                        int height = 0;
                        if(name.equals("nuage")){
                            y = y*dx;
                            width = nuageSimpeWidth;
                            height = nuageSimpleHeight;
                        }
                        else if(name.equals("colline_petite")){
                            width = petiteCollineWidth;
                            height = petiteCollineHeight;
                            y = petiteCollineY;
                        }
                        else if(name.equals("colline_grande")){
                            width = grandeCollineWidth;
                            height = grandeCollineHeight;
                            y = grandeCollineY;
                        }
                        else if(name.equals("hills1")){
                            width = (int) (displayWidth*0.3);
                            height = width;
                            y = displayHeight - FLOOR_HEIGHT - height;
                        }
                        Objet objet = new Objet(this, name, x, y, width, height);
                        objets.add(objet);
                    }
                }
                drawLine("BrownBloc", "hardbloc", 130*dx, 190*dx, hardSquareY, hardsquareWidth, hardSquareHeight, false, objets);
                drawScale("BrownBloc", "hardbloc", 187*dx, 7, hardSquareY, hardsquareWidth, hardSquareHeight, objets);
                drawLine("BrownBloc", "hardbloc", 209*dx, 250*dx, floorY-7*hardSquareHeight, hardsquareWidth, hardSquareHeight, false, objets);
                drawPieceSquare(212*dx, pieceY, 5, objets);
                break;
            case 4 :
                String[][] objetResource4 = {
                        {"Objet", "24", "0", "colline_petite"},
                        {"Objet", "80", "0", "colline_grande"},
                        {"Objet", "1", "2", "nuage"},
                        {"Objet", "1", "7", "nuage"},
                        {"Objet", "120", "0", "colline_grande"},
                        {"Objet", "100", "0", "hills1"},
                        {"BrownBloc","30", "4", "darkbloc", "piece_jaune"},
                        {"YellowBloc","34", "4", "bloc", "champignon"},
                        {"BrownBloc","38", "4", "darkbloc", "piece_jaune"},
                        {"BrownBloc", "74", "0", "pillier_champi_platforme"},
                        {"BrownBloc", "69", "0", "champi_platforme", "piece_jaune"},
                        {"Castle", "0", "0", "castle"},
                        {"Castle", "400", "0", "castle"},
                        {"Piece", "50", "0", "piece_jaune"},
                        {"BrownBloc", "100", "1", "pillier_champi_platforme"},
                        {"BrownBloc", "92", "1", "champi_platforme", "piece_jaune"},
                        {"BrownBloc", "125", "0", "pillier_champi_platforme"},
                        {"BrownBloc", "120", "0", "champi_platforme", "piece_jaune"},
                        {"BrownBloc","130", "1", "hardbloc", "piece_jaune"},
                        {"YellowBloc","160", "4", "bloc", "champignon"}};

                for(String[] info : objetResource4){
                    int x = Integer.valueOf(info[1])*dx;
                    int y = Integer.valueOf(info[2]);
                    String name = info[3];
                    if(info[0].equals("BrownBloc")){
                        int width = BLOC_WIDTH;
                        int height = BLOC_HEIGHT;
                        if(name.equals("darkbloc")){
                            y = displayHeight - FLOOR_HEIGHT - y*BLOC_HEIGHT;
                        }
                        else if(name.equals("darkbloc")){
                            y = displayHeight - FLOOR_HEIGHT - y*hardSquareHeight;
                            width = hardsquareWidth;
                            height = hardSquareHeight;
                        }
                        else if(name.equals("pillier_champi_platforme")){
                            if(y==0){
                                y = platformChampiFooterMediumY;
                                width = platformChampiFooterWidthMedium;
                                height = platformChampiFooterHeightMedium;
                            }
                            else if(y == 1){
                                y = platformChampiFooterY;
                                width = platformChampiFooterWidth;
                                height = platformChampiFooterHeight;
                            }
                        }
                        else if(name.equals("champi_platforme")){
                            if(y==0){
                                y = platformChampiHeaderYMedium;
                                width = platformChampiHeaderWidthMedium;
                                height = platformChampiHeaderHeightMedium;
                            }
                            else if(y == 1){
                                y = platformChampiHeaderY;
                                width = platformChampiHeaderWidth;
                                height = platformChampiHeaderHeight;
                            }
                        }
                        BrownBloc bb = new BrownBloc(this, name, x, y, width, height);
                        if(info.length>4){
                            String itemName = info[4];
                            if(name.equals("darkbloc") || name.equals("hardbloc")){
                                int itemWidth = PIECE_WIDTH;
                                int itemHeight = itemWidth;
                                int itemX = x + (width - itemWidth)/2;
                                int itemY = y - itemHeight - dx;

                                Piece item = new Piece(this, itemName, itemX, itemY, itemWidth, itemHeight);
                                objets.add(item);
                            }
                            else if(name.equals("champi_platforme")){
                                int itemWidth = PIECE_WIDTH;
                                int itemHeight = itemWidth;
                                int itemRate = width/itemWidth;
                                int itemY = y - itemHeight - dx;
                                drawPieceLine(itemName, x, x+width, itemY, PIECE_WIDTH, PIECE_HEIGHT, objets);

                            }
                        }
                        objets.add(bb);
                    }
                    else if(info[0].equals("YellowBloc")){

                        int width = BLOC_WIDTH;
                        int height = BLOC_HEIGHT;
                        y = displayHeight - FLOOR_HEIGHT - y*BLOC_HEIGHT;

                        YellowBloc yb = new YellowBloc(this, name, x, y, BLOC_WIDTH, BLOC_HEIGHT);
                        if(info.length>4){
                            String item = info[4];
                            int itemWidth = width*2/3;
                            int itemHeight = itemWidth;
                            int itemX = x + (width - itemWidth)/2;
                            int itemY = y + (height - itemHeight)/2;

                            Item ite = new Item(this, item, itemX, itemY, itemWidth, itemHeight );
                            ite.setActivated(false);
                            yb.setItem(ite);
                            objets.add(ite);
                        }
                        objets.add(yb);
                    }
                    else if(info[0].equals("Castle")){
                        Castle castle = new Castle(this, name, x, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);
                        objets.add(castle);
                        if (x > 0) {
                            int floorRate = (x+CASTLE_WIDTH)/FLOOR_WIDTH +1;
                            for(int i = 0; i<floorRate; i++){

                                Floor floor = new Floor(this, "darkbrick", i*FLOOR_WIDTH, floorY, FLOOR_WIDTH, FLOOR_HEIGHT);
                                objets.add(floor);
                            }
                        }
                    }
                    else if(info[0].equals("Pipe")){
                        Pipe pipe = new Pipe(this, name, x, pipeY, PIPE_WIDTH, PIPE_HEIGHT);
                        objets.add(pipe);
                    }
                    else if(info[0].equals("Piece")){
                        if(y == 0){y = displayHeight - FLOOR_HEIGHT - PIECE_HEIGHT - dx;}
                        Piece piece = new Piece(this, name, x, y, PIECE_WIDTH, PIECE_HEIGHT);
                        objets.add(piece);
                    }
                    else if(info[0].equals("Objet")){
                        int width = 0;
                        int height = 0;
                        if(name.equals("nuage")){
                            y = y*dx;
                            width = nuageSimpeWidth;
                            height = nuageSimpleHeight;
                        }
                        else if(name.equals("colline_petite")){
                            width = petiteCollineWidth;
                            height = petiteCollineHeight;
                            y = petiteCollineY;
                        }
                        else if(name.equals("colline_grande")){
                            width = grandeCollineWidth;
                            height = grandeCollineHeight;
                            y = grandeCollineY;
                        }
                        else if(name.equals("hills1")){
                            width = (int) (displayWidth*0.3);
                            height = width;
                            y = displayHeight - FLOOR_HEIGHT - height;
                        }
                        Objet objet = new Objet(this, name, x, y, width, height);
                        objets.add(objet);
                    }
                }
                drawLine("BrownBloc", "hardbloc", 130*dx, 190*dx, hardSquareY, hardsquareWidth, hardSquareHeight, false, objets);
                drawScale("BrownBloc", "hardbloc", 187*dx, 7, hardSquareY, hardsquareWidth, hardSquareHeight, objets);
                drawLine("BrownBloc", "hardbloc", 209*dx, 250*dx, floorY-7*hardSquareHeight, hardsquareWidth, hardSquareHeight, false, objets);
                drawPieceSquare(212*dx, pieceY, 5, objets);
                break;
            case 5 :

                break;
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
    public void setFloor(int x, String key){
        int floorRate = x/FLOOR_WIDTH;
        int floorY = displayHeight - FLOOR_HEIGHT;
        for(int i = 0; i<floorRate; i++){
            Floor floor = new Floor(this, key, i*FLOOR_WIDTH, floorY, FLOOR_WIDTH, FLOOR_HEIGHT);
            System.out.print(" | " + i*FLOOR_WIDTH);
            objets.add(floor);
        }
    }
    public void drawPieceLine(String name, int initX, int endX, int y, int width, int height, ArrayList<Objet> objets){
        for(int i = initX; i<endX; i+=width){
            Piece piece = new Piece(this, name, i, y, width, height);
            objets.add(piece);
        }
    }
    public void drawLine(String objetType, String name, int initX, int endX,
                         int y, int width, int height, boolean piece, ArrayList<Objet> objets){
        int rate = (endX - initX)/width;
        switch (objetType){
            case "BrownBloc" :
                for(int i = 0; i<rate; i++){
                    BrownBloc bb = new BrownBloc(this, name, initX + i*width, y, width, height);
                    objets.add(bb);
                }
                break;
            case "YellowBloc" :
                for(int i = 0; i<rate; i++){
                    YellowBloc yb = new YellowBloc(this, name, initX + i*width, y, width, height);
                    objets.add(yb);
                }
                break;
            case "Piece" :
                for(int i = 0; i<rate; i++){
                    Piece p = new Piece(this, name, initX + i*width, y, width, height);
                    objets.add(p);
                }
        }

        if(piece){
            for(int i = 0; i<rate; i++){
                int pieceX = initX + (width - PIECE_WIDTH)/2 + i*width;
                int pieceY = y - PIECE_HEIGHT - dx;

                Piece p = new Piece(this, "piece_jaune", pieceX, pieceY, PIECE_WIDTH, PIECE_HEIGHT);
                objets.add(p);
            }
        }
    }
    public void drawPieceLine(int startX, int y, int number, ArrayList<Objet> objets){
        for(int i = 0; i<number; i++){
            Piece p = new Piece(this, "piece_jaune", startX + i*PIECE_WIDTH, y, PIECE_WIDTH, PIECE_HEIGHT);
            objets.add(p);
        }
    }
    public void drawPyramid(String objetType, String name, int startX, int stares, int initY, int width, int height, ArrayList<Objet> objets){
        for(int i = 0; i<stares; i++){
            drawLine(objetType, name, startX + i*width, startX+(stares-i)*width, initY-i*height, width, height, false, objets);
        }
    }
    public void drawScale(String objetType, String name, int startX, int stares, int initY, int width, int height, ArrayList<Objet> objets){
        for(int i = 0; i<stares; i++){
            drawLine(objetType, name, startX + i*width, startX+stares*width, initY-i*height, width, height, false, objets);
        }
    }
    public void drawPieceSquare(int startX, int initY, int quantite, ArrayList<Objet> objets){
        for(int i = 0; i<quantite; i++){
            drawPieceLine(startX, initY - i*PIECE_HEIGHT, quantite, objets);
        }
    }
}
