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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database2Handler {

    private SQLiteDatabase database;
    private Database2Creator db2helper;

    public Database2Handler(Context context)
    {
        db2helper= new Database2Creator(context);
    }

    public void open()
    {
        database=db2helper.getWritableDatabase();
    }

    public void close()
    {
        db2helper.close();
    }

    public void addCourse(String name, Integer total, Integer att, Integer mandatory) {
        ContentValues values = new ContentValues();
        values.put(Database2Creator.KEY_COURSE_NAME, name);
        values.put(Database2Creator.KEY_TOTAL, total);
        values.put(Database2Creator.KEY_ATT, att);
        values.put(Database2Creator.KEY_OVER, att);
        values.put(Database2Creator.KEY_CANCEL, att);
        values.put(Database2Creator.KEY_MANDATORY, mandatory);

        database.insert(Database2Creator.TABLE_PROGRESS, null, values);
        Log.d("DB2", "Course Added");
    }

    public void updateProgress(String name, Integer today)
    {
        Integer currentTotal=readTotal(name);
        Integer currentAtt=readAtt(name);
        Integer currentOver=readOver(name);
        Integer currentCancel=readCancel(name);
        Integer currentMandatory=readMandatory(name);
        ContentValues values = new ContentValues();
        if(today==-1)
        {
            values.put(Database2Creator.KEY_COURSE_NAME, name);
            values.put(Database2Creator.KEY_TOTAL, currentTotal-1);
            values.put(Database2Creator.KEY_ATT, currentAtt);
            values.put(Database2Creator.KEY_OVER, currentOver);
            values.put(Database2Creator.KEY_CANCEL, currentCancel+1);
            values.put(Database2Creator.KEY_MANDATORY, currentMandatory);
        }
        if(today==0)
        {
            values.put(Database2Creator.KEY_COURSE_NAME, name);
            values.put(Database2Creator.KEY_TOTAL, currentTotal);
            values.put(Database2Creator.KEY_ATT, currentAtt);
            values.put(Database2Creator.KEY_OVER, currentOver+1);
            values.put(Database2Creator.KEY_CANCEL, currentCancel);
            values.put(Database2Creator.KEY_MANDATORY, currentMandatory);
        }
        if(today==1)
        {
            values.put(Database2Creator.KEY_COURSE_NAME, name);
            values.put(Database2Creator.KEY_TOTAL, currentTotal);
            values.put(Database2Creator.KEY_ATT, currentAtt+1);
            values.put(Database2Creator.KEY_OVER, currentOver+1);
            values.put(Database2Creator.KEY_CANCEL, currentCancel);
            values.put(Database2Creator.KEY_MANDATORY, currentMandatory);
        }
        String q="course IS \""+name+"\"";
        database.update(Database2Creator.TABLE_PROGRESS, values, q, null);
        Log.d("DB2", "Course Updated");
    }

    public int readTotal(String name){
        Cursor cursor;
        String q="SELECT total FROM Progress WHERE course IS \""+name+"\"";
        cursor = database.rawQuery(q, null);
        Log.d("DB2", "Value Read");
        cursor.moveToFirst();
        int a=cursor.getInt(cursor.getColumnIndex("total"));
        return a;
    }

    public int readAtt(String name){
        Cursor cursor;
        String q="SELECT attended FROM Progress WHERE course IS \""+name+"\"";
        cursor = database.rawQuery(q, null);
        Log.d("DB2", "Value Read");
        cursor.moveToFirst();
            int a=cursor.getInt(cursor.getColumnIndex("attended"));
        return a;
    }
    public int readOver(String name){
        Cursor cursor;
        String q="SELECT over FROM Progress WHERE course IS \""+name+"\"";
        cursor = database.rawQuery(q,null);
        Log.d("DB2", "Value Read");
        cursor.moveToFirst();
        int a=cursor.getInt(cursor.getColumnIndex("over"));
        return a;
    }

    public int readCancel(String name){
        Cursor cursor;
        String q="SELECT cancel FROM Progress WHERE course IS \""+name+"\"";
        cursor = database.rawQuery(q,null);
        Log.d("DB2", "Value Read");
        cursor.moveToFirst();
        int a=cursor.getInt(cursor.getColumnIndex("cancel"));
        return a;
    }

    public int readMandatory(String name){
        Cursor cursor;
        String q="SELECT mandatory FROM Progress WHERE course IS \""+name+"\"";
        cursor=database.rawQuery(q, null);
        cursor.moveToFirst();
        int a=cursor.getInt(cursor.getColumnIndex("mandatory"));
        return a;
    }

    public void writeAtt(String name, Integer val){
        Integer currentTotal=readTotal(name);
        Integer currentAtt=readAtt(name);
        Integer currentOver=readOver(name);
        Integer currentCancel=readCancel(name);
        Integer currentMandatory=readMandatory(name);
        ContentValues values = new ContentValues();
        values.put(Database2Creator.KEY_COURSE_NAME, name);
        values.put(Database2Creator.KEY_TOTAL, currentTotal);
        values.put(Database2Creator.KEY_ATT, val);
        values.put(Database2Creator.KEY_OVER, currentOver);
        values.put(Database2Creator.KEY_CANCEL, currentCancel);
        values.put(Database2Creator.KEY_MANDATORY, currentMandatory);
        String e="course IS \""+name+"\"";
        database.update(Database2Creator.TABLE_PROGRESS, values, e ,null);
        Log.d("DB2", "Attended Updated");
    }

    public void writeCancel(String name, Integer val){
        Integer currentTotal=readTotal(name);
        Integer currentAtt=readAtt(name);
        Integer currentOver=readOver(name);
        Integer currentCancel=readCancel(name);
        Integer currentMandatory=readMandatory(name);
        ContentValues values = new ContentValues();
        values.put(Database2Creator.KEY_COURSE_NAME, name);
        values.put(Database2Creator.KEY_TOTAL, currentTotal);
        values.put(Database2Creator.KEY_ATT, currentAtt);
        values.put(Database2Creator.KEY_OVER, currentOver);
        values.put(Database2Creator.KEY_CANCEL, val);
        values.put(Database2Creator.KEY_MANDATORY, currentMandatory);
        String e="course IS \""+name+"\"";
        database.update(Database2Creator.TABLE_PROGRESS, values, e ,null);
        Log.d("DB2", "Cancel Updated");
    }

    public void writeOver(String name, Integer val){
        Integer currentTotal=readTotal(name);
        Integer currentAtt=readAtt(name);
        Integer currentOver=readOver(name);
        Integer currentCancel=readCancel(name);
        Integer currentMandatory=readMandatory(name);
        ContentValues values = new ContentValues();
        values.put(Database2Creator.KEY_COURSE_NAME, name);
        values.put(Database2Creator.KEY_TOTAL, currentTotal);
        values.put(Database2Creator.KEY_ATT, currentAtt);
        values.put(Database2Creator.KEY_OVER, val);
        values.put(Database2Creator.KEY_CANCEL, currentCancel);
        values.put(Database2Creator.KEY_MANDATORY, currentMandatory);
        String e="course IS \""+name+"\"";
        database.update(Database2Creator.TABLE_PROGRESS, values, e, null);
        Log.d("DB2", "Over Updated");
    }

    public void updateTotalonEndDateChange(String name, Integer val){
        Integer currentAtt=readAtt(name);
        Integer currentOver=readOver(name);
        Integer currentCancel=readCancel(name);
        Integer currentMandatory=readMandatory(name);
        int newTotal=val-currentCancel;
        ContentValues values = new ContentValues();
        values.put(Database2Creator.KEY_COURSE_NAME, name);
        values.put(Database2Creator.KEY_TOTAL, newTotal);
        values.put(Database2Creator.KEY_ATT, currentAtt);
        values.put(Database2Creator.KEY_OVER, currentOver);
        values.put(Database2Creator.KEY_CANCEL, currentCancel);
        values.put(Database2Creator.KEY_MANDATORY, currentMandatory);
        String e="course IS \""+name+"\"";
        database.update(Database2Creator.TABLE_PROGRESS, values, e, null);
    }
    public ArrayList<HashMap<String, String>> displayCourses()
    {
        String q="SELECT * FROM Progress";
        Cursor cursor=database.rawQuery(q, null);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("CourseNameView", cursor.getString(cursor.getColumnIndex("course")));
                map.put("CoursePercentage", " "+Integer.toString(percentcal(cursor.getString(cursor.getColumnIndex("course"))))+"%");
                list.add(map);
                cursor.moveToNext();
            }
        cursor.close();

        return list;
    }

    public int percentcal(String name)
    {
        String q1="SELECT attended FROM Progress WHERE course IS \""+name+"\"";
        String q2="SELECT over FROM Progress WHERE course IS \""+name+"\"";
        Cursor cursor;
        cursor = database.rawQuery(q1, null);
        cursor.moveToFirst();
        Integer attended=cursor.getInt(cursor.getColumnIndex("attended"));
        cursor.close();
        cursor = database.rawQuery(q2, null);
        cursor.moveToFirst();
        Integer over=cursor.getInt(cursor.getColumnIndex("over"));
        cursor.close();
        if(over>0)
        {
            return (attended*100)/over;
        }
        else
        return 100;
    }

    public void deleteProgressTable()
    {
        database.delete(Database2Creator.TABLE_PROGRESS, null, null);
    }

    public void deleteCourseProgress(String name){
        database.delete(Database2Creator.TABLE_PROGRESS, "course IS ?", new String[]{name});
    }
}