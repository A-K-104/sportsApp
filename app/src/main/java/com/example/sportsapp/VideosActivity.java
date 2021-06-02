package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VideosActivity extends AppCompatActivity {
    Button bt1,bt2,bt3,btBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        bt1 = (Button) findViewById(R.id.bt_calorie_killer);
        bt2 = (Button) findViewById(R.id.bt_full_body);
        bt3 = (Button) findViewById(R.id.bt_stomach_exercises);
        btBack = (Button) findViewById(R.id.back_bt);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=jpizoUy4K9s")));
    }});
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=oAPCPjnU1wA")));
            }});
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=2pLT-olgUJs")));
            }});
    }
        }