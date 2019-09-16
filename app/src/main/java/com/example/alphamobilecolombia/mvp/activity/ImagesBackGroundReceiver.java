package com.example.alphamobilecolombia.mvp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.example.alphamobilecolombia.utils.crashlytics.LogError;

//Gestiona el segundo plano, entre este BroadCastReceiver y el Servicio ImagesBackgroundService
//Cuando el Servicio ImagesBackgroundService muere, ejecuta este BroadcastReceiver y este a su vez vuelve a crear un servicio
public class ImagesBackGroundReceiver extends BroadcastReceiver {

    //Define el contexto
    private static Context _context;

    //Ejecuta el servicio
    //Se produce cuando el servicio ImagesBackgroundService Muere
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null)
        _context = context; //Establece el contexto
        execute(); //Ejecuta el servicio
    }

    //Comienza el servicio ImagesBackgroundService
    //Se ejecuta de manera recursiva cuando se revienta
    public void execute() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Si la version es mayor o igual que la 26 (OREO) entonces
                _context.startForegroundService(new Intent(_context, ImagesBackgroundService.class));
            } else {
                _context.startService(new Intent(_context, ImagesBackgroundService.class));
            }
        } catch (Exception ex) {
            execute(); //Se llama así mismo para intentar nuevamente ejecutar el servicio
        }
    }
}

//if (context != null)
//context.startService(new Intent(context, ImagesBackgroundService.class));

/*

*/
/*






*/
