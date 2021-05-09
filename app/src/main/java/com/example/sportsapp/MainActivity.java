package com.example.sportsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    TextView tvSteps,tvMaxSteps;
    boolean running= false,weekly=false,daily = true, monthly=false;
    Button btWorkout,btMonthly,btDaily,btWeekly,btAbout,btUpdate;
    CircularProgressBar circularProgressBar;
    int monthlyStepsMax = 310000,weeklyStepsMax=70000,dailyStepsMax=10000,currentSteps=0;
    DatabaseHandler databaseHandler;
    UserClass userClass;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userClass = (UserClass) getIntent().getSerializableExtra("USER_CLASS");
        tvSteps = (TextView) findViewById(R.id.tv_steps_taken);//link the textbox to java
        tvMaxSteps = (TextView) findViewById(R.id.tv_total_max);//link the textbox to java
        btWorkout = (Button) findViewById(R.id.workouts_bt);
        btAbout = (Button) findViewById(R.id.about_bt);
        circularProgressBar = (CircularProgressBar) findViewById(R.id.progress_circular);
        btMonthly = (Button) findViewById(R.id.monthly_bt);
        btWeekly = (Button) findViewById(R.id.weekly_bt);
        btDaily = (Button) findViewById(R.id.daily_bt);
        btUpdate = (Button) findViewById(R.id.update_bt);
        databaseHandler = new DatabaseHandler(this);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){//check if there is permission for step sensor
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        btWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VideosActivity.class);
                startActivity(intent);
            }
        });
        btAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        btMonthly.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//"@color/dark_gray"));
//                btMonthly.setBackgroundColor(getResources().getColor(R.color.dark_gray));
//                btDaily.setBackgroundColor(getResources().getColor(R.color.light_gray));
//                btWeekly.setBackgroundColor(getResources().getColor(R.color.light_gray));
                btMonthly.setBackgroundTintList(btMonthly.getResources().getColorStateList(R.color.dark_gray));
                btDaily.setBackgroundTintList(btDaily.getResources().getColorStateList(R.color.light_gray));
                btWeekly.setBackgroundTintList(btWeekly.getResources().getColorStateList(R.color.light_gray));
                setStepsCounter(monthlyStepsMax,userClass.getStepsStartMonthly());
                monthly=true;
                weekly=false;
                daily=false;
            }
        });
        btDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//"@color/dark_gray"));
                btMonthly.setBackgroundTintList(btMonthly.getResources().getColorStateList(R.color.light_gray));
                btDaily.setBackgroundTintList(btDaily.getResources().getColorStateList(R.color.dark_gray));
                btWeekly.setBackgroundTintList(btWeekly.getResources().getColorStateList(R.color.light_gray));
//                btMonthly.setBackgroundColor(getResources().getColor(R.color.light_gray));
//                btDaily.setBackgroundColor(getResources().getColor(R.color.dark_gray));
//                btWeekly.setBackgroundColor(getResources().getColor(R.color.light_gray));
                setStepsCounter(dailyStepsMax,userClass.getStepsStartDaily());
                monthly=false;
                weekly=false;
                daily=true;
            }
        });
        btWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btMonthly.setBackgroundColor(getResources().getColor(R.color.light_gray));
//                btDaily.setBackgroundColor(getResources().getColor(R.color.light_gray));
//                btWeekly.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                btMonthly.setBackgroundTintList(btMonthly.getResources().getColorStateList(R.color.light_gray));
                btDaily.setBackgroundTintList(btDaily.getResources().getColorStateList(R.color.light_gray));
                btWeekly.setBackgroundTintList(btWeekly.getResources().getColorStateList(R.color.dark_gray));
                setStepsCounter(weeklyStepsMax,userClass.getStepsStartWeekly());
                monthly=false;
                weekly=true;
                daily=false;
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UpdateUserActivity.class);
                intent.putExtra("USER_CLASS",userClass);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);//define the sensor type
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();//in case there is no sensor
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;

//        sensorManager.unregisterDynamicSensorCallback(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
        String date1 = df1.format(Calendar.getInstance().getTime());
        DateClass date = new DateClass(date1);
        if(userClass.getZeroDateOfDaily().after( date)) {
            Log.d("MainActivity","your zero day of the day count was restart");
            userClass.setZeroDateOfDaily(date.getDay() + "/" + date.getMonth() + "/" + date.getYear());
            userClass.setStepsStartDaily((int)event.values[0]);
            databaseHandler.updateUserClass(databaseHandler.findUserIdCursorfromUserName(userClass.getUserName()),userClass);
        }
        if(userClass.getZeroDateOfWeekly().afterDay( date,7)) {
            Log.d("MainActivity","your zero day of the week was count restart");
            userClass.setZeroDateOfWeekly(date.getDay() + "/" + date.getMonth() + "/" + date.getYear());
            userClass.setStepsStartWeekly((int)event.values[0]);
            databaseHandler.updateUserClass(databaseHandler.findUserIdCursorfromUserName(userClass.getUserName()),userClass);
        }
        if(userClass.getZeroDateOfMonthly().afterMonth( date,1)) {
            Log.d("MainActivity","your zero day of the month was count restart");
            userClass.setZeroDateOfMonthly(date.getDay() + "/" + date.getMonth() + "/" + date.getYear());
            userClass.setStepsStartMonthly((int)event.values[0]);
            databaseHandler.updateUserClass(databaseHandler.findUserIdCursorfromUserName(userClass.getUserName()),userClass);
        }

    if(running){
        currentSteps = (int) event.values[0];
        if(weekly) {
            setStepsCounter(weeklyStepsMax, userClass.getStepsStartWeekly());
        }
        else {
            if(daily){
                setStepsCounter(dailyStepsMax, userClass.getStepsStartDaily());
            }
            else {
                setStepsCounter(monthlyStepsMax, userClass.getStepsStartMonthly());
            }
        }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void setStepsCounter(int max,int correction){
        tvMaxSteps.setText("/"+max);
        circularProgressBar.setProgressMax(max);
        tvSteps.setText(String.valueOf(currentSteps- correction));
        circularProgressBar.setProgressWithAnimation(currentSteps- correction, (long) 1000); // =1s
    }
}