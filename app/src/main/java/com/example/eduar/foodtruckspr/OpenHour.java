package com.example.eduar.foodtruckspr;

import java.util.UUID;

public class OpenHour {

    private UUID id;
    private int fromDay;
    private int toDay;
    private int fromHour;
    private int toHour;
    private int fromMinute;
    private int toMinute;
    private int fromAPM;
    private int toAPM;
    private boolean isInitialized;

    public OpenHour(){
        this.fromDay = -1;
        this.toDay = -1;
        this.fromHour = -1;
        this.toHour = -1;
        this.fromMinute = -1;
        this.toMinute = -1;
        this.fromAPM = -1;
        this.toAPM = -1;
        this.isInitialized = false;
        this.id = UUID.randomUUID();
    }

    public int getFromDay() {
        return fromDay;
    }

    public void setFromDay(int fromDay) {
        this.fromDay = fromDay;
    }

    public int getToDay() {
        return toDay;
    }

    public void setToDay(int toDay) {
        this.toDay = toDay;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getToHour() {
        return toHour;
    }

    public void setToHour(int toHour) {
        this.toHour = toHour;
    }

    public int getFromMinute() {
        return fromMinute;
    }

    public void setFromMinute(int fromMinute) {
        this.fromMinute = fromMinute;
    }

    public int getToMinute() {
        return toMinute;
    }

    public void setToMinute(int toMinute) {
        this.toMinute = toMinute;
    }

    public int getFromAPM() {
        return fromAPM;
    }

    public void setFromAPM(int fromAPM) {
        this.fromAPM = fromAPM;
    }

    public int getToAPM() {
        return toAPM;
    }

    public void setToAPM(int toAPM) {
        this.toAPM = toAPM;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    @Override
    public String toString(){
        return fromDay+"-"+toDay+"\n"+"Opens: "+fromHour+":"+fromMinute+" "+fromAPM+"\n"+"Closes: "+toHour+":"+toMinute+" "+toAPM;
    }

    @Override
    public boolean equals(Object op){
        try{
            OpenHour that = (OpenHour) op;
            if(this.id.equals(that.id)) return true;
            else return false;
        } catch (Exception e){
            return false;
        }
    }
}
