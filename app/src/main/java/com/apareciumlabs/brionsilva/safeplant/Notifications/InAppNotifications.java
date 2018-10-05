package com.apareciumlabs.brionsilva.safeplant.Notifications;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.apareciumlabs.brionsilva.safeplant.R;
import com.apareciumlabs.brionsilva.safeplant.SplashScreen;

import java.util.Random;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 24/06/2017
 */
public class InAppNotifications extends Activity {

    private int UNIQUE_ID;


    public void createNotification(String notificationTitle, String notificationTicker, String notificationBody , Class targetClass) {

        //creating a random id
        Random random = new Random();
        UNIQUE_ID = random.nextInt(10000+1);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker(notificationTicker)
                        .setContentTitle(notificationTitle)
                        .setWhen(System.currentTimeMillis())
                        .setContentText(notificationBody);

        Intent notificationIntent = new Intent(this, SplashScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(UNIQUE_ID, builder.build());
    }

}
