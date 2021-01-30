/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dev.pigeonproviderapp.Firebase;

/**
 * Created by krish on 28/09/16.
 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.dev.pigeonproviderapp.ActivityAll.SpalshActivity;
import com.dev.pigeonproviderapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";
    private static int count = 0;
    private static final String CHANNEL_NAME = "FCM";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";



    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e(TAG, "onNewToken: " + s);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //System.out.println("Mangaldip"+"Check");
        // Check if message contains a data payload.

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            //sendNotification(remoteMessage);
        }

        if (remoteMessage.getData().size() > 0)
        {
            try {
                sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
            } catch (Exception e) {
                e.printStackTrace();
            }




        }



    }


    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(getApplicationContext(), SpalshActivity.class);
//you can use your launcher Activity insted of SplashActivity, But if the Activity you used here is not launcher Activty than its not work when App is in background.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//Add Any key-value to pass extras to intent

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        /*Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + getApplicationContext().getPackageName() + "/raw/notification");*/
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //For Android Version Orio and greater than orio.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("Sesame", "Sesame", importance);
            mChannel.setDescription(messageBody);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotifyManager.createNotificationChannel(mChannel);
        }
        //For Android Version lower than oreo.
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "Seasame");
        mBuilder.setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setColor(Color.parseColor("#FFD600"))
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setChannelId("Sesame")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        //if (Build.VERSION.SDK_INT >= 21) mBuilder.setVibrate(new long[0]);

        mNotifyManager.notify(count, mBuilder.build());
        count++;
    }

}

