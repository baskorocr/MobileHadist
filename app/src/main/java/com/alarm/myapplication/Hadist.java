package com.alarm.myapplication;

public class Hadist {

    private String arab;
    private String id;
    private String number;

    public Hadist(){}
    public Hadist(String Arab, String Id, String Number){
        this.arab = Arab;
        this.id = Id;
        this.number = Number;
    }

    public String getArab(){return arab;}
    public void setArab(String arab) {
        this.arab = arab;
    }
    public String getId(){return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber(){return number;}

    public void setNumber(String number) {
        this.number = number;
    }
}
