package com.tc.hackwe;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LogListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView lvLog;
    CursorAdapter mAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("record");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.R.drawable.ic_media_ff);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.clear:
                        clear();
                        break;
                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvLog = (ListView) findViewById(R.id.lv_log);
        mAdapter = new CursorAdapter(this, null, true) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LogUtil.logE("CursorAdapter---newView");
                View view = LayoutInflater.from(context).inflate(R.layout.item_log, parent, false);
                LogViewHolder holder = new LogViewHolder(view);
                view.setTag(holder);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                LogUtil.logE("CursorAdapter---bindView");
                LogViewHolder holder = (LogViewHolder) view.getTag();
                LogModel model = new LogModel();
                model.setTime(cursor.getLong(cursor.getColumnIndex(Constans.COLUMN_TIME)));
                model.setName(cursor.getString(cursor.getColumnIndex(Constans.COLUMN_WHO)));
                model.setContent(cursor.getString(cursor.getColumnIndex(Constans.COLUMN_CONTENT)));
                holder.update(model);
            }
        };
        lvLog.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void clear() {
        getContentResolver().delete(Constans.LOG_CONTENT_URI, null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log_list, menu);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        LogUtil.logE("onCreateLoader");
        return new CursorLoader(this, Constans.LOG_CONTENT_URI, null, null, null, Constans.COLUMN_TIME + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        LogUtil.logE("onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        LogUtil.logE("onLoaderReset");
    }
}
