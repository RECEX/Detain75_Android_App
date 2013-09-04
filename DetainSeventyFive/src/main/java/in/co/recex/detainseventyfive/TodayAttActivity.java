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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import in.co.recex.detainseventyfive.Utils.Database2Handler;
import in.co.recex.detainseventyfive.Utils.DatabaseHandler;

public class TodayAttActivity extends Activity {
    DatabaseHandler db=new DatabaseHandler(this);
    Database2Handler db2=new Database2Handler(this);
    public ArrayList<String> array;
    public int count=0;
    public AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todayatt);
        db.open();
        db2.open();
        Log.d("TAA", "CalObj");
        Calendar calendar=Calendar.getInstance();
        String day="123";
        if(calendar.get(Calendar.DAY_OF_WEEK)==1)
        {
            day="sun";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK)==2)
        {
            day="mon";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK)==3)
        {
            day="tue";
        }

        if(calendar.get(Calendar.DAY_OF_WEEK)==4)
        {
            day="wed";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK)==5)
        {
            day="thu";
        }
        if(calendar.get(Calendar.DAY_OF_WEEK)==6)
        {
            day="fri";
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK)==7)
        {
            day="sat";
        }
        Log.d("TAA", "CalObj");
        array=db.displayTodayCourses(day);
        Log.d("TAA", "array");
        count=db.getCount(day);
        Log.d("TAA", "count");
        if(count==0){
            Context context = getApplicationContext();
            CharSequence text = "You don't have any courses added!";
            int duration = Toast.LENGTH_SHORT;
            if(context!=null)
            {
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            Intent intent = new Intent(TodayAttActivity.this, HomeScreenActivity.class);
            startActivity(intent);
        }
        else{
            ShowDialog(0);
        }


    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.today_att, menu);
        return true;
    }*/


    @Override
    public void onBackPressed() {
    }

    public void ShowDialog(int i){
        alertDialog=new AlertDialog.Builder(TodayAttActivity.this);
        if(i==count-1)
        {
            // Setting Dialog Title
            alertDialog.setTitle(array.get(i));
            final int counter=i;
            // Setting Dialog Message
            alertDialog.setMessage("Enter today's attendance record for "+array.get(i));

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("Attended", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // User pressed YES button. Write Logic Here
                    db2.updateProgress(array.get(counter), 1);
                    dialog.dismiss();
                    Intent intent=new Intent(TodayAttActivity.this, HomeScreenActivity.class);
                    startActivity(intent);

                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("Not Attended", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // User pressed No button. Write Logic Here
                    db2.updateProgress(array.get(counter), 0);
                    dialog.dismiss();
                    Intent intent=new Intent(TodayAttActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                }
            });

            // Setting Netural "Cancel" Button
            alertDialog.setNeutralButton("Class Cancelled", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // User pressed Cancel button. Write Logic Here
                    db2.updateProgress(array.get(counter), -1);
                    dialog.dismiss();
                    Intent intent=new Intent(TodayAttActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                }
            });

            // Showing Alert Message
            alertDialog.show();
            Log.d("TAA", "LastDialog");
        }

        else{
            // Setting Dialog Title
            alertDialog.setTitle(array.get(i));
            final int counter=i;
            // Setting Dialog Message
            alertDialog.setMessage("Enter today's attendance record for "+array.get(i));

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("Attended", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // User pressed YES button. Write Logic Here
                    db2.updateProgress(array.get(counter), 1);
                    dialog.dismiss();
                    ShowDialog(counter + 1);
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("Not Attended", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // User pressed No button. Write Logic Here
                    db2.updateProgress(array.get(counter), 0);
                    dialog.dismiss();
                    ShowDialog(counter+1);
                }
            });

            // Setting Netural "Cancel" Button
            alertDialog.setNeutralButton("Class Cancelled", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // User pressed Cancel button. Write Logic Here
                    db2.updateProgress(array.get(counter), -1);
                    dialog.dismiss();
                    ShowDialog(counter+1);
                }
            });

            // Showing Alert Message
            alertDialog.show();
            Log.d("TAA", "DialogShow");
        }

    }
}
