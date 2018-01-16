package com.tc.hackwe;

import android.app.Application;

/**
 * Created by tiancheng on 2017/3/13.
 */

public class LogApplication extends Application {

    private static LogApplication mLogApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mLogApp = this;
    }

    public static LogApplication getInstance() {
        return mLogApp;
    }
}
