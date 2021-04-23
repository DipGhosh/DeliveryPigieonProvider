package com.dev.pigeonproviderapp.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class StopNotificationSoundService extends Service {

    private BroadcastReceiver sReceiver;

    public IBinder onBind(Intent arg) {
        return null;
    }

    public int onStartCommand(Intent intent, int flag, int startIs) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        sReceiver = new StopSoundNotificationReceiver();
        registerReceiver(sReceiver, filter);
        return START_STICKY;
    }

    public void onDestroy() {
        if (sReceiver != null)
            unregisterReceiver(sReceiver);
        super.onDestroy();
    }
}
