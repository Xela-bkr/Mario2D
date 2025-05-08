package com.example.mario2d.game.loop;

import static com.example.mario2d.game.loop.GameActivity.BLOC_HEIGHT;
import static com.example.mario2d.game.loop.GameActivity.BLOC_WIDTH;
import static com.example.mario2d.game.loop.GameActivity.CASTLE_HEIGHT;
import static com.example.mario2d.game.loop.GameActivity.CASTLE_WIDTH;
import static com.example.mario2d.game.loop.GameActivity.FLOOR_HEIGHT;
import static com.example.mario2d.game.loop.GameActivity.FLOOR_WIDTH;
import static com.example.mario2d.game.loop.GameActivity.LEVEL_SELECTED;
import static com.example.mario2d.game.loop.GameActivity.PIECE_HEIGHT;
import static com.example.mario2d.game.loop.GameActivity.PIECE_WIDTH;
import static com.example.mario2d.game.loop.GameActivity.PIPE_HEIGHT;
import static com.example.mario2d.game.loop.GameActivity.PIPE_WIDTH;
import static com.example.mario2d.game.loop.GameActivity.brownBlocs;
import static com.example.mario2d.game.loop.GameActivity.castles;
import static com.example.mario2d.game.loop.GameActivity.champis;
import static com.example.mario2d.game.loop.GameActivity.displayHeight;
import static com.example.mario2d.game.loop.GameActivity.displayWidth;
import static com.example.mario2d.game.loop.GameActivity.dx;
import static com.example.mario2d.game.loop.GameActivity.ennemies;
import static com.example.mario2d.game.loop.GameActivity.etoiles;
import static com.example.mario2d.game.loop.GameActivity.fleursfeu;
import static com.example.mario2d.game.loop.GameActivity.objets;
import static com.example.mario2d.game.loop.GameActivity.persos;
import static com.example.mario2d.game.loop.GameActivity.pieces;
import static com.example.mario2d.game.loop.GameActivity.pipes;
import static com.example.mario2d.game.loop.GameActivity.platformes;
import static com.example.mario2d.game.loop.GameActivity.waitingLine;
import static com.example.mario2d.game.loop.GameActivity.waitingLineBrownBlocs;
import static com.example.mario2d.game.loop.GameActivity.waitingLineBrownBlocsForRemoving;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;
import static com.example.mario2d.game.loop.GameActivity.yellowBlocs;

import android.os.Build;
import android.os.Bundle;

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
import com.example.mario2d.game.personnage.Goomba;
import com.example.mario2d.game.personnage.Koopa;
import com.example.mario2d.game.personnage.Magikoopa;
import com.example.mario2d.game.personnage.Parakoopa;
import com.example.mario2d.game.personnage.Personnage;
import com.example.mario2d.game.personnage.PlantePirhana;
import com.example.mario2d.game.personnage.Podoboo;
import com.example.mario2d.game.personnage.Skelerex;
import com.example.mario2d.game.personnage.Spiny;
import com.example.mario2d.game.personnage.Thwomp;

import java.util.ArrayList;

public class GameActivity2 extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setLevels();
        setContentView(new GameView(this, displayWidth, displayHeight, false, 1));
    }
    private void setLevels() {

        final int surface = displayHeight-FLOOR_HEIGHT;

        switch (LEVEL_SELECTED) {
            case 1 :
                Castle castle = new Castle(this, "hardbloc", 0,surface-5*dx, 5*dx, 5*dx);
                castles.add(castle);
                Castle castle2 = new Castle(this, "hardbloc", 200*dx,surface-5*dx, 5*dx, 5*dx);
                castles.add(castle2);
                drawLine("BrownBloc", "hardbloc", 0, 200*dx, surface, 5*dx, 5*dx, false);
                return;
            case 2 :

        }
    }
    public void drawCastle(String key, int x, String...floorKey){
        int castleY = displayHeight - FLOOR_HEIGHT - CASTLE_HEIGHT;
        Castle castle = new Castle(this, key, x, castleY, CASTLE_WIDTH, CASTLE_HEIGHT);
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
}
