package com.tejas.bmicalculator;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    TextView tvView;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        tvView=(TextView)findViewById(R.id.tvView);
        tvView.setMovementMethod(new ScrollingMovementMethod());
        btnBack=(Button)findViewById(R.id.btnBack);

        String data=MainActivity.db.viewdata();
        if(data.length()==0)
        {
            tvView.setText("No data to show");

        }
        else
        {
            tvView.setText(data);

        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ViewActivity.this,MainActivity.class);
                startActivity(i);

                finish();
            }
        });


    }
    public void onBackPressed() {
        Intent i=new Intent(ViewActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
