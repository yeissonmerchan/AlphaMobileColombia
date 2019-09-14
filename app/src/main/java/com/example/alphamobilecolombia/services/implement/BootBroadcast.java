package com.example.alphamobilecolombia.services.implement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.alphamobilecolombia.services.FileStorageService;

public class BootBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        ctx.startService(new Intent(ctx, FileStorageService.class));
    }
}
