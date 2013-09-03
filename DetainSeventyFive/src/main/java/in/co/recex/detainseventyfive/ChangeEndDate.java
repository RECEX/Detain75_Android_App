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

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;

import java.util.ArrayList;

import in.co.recex.detainseventyfive.Utils.Database2Handler;
import in.co.recex.detainseventyfive.Utils.DatabaseHandler;
import in.co.recex.detainseventyfive.Utils.SemDataHandler;
import in.co.recex.detainseventyfive.Utils.UtilityFunctions;

public class ChangeEndDate extends Activity {

    SemDataHandler database=new SemDataHandler(this);
    Database2Handler database2=new Database2Handler(this);
    DatabaseHandler database3=new DatabaseHandler(this);
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeenddate);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.change_end_date, menu);
        return true;
    }*/

    public void onChangeEnd(View v)
    {
        database.open();
        database2.open();
        database3.open();
        DatePicker semend;
        semend = (DatePicker) findViewById(R.id.semenddate);
        int enddate = semend.getDayOfMonth();
        int endmonth = semend.getMonth();
        int endyear = semend.getYear();
        Log.d("CED", "Date init");
        database.changeEndSemData(endyear, endmonth, enddate);
        int[] semstart=database.ReadSemData();
        Log.d("CED","RSD");
        ArrayList Name=database3.getName();
        Log.d("CED","GNA");
        int i=0;
        int totalcourses=database.readTotalCourses();
        while(i<totalcourses)
        {
        String name=(Name.get(i)).toString();
        int[] schedule=database3.getSchedule(name);
        int totalclasses=UtilityFunctions.ClassCount(semstart[0], semstart[1], semstart[2], endyear, endmonth, enddate,
                schedule[0], schedule[1], schedule[2], schedule[3], schedule[4], schedule[5], schedule[6]);
        database2.updateTotalonEndDateChange(name, totalclasses);
        i++;
        Log.d("CED", "LOOP");
        }
        database.close();
        database3.close();
        database2.close();
        Intent intent=new Intent(ChangeEndDate.this, HomeScreenActivity.class);
        startActivity(intent);
    }
    
}
