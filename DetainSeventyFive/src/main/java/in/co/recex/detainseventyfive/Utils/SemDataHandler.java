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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sopandev on 27/8/13.
 */
public class SemDataHandler {

    private SQLiteDatabase database;
    private SemDataCreator semdbhelper;

    public SemDataHandler(Context context)
    {
        semdbhelper= new SemDataCreator(context);
    }

    public void open()
    {
        database=semdbhelper.getWritableDatabase();
    }

    public void close()
    {
        semdbhelper.close();
    }

    public void addSemData(int startyear, int startmonth, int startdate, int endyear, int endmonth, int enddate, int endhour, int endminute, int total)
    {
        ContentValues values = new ContentValues();
        values.put(SemDataCreator.KEY_START_YEAR, startyear);
        values.put(SemDataCreator.KEY_START_MONTH, startmonth);
        values.put(SemDataCreator.KEY_START_DATE, startdate);
        values.put(SemDataCreator.KEY_END_YEAR, endyear);
        values.put(SemDataCreator.KEY_END_MONTH, endmonth);
        values.put(SemDataCreator.KEY_END_DATE, enddate);
        values.put(SemDataCreator.KEY_END_HOUR, endhour);
        values.put(SemDataCreator.KEY_END_MINUTE, endminute);
        values.put(SemDataCreator.KEY_TOTAL_COURSES, total);
        database.insert(SemDataCreator.TABLE_SEMDATA, null, values);
    }

    public int[] ReadSemData()
    {
        Cursor cursor;
        String q="SELECT * FROM semesterdata";
        cursor=database.rawQuery(q, null);
        cursor.moveToFirst();
        int startyear=cursor.getInt(cursor.getColumnIndex("startyear"));
        int startmonth=cursor.getInt(cursor.getColumnIndex("startmonth"));
        int startdate=cursor.getInt(cursor.getColumnIndex("startdate"));
        int endyear=cursor.getInt(cursor.getColumnIndex("endyear"));
        int endmonth=cursor.getInt(cursor.getColumnIndex("endmonth"));
        int enddate=cursor.getInt(cursor.getColumnIndex("enddate"));
        int[] data= new int[]{startyear, startmonth, startdate, endyear, endmonth, enddate};
        cursor.close();
        return data;
    }
    public void changeEndSemData(int endyear, int endmonth, int enddate)
    {
        int[] startdate=ReadSemData();
        int year=startdate[0];
        int month=startdate[1];
        int date=startdate[2];

        ContentValues values=new ContentValues();
        values.put(SemDataCreator.KEY_START_YEAR, year);
        values.put(SemDataCreator.KEY_START_MONTH, month);
        values.put(SemDataCreator.KEY_START_DATE, date);
        values.put(SemDataCreator.KEY_END_YEAR, endyear);
        values.put(SemDataCreator.KEY_END_MONTH, endmonth);
        values.put(SemDataCreator.KEY_END_DATE, enddate);
        database.update(SemDataCreator.TABLE_SEMDATA, values, null ,null);
    }

    public int readTotalCourses()
    {
        Cursor cursor;
        String q="SELECT * FROM semesterdata";
        cursor=database.rawQuery(q, null);
        cursor.moveToFirst();
        int totalcourses=cursor.getInt(cursor.getColumnIndex("totalcourses"));
        return totalcourses;
    }
    public void updateTotalCourses(int totalcourses)
    {
        int[] data=ReadSemData();
        ContentValues values=new ContentValues();
        values.put(SemDataCreator.KEY_START_YEAR, data[0]);
        values.put(SemDataCreator.KEY_START_MONTH, data[1]);
        values.put(SemDataCreator.KEY_START_DATE, data[2]);
        values.put(SemDataCreator.KEY_END_YEAR, data[3]);
        values.put(SemDataCreator.KEY_END_MONTH, data[4]);
        values.put(SemDataCreator.KEY_END_DATE, data[5]);
        values.put(SemDataCreator.KEY_TOTAL_COURSES, totalcourses);
        database.update(SemDataCreator.TABLE_SEMDATA, values, null, null);
    }

    public void deleteSemTable()
    {
        database.delete(SemDataCreator.TABLE_SEMDATA, null, null);
    }

    public int[] readTime()
    {

        Cursor cursor;
        String q="SELECT * FROM semesterdata";
        cursor=database.rawQuery(q, null);
        cursor.moveToFirst();
        int hour = cursor.getInt(cursor.getColumnIndex("endhour"));
        int minute = cursor.getInt(cursor.getColumnIndex("endminute"));
        int[] time=new int[]{hour, minute};
        return time;
    }
}
