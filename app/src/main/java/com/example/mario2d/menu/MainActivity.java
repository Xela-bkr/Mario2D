package com.example.mario2d.menu;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mario2d.R;
import com.example.mario2d.game.loop.GameActivity;

import java.util.ArrayList;

/**
 * Actvité prinipale constituant le menu du jeu
 */
public class MainActivity extends AppCompatActivity {

    //****VARIABLES****//
    /**
     * Constantes pour définir le prsonnage sélectionné par le joueur.
     */
    public static int MARIO_SELECTED = 1;
    public static int LUIGI_SELECTED = 2;

    /**
     * Layout pour le sous-menu dédié aux paramètres
     */
    public LinearLayout settingLayout;
    public int currentSelectedCharacter;
    public int currentLevelSelected;
    /**
     * Instances des niveaux à afficher.
     */
    public AbstractFragment level1 = AbstractFragment.newInstance(R.drawable.redbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
    public AbstractFragment level2 = AbstractFragment.newInstance(R.drawable.greenbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.dark_gray);
    public AbstractFragment level3 = AbstractFragment.newInstance(R.drawable.yellowbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
    public AbstractFragment level4 = AbstractFragment.newInstance(R.drawable.darkbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.volcan200);
    public AbstractFragment level5 = AbstractFragment.newInstance(R.drawable.nuageplatform, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);

    /**
     * Paramètres de l'interface "setting"
     */
    public Boolean leftMode, soundEffect, music;

    //****METHODES****//

    /**
     * onCreate. Fonction dans laquelle tous les composants du menu sont instaciés et paramétrés.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingLayout = findViewById(R.id.settings_layout);
        closeSettings();

        currentSelectedCharacter = MARIO_SELECTED;
        currentLevelSelected = 1;

        changetitle(currentLevelSelected);

        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;

        FragmentManager fragmentManager = getSupportFragmentManager();

        initFirstFragment(fragmentManager);
        setDefaultValues();

        ImageButton fleche_droite = findViewById(R.id.fleche_droite);
        ImageButton fleche_gauche = findViewById(R.id.fleche_gauche);

        fleche_droite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLevelSelected ++;
                moveFragment(fragmentManager);
            }
        });
        fleche_gauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLevelSelected --;
                moveFragment(fragmentManager);
            }
        });

        ImageView paramButton = findViewById(R.id.parametres_menu);
        paramButton.setImageResource(R.drawable.settings);
        setViewDimension(300, 250, paramButton);
        paramButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        paramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetting();
            }
        });

        ImageView closeSettingButton = findViewById(R.id.close_setting_button);
        closeSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSettings();
            }
        });

        //LinearLayout.LayoutParams settingLayoutParams = new LinearLayout.LayoutParams(settingLayoutWidth, settingLayoutHeight);
        //settingLayout.setLayoutParams(settingLayoutParams);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch joystick = findViewById(R.id.joystick_switch_setting);
        joystick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {setLeftHandMode(isChecked);}
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch soundSwitch = findViewById(R.id.soundeffect_switch_setting);
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { setSoundEffect(isChecked);}
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch musicSwitch = findViewById(R.id.musique_switch_setting);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {setMusic(isChecked);}
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch personnageSwitch = findViewById(R.id.personnage_switch_setting);
        personnageSwitch.setChecked(false);
        personnageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final int CHARACTER_WIDTH = (int)(0.05*displayWidth);
                final int CHARACTER_HEIGHT = (int)(1.86f*CHARACTER_WIDTH);
                if(isChecked){
                    setCurrentCharacter(LUIGI_SELECTED);
                    moveFragment(fragmentManager);
                }
                else{
                    setCurrentCharacter(MARIO_SELECTED);
                    moveFragment(fragmentManager);
                }
            }
        });

        ImageButton restore = findViewById(R.id.restore_default_settings_button);
        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joystick.setChecked(false);
                soundSwitch.setChecked(false);
                musicSwitch.setChecked(false);
                personnageSwitch.setChecked(false);
            }
        });
        joystick.setChecked(false);
        soundSwitch.setChecked(false);
        musicSwitch.setChecked(false);
        personnageSwitch.setChecked(false);

        Button play = findViewById(R.id.play_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("displayWidth", level1.displayWidth);
                intent.putExtra("displayHeight", level1.displayHeight);
                intent.putExtra("characterWidth", level1.CHARACTER_WIDTH);
                intent.putExtra("characterHeight", level1.CHARACTER_HEIGHT);
                intent.putExtra("floorWidth", level1.FLOOR_WIDTH);
                intent.putExtra("floorHeight", level1.FLOOR_HEIGHT);
                intent.putExtra("floorRate", level1.FLOOR_RATE);
                intent.putExtra("castleWidth", level1.CASTLE_WIDTH);
                intent.putExtra("castleHeight", level1.CASTLE_HEIGHT);
                intent.putExtra("blocWidth", level1.BLOC_WIDTH);
                intent.putExtra("blocHeight", level1.BLOC_HEIGHT);
                intent.putExtra("pipeWidth", level1.PIPE_WIDTH);
                intent.putExtra("pipeHeight", level1.PIPE_HEIGHT);
                intent.putExtra("levelSelected", currentLevelSelected);
                intent.putExtra("leftHandMode", leftMode);
                intent.putExtra("soundEffect", soundEffect);
                intent.putExtra("music", music);
                intent.putExtra("selectedCharacter", currentSelectedCharacter);
                // TODO passer l'historique du joueur si implémentation d'historique
                startActivity(intent);
                finish();
            }
        });

    }
    //****GETTERS****//
    public int getCurrentLevelSelected(){return this.currentLevelSelected;}
    public int getCurrentSelectedCharacter(){return this.currentSelectedCharacter;}

    //****SETTERS****//
    public void setCurrentCharacter(int c) {this.currentSelectedCharacter = c;}
    public void setLeftHandMode(Boolean b){this.leftMode = b;}
    public void setCurrentLevelSelected(int i){this.currentLevelSelected=i;}

    /**
     * Restaure les paramètres par défaut.
     */
    public void setDefaultValues(){
        setCurrentCharacter(1);
        setCurrentLevelSelected(1);
        setLeftHandMode(false);
    }
    public void setSoundEffect(boolean b){this.soundEffect=b;}
    public void setMusic(boolean b){this.music=b;}
    //**** AUTRES METHODES ****//

    /**
     * @param fragManager
     * Intialise le premier fragment à afficher du FragmentManager
     */
    public void initFirstFragment(FragmentManager fragManager){
        fragManager.beginTransaction()
                .replace(R.id.mainmenu_fragment, level1)
                .setReorderingAllowed(true).commit();
    }

    /**
     * @param fm
     * Fonction permettant de changer de niveau en fonction de l'attribut "currentLevelSelected"
     * Utilise le FragmentManager de l'interfece.
     */
    public void moveFragment(FragmentManager fm){
        switch(currentLevelSelected){
            case 0 :
                currentLevelSelected=5;
                changetitle(currentLevelSelected);
                if(currentSelectedCharacter == MARIO_SELECTED){
                    level5 = AbstractFragment.newInstance(R.drawable.nuageplatform, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                else{
                    level5 = AbstractFragment.newInstance(R.drawable.nuageplatform, R.drawable.castle, R.drawable.luigi_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                fm.beginTransaction()
                        .replace(R.id.mainmenu_fragment, level5)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            case 1 :
                changetitle(currentLevelSelected);
                if(currentSelectedCharacter == MARIO_SELECTED){
                    level1 = AbstractFragment.newInstance(R.drawable.redbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                else{
                    level1 = AbstractFragment.newInstance(R.drawable.redbrick, R.drawable.castle, R.drawable.luigi_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                fm.beginTransaction()
                        .replace(R.id.mainmenu_fragment, level1)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2 :
                changetitle(currentLevelSelected);
                if(currentSelectedCharacter==MARIO_SELECTED){
                    level2 = AbstractFragment.newInstance(R.drawable.greenbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.dark_gray);
                }
                else{
                    level2 = AbstractFragment.newInstance(R.drawable.greenbrick, R.drawable.castle, R.drawable.luigi_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.dark_gray);
                }
                fm.beginTransaction()
                        .replace(R.id.mainmenu_fragment, level2)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            case 3 :
                changetitle(currentLevelSelected);
                if(currentSelectedCharacter == MARIO_SELECTED){
                    level3 = AbstractFragment.newInstance(R.drawable.yellowbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                else{
                    level3 = AbstractFragment.newInstance(R.drawable.yellowbrick, R.drawable.castle, R.drawable.luigi_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                fm.beginTransaction()
                        .replace(R.id.mainmenu_fragment, level3)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            case 4 :
                changetitle(currentLevelSelected);
                if(currentSelectedCharacter == MARIO_SELECTED){
                    level4 = AbstractFragment.newInstance(R.drawable.darkbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.volcan200);
                }
                else{
                    level4 = AbstractFragment.newInstance(R.drawable.darkbrick, R.drawable.castle, R.drawable.luigi_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.volcan200);
                }
                fm.beginTransaction()
                        .replace(R.id.mainmenu_fragment, level4)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            case 5 :
                changetitle(currentLevelSelected);
                if(currentSelectedCharacter == MARIO_SELECTED){
                    level5 = AbstractFragment.newInstance(R.drawable.nuageplatform, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                else{
                    level5 = AbstractFragment.newInstance(R.drawable.nuageplatform, R.drawable.castle, R.drawable.luigi_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                fm.beginTransaction()
                        .replace(R.id.mainmenu_fragment, level5)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
            default :
                currentLevelSelected = 1;
                changetitle(currentLevelSelected);
                if(currentSelectedCharacter == MARIO_SELECTED){
                    level1 = AbstractFragment.newInstance(R.drawable.redbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                else{
                    level1 = AbstractFragment.newInstance(R.drawable.redbrick, R.drawable.castle, R.drawable.luigi_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
                }
                fm.beginTransaction()
                        .replace(R.id.mainmenu_fragment, level1)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    //****METHODES****//
    public void setViewDimension(int width, int height, View view){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                width, height);
        view.setLayoutParams(params);
    }
    public void openSetting(){
        this.settingLayout.setActivated(true);
        this.settingLayout.setVisibility(View.VISIBLE);
    }
    public void closeSettings(){
        this.settingLayout.setActivated(false);
        this.settingLayout.setVisibility(View.GONE);
    }

    /**
     * Permet de changer dynamiquement le nom du monde affiché à l'écran
     * @param level
     */
    public void changetitle(int level){
        TextView tv = findViewById(R.id.world);
        switch(level){
            case 1 :
                tv.setText("World : Prairie");
                break;
            case 2 :
                tv.setText("World = Ghostland");
                break;
            case 3 :
                tv.setText("World : Egypt");
                break;
            case 4 :
                tv.setText("World : Volcano");
                break;
            case 5 :
                tv.setText("World : Sky");
                break;
        }
    }
}