package com.example.mario2d.tool;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;

public class AbstractButton {
    private static int CIRCLE = 1;
    private static int RECTANGLE = 2;
    private int x, y, width, height, borderWidth, choosenShape, font, rectangleRoundRadius;
    private boolean bordure, pressed;
    private int[] rayons;
    private Color[] colors;
    private Bitmap[] bitmaps;
    private String texte;
    private Color textColor;
    private float textSize;
    private Context context;

    /**
     * Constructeur pour des formes rectangulaires
     * @param x
     * @param y
     * @param width
     * @param height
     * @param bordure
     * @param couleurs
     * @param bitmaps
     */
    public AbstractButton(Context context, int x, int y, int width, int height, boolean bordure, @Nullable Color couleurs[],@Nullable Bitmap bitmaps[]){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.context = context;

        if(couleurs!=null){
            colors = new Color[couleurs.length];
            colors = couleurs;
        }
        if(bitmaps!=null){
            this.bitmaps = new Bitmap[bitmaps.length];
            this.bitmaps = bitmaps;
        }
        this.bordure = bordure;

        if(bordure){borderWidth = 3;}
        else{borderWidth = 0;}

        this.choosenShape = RECTANGLE;
        this.pressed = false;
        this.textSize = 0;
        this.font = 0;
        this.rectangleRoundRadius = 0;
    }

    /**
     * Constructeur pour boutons de forme circulaire
     * @param x
     * @param y
     * @param rayons
     * @param couleurs
     * @param bitmaps
     */
    public AbstractButton(Context context,int x, int y, int[] rayons, boolean bordure,@Nullable Color[] couleurs,@Nullable Bitmap bitmaps[]){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.context = context;

        this.rayons = new int[rayons.length];
        this.rayons = rayons;

        if(couleurs!=null){
            colors = new Color[couleurs.length];
            colors = couleurs;
        }
        if(bitmaps != null){
            this.bitmaps = new Bitmap[bitmaps.length];
            this.bitmaps = bitmaps;
        }
        this.bordure = bordure;
        this.choosenShape = CIRCLE;
        this.pressed = false;
        this.textSize = 0;
        this.font = 0;
        this.rectangleRoundRadius = 0;
    }
    /**
     * Getters et setters
     */
    public int[] getRayons(){return this.rayons;}
    public Color[] getColors(){return this.colors;}
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public boolean getBordure(){return this.bordure;}
    public Bitmap[] getBitmaps(){return this.bitmaps;}
    public int getBorderWidth(){return this.borderWidth;}
    public boolean getIsPressed(){return this.pressed;}
    public String getTexte(){return this.texte;}
    public Color getTextColor(){return this.textColor;}
    public float getTextSize(){return this.textSize;}
    public int getFont(){return this.font;}
    public int getRectangleRoundRadius(){return this.rectangleRoundRadius;}

    public void setRayons(int[] r){this.rayons = r;}
    public void setColors(Color[] c){this.colors = c;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    public void setWidth(int width){this.width = width;}
    public void setHeight(int h){this.height = h;}
    public void setBordure(boolean b){this.bordure = b;}
    public void setBitmaps(Bitmap[] b){this.bitmaps = b;}
    public void setBorderWidth(int b){this.borderWidth = b;}
    public void setPressed(boolean b){this.pressed = b;}
    public void setTexte(String texte){this.texte = texte;}
    public void setTextColor(Color col){this.textColor = col;}
    public void setTextSize(float ts){this.textSize=ts;}
    public void setFont(int res){this.font = res;}
    public void setRectangleRoundRadius(int r){this.rectangleRoundRadius = r;}
    /**
     * Methodes
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        if(this.rayons!=null){
            if(this.rayons.length>0){
                for(int i = 0; i<rayons.length; i++){
                    if(this.bitmaps!=null){
                        if(bitmaps.length>0 && bitmaps.length >= i && bitmaps[i]!=null){
                            paint.setAntiAlias(true);
                            BitmapShader shader = new BitmapShader(bitmaps[i], Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                            paint.setShader(shader);
                            canvas.drawCircle((float)x, (float)y, rayons[i], paint);
                        }
                    }
                    else if(this.colors!=null){
                        if(colors.length >= i && colors[i] != null){
                            int col = colors[i].toArgb();
                            paint.setColor(col);
                        }
                        else{paint.setColor(Color.GRAY);}
                    }
                    canvas.drawCircle((float)x, (float)y, (float)rayons[i], paint);
                }
            }
        }
        else{
            if(this.bordure){
                if(this.colors!=null){
                    if(colors.length>=2 && colors[1]!=null){
                        int col = colors[1].toArgb();
                        paint.setColor(col);
                        canvas.drawRoundRect(x - borderWidth, y-borderWidth, x+width+borderWidth, y+height+borderWidth,rectangleRoundRadius, rectangleRoundRadius, paint);
                    }
                }
                if(this.bitmaps!=null){
                    if(bitmaps.length>=2 && bitmaps[1]!=null){
                        canvas.drawBitmap(bitmaps[1], x, y, paint);
                    }
                }
            }
            if(this.colors!=null){
                if(colors.length>=1){
                    paint.setColor(colors[0].toArgb());
                    canvas.drawRoundRect(x, y, x+width,y+ height,rectangleRoundRadius, rectangleRoundRadius, paint);
                }
            }
            if(this.bitmaps!=null){
                if(bitmaps.length>=1 && bitmaps[0]!=null){
                    paint.setAntiAlias(true);
                    canvas.drawBitmap(bitmaps[0], x, y, paint);
                }
            }
        }
        if(this.texte != null && this.textColor != null){
            paint.setColor(textColor.toArgb());
            if(textSize!=0){paint.setTextSize(textSize);}
            else{textSize = 10;paint.setTextSize(10);}

            if(this.font!=0){
                Typeface tFont = ResourcesCompat.getFont(context, font);
                paint.setTypeface(tFont);
            }

            Paint.FontMetrics metrics = paint.getFontMetrics();
            float yCentered = y + getHeight()/2 - (metrics.ascent + metrics.descent) / 2;
            float textWidth = paint.measureText(texte);
            float xCentered = x + getWidth()/2 - textWidth / 2;
            canvas.drawText(texte, xCentered, yCentered, paint);
        }
    }
    public boolean isInside(double posX, double posY){
        if(this.choosenShape==RECTANGLE){
            if(posX>=this.x && posX<=(getX()+getWidth()) && posY>=this.y && posY<=getY()+getHeight()){
                return true;
            }
            else{
                return false;
            }
        }
        else if(choosenShape==CIRCLE){
            double distance = Math.sqrt(Math.pow(posX-getX(), 2)+Math.pow(posY-getY(), 2));
            int rayon = rayons[rayons.length-1];
            if((int)distance <= rayon){return true;}
            else{return false;}
        }
        else{
            return false;
        }
    }
}
