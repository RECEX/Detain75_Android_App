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
import android.util.Log;

/**
 * Created by sopandev on 26/8/13.
 */
public class Database2Creator extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ClassProgress";

    public static final String TABLE_PROGRESS = "Progress";

    public static final String KEY_COURSE_NAME = "course";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_ATT = "attended";
    public static final String KEY_OVER = "over";
    public static final String KEY_CANCEL = "cancel";
    public static final String KEY_MANDATORY = "mandatory";

    private static String CREATE_PROGRESS_TABLE = "CREATE TABLE " + TABLE_PROGRESS+ "("
            + KEY_COURSE_NAME+ " TEXT,"
            + KEY_TOTAL+ " INTEGER,"
            + KEY_ATT+ " INTEGER,"
            + KEY_OVER+ " INTEGER,"
            + KEY_CANCEL+ " INTEGER,"
            + KEY_MANDATORY+ " INTEGER"+ ")";

    public Database2Creator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROGRESS_TABLE);
        Log.d("DB2", "Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);

        // Create tables again
        onCreate(db);
    }
}
