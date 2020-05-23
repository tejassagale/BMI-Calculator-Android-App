package com.tejas.bmicalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by ANIL on 26/06/2018.
 */

public class MyDatabase extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;

    MyDatabase(Context context)
    {
        super(context,"bmidb",null,1);
        this.context=context;
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="create table bmi( bmi text,date String)";
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void addData(String Bmi,String date){
        ContentValues cv=new ContentValues();

        cv.put("bmi",Bmi);
        cv.put("date",date);

        long rid=db.insert("bmi",null,cv);
        if(rid<0)
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show();

    }

    public String viewdata()
    {
        Cursor cursor=db.query("bmi",null,null,null,null,null,null);
        StringBuffer sb=new StringBuffer();
        cursor.moveToFirst();

        if( cursor.getCount() > 0)
        {
            do {
                sb.append("\n \u25BA BMI : "+cursor.getString(0)+"\n     "+cursor.getString(1));

            }while(cursor.moveToNext());
        }
        return sb.toString();
    }

    }

