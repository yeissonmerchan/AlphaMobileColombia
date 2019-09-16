package com.example.alphamobilecolombia.utils.notification.local.implement;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.utils.notification.local.INotification;
import com.example.alphamobilecolombia.utils.notification.model.LocalNotification;

public class Notification implements INotification {
    Context _context;
    public Notification(Context context){
        _context = context;
    }

    public boolean ShowNotification(LocalNotification message) {
        Bitmap bitmapLogo = BitmapFactory.decodeResource(_context.getResources(), R.mipmap.ico);
        PendingIntent pendingIntent = PendingIntent.getActivity(_context, 0, new Intent(),0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context,"default")
                .setLargeIcon(bitmapLogo)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message.getMessage()))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(message.getTitle())
                .setContentText(message.getMessage())
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("YOUR_PACKAGE_NAME");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "YOUR_PACKAGE_NAME",
                    "YOUR_APP_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        notificationManager.notify(1,builder.build());

        return true;
    }
}
