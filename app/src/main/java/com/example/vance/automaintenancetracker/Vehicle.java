package com.example.vance.automaintenancetracker;

/**
 * Created by Vance on 11/29/2015.
 */


public class Vehicle {
    private int id;
    private String make;
    private String model;
    private int year;
    private String color;
    private double mileage;
    private String purcahsed;
    private double cost;

    //Constructors




    //Get Data
    public int getId(){
        return id;
    }

    public String getMake(){
        return make;
    }

    public String getModel(){
        return model;
    }

    public int getYear(){
        return year;
    }

    public String getColor(){
        return color;
    }

    public double getMileage(){
        return mileage;
    }

    public String getPurcahsed(){
        return purcahsed;
    }

    public double getCost(){
        return cost;
    }

    //Set Data
    public void setId(int id){
        this.id=id;
    }

    public void setMake(String make){
        this.make=make;
    }

    public void setModel(String model){
        this.model=model;
    }

    public void setYear(int year){
        this.year=year;
    }

    public void setColor(String color){
        this.color=color;
    }

    public void setMileage(double miles){
        this.mileage=miles;
    }

    public void setPurcahsed(String purchased){
        this.purcahsed=purchased;
    }

    public void setCost(double cost){
        this.cost=cost;
    }


}
