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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import in.co.recex.detainseventyfive.Utils.Database2Handler;
import in.co.recex.detainseventyfive.Utils.SemDataHandler;
import in.co.recex.detainseventyfive.Utils.UtilityFunctions;

public class NextScreenActivity extends Activity {
    public Integer classestillnow;
    Database2Handler db=new Database2Handler(this);
    SemDataHandler database=new SemDataHandler(this);

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextscreen);
        Log.d("NSA", "CodeShift");
        Calendar  calendar=Calendar.getInstance();
        int todaydate=calendar.get(Calendar.DAY_OF_MONTH);
        int todaymonth=calendar.get(Calendar.MONTH);
        int todayyear=calendar.get(Calendar.YEAR);
        Log.d("NSA", "GetTodayDate");
        database.open();
        int[] semdata=database.ReadSemData();
        classestillnow=UtilityFunctions.ClassCount(semdata[0], semdata[1], semdata[2], todayyear, todaymonth, todaydate,
                UtilityFunctions.mon , UtilityFunctions.tue, UtilityFunctions.wed,
                UtilityFunctions.thu, UtilityFunctions.fri, UtilityFunctions.sat, UtilityFunctions.sun);
        Log.d("NSA", "Calculation");
        TextView txt=(TextView) findViewById(R.id.classestill);
        txt.setText(""+classestillnow+"");
        Log.d("NSA", "Display");
    }

    public void SubmitButton(int alatt, int alcan)
    {

        db.open();
        db.writeCancel(UtilityFunctions.name, alcan);
        db.writeOver(UtilityFunctions.name, (classestillnow-alcan));
        db.writeAtt(UtilityFunctions.name, alatt);
        database.close();
        db.close();

        Context context = getApplicationContext();
        CharSequence text = "Course Added!";
        int duration = Toast.LENGTH_SHORT;
        if(context!=null)
        {
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        Intent intent = new Intent(NextScreenActivity.this, HomeScreenActivity.class);
        startActivity(intent);
    }

    public void BufferSubmit(View v)
    {

    EditText e=(EditText) findViewById(R.id.aldatt);
    Integer alatt=-1;
    EditText f=(EditText) findViewById(R.id.aldcan);
    Integer alcan=-1;
    try{
            alatt=Integer.valueOf(e.getText().toString()).intValue();
            alcan=Integer.valueOf(f.getText().toString()).intValue();
    }
    catch (Exception x){

        Context context = getApplicationContext();
        CharSequence text = "Please enter data into the empty fields!";
        int duration = Toast.LENGTH_SHORT;
        if(context!=null)
        {
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
    if(alcan!=-1&&alatt!=-1)
    {
    if(alatt>classestillnow||alcan>classestillnow||alcan>(classestillnow-alatt))
    {

        Context context = getApplicationContext();
        CharSequence text = "Please enter correct data!";
        int duration = Toast.LENGTH_SHORT;
        if(context!=null)
        {
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }
    else if(alcan==0&&alatt==0)
    {
            Context context = getApplicationContext();
            CharSequence text = "Please enter correct data!";
            int duration = Toast.LENGTH_SHORT;
            if(context!=null)
            {
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
    }
    else{
        SubmitButton(alatt, alcan);
    }
    }


}
}

