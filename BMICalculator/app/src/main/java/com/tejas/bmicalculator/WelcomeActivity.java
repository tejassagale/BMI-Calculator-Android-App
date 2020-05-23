package com.tejas.bmicalculator;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;

import pl.droidsonroids.gif.GifImageView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView iv1;
    Animation animation1,animation2;
    GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        iv1 = (ImageView) findViewById(R.id.iv1);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.a2);
        iv1.startAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iv1.setVisibility(View.GONE);
                gif.setVisibility(View.VISIBLE);


            }
        },4000);

        animation2= AnimationUtils.loadAnimation(WelcomeActivity.this,R.anim.a1);
        gif=(GifImageView)findViewById(R.id.gif);

        gif.startAnimation(animation2);



        new Thread(new Runnable() {
            @Override
            public void run() {

                try {


                    Thread.sleep(10000);
                    Intent i=new Intent(WelcomeActivity.this,SignupActivity.class);
                    startActivity(i);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }




        }).start();


    }
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog a = builder.create();
        a.setTitle("Exit");
        a.show();


    }

}
