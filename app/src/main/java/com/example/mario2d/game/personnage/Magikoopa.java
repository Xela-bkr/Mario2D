package com.example.mario2d.game.personnage;

import static com.example.mario2d.game.loop.GameActivity.displayHeight;
import static com.example.mario2d.game.loop.GameActivity.displayWidth;
import static com.example.mario2d.game.loop.GameActivity.dx;
import static com.example.mario2d.game.loop.GameActivity.player;
import static com.example.mario2d.game.loop.GameActivity.waitingLine;
import static com.example.mario2d.game.loop.GameActivity.waitingLineBrownBlocs;
import static com.example.mario2d.game.loop.GameActivity.waitingLineBrownBlocsForRemoving;
import static com.example.mario2d.game.loop.GameActivity.waitingLineForRemoving;
import static com.example.mario2d.game.loop.GameView.theme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mario2d.R;
import com.example.mario2d.game.loop.GameActivity;
import com.example.mario2d.game.objet.BrownBloc;
import com.example.mario2d.tool.Audio;

import java.util.ArrayList;

public class Magikoopa extends Ennemy{

    private ArrayList<BrownBloc> brownBlocs = new ArrayList<BrownBloc>();
    private int spellCompteur, waiting;
    private boolean spellCasted;
    private Bitmap wait_gauche, wait_droite, prepare_gauche_1, prepare_gauche_2, prepare_gauche_3,
            prepare_droite_1, prepare_droite_2, prepare_droite_3, lance_gauche, lance_droite, mort_gauche, mort_droite;
    public Magikoopa(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        spellCompteur = 0;
        waiting = 0;
        deadCompteur = 0;
        activated = false;
        gravityFall = false;
        isResting = false;
        this.life = 3;
        setBitmaps();
    }
    @Override
    public void setBitmaps() {
        Bitmap b1 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_wait_gauche"));
        Bitmap b2 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_wait_droite"));
        Bitmap b3 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_prepare_gauche_1"));
        Bitmap b4 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_prepare_gauche_2"));
        Bitmap b5 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_prepare_gauche_3"));
        Bitmap b6 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_prepare_droite_1"));
        Bitmap b7 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_prepare_droite_2"));
        Bitmap b8 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_prepare_droite_3"));
        Bitmap b9 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_lance_gauche"));
        Bitmap b10 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_lance_droite"));
        Bitmap b11 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort_gauche"));
        Bitmap b12 = BitmapFactory.decodeResource(context.getResources(), spriteBank.get(name+"_mort_droite"));

        wait_gauche = Bitmap.createScaledBitmap(b1, getWidth(), getHeight(), true);
        wait_droite = Bitmap.createScaledBitmap(b2, getWidth(), getHeight(), true);
        prepare_gauche_1 = Bitmap.createScaledBitmap(b3, getWidth(), getHeight(), true);
        prepare_gauche_2 = Bitmap.createScaledBitmap(b4, getWidth(), getHeight(), true);
        prepare_gauche_3 = Bitmap.createScaledBitmap(b5, getWidth(), getHeight(), true);
        prepare_droite_1 = Bitmap.createScaledBitmap(b6, getWidth(), getHeight(), true);
        prepare_droite_2 = Bitmap.createScaledBitmap(b7, getWidth(), getHeight(), true);
        prepare_droite_3 = Bitmap.createScaledBitmap(b8, getWidth(), getHeight(), true);
        lance_gauche = Bitmap.createScaledBitmap(b9, getWidth(), getHeight(), true);
        lance_droite = Bitmap.createScaledBitmap(b10, getWidth(), getHeight(), true);
        mort_gauche = Bitmap.createScaledBitmap(b11, getWidth(), getHeight(), true);
        mort_droite = Bitmap.createScaledBitmap(b12, getWidth(), getHeight(), true);
        this.bitmap = wait_gauche;
    }
    @Override
    public void update() {
        if (activated) {
            if(!isAlive) {
                dead();
                return;
            }
            if(brownBlocs.size() == 0) {
                setArena();
            }
             if(isResting) {
                rest();
            } else if(life <= 0) {
                dead();
            } else {
                if (player.getX() < getX()) {
                    isRight = false;
                    if (!collisionWithObject(1)) {
                        translateX(-3);
                    }
                } else if (player.getX() > getX() + getWidth()) {
                    isRight = true;
                    if (!collisionWithObject(3)) {
                        translateX(3);
                    }
                }
                if(waiting <= 150) {
                    updateImage();
                } else {
                    castSpell();
                    if(waiting > 240) {
                        waiting = 0;
                    }
                }
                if(!isResting) updateCollisions();
                waiting ++;
            }
        } else if (getX() < displayWidth*2/3) {
            activated = true;
        }
    }
    public void castSpell() {
        if(spellCompteur < 15) {
            if(isRight) this.bitmap = prepare_droite_1;
            else this.bitmap = prepare_gauche_1;
        } else if (spellCompteur < 30) {
            if(isRight) this.bitmap = prepare_droite_2;
            else this.bitmap = prepare_gauche_2;
        } else if (spellCompteur < 60) {
            if(!spellCasted) {
                dropMagiboule();
                spellCasted = true;
            }
            if(isRight) this.bitmap = prepare_droite_3;
            else this.bitmap = prepare_gauche_3;
        } else if (spellCompteur < 90) {
            if (isRight) this.bitmap = lance_droite;
            else this.bitmap = lance_gauche;
        } else {
            spellCasted = false;
            spellCompteur = 0;
        }
        spellCompteur ++;
    }
    public void dropMagiboule(){
        int magWidth = getWidth()/2;
        int magHeight = magWidth;
        int magX = getX() + (getWidth() - magWidth)/2;
        int magY = getY() - magHeight;
        if(life <= 1) {
            Carapace carapace = new Carapace(context, "carapace", magX, magY, magWidth, magHeight);
            waitingLine.add(carapace);
        } else {
            Magiboule mb = new Magiboule(context, "magiboule", magX, magY, magWidth, magHeight);
            waitingLine.add(mb);
        }
    }
    public void updateImage() {
        if(isRight) {
            this.bitmap = wait_droite;
        } else {
            this.bitmap = wait_gauche;
        }
    }
    public void rest() {
        isResting = true;
        if(restCompteur < 50) {
            if(restCompteur%3 == 0) {
                this.bitmap = null;
            } else {
                if(isRight) {this.bitmap = wait_droite;}
                else {this.bitmap = wait_gauche;}
            }
        } else {
            restCompteur = 0;
            isResting = false;
        }
        restCompteur ++;
    }
    public void dead() {
        isAlive = false;
        if(deadCompteur == 0) {
            Audio.playSound(context, R.raw.magikoopa_dead);
        }
        if(deadCompteur < 50) {
            if(isRight) {
                this.bitmap = mort_droite;
            } else {
                this.bitmap = mort_gauche;
            }
        } else {
            removeArena();
            setActivated(false);
            setAlive(false);
            waitingLineForRemoving.add(this);
        }
        deadCompteur ++;
    }
    public void updateCollisions() {
        boolean[] tab = player.detectCollision(this, 0, (int) (getWidth()*0.1), 0, (int) (getWidth()*0.1));
        if (tab[0]) {
            if(!isResting) {
                System.out.printf("magikoopa lost a life %d\n", life);
                Audio.playSound(context, R.raw.kick_2);
                this.decreaseLife();
                System.out.printf("after decreaselife func %d\n", life);
            }
            player.jump2();
        } else if (tab[1] || tab[2] || tab[3]) {
            if (!player.getInvincible()) {
                if(!player.getResting()) {
                    player.decreaseLife();
                }
            } else {
                Audio.playSound(context, R.raw.kick_2);
                System.out.println("magikoopa dead");
                dead();
            }
        }
    }
    @Override
    public void decreaseLife() {
        life --;
        isResting = true;
    }
    private void setArena() {
        theme = new Audio(context, R.raw.magikoopa_theme);
        theme.setLoop(true);
        for (int i = 0; i<10; i++) {
            BrownBloc bb = new BrownBloc(context, "hardbloc", 0, getY() + getHeight() - i*5*dx, 5*dx, 5*dx);
            BrownBloc bb2 = new BrownBloc(context, "hardbloc", displayWidth-10*dx, getY() + getHeight() - i*5*dx, 5*dx, 5*dx);
            brownBlocs.add(bb);
            brownBlocs.add(bb2);
            waitingLineBrownBlocs.add(bb);
            waitingLineBrownBlocs.add(bb2);
        }
        theme.play();
    }
    private void removeArena () {
        for(BrownBloc bb : brownBlocs) {
            waitingLineBrownBlocsForRemoving.add(bb);
        }
        theme.stop();
    }
}
