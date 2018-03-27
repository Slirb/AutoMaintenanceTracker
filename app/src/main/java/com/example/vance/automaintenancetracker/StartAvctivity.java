//Auto Maintenance App
//Vance Curtis
//December 10 2015
//This app allows a user to maintain a record of vehicles and their corresponding repairs


package com.example.vance.automaintenancetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.ArrayList;


public class StartAvctivity extends AppCompatActivity {

    private ArrayList<Vehicle> vehicles;
    private Spinner myspin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_avctivity);

       loadSpinner();
    }

    public void loadSpinner(){
        myspin=(Spinner)findViewById(R.id.vehicleSpinner);

        vehicles=new ArrayList<Vehicle>();
        dbHandler dbHandler=new dbHandler(this,null,null,1);
        int numVehicles=dbHandler.vehicleCount();
        vehicles=dbHandler.listVehicles();


        if( vehicles.size() < 1)
        {
            List<String> spinnerArray =  new ArrayList<String>();
            spinnerArray.add("Please enter a vehicle to start");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, R.layout.custom_spinner, spinnerArray);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            myspin.setAdapter(adapter);
        }
        else
        {
            try {
                List<String> spinnerArray = new ArrayList<String>();


                for (Vehicle v : vehicles) {
                    spinnerArray.add(v.getMake().toString() + "\t" + v.getModel().toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, R.layout.custom_spinner, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                myspin.setAdapter(adapter);
            }
            catch (Exception e)
            {

            }
        }
    }



    public void newVehicle(View view){

        Intent i =new Intent(this,VehicleActivity.class);

        startActivity(i);

    }

    public void selectVehicle(View view){

        String selected=myspin.getSelectedItem().toString();
        int pos=selected.indexOf("\t", 0);
        String make= selected.substring(0, pos);
        String model= selected.substring(pos+1);
        Vehicle v=new Vehicle();


        for (Vehicle vtemp : vehicles){


            if(vtemp.getModel().equals(model) && vtemp.getMake().equals(make)) {
                v = vtemp;

                Intent i = new Intent(this, DisplayRepairsActivity.class);
                i.putExtra("vehicle_id", v.getId());
                i.putExtra("vehicle_make", v.getMake());
                startActivity(i);
            }

        }



    }

    public void editVehicle(View view){

        String selected=myspin.getSelectedItem().toString();
        int pos=selected.indexOf("\t", 0);
        String make= selected.substring(0, pos);
        String model= selected.substring(pos+1);
        Vehicle v=new Vehicle();


        for (Vehicle vtemp : vehicles){


            if(vtemp.getModel().equals(model) && vtemp.getMake().equals(make)) {
                v = vtemp;

                Intent i = new Intent(this, VehicleActivity.class);
                i.putExtra("id", v.getId());
                startActivity(i);
            }

        }

    }


    @Override
    public void onResume() {
        super.onResume();

        loadSpinner();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_avctivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Uri gmmIntentUri;
        Intent mapIntent;

        switch (item.getItemId()){
            case R.id.menu_dealerships:
                // Search for Dealerships nearby
                gmmIntentUri = Uri.parse("geo:0,0?q=dealerships");
                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                return true;
            case R.id.menu_autoparts:
                // Search for Autoparts stores nearby
                gmmIntentUri = Uri.parse("geo:0,0?q=auto%20parts");
                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
}
