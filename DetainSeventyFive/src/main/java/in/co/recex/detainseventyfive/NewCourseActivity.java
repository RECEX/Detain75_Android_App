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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import in.co.recex.detainseventyfive.Utils.Database2Handler;
import in.co.recex.detainseventyfive.Utils.DatabaseHandler;
import in.co.recex.detainseventyfive.DialogShowActivity;
import in.co.recex.detainseventyfive.Utils.SemDataHandler;
import in.co.recex.detainseventyfive.Utils.UtilityFunctions;

public class NewCourseActivity extends Activity {

    DatabaseHandler database= new DatabaseHandler(this);
    Database2Handler database2= new Database2Handler(this);
    SemDataHandler database3=new SemDataHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcourse);
    }

    public void onNext(View v)
    {
        database.open();
        database2.open();
        database3.open();
        Log.d("NewCourse", "InitiateOnNext");
        EditText edtTxtName = (EditText) findViewById(R.id.name);
        EditText edtTxtMon = (EditText) findViewById(R.id.mon);
        EditText edtTxtTue = (EditText) findViewById(R.id.tue);
        EditText edtTxtWed = (EditText) findViewById(R.id.wed);
        EditText edtTxtThu = (EditText) findViewById(R.id.thu);
        EditText edtTxtFri = (EditText) findViewById(R.id.fri);
        EditText edtTxtSat = (EditText) findViewById(R.id.sat);
        EditText edtTxtSun = (EditText) findViewById(R.id.sun);
        EditText edtTxtM = (EditText) findViewById(R.id.mandatory);

        UtilityFunctions.name = edtTxtName.getText().toString();
        try {
            UtilityFunctions.mon = Integer.valueOf(edtTxtMon.getText().toString()).intValue();
        }
        catch(Exception e)
        {
            UtilityFunctions.mon=0;
        }
        try {
            UtilityFunctions.tue = Integer.valueOf(edtTxtTue.getText().toString()).intValue();
        }
        catch(Exception e)
        {
            UtilityFunctions.tue=0;
        }
        try {
            UtilityFunctions.wed = Integer.valueOf(edtTxtWed.getText().toString()).intValue();
        }
        catch(Exception e)
        {
            UtilityFunctions.wed=0;
        }
        try {
            UtilityFunctions.thu = Integer.valueOf(edtTxtThu.getText().toString()).intValue();
        }
        catch(Exception e)
        {
            UtilityFunctions.thu=0;
        }
        try {
            UtilityFunctions.fri = Integer.valueOf(edtTxtFri.getText().toString()).intValue();
        }
        catch(Exception e)
        {
            UtilityFunctions.fri=0;
        }
        try {
            UtilityFunctions.sat = Integer.valueOf(edtTxtSat.getText().toString()).intValue();
        }
        catch(Exception e)
        {
            UtilityFunctions.sat=0;
        }
        try {
            UtilityFunctions.sun = Integer.valueOf(edtTxtSun.getText().toString()).intValue();
        }
        catch(Exception e)
        {
            UtilityFunctions.sun=0;
        }
        try{
            UtilityFunctions.mandatory = Integer.valueOf(edtTxtM.getText().toString()).intValue();
        }
        catch (Exception e){
            UtilityFunctions.mandatory=75;
        }

        Log.d("NewCourse", "Conversion");
        int[] semdata=database3.ReadSemData();
        Log.d("NewCourse", "ClassCall");
        int totclass=UtilityFunctions.ClassCount(semdata[0], semdata[1], semdata[2], semdata[3], semdata[4], semdata[5],
                UtilityFunctions.mon , UtilityFunctions.tue, UtilityFunctions.wed,
                UtilityFunctions.thu, UtilityFunctions.fri, UtilityFunctions.sat, UtilityFunctions.sun);
        Log.d("NewCourse", "Calculation");
        database.addCourse(UtilityFunctions.name, UtilityFunctions.mon, UtilityFunctions.tue, UtilityFunctions.wed, UtilityFunctions.thu, UtilityFunctions.fri, UtilityFunctions.sat, UtilityFunctions.sun);
        int currenttotal=database3.readTotalCourses();
        currenttotal++;
        database3.updateTotalCourses(currenttotal);
        Log.d("NewCourse", "DB1");
        database2.addCourse(UtilityFunctions.name, totclass, 0, UtilityFunctions.mandatory);
        Log.d("NewCourse", "DB2");
        Calendar  calendar=Calendar.getInstance();
        int todaydate=calendar.get(Calendar.DAY_OF_MONTH);
        int todaymonth=calendar.get(Calendar.MONTH);
        int todayyear=calendar.get(Calendar.YEAR);
        database2.close();
        database.close();
        database3.close();
        if(todayyear==semdata[0]&&todaymonth==semdata[1]&&todaydate==semdata[2])
        {
            Context context = getApplicationContext();
            CharSequence text = "Your Semester starts today. Course Added!";
            int duration = Toast.LENGTH_SHORT;
            if(context!=null)
            {
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            Intent intent = new Intent(NewCourseActivity.this, HomeScreenActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(NewCourseActivity.this, NextScreenActivity.class);
            startActivity(intent);
        }
    }
}
