/*
 * Copyright (c) 2013. Sopandev Tewari, Recex
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.co.recex.detainseventyfive;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import in.co.recex.detainseventyfive.Utils.Database2Handler;
import in.co.recex.detainseventyfive.Utils.DatabaseHandler;
import in.co.recex.detainseventyfive.Utils.SemDataHandler;

public class HomeScreenActivity extends Activity {
    Database2Handler db = new Database2Handler(this);
    DatabaseHandler db1=new DatabaseHandler(this);
    SemDataHandler db3=new SemDataHandler(this);
    AlarmManager alarmManager;
    Calendar calendar;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        boolean first = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("first", true);
        if (first){
            Intent intent = new Intent(getApplicationContext(), DialogShowActivity.class);
            startActivity(intent);
            // Save the state
            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("first", false)
                    .commit();

        }
        db.open();
        ListView eListView;
        eListView = (ListView)findViewById(R.id.listView);
        Log.d("HomeScreen", "Here1");
        final ArrayList<HashMap<String, String>>data = db.displayCourses();
        ListAdapter adapter = new SimpleAdapter(HomeScreenActivity.this, data, R.layout.view_listrecord, new String[]{"CourseNameView", "CoursePercentage"}, new int[]{R.id.CourseNameView, R.id.CoursePercentage});
        eListView.setAdapter(adapter);
        Log.d("HomeScreen", "Here1");
        db.close();

        db3.open();
        try{
            int[] time=db3.readTime();
            calendar=Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, time[0]);
            calendar.set(Calendar.MINUTE, time[1]);
            calendar.set(Calendar.SECOND, 0);
        }
        catch(Exception e)
        {
            calendar=Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        }

        long timeToAlarm=calendar.getTimeInMillis();
        if(calendar.getTimeInMillis()<System.currentTimeMillis())
        {
            timeToAlarm += (24*60*60*1000);
        }
            Intent myIntent = new Intent(HomeScreenActivity.this, BroadcastRec.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(HomeScreenActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeToAlarm, 24*60*60*1000, pendingIntent);




            Log.d("HomeScreen", "Alarm");
        eListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    HashMap<String, String> map = data.get(i);
                    String name=map.get("CourseNameView");
                    Intent intent = new Intent(HomeScreenActivity.this, DetailShowActivity.class);
                    Bundle b = new Bundle();
                    b.putString("name", name);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }*/

    public void AddNewClick(View view){
        Intent intent = new Intent(HomeScreenActivity.this, NewCourseActivity.class);
        startActivity(intent);
    }

    public void ChangeEndDate(View v){
        Intent intent=new Intent(HomeScreenActivity.this, ChangeEndDate.class);
        startActivity(intent);
    }

    public void NewSem(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                HomeScreenActivity.this);

        // set title
        alertDialogBuilder.setTitle("Warning");

        // set dialog message
        alertDialogBuilder
                .setMessage("This will erase all data. Are you sure you want to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity

                        db.open();
                        db1.open();
                        db3.open();
                        db.deleteProgressTable();
                        db1.deleteScheduleTable();
                        db3.deleteSemTable();
                        Intent intent=new Intent(HomeScreenActivity.this, DialogShowActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
