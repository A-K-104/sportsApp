package com.example.sportsapp;

import android.util.Log;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserClass implements Serializable {

    private String userName;
    private double weight;
    private double height;
    private Date dateOfBirth;
    private boolean gender;//male=true // female= false
    private int stepsStartMonthly;
    private int stepsStartDaily;
    private int stepsStartWeekly;
    private Date zeroDateOfMonthly;
    private Date zeroDateOfDaily;
    private Date zeroDateOfWeekly;

    public UserClass(String name, double weight, double height ,String dateOfBirth,boolean gender,int setpsZero,String zeroDateOfMonthly,String zeroDateOfDaily,String zeroDateOfWeekly) {
        this.userName = name;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth= returnsDateFromString(dateOfBirth);
        this.zeroDateOfMonthly= returnsDateFromString(zeroDateOfMonthly);
        this.zeroDateOfDaily= returnsDateFromString(zeroDateOfDaily);
        this.zeroDateOfWeekly= returnsDateFromString(zeroDateOfWeekly);
        this.gender = gender;
        this.stepsStartDaily=setpsZero;
        this.stepsStartMonthly=setpsZero;
        this.stepsStartWeekly=setpsZero;
    }
    private Date returnsDateFromString(String stringDate){
        if(stringDate.length()==10)
        return new Date(Integer.parseInt(stringDate.substring(6,10)),Integer.parseInt(stringDate.substring(3,5))-1,Integer.parseInt(stringDate.substring(0,2)));
    return new Date(1900,1,1);
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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = returnsDateFromString(dateOfBirth);
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

    public Date getZeroDateOfMonthly() {
        return zeroDateOfMonthly;
    }

    public void setZeroDateOfMonthly(String zeroDateOfMonthly) {
        this.zeroDateOfMonthly = returnsDateFromString(zeroDateOfMonthly);
    }

    public Date getZeroDateOfDaily() {
        return zeroDateOfDaily;
    }

    public void setZeroDateOfDaily(String zeroDateOfDaily) {
        this.zeroDateOfDaily = returnsDateFromString(zeroDateOfDaily);
    }

    public Date getZeroDateOfWeekly() {
        return zeroDateOfWeekly;
    }

    public void setZeroDateOfWeekly(String zeroDateOfWeekly) {
        this.zeroDateOfWeekly = returnsDateFromString(zeroDateOfWeekly);
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
