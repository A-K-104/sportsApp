package com.example.sportsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    TextView tv_steps;
    boolean running= false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_steps= (TextView) findViewById(R.id.TextBox);//link the textbox to java
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
//        Toast.makeText(this,"running",Toast.LENGTH_SHORT).show();
        tv_steps.setText(String.valueOf(event.values[0]));
    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}