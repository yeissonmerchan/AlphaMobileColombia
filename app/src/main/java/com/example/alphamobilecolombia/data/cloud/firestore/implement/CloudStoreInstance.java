package com.example.alphamobilecolombia.data.cloud.firestore.implement;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.alphamobilecolombia.data.cloud.firestore.ICloudStoreInstance;
import com.example.alphamobilecolombia.data.cloud.firestore.entity.ConfiguracionMotor;
import com.example.alphamobilecolombia.data.cloud.firestore.entity.ListData;
import com.example.alphamobilecolombia.data.cloud.firestore.entity.ParametrizacionMotor;
import com.example.alphamobilecolombia.data.cloud.firestore.entity.VariableMotor;
import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.local.entity.Parameter;
import com.example.alphamobilecolombia.data.local.entity.SelectionOption;
import com.example.alphamobilecolombia.utils.configuration.implement.ApplicationData;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.notification.local.INotification;
import com.example.alphamobilecolombia.utils.notification.model.LocalNotification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.RealmObject;

import static io.fabric.sdk.android.Fabric.TAG;

//Gestiona la sincronización entre FireBase y Realm
public class CloudStoreInstance implements ICloudStoreInstance {

    //Define el contexto
    Context _context;

    INotification _iNotification;

    //Define el almacenamiento local
    IRealmInstance _iRealmInstance;

    //CONSTRUCTOR: Inicializa las propiedades de esta clase
    public CloudStoreInstance(INotification iNotification, IRealmInstance iRealmInstance, Context context) {
        _context = context; //Establece el contexto
        _iNotification = iNotification; //Establece la notificación
        _iRealmInstance = iRealmInstance; //Establece el almacenamiento local
    }

    //Obtiene la instancia de FireBase
    public FirebaseFirestore instanceDb() {

        FirebaseFirestoreSettings fireStoreSettings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.setFirestoreSettings(fireStoreSettings);

        return db;
    }

    //Sincroniza la colección
    public Task<QuerySnapshot> syncCollection(String nameCollection) {

        //Define la colección a retornar
        Task<QuerySnapshot> collectionSimulator = null;

        try {

            FirebaseFirestore db = instanceDb();

            collectionSimulator = db.collection(nameCollection)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<ParametrizacionMotor> objects = task.getResult().toObjects(ParametrizacionMotor.class);
                                if (objects != null && objects.size() > 0) {
                                    ParametrizacionMotor parametrizacionMotor = objects.get(0);
                                    uploadConfiguration(parametrizacionMotor, nameCollection);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return collectionSimulator;
    }

    //Carga la configuración
    private void uploadConfiguration(ParametrizacionMotor parametrizacionMotor, String nameCollection) {
        LocalNotification localNotification = new LocalNotification();
        try {
            if (parametrizacionMotor != null) {
                List<VariableMotor> variableMotors = parametrizacionMotor.getVariables();
                List<SelectionOption> newOptions = new ArrayList<>();

                boolean deleteComplete = _iRealmInstance.DeleteObject(new SelectionOption());
                if (!deleteComplete)
                    throw new Exception("Ah ocurrido un error en el storage, se procede a realizar la reparación inicial del app");

                for (VariableMotor variableMotor : variableMotors) {
                    if (variableMotor.getListaData() != null) {
                        for (ListData listData : variableMotor.getListaData()) {
                            newOptions.add(new SelectionOption(variableMotor.getCodigoCampo(), variableMotor.getNombreControl(), listData.getCodigo(), listData.getNombre()));
                        }
                    }
                }

                boolean isValidInsert = _iRealmInstance.Insert(newOptions);
                if (!isValidInsert)
                    throw new Exception("Ah ocurrido un error en el storage, se procede a realizar la reparación inicial del app");

                localNotification.setMessage("Sincronización de configuración del app finalizada.");
                localNotification.setTitle("Check!");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "uploadConfiguration: parametrizacionMotor", ex, _context);
            localNotification.setMessage("Error en la sincronización de la configuración del app.");
            localNotification.setTitle("Check!");
            ApplicationData applicationData = new ApplicationData();
            applicationData.RestartNewVersionApp(_context.getApplicationContext());
            syncCollection(nameCollection);
        } finally {
           // _iNotification.ShowNotification(localNotification, new Intent());
            Runtime.getRuntime().gc();
        }
    }

}
