package com.tejas.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText etName,etAge,etPhone;
    RadioGroup rgGender;
    Button btnRegister;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        etName=(EditText)findViewById(R.id.etName);
        etAge=(EditText)findViewById(R.id.etAge);
        etPhone=(EditText)findViewById(R.id.etPhone);
        rgGender=(RadioGroup)findViewById(R.id.rgGender);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        sp=getSharedPreferences("p1",MODE_PRIVATE);
        String n=sp.getString("n","");
        String a=sp.getString("a","");
        String p=sp.getString("p","");
        String g=sp.getString("g","");
        if (n.length()!=0 && a.length()!=0 && p.length()!=0 && g.length()!=0)
        {
            Intent i=new Intent(SignupActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = etName.getText().toString();
                    if (name.length() == 0 || !name.matches("[a-zA-Z]+")) {
                        etName.setError("Invalid name");
                        etName.requestFocus();
                        return;
                    }
                    String age = etAge.getText().toString();
                    if (age.length() == 0) {
                        etAge.setError("Invalid age");
                        etAge.requestFocus();
                        return;
                    }
                    String phone = etPhone.getText().toString();
                    if (phone.length() != 10) {
                        etPhone.setError("Invalid Phone Number");
                        etPhone.requestFocus();
                        return;
                    }

                    int id = rgGender.getCheckedRadioButtonId();
                    RadioButton rb = rgGender.findViewById(id);
                    String gender = rb.getText().toString();

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("n", name);
                    editor.putString("a", age);
                    editor.putString("p", phone);
                    editor.putString("g", gender);
                    editor.commit();
                    Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();





                }
            });
        }

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
