package com.example.sportsapp;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserClass {

    private String userName;
    private double weight;
    private double height;
    private Date dateOfBirth;
    private boolean gender;//male=true // female= false
    private int stepsStartMonthly;
    private int stepsStartDaily;
    private int stepsStartWeekly;

    public UserClass(String name, double weight, double height ,String dateOfBirth,boolean gender,int setpsZero) {
        this.userName = name;
        this.weight = weight;
        this.height = height;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.dateOfBirth = dateFormat.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Log.d("date of birth",getDateOfBirthString()+" <> "+dateOfBirth);
//        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.stepsStartDaily=setpsZero;
        this.stepsStartMonthly=setpsZero;
        this.stepsStartWeekly=setpsZero;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public String getDateOfBirthString() {
        return dateOfBirth.getDate()+"/"+(dateOfBirth.getMonth()+1)+"/"+dateOfBirth.getYear();
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isGender() {
        return gender;
    }

    public int getStepsStartMonthly() {
        return stepsStartMonthly;
    }

    public void setStepsStartMonthly(int stepsStartMonthly) {
        this.stepsStartMonthly = stepsStartMonthly;
    }

    public int getStepsStartDaily() {
        return stepsStartDaily;
    }

    public void setStepsStartDaily(int stepsStartDaily) {
        this.stepsStartDaily = stepsStartDaily;
    }

    public int getStepsStartWeekly() {
        return stepsStartWeekly;
    }

    public void setStepsStartWeekly(int stepsStartWeekly) {
        this.stepsStartWeekly = stepsStartWeekly;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBmiString(){
        DecimalFormat precision = new DecimalFormat("0.00");
        return precision.format(getBmiDouble());
    }

    public double getBmiDouble(){
        return  this.weight/Math.pow((this.height/100),2);
    }

    @Override
    public String toString() {
        String tempGender = "female";
        if (gender)
        {
            tempGender = "male";
        }
        return "UserClass{" +
                "user name='" + userName + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", dateOfBirth=" + getDateOfBirthString() +
                ", gender=" + tempGender +
                ", stepsStartWeekly=" + stepsStartWeekly +
                ", stepsStartMonthly=" + stepsStartMonthly +
                ", stepsStartDaily=" + stepsStartDaily +
                '}';
    }
}
