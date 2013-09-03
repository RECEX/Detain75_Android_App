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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sopandev on 27/8/13.
 */
public class SemDataCreator extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SemData";

    public static final String TABLE_SEMDATA = "semesterdata";

    public static final String KEY_START_YEAR="startyear";
    public static final String KEY_START_MONTH="startmonth";
    public static final String KEY_START_DATE="startdate";
    public static final String KEY_END_YEAR="endyear";
    public static final String KEY_END_MONTH="endmonth";
    public static final String KEY_END_DATE="enddate";
    public static final String KEY_END_HOUR="endhour";
    public static final String KEY_END_MINUTE="endminute";
    public static final String KEY_TOTAL_COURSES="totalcourses";

    private static String CREATE_SEMDATA_TABLE = "CREATE TABLE " + TABLE_SEMDATA+ "("
            + KEY_START_YEAR+ " INTEGER,"
            + KEY_START_MONTH+ " INTEGER,"
            + KEY_START_DATE+ " INTEGER,"
            + KEY_END_YEAR+ " INTEGER,"
            + KEY_END_MONTH+ " INTEGER,"
            + KEY_END_DATE+ " INTEGER,"
            + KEY_END_HOUR+ " INTEGER,"
            + KEY_END_MINUTE+ " INTEGER,"
            + KEY_TOTAL_COURSES+ " INTEGER"+ ")";

    public SemDataCreator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SEMDATA_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEMDATA);

        // Create tables again
        onCreate(db);
    }
}
