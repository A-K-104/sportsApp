package com.example.sportsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    TextView tvSteps,tvMaxSteps;
    boolean running= false,weekly=false,daily = true, monthly=false;
    Button btWorkout,btMonthly,btDaily,btWeekly,btAbout;
    CircularProgressBar circularProgressBar;
    int monthlySteps =0,monthlyStepsMax = 310000,weeklySteps=100,weeklyStepsMax=70000,dailySteps=7000,dailyStepsMax=10000,currentSteps=0;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSteps = (TextView) findViewById(R.id.tv_steps_taken);//link the textbox to java
        tvMaxSteps = (TextView) findViewById(R.id.tv_total_max);//link the textbox to java
        btWorkout = (Button) findViewById(R.id.workouts_bt);
        btAbout = (Button) findViewById(R.id.about_bt);
        circularProgressBar = (CircularProgressBar) findViewById(R.id.progress_circular);
        btMonthly = (Button) findViewById(R.id.monthly_bt);
        btWeekly = (Button) findViewById(R.id.weekly_bt);
        btDaily = (Button) findViewById(R.id.daily_bt);
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
                btMonthly.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                btDaily.setBackgroundColor(getResources().getColor(R.color.light_gray));
                btWeekly.setBackgroundColor(getResources().getColor(R.color.light_gray));
                setStepsCounter(monthlyStepsMax,monthlySteps);
                monthly=true;
                weekly=false;
                daily=false;
            }
        });
        btDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//"@color/dark_gray"));
                btMonthly.setBackgroundColor(getResources().getColor(R.color.light_gray));
                btDaily.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                btWeekly.setBackgroundColor(getResources().getColor(R.color.light_gray));
                setStepsCounter(dailyStepsMax,dailySteps);
                monthly=false;
                weekly=false;
                daily=true;
            }
        });
        btWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//"@color/dark_gray"));
                btMonthly.setBackgroundColor(getResources().getColor(R.color.light_gray));
                btDaily.setBackgroundColor(getResources().getColor(R.color.light_gray));
                btWeekly.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                setStepsCounter(weeklyStepsMax,weeklySteps);
                monthly=false;
                weekly=true;
                daily=false;
            }
        });
        UserClass userClass=new UserClass("assaf","kostiner",67.0,185.0, new Date(1999,10,14),true);
//        Toast.makeText(this,"lol: "+String.valueOf(userClass.getBmiString()),Toast.LENGTH_LONG).show();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){//check if there is permission for step sensor
            //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        else {//start the sensor
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        }
        }


    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);//define the sensor type
        if(countSensor!=null){
            sensorManager.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
        }
        else {
            Toast.makeText(this,"Sensor not found!",Toast.LENGTH_SHORT).show();//in case there is no sensor
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
    if(running){
        currentSteps = (int) event.values[0];
        if(weekly) {
            setStepsCounter(weeklyStepsMax, weeklySteps);
        }
        else {
            if(daily){
                setStepsCounter(dailyStepsMax, dailySteps);
            }
            else {
                setStepsCounter(monthlyStepsMax, monthlySteps);
            }
        }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void setStepsCounter(int max,int correction){
        tvMaxSteps.setText(String.valueOf("/"+max));
        circularProgressBar.setProgressMax(max);
        tvSteps.setText(String.valueOf(currentSteps- correction));
        circularProgressBar.setProgressWithAnimation(currentSteps- correction, (long) 1000); // =1s
    }
}