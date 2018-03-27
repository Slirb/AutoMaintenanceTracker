package com.example.vance.automaintenancetracker;

/**
 * Created by Vance on 11/29/2015.
 */


public class Repair {

    private int id;
    private int vehicleID;
    private String name;
    private double cost;
    private String date; //date must be stored as YYYY-MM-DD to work correctly with database
    private double mileage;
    private String repairer;


    //Constructors



    //Get Data
    public int getId(){
        return id;
    }

    public int getVehicleID(){
        return vehicleID;
    }

    public String getName(){
        return name;
    }

    public double getCost(){
        return cost;
    }

    public String getDate(){
        return date;
    }

    public double getMileage(){
        return mileage;
    }

    public String getRepairer(){
        return repairer;
    }

    //Set Data
    public void setId(int id){
        this.id=id;
    }

    public void setVehicleID(int id){
        this.vehicleID=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setCost(double cost){
        this.cost=cost;
    }

    public void setDate(String date){
        this.date=date;
    }

    public void setMileage(double miles){
        this.mileage=miles;
    }

    public void setRepairer(String repairer){
        this.repairer=repairer;
    }

}
