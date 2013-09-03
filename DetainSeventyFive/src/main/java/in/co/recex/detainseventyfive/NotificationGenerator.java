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

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import in.co.recex.detainseventyfive.R;
import in.co.recex.detainseventyfive.TodayAttActivity;

/**
 * Created by sopandev on 29/8/13.
 */
public class NotificationGenerator extends Service{
    NotificationCompat.Builder mBuilder;
    Intent intent1;
    PendingIntent pintent;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }
    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder =
                new NotificationCompat.Builder(NotificationGenerator.this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Detain75")
                        .setContentText("Enter Today's Attendance.")
                        .setOngoing(true)
                        .setAutoCancel(true);

        intent1 = new Intent(this.getApplicationContext(), TodayAttActivity.class);
        pintent = PendingIntent.getActivity(this.getApplicationContext(),0, intent1 , 0);

        mBuilder.setContentIntent(pintent);
        int mNotificationId = 001;


        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
