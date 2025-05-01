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
import com.example.mario2d.game.personnage.Thwomp;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private int displayWidth, displayHeight, CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_RATE,
            CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT, LEVEL_SELECTED,
            CHARACTER_SELECTED, PIECE_WIDTH, PIECE_HEIGHT, dx;

    private Boolean leftHandMode, soundEffect, music;
    private ArrayList<Objet> objets = new ArrayList<Objet>();
    private ArrayList<Personnage> persos = new ArrayList<Personnage>();
    public static Player player;

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
        this.player = player;
        setObjets();
        setContentView(new GameView(this, displayWidth, displayHeight,leftHandMode, LEVEL_SELECTED, objets, persos));
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
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int staticBlocY = mysteryBlocY;
        final int surface = displayHeight-FLOOR_HEIGHT;
        this.PIECE_WIDTH = (int)(displayWidth*0.03);
        this.PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1354);

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

                drawGoomba("goomba", 85*dx, surface-4*dx, true);
                drawGoomba("goomba", 70*dx, surface-4*dx, true);
                drawKoopa("greenkoopa", 125*dx, (int) (surface-(displayWidth*0.03 - 1.7458) - dx), true);
                drawKoopa("greenkoopa", 170*dx, (int) (surface-(displayWidth*0.03 - 1.7458) - 4*dx),true);
                drawParakoopa("greenparakoopa", 240*dx, surface - 30*dx, false);

                return;
            case 2 :

                drawCastle("greencastle", 0);
                drawCastle("greencastle", 400*dx, "greenbrick");
                drawAlternatesBloc("brownbloc", "bloc", 30*dx, staticBlocY, 10, 2, 5);

                final int greyBrickDim = 3*dx;
                drawBloc("greybrick", 30*dx, displayHeight-FLOOR_HEIGHT-greyBrickDim, greyBrickDim, greyBrickDim, 1 );
                drawScale("BrownBloc", "greybrick", 80*dx, 6, surface-greyBrickDim, greyBrickDim, greyBrickDim);
                drawLine("BrownBloc", "greybrick", 98*dx,98*dx+30*dx,surface-6*greyBrickDim, greyBrickDim, greyBrickDim,false );
                drawLine("Objet", "greybrick", 98*dx,98*dx+15*dx,surface-5*greyBrickDim, greyBrickDim, greyBrickDim,false );
                drawBoo(98*dx+15*dx, surface-5*greyBrickDim);
                drawLine("Objet", "greybrick", 98*dx+18*dx,98*dx+30*dx,surface-5*greyBrickDim, greyBrickDim, greyBrickDim,false );
                drawLine("Objet", "greybrick", 98*dx,98*dx+30*dx,surface-4*greyBrickDim, greyBrickDim, greyBrickDim,false );
                drawLine("Objet", "greybrick", 98*dx,98*dx+30*dx,surface-3*greyBrickDim, greyBrickDim, greyBrickDim,false );
                drawLine("Objet", "greybrick", 98*dx,98*dx+30*dx,surface-2*greyBrickDim, greyBrickDim, greyBrickDim,false );
                drawLine("Objet", "greybrick", 98*dx,98*dx+30*dx,surface-greyBrickDim, greyBrickDim, greyBrickDim,false );
                drawColumn("BrownBloc", "greybrick", 98*dx+30*dx, surface - greyBrickDim, 6,greyBrickDim, greyBrickDim, true);

                drawLine("BrownBloc", "greybrick", 131*dx, 161*dx, surface-greyBrickDim, greyBrickDim, greyBrickDim, true);
                drawBoo(161*dx, surface-greyBrickDim);
                drawPiece(161*dx, surface-greyBrickDim-PIECE_HEIGHT-dx);
                drawLine("BrownBloc", "greybrick", 164*dx, 194*dx, surface-greyBrickDim, greyBrickDim, greyBrickDim, true);
                drawThwomp(186*dx, (int) (-8*dx*1.2513));


                break;
            case 3 :
                drawCastle("castle", 0);
                drawCastle("castle", 400*dx, "yellowbrick");
                //drawPyramid("BrownBloc", "goldenbloc", 100*dx, 10, displayHeight-FLOOR_HEIGHT-BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT);
                drawKoopa("redkoopa", 40*dx, (int) (surface-(displayWidth*0.03 - 1.7458) - dx), true);
                drawParakoopa("redparakoopa", 50*dx, (int) (surface-(displayWidth*0.03 - 1.7458) - dx), true);
                break;
            case 4 :
                drawCastle("castle", 0);
                drawCastle("castle", 400*dx, "darkbrick");
                break;
            case 5 :
                drawCastle("castle", 0);
                drawCastle("castle", 400*dx, "nuageplatform");
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
            setFloor(x + CASTLE_WIDTH, floorKey[0]);
        }
    }
    public void setCharacters(){

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
            case "Objet" :
                for(int i = 0; i<rate; i++){
                    Objet objet = new Objet(this, name, initX + i*width, y, width, height);
                    objets.add(objet);
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
    public void drawColumn(String objetType, String name, int x, int initY, int quantite, int width, int height, boolean pieceOnTop){
        switch (objetType){
            case "BrownBloc" :
                for(int i = 0; i<quantite; i++){
                    BrownBloc bb = new BrownBloc(this, name, x, initY-i*height, width, height);
                    objets.add(bb);
                }
                break;
            case "YellowBloc" :
                for(int i = 0; i<quantite; i++){
                    YellowBloc yb = new YellowBloc(this, name, x, initY - i*height, width, height);
                    objets.add(yb);
                }
                break;
            case "Piece" :
                for(int i = 0; i<quantite; i++){
                    Piece p = new Piece(this, name, x, initY - i*height, width, height);
                    objets.add(p);
                }
            case "Objet" :
                for(int i = 0; i<quantite; i++){
                    Objet objet = new Objet(this, name, x, initY - i*height, width, height);
                    objets.add(objet);
                }
        }
        if(pieceOnTop){
            int pieceX = x + (width-PIECE_WIDTH)/2;
            int pieceY = initY - quantite*height - PIECE_HEIGHT-dx;
            Piece piece = new Piece(this, "piece_jaune", pieceX, pieceY, PIECE_WIDTH, PIECE_HEIGHT);
            objets.add(piece);
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
    public void drawGoomba(String key, int x, int y, boolean gravityFall){
        Goomba goomba = new Goomba(this, key, x, y, dx*3, dx*3);
        goomba.setGravityFall(gravityFall);
        persos.add(goomba);
    }
    public void drawKoopa(String key, int x, int y, boolean gravityFall){
        final int KOOPA_WIDTH = (int) (displayWidth*0.03);
        final int KOOPA_HEIGHT = (int) (KOOPA_WIDTH*1.7458);
        Koopa koopa = new Koopa(this, key, x, y, KOOPA_WIDTH, KOOPA_HEIGHT);
        koopa.setGravityFall(gravityFall);
        persos.add(koopa);
    }
    public void drawParakoopa(String key, int x, int y, boolean gravityFall){
        final int PARAKOOPA_WIDTH = (int) (displayWidth*0.03) + dx;
        final int PARAKOOPA_HEIGHT = (int) (PARAKOOPA_WIDTH*1.7458);
        Parakoopa pk = new Parakoopa(this, key, x, y, PARAKOOPA_WIDTH, PARAKOOPA_HEIGHT);
        pk.setGravityFall(gravityFall);
        persos.add(pk);
    }
    public void drawBoo(int x, int y){

        Boo boo2 = new Boo(this, "boo", x, y, 3*dx, 3*dx);
        persos.add(boo2);

    }
    public void drawSkelerex(int x, int y, boolean gravityFall){
        final int KOOPA_WIDTH = (int) (displayWidth*0.03);
        final int KOOPA_HEIGHT = (int) (KOOPA_WIDTH*1.7458);
        Skelerex sk = new Skelerex(this, "skelerex", x, y, KOOPA_WIDTH, KOOPA_HEIGHT);
        sk.setGravityFall(gravityFall);
        persos.add(sk);
    }
    public void drawThwomp(int x, int y){
        int thwompWidth = 8*dx;
        int thwompHeight = (int) (thwompWidth*1.2513);
        Thwomp thwomp = new Thwomp(this, "thwomp", x, y, thwompWidth, thwompHeight);
        persos.add(thwomp);
    }
}
