package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    TextView tvPassword,tvUserName;
    Button btLogIn,btRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        tvPassword= (TextView) findViewById(R.id.tv_password);
        tvUserName= (TextView) findViewById(R.id.tv_user_name);
        btLogIn= (Button) findViewById(R.id.bt_login);
        btRegister= (Button) findViewById(R.id.bt_register);
//        databaseHandler.createNewRowOfData("name2","23456",1.73,21.3,"12/02/2001",false,0,0,0);
        btLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName =  String.valueOf(tvUserName.getText());
                String password = String.valueOf(tvPassword.getText());
                password = password.replaceAll("\\s+","");//delete all spaces in the password
                String id = databaseHandler.findUserIdCursorfromUserName(userName);
                if(id!=null){
                    if(databaseHandler.returnUserPasswordfromUserId(id).equals(password))
                    {
                        UserClass userClass=databaseHandler.returnUserClassByUserId(id);
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        intent.putExtra("USER_CLASS", userClass );
                        Log.d("pass data",userClass.toString());
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(LogInActivity.this, "Failed to login. password/username is wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LogInActivity.this, "Failed to login. password/username is wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}