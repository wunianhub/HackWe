package com.tc.hackwe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tc.hackwe.database.LogSqlDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends AppCompatActivity {

    RecyclerView rvList;
    LogAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LogAdapter();
        rvList.setAdapter(mAdapter);
        new AsyncTask<Void, Void, List<LogModel>>() {

            @Override
            protected List<LogModel> doInBackground(Void... params) {
                return LogSqlDatabaseHelper.getInstance().selectAll();
            }

            @Override
            protected void onPostExecute(List<LogModel> logModels) {
                mAdapter.updateData(logModels);
            }
        }.execute();
    }

    static class LogHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvContent;

        public LogHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false));
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }

        public void update(LogModel data) {
            tvTime.setText(data.getFormatTime());
            tvContent.setText(data.getName() + "：" + data.getContent());
        }
    }

    static class LogAdapter extends RecyclerView.Adapter<LogHolder> {
        List<LogModel> mData = new ArrayList<>();

        public void updateData(List<LogModel> data) {
            mData = data;
            notifyDataSetChanged();
        }

        @Override
        public LogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new LogHolder(parent);
        }

        @Override
        public void onBindViewHolder(LogHolder holder, int position) {
            holder.update(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

}
