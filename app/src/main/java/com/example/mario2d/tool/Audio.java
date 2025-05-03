package com.example.mario2d.tool;

import android.content.Context;
import android.media.MediaPlayer;

public class Audio {

    private MediaPlayer mediaPlayer;
    public boolean isRunning;

    public Audio(Context context,int resource)
    {
        try{
            mediaPlayer = MediaPlayer.create(context, resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setLoop(boolean loop)
    {
        try {
            mediaPlayer.setLooping(loop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public MediaPlayer getMediaPlayer()
    {
        return this.mediaPlayer;
    }
    public void play(){
        if (mediaPlayer != null)
        {
            mediaPlayer.start();
            isRunning = true;
        }
    }
    public void stop()
    {
        isRunning = false;
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
    public static void playSound(Context context, int resource)
    {
        Audio audio = new Audio(context, resource);
        audio.play();
    }
    public boolean getIsRunning()
    {
        return this.isRunning;
    }
}
