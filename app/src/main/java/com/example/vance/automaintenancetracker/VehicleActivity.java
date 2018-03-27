package com.example.vance.automaintenancetracker;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

/**
 * Created by Vance on 12/2/2015.
 */
public class VehicleActivity extends AppCompatActivity{

    EditText color;
    EditText cost;
    EditText make;
    EditText model;
    EditText mileage;
    EditText purchased;
    EditText year;
    int id=-2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_activity);


        color=(EditText)findViewById(R.id.colorEditText);
        cost=(EditText)findViewById(R.id.costEditText);
        make=(EditText)findViewById(R.id.makeEditText);
        model=(EditText)findViewById(R.id.modelEditText);
        mileage=(EditText)findViewById(R.id.mileageEditText);
        purchased=(EditText)findViewById(R.id.purchasedEditText);
        year=(EditText)findViewById(R.id.yearEditText);

        Bundle extras=getIntent().getExtras();
        if(extras == null)
        {
            return;
        }

        id=extras.getInt("id");

        dbHandler dbHandler=new dbHandler(this,null,null,1);
        Vehicle v= dbHandler.findVehicle(id);

        color.setText(v.getColor());
        cost.setText(Double.valueOf(v.getCost()).toString());
        make.setText(v.getMake());
        model.setText(v.getModel());
        mileage.setText(Double.valueOf(v.getMileage()).toString());
        purchased.setText(v.getPurcahsed());
        year.setText(String.valueOf(v.getYear()));
    }

    public void saveVehicle(View veiw){

        Vehicle v= new Vehicle();


        v.setColor(color.getText().toString());
        v.setCost(Double.valueOf(cost.getText().toString()));
        v.setMake(make.getText().toString());
        v.setModel(model.getText().toString());
        v.setMileage(Double.valueOf(mileage.getText().toString()));
        v.setPurcahsed(purchased.getText().toString());
        v.setYear(Integer.valueOf(year.getText().toString()));

        dbHandler dbHandler=new dbHandler(this,null,null,1);

        if(id!=-2)
        {
            v.setId(id);
            dbHandler.updateVehicle(v);

        }
        else
        {

            dbHandler.addVehicle(v);
        }
        finish();

    }
    public void deleteVehicle(View view){

        dbHandler dbHandler=new dbHandler(this,null,null,1);

        if(id!=-2) {
            dbHandler.deleteVehicle(id);
            finish();
        }
    }

    public void returnVehicle(View view){
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
