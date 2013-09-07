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
import android.widget.TextView;
import android.widget.Toast;

import in.co.recex.detainseventyfive.Utils.Database2Handler;
import in.co.recex.detainseventyfive.Utils.DatabaseHandler;
import in.co.recex.detainseventyfive.Utils.SemDataHandler;

public class DetailShowActivity extends Activity {
    Database2Handler db2= new Database2Handler(this);
    DatabaseHandler db=new DatabaseHandler(this);

    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailshow);
        Intent last=getIntent();
        Bundle b = last.getExtras();
        Log.d("DSA", "GotBundle");
        if(b!=null)
        {
        name=b.getString("name");
            Log.d("DSA", "GotString");
        }
        db2.open();
        int att=db2.readAtt(name);
        int over=db2.readOver(name);
        int total=db2.readTotal(name);
        int cancelled=db2.readCancel(name);
        int left=(total-over)-cancelled;
        int mandatory=db2.readMandatory(name);
        int suggestion;
        float fraction=(float) mandatory/100;
        float var=fraction*(total-cancelled);
        suggestion=Math.round(var) - att;
        if(suggestion<0)
        {
            suggestion=0;
        }
        if(suggestion>left)
        {

        }
        TextView txtname=(TextView) findViewById(R.id.CourseNameDetail);
        txtname.setText(name);
        TextView attended=(TextView) findViewById(R.id.AttendedDetail);
        attended.setText(Integer.toString(att));
        TextView overtext=(TextView) findViewById(R.id.OverDetail);
        overtext.setText(Integer.toString(over));
        TextView lefttext=(TextView) findViewById(R.id.LeftDetail);
        lefttext.setText(Integer.toString(left));
        TextView mandatorytext=(TextView) findViewById(R.id.Mandat);
        mandatorytext.setText(Integer.toString(mandatory));
        TextView suggtext=(TextView) findViewById(R.id.suggestion);
        suggtext.setText(Integer.toString(suggestion));
        TextView suggest=(TextView) findViewById(R.id.textView5);
        if(suggestion>left)
        {
            suggest.setText("You cannot achieve mandatory attendance anymore. ");
            suggtext.setText("");
        }

        db2.close();
    }

   /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_show, menu);
        return true;
    }*/

    public void onBack(View v)
    {
        Intent intent= new Intent(DetailShowActivity.this, HomeScreenActivity.class);
        startActivity(intent);
    }

    public void onDelete(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailShowActivity.this);

        // set title
        alertDialogBuilder.setTitle("Confirmation");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to delete this course?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        db.open();
                        db2.open();
                        db.deleteCourse(name);
                        db2.deleteCourseProgress(name);
                        db.close();
                        db2.close();
                        Context context = getApplicationContext();
                        CharSequence text = "Course Deleted!";
                        int duration = Toast.LENGTH_SHORT;
                        if(context!=null)
                        {
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                        Intent intent = new Intent(DetailShowActivity.this, HomeScreenActivity.class);
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
