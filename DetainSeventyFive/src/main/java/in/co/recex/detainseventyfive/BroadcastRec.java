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

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import in.co.recex.detainseventyfive.HomeScreenActivity;
import in.co.recex.detainseventyfive.NotificationGenerator;

/**
 * Created by sopandev on 28/8/13.
 */
public class BroadcastRec extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service1 = new Intent(context, NotificationGenerator.class);
        context.startService(service1);
        context.stopService(service1);
    }
}
