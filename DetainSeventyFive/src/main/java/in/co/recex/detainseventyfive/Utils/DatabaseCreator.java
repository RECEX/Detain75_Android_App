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
 * Created by sopandev on 26/8/13.
 */
public class DatabaseCreator extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "WeeklySchedule";

    public static final String TABLE_SCHEDULE = "Schedule";


    public static final String KEY_COURSE_NAME = "course";
    public static final String KEY_MON = "mon";
    public static final String KEY_TUE = "tue";
    public static final String KEY_WED = "wed";
    public static final String KEY_THU = "thu";
    public static final String KEY_FRI = "fri";
    public static final String KEY_SAT = "sat";
    public static final String KEY_SUN = "sun";


    public DatabaseCreator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCHEDULE_TABLE = "CREATE TABLE " + TABLE_SCHEDULE+ "("
                + KEY_COURSE_NAME+ " TEXT,"
                + KEY_MON+ " INTEGER,"
                + KEY_TUE+ " INTEGER,"
                + KEY_WED+ " INTEGER,"
                + KEY_THU+ " INTEGER,"
                + KEY_FRI+ " INTEGER,"
                + KEY_SAT+ " INTEGER,"
                + KEY_SUN+ " INTEGER" + ")";
        db.execSQL(CREATE_SCHEDULE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);

        // Create tables again
        onCreate(db);
    }

}
