package com.tc.hackwe.hackservice;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.tc.hackwe.LogUtil;

public class HackNotificationService extends NotificationListenerService {
    public HackNotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.logE("HackNotificationService---onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.logE("HackNotificationService---onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        LogUtil.logE("onNotificationPosted---" + sbn.toString());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        LogUtil.logE("onNotificationRemoved---" + sbn.toString());
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.logE("onNotificationRemoved---onBind");
        return null;
    }
}
