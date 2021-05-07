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

    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_NAME = "test.db";
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "userdata";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MONTHLY="steps_start_monthly";
    private static final String COLUMN_DAILY="steps_start_daily";
    private static final String COLUMN_WEEKLY="steps_start_weekly";
    private static final String COLUMN_USER_NAME="user_name";
    private static final String COLUMN_USER_PASSWORD="user_password";
    private static final String COLUMN_WEIGHT="weight";
    private static final String COLUMN_HEIGHT="height";
    private static final String COLUMN_DATE_OF_BIRTH="date_of_birth";
    private static final String COLUMN_GENDER="gender";//male=true // female= false
    private static final String COLUMN_ZERO_DATE_MONTHLY="zeroDateOfMonthly";
    private static final String COLUMN_ZERO_DATE_DAILY="zeroDateOfDaily";
    private static final String COLUMN_ZERO_DATE_WEEKLY="zeroDateOfWeekly";


    SQLiteDatabase database;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database= getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+ " ( "+COLUMN_ID+" INTEGER PRIMARY KEY , "/*+COLUMN_NAME+" TEXT, "*/
                +COLUMN_MONTHLY+" INTEGER, "+COLUMN_DAILY+" INTEGER, "+COLUMN_WEEKLY+" INTEGER, "+COLUMN_USER_NAME+" TEXT, "+COLUMN_USER_PASSWORD+" TEXT, "
                +COLUMN_WEIGHT+" DOUBLE, "+COLUMN_HEIGHT+" DOUBLE, "+COLUMN_DATE_OF_BIRTH+" TEXT, "+COLUMN_GENDER+" BOOLEAN, "+COLUMN_ZERO_DATE_MONTHLY+" TEXT, "+COLUMN_ZERO_DATE_DAILY+" TEXT, "+COLUMN_ZERO_DATE_WEEKLY+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    //upload new data to db
    public boolean createNewRowOfData(UserClass userClass,String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
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


//      Returns all the data from database
    public Cursor getDataBase(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;

    }
    public String convertCursorToString (Cursor cursor){
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

    public String findUserIdCursorfromUserName(String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_USER_NAME + " = '" + userName + "'";
        Cursor data = db.rawQuery(query, null);
        String output =convertCursorToString(data);
        db.close();
        data.close();
        if(output!="") {
            return output.substring(4, 5);
        }
        return null;
    }
    public String returnUserPasswordfromUserId(String userId){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_USER_PASSWORD + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = '" + userId + "'";
        Cursor data = db.rawQuery(query, null);
        String password = null;
        if (data.moveToFirst()) { // data?
            password = data.getString(data.getColumnIndex("user_password"));
        }
        data.close();
        db.close();
        return password;
    }

    public UserClass returnUserClassByUserId(String userId)  {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT *" +  " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = '" + userId + "'";
        Cursor data = db.rawQuery(query, null);
        String password = null;
        if (data.moveToFirst()) {// data?
            password = data.getString(data.getColumnIndex("user_password"));
//        String name, double weight, double height, Date dateOfBirth,boolean gender
            boolean temp = false;
            UserClass userClass;
            if (data.getString(data.getColumnIndex(COLUMN_GENDER)) == "1") {
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
//            userClass.setStepsStartMonthly(Integer.parseInt(data.getString(1)));
//            userClass.setStepsStartDaily(Integer.parseInt(data.getString(2)));
//            userClass.setStepsStartWeekly(Integer.parseInt(data.getString(3)));
            data.close();
            db.close();
            return userClass;
        }
        return  new UserClass("name",0,0,"1/1/1900",false,0,0,0,"1/1/1900","1/1/1900","1/1/1900");
    }


    public void updateUserClass( String id, UserClass userClass){
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
//
//    /**
//     * Delete from database
//     * @param id
//     * @param name
//     */
//    public void deleteName(int id, String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COLUMN_ID + " = '" + id + "'" +
//                " AND " + COLUMN_NAME + " = '" + name + "'";
//        Log.d(TAG, "deleteName: query: " + query);
//        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
//        db.execSQL(query);
//    }
}
