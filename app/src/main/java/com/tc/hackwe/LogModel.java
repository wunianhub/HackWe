package com.tc.hackwe;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by tiancheng on 2017/3/13.
 */

public class LogModel implements Serializable {
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String content;
    private String name;
    private long time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFormatTime() {
        return format.format(time);
    }

}
