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

package in.co.recex.detainseventyfive.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHandler {

    // All Static variables
    // Database Version

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    private SQLiteDatabase database;
    private DatabaseCreator dbhelper;

    public DatabaseHandler(Context context)
    {
        dbhelper= new DatabaseCreator(context);
    }

    public void open()
    {
        database=dbhelper.getWritableDatabase();
    }

    public void close()
    {
        dbhelper.close();
    }
    public void addCourse(String name, Integer mon, Integer tue, Integer wed, Integer thu, Integer fri, Integer sat, Integer sun) {

        ContentValues values = new ContentValues();
        values.put(DatabaseCreator.KEY_COURSE_NAME, name);
        values.put(DatabaseCreator.KEY_MON, mon);
        values.put(DatabaseCreator.KEY_TUE, tue);
        values.put(DatabaseCreator.KEY_WED, wed);
        values.put(DatabaseCreator.KEY_THU, thu);
        values.put(DatabaseCreator.KEY_FRI, fri);
        values.put(DatabaseCreator.KEY_SAT, sat);
        values.put(DatabaseCreator.KEY_SUN, sun);
        // Inserting Row
        database.insert(DatabaseCreator.TABLE_SCHEDULE, null, values);

        database.close(); // Closing database connection
        Log.d("DB1", "Added Course");
    }

    public int[] getSchedule(String name){
        String q="SELECT * FROM Schedule WHERE course IS \""+name+"\"";
        Cursor cursor;
        cursor=database.rawQuery(q, null);
        cursor.moveToFirst();
        int mon=cursor.getInt(cursor.getColumnIndex("mon"));
        int tue=cursor.getInt(cursor.getColumnIndex("tue"));
        int wed=cursor.getInt(cursor.getColumnIndex("wed"));
        int thu=cursor.getInt(cursor.getColumnIndex("thu"));
        int fri=cursor.getInt(cursor.getColumnIndex("fri"));
        int sat=cursor.getInt(cursor.getColumnIndex("sat"));
        int sun=cursor.getInt(cursor.getColumnIndex("sun"));
        cursor.close();
        int[] schedule=new int[]{mon, tue, wed, thu, fri, sat, sun};
        return schedule;
    }

    public ArrayList<String> getName(){
        String q="SELECT * FROM Schedule";
        ArrayList<String> nameArr=new ArrayList<String>();
        Cursor cursor;
        cursor=database.rawQuery(q, null);
        cursor.moveToFirst();
        int i=0;
        while(!cursor.isAfterLast()){
            nameArr.add(cursor.getString(cursor.getColumnIndex("course")));
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        return nameArr;
    }

    public void deleteScheduleTable(){
        database.delete(DatabaseCreator.TABLE_SCHEDULE, null, null);
    }


    public ArrayList<String> displayTodayCourses(String day)
    {
        String q="SELECT * FROM Schedule";
        Cursor cursor=database.rawQuery(q, null);
        cursor.moveToFirst();
        ArrayList<String> array=new ArrayList<String>();
        while(!cursor.isAfterLast())
        {
            int classno=cursor.getInt(cursor.getColumnIndex(day));
            while(classno>0)
            {
                String classname=cursor.getString(cursor.getColumnIndex("course"));
                array.add(classname);
                classno--;
            }
            cursor.moveToNext();
        }

        cursor.close();

        return array;
    }
    public int getCount(String day){
        String q="SELECT * FROM Schedule";
        Cursor cursor=database.rawQuery(q, null);
        cursor.moveToFirst();
        int count=0;
        while(!cursor.isAfterLast()){
            int classno=cursor.getInt(cursor.getColumnIndex(day));
            while(classno>0)
            {
                count++;
                classno--;
            }
            cursor.moveToNext();
        }
        return count;
    }

}