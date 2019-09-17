package com.example.alphamobilecolombia.mvp.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.alphamobilecolombia.utils.crashlytics.LogError;

import java.util.Timer;
import java.util.TimerTask;

//Gestiona el segundo plano, entre este Servicio y el BroadcastReceiver ImagesBackGroundReceiver
public class ImagesBackgroundService extends Service {

    //Define el contador
    private int counter = 0;

    //Define el temporizador
    private Timer timer;

    //Define la tarea del temporizador
    private TimerTask timerTask;

    //Define el contexto
    public static Context _context;

    //Se produce cuando se inicia este servicio
    public ImagesBackgroundService() {
    }

    //Se produce cuando se inicia este servicio
    public ImagesBackgroundService(Context context) {
        super(); //Invoca la clase base
        if (context != null)
            _context = context; //Establece el contexto
    }

    //Ejecuta el Foreground
    //Se produce al crearse esta actividad
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startMyOwnForeground();
        }
    }

    //Se produce cuando se inicia este servicio
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    //Se produce al destruir este servicio
    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent(_context, ImagesBackGroundReceiver.class);
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    //No hace nada actualmente pero se debe implementar
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Configura el temporizador
    public void startTimer() {
        timer = new Timer(); //Inicializa el temporizador
        initializeTimerTask(); //Inicializa la configuración del temporizador
        timer.schedule(timerTask, 1000, 60000); //Establece la tiempo de ejecución del temporizador
    }

    //Inicializa la tarea del temporizador
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));


                try {
                    IntentFilter filter = new IntentFilter();
                    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    registerReceiver(new ImagesBackGroundReceiver2(), filter);


                } catch (NullPointerException e) {
                    e.printStackTrace();
//                    LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "initializeTimerTask", e, _context);

                }
            }
        };
    }

    //Se produce cuando se destruye el servicio
    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    //Ejecuta el Foreground personalizado
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
/*                .setSmallIcon(R.drawable.icon_1)*/
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

/*    //Verifica si hay internet
    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "isOnline", e, _context);
            return false;
        }
    }*/

}