package com.example.vance.automaintenancetracker;

/**
 * Created by Vance on 11/29/2015.
 */


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;


public class dbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "VehicleRecords.db";

    //Table names
    public static final String TABLE_VEHICLES = "vehicles";
    public static final String TABLE_REPAIRS = "repairs";


    //Vehicle table column names
    public static final String COLUMN_MAKE = "make";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_PURCHASED = "purchased";


    //Repairs table column names
    public static final String COLUMN_VEHICLE_ID = "vehicle_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_REPAIRER = "repairer";


    //Shared column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_MILEAGE = "mileage";



    public dbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        //Create Vehicles table
        String CREATE_VEHICLES_TABLE = "CREATE TABLE " + TABLE_VEHICLES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_MAKE +
                " TEXT," + COLUMN_MODEL + " TEXT," + COLUMN_YEAR + " INTEGER," + COLUMN_COLOR + " TEXT," + COLUMN_MILEAGE + " REAL," + COLUMN_PURCHASED
                + " TEXT," + COLUMN_COST + " REAL" + ")";
        db.execSQL(CREATE_VEHICLES_TABLE);

        //Enable foreign key support in database
        db.execSQL("PRAGMA foreign_keys=ON;");

        //Create Repairs table
        String CREATE_REPAIRS_TABLE = "CREATE TABLE " + TABLE_REPAIRS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_VEHICLE_ID +
                " INTEGER," + COLUMN_NAME + " TEXT," + COLUMN_COST + " REAL," + COLUMN_DATE + " TEXT," + COLUMN_MILEAGE + " REAL," + COLUMN_REPAIRER
                + " TEXT," + "FOREIGN KEY(" + COLUMN_VEHICLE_ID + ") REFERENCES " + TABLE_VEHICLES + "(" + COLUMN_ID + ") ON DELETE CASCADE" + ")";
        db.execSQL(CREATE_REPAIRS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPAIRS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        onCreate(db);
    }


    //Add a new vehicle_activity to the database
    public void addVehicle(Vehicle vehicle){

        ContentValues values = new ContentValues();
        values.put(COLUMN_MAKE, vehicle.getMake());
        values.put(COLUMN_MODEL, vehicle.getModel());
        values.put(COLUMN_YEAR, vehicle.getYear());
        values.put(COLUMN_COLOR, vehicle.getColor());
        values.put(COLUMN_MILEAGE, vehicle.getMileage());
        values.put(COLUMN_PURCHASED, vehicle.getPurcahsed());
        values.put(COLUMN_COST, vehicle.getCost());


        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_VEHICLES, null, values);
        db.close();
    }

    //Add a new repair to the database
    public void addRepair(Repair repair){

        ContentValues values= new ContentValues();
        values.put(COLUMN_VEHICLE_ID, repair.getVehicleID());
        values.put(COLUMN_NAME, repair.getName());
        values.put(COLUMN_COST, repair.getCost());
        values.put(COLUMN_DATE, repair.getDate());
        values.put(COLUMN_MILEAGE, repair.getMileage());
        values.put(COLUMN_REPAIRER, repair.getRepairer());


        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_REPAIRS, null, values);
        db.close();
    }

    public int vehicleCount(){

        int count=0;
        String countq = "SELECT COUNT(*) FROM " + TABLE_VEHICLES + "";

        SQLiteDatabase cdb = this.getWritableDatabase();

        Cursor countcursor = cdb.rawQuery(countq, null);
        if(countcursor.moveToFirst())
        {

            count=Integer.parseInt(countcursor.getString(0));

        }
        else
        {
            count=1;
        }
        countcursor.close();
        return count;
    }

    //Returns a list of all vehicles in the database
    public ArrayList<Vehicle> listVehicles(){

        String query = "SELECT * FROM " + TABLE_VEHICLES + "";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Vehicle> vehicle=new ArrayList<Vehicle>();


        try
        {
            while(cursor.moveToNext())
            {
                Vehicle temp= new Vehicle();
                temp.setId(Integer.parseInt(cursor.getString(0)));
                temp.setMake(cursor.getString(1));
                temp.setModel(cursor.getString(2));
                vehicle.add(temp);


            }
        }
        catch (Exception e)
        {
            Vehicle temp=null;
            vehicle.add(temp);
        }

        cursor.close();
        db.close();

        return vehicle;

    }

    //Returns a list of repairs for the selected vehicle
    public ArrayList<Repair> listRepairs(int v_id){

        String query = "SELECT * FROM " + TABLE_REPAIRS + " WHERE " + COLUMN_VEHICLE_ID + " = \"" + v_id + "\" ";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Repair> repairs =new ArrayList<Repair>();



        try
        {
            while(cursor.moveToNext())
            {
                Repair temp= new Repair();
                temp.setId(cursor.getInt(0));
                temp.setVehicleID(cursor.getInt(1));
                temp.setName(cursor.getString(2));
                temp.setCost(cursor.getDouble(3));
                temp.setDate(cursor.getString(4));
                temp.setMileage(cursor.getDouble(5));
                temp.setRepairer(cursor.getString(6));

                repairs.add(temp);


            }
        }
        catch (Exception e)
        {
            Repair temp=null;
            repairs.add(temp);
        }

        cursor.close();
        db.close();

        return repairs;

    }

    //Returns a specific vehicle
    public Vehicle findVehicle(int ID){

        String query = "SELECT * FROM " + TABLE_VEHICLES + " WHERE " + COLUMN_ID + " = \"" + ID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Vehicle vehicle = new Vehicle();



        if(cursor.moveToFirst())
        {
            cursor.moveToFirst();
            Vehicle temp= new Vehicle();
            temp.setId(Integer.parseInt(cursor.getString(0)));
            temp.setMake(cursor.getString(1));
            temp.setModel(cursor.getString(2));
            temp.setYear(cursor.getInt(3));
            temp.setColor(cursor.getString(4));
            temp.setMileage(cursor.getDouble(5));
            temp.setPurcahsed(cursor.getString(6));
            temp.setCost(cursor.getDouble(7));

            vehicle=temp;



        }
        else
        {
            vehicle=null;
        }

        cursor.close();
        db.close();
        return vehicle;

    }

    //Returns a specific repair
    public Repair findRepair(int ID){

        String query = "SELECT * FROM " + TABLE_REPAIRS + " WHERE " + COLUMN_ID + " = \"" + ID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Repair repair = new Repair();



        if(cursor.moveToFirst())
        {
            cursor.moveToFirst();
            Vehicle temp= new Vehicle();
            repair.setId(Integer.parseInt(cursor.getString(0)));
            repair.setVehicleID(cursor.getInt(1));
            repair.setName(cursor.getString(2));
            repair.setCost(cursor.getDouble(3));
            repair.setDate(cursor.getString(4));
            repair.setMileage(cursor.getDouble(5));
            repair.setRepairer(cursor.getString(6));


        }
        else
        {
            repair=null;
        }

        cursor.close();
        db.close();
        return repair;

    }


    //Updates a vehicle
    public void updateVehicle(Vehicle v){

        ContentValues values = new ContentValues();
        values.put(COLUMN_MAKE, v.getMake());
        values.put(COLUMN_MODEL, v.getModel());
        values.put(COLUMN_YEAR, v.getYear());
        values.put(COLUMN_COLOR, v.getColor());
        values.put(COLUMN_MILEAGE, v.getMileage());
        values.put(COLUMN_PURCHASED, v.getPurcahsed());
        values.put(COLUMN_COST, v.getCost());


        SQLiteDatabase db = this.getWritableDatabase();

        db.update(TABLE_VEHICLES, values, COLUMN_ID + " = " + v.getId(), null);
        db.close();

    }


    //Updates a repair
    public void updateRepair(Repair r){

        ContentValues values= new ContentValues();
        values.put(COLUMN_VEHICLE_ID, r.getVehicleID());
        values.put(COLUMN_NAME, r.getName());
        values.put(COLUMN_COST, r.getCost());
        values.put(COLUMN_DATE, r.getDate());
        values.put(COLUMN_MILEAGE, r.getMileage());
        values.put(COLUMN_REPAIRER, r.getRepairer());


        SQLiteDatabase db = this.getWritableDatabase();

        db.update(TABLE_REPAIRS, values, COLUMN_ID + " = " + r.getId(), null);
        db.close();

    }

    //Deletes a vehicle and its repairs from the database
    public boolean deleteVehicle(int ID){

        boolean result = false;

        String query = "SELECT * FROM " + TABLE_VEHICLES + " WHERE " + COLUMN_ID + " = \"" + ID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Vehicle vehicle= new Vehicle();

        if(cursor.moveToFirst()) {
            vehicle.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_VEHICLES, COLUMN_ID + " = ?", new String[]{String.valueOf(vehicle.getId())});
            cursor.close();
            result=true;
        }

        db.close();
        return result;
    }

    //Deletes a repair from the database
    public boolean deleteRepair(int ID){

        boolean result = false;

        String query = "SELECT * FROM " + TABLE_REPAIRS + " WHERE " + COLUMN_ID + " = \"" + ID + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Repair repair = new Repair();

        if(cursor.moveToFirst()) {
            repair.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_REPAIRS, COLUMN_ID + " = ?", new String[]{String.valueOf(repair.getId())});
            cursor.close();
            result=true;
        }

        db.close();
        return result;
    }

}

