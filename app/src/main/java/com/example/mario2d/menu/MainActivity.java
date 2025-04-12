package com.example.mario2d.menu;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //****VARIABLES****//
    public static int MARIO_SELECTED = 1;
    public static int LUIGI_SELECTED = 2;
    public LinearLayout settingLayout;
    public int currentSelectedCharacter;
    public int currentLevelSelected;
    public AbstractFragment level1 = AbstractFragment.newInstance(R.drawable.redbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
    public AbstractFragment level2 = AbstractFragment.newInstance(R.drawable.greenbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.dark_gray);
    public AbstractFragment level3 = AbstractFragment.newInstance(R.drawable.yellowbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);
    public AbstractFragment level4 = AbstractFragment.newInstance(R.drawable.darkbrick, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.volcan200);
    public AbstractFragment level5 = AbstractFragment.newInstance(R.drawable.nuageplatform, R.drawable.castle, R.drawable.mario_arret_droite, R.drawable.brownbloc, R.drawable.bloc, R.drawable.greenpipe, R.color.blue_sky);

    public Boolean leftMode, soundEffect, music;

    //****METHODES****//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("OnCreate Method called");
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
                    //for(Fragment lev : levels){
                        //AbstractFragment level = (AbstractFragment)lev;
                        //level.setChar(level.getView(), level.CHARACTER_WIDTH, level.CHARACTER_HEIGHT, R.drawable.luigi_arret_droite);
                    //}
                    moveFragment(fragmentManager);
                }
                else{
                    setCurrentCharacter(MARIO_SELECTED);
                    //for(Fragment lev : levels){
                        //AbstractFragment level = (AbstractFragment)lev;
                        //level.setChar(level.getView(), level.CHARACTER_WIDTH, level.CHARACTER_HEIGHT, R.drawable.mario_arret_droite);
                    //}
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

    }
    //****GETTERS****//
    public int getCurrentLevelSelected(){return this.currentLevelSelected;}
    public int getCurrentSelectedCharacter(){return this.currentSelectedCharacter;}

    //****SETTERS****//
    public void setCurrentCharacter(int c) {this.currentSelectedCharacter = c;}
    public void setLeftHandMode(Boolean b){this.leftMode = b;}
    public void setCurrentLevelSelected(int i){this.currentLevelSelected=i;}
    public void setDefaultValues(){
        setCurrentCharacter(1);
        setCurrentLevelSelected(1);
        setLeftHandMode(false);
    }
    public void setSoundEffect(boolean b){this.soundEffect=b;}
    public void setMusic(boolean b){this.music=b;}
    //**** AUTRES METHODES ****//
    public void initFirstFragment(FragmentManager fragManager){
        fragManager.beginTransaction()
                .replace(R.id.mainmenu_fragment, level1)
                .setReorderingAllowed(true).commit();
    }
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