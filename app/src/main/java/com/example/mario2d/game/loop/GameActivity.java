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
import com.example.mario2d.game.personnage.Boo;
import com.example.mario2d.game.personnage.Goomba;
import com.example.mario2d.game.personnage.Koopa;
import com.example.mario2d.game.personnage.Parakoopa;
import com.example.mario2d.game.personnage.Personnage;
import com.example.mario2d.game.personnage.Player;
import com.example.mario2d.game.personnage.Skelerex;

import java.util.ArrayList;

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

        setObjets();

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

        Goomba goo = new Goomba(this, "goomba", 32*dx, 15*dx, GOOMBA_WIDTH, GOOMBA_HEIGHT);
        goo.setGravityFall(false);
        persos.add(goo);

        Koopa k = new Koopa(this, "greenkoopa", 32*dx, 15*dx, KOOPA_WIDTH, KOOPA_HEIGHT);
        k.setGravityFall(false);
        persos.add(k);

        Parakoopa pk = new Parakoopa(this, "greenpatrakoopa", 500, 100, KOOPA_WIDTH + 30, KOOPA_HEIGHT);
        persos.add(pk);

        final int greyBrickDim = 3*dx;
        //Boo boo = new Boo(this, "boo", 70*dx, displayHeight - FLOOR_HEIGHT - greyBrickDim, greyBrickDim, greyBrickDim);
        //persos.add(boo);

        Boo boo2 = new Boo(this, "boo", 170*dx, displayHeight - FLOOR_HEIGHT - greyBrickDim, greyBrickDim, greyBrickDim);
        persos.add(boo2);

        Skelerex skel = new Skelerex(this, "skelerex", 140*dx, displayHeight-FLOOR_HEIGHT-KOOPA_HEIGHT-dx, KOOPA_WIDTH, KOOPA_HEIGHT);
        persos.add(skel);

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
    public void setObjets(){

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

        switch (LEVEL_SELECTED){
            case 1 :

                final int nuageSimpeWidth = (int) (displayWidth*0.076);
                final int nuageSimpleHeight = (int) (0.9465*nuageSimpeWidth);

                final int hardsquareWidth = (int) (BLOC_WIDTH*0.8);
                final int hardSquareHeight = hardsquareWidth;
                final int hardSquareY = displayHeight - FLOOR_HEIGHT - hardSquareHeight;

                drawCastle("castle", 0);
                drawCastle("castle", 400*dx, "redbrick");

                drawDecoration("colline_petite", 40*dx,15*dx, 7*dx);
                drawDecoration("colline_grande", 80*dx, 20*dx, 10*dx);
                drawDecoration("hill2", 124*dx, 30*dx, 30*dx);
                drawDecoration("hill2", 138*dx, 20*dx, 20*dx);
                drawDecoration("hill2", 125*dx, 15*dx, 15*dx);
                drawDecoration("nuage", 10*dx, 2*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 27*dx, 3*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 44*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 78*dx, 5*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 100*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 137*dx, 4*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 145*dx, 3*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 163*dx, 4*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 175*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);


                drawAlternatesBloc("brownbloc", "bloc", 30*dx, displayHeight-FLOOR_HEIGHT-BLOC_HEIGHT*4, 3, 2, 4);
                drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 80*dx, 4*dx, 10*dx, 12*dx, 4*dx, 2);
                drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 120*dx, 4*dx, 15*dx, 15*dx, 4*dx, 2);
                drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 160*dx, 4*dx, 10*dx, 12*dx, 4*dx, 2);
                drawLine("BrownBloc", "hardbloc", 164*dx, 200*dx, displayHeight-FLOOR_HEIGHT-hardSquareHeight, hardsquareWidth, hardSquareHeight, false);
                drawScale("BrownBloc", "hardbloc", 199*dx, 7, displayHeight-FLOOR_HEIGHT-hardSquareHeight, hardsquareWidth, hardSquareHeight);
                drawLine("BrownBloc", "hardbloc", 199*dx+7*hardsquareWidth,199*dx+7*hardsquareWidth+10*hardsquareWidth ,displayHeight-FLOOR_HEIGHT-7*hardSquareHeight, hardsquareWidth, hardSquareHeight, false);
                drawPieceSquare(199*dx+7*hardsquareWidth+dx, displayHeight-FLOOR_HEIGHT-PIECE_HEIGHT-dx, 5);
                drawBloc("bloc", 178*dx, displayHeight-FLOOR_HEIGHT-hardSquareHeight-4*BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 4);
                return;
            case 2 :
                drawCastle("greencastle", 0);
                drawCastle("greencastle", 400*dx, "greenbrick");
                drawAlternatesBloc("brownbloc", "bloc", 30*dx, staticBlocY, 10, 2, 5);

                final int greyBrickDim = 3*dx;
                drawBloc("greybrick", 30*dx, displayHeight-FLOOR_HEIGHT-greyBrickDim, greyBrickDim, greyBrickDim, 1 );
                drawScale("BrownBloc", "greybrick", 80*dx, 6, displayHeight-FLOOR_HEIGHT-greyBrickDim, greyBrickDim, greyBrickDim);

                //Boo boo = new Boo(this, "boo", 50*dx, displayHeight - FLOOR_HEIGHT - greyBrickDim, greyBrickDim, greyBrickDim);
                //persos.add(boo);

                break;
            case 3 :
                drawCastle("castle", 0);
                drawCastle("castle", 400*dx);
                drawPyramid("BrownBloc", "goldenbloc", 100*dx, 10, displayHeight-FLOOR_HEIGHT-BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT);
                break;
            case 4 :
                break;
            case 5 :
                break;
        }

    }
    /**
     * DrawCastle dessine un château
     * @param key
     * @param x
     * @param floorKey
     */
    public void drawCastle(String key, int x, String...floorKey){
        int castleY = displayHeight - FLOOR_HEIGHT - CASTLE_HEIGHT;
        Castle castle = new Castle(this, key, x, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);
        objets.add(castle);
        if(x>0){
            setFloor(x, floorKey[0]);
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

    /**
     * Dessine le sol
     * @param x
     * @param key
     */
    public void setFloor(int x, String key){
        int floorRate = x/FLOOR_WIDTH;
        int floorY = displayHeight - FLOOR_HEIGHT;
        for(int i = 0; i<floorRate; i++){
            Floor floor = new Floor(this, key, i*FLOOR_WIDTH, floorY, FLOOR_WIDTH, FLOOR_HEIGHT);
            System.out.print(" | " + i*FLOOR_WIDTH);
            objets.add(floor);
        }
    }

    /**
     * Dessine une ligne de piece en fonction d'un X de départ et d'un X d'arrêt.
     * @param name
     * @param initX
     * @param endX
     * @param y
     * @param width
     * @param height
     * @param objets
     */
    public void drawPieceLine(String name, int initX, int endX, int y, int width, int height, ArrayList<Objet> objets){
        for(int i = initX; i<endX; i+=width){
            Piece piece = new Piece(this, name, i, y, width, height);
            objets.add(piece);
        }
    }
    /**
     * Dessine une ligne d'objets entre deux X parmi les BrownBlocs, YellowBlocs
     * @param objetType
     * @param name
     * @param initX
     * @param endX
     * @param y
     * @param width
     * @param height
     * @param piece
     */
    public void drawLine(String objetType, String name, int initX, int endX, int y, int width, int height, boolean piece){
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

    /**
     * Dessine une ligne de piece
     * @param startX
     * @param y
     * @param number
     */
    public void drawPieceLine(int startX, int y, int number){
        for(int i = 0; i<number; i++){
            Piece p = new Piece(this, "piece_jaune", startX + i*PIECE_WIDTH, y, PIECE_WIDTH, PIECE_HEIGHT);
            objets.add(p);
        }
    }
    public void drawPyramid(String objetType, String name, int startX, int stares, int initY, int width, int height){
        for(int i = 0; i<stares; i++){
            drawLine(objetType, name, startX + i*width, startX+(stares-i)*width, initY-i*height, width, height, false);
        }
    }
    public void drawScale(String objetType, String name, int startX, int stares, int initY, int width, int height){
        for(int i = 0; i<stares; i++){
            drawLine(objetType, name, startX + i*width, startX+stares*width, initY-i*height, width, height, false);
        }
    }
    public void drawPieceSquare(int startX, int initY, int quantite){
        for(int i = 0; i<quantite; i++){
            drawPieceLine(startX, initY - i*PIECE_HEIGHT, quantite);
        }
    }
    public void drawBloc(String name, int x, int y, int width, int height, int code){

        switch (code){
            case 1 :
                BrownBloc bloc1 = new BrownBloc(this, name, x, y, width, height);
                objets.add(bloc1);
                return;
            case 2 :
                BrownBloc bloc2 = new BrownBloc(this, name, x, y, width, height);
                Piece piece2 = new Piece(this, "piece_jaune", x + (width-PIECE_WIDTH)/2, y-PIECE_HEIGHT-dx, PIECE_WIDTH, PIECE_HEIGHT);
                objets.add(bloc2);
                objets.add(piece2);
                return;
            case 3 :
                YellowBloc yb3 = new YellowBloc(this, name, x, y, width, height);
                Item item3 = new Item(this, "piece_jaune_face", x+(width-PIECE_WIDTH)/2, y-PIECE_HEIGHT - dx, PIECE_WIDTH, PIECE_HEIGHT);
                item3.setActivated(false);
                yb3.setItem(item3);
                objets.add(yb3);
                objets.add(item3);
                return;
            case 4 :
                YellowBloc yb4 = new YellowBloc(this, name, x, y, width, height);
                int itemWidth = (int) (width * 2/3);
                int itemHeight = itemWidth;
                Item item4 = new Item(this, "champignon", x+(width-itemWidth)/2, y+(height-itemHeight)/2, itemWidth, itemHeight);
                item4.setActivated(false);
                yb4.setItem(item4);
                objets.add(yb4);
                objets.add(item4);
                return;
            case 5 :
                YellowBloc yb5 = new YellowBloc(this, name, x, y, width, height);
                int itWidth = (int) (width * 2/3);
                int itHeight = itWidth;
                Item item5 = new Item(this, "etoile", x+(width-itWidth)/2, y+(height-itHeight)/2, itWidth, itHeight);
                item5.setActivated(false);
                yb5.setItem(item5);
                objets.add(yb5);
                objets.add(item5);
                return;
        }
    }
    public void drawStaticPlatform(String footName, String headName, int x, int footWidth, int footHeight, int headWidth, int headHeight, int code){
        int footY = displayHeight - FLOOR_HEIGHT - footHeight;
        drawBloc(footName, x, footY, footWidth, footHeight, 1);
        int headX = x + footWidth/2 - headWidth/2;
        int headY = footY - headHeight;
        switch(code){

            case 1 :
                drawBloc(headName, headX, headY, headWidth, headHeight, 1);
                return;
            case 2 :
                drawBloc(headName, headX, headY, headWidth, headHeight, 2);
                return;
            case 3 :
                int quantity = headWidth/PIECE_WIDTH;
                drawBloc(headName, headX, headY, headWidth, headHeight, 1);
                drawPieceLine(headX, headY - PIECE_HEIGHT-dx, quantity, 1);
                return;
            case 4 :
                int quantite = headWidth/PIECE_WIDTH;
                drawBloc(headName, headX, headY, headWidth, headHeight, 1);
                drawPieceLine(headX, headY-PIECE_HEIGHT-dx, quantite, 2);
                break;
            default :
                return;
        }
    }
    public void drawPieceLine(int startX,int y, int quantite, int code){
        switch (code){
            case 1 :
                for(int i = 0; i<quantite; i++){drawPiece( startX + i*PIECE_WIDTH, y);}
                break;
            case 2 :
                for(int i = 0; i<quantite; i++){if(i%2 == 0){drawPiece(startX + i*PIECE_WIDTH, y);}}
                break;
            default :
                return;
        }
    }
    public void drawPiece(int x, int y){
        Piece piece = new Piece(this, "piece_jaune", x,y, PIECE_WIDTH, PIECE_HEIGHT);
        objets.add(piece);
    }
    public void drawDecoration(String key, int x, int width, int height){
        int decoY = displayHeight - FLOOR_HEIGHT - height;
        Objet objet = new Objet(this, key, x, decoY, width, height);
        objets.add(objet);
    }
    public void drawDecoration(String key, int x, int y, int width, int height){
        Objet objet = new Objet(this, key, x, y, width, height);
        objets.add(objet);
    }
    public void drawAlternatesBloc(String staticKey, String mysteryKey, int startX, int y, int numberOfBloc, int code1, int code2){
        for(int i = 0; i<numberOfBloc; i++){
            if(i%2 == 0){drawBloc(staticKey, startX + i*BLOC_WIDTH, y, BLOC_WIDTH, BLOC_HEIGHT, code1);}
            else{drawBloc(mysteryKey, startX+i*BLOC_WIDTH, y, BLOC_WIDTH, BLOC_HEIGHT, code2);}
        }
    }
}
