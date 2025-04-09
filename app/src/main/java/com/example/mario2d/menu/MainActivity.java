package com.example.mario2d.menu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mario2d.R;

public class MainActivity extends AppCompatActivity {

    //****VARIABLES****//
    protected String currentSelectedCaracter;
    protected String currentLevelSelected;
    protected Boolean leftMode;

    //****METHODES****//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    //****GETTERS****//
    public String getCurrentLevelSelected(){return this.currentLevelSelected;}
    public String getCurrentSelectedCharacter(){return this.currentSelectedCaracter;}

    //****SETTERS****//
    public void setCurrentCharacter(String c) {this.currentSelectedCaracter = c;}
    public void setLefthandMode(Boolean b){this.leftMode = b;}
    public void setDefaultValues(){
        setCurrentCharacter("Mario");
        setLefthandMode(false);
    }

}