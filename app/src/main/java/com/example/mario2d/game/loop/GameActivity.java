package com.example.mario2d.game.loop;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mario2d.game.objet.BrownBloc;
import com.example.mario2d.game.objet.Canon;
import com.example.mario2d.game.objet.Castle;
import com.example.mario2d.game.objet.Champignon;
import com.example.mario2d.game.objet.Etoile;
import com.example.mario2d.game.objet.FleurFeu;
import com.example.mario2d.game.objet.Objet;
import com.example.mario2d.game.objet.Piece;
import com.example.mario2d.game.objet.Pipe;
import com.example.mario2d.game.objet.PlateformeEphemere;
import com.example.mario2d.game.objet.Platforme;
import com.example.mario2d.game.objet.YellowBloc;
import com.example.mario2d.game.personnage.Boo;
import com.example.mario2d.game.personnage.Coconut;
import com.example.mario2d.game.personnage.Ennemy;
import com.example.mario2d.game.personnage.FireBowl;
import com.example.mario2d.game.personnage.Goomba;
import com.example.mario2d.game.personnage.Koopa;
import com.example.mario2d.game.personnage.Magikoopa;
import com.example.mario2d.game.personnage.Parakoopa;
import com.example.mario2d.game.personnage.Personnage;
import com.example.mario2d.game.personnage.PlantePirhana;
import com.example.mario2d.game.personnage.Player;
import com.example.mario2d.game.personnage.Podoboo;
import com.example.mario2d.game.personnage.Pokey;
import com.example.mario2d.game.personnage.Skelerex;
import com.example.mario2d.game.personnage.Spiny;
import com.example.mario2d.game.personnage.Thwomp;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static int  CHARACTER_WIDTH, CHARACTER_HEIGHT, FLOOR_WIDTH, FLOOR_HEIGHT, FLOOR_RATE,
            CASTLE_WIDTH, CASTLE_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, PIPE_WIDTH, PIPE_HEIGHT, LEVEL_SELECTED,
            CHARACTER_SELECTED, PIECE_WIDTH, PIECE_HEIGHT;
    public static int displayWidth, displayHeight;
    public static int dx;
    private Boolean leftHandMode, soundEffect, music;
    public static ArrayList<Objet> objets = new ArrayList<Objet>();
    public static ArrayList<BrownBloc> waitingLineBrownBlocs = new ArrayList<BrownBloc>();
    public static ArrayList<BrownBloc> waitingLineBrownBlocsForRemoving = new ArrayList<BrownBloc>();
    public static ArrayList<Castle> castles = new ArrayList<Castle>();
    public static ArrayList<BrownBloc> brownBlocs = new ArrayList<BrownBloc>();
    public static ArrayList<YellowBloc> yellowBlocs = new ArrayList<YellowBloc>();
    public static ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    public static ArrayList<Piece> pieces = new ArrayList<Piece>();
    public static ArrayList<Champignon> champis = new ArrayList<Champignon>();
    public static ArrayList<Etoile> etoiles = new ArrayList<Etoile>();
    public static ArrayList<FleurFeu> fleursfeu = new ArrayList<FleurFeu>();
    public static ArrayList<Ennemy> ennemies = new ArrayList<Ennemy>();
    public static ArrayList<Ennemy> waitingLine = new ArrayList<Ennemy>();
    public static ArrayList<Ennemy> waitingLineForRemoving = new ArrayList<Ennemy>();
    public static ArrayList<Personnage> persos = new ArrayList<Personnage>();
    public static ArrayList<Platforme> platformes = new ArrayList<Platforme>();
    public static Player player;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        this.setExtraData(extras);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String name = CHARACTER_SELECTED == 1 ? "mario" : "luigi";
        int playerX = (int)(displayWidth/2 - CHARACTER_WIDTH/2);
        int playerY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - 1;

        this.player = new Player(this, name, playerX, playerY, (int) (CHARACTER_WIDTH*0.9), (int) (CHARACTER_HEIGHT*0.9));

        objets.clear();
        persos.clear();
        ennemies.clear();
        champis.clear();
        pieces.clear();
        pipes.clear();
        yellowBlocs.clear();
        brownBlocs.clear();
        castles.clear();
        etoiles.clear();
        platformes.clear();
        waitingLine.clear();
        fleursfeu.clear();
        waitingLineForRemoving.clear();
        waitingLineBrownBlocs.clear();
        waitingLineBrownBlocsForRemoving.clear();

        if(LEVEL_SELECTED == 1) {
            level1();
        } else if (LEVEL_SELECTED == 2) {
            level2();
        } else if (LEVEL_SELECTED == 3) {
            level3();
        } else if (LEVEL_SELECTED == 4) {
            level4();
        } else {
            level5();
        }
        //setObjets();
        setContentView(new GameView(this, displayWidth, displayHeight,leftHandMode, LEVEL_SELECTED));
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

        objets = new ArrayList<Objet>();
        persos = new ArrayList<Personnage>();

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
                drawCastle("castle", 450*dx);
                drawLine("BrownBloc", "redbrick", 0, 455*dx+CASTLE_WIDTH, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);

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
                drawDecoration("nuage", 175*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 200*dx, 4*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 229*dx, 3*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 245*dx, 2*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 263*dx, 5*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 280*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 312*dx, 3*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 330*dx, 2*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 367*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 385*dx, 4*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 418*dx, 3*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("nuage", 448*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
                drawDecoration("hills1", 230*dx, 30*dx, 30*dx);
                drawDecoration("buisson_simple", 25*dx, 5*dx, (int) (2.5*dx));
                drawDecoration("buisson_triple", 140*dx, 15*dx, 3*dx);
                drawDecoration("buisson_triple", 180*dx, surface - 5*dx, 15*dx, 3*dx);

                drawGoomba("goomba", 65*dx, surface-4*dx, true, true);
                drawAlternatesBloc("brownbloc", "bloc", 42*dx, surface - 15*dx, 3, 2, 4);
                drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 80*dx, 4*dx, 10*dx, 12*dx, 4*dx, 2);
                drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 120*dx, 4*dx, 15*dx, 15*dx, 4*dx, 2);
                drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 160*dx, 4*dx, 10*dx, 12*dx, 4*dx, 2);
                drawLine("BrownBloc", "hardbloc", 164*dx, 200*dx, displayHeight-FLOOR_HEIGHT-hardSquareHeight, hardsquareWidth, hardSquareHeight, false);
                drawScale("BrownBloc", "hardbloc", 199*dx, 4, displayHeight-FLOOR_HEIGHT-hardSquareHeight, hardsquareWidth, hardSquareHeight);
                drawColumn("BrownBloc", "hardbloc", 229*dx, surface - hardSquareHeight, 4, hardsquareWidth, hardSquareHeight, false);
                drawColumn("BrownBloc", "hardbloc", 232*dx, surface - hardSquareHeight, 3, hardsquareWidth, hardSquareHeight, false);
                drawColumn("BrownBloc", "hardbloc", 235*dx, surface - hardSquareHeight, 2, hardsquareWidth, hardSquareHeight, false);
                drawBloc("hardbloc", 238*dx, surface-hardSquareHeight, hardsquareWidth, hardSquareHeight, 1);
                //drawLine("BrownBloc", "hardbloc", 199*dx+7*hardsquareWidth,199*dx+7*hardsquareWidth+10*hardsquareWidth ,displayHeight-FLOOR_HEIGHT-7*hardSquareHeight, hardsquareWidth, hardSquareHeight, false);
                //drawPieceSquare(199*dx+7*hardsquareWidth+dx, displayHeight-FLOOR_HEIGHT-PIECE_HEIGHT-dx, 5);
                //drawBloc("bloc", 190*dx, displayHeight-FLOOR_HEIGHT-hardSquareHeight-4*BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 4);
                drawGoomba("goomba", 85*dx, surface-4*dx, true, true);
                drawKoopa("greenkoopa", 125*dx, (int) (surface-(displayWidth*0.03 - 1.7458) - 3*dx), true, false);
                drawKoopa("greenkoopa", 170*dx, (int) (surface-(displayWidth*0.03 - 1.7458) - 6*dx),true, false);
                drawParakoopa("greenparakoopa", 275*dx, surface - 27*dx, false, true);
                drawBloc("bloc", 219*dx, displayHeight-FLOOR_HEIGHT-hardSquareHeight-4*BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 3);
                drawGoomba("goomba", 220*dx, surface - 5*dx, false, false);
                drawMovablePlatform("platforme", 100*dx, surface - 10*dx, 10*dx, (int) (20*dx*0.1657), true, 10*dx, surface-6*dx);
                drawMovablePlatform("platforme", 275*dx, surface - 6*dx, 10*dx, (int) (20*dx*0.1657), true, 10*dx, surface-6*dx);
                drawMovablePlatform("platforme", 295*dx, 10*dx, 10*dx, (int) (20*dx*0.1657), false, 10*dx, surface-6*dx);
                drawMovablePlatform("platforme", 315*dx, surface - 6*dx, 10*dx, (int) (20*dx*0.1657), true, 10*dx, surface-6*dx);
                drawMovablePlatform("platforme", 335*dx, 10*dx, 10*dx, (int) (20*dx*0.1657), false, 10*dx, surface-6*dx);
                drawParakoopa("greenparakoopa", 320*dx, surface - 27*dx, true, true);
                drawPieceLine(275*dx, surface - 22*dx, 24);
                drawPieceLine(275*dx, surface - 27*dx, 10);
                drawAlternatesBloc("brownbloc", "bloc", 304*dx, surface-27*dx, 3, 1, 4);
                drawPieceLine(317*dx, surface - 27*dx, 10);
                drawPieceLine(275*dx, surface - 32*dx, 24);
                drawPipe("greenpipe", 288*dx, surface-PIPE_HEIGHT);
                drawPipe("greenpipe", 350*dx, surface-PIPE_HEIGHT);
                //drawBloc("brownbloc", 304*dx, surface-BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 1);
                //drawBloc("brownbloc", 340*dx, surface-BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 1);
                drawGoomba("goomba", 260*dx, surface - 4*dx, false, true);
                drawGoomba("goomba", 263*dx, surface - 4*dx, false, true);
                drawGoomba("goomba", 330*dx, surface - 5*dx, false, true);
                drawDecoration("buisson_simple", 260*dx, 5*dx, (int) (2.5*dx));
                drawDecoration("buisson_simple", 310*dx, 5*dx, (int) (2.5*dx));
                drawDecoration("colline_petite", 315*dx, 15*dx, 7*dx);

                drawUnmovablePlatforme("herbe_platforme", 360*dx, surface - 15*dx, 17*dx, 4*dx);
                drawDecoration("pillier_terre", 361*dx, surface - 12*dx,14*dx, 14*dx);
                drawUnmovablePlatforme("herbe_platforme", 374*dx, surface - 27*dx, 17*dx, 4*dx);
                drawDecoration("pillier_terre", 375*dx, surface - 24*dx,14*dx, 14*dx);
                drawDecoration("pillier_terre", 375*dx, surface - 12*dx,14*dx, 14*dx);
                drawUnmovablePlatforme("herbe_platforme", 387*dx, surface - 8*dx, 17*dx, 4*dx);
                drawDecoration("pillier_terre", 389*dx, (int) (surface - 6*dx),14*dx, 14*dx);

                drawKoopa("greenkoopa", 390*dx, surface-31*dx, false, true);
                drawKoopa("greenkoopa", 368*dx, surface - 19*dx, false, true);
                drawGoomba("goomba", 395*dx, surface-12*dx, false, false);
                drawPieceColumn(395*dx, surface - 12*dx, 5);
                drawDecoration("colline_grande", 420*dx, 16*dx, 7*dx);
                drawDecoration("buisson_triple", 439*dx, 12*dx, 4*dx);

                return;
            case 2 :

                drawCastle("greencastle", 0);
                drawCastle("greencastle", 561*dx);
                drawLine("BrownBloc", "greenbrick", 0, 400*dx, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);
                drawAlternatesBloc("greenbloc", "bloc", 30*dx, staticBlocY, 10, 2, 3);
                drawLine("Objet", "greybrick2", 0, 400*dx, 0, 3*dx, 3*dx, false);
                drawLine("BrownBloc", "greybrick2", 0, 186*dx, 3*dx, 3*dx, 3*dx, false);
                drawLine("BrownBloc", "greybrick2", 194*dx, 250*dx,3*dx, 3*dx, 3*dx, false);

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
                drawLine("BrownBloc", "greybrick", 194*dx, 400*dx, surface - greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawColumn("BrownBloc", "greybrick", 200*dx, surface-greyBrickDim, 5, greyBrickDim, greyBrickDim, false);
                drawColumn("BrownBloc", "greybrick", 248*dx, surface-greyBrickDim, 5, greyBrickDim, greyBrickDim, true);
                drawSkelerex(223*dx, surface-greyBrickDim-6*dx, true, false);
                drawAlternatesBloc("greenbloc", "bloc", 220*dx, surface-4*BLOC_WIDTH, 3, 2, 4);
                drawBoo(248*dx, greyBrickDim, "greybrick2");
                drawLine("BrownBloc", "greybrick2", 251*dx, 280*dx, greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawThwomp(278*dx, (int) (-8*dx*1.2513));

                drawColumn("BrownBloc", "greybrick2", 283*dx+3*dx, surface-8*greyBrickDim, 5, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 289*dx, greyBrickDim, 319*dx, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 289*dx, 319*dx, greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 289*dx, 319*dx, 2*greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 289*dx, 319*dx, 3*greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 289*dx, 319*dx, 4*greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawLine("BrownBloc", "greybrick2", 289*dx, 319*dx, 5*greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawColumn("BrownBloc", "greybrick2", 319*dx, surface-8*greyBrickDim, 5, greyBrickDim, greyBrickDim, false);
                drawThwomp(322*dx, (int) (-8*dx*1.2513));

                drawColumn("BrownBloc", "greybrick2", 330*dx, surface-8*greyBrickDim, 5, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 333*dx, greyBrickDim, 319*dx, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 333*dx, 363*dx, greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 333*dx, 363*dx, 2*greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 333*dx, 363*dx, 3*greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawLine("Objet", "greybrick2", 333*dx, 345*dx, 4*greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawBoo(345*dx, 4*greyBrickDim, "greybrick2");
                drawLine("Objet", "greybrick2", 348*dx, 363*dx,4*greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawLine("BrownBloc", "greybrick2", 333*dx, 363*dx, 5*greyBrickDim, greyBrickDim, greyBrickDim, false);
                drawColumn("BrownBloc", "greybrick2", 363*dx, surface-8*greyBrickDim, 5, greyBrickDim, greyBrickDim, false);

                drawBloc("greenbloc", 302*dx, surface - greyBrickDim - BLOC_WIDTH, BLOC_WIDTH, BLOC_HEIGHT, 2);
                drawBloc("greenbloc", 346*dx, surface - greyBrickDim - BLOC_WIDTH, BLOC_WIDTH, BLOC_HEIGHT, 2);
                drawPlatformeEphemereLine("tile1", 405*dx, surface - 12*dx, 3*dx, 3*dx, 70, false, false, 10);
                drawPlatformeEphemereLine("tile1", 435*dx, surface - 9*dx, 3*dx, 3*dx, 70, false, false, 10);
                drawPlatformeEphemereLine("tile1", 465*dx, surface - 12*dx, 3*dx, 3*dx, 70, false, false, 10);
                drawPlatformeEphemereLine("tile1", 495*dx, surface - 9*dx, 3*dx, 3*dx, 70, false, false, 10);
                drawPlatformeEphemereLine("tile1", 525*dx, surface - 6*dx, 3*dx, 3*dx, 70, false, false,4);
                drawPlatformeEphemereLine("tile1", 537*dx, surface - 3*dx, 3*dx, 3*dx, 70, false, false,4);
                drawPlatformeEphemereLine("tile1", 549*dx, surface, 3*dx, 3*dx, 70, false, false,4);


                drawBoo(432*dx, surface - 9*dx, "tile1_1");
                drawBoo(465*dx, surface - 9*dx, "tile1_1");
                drawSkelerex(440*dx, surface-13*dx, false, true);
                drawSkelerex(500*dx, surface-13*dx, false, true);
                drawAlternatesBloc("greenbloc", "bloc", 414*dx, surface - 26*dx, 3, 2, 3);
                drawLine("BrownBloc", "greenbrick", 561*dx, 562*dx + CASTLE_WIDTH, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);

                return;
            case 3 :

                final int nuageWidth = (int) (7*dx);
                final int nuageHeight = (int) (0.9465*nuageWidth);

                drawDecoration("nuage", 30*dx, 2*dx, nuageWidth, nuageHeight);
                drawDecoration("nuage", 60*dx, 3*dx, nuageWidth, nuageHeight);
                drawDecoration("nuage", 75*dx, 1*dx, nuageWidth, nuageHeight);
                drawDecoration("nuage", 100*dx, 5*dx, nuageWidth, nuageHeight);
                drawDecoration("nuage", 130*dx, 1*dx, nuageWidth, nuageHeight);
                drawDecoration("nuage", 143*dx, 4*dx, nuageWidth, nuageHeight);
                drawDecoration("nuage", 160*dx, 3*dx, nuageWidth, nuageHeight);
                drawDecoration("nuage", 184*dx, 4*dx, nuageWidth, nuageHeight);
                drawDecoration("nuage", 200*dx, 1*dx, nuageWidth, nuageHeight);

                drawCastle("castle", 0);
                drawCastle("castle", 500*dx );

                drawLine("BrownBloc", "yellowbrick", 0, 550*dx, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);
                drawDecoration("pyramides", CASTLE_WIDTH,0, displayWidth, displayHeight-FLOOR_HEIGHT);
                drawDecoration("sable", CASTLE_WIDTH + displayWidth,0, displayWidth, displayHeight-FLOOR_HEIGHT);
                drawDecoration("sable", CASTLE_WIDTH + displayWidth*2,0, displayWidth, displayHeight-FLOOR_HEIGHT);
                drawDecoration("sable", CASTLE_WIDTH + displayWidth*3,0, displayWidth, displayHeight-FLOOR_HEIGHT);
                drawDecoration("sable", CASTLE_WIDTH + displayWidth*4,0, displayWidth, displayHeight-FLOOR_HEIGHT);
                drawDecoration("pyramides", CASTLE_WIDTH + displayWidth*5,0, displayWidth, displayHeight-FLOOR_HEIGHT);
                drawDecoration("palmier", 40*dx, 12*dx, 30*dx);
                drawDecoration("palmier", 80*dx, 12*dx, 30*dx);

                drawAlternatesBloc("goldenbloc", "bloc", 60*dx, surface - 15*dx, 3, 2, 3);
                drawPlantPirhanaWithGreenPipe(100*dx, surface-PIPE_HEIGHT);
                drawPlantPirhanaWithGreenPipe(180*dx, surface-PIPE_HEIGHT);
                drawSpiny("spiny", 139*dx, surface - 5*dx, 3*dx, 3*dx, true, true) ;
                drawAlternatesBloc("goldenbloc", "bloc", 134*dx, surface - 15*dx, 3, 1, 3);
                drawBloc("bloc", 138*dx, surface - 30*dx, BLOC_WIDTH, BLOC_HEIGHT, 5);

                drawDecoration("palmier", 190*dx, 12*dx, 30*dx);
                drawCoconut("coconut", 189*dx, surface - 26*dx, 3*dx, 3*dx);
                drawDecoration("palmier", 270*dx, 12*dx, 30*dx);
                drawCoconut("coconut", 270*dx, surface - 29*dx, 3*dx, 3*dx);
                drawCoconut("coconut", 275*dx, surface - 26*dx, 3*dx, 3*dx);
                drawCoconut("coconut", 280*dx, surface - 27*dx, 3*dx, 3*dx);
                drawColumn("BrownBloc", "brique_jaune", 210*dx, surface-4*dx, 3, 4*dx, 4*dx, true);
                drawColumn("BrownBloc", "brique_jaune", 210*dx, surface-24*dx, 3, 4*dx, 4*dx, true);
                drawColumn("BrownBloc", "brique_jaune", 258*dx, surface-4*dx, 3, 4*dx, 4*dx, true);
                drawColumn("BrownBloc", "brique_jaune", 258*dx, surface-24*dx, 3, 4*dx, 4*dx, true);
                drawLine("BrownBloc", "brique_jaune", 214*dx, 228*dx, surface-12*dx, BLOC_WIDTH, BLOC_HEIGHT, false);
                drawLine("BrownBloc", "brique_jaune", 246*dx, 258*dx, surface-12*dx, BLOC_WIDTH, BLOC_HEIGHT, false);
                drawLine("BrownBloc", "brique_jaune", 228*dx, 246*dx, surface-24*dx, BLOC_WIDTH, BLOC_HEIGHT, false);
                drawPieceLine(215*dx, surface-PIECE_HEIGHT-dx, 14);
                drawGoomba("goomba", 225*dx, surface - 27*dx, false, true);
                drawSpiny("spiny", 240*dx, surface - 4*dx, 3*dx, 3*dx, false, true);
                drawColumn("BrownBloc", "brique_jaune", 290*dx, surface - 4*dx, 3, 4*dx, 4*dx, false);
                drawGoomba("goomba", 282*dx, surface-4*dx, true, true);
                drawDecoration("palmier", 320*dx, surface - 17*dx, 5*dx, 15*dx);
                drawDecoration("palmier", 340*dx, surface - 17*dx, 5*dx, 15*dx);
                drawDecoration("palmier", 360*dx, surface - 16*dx, 5*dx, 15*dx);
                drawDecoration("palmier", 305*dx, 15*dx, 38*dx);
                drawDecoration("palmier", 314*dx, 10*dx, 25*dx);
                drawDecoration("palmier", 325*dx, 12*dx, 30*dx);
                drawDecoration("palmier", 336*dx, 8*dx, 20*dx);
                drawDecoration("palmier", 345*dx, 13*dx, 34*dx);
                drawDecoration("palmier", 358*dx, 16*dx, 39*dx);
                drawCoconut("coconut", 306*dx, surface - 26*dx, 3*dx, 3*dx);
                drawCoconut("coconut", 322*dx, surface - 23*dx, 3*dx, 3*dx);
                drawCoconut("coconut", 350*dx, surface - 30*dx, 3*dx, 3*dx);
                drawCoconut("coconut", 360*dx, surface - 39*dx, 3*dx, 3*dx);
                drawCoconut("coconut", 371*dx, surface - 39*dx, 3*dx, 3*dx);
                drawKoopa("redkoopa", 320*dx, surface-5*dx, true, false);
                drawKoopa("redkoopa", 320*dx, surface-5*dx, true, true);
                drawColumn("BrownBloc", "brique_jaune", 385*dx, surface-4*dx, 3, 4*dx, 4*dx, true);
                drawPokey("pokey", 120*dx, surface-20*dx);
                drawPokey("pokey", 160*dx, surface - 20*dx);
                drawPokey("pokey", 390*dx, surface - 20*dx);
                drawPokey("pokey", 440*dx, surface - 20*dx);
                drawPokey("pokey", 490*dx, surface - 20*dx);
                drawDecoration("palmier", 390*dx, 12*dx, 30*dx);
                drawDecoration("palmier", 430*dx, 12*dx, 30*dx);
                drawCoconut("coconut", 433*dx, surface - 26*dx, 3*dx, 3*dx);
                return;
            case 4 :

                int decorVolcanWidth = (int) ((displayHeight-FLOOR_HEIGHT)*1.179);
                int decorVolcanHeight = displayHeight- FLOOR_HEIGHT;

                drawCastle("castle", 0);
                drawCastle("castle", 521*dx );
                drawLine("Objet", "volcan_decor2", 0, 560*dx, 0, displayHeight, displayHeight, false);
                drawLine("BrownBloc", "darkbrick", 0, CASTLE_WIDTH+1, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);
                drawPlatformeEphemereLine("tile1", CASTLE_WIDTH - dx, surface, 3*dx, 3*dx, 70, false, false, 7);
                drawBloc("briquestriee", displayWidth/2 - 5*dx, surface, 10*dx, 10*dx, 1);
                drawBloc("briquestriee", displayWidth/2 + 5*dx, surface - 4*dx, 10*dx, 10*dx, 1);
                drawBloc("briquestriee", displayWidth/2 + 15*dx, surface - 8*dx, 10*dx, 10*dx, 1);
                drawBloc("bloc", 40*dx, surface-3*BLOC_HEIGHT-dx, BLOC_WIDTH, BLOC_HEIGHT, 6);
                drawPlatformeEphemereLine("tile1", 75*dx, surface - 8*dx, 3*dx, 3*dx, 70, false, false, 3);
                drawPlatformeEphemereLine("tile1", 100*dx, surface - 8*dx, 3*dx, 3*dx, 70, false, false, 3);
                drawPlatformeEphemereLine("tile1", 125*dx, surface - 8*dx, 3*dx, 3*dx, 70, false, false, 3);
                drawPodoboo("podoboo", 90*dx, surface-15*dx, 3*dx, 3*dx);
                drawPodoboo("podoboo", 93*dx, surface-15*dx, 3*dx, 3*dx);
                drawPodoboo("podoboo", 115*dx, surface-15*dx, 3*dx, 3*dx);
                drawPodoboo("podoboo", 118*dx, surface-15*dx, 3*dx, 3*dx);
                drawLine("BrownBloc", "greybrick3", 140*dx, 150*dx, surface - 8*dx, 5*dx, 5*dx, false);
                drawLine("BrownBloc", "greybrick3", 150*dx, 160*dx, surface - 13*dx, 5*dx, 5*dx, false);
                drawLine("BrownBloc", "greybrick3", 160*dx, 170*dx, surface-18*dx, 5*dx, 5*dx, false);
                drawLine("BrownBloc", "greybrick3", 170*dx, 260*dx, surface - 23*dx, 5*dx, 5*dx, false);
                drawBloc("darkbloc", 213*dx, surface - 27*dx, BLOC_WIDTH, BLOC_HEIGHT, 1);
                drawKoopa("redkoopa", 180*dx, surface - 27*dx, false, true);
                drawKoopa("redkoopa", 250*dx, surface - 27*dx, false, false);
                drawAlternatesBloc("darkbloc", "bloc", 187*dx, 2*dx, 3, 1, 3);
                drawAlternatesBloc("darkbloc", "bloc", 233*dx, 2*dx, 3, 1, 3);
                drawLine("BrownBloc", "greybrick3", 260*dx, 270*dx, surface - 18*dx, 5*dx, 5*dx, true);
                drawLine("BrownBloc", "greybrick3", 270*dx, 300*dx, surface - 13*dx, 5*dx, 5*dx, false);
                drawLine("BrownBloc", "greybrick3", 310*dx, 340*dx,surface - 13*dx, 5*dx, 5*dx, false);
                drawLine("BrownBloc", "greybrick3", 160*dx, 180*dx, displayHeight-5*dx,5*dx, 5*dx, false);
                drawPlatformeEphemereLine("tile1", 183*dx, displayHeight - 5*dx, 3*dx, 3*dx, 70, false, false, 2);
                drawLine("BrownBloc", "greybrick3", 192*dx, 214*dx, displayHeight-5*dx,5*dx, 5*dx, false);
                drawPlatformeEphemereLine("tile1", 215*dx, displayHeight-5*dx, 3*dx, 3*dx, 70, false, false, 2);
                drawLine("BrownBloc", "greybrick3", 224*dx, 244*dx, displayHeight-5*dx,5*dx, 5*dx, false);
                drawPlatformeEphemereLine("tile1", 247*dx, displayHeight-5*dx, 3*dx, 3*dx, 70, false, false, 2);
                drawLine("BrownBloc", "greybrick3", 256*dx, 276*dx, displayHeight-5*dx,5*dx, 5*dx, false);
                drawPlatformeEphemereLine("tile1", 276*dx, displayHeight-5*dx, 3*dx, 3*dx, 70, false, false, 4);
                drawLine("BrownBloc", "greybrick3", 288*dx, 315*dx, displayHeight-5*dx,5*dx, 5*dx, false);
                drawAlternatesBloc("darkbloc", "bloc", 180*dx, surface - 14*dx, 3, 1, 5);
                drawAlternatesBloc("darkbloc", "bloc", 212*dx, surface - 14*dx, 3, 1, 4);
                drawAlternatesBloc("darkbloc", "bloc", 244*dx, surface - 14*dx, 3, 1, 3);
                drawGoomba("goomba", 155*dx, surface - 16*dx, false, true);
                drawPodoboo("podoboo", 180*dx, surface, 3*dx, 3*dx);
                drawPodoboo("podoboo", 189*dx, surface, 3*dx, 3*dx);
                drawPodoboo("podoboo", 212*dx, surface, 3*dx, 3*dx);
                drawPodoboo("podoboo", 221*dx, surface, 3*dx, 3*dx);
                drawPodoboo("podoboo", 244*dx, surface, 3*dx, 3*dx);
                drawPodoboo("podoboo", 253*dx, surface, 3*dx, 3*dx);
                drawGoomba("goomba", 260*dx, surface - 21*dx, false, true);
                drawKoopa("redkoopa", 280*dx, surface - 16*dx, false, true);
                drawKoopa("redkoopa", 320*dx, surface - 16*dx, false, true);
                drawColumn("BrownBloc", "brique_marron",340*dx, surface+dx, 5, 5*dx, 5*dx, true);
                drawLine("BrownBloc", "brique_marron", 345*dx, 450*dx, surface + dx, 5*dx, 5*dx, false);
                drawColumn("BrownBloc", "brique_marron",450*dx, surface+dx, 5, 5*dx, 5*dx, true);
                drawBloc("brique_marron", 445*dx, surface - 4*dx, 5*dx, 5*dx, 1);
                drawPlatformeEphemereLine("tile1", 455*dx, surface - 24*dx, 3*dx, 3*dx, 70, false, false, 2);
                drawPlatformeEphemereLine("tile1", 470*dx, surface - 18*dx, 3*dx, 3*dx, 70, false, false, 2);
                drawPlatformeEphemereLine("tile1", 485*dx, surface - 12*dx, 3*dx, 3*dx, 70, false, false, 2);
                drawPlatformeEphemereLine("tile1", 500*dx, surface - 6*dx, 3*dx, 3*dx, 70, false, false, 2);
                drawPlatformeEphemereLine("tile1", 515*dx, surface, 3*dx, 3*dx, 70, false, false, 2);
                drawMagikoopa(445*dx, surface - 9*dx);
                drawPodoboo("podoboo", 480*dx, surface - 20*dx, 3*dx, 3*dx);
                drawPodoboo("podoboo", 495*dx, surface - 14*dx, 3*dx, 3*dx);

                return;
            case 5 :
                drawCastle("castle", 0);
                drawCastle("castle", 550*dx );
                drawBloc("forest_platform",0, surface, CASTLE_WIDTH, CASTLE_HEIGHT/3, 1 );
                drawLine("BrownBloc", "brownbloc", CASTLE_WIDTH + 2*BLOC_WIDTH, 76*dx, surface, BLOC_WIDTH, BLOC_HEIGHT, true);
                drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 80*dx, displayHeight-15*dx,5*dx, 20*dx, 20*dx, 5*dx, 3);
                drawStaticPlatform("pillier_champi_platforme", "champi_platforme_jaune", 110*dx, displayHeight-20*dx,5*dx, 21*dx, 20*dx, 5*dx, 2);
                drawMovablePlatform("platforme", 130*dx, surface - 10*dx, 10*dx, (int) (20*dx*0.1657), true, 10*dx, surface-6*dx);
                drawMovablePlatform("platforme", 150*dx, surface - 10*dx, 10*dx, (int) (20*dx*0.1657), false, 10*dx, surface-6*dx);
                drawMovablePlatform("platforme", 170*dx, surface - 10*dx, 10*dx, (int) (20*dx*0.1657), true, 10*dx, surface-6*dx);
                drawMovablePlatform("platforme", 190*dx, surface - 10*dx, 10*dx, (int) (20*dx*0.1657), false, 10*dx, surface-6*dx);
                drawLine("BrownBloc", "brownbloc", 210*dx, 255*dx, surface - 5*BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, true);
                drawBloc("bloc", 254*dx, surface - 5*BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 5);
                drawLine("BrownBloc", "brownbloc", 258*dx, 300*dx, surface - 5*BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, true);
                drawLine("BrownBloc", "brownbloc", 210*dx, 300*dx, displayHeight-BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, true);
                drawBloc("bloc_jaune", 294*dx, surface - 6*BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 1);
                drawMovablePlatform("nuageplatform2", 300*dx, displayHeight-BLOC_HEIGHT, 10*dx, 3*dx, true, surface - 5*BLOC_HEIGHT, displayHeight - BLOC_HEIGHT);
                drawMovablePlatform("nuageplatform2", 310*dx, displayHeight-BLOC_HEIGHT, 10*dx, 3*dx, false, surface - 5*BLOC_HEIGHT, displayHeight - BLOC_HEIGHT);
                drawPlatformeEphemereLine("tile1", 322*dx, surface - 2*BLOC_HEIGHT, 3*dx, 3*dx, 50, false, false, 10);
                drawPlatformeEphemereLine("tile1", 352*dx, surface - 2*BLOC_HEIGHT-3*dx, 3*dx, 3*dx, 50, false, false, 10);
                drawBloc("forest_platform", 385*dx, surface - 2*BLOC_HEIGHT-3*dx, CASTLE_WIDTH, CASTLE_HEIGHT/3, 2);
                drawBloc("forest_platform", 385*dx+CASTLE_WIDTH +4*dx, surface - 8*dx, CASTLE_WIDTH, CASTLE_HEIGHT/3, 1);
                drawCanon("canon", 425*dx, surface - 18*dx, 5*dx, 10*dx, false);
                drawBloc("forest_platform", 440*dx, surface - 8*dx, CASTLE_WIDTH, CASTLE_HEIGHT/3, 1);
                drawPyramid("BrownBloc", "brique_jaune", 480*dx, 10, surface-8*dx, 4*dx, 4*dx);
                drawUnmovablePlatforme("nuageplatform2", 479*dx, (int) (surface - 6.5*dx), 10*dx, 3*dx);
                drawUnmovablePlatforme("nuageplatform2", 496*dx, (int) (surface - 6.5*dx), 10*dx, 3*dx);
                drawUnmovablePlatforme("nuageplatform2", 511*dx, (int) (surface - 6.5*dx), 10*dx, 3*dx);
                drawUnmovablePlatforme("nuageplatform2", 487*dx, (int) (surface - 6.5*dx), 10*dx, 3*dx);
                drawUnmovablePlatforme("nuageplatform2", 503*dx, (int) (surface - 6.5*dx), 10*dx, 3*dx);
                drawGoomba("goomba", 120*dx, displayHeight-26*dx, false, true);
                drawAlternatesBloc("bloc_jaune", "bloc", 76*dx, displayHeight - 33*dx, 3, 1, 3);
                return;
        }
    }
    public void level1() {

        objets = new ArrayList<Objet>();
        persos = new ArrayList<Personnage>();

        this.dx = (int) (displayWidth*0.01);
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int surface = displayHeight-FLOOR_HEIGHT;
        this.PIECE_WIDTH = (int)(displayWidth*0.03);
        this.PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1354);

        final int nuageSimpeWidth = (int) (displayWidth*0.076);
        final int nuageSimpleHeight = (int) (0.9465*nuageSimpeWidth);

        final int hardsquareWidth = (int) (BLOC_WIDTH*0.8);
        final int hardSquareHeight = hardsquareWidth;
        final int hardSquareY = displayHeight - FLOOR_HEIGHT - hardSquareHeight;

        drawCastle("castle", 0);
        drawCastle("castle", 450*dx);
        drawLine("BrownBloc", "redbrick", 0, 455*dx+CASTLE_WIDTH, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);

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
        drawDecoration("nuage", 175*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 200*dx, 4*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 229*dx, 3*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 245*dx, 2*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 263*dx, 5*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 280*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 312*dx, 3*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 330*dx, 2*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 367*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 385*dx, 4*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 418*dx, 3*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("nuage", 448*dx, 1*dx, nuageSimpeWidth, nuageSimpleHeight);
        drawDecoration("hills1", 230*dx, 30*dx, 30*dx);
        drawDecoration("buisson_simple", 25*dx, 5*dx, (int) (2.5*dx));
        drawDecoration("buisson_triple", 140*dx, 15*dx, 3*dx);
        drawDecoration("buisson_triple", 180*dx, surface - 5*dx, 15*dx, 3*dx);

        drawGoomba("goomba", 65*dx, surface-4*dx, true, true);
        drawAlternatesBloc("brownbloc", "bloc", 42*dx, surface - 15*dx, 3, 2, 4);
        drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 80*dx, 4*dx, 10*dx, 12*dx, 4*dx, 2);
        drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 120*dx, 4*dx, 15*dx, 15*dx, 4*dx, 2);
        drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 160*dx, 4*dx, 10*dx, 12*dx, 4*dx, 2);
        drawLine("BrownBloc", "hardbloc", 164*dx, 200*dx, displayHeight-FLOOR_HEIGHT-hardSquareHeight, hardsquareWidth, hardSquareHeight, false);
        drawScale("BrownBloc", "hardbloc", 199*dx, 4, displayHeight-FLOOR_HEIGHT-hardSquareHeight, hardsquareWidth, hardSquareHeight);
        drawColumn("BrownBloc", "hardbloc", 229*dx, surface - hardSquareHeight, 4, hardsquareWidth, hardSquareHeight, false);
        drawColumn("BrownBloc", "hardbloc", 232*dx, surface - hardSquareHeight, 3, hardsquareWidth, hardSquareHeight, false);
        drawColumn("BrownBloc", "hardbloc", 235*dx, surface - hardSquareHeight, 2, hardsquareWidth, hardSquareHeight, false);
        drawBloc("hardbloc", 238*dx, surface-hardSquareHeight, hardsquareWidth, hardSquareHeight, 1);
        //drawLine("BrownBloc", "hardbloc", 199*dx+7*hardsquareWidth,199*dx+7*hardsquareWidth+10*hardsquareWidth ,displayHeight-FLOOR_HEIGHT-7*hardSquareHeight, hardsquareWidth, hardSquareHeight, false);
        //drawPieceSquare(199*dx+7*hardsquareWidth+dx, displayHeight-FLOOR_HEIGHT-PIECE_HEIGHT-dx, 5);
        //drawBloc("bloc", 190*dx, displayHeight-FLOOR_HEIGHT-hardSquareHeight-4*BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 4);
        drawGoomba("goomba", 85*dx, surface-4*dx, true, true);
        drawKoopa("greenkoopa", 125*dx, (int) (surface-(displayWidth*0.03 - 1.7458) - 3*dx), true, false);
        drawKoopa("greenkoopa", 170*dx, (int) (surface-(displayWidth*0.03 - 1.7458) - 6*dx),true, false);
        drawParakoopa("greenparakoopa", 275*dx, surface - 27*dx, false, true);
        drawBloc("bloc", 219*dx, displayHeight-FLOOR_HEIGHT-hardSquareHeight-4*BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 3);
        drawGoomba("goomba", 220*dx, surface - 5*dx, false, false);
        drawMovablePlatform("platforme", 100*dx, surface - 10*dx, 10*dx, (int) (20*dx*0.1657), true, 10*dx, surface-6*dx);
        drawMovablePlatform("platforme", 275*dx, surface - 6*dx, 10*dx, (int) (20*dx*0.1657), true, 10*dx, surface-6*dx);
        drawMovablePlatform("platforme", 295*dx, 10*dx, 10*dx, (int) (20*dx*0.1657), false, 10*dx, surface-6*dx);
        drawMovablePlatform("platforme", 315*dx, surface - 6*dx, 10*dx, (int) (20*dx*0.1657), true, 10*dx, surface-6*dx);
        drawMovablePlatform("platforme", 335*dx, 10*dx, 10*dx, (int) (20*dx*0.1657), false, 10*dx, surface-6*dx);
        drawParakoopa("greenparakoopa", 320*dx, surface - 27*dx, true, true);
        drawPieceLine(275*dx, surface - 22*dx, 24);
        drawPieceLine(275*dx, surface - 27*dx, 10);
        drawAlternatesBloc("brownbloc", "bloc", 304*dx, surface-27*dx, 3, 1, 4);
        drawPieceLine(317*dx, surface - 27*dx, 10);
        drawPieceLine(275*dx, surface - 32*dx, 24);
        drawPipe("greenpipe", 288*dx, surface-PIPE_HEIGHT);
        drawPipe("greenpipe", 350*dx, surface-PIPE_HEIGHT);
        //drawBloc("brownbloc", 304*dx, surface-BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 1);
        //drawBloc("brownbloc", 340*dx, surface-BLOC_HEIGHT, BLOC_WIDTH, BLOC_HEIGHT, 1);
        drawGoomba("goomba", 260*dx, surface - 4*dx, false, true);
        drawGoomba("goomba", 263*dx, surface - 4*dx, false, true);
        drawGoomba("goomba", 330*dx, surface - 5*dx, false, true);
        drawDecoration("buisson_simple", 260*dx, 5*dx, (int) (2.5*dx));
        drawDecoration("buisson_simple", 310*dx, 5*dx, (int) (2.5*dx));
        drawDecoration("colline_petite", 315*dx, 15*dx, 7*dx);

        drawUnmovablePlatforme("herbe_platforme", 360*dx, surface - 15*dx, 17*dx, 4*dx);
        drawDecoration("pillier_terre", 361*dx, surface - 12*dx,14*dx, 14*dx);
        drawUnmovablePlatforme("herbe_platforme", 374*dx, surface - 27*dx, 17*dx, 4*dx);
        drawDecoration("pillier_terre", 375*dx, surface - 24*dx,14*dx, 14*dx);
        drawDecoration("pillier_terre", 375*dx, surface - 12*dx,14*dx, 14*dx);
        drawUnmovablePlatforme("herbe_platforme", 387*dx, surface - 8*dx, 17*dx, 4*dx);
        drawDecoration("pillier_terre", 389*dx, (int) (surface - 6*dx),14*dx, 14*dx);

        drawKoopa("greenkoopa", 390*dx, surface-31*dx, false, true);
        drawKoopa("greenkoopa", 368*dx, surface - 19*dx, false, true);
        drawGoomba("goomba", 395*dx, surface-12*dx, false, false);
        drawPieceColumn(395*dx, surface - 12*dx, 5);
        drawDecoration("colline_grande", 420*dx, 16*dx, 7*dx);
        drawDecoration("buisson_triple", 439*dx, 12*dx, 4*dx);
    }
    public void level2() {

        objets = new ArrayList<Objet>();
        persos = new ArrayList<Personnage>();

        this.dx = (int) (displayWidth*0.01);
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int surface = displayHeight-FLOOR_HEIGHT;
        this.PIECE_WIDTH = (int)(displayWidth*0.03);
        this.PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1354);

        final int nuageSimpeWidth = (int) (displayWidth*0.076);
        final int nuageSimpleHeight = (int) (0.9465*nuageSimpeWidth);

        final int hardsquareWidth = (int) (BLOC_WIDTH*0.8);
        final int hardSquareHeight = hardsquareWidth;
        final int hardSquareY = displayHeight - FLOOR_HEIGHT - hardSquareHeight;

        drawCastle("greencastle", 0);
        drawCastle("greencastle", 561*dx);
        drawLine("BrownBloc", "greenbrick", 0, 400*dx, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);
        drawAlternatesBloc("greenbloc", "bloc", 30*dx, surface - 15*dx, 10, 2, 3);
        drawLine("Objet", "greybrick2", 0, 400*dx, 0, 3*dx, 3*dx, false);
        drawLine("BrownBloc", "greybrick2", 0, 186*dx, 3*dx, 3*dx, 3*dx, false);
        drawLine("BrownBloc", "greybrick2", 194*dx, 250*dx,3*dx, 3*dx, 3*dx, false);

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
        drawLine("BrownBloc", "greybrick", 194*dx, 400*dx, surface - greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawColumn("BrownBloc", "greybrick", 200*dx, surface-greyBrickDim, 5, greyBrickDim, greyBrickDim, false);
        drawColumn("BrownBloc", "greybrick", 248*dx, surface-greyBrickDim, 5, greyBrickDim, greyBrickDim, true);
        drawSkelerex(223*dx, surface-greyBrickDim-6*dx, true, false);
        drawAlternatesBloc("greenbloc", "bloc", 220*dx, surface-4*BLOC_WIDTH, 3, 2, 4);
        drawBoo(248*dx, greyBrickDim, "greybrick2");
        drawLine("BrownBloc", "greybrick2", 251*dx, 280*dx, greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawThwomp(278*dx, (int) (-8*dx*1.2513));

        drawColumn("BrownBloc", "greybrick2", 283*dx+3*dx, surface-8*greyBrickDim, 5, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 289*dx, greyBrickDim, 319*dx, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 289*dx, 319*dx, greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 289*dx, 319*dx, 2*greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 289*dx, 319*dx, 3*greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 289*dx, 319*dx, 4*greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawLine("BrownBloc", "greybrick2", 289*dx, 319*dx, 5*greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawColumn("BrownBloc", "greybrick2", 319*dx, surface-8*greyBrickDim, 5, greyBrickDim, greyBrickDim, false);
        drawThwomp(322*dx, (int) (-8*dx*1.2513));

        drawColumn("BrownBloc", "greybrick2", 330*dx, surface-8*greyBrickDim, 5, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 333*dx, greyBrickDim, 319*dx, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 333*dx, 363*dx, greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 333*dx, 363*dx, 2*greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 333*dx, 363*dx, 3*greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawLine("Objet", "greybrick2", 333*dx, 345*dx, 4*greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawBoo(345*dx, 4*greyBrickDim, "greybrick2");
        drawLine("Objet", "greybrick2", 348*dx, 363*dx,4*greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawLine("BrownBloc", "greybrick2", 333*dx, 363*dx, 5*greyBrickDim, greyBrickDim, greyBrickDim, false);
        drawColumn("BrownBloc", "greybrick2", 363*dx, surface-8*greyBrickDim, 5, greyBrickDim, greyBrickDim, false);

        drawBloc("greenbloc", 302*dx, surface - greyBrickDim - BLOC_WIDTH, BLOC_WIDTH, BLOC_HEIGHT, 2);
        drawBloc("greenbloc", 346*dx, surface - greyBrickDim - BLOC_WIDTH, BLOC_WIDTH, BLOC_HEIGHT, 2);
        drawPlatformeEphemereLine("tile1", 405*dx, surface - 12*dx, 3*dx, 3*dx, 70, false, false, 10);
        drawPlatformeEphemereLine("tile1", 435*dx, surface - 9*dx, 3*dx, 3*dx, 70, false, false, 10);
        drawPlatformeEphemereLine("tile1", 465*dx, surface - 12*dx, 3*dx, 3*dx, 70, false, false, 10);
        drawPlatformeEphemereLine("tile1", 495*dx, surface - 9*dx, 3*dx, 3*dx, 70, false, false, 10);
        drawPlatformeEphemereLine("tile1", 525*dx, surface - 6*dx, 3*dx, 3*dx, 70, false, false,4);
        drawPlatformeEphemereLine("tile1", 537*dx, surface - 3*dx, 3*dx, 3*dx, 70, false, false,4);
        drawPlatformeEphemereLine("tile1", 549*dx, surface, 3*dx, 3*dx, 70, false, false,4);


        drawBoo(432*dx, surface - 9*dx, "tile1_1");
        drawBoo(465*dx, surface - 9*dx, "tile1_1");
        drawSkelerex(440*dx, surface-13*dx, false, true);
        drawSkelerex(500*dx, surface-13*dx, false, true);
        drawAlternatesBloc("greenbloc", "bloc", 414*dx, surface - 26*dx, 3, 2, 3);
        drawLine("BrownBloc", "greenbrick", 561*dx, 562*dx + CASTLE_WIDTH, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);

    }
    public void level3() {

        objets = new ArrayList<Objet>();
        persos = new ArrayList<Personnage>();

        this.dx = (int) (displayWidth*0.01);
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int surface = displayHeight-FLOOR_HEIGHT;
        this.PIECE_WIDTH = (int)(displayWidth*0.03);
        this.PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1354);

        final int nuageSimpeWidth = (int) (displayWidth*0.076);
        final int nuageSimpleHeight = (int) (0.9465*nuageSimpeWidth);

        final int hardsquareWidth = (int) (BLOC_WIDTH*0.8);
        final int hardSquareHeight = hardsquareWidth;
        final int hardSquareY = displayHeight - FLOOR_HEIGHT - hardSquareHeight;
        final int nuageWidth = (int) (7*dx);
        final int nuageHeight = (int) (0.9465*nuageWidth);

        drawDecoration("nuage", 30*dx, 2*dx, nuageWidth, nuageHeight);
        drawDecoration("nuage", 60*dx, 3*dx, nuageWidth, nuageHeight);
        drawDecoration("nuage", 75*dx, 1*dx, nuageWidth, nuageHeight);
        drawDecoration("nuage", 100*dx, 5*dx, nuageWidth, nuageHeight);
        drawDecoration("nuage", 130*dx, 1*dx, nuageWidth, nuageHeight);
        drawDecoration("nuage", 143*dx, 4*dx, nuageWidth, nuageHeight);
        drawDecoration("nuage", 160*dx, 3*dx, nuageWidth, nuageHeight);
        drawDecoration("nuage", 184*dx, 4*dx, nuageWidth, nuageHeight);
        drawDecoration("nuage", 200*dx, 1*dx, nuageWidth, nuageHeight);

        drawCastle("castle", 0);
        drawCastle("castle", 500*dx );

        drawLine("BrownBloc", "yellowbrick", 0, 550*dx, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);
        drawDecoration("pyramides", CASTLE_WIDTH,0, displayWidth, displayHeight-FLOOR_HEIGHT);
        drawDecoration("sable", CASTLE_WIDTH + displayWidth,0, displayWidth, displayHeight-FLOOR_HEIGHT);
        drawDecoration("sable", CASTLE_WIDTH + displayWidth*2,0, displayWidth, displayHeight-FLOOR_HEIGHT);
        drawDecoration("sable", CASTLE_WIDTH + displayWidth*3,0, displayWidth, displayHeight-FLOOR_HEIGHT);
        drawDecoration("sable", CASTLE_WIDTH + displayWidth*4,0, displayWidth, displayHeight-FLOOR_HEIGHT);
        drawDecoration("pyramides", CASTLE_WIDTH + displayWidth*5,0, displayWidth, displayHeight-FLOOR_HEIGHT);
        drawDecoration("palmier", 40*dx, 12*dx, 30*dx);
        drawDecoration("palmier", 80*dx, 12*dx, 30*dx);

        drawAlternatesBloc("goldenbloc", "bloc", 60*dx, surface - 15*dx, 3, 2, 3);
        drawPlantPirhanaWithGreenPipe(100*dx, surface-PIPE_HEIGHT);
        drawPlantPirhanaWithGreenPipe(180*dx, surface-PIPE_HEIGHT);
        drawSpiny("spiny", 139*dx, surface - 5*dx, 3*dx, 3*dx, true, true) ;
        drawAlternatesBloc("goldenbloc", "bloc", 134*dx, surface - 15*dx, 3, 1, 3);
        drawBloc("bloc", 138*dx, surface - 30*dx, BLOC_WIDTH, BLOC_HEIGHT, 5);

        drawDecoration("palmier", 190*dx, 12*dx, 30*dx);
        drawCoconut("coconut", 189*dx, surface - 26*dx, 3*dx, 3*dx);
        drawDecoration("palmier", 270*dx, 12*dx, 30*dx);
        drawCoconut("coconut", 270*dx, surface - 29*dx, 3*dx, 3*dx);
        drawCoconut("coconut", 275*dx, surface - 26*dx, 3*dx, 3*dx);
        drawCoconut("coconut", 280*dx, surface - 27*dx, 3*dx, 3*dx);
        drawColumn("BrownBloc", "brique_jaune", 210*dx, surface-4*dx, 3, 4*dx, 4*dx, true);
        drawColumn("BrownBloc", "brique_jaune", 210*dx, surface-24*dx, 3, 4*dx, 4*dx, true);
        drawColumn("BrownBloc", "brique_jaune", 258*dx, surface-4*dx, 3, 4*dx, 4*dx, true);
        drawColumn("BrownBloc", "brique_jaune", 258*dx, surface-24*dx, 3, 4*dx, 4*dx, true);
        drawLine("BrownBloc", "brique_jaune", 214*dx, 228*dx, surface-12*dx, BLOC_WIDTH, BLOC_HEIGHT, false);
        drawLine("BrownBloc", "brique_jaune", 246*dx, 258*dx, surface-12*dx, BLOC_WIDTH, BLOC_HEIGHT, false);
        drawLine("BrownBloc", "brique_jaune", 228*dx, 246*dx, surface-24*dx, BLOC_WIDTH, BLOC_HEIGHT, false);
        drawPieceLine(215*dx, surface-PIECE_HEIGHT-dx, 14);
        drawGoomba("goomba", 225*dx, surface - 27*dx, false, true);
        drawSpiny("spiny", 240*dx, surface - 4*dx, 3*dx, 3*dx, false, true);
        drawColumn("BrownBloc", "brique_jaune", 290*dx, surface - 4*dx, 3, 4*dx, 4*dx, false);
        drawGoomba("goomba", 282*dx, surface-4*dx, true, true);
        drawDecoration("palmier", 320*dx, surface - 17*dx, 5*dx, 15*dx);
        drawDecoration("palmier", 340*dx, surface - 17*dx, 5*dx, 15*dx);
        drawDecoration("palmier", 360*dx, surface - 16*dx, 5*dx, 15*dx);
        drawDecoration("palmier", 305*dx, 15*dx, 38*dx);
        drawDecoration("palmier", 314*dx, 10*dx, 25*dx);
        drawDecoration("palmier", 325*dx, 12*dx, 30*dx);
        drawDecoration("palmier", 336*dx, 8*dx, 20*dx);
        drawDecoration("palmier", 345*dx, 13*dx, 34*dx);
        drawDecoration("palmier", 358*dx, 16*dx, 39*dx);
        drawCoconut("coconut", 306*dx, surface - 26*dx, 3*dx, 3*dx);
        drawCoconut("coconut", 322*dx, surface - 23*dx, 3*dx, 3*dx);
        drawCoconut("coconut", 350*dx, surface - 30*dx, 3*dx, 3*dx);
        drawCoconut("coconut", 360*dx, surface - 39*dx, 3*dx, 3*dx);
        drawCoconut("coconut", 371*dx, surface - 39*dx, 3*dx, 3*dx);
        drawKoopa("redkoopa", 320*dx, surface-5*dx, true, false);
        drawKoopa("redkoopa", 320*dx, surface-5*dx, true, true);
        drawColumn("BrownBloc", "brique_jaune", 385*dx, surface-4*dx, 3, 4*dx, 4*dx, true);
        drawPokey("pokey", 120*dx, surface-20*dx);
        drawPokey("pokey", 160*dx, surface - 20*dx);
        drawPokey("pokey", 390*dx, surface - 20*dx);
        drawPokey("pokey", 440*dx, surface - 20*dx);
        drawPokey("pokey", 490*dx, surface - 20*dx);
        drawDecoration("palmier", 390*dx, 12*dx, 30*dx);
        drawDecoration("palmier", 430*dx, 12*dx, 30*dx);
        drawCoconut("coconut", 433*dx, surface - 26*dx, 3*dx, 3*dx);
    }

    public void level4() {

        objets = new ArrayList<Objet>();
        persos = new ArrayList<Personnage>();

        this.dx = (int) (displayWidth*0.01);
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int surface = displayHeight-FLOOR_HEIGHT;
        this.PIECE_WIDTH = (int)(displayWidth*0.03);
        this.PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1354);

        final int nuageSimpeWidth = (int) (displayWidth*0.076);
        final int nuageSimpleHeight = (int) (0.9465*nuageSimpeWidth);

        final int hardsquareWidth = (int) (BLOC_WIDTH*0.8);
        final int hardSquareHeight = hardsquareWidth;
        final int hardSquareY = displayHeight - FLOOR_HEIGHT - hardSquareHeight;

        int decorVolcanWidth = (int) ((displayHeight-FLOOR_HEIGHT)*1.179);
        int decorVolcanHeight = displayHeight- FLOOR_HEIGHT;

        drawCastle("castle", 0);
        drawCastle("castle", 521*dx );
        drawLine("Objet", "volcan_decor2", 0, 560*dx, 0, displayHeight, displayHeight, false);
        drawLine("BrownBloc", "darkbrick", 0, CASTLE_WIDTH+1, surface, FLOOR_WIDTH, FLOOR_HEIGHT, false);
        drawPlatformeEphemereLine("tile1", CASTLE_WIDTH - dx, surface, 3*dx, 3*dx, 70, false, false, 7);
        drawBloc("briquestriee", displayWidth/2 - 5*dx, surface, 10*dx, 10*dx, 1);
        drawBloc("briquestriee", displayWidth/2 + 5*dx, surface - 4*dx, 10*dx, 10*dx, 1);
        drawBloc("briquestriee", displayWidth/2 + 15*dx, surface - 8*dx, 10*dx, 10*dx, 1);
        drawBloc("bloc", 40*dx, surface-3*BLOC_HEIGHT-dx, BLOC_WIDTH, BLOC_HEIGHT, 6);
        drawPlatformeEphemereLine("tile1", 75*dx, surface - 8*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPlatformeEphemereLine("tile1", 100*dx, surface - 8*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPlatformeEphemereLine("tile1", 125*dx, surface - 8*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPodoboo("podoboo", 90*dx, surface-15*dx, 3*dx, 3*dx);
        drawPodoboo("podoboo", 93*dx, surface-15*dx, 3*dx, 3*dx);
        drawPodoboo("podoboo", 115*dx, surface-15*dx, 3*dx, 3*dx);
        drawPodoboo("podoboo", 118*dx, surface-15*dx, 3*dx, 3*dx);
        drawLine("BrownBloc", "greybrick3", 140*dx, 150*dx, surface - 8*dx, 5*dx, 5*dx, false);
        drawLine("BrownBloc", "greybrick3", 150*dx, 160*dx, surface - 13*dx, 5*dx, 5*dx, false);
        drawLine("BrownBloc", "greybrick3", 160*dx, 170*dx, surface-18*dx, 5*dx, 5*dx, false);
        drawLine("BrownBloc", "greybrick3", 170*dx, 260*dx, surface - 23*dx, 5*dx, 5*dx, false);
        drawBloc("darkbloc", 213*dx, surface - 27*dx, BLOC_WIDTH, BLOC_HEIGHT, 1);
        drawKoopa("redkoopa", 180*dx, surface - 27*dx, false, true);
        drawKoopa("redkoopa", 250*dx, surface - 27*dx, false, false);
        drawAlternatesBloc("darkbloc", "bloc", 187*dx, 2*dx, 3, 1, 3);
        drawAlternatesBloc("darkbloc", "bloc", 233*dx, 2*dx, 3, 1, 3);
        drawLine("BrownBloc", "greybrick3", 260*dx, 270*dx, surface - 18*dx, 5*dx, 5*dx, true);
        drawLine("BrownBloc", "greybrick3", 270*dx, 300*dx, surface - 13*dx, 5*dx, 5*dx, false);
        drawLine("BrownBloc", "greybrick3", 310*dx, 340*dx,surface - 13*dx, 5*dx, 5*dx, false);
        drawLine("BrownBloc", "greybrick3", 160*dx, 180*dx, displayHeight-5*dx,5*dx, 5*dx, false);
        drawPlatformeEphemereLine("tile1", 183*dx, displayHeight - 5*dx, 3*dx, 3*dx, 70, false, false, 2);
        drawLine("BrownBloc", "greybrick3", 192*dx, 214*dx, displayHeight-5*dx,5*dx, 5*dx, false);
        drawPlatformeEphemereLine("tile1", 215*dx, displayHeight-5*dx, 3*dx, 3*dx, 70, false, false, 2);
        drawLine("BrownBloc", "greybrick3", 224*dx, 244*dx, displayHeight-5*dx,5*dx, 5*dx, false);
        drawPlatformeEphemereLine("tile1", 247*dx, displayHeight-5*dx, 3*dx, 3*dx, 70, false, false, 2);
        drawLine("BrownBloc", "greybrick3", 256*dx, 276*dx, displayHeight-5*dx,5*dx, 5*dx, false);
        drawPlatformeEphemereLine("tile1", 276*dx, displayHeight-5*dx, 3*dx, 3*dx, 70, false, false, 4);
        drawLine("BrownBloc", "greybrick3", 288*dx, 315*dx, displayHeight-5*dx,5*dx, 5*dx, false);
        drawAlternatesBloc("darkbloc", "bloc", 180*dx, surface - 14*dx, 3, 1, 5);
        drawAlternatesBloc("darkbloc", "bloc", 212*dx, surface - 14*dx, 3, 1, 4);
        drawAlternatesBloc("darkbloc", "bloc", 244*dx, surface - 14*dx, 3, 1, 3);
        drawGoomba("goomba", 155*dx, surface - 16*dx, false, true);
        drawPodoboo("podoboo", 180*dx, surface, 3*dx, 3*dx);
        drawPodoboo("podoboo", 189*dx, surface, 3*dx, 3*dx);
        drawPodoboo("podoboo", 212*dx, surface, 3*dx, 3*dx);
        drawPodoboo("podoboo", 221*dx, surface, 3*dx, 3*dx);
        drawPodoboo("podoboo", 244*dx, surface, 3*dx, 3*dx);
        drawPodoboo("podoboo", 253*dx, surface, 3*dx, 3*dx);
        drawGoomba("goomba", 260*dx, surface - 21*dx, false, true);
        drawKoopa("redkoopa", 280*dx, surface - 16*dx, false, true);
        drawKoopa("redkoopa", 320*dx, surface - 16*dx, false, true);
        drawColumn("BrownBloc", "brique_marron",340*dx, surface+dx, 5, 5*dx, 5*dx, true);
        drawLine("BrownBloc", "brique_marron", 345*dx, 450*dx, surface + dx, 5*dx, 5*dx, false);
        drawColumn("BrownBloc", "brique_marron",450*dx, surface+dx, 5, 5*dx, 5*dx, true);
        drawBloc("brique_marron", 445*dx, surface - 4*dx, 5*dx, 5*dx, 1);
        drawPlatformeEphemereLine("tile1", 455*dx, surface - 24*dx, 3*dx, 3*dx, 70, false, false, 2);
        drawPlatformeEphemereLine("tile1", 470*dx, surface - 18*dx, 3*dx, 3*dx, 70, false, false, 2);
        drawPlatformeEphemereLine("tile1", 485*dx, surface - 12*dx, 3*dx, 3*dx, 70, false, false, 2);
        drawPlatformeEphemereLine("tile1", 500*dx, surface - 6*dx, 3*dx, 3*dx, 70, false, false, 2);
        drawPlatformeEphemereLine("tile1", 515*dx, surface, 3*dx, 3*dx, 70, false, false, 2);
        drawMagikoopa(445*dx, surface - 9*dx);
        drawPodoboo("podoboo", 480*dx, surface - 20*dx, 3*dx, 3*dx);
        drawPodoboo("podoboo", 495*dx, surface - 14*dx, 3*dx, 3*dx);
    }
    public void level5() {

        objets = new ArrayList<Objet>();
        persos = new ArrayList<Personnage>();

        this.dx = (int) (displayWidth*0.01);
        final int mysteryBlocY = displayHeight - FLOOR_HEIGHT - CHARACTER_HEIGHT - BLOC_HEIGHT*2;
        final int surface = displayHeight-FLOOR_HEIGHT;
        this.PIECE_WIDTH = (int)(displayWidth*0.03);
        this.PIECE_HEIGHT = (int)(PIECE_WIDTH*1.1354);

        final int nuageSimpeWidth = (int) (displayWidth*0.076);
        final int nuageSimpleHeight = (int) (0.9465*nuageSimpeWidth);

        final int hardsquareWidth = (int) (BLOC_WIDTH*0.8);
        final int hardSquareHeight = hardsquareWidth;
        final int hardSquareY = displayHeight - FLOOR_HEIGHT - hardSquareHeight;

        drawCastle("castle", 0);
        drawCastle("castle", 565*dx, surface - 33*dx);
        drawBloc("forest_platform",0, surface, CASTLE_WIDTH, CASTLE_HEIGHT/3, 1 );
        drawLine("BrownBloc", "brownbloc", CASTLE_WIDTH + 2*BLOC_WIDTH, 76*dx, surface, BLOC_WIDTH, BLOC_HEIGHT, true);
        drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 80*dx, displayHeight-15*dx,5*dx, 20*dx, 20*dx, 5*dx, 3);
        drawStaticPlatform("pillier_champi_platforme", "champi_platforme_jaune", 110*dx, displayHeight-20*dx,5*dx, 21*dx, 20*dx, 5*dx, 2);
        drawGoomba("goomba", 115*dx, displayHeight-30*dx, false, true);
        drawParakoopa("greenparakoopa", 150*dx, surface - 20*dx, true, false);
        drawParakoopa("greenparakoopa", 180*dx, surface - 20*dx, true, true);
        drawPieceLine(135*dx, 10*dx, 20);
        drawPieceLine(135*dx, surface-8*dx, 20);
        drawAlternatesBloc("bloc_jaune", "bloc", 76*dx, displayHeight - 33*dx, 3, 1, 3);
        drawMovablePlatform("nuageplatform2", 130*dx, surface - 10*dx, 10*dx, 3*dx, true, 10*dx, surface);
        drawMovablePlatform("nuageplatform2", 150*dx, surface - 10*dx, 10*dx, 3*dx, false, 10*dx, surface);
        drawMovablePlatform("nuageplatform2", 170*dx, surface - 10*dx, 10*dx, 3*dx, true, 10*dx, surface);
        drawMovablePlatform("nuageplatform2", 190*dx, surface - 10*dx, 10*dx, 3*dx, false, 10*dx, surface);
        drawUnmovablePlatforme("forest_platform",210*dx, surface - 3*dx, CASTLE_WIDTH, CASTLE_HEIGHT/3);
        drawAlternatesBloc("brownbloc", "bloc", 216*dx, surface - 18*dx, 3, 2, 4);
        drawKoopa("greenkoopa", 220*dx, surface - 10*dx, false, true);
        drawStaticPlatform("pillier_champi_platforme", "champi_platforme_jaune", 250*dx, displayHeight-10*dx,3*dx, 10*dx, 15*dx, 4*dx, 2);
        drawStaticPlatform("pillier_champi_platforme", "champi_platforme", 280*dx, displayHeight-10*dx,3*dx, 10*dx, 15*dx, 4*dx, 3);
        drawCanon("canon", 300*dx, surface - 14*dx, 5*dx, 10*dx, false);
        drawColumn("BrownBloc", "brique_marron",300*dx ,displayHeight, 3,5*dx, 5*dx, false );
        drawUnmovablePlatforme("forest_platform",310*dx, surface - 5*dx, CASTLE_WIDTH, CASTLE_HEIGHT/3);
        drawUnmovablePlatforme("forest_platform",322*dx, surface - 20*dx, CASTLE_WIDTH, CASTLE_HEIGHT/3);
        drawUnmovablePlatforme("forest_platform",334*dx, surface - 5*dx, CASTLE_WIDTH, CASTLE_HEIGHT/3);
        drawGoomba("goomba", 320*dx, surface - 8*dx, false, true);
        drawGoomba("goomba", 324*dx, surface - 8*dx, false, true);
        drawGoomba("goomba", 328*dx, surface - 8*dx, false, true);
        drawKoopa("greenkoopa", 320*dx, surface - 25*dx, false, false);
        drawStaticPlatform("pillier_champi_platforme", "champi_platforme_jaune", 382*dx, surface-15*dx,5*dx, 22*dx, 20*dx, 5*dx, 2);
        drawAlternatesBloc("brownbloc", "bloc", 355*dx, surface - 20*dx, 3, 1, 5);
        drawLine("BrownBloc", "brownbloc", 350*dx, 382*dx, surface - 5*dx, BLOC_WIDTH, BLOC_HEIGHT, false);
        drawPlatformeEphemereLine("tile1", 400*dx, surface - 20*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPlatformeEphemereLine("tile1", 418*dx, surface - 20*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPlatformeEphemereLine("tile1", 436*dx, surface - 20*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPlatformeEphemereLine("tile1", 454*dx, surface - 20*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPlatformeEphemereLine("tile1", 409*dx, surface - 6*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPlatformeEphemereLine("tile1", 427*dx, surface - 6*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPlatformeEphemereLine("tile1", 445*dx, surface - 6*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawPlatformeEphemereLine("tile1", 463*dx, surface - 6*dx, 3*dx, 3*dx, 70, false, false, 3);
        drawParakoopa("greenparakoopa", 420*dx, surface - 13*dx, false, false);
        drawParakoopa("greenparakoopa", 440*dx, surface - 13*dx, false, true);
        drawMovablePlatform("nuageplatform2", 475*dx, surface - 10*dx, 10*dx, 3*dx, true, 10*dx, surface);
        drawColumn("BrownBloc", "brique_marron",490*dx ,displayHeight, 6,5*dx, 5*dx, false );
        drawCanon("canon", 490*dx, surface - 27*dx, 5*dx, 10*dx, false);
        drawUnmovablePlatforme("nuageplatform2", 495*dx, surface - 10*dx, 10*dx, 3*dx);
        drawUnmovablePlatforme("nuageplatform2", 505*dx, surface - 10*dx, 10*dx, 3*dx);
        drawUnmovablePlatforme("nuageplatform2", 515*dx, surface - 10*dx, 10*dx, 3*dx);
        drawUnmovablePlatforme("nuageplatform2", 525*dx, surface - 10*dx, 10*dx, 3*dx);
        drawUnmovablePlatforme("nuageplatform2", 545*dx, surface - 10*dx, 10*dx, 3*dx);
        drawUnmovablePlatforme("nuageplatform2", 555*dx, surface - 10*dx, 10*dx, 3*dx);
        drawUnmovablePlatforme("nuageplatform2", 565*dx, surface - 10*dx, 10*dx, 3*dx);
        drawUnmovablePlatforme("nuageplatform2", 575*dx, surface - 10*dx, 10*dx, 3*dx);
        drawUnmovablePlatforme("nuageplatform2", 585*dx, surface - 10*dx, 10*dx, 3*dx);

        drawPlatformeEphemereLine("tile1", 495*dx, displayHeight-3*dx, 3*dx, 3*dx, 70, false, false, 22);
        drawColumn("BrownBloc", "brownbloc", 561*dx,displayHeight - BLOC_HEIGHT, 3, BLOC_WIDTH, BLOC_HEIGHT, false );
        drawPieceLine(495*dx, surface - 4*dx, 23);
        drawGoomba("goomba", 500*dx, surface, true, true);
        drawGoomba("goomba", 500*dx, surface - 13*dx, false, true);

    }
    /**
     * DrawCastle dessine un château
     * @param key
     * @param x
     */
    public void drawCastle(String key, int x){
        int castleY = displayHeight - FLOOR_HEIGHT - CASTLE_HEIGHT;
        Castle castle = new Castle(this, key, x, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);
        castles.add(castle);
    }
    public void drawCastle(String key, int x, int y){
        Castle castle = new Castle(this, key, x, y, CASTLE_WIDTH, CASTLE_HEIGHT);
        castles.add(castle);
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
            BrownBloc bb = new BrownBloc(this, key, i*FLOOR_WIDTH, floorY, FLOOR_WIDTH, FLOOR_HEIGHT);
            brownBlocs.add(bb);
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
     */
    public void drawPieceLine(String name, int initX, int endX, int y, int width, int height){
        for(int i = initX; i<endX; i+=width){
            Piece piece = new Piece(this, name, i, y, width, height);
            pieces.add(piece);
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
                    brownBlocs.add(bb);
                }
                break;
            case "YellowBloc" :
                for(int i = 0; i<rate; i++){
                    YellowBloc yb = new YellowBloc(this, name, initX + i*width, y, width, height);
                    yellowBlocs.add(yb);
                }
                break;
            case "Piece" :
                for(int i = 0; i<rate; i++){
                    Piece p = new Piece(this, name, initX + i*width, y, width, height);
                    pieces.add(p);
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
                pieces.add(p);
            }
        }
    }
    public void drawDecorationLine(String key, int x, int y, int width, int height, int quantite, int equart){
        for(int i = 0; i<quantite; i++){
            drawDecoration(key, x + i*equart, y, width, height);
        }
    }
    public void drawColumn(String objetType, String name, int x, int initY, int quantite, int width, int height, boolean pieceOnTop){
        switch (objetType){
            case "BrownBloc" :
                for(int i = 0; i<quantite; i++){
                    BrownBloc bb = new BrownBloc(this, name, x, initY-i*height, width, height);
                    brownBlocs.add(bb);
                }
                break;
            case "YellowBloc" :
                for(int i = 0; i<quantite; i++){
                    YellowBloc yb = new YellowBloc(this, name, x, initY - i*height, width, height);
                    yellowBlocs.add(yb);
                }
                break;
            case "Piece" :
                for(int i = 0; i<quantite; i++){
                    Piece p = new Piece(this, name, x, initY - i*height, width, height);
                    pieces.add(p);
                }
            case "Objet" :
                for(int i = 0; i<quantite; i++){
                    Objet objet = new Objet(this, name, x, initY - i*height, width, height);
                    objets.add(objet);
                }
        }
        if(pieceOnTop){
            int pieceX = x + (width-PIECE_WIDTH)/2;
            int pieceY = initY - quantite*(height-1) - dx;
            Piece piece = new Piece(this, "piece_jaune", pieceX, pieceY, PIECE_WIDTH, PIECE_HEIGHT);
            pieces.add(piece);
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
            pieces.add(p);
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
                brownBlocs.add(bloc1);
                return;
            case 2 :
                BrownBloc bloc2 = new BrownBloc(this, name, x, y, width, height);
                Piece piece2 = new Piece(this, "piece_jaune", x + (width-PIECE_WIDTH)/2, y-PIECE_HEIGHT-dx, PIECE_WIDTH, PIECE_HEIGHT);
                piece2.setActivated(true);
                piece2.setIsPickabe(true);
                brownBlocs.add(bloc2);
                pieces.add(piece2);
                return;
            case 3 :
                YellowBloc yb3 = new YellowBloc(this, name, x, y, width, height);
                Piece p = new Piece(this, "piece_jaune", x+(width-PIECE_WIDTH)/2, y-PIECE_HEIGHT - dx, PIECE_WIDTH, PIECE_HEIGHT);
                p.setActivated(false);
                yb3.setPiece(p);
                yellowBlocs.add(yb3);
                pieces.add(p);
                return;
            case 4 :
                YellowBloc yb4 = new YellowBloc(this, name, x, y, width, height);
                int itemWidth = (int) (width * 2/3);
                int itemHeight = itemWidth;
                Champignon item4 = new Champignon(this, "champignon", x+(width-itemWidth)/2, y+(height-itemHeight)/2, itemWidth, itemHeight);
                yb4.setChampi(item4);
                yellowBlocs.add(yb4);
                champis.add(item4);
                return;
            case 5 :
                YellowBloc yb5 = new YellowBloc(this, name, x, y, width, height);
                int itWidth = (int) (width * 2/3);
                int itHeight = itWidth;
                Etoile item5 = new Etoile(this, "etoile", x+(width-itWidth)/2, y+(height-itHeight)/2, itWidth, itHeight);
                item5.setActivated(false);
                yb5.setEtoile(item5);
                yellowBlocs.add(yb5);
                etoiles.add(item5);
                return;
            case 6 :
                YellowBloc yb6 = new YellowBloc(this, name, x, y, width, height);
                int fleurWidth = (int) (width * 2/3);
                int fleurHeight = fleurWidth;
                FleurFeu fleur = new FleurFeu(this, "fleurfeu", x+(width-fleurWidth)/2, y+(height-fleurHeight)/2, fleurWidth, fleurHeight);
                fleur.setActivated(false);
                yb6.setFleurFeu(fleur);
                yellowBlocs.add(yb6);
                fleursfeu.add(fleur);
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
    public void drawStaticPlatform(String footName, String headName, int x, int y, int footWidth, int footHeight, int headWidth, int headHeight, int code){
        int footY = y;
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
        piece.setActivated(true);
        piece.setIsPickabe(true);
        pieces.add(piece);
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
    public void drawGoomba(String key, int x, int y, boolean gravityFall, boolean isRight){
        Goomba goomba = new Goomba(this, key, x, y, dx*3, dx*3);
        goomba.setGravityFall(gravityFall);
        goomba.setRight(isRight);
        persos.add(goomba);
        ennemies.add(goomba);
    }
    public void drawKoopa(String key, int x, int y, boolean gravityFall, boolean isRight){
        final int KOOPA_WIDTH = (int) (displayWidth*0.03);
        final int KOOPA_HEIGHT = (int) (KOOPA_WIDTH*1.7458);
        Koopa koopa = new Koopa(this, key, x, y, KOOPA_WIDTH, KOOPA_HEIGHT);
        koopa.setGravityFall(gravityFall);
        koopa.setRight(isRight);
        persos.add(koopa);
        ennemies.add(koopa);
    }
    public void drawParakoopa(String key, int x, int y, boolean gravityFall, boolean isRight){
        final int PARAKOOPA_WIDTH = (int) (displayWidth*0.03) + dx;
        final int PARAKOOPA_HEIGHT = (int) (PARAKOOPA_WIDTH*1.7458);
        Parakoopa pk = new Parakoopa(this, key, x, y, PARAKOOPA_WIDTH, PARAKOOPA_HEIGHT);
        pk.setRight(isRight);
        pk.setGravityFall(gravityFall);
        persos.add(pk);
        ennemies.add(pk);
    }
    public void drawBoo(int x, int y){

        Boo boo2 = new Boo(this, "boo", x, y, 3*dx, 3*dx);
        persos.add(boo2);
        ennemies.add(boo2);

    }
    public void drawBoo(int x, int y, String camouflage){
        Boo boo = new Boo(this, "boo", x, y, 3*dx, 3*dx);
        boo.changeCamouflage(camouflage);
        persos.add(boo);
        ennemies.add(boo);
    }
    public void drawSkelerex(int x, int y, boolean gravityFall, boolean isRight){
        final int KOOPA_WIDTH = (int) (displayWidth*0.03);
        final int KOOPA_HEIGHT = (int) (KOOPA_WIDTH*1.7458);
        Skelerex sk = new Skelerex(this, "skelerex", x, y, KOOPA_WIDTH, KOOPA_HEIGHT);
        sk.setGravityFall(gravityFall);
        sk.setRight(isRight);
        persos.add(sk);
        ennemies.add(sk);
    }
    public void drawThwomp(int x, int y){
        int thwompWidth = 8*dx;
        int thwompHeight = (int) (thwompWidth*1.2513);
        Thwomp thwomp = new Thwomp(this, "thwomp", x, y, thwompWidth, thwompHeight);
        persos.add(thwomp);
        ennemies.add(thwomp);
    }
    public void drawSpiny(String key, int x, int y, int width, int height, boolean gravityFall, boolean isRight){
        Spiny spin = new Spiny(this, key, x, y, width, height);
        spin.setGravityFall(gravityFall);
        spin.setRight(isRight);
        persos.add(spin);
        ennemies.add(spin);
    }
    public void drawPlantePirhana(String key, int x, int y){
        int width = 5*dx;
        int height = (int) (5*dx*1.488);
        PlantePirhana pp = new PlantePirhana(this, key, x, y, width, height);
        persos.add(pp);
        ennemies.add(pp);
    }
    public void drawPlantPirhanaWithGreenPipe(int x, int y){

        int plantWidth = (int) (4.5*dx);
        int plantHeight = (int) (5*dx*1.488);
        int plantX = x + Math.abs(PIECE_WIDTH - plantWidth)/2;
        int plantY = y;

        Pipe pipe = new Pipe(this, "longreenpipe", x, y, PIPE_WIDTH, PIPE_HEIGHT+FLOOR_HEIGHT);
        PlantePirhana pp = new PlantePirhana(this, " ", plantX, plantY + dx, plantWidth, plantHeight);

        persos.add(pp);
        ennemies.add(pp);
        pipes.add(pipe);
    }
    public void drawUnmovablePlatforme(String key, int x, int y, int width, int height){
        Platforme platforme = new Platforme(this, key, x, y, width, height);
        platforme.setMovable(false);
        platformes.add(platforme);
    }
    public void drawUnmovablePlaftformLine(String key, int x, int y, int width, int height, int quantite) {
        for (int i = 0; i<quantite; i++) {
            drawUnmovablePlatforme(key, x + i*width, y, width, height);
        }
    }
    public void drawMovablePlatform(String key, int x, int y, int width, int height, boolean up, int upperLimit, int lowerLimit){
        Platforme platforme = new Platforme(this, key, x, y, width, height);
        platforme.setMovable(true);
        platforme.setUp(up);
        platforme.setUpperLimit(upperLimit);
        platforme.setLowerLimit(lowerLimit);
        platformes.add(platforme);
    }
    public void drawPodoboo(String name, int x, int y, int width, int height)
    {
        Podoboo pdb = new Podoboo(this, name, x, y, width, height);
        ennemies.add(pdb);
    }
    public void drawPlatformeEphemere(String key, int x, int y, int width, int height, int vie, boolean movable, boolean up) {
        PlateformeEphemere pe = new PlateformeEphemere(this, key, x, y, width, height);
        pe.setMovable(movable);
        pe.setUp(up);
        pe.setVie(vie);
        platformes.add(pe);
    }
    public void drawPlatformeEphemereLine(String key, int x, int y, int width, int height, int vie, boolean movable, boolean up, int quantite) {
        for (int i = 0; i<quantite; i++) {
            drawPlatformeEphemere(key, x + i*width, y, width, height, vie, movable, up);
        }
    }
    public void drawCanon (String key, int x, int y, int width, int height, boolean right) {
        Canon canon = new Canon(this, key, x, y, width, height);
        canon.setRight(right);
        brownBlocs.add(canon);
    }
    public void drawMagikoopa(int x, int y) {
        int magWidth = 5*dx;
        int magHeight = (int) (magWidth*1.2);
        Magikoopa mag = new Magikoopa(this, "magikoopa", x, y, magWidth, magHeight);
        ennemies.add(mag);
    }
    public void drawCoconut(String name, int x, int y, int width, int height) {
        Coconut cc = new Coconut(this, name, x, y, width, height);
        ennemies.add(cc);
    }
    public void drawPipe(String key, int x, int y) {
        Pipe pipe = new Pipe(this, key, x, y, PIPE_WIDTH, PIPE_HEIGHT);
        pipes.add(pipe);
    }
    public void drawPieceColumn(int x, int y, int quantite){
        for (int i = 0; i<quantite; i++) {
            Piece piece = new Piece(this, "piece_jaune", x, y-i*PIECE_HEIGHT-dx, PIECE_WIDTH, PIECE_HEIGHT);
            pieces.add(piece);
        }
    }
    public void drawPokey(String key, int x, int y){
        int pokeyWidth = 4*dx;
        int pokeyHeight = 16*dx;
        Pokey pok = new Pokey(this, key, x, y, pokeyWidth, pokeyHeight);
        ennemies.add(pok);
    }
}
