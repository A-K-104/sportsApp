package com.example.sportsapp;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


public class DateClass implements Serializable {
    private int day;
    private int month;
    private int year;

    public  DateClass(String stringDate) {
        setDate(stringDate);
        resetToDate();
    }

    public DateClass(int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
        resetToDate();
    }

    public void setDate(@NotNull String stringDate) {
        if (stringDate.indexOf("/") != -1) {
            if (stringDate.substring(stringDate.indexOf("/")).indexOf("/") != -1) {
                this.day = Integer.parseInt(stringDate.substring(0, stringDate.indexOf("/")));
                stringDate = stringDate.substring(stringDate.indexOf("/") + 1);
                this.month = Integer.parseInt(stringDate.substring(0, stringDate.indexOf("/")));
                this.year = Integer.parseInt(stringDate.substring(stringDate.indexOf("/") + 1));
            }
            else {
                this.day =1;
                this.month =1;
                this.year=1900;
            }
        }
        else {
            this.day =1;
            this.month =1;
            this.year=1900;
        }
        resetToDate();
    }
    private void resetToDate() {//reset the date to make sure that it is in the lines of date (like: 32/13/2020-->01/02/2021)
        while (this.month > 12) {
            this.year++;
            this.month -= 12;
        }
        while (this.day > 31) {
            if (this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7 || this.month == 8 || this.month == 10 || this.month == 12) {
                if (this.day > 31) {
                    this.month++;
                    this.day -= 31;
                }
            } else {
                if (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
                    if (this.day > 30) {
                        this.month++;
                        this.day -= 30;
                    }
                } else {
                    if (this.month == 2)
                        if ((this.year) % 4 == 0) {
                            if (this.day > 29) {
                                this.month++;
                                this.day -= 29;
                            }
                        } else {
                            if (this.day > 28) {
                                this.month++;
                                this.day -= 28;
                            }
                        }
                }
            }
            if (this.month > 12) {
                this.year++;
                this.month -= 12;
            }
        }
        if (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
            if (this.day > 30) {
                this.month++;
                this.day -= 30;
            }
        } else {
            if (this.month == 2)
                if ((this.year) % 4 == 0) {
                    if (this.day > 29) {
                        this.month++;
                        this.day -= 29;
                    }
                } else {
                    if (this.day > 28) {
                        this.month++;
                        this.day -= 28;
                    }
                }
        }
        if (this.month > 12) {
            this.year++;
            this.month -= 12;
        }
        resetToDateZero();
    }
    private void resetToDateZero() {//reset the date to make sure that it is in the lines of date (like: 32/13/2020-->01/02/2021)
        while (this.day < 0) {

            if (this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7 || this.month == 8 || this.month == 10 || this.month == 12) {
                this.month--;
                this.day = 31;
            } else {
                if (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
                    this.month--;
                    this.day = 30;
                } else {
                    if (this.month == 2)
                        if ((this.year) % 4 == 0) {
                            this.month--;
                            this.day = 29;
                        } else {
                            this.month--;
                            this.day = 28;
                        }
                }
            }
            if (this.month == 0) {
                this.year--;
                this.month = 12;
            }
        }
    }
    public String getDate() {
        return day +
                "/" + month +
                "/" + year;
    }
    private int convertMonthIntoDays(int month){
        int days=0;
        while (month>0) {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                days += 31;
            } else {
                if (month == 4 || month == 6 || month == 9 || month == 11) {
                    days += 30;
                } else {
                    if (month == 2)
                        days += 28;
                }
            }
            month--;
        }
        return days;
    }
    public Boolean after(@NotNull DateClass dateClass){
        int compere= 365*this.year;
        int current = 365*dateClass.year;
        compere += this.year/4;
        current +=dateClass.year/4;
        compere+= this.day;
        current+= dateClass.day;
        compere+=convertMonthIntoDays(this.month);
        current+=convertMonthIntoDays(dateClass.month);
        if(compere<current)
        {
            return true;
        }
        return false;
    }
    public Boolean afterDay(@NotNull DateClass dateClass,int plusDays){
        return after(new DateClass(dateClass.year,dateClass.month,dateClass.day-plusDays));
    }
    public Boolean afterMonth(@NotNull DateClass dateClass, int plusMonth){
        return after(new DateClass(dateClass.year,dateClass.month-plusMonth,(dateClass.day+1)));
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        resetToDate();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        resetToDate();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "DateClass{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
