package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateUserActivity extends AppCompatActivity {

    UserClass userClass;
    TextView userNameTv,weightTv,heightTv,loginTv;
    DatePicker dateOfBirthDP;
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
        dateOfBirthDP = (DatePicker) findViewById(R.id.update_date_picker);
        updateBt = (Button) findViewById(R.id.bt_update);
        restDayBt = (Button) findViewById(R.id.bt_reset_day_count);
        restMonthBt = (Button) findViewById(R.id.bt_reset_month_count);
        restWeekBt = (Button) findViewById(R.id.bt_reset_week_count);
        genderSwitch = (Switch) findViewById(R.id.update_gender_sw);
        userNameTv.setText(userClass.getUserName());
        weightTv.setText(String.valueOf(userClass.getWeight()));
        heightTv.setText(String.valueOf(userClass.getHeight()));
        dateOfBirthDP.init(userClass.getDateOfBirth().getYear(),userClass.getDateOfBirth().getMonth()-1,userClass.getDateOfBirth().getDay(),null);
        genderSwitch.setChecked(userClass.isGender());
        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((((databaseHandler.returnUserIdByUserName(String.valueOf(userNameTv.getText())) == null) ))||//if the userName isn't already in use
                        (String.valueOf(userNameTv.getText()).equals(userClass.getUserName()))&&//or the user is the same as the current userName
                         (!String.valueOf(userNameTv.getText()).equals(""))//if the user name isn't empty
                        && (parseIntOrNull(String.valueOf(weightTv.getText())) != null) &&//if the weight value is int
                        (parseIntOrNull(String.valueOf(heightTv.getText())) != null)){//if the height value is int
                    /**
                     * update the new data User from the data in textBox's
                     * userName, weight, height, dateOfBirth, gender (as bool), zero point of counting steps
                     * then we upload the updated data to db
                     * and last it will return previous  activity
                     */
                    parseIntOrNull(String.valueOf(heightTv.getText()));
                    String oldUserName=userClass.getUserName();
                    userClass.setUserName(String.valueOf(userNameTv.getText()));
                    userClass.setDateOfBirth(dateOfBirthDP.getDayOfMonth() + "/" + (dateOfBirthDP.getMonth()+1) + "/" + dateOfBirthDP.getYear());
                    userClass.setGender(genderSwitch.isChecked());
                    userClass.setWeight(Double.parseDouble(String.valueOf(weightTv.getText())));
                    userClass.setHeight(Double.parseDouble(String.valueOf(heightTv.getText())));
                    databaseHandler.updateUserClass(databaseHandler.returnUserIdByUserName(oldUserName),userClass);
                    Toast.makeText(UpdateUserActivity.this,"updated successfully",Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent(UpdateUserActivity.this,MainActivity.class);
                    resultIntent.putExtra("USER_CLASS", userClass);
                    setResult(Activity.RESULT_OK, resultIntent);
                    startActivity(resultIntent);
                } else {
                    /**
                     * we are retesting all data by itself and then mark the wrong one
                     */
                    if (String.valueOf(userNameTv.getText()).equals("")) {
                        userNameTv.setTextColor(Color.RED);
                        Toast.makeText(UpdateUserActivity.this, "Failed to update, need user name", Toast.LENGTH_SHORT).show();
                    }

                    if (databaseHandler.returnUserIdByUserName(String.valueOf(userNameTv.getText())) != null&&!String.valueOf(userNameTv.getText()).equals(userClass.getUserName())) {
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
        /**
         * this button rest the date of daily steps counter by changing the date to 1/1/1900
         */
        restDayBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClass.setZeroDateOfDaily("1/1/1900");
                databaseHandler.updateUserClass(databaseHandler.returnUserIdByUserName(userClass.getUserName()),userClass);
                Toast.makeText(UpdateUserActivity.this,"updated successfully",Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent(UpdateUserActivity.this,MainActivity.class);
                resultIntent.putExtra("USER_CLASS", userClass);
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(resultIntent);
            }
        });
        /**
         * this button rest the date of weekly steps counter by changing the date to 1/1/1900
         */
        restWeekBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClass.setZeroDateOfWeekly("1/1/1900");
                databaseHandler.updateUserClass(databaseHandler.returnUserIdByUserName(userClass.getUserName()),userClass);
                Toast.makeText(UpdateUserActivity.this,"updated successfully",Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent(UpdateUserActivity.this,MainActivity.class);
                resultIntent.putExtra("USER_CLASS", userClass);
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(resultIntent);
            }
        });
        /**
         * this button rest the date of monthly steps counter by changing the date to 1/1/1900
         */
        restMonthBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClass.setZeroDateOfMonthly("1/1/1900");
                databaseHandler.updateUserClass(databaseHandler.returnUserIdByUserName(userClass.getUserName()),userClass);
                Toast.makeText(UpdateUserActivity.this,"updated successfully",Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent(UpdateUserActivity.this,MainActivity.class);
                resultIntent.putExtra("USER_CLASS", userClass);
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(resultIntent);
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