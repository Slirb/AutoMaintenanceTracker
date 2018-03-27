package com.example.vance.automaintenancetracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ListView;


import java.util.ArrayList;



public class DisplayRepairsActivity extends AppCompatActivity{

    private int id;
    private String vehicle_make;
    private ArrayList<Repair> repairs;



    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repairs_display_activity);

        Bundle extras=getIntent().getExtras();
        if(extras==null)
        {
            return;
        }



        id=extras.getInt("vehicle_id");
        vehicle_make=extras.getString("vehicle_make");

        repairs=new ArrayList<Repair>();


        //generate list
        dbHandler dbHandler=new dbHandler(this,null,null,1);
        repairs= dbHandler.listRepairs(id);
        //instantiate custom adapter
        myListAdapter adapter = new myListAdapter(repairs,this, vehicle_make);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.listView);
        lView.setAdapter(adapter);

    }


    public void loadList(){
        repairs=new ArrayList<Repair>();


        //generate list
        dbHandler dbHandler=new dbHandler(this,null,null,1);
        repairs= dbHandler.listRepairs(id);
        //instantiate custom adapter
        myListAdapter adapter = new myListAdapter(repairs,this, vehicle_make);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.listView);
        lView.setAdapter(adapter);

    }

    public void newRepair(View view) {

        Intent i = new Intent(this, RepairsActivity.class);
        i.putExtra("vehicle_id", id);
        i.putExtra("vehicle_make",vehicle_make);
        startActivity(i);
    }

    public void vehicleSelection(View view){

        finish();
    }


    @Override
    public void onResume() {
        super.onResume();

        loadList();

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
                gmmIntentUri = Uri.parse("geo:0,0?q="+vehicle_make+"%20dealership");
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
