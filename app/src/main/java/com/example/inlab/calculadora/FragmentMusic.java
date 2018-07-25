package com.example.inlab.calculadora;

import android.Manifest.permission;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class FragmentMusic extends Fragment implements View.OnClickListener{

    View myView;
    Context myContext;
    MediaPlayer myMediaPlayer;

    ImageView play_pause, stop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_fragment_music, container, false);
        myContext = getContext();
        setButtons();

        myMediaPlayer = new MediaPlayer();
        myMediaPlayer = MediaPlayer.create(getContext(),R.raw.xxxtentacion);
        return myView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clickStop();
    }

    private void clickPlayPause(){
            if (myMediaPlayer == null) myMediaPlayer = MediaPlayer.create(getContext(), R.raw.xxxtentacion);

            if (myMediaPlayer.isPlaying()) {
                play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                myMediaPlayer.pause();
            }
            else {
                play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                myMediaPlayer.start();
                Toast.makeText(getActivity(), "Now playing: XXXTentacion - Jocelyn Flores", Toast.LENGTH_SHORT).show();
            }
    }

    private void clickStop(){
        if (myMediaPlayer != null){
            if (myMediaPlayer.isPlaying()){
                myMediaPlayer.stop();
            }
            myMediaPlayer.reset();
            myMediaPlayer.release();
            myMediaPlayer = null;

        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.play_pause:
                //view.startAnimation(AnimationUtils.loadAnimation(myContext, R.anim.image_click));
                clickPlayPause();
                break;
            case R.id.stop:
                //view.startAnimation(AnimationUtils.loadAnimation(myContext, R.anim.image_click));
                play_pause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                clickStop();
                break;
        }
    }

    private void setButtons(){
        play_pause = (ImageView) myView.findViewById(R.id.play_pause);
        stop = (ImageView) myView.findViewById(R.id.stop);

        play_pause.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

}