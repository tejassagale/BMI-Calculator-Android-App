package com.tejas.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.round;

public class CalculateActivity extends AppCompatActivity {

    TextView tvdata,tvUnderWeight,tvNormal,tvOverWeight,tvObese;
    Button btnBack,btnShare,btnSave;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        tvdata=(TextView)findViewById(R.id.tvData);
        tvUnderWeight=(TextView)findViewById(R.id.tvUnderWeight);
        tvNormal=(TextView)findViewById(R.id.tvNormal);
        tvOverWeight=(TextView)findViewById(R.id.tvOverWeight);
        tvObese=(TextView)findViewById(R.id.tvObese);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnShare=(Button)findViewById(R.id.btnShare);
        btnSave=(Button)findViewById(R.id.btnSave);



        sp=getSharedPreferences("p1",MODE_PRIVATE);
        final String name=sp.getString("n","");
        final String age=sp.getString("a","");
        final String phone=sp.getString("p","");
        final String gender=sp.getString("g","");
        Intent i=getIntent();
        final String bmi=i.getStringExtra("bmi");
        String result="";
        double Bmi=Double.parseDouble(bmi);
        Toast.makeText(this, "Bmi="+bmi.toString().substring(0,5), Toast.LENGTH_SHORT).show();
        if(Bmi<18.5) {
            result = " You are UnderWeight";
            tvUnderWeight.setTextColor(Color.parseColor("#ff0000"));
        }
        else if (Bmi > 18.5 && Bmi < 25){
            result = "You are Normal";
            tvNormal.setTextColor(Color.parseColor("#ff0000"));
        }
        else if (Bmi>25 && Bmi<30) {
            result = "You are OverWeight";
            tvOverWeight.setTextColor(Color.parseColor("#ff0000"));
        }
        else if (Bmi>30) {
            result = "You are Obese";
            tvObese.setTextColor(Color.parseColor("#ff0000"));
        }

        tvdata.setText("Your BMI is "+bmi.toString().substring(0,5)+" and "+result);
        tvUnderWeight.setText("Below 18.5 is UnderWeight");
        tvNormal.setText("Between 18.5 and 25 is Normal");
        tvOverWeight.setText("Between 25 and 30 is Overweight");
        tvObese.setText("More thane 30 is Obese");


        final String msg="Name:"+name+"\nAge : "+age+"\nPhone Number : "+phone+"\nGender : "+gender+"\nBMI : "
                +bmi.toString().substring(0,5)+"\n"+result;

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CalculateActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,msg);
                startActivity(i);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date d= Calendar.getInstance().getTime();
                String date=d.toString();
                MainActivity.db.addData(bmi.toString().substring(0,5),date);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(CalculateActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
