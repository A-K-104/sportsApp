package com.example.sportsapp;

import java.text.DecimalFormat;
import java.util.Date;

public class UserClass {
    private String firstName;
    private String lastName;
    private double weight;
    private double height;
    private Date dateOfBirth;
    private boolean gender;//male=true // female= false
    public UserClass(String name, String lastName, double weight, double height, Date dateOfBirth,boolean gender) {
        this.firstName = name;
        this.lastName = lastName;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isGender() {
        return gender;
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
                "name='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + tempGender +
                '}';
    }
}
