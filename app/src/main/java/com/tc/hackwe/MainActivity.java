package com.tc.hackwe;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });
        findViewById(R.id.btn_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogActivity.class));
            }
        });
        findViewById(R.id.btn_log_cursor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogListActivity.class));
            }
        });
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        findViewById(R.id.btn_start_server_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String string = Settings.Secure.getString(getContentResolver(),
//                        "enabled_notification_listeners");
//                if (!string.contains(HackNotificationService.class.getName())) {
//                    startActivity(new Intent(
//                            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
//                } else {
//                    LogUtil.logE("已经开启");
//                }
                startActivity(new Intent(
                        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
        });

        findViewById(R.id.btn_send_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(MainActivity.this, LogActivity.class);
                PendingIntent viewPendingIntent =
                        PendingIntent.getActivity(MainActivity.this, 0, viewIntent, 0);

                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setTicker("测试一下")
                        .setContentTitle("测试一下下")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(viewPendingIntent)
                        .build();
                NotificationManagerCompat.from(MainActivity.this).notify((int) System.currentTimeMillis(), notification);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "申请权限被拒", Toast.LENGTH_SHORT).show();
        }
    }
}
