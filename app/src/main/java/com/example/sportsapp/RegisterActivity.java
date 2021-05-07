package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {
TextView userNameTv,weightTv,heightTv,passwordTv;
DatePicker dateOfbirthDP;
Button registerBt;
Switch genderSwitch;
    TextView tvPasswordText,tvUserNameText,tvWeightText,tvHeightText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        userNameTv = (TextView) findViewById(R.id.txtName);
        weightTv = (TextView) findViewById(R.id.txtWeight);
        heightTv = (TextView) findViewById(R.id.txtHeight);
        passwordTv = (TextView) findViewById(R.id.txtPwd);
        dateOfbirthDP = (DatePicker) findViewById(R.id.date_picker);
        registerBt = (Button) findViewById(R.id.bt_resistor);
        genderSwitch = (Switch) findViewById(R.id.genderSw);
        tvPasswordText= (TextView) findViewById(R.id.sixTxt);
        tvUserNameText= (TextView) findViewById(R.id.fstTxt);
        tvWeightText= (TextView) findViewById(R.id.secTxt);
        tvHeightText= (TextView) findViewById(R.id.thirdTxt);
        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!String.valueOf(userNameTv.getText()).equals("")) && ((String.valueOf(passwordTv.getText()).length() >5)) &&
                        (databaseHandler.findUserIdCursorfromUserName(String.valueOf(userNameTv.getText())) == null)
                        && (parseIntOrNull(String.valueOf(weightTv.getText())) != null) && (parseIntOrNull(String.valueOf(heightTv.getText())) != null)) {
                    parseIntOrNull(String.valueOf(heightTv.getText()));
                    UserClass userClass = new UserClass(String.valueOf(userNameTv.getText()), Double.parseDouble(String.valueOf(weightTv.getText()))
                            , Double.parseDouble(String.valueOf(heightTv.getText())),
                            dateOfbirthDP.getDayOfMonth() + "/" + dateOfbirthDP.getMonth() + "/" + dateOfbirthDP.getYear(),
                            genderSwitch.isChecked(),
                            0,0,0, "1/1/1900", "1/1/1900", "1/1/1900"//presetOfSteps
                    );
                    databaseHandler.createNewRowOfData(userClass,String.valueOf(passwordTv.getText()));
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    intent.putExtra("USER_CLASS", userClass);
                    Log.d("userClass", userClass.toString() + "****" + dateOfbirthDP.getDayOfMonth() + "/" + dateOfbirthDP.getMonth() + "/" + dateOfbirthDP.getYear());
                    startActivity(intent);

                } else {
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
                    if (databaseHandler.findUserIdCursorfromUserName(String.valueOf(userNameTv.getText())) != null) {
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
    public Double parseIntOrNull(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}