package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateUserActivity extends AppCompatActivity {

    UserClass userClass;
    TextView userNameTv,weightTv,heightTv,loginTv;
    DatePicker dateOfbirthDP;
    Button updateBt,restDayBt,restMonthBt,restWeekBt;
    Switch genderSwitch;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        databaseHandler = new DatabaseHandler(this);
        userClass = (UserClass) getIntent().getSerializableExtra("USER_CLASS");
        userNameTv = (TextView) findViewById(R.id.txt_update_user_name);
        weightTv = (TextView) findViewById(R.id.txt_update_weight);
        heightTv = (TextView) findViewById(R.id.txt_update_height);
        dateOfbirthDP = (DatePicker) findViewById(R.id.update_date_picker);
        updateBt = (Button) findViewById(R.id.bt_update);
        restDayBt = (Button) findViewById(R.id.bt_reset_day_count);
        restMonthBt = (Button) findViewById(R.id.bt_reset_month_count);
        restWeekBt = (Button) findViewById(R.id.bt_reset_week_count);
        genderSwitch = (Switch) findViewById(R.id.update_gender_sw);
        userNameTv.setText(userClass.getUserName());
        weightTv.setText(String.valueOf(userClass.getWeight()));
        heightTv.setText(String.valueOf(userClass.getHeight()));
        dateOfbirthDP.init(userClass.getDateOfBirth().getYear(),userClass.getDateOfBirth().getMonth()-1,userClass.getDateOfBirth().getDay(),null);
        genderSwitch.setChecked(userClass.isGender());
        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((((databaseHandler.findUserIdCursorfromUserName(String.valueOf(userNameTv.getText())) == null) ))||(String.valueOf(userNameTv.getText()).equals(userClass.getUserName()))&&
                         (!String.valueOf(userNameTv.getText()).equals(""))
                        && (parseIntOrNull(String.valueOf(weightTv.getText())) != null) && (parseIntOrNull(String.valueOf(heightTv.getText())) != null)){
                    parseIntOrNull(String.valueOf(heightTv.getText()));
                    String oldUserName=userClass.getUserName();
                    userClass.setUserName(String.valueOf(userNameTv.getText()));
                    userClass.setDateOfBirth(dateOfbirthDP.getDayOfMonth() + "/" + (dateOfbirthDP.getMonth()+1) + "/" + dateOfbirthDP.getYear());
                    userClass.setGender(genderSwitch.isChecked());
                    userClass.setWeight(Double.parseDouble(String.valueOf(weightTv.getText())));
                    userClass.setHeight(Double.parseDouble(String.valueOf(heightTv.getText())));
                    databaseHandler.updateUserClass(databaseHandler.findUserIdCursorfromUserName(oldUserName),userClass);
                    Toast.makeText(UpdateUserActivity.this,"updated successfully",Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent(UpdateUserActivity.this,MainActivity.class);
                    resultIntent.putExtra("USER_CLASS", userClass);
                    setResult(Activity.RESULT_OK, resultIntent);
                    startActivity(resultIntent);
                } else {

                    if (String.valueOf(userNameTv.getText()).equals("")) {
                        userNameTv.setTextColor(Color.RED);
                        Toast.makeText(UpdateUserActivity.this, "Failed to update, need user name", Toast.LENGTH_SHORT).show();
                    }

                    if (databaseHandler.findUserIdCursorfromUserName(String.valueOf(userNameTv.getText())) != null&&!String.valueOf(userNameTv.getText()).equals(userClass.getUserName())) {
                        userNameTv.setTextColor(Color.RED);
                        Toast.makeText(UpdateUserActivity.this, "Failed to update, user name already in use", Toast.LENGTH_SHORT).show();
                    }
                    if (parseIntOrNull(String.valueOf(weightTv.getText())) == null) {
                        weightTv.setTextColor(Color.RED);
                        Toast.makeText(UpdateUserActivity.this, "Failed to update, need weight in 00.0 format", Toast.LENGTH_SHORT).show();
                    }
                    if (parseIntOrNull(String.valueOf(heightTv.getText())) == null) {
                        heightTv.setTextColor(Color.RED);
                        Toast.makeText(UpdateUserActivity.this, "Failed to update, need weight in 00.0 format", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        restDayBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClass.setZeroDateOfDaily("1/1/1900");
                databaseHandler.updateUserClass(databaseHandler.findUserIdCursorfromUserName(userClass.getUserName()),userClass);
                Toast.makeText(UpdateUserActivity.this,"updated successfully",Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent(UpdateUserActivity.this,MainActivity.class);
                resultIntent.putExtra("USER_CLASS", userClass);
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(resultIntent);
            }
        });
        restWeekBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClass.setZeroDateOfWeekly("1/1/1900");
                databaseHandler.updateUserClass(databaseHandler.findUserIdCursorfromUserName(userClass.getUserName()),userClass);
                Toast.makeText(UpdateUserActivity.this,"updated successfully",Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent(UpdateUserActivity.this,MainActivity.class);
                resultIntent.putExtra("USER_CLASS", userClass);
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(resultIntent);
            }
        });
        restMonthBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClass.setZeroDateOfMonthly("1/1/1900");
                databaseHandler.updateUserClass(databaseHandler.findUserIdCursorfromUserName(userClass.getUserName()),userClass);
                Toast.makeText(UpdateUserActivity.this,"updated successfully",Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent(UpdateUserActivity.this,MainActivity.class);
                resultIntent.putExtra("USER_CLASS", userClass);
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(resultIntent);
            }
        });

    }
    public Double parseIntOrNull(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}