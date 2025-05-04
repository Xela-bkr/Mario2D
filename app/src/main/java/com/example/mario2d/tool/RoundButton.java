package com.example.mario2d.tool;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;

public class RoundButton {
    private int x, y, rayon, color, bitmapResource;
    private Bitmap bitmap;
    private BitmapShader shader;
    private Paint paint;
    private Context context;

    public RoundButton(Context context, int x, int y, int rayon, int color, int...BitmapResource) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.rayon = rayon;
        this.color = color;
        if (BitmapResource.length > 0) {
            this.bitmapResource = BitmapResource[0];
        }
        Bitmap brutus = BitmapFactory.decodeResource(context.getResources(), bitmapResource);
        bitmap = Bitmap.createScaledBitmap(brutus, rayon, rayon, true);
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);

    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(x+rayon/2, y+rayon/2, rayon, paint);
        canvas.drawCircle(x + rayon / 2, y + rayon / 2, rayon / 2, paint);
        canvas.drawBitmap(bitmap, x, y, paint);
    }
    public boolean pointerIn(double posX, double posY) {
        double distance = Math.sqrt(Math.pow(posX-x, 2)+Math.pow(posY-y, 2));

        if ( (int) distance <= rayon ) {
            return true;
        } else {
            return false;
        }
    }
}
