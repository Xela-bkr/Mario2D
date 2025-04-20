package com.example.mario2d.game.objet;

import android.content.Context;

public class Castle extends Objet{
    public Castle(Context context, String name, int x, int y, int width, int height) {
        super(context, name, x, y, width, height);
        this.setBitmap(name);
    }
}
