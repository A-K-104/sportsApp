package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
TextView userNameTv,weightTv,heightTv,passwordTv,loginTv;
DatePicker dateOfBirthDP;
Button registerBt;
Switch genderSw;
    TextView tvPasswordText,tvUserNameText,tvWeightText,tvHeightText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        userNameTv = (TextView) findViewById(R.id.reg_name_et);
        weightTv = (TextView) findViewById(R.id.reg_weight_et);
        heightTv = (TextView) findViewById(R.id.reg_height_et);
        passwordTv = (TextView) findViewById(R.id.reg_password_et);
        dateOfBirthDP = (DatePicker) findViewById(R.id.reg_date_of_birth_picker);
        registerBt = (Button) findViewById(R.id.reg_bt_resistor);
        genderSw = (Switch) findViewById(R.id.reg_gender_sw);
        tvPasswordText= (TextView) findViewById(R.id.reg_password_tv);
        tvUserNameText= (TextView) findViewById(R.id.reg_name_tv);
        tvWeightText= (TextView) findViewById(R.id.reg_weight_tv);
        tvHeightText= (TextView) findViewById(R.id.reg_height_tv);
        loginTv= (TextView) findViewById(R.id.reg_tv_login);
        /**
         * button that returns us to the previous activity
         */
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * register button
         * it is testing the values of input if ok will save the values and move activity
         * else will show message and mak in red the issue
         */
        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!String.valueOf(userNameTv.getText()).equals("")) && //if the user name isn't empty
                        ((String.valueOf(passwordTv.getText()).length() >5)) &&//if the password is longer then 5
                        (databaseHandler.returnUserIdByUserName(String.valueOf(userNameTv.getText())) == null) &&//if the userName isn't already in use
                        (parseIntOrNull(String.valueOf(weightTv.getText())) != null) &&//if the weight value is int
                        (parseIntOrNull(String.valueOf(heightTv.getText())) != null)) {//if the height value is int
                    /**
                     * create the new User from the data in textBox's
                     * userName, weight, height, dateOfBirth, gender (as bool), zero point of counting steps
                     * then we upload the data to db
                     * and last it will start new activity
                     */
                    UserClass userClass = new UserClass(String.valueOf(userNameTv.getText()), Double.parseDouble(String.valueOf(weightTv.getText()))
                            , Double.parseDouble(String.valueOf(heightTv.getText())),
                            dateOfBirthDP.getDayOfMonth() + "/" + dateOfBirthDP.getMonth() + "/" + dateOfBirthDP.getYear(),
                            genderSw.isChecked(),
                            0,0,0, "1/1/1900", "1/1/1900", "1/1/1900"//presetOfSteps
                    );
                    databaseHandler.createNewRowOfData(userClass,String.valueOf(passwordTv.getText()));
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    intent.putExtra("USER_CLASS", userClass);
                    startActivity(intent);

                } else {
                    /**
                     * first we rest all text to black
                     * then we are retesting all data by itself and then mark the wrong one
                     */
                    userNameTv.setTextColor(Color.BLACK);
                    tvUserNameText.setTextColor(Color.BLACK);
                    passwordTv.setTextColor(Color.BLACK);
                    tvPasswordText.setTextColor(Color.BLACK);
                    weightTv.setTextColor(Color.BLACK);
                    tvWeightText.setTextColor(Color.BLACK);
                    heightTv.setTextColor(Color.BLACK);
                    tvHeightText.setTextColor(Color.BLACK);
                    if (String.valueOf(userNameTv.getText()).equals("")) {
                    userNameTv.setTextColor(Color.RED);
                        tvUserNameText.setTextColor(Color.RED);
                        Toast.makeText(RegisterActivity.this, "Failed to register, need user name", Toast.LENGTH_SHORT).show();
                    }
                    if (String.valueOf(passwordTv.getText()).length()<=5) {
                        passwordTv.setTextColor(Color.RED);
                        tvPasswordText.setTextColor(Color.RED);
                        Toast.makeText(RegisterActivity.this, "Failed to register, password to short at list 6 letters", Toast.LENGTH_SHORT).show();
                    }
                    if (databaseHandler.returnUserIdByUserName(String.valueOf(userNameTv.getText())) != null) {
                        userNameTv.setTextColor(Color.RED);
                        tvUserNameText.setTextColor(Color.RED);
                        Toast.makeText(RegisterActivity.this, "Failed to register, user name already in use", Toast.LENGTH_SHORT).show();
                    }
                    if (parseIntOrNull(String.valueOf(weightTv.getText())) == null) {
                        weightTv.setTextColor(Color.RED);
                        tvWeightText.setTextColor(Color.RED);
                        Toast.makeText(RegisterActivity.this, "Failed to register, need weight in 00.0 format", Toast.LENGTH_SHORT).show();
                    }
                    if (parseIntOrNull(String.valueOf(heightTv.getText())) == null) {
                        heightTv.setTextColor(Color.RED);
                        tvHeightText.setTextColor(Color.RED);
                        Toast.makeText(RegisterActivity.this, "Failed to register, need weight in 00.0 format", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    /**
     * this function test if a string value is parsable
     * @param value the string that you want to test
     * @return if it parsable it will return the number else null
     */
    public Double parseIntOrNull(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}