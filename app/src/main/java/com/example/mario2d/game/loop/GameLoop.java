package com.example.mario2d.game.loop;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class GameLoop implements Runnable{
    private Boolean running;
    private Thread thread;
    private GameView gameView;

    public GameLoop(GameView gView){
        gameView = gView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {

        final int RESOLUTION = 60; // FPS - frame per second
        final double FREQUENCY = ((double) 1 /RESOLUTION)*1_000_000_000; // NsPF - nanosecond per frame

        double delay = 0.0; // init delay to 0 nanoseconds

        while(running){

            // remove 1 period to avoid overflow
            if(delay > FREQUENCY){
                delay -= FREQUENCY;
            }

            // define current frequency according to delay
            double currentFrequency = (double) (FREQUENCY - delay); // nanosecond

            // getting time before rendering and update functions
            double t0 = System.nanoTime(); // nanosecond

            // On met à jour
            update();
            render();

            // getting time after rendering and update functions
            double t1 = System.nanoTime();

            // Timelapse of render and update functions
            double deltaTime = t1 - t0; // nanosecond

            // dealing according to deltaTime
            if(deltaTime < currentFrequency){
                delay = 0; // Reset delay
                double elapsedTimeMillisecond = (currentFrequency - deltaTime)/1_000_000;
                try {
                    Thread.sleep((long) elapsedTimeMillisecond);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                delay += deltaTime - currentFrequency;
            }
        }
    }
    public void start(){
        this.running = true;
        thread = new Thread(this);
        thread.start();
    }
    public void stop(){this.running = false;}

    public void update(){
        gameView.update();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void render(){gameView.render();}

    public boolean isRunning(){return this.running;}

}
