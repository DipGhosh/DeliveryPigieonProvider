package com.dev.pigeonproviderapp.Service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.dev.pigeonproviderapp.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class StopSoundNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("LOG_TAG", "StopSoundNotificationReceiver");
        // Here is code that stops sound

        NotificationManager notify_manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notify_manager.cancelAll();

        try {
            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getApplicationContext().getPackageName() + "/" + R.raw.pushnotificaton_alarm);
            Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), sound);
            r.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
