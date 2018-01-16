package com.tc.hackwe.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.tc.hackwe.Constans;

public class LogContentProvider extends ContentProvider {

    public static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(Constans.AUTHORITY, "log", Constans.LOG_URI_CODE);
    }

    public LogContentProvider() {

    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case Constans.LOG_URI_CODE:
                return LogSqlDatabaseHelper.getInstance().delete(selection, selectionArgs);
        }
        return -1;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case Constans.LOG_URI_CODE:
                LogSqlDatabaseHelper.getInstance().insert(values);
                break;
        }
        return uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case Constans.LOG_URI_CODE:
                return LogSqlDatabaseHelper.getInstance().query(projection, selection,
                        selectionArgs, sortOrder);
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case Constans.LOG_URI_CODE:
                return LogSqlDatabaseHelper.getInstance().update(values, selection, selectionArgs);
        }
        return -1;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}