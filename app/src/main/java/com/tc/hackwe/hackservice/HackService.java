package com.tc.hackwe.hackservice;

import android.accessibilityservice.AccessibilityService;
import android.os.Environment;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.tc.hackwe.LogModel;
import com.tc.hackwe.database.LogSqlDatabaseHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HackService extends AccessibilityService {
    public static final String FILE_NAME = "/hackWe.txt";
    public static final String DEFAULT_NAME = "系统";

    File hackFile;

    BlockingQueue<LogModel> msgQueue = new ArrayBlockingQueue<LogModel>(100);

    public HackService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        log("onCreate");
        if (hackFile == null) {
            String name = Environment.getExternalStorageDirectory().getAbsoluteFile() + FILE_NAME;
            hackFile = new File(name);
            log("文件已创建：" + name);
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        log("收到时间：" + event);
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            try {
                LogModel model = new LogModel();
                if (event.getText() != null && event.getText().size() > 0) {
                    setModel(model, event.getText().get(0).toString());
                }
                msgQueue.put(model);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setModel(LogModel model, String string) {
        model.setTime(System.currentTimeMillis());
        String[] s = string.split("：|:", 2);
        if (s.length < 2) {
            model.setName(DEFAULT_NAME);
            model.setContent(s[0]);
        } else {
            model.setName(s[0]);
            model.setContent(s[1]);
        }

    }

    @Override
    public void onInterrupt() {
        log("onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        new Thread(consumer).start();
        log("onServiceConnect");
    }

    private void log(String s) {
        Log.e("tiancheng", s);
    }

    Runnable consumer = new Runnable() {
        @Override
        public void run() {
            log("------------------------写入服务启动-----------------------");
            FileWriter writer = null;
            try {
                writer = new FileWriter(hackFile, true);
                while (true) {
                    LogModel model = msgQueue.take();
                    String s = model.getFormatTime() + "——-" + model.getName() + "：" + model.getContent();
                    writer.write(s + "\n");
                    writer.flush();
                    LogSqlDatabaseHelper.getInstance().insert(model);
                    log("写入一条消息：" + s);
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                        log("writer close");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
}
