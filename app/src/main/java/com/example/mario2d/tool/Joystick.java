package com.example.mario2d.tool;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Joystick {
    private float innerCircleCenterX, innerCircleCenterY, outerCircleCenterX, outerCircleCenterY, incrX, incrY;
    private float innerCircleRadius, outerCircleRadius;
    private int couleurInterne, couleurExterne;
    private double actuatorX, actuatorY;
    private boolean isPressed;
    private Paint outerPaint, innerPaint;

    public Joystick(float centerX, float centerY, float innerCircleRadius, float outerCircleRadius){

        this.innerCircleCenterX = centerX;
        this.innerCircleCenterY = centerY;
        this.outerCircleCenterX = centerX;
        this.outerCircleCenterY = centerY;

        this.actuatorX = 0;
        this.actuatorY = 0;

        this.innerCircleRadius = innerCircleRadius;
        this.outerCircleRadius = outerCircleRadius;

        this.couleurInterne=Color.RED;
        this.couleurExterne=Color.WHITE;

        outerPaint = new Paint();
        outerPaint.setColor(Color.WHITE);
        outerPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerPaint = new Paint();
        innerPaint.setColor(Color.RED);
        innerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    public void draw(Canvas canvas){
        float x = (float)(innerCircleCenterX+incrX);
        float y = (float)(innerCircleCenterY+incrY);
        canvas.drawCircle(outerCircleCenterX, outerCircleCenterY, outerCircleRadius, outerPaint);
        canvas.drawCircle(x, y, innerCircleRadius,innerPaint);
    }
    public boolean isPressed(double x, double y){
        return fitInCircle(outerCircleCenterX, outerCircleCenterY, outerCircleRadius, x, y);
    }
    public void setIsPressed(boolean b){this.isPressed = b;}
    public boolean getIsPressed(){return this.isPressed;}
    public void setActuator(double x, double y){
        double deltaX = x - outerCircleCenterX;
        double deltaY = y - outerCircleCenterY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if(deltaDistance < outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        }
        else{
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }
    public void update(){
        updateInnerCirclePosition();
    }
    public void updateInnerCirclePosition(){
        innerCircleCenterX = (float) (outerCircleCenterX + actuatorX*outerCircleRadius);
        innerCircleCenterY = (float) (outerCircleCenterY + actuatorY*outerCircleRadius);
    }
    public boolean fitInCircle(double cx, double cy, double r, double x, double y){
        double vector = Math.sqrt(Math.pow(x-cx, 2) + Math.pow(y-cy, 2));
        return vector<r;
    }
    public void resetActuatorXY(){
        actuatorX = 0.0;
        actuatorY = 0.0;
    }
    public boolean orientedInRight(){
        if(innerCircleCenterX>=outerCircleCenterX){return true;}
        return false;
    }
}
