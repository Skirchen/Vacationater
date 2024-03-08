package skirchen.example.Vacationater.ui;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import skirchen.example.Vacationater.R;

public class MyReceiver extends BroadcastReceiver {
    private String CHANNEL_ID = "Channel";
    public static int notificationNum;
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");

        Toast.makeText(context, text, Toast.LENGTH_LONG).show();

        System.out.println(intent.getCharSequenceExtra("text"));
        createNotificationChannel(context, CHANNEL_ID);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationNum++, notification);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = "channelName";
        String description = "channelDescript";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}