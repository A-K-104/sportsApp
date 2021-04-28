package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class VideosActivity extends AppCompatActivity {
    Button bt1,bt2,bt3,btBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        bt1 = (Button) findViewById(R.id.bt_hand_exercises);
        bt2 = (Button) findViewById(R.id.bt_leg_exercises);
        bt3 = (Button) findViewById(R.id.bt_stomach_exercises);
        btBack = (Button) findViewById(R.id.workouts_bt);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ")));
    }});
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=G1IbRujko-A")));
            }});
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=YgGzAKP_HuM")));
            }});
    }
        }