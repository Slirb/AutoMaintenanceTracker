package com.example.vance.automaintenancetracker;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Vance on 12/2/2015.
 */
public class RepairsActivity extends AppCompatActivity{

    private int id;
    private String vehicle_make;
    EditText name;
    EditText cost;
    DatePicker date;
    EditText mileage;
    EditText repairer;
    private int repairIDNumber=-2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repairs_activity);

        name=(EditText)findViewById(R.id.nameEditText);
        cost=(EditText)findViewById(R.id.costEditText);
        date=(DatePicker)findViewById(R.id.datePicker);
        mileage=(EditText)findViewById(R.id.mileageEditText);
        repairer=(EditText)findViewById(R.id.repairerEditText);

        Bundle extras=getIntent().getExtras();
        if(extras==null)
        {
            return;
        }

        id=extras.getInt("vehicle_id");
        vehicle_make=extras.getString("vehicle_make");
        try{

            int repairID=Integer.valueOf(extras.getString("id"));
            if(repairID>0) {
                dbHandler dbHandler = new dbHandler(this, null, null, 1);
                Repair r = dbHandler.findRepair(repairID);

                name.setText(r.getName());
                cost.setText(Double.valueOf(r.getCost()).toString());

                mileage.setText(Double.valueOf(r.getMileage()).toString());
                repairer.setText(r.getRepairer());
                repairIDNumber = repairID;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date time = sdf.parse(r.getDate());
                date.updateDate(time.getYear(), time.getMonth(), time.getDay());

            }
        }
        catch (Exception e)
        {

        }
    }

    public void returnRecord(View view){
        finish();

    }

    public void exitRecord(View view){
        finish();
    }

    public void saveRepair(View view){

        Repair r= new Repair();

        r.setVehicleID(id);
        r.setName(name.getText().toString());
        r.setCost(Double.valueOf(cost.getText().toString()));

        Date temp= new Date(date.getYear(), date.getMonth(), date.getDayOfMonth());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(temp);

        r.setDate(dateString);
        r.setMileage(Double.valueOf(mileage.getText().toString()));
        r.setRepairer(repairer.getText().toString());

        dbHandler dbHandler=new dbHandler(this,null,null,1);

        if(repairIDNumber!=-2)
        {
            r.setId(repairIDNumber);
            dbHandler.updateRepair(r);
        }
        else
        {
            dbHandler.addRepair(r);
        }
        finish();

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