package com.tc.hackwe;

import android.net.Uri;

/**
 * Created by tiancheng on 2017/3/14.
 */

public class Constans {
    public static final String AUTHORITY = "com.tc.hackwe.database.provider";
    public static final Uri LOG_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/log");
    public static final int LOG_URI_CODE = 1;

    public static final String DB_NAME = "db_log";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "table_log";

    public static final String CULUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_WHO = "who";
    public static final String COLUMN_CONTENT = "content";

}
