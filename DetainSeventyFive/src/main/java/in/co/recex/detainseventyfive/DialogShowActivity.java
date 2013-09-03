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
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.co.recex.detainseventyfive.Utils.Database2Handler;
import in.co.recex.detainseventyfive.Utils.SemDataHandler;
import in.co.recex.detainseventyfive.Utils.UtilityFunctions;

public class DialogShowActivity extends Activity {
    SemDataHandler database= new SemDataHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogshow);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dialog_show, menu);
        return true;
    }*/

    @Override
    public void onBackPressed() {
    }


    public void OnSubmit(View v)
    {
        DatePicker semstart;
        semstart = (DatePicker) findViewById(R.id.datePicker);
        int startdate = semstart.getDayOfMonth();
        int startmonth = semstart.getMonth();
        int startyear = semstart.getYear();
        DatePicker semend;
        semend = (DatePicker) findViewById(R.id.datePicker2);
        int enddate = semend.getDayOfMonth();
        int endmonth = semend.getMonth();
        int endyear = semend.getYear();
        TimePicker workend = (TimePicker) findViewById(R.id.timePicker);
        int workhour = workend.getCurrentHour();
        int workminute = workend.getCurrentMinute();
        int totalcourses=0;
        database.open();
        database.addSemData(startyear, startmonth, startdate, endyear, endmonth, enddate, workhour, workminute, totalcourses);
        Intent intent = new Intent(DialogShowActivity.this, HomeScreenActivity.class);
        startActivity(intent);
        database.close();
    }



}
