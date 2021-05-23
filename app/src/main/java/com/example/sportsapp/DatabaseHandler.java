package com.example.sportsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";//tag of db for the logs

    private static final int DATABASE_VERSION=1;//version of db
    private static final String DATABASE_NAME = "test.db";//name of db
    private static final String TABLE_NAME = "userdata";
    private static final String COLUMN_ID = "id";//id  of line of db
    private static final String COLUMN_MONTHLY="steps_start_monthly";//the number of zero steps in month
    private static final String COLUMN_DAILY="steps_start_daily";//the number of zero steps in day
    private static final String COLUMN_WEEKLY="steps_start_weekly";//the number of zero steps in week
    private static final String COLUMN_USER_NAME="user_name";
    private static final String COLUMN_USER_PASSWORD="user_password";
    private static final String COLUMN_WEIGHT="weight";
    private static final String COLUMN_HEIGHT="height";
    private static final String COLUMN_DATE_OF_BIRTH="date_of_birth";
    private static final String COLUMN_GENDER="gender";//male=true female= false
    private static final String COLUMN_ZERO_DATE_MONTHLY="zeroDateOfMonthly";//the date that we started to count month steps
    private static final String COLUMN_ZERO_DATE_DAILY="zeroDateOfDaily";//the date that we started to count day steps
    private static final String COLUMN_ZERO_DATE_WEEKLY="zeroDateOfWeekly";//the date that we started to count week steps


    SQLiteDatabase database;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database= getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+ " ( "+COLUMN_ID+" INTEGER PRIMARY KEY , "
                +COLUMN_MONTHLY+" INTEGER, "+COLUMN_DAILY+" INTEGER, "+COLUMN_WEEKLY+" INTEGER, "+COLUMN_USER_NAME+" TEXT, "+COLUMN_USER_PASSWORD+" TEXT, "
                +COLUMN_WEIGHT+" DOUBLE, "+COLUMN_HEIGHT+" DOUBLE, "+COLUMN_DATE_OF_BIRTH+" TEXT, "+COLUMN_GENDER+" BOOLEAN, "+COLUMN_ZERO_DATE_MONTHLY+" TEXT, "+
                COLUMN_ZERO_DATE_DAILY+" TEXT, "+COLUMN_ZERO_DATE_WEEKLY+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //upload new data row to db
    public boolean createNewRowOfData(UserClass userClass,String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, userClass.getUserName());
        contentValues.put(COLUMN_USER_PASSWORD, userPassword);
        contentValues.put(COLUMN_WEIGHT, userClass.getWeight());
        contentValues.put(COLUMN_HEIGHT, userClass.getWeight());
        contentValues.put(COLUMN_DATE_OF_BIRTH, userClass.getDateOfBirth().getDate());
        contentValues.put(COLUMN_GENDER, userClass.getDateOfBirth().getDate());
        contentValues.put(COLUMN_WEEKLY, userClass.getStepsStartWeekly());
        contentValues.put(COLUMN_DAILY, userClass.getStepsStartDaily());
        contentValues.put(COLUMN_MONTHLY, userClass.getStepsStartMonthly());
        contentValues.put(COLUMN_ZERO_DATE_MONTHLY, userClass.getZeroDateOfMonthly().getDate());
        contentValues.put(COLUMN_ZERO_DATE_DAILY, userClass.getZeroDateOfDaily().getDate());
        contentValues.put(COLUMN_ZERO_DATE_WEEKLY, userClass.getZeroDateOfWeekly().getDate());
        long result = db.insert(TABLE_NAME, null, contentValues);
        //if date as inserted incorrectly it will return -1
        db.close();
        if (result == -1) {
            Log.d(TAG,"failed to upload new data");
            return false;
        } else {
            Log.d(TAG,"successfully uploaded new data");
            return true;
        }
    }



    public String convertCursorToString (Cursor cursor){//converting pointer of row to string of data
        String tableString="";
        if (cursor.moveToFirst() ){
            String[] columnNames = cursor.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (cursor.moveToNext());
        }
        return tableString;
    }

    public String returnUserIdByUserName(String userName){//gets username and finds it's id
        String output;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_USER_NAME + " = '" + userName + "'";
        Cursor data = db.rawQuery(query, null);
         output =convertCursorToString(data);
        if (data.moveToFirst()) { // check if he finds data in case not it will return null
            output= data.getString(data.getColumnIndex(COLUMN_ID));
        }
        db.close();
        data.close();
        return output;
    }

    public String returnUserPasswordFromUserId(String userId){//returns password from user id

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_USER_PASSWORD + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = '" + userId + "'";
        Cursor data = db.rawQuery(query, null);
        String password = null;
        if (data.moveToFirst()) { // check if he finds data in case not it will return null
            password = data.getString(data.getColumnIndex("user_password"));
        }
        data.close();
        db.close();
        return password;
    }

    public UserClass returnUserClassByUserId(String userId)  {//returns user by id
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT *" +  " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = '" + userId + "'";
        Cursor data = db.rawQuery(query, null);
        String password = null;
        if (data.moveToFirst()) {// check if he finds data in case not it will return null
            password = data.getString(data.getColumnIndex("user_password"));
            boolean temp = false;
            UserClass userClass;
            if (data.getString(data.getColumnIndex(COLUMN_GENDER)) == "1") {//converting 1/0 to boolean
                temp = true;
            }
            userClass = new UserClass(
                    data.getString(data.getColumnIndex(COLUMN_USER_NAME)),
                    Double.parseDouble(data.getString(data.getColumnIndex(COLUMN_WEIGHT))),
                    Double.parseDouble(data.getString(data.getColumnIndex(COLUMN_HEIGHT))),
                    data.getString(data.getColumnIndex(COLUMN_DATE_OF_BIRTH)),
                    temp,
                    Integer.parseInt(data.getString(data.getColumnIndex(COLUMN_DAILY))),
                    Integer.parseInt(data.getString(data.getColumnIndex(COLUMN_MONTHLY))),
                    Integer.parseInt(data.getString(data.getColumnIndex(COLUMN_WEEKLY))),
                    data.getString(data.getColumnIndex(COLUMN_ZERO_DATE_MONTHLY)),
                    data.getString(data.getColumnIndex(COLUMN_ZERO_DATE_DAILY)),
                    data.getString(data.getColumnIndex(COLUMN_ZERO_DATE_WEEKLY))
            );
            data.close();
            db.close();
            return userClass;
        }// if he didn't find data it will return the following data V
        return  new UserClass("name",0,0,"1/1/1900",false,0,0,0,"1/1/1900","1/1/1900","1/1/1900");
    }


    public void updateUserClass( String id, UserClass userClass){//updating user by id as location and inserting UserClass as data
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " +
                COLUMN_USER_NAME+" = '" + userClass.getUserName()+"' , "+
                COLUMN_WEIGHT +" = '" + userClass.getWeight()+"' , "+
                COLUMN_HEIGHT +" = '" + userClass.getHeight()+"' , "+
                COLUMN_DATE_OF_BIRTH+" = '" + userClass.getDateOfBirth().getDate()+"' , "+
                COLUMN_GENDER+" = '" + userClass.getGender()+"' , "+
                COLUMN_WEEKLY+" = '" +userClass.getStepsStartWeekly()+"' , "+
                COLUMN_DAILY+" = '" +userClass.getStepsStartDaily()+"' , "+
                COLUMN_MONTHLY+" = '" + userClass.getStepsStartMonthly()+"' , "+
                COLUMN_ZERO_DATE_MONTHLY+" = '" + userClass.getZeroDateOfMonthly().getDate()+"' , "+
                COLUMN_ZERO_DATE_DAILY+" = '" +userClass.getZeroDateOfDaily().getDate()+"' , "+
                COLUMN_ZERO_DATE_WEEKLY+" = '" +userClass.getZeroDateOfWeekly().getDate()+ "' WHERE " + COLUMN_ID + " = '" + id + "'";
        db.execSQL(query);
    }

}
