package com.tejas.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    TextView tvCity, tvTemp, tvWelcome, tvHeight, tvFeet, tvInch, tvWeight;
    EditText etWeight;
    Spinner spnFeet, spnInch;
    Button btnCalculate, btnViewHistory;
    SharedPreferences sp;
    String feet, inch;
    String w;
    double height, weight, f, inc;
    GoogleApiClient gac;
    Location loc;
    public String City;
     static MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int o = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        tvCity = (TextView) findViewById(R.id.tvCity);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvFeet = (TextView) findViewById(R.id.tvFeet);
        tvInch = (TextView) findViewById(R.id.tvInch);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        etWeight = (EditText) findViewById(R.id.etWeight);
        spnFeet = (Spinner) findViewById(R.id.spnFeet);
        spnInch = (Spinner) findViewById(R.id.spnInch);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnViewHistory = (Button) findViewById(R.id.btnViewHistory);

        db=new MyDatabase(this);
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        gac = builder.build();






        sp = getSharedPreferences("p1", MODE_PRIVATE);
        String name = sp.getString("n", "");
        tvWelcome.setText("Welcome " + name);


        final ArrayList<String> feetlist = new ArrayList<String>();
        feetlist.add("1");
        feetlist.add("2");
        feetlist.add("3");
        feetlist.add("4");
        feetlist.add("5");
        feetlist.add("6");
        feetlist.add("7");
        feetlist.add("8");
        feetlist.add("9");
        feetlist.add("10");

        ArrayAdapter<String> feetadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, feetlist);
        spnFeet.setAdapter(feetadapter);

        final ArrayList<String> inchlist = new ArrayList<String>();
        inchlist.add("0");
        inchlist.add("1");
        inchlist.add("2");
        inchlist.add("3");
        inchlist.add("4");
        inchlist.add("5");
        inchlist.add("6");
        inchlist.add("7");
        inchlist.add("8");
        inchlist.add("9");
        inchlist.add("10");
        inchlist.add("11");
        inchlist.add("12");

        ArrayAdapter<String> inchadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inchlist);
        spnInch.setAdapter(inchadapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = spnFeet.getSelectedItemPosition();
                feet = feetlist.get(id);

                int id1 = spnInch.getSelectedItemPosition();
                inch = inchlist.get(id1);

                f = Double.parseDouble(feet);
                inc = Double.parseDouble(inch);
                if (inc < 9)
                    inc = inc / 10;
                else
                    inc = inc / 100;


                w = etWeight.getText().toString();
                if (w.length() == 0) {
                    etWeight.setError("Invalid Weight");
                    etWeight.requestFocus();
                    return;
                }

                weight = Double.parseDouble(w);
                height = (f + inc) / 3.28;
                double bmi = (weight / (height * height));
                String BMI = String.valueOf(bmi);

                Intent i = new Intent(MainActivity.this, CalculateActivity.class);
                i.putExtra("bmi", BMI);
                startActivity(i);
                finish();

            }
        });

        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,ViewActivity.class);
                startActivity(i);
                finish();


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Website) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.calculator.net/bmi-calculator.html"));
            startActivity(i);
        }
        if (item.getItemId() == R.id.About) {
            Toast.makeText(this, "BMI calculator developed by Tejas", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gac != null)
            gac.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gac.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        loc = LocationServices.FusedLocationApi.getLastLocation(gac);

        if (loc != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            Geocoder g = new Geocoder(this, Locale.ENGLISH);
            try {
                List<Address> la = g.getFromLocation(lat, lon, 1);
                Address add = la.get(0);
                City = add.getLocality();
                tvCity.setText(City);

                String url = "http://api.openweathermap.org/";
                String spc = "data/2.5/weather?units=metric";
                String qu = "&q="+City;
                String id = "25f8300adeb39bfd944443b89c1e9463";
                String m = url + spc + qu + "&appid=" + id;

                MyTask t = new MyTask();
                t.execute(m);





            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Please enable Gps/Come in open Area", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    class MyTask extends AsyncTask<String, Void, Double> {


        @Override
        protected Double doInBackground(String... strings) {
            double temp = 0.0;
            String line = "", json = "";

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                while ((line = br.readLine()) != null) {
                    json = json + line + "\n";
                }

                JSONObject o = new JSONObject(json);
                JSONObject p = o.getJSONObject("main");
                temp = p.getDouble("temp");


            } catch (Exception e) {
                e.printStackTrace();
            }
            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvTemp.setText("Temp="+aDouble+"\u2103");
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






