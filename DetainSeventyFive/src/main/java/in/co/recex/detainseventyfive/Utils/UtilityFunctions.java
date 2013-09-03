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

import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by sopandev on 25/8/13.
 */
public class UtilityFunctions {
    public static String name;
    public static int totalcourses=0;
    public static int mon;
    public static int tue;
    public static int wed;
    public static int thu;
    public static int fri;
    public static int sat;
    public static int sun;
    public static int mandatory;

    public static int ClassCount(int startyear, int startmonth, int startdate, int endyear, int endmonth, int enddate, int mon, int tue, int wed, int thu, int fri, int sat, int sun)
    {
        Calendar ptrcal = new GregorianCalendar(startyear, startmonth, startdate);
        Calendar endcal = new GregorianCalendar(endyear, endmonth, enddate);
        int dayofyearptr=ptrcal.get(GregorianCalendar.DAY_OF_YEAR);
        int dayofyearend=endcal.get(GregorianCalendar.DAY_OF_YEAR);
        int classcount=0;
        while (dayofyearptr!=dayofyearend)
        {
            int day=ptrcal.get(GregorianCalendar.DAY_OF_WEEK);
            if(day==1)
            {
                classcount+=sun;
            }
            if(day==2)
            {
                classcount+=mon;
            }
            if(day==3)
            {
                classcount+=tue;
            }
            if(day==4)
            {
                classcount+=wed;
            }
            if(day==5)
            {
                classcount+=thu;
            }
            if(day==6)
            {
                classcount+=fri;
            }
            if(day==7)
            {
                classcount+=sat;
            }
            if(dayofyearptr==365)
            {
                ptrcal.set(startyear+1, GregorianCalendar.JANUARY, 1);
                dayofyearptr=1;
            }
            else
            {
            ptrcal.roll(GregorianCalendar.DAY_OF_YEAR, true);
            dayofyearptr+=1;
            }
            Log.d("Count", "LoopComplete");
        }
         return classcount;
    }

}
