package com.example.alphamobilecolombia.utils.notification.local;

import android.content.Intent;

import com.example.alphamobilecolombia.utils.notification.model.LocalNotification;

public interface INotification {
    boolean ShowNotification(LocalNotification message, Intent intent);
}
