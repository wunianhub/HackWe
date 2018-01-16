package com.tc.hackwe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tc.hackwe.Constans;
import com.tc.hackwe.LogApplication;
import com.tc.hackwe.LogModel;
import com.tc.hackwe.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tiancheng on 2017/3/13.
 */

public class LogSqlDatabaseHelper extends SQLiteOpenHelper {


    private static LogSqlDatabaseHelper mHelper;

    private LogSqlDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                 int version) {
        super(context, name, factory, version);
    }

    private LogSqlDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                 int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public static LogSqlDatabaseHelper getInstance() {
        if (mHelper == null) {
            mHelper = new LogSqlDatabaseHelper(LogApplication.getInstance(), Constans.DB_NAME, null,
                    Constans.DB_VERSION);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.logE("SqlLite-OnCreate");
        db.execSQL(Sql.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.logE("SqlLite-OnUpgrade");
        if (oldVersion == 1) {
            db.execSQL(Sql.SQL_ADD_COLUMN);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        LogUtil.logE("SqlLite-OnOpen");
    }

    public void insert(LogModel model) {
        if (model == null) {
            return;
        }
        insert(model.getTime(), model.getName(), model.getContent());
    }

    public void insert(long time, String name, String content) {
        ContentValues values = new ContentValues();
        values.put(Constans.COLUMN_TIME, time);
        values.put(Constans.COLUMN_WHO, name);
        values.put(Constans.COLUMN_CONTENT, content);
        insert(values);
    }

    public long insert(ContentValues values) {
        return getInstance().getWritableDatabase().insert(Constans.TABLE_NAME, null, values);
    }

    public Cursor query(String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return getReadableDatabase().query(Constans.TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);
    }

    public int delete(String selection, String[] selectionArgs) {
        return getWritableDatabase().delete(Constans.TABLE_NAME, selection, selectionArgs);
    }

    public int update(ContentValues values, String selection,
                      String[] selectionArgs) {
        return getWritableDatabase().update(Constans.TABLE_NAME, values, selection, selectionArgs);
    }

    public List<LogModel> selectAll() {
        List<LogModel> list = new ArrayList<>();
        String[] columns = new String[]{Constans.COLUMN_TIME, Constans.COLUMN_WHO, Constans.COLUMN_CONTENT};
        Cursor cursor = getInstance().getReadableDatabase().query(Constans.TABLE_NAME, columns,
                null, null, null, null, Constans.COLUMN_TIME);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                LogModel model = new LogModel();
                model.setTime(cursor.getLong(cursor.getColumnIndex(Constans.COLUMN_TIME)));
                model.setName(cursor.getString(cursor.getColumnIndex(Constans.COLUMN_WHO)));
                model.setContent(cursor.getString(cursor.getColumnIndex(Constans.COLUMN_CONTENT)));
                list.add(model);
            }
            cursor.close();
        }
        return list;
    }

    private static class Sql {
        public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + Constans.TABLE_NAME
                + " ( " + Constans.CULUMN_ID + " LONG," + Constans.COLUMN_TIME + " LONG," + Constans.COLUMN_WHO + " VARCHAR,"
                + Constans.COLUMN_CONTENT + " VARCHAR)";
        public static final String SQL_DROP_TABLE = "";

        public static final String SQL_ADD_COLUMN = "ALTER TABLE " + Constans.TABLE_NAME
                + " ADD " + Constans.CULUMN_ID + " LONG";
    }
}
