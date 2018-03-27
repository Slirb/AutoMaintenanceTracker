package com.example.vance.automaintenancetracker;

/**
 * Created by Vance on 12/7/2015.
 */

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class myListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Repair> list = new ArrayList<Repair>();
    private Context context;
    private int idNum;
    private int vID;
    private String make;



    public myListAdapter(ArrayList<Repair> list, Context context, String make) {
        this.list = list;
        this.context = context;
        this.make=make;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_layout, null);
        }
        idNum = list.get(position).getId();
        vID=list.get(position).getVehicleID();

        final TextView idtext=(TextView)view.findViewById(R.id.idTextView);
        idtext.setText(String.valueOf(list.get(position).getId()));

        //Handle TextView and display string from your list
        TextView name = (TextView)view.findViewById(R.id.nameTextView);
        name.setText(list.get(position).getName());

        TextView cost = (TextView)view.findViewById(R.id.costTextView);
        cost.setText(String.valueOf(list.get(position).getCost()));

        TextView date = (TextView)view.findViewById(R.id.dateTextView);

        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date time = sdf.parse(list.get(position).getDate());
            sdf = new SimpleDateFormat("MM-dd-yy");
            String dateString = sdf.format(time);
            date.setText(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        TextView mileage = (TextView)view.findViewById(R.id.mileageTextView);
        mileage.setText(String.valueOf(list.get(position).getMileage()));

        TextView repairer = (TextView)view.findViewById(R.id.repairerTextView);
        repairer.setText(list.get(position).getRepairer());


        //Handle buttons and add onClickListeners
        Button del = (Button)view.findViewById(R.id.deleteButton);
        Button edit = (Button)view.findViewById(R.id.editButton);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                dbHandler dbHandler= new dbHandler(context,null,null,1);
                dbHandler.deleteRepair(idNum);
                notifyDataSetChanged();
                ((DisplayRepairsActivity)context).loadList();

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                Intent i = new Intent(context, RepairsActivity.class);
                i.putExtra("vehicle_id",vID);
                i.putExtra("id", idtext.getText());
                i.putExtra("vehicle_make", make);
                context.startActivity(i);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}