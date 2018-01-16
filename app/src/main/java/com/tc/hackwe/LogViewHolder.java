package com.tc.hackwe;

import android.view.View;
import android.widget.TextView;

import java.text.MessageFormat;

/**
 * Created by tiancheng on 2017/3/14.
 */

public class LogViewHolder {
    private TextView tvTime;
    private TextView tvContent;

    public LogViewHolder(View parent) {
        tvTime = (TextView) parent.findViewById(R.id.tv_time);
        tvContent = (TextView) parent.findViewById(R.id.tv_content);
    }

    public void update(LogModel model) {
        if (model == null) {
            return;
        }
        tvTime.setText(model.getFormatTime());
        tvContent.setText(MessageFormat.format("{0}：{1}", model.getName(), model.getContent()));
    }
}
