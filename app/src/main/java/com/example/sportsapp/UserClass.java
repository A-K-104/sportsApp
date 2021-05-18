package com.example.sportsapp;

import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.text.DecimalFormat;


public class UserClass implements Serializable {

    private String userName;
    private double weight;
    private double height;
    private DateClass dateOfBirth;
    private boolean gender;//male=true // female= false
    private int stepsStartMonthly;
    private int stepsStartDaily;
    private int stepsStartWeekly;
    private DateClass zeroDateOfMonthly;
    private DateClass zeroDateOfDaily;
    private DateClass zeroDateOfWeekly;

    public UserClass(String name, double weight, double height ,String dateOfBirth,boolean gender,
                     int stepsStartDaily,int stepsStartMonthly,int stepsStartWeekly,String zeroDateOfMonthly,
                     String zeroDateOfDaily,String zeroDateOfWeekly) {
        this.userName = name;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth= new DateClass(dateOfBirth);
        this.zeroDateOfMonthly=new DateClass(zeroDateOfMonthly);
        this.zeroDateOfDaily=new DateClass(zeroDateOfDaily);
        this.zeroDateOfWeekly=new DateClass(zeroDateOfWeekly);
        this.gender = gender;
        this.stepsStartDaily=stepsStartDaily;
        this.stepsStartMonthly=stepsStartMonthly;
        this.stepsStartWeekly=stepsStartWeekly;
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

    public DateClass getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = new DateClass(dateOfBirth);
    }

    public int getGender() {
        if(gender)
        return 1;
        return 0;
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

    public double getBmiDouble(){
        return  this.weight/Math.pow((this.height/100),2);
    }

    public DateClass getZeroDateOfMonthly() {
        return zeroDateOfMonthly;
    }

    public void setZeroDateOfMonthlyString(String zeroDateOfMonthly) {
        this.zeroDateOfMonthly = new DateClass(zeroDateOfMonthly);
    }

    public void setZeroDateOfMonthly(String zeroDateOfMonthly) {
        this.zeroDateOfMonthly = new DateClass(zeroDateOfMonthly);
    }

    public DateClass getZeroDateOfDaily() {
        return zeroDateOfDaily;
    }

    public void setZeroDateOfDaily(String zeroDateOfDaily) {
        this.zeroDateOfDaily = new DateClass(zeroDateOfDaily);
    }

    public DateClass getZeroDateOfWeekly() {
        return zeroDateOfWeekly;
    }

    public void setZeroDateOfWeekly(String zeroDateOfWeekly) {
        this.zeroDateOfWeekly = new DateClass(zeroDateOfWeekly);
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
                ", dateOfBirth=" + dateOfBirth.getDate() +
                ", gender=" + tempGender +
                ", stepsStartWeekly=" + stepsStartWeekly +
                ", stepsStartMonthly=" + stepsStartMonthly +
                ", stepsStartDaily=" + stepsStartDaily +
                ", zeroDateOfMonthly=" + zeroDateOfMonthly.getDate() +
                ", zeroDateOfDaily=" + zeroDateOfDaily.getDate() +
                ", zeroDateOfWeekly=" + zeroDateOfWeekly.getDate() +
                '}';
    }
}
