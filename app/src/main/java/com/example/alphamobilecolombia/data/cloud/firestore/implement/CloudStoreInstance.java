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
import com.example.alphamobilecolombia.utils.notification.local.INotification;
import com.example.alphamobilecolombia.utils.notification.model.LocalNotification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

import static io.fabric.sdk.android.Fabric.TAG;

public class CloudStoreInstance implements ICloudStoreInstance {
    Context _context;
    INotification _iNotification;
    IRealmInstance _iRealmInstance;
    public CloudStoreInstance(INotification iNotification, IRealmInstance iRealmInstance, Context context){
        _context = context;
        _iNotification = iNotification;
        _iRealmInstance = iRealmInstance;
    }

    public FirebaseFirestore instanceDb(){
        FirebaseFirestoreSettings fireStoreSettings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(fireStoreSettings);
        return db;
    }

    public Task<QuerySnapshot> syncCollection(String nameCollection){
        try {
            /*Parameter newParameter = new Parameter();
            newParameter.setKey("campo1");
            newParameter.setValue("fgfgfhfghfghgfhgfhgfhfg");
            _iRealmInstance.Insert(newParameter);

            Parameter newParameter2 = new Parameter();
            newParameter2.setKey("campo2");
            newParameter2.setValue("673478487456");
            _iRealmInstance.Insert(newParameter2);

            List<RealmObject> lisParameters = _iRealmInstance.GetAll(newParameter);
            List<Parameter> lisParameters2 = _iRealmInstance.GetAllGeneric(newParameter);
            for (Parameter parameter : lisParameters2){
                String key = parameter.getKey();
                String value = parameter.getValue();
            }

            Parameter busqueda = _iRealmInstance.GetByAttribute(newParameter,"Key",newParameter.getKey());
            String key = busqueda.getKey();
            String value = busqueda.getValue();*/

            FirebaseFirestore db = instanceDb();
            Task<QuerySnapshot> collectionSimulator = db.collection(nameCollection)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<ParametrizacionMotor> objects = task.getResult().toObjects(ParametrizacionMotor.class);
                            if (objects != null)
                            {
                                ParametrizacionMotor parametrizacionMotor = objects.get(0);
                                uploadConfiguration(parametrizacionMotor);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });

            return collectionSimulator;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private void uploadConfiguration(ParametrizacionMotor parametrizacionMotor){
        LocalNotification localNotification = new LocalNotification();
        try {
            if (parametrizacionMotor != null) {
                List<VariableMotor> variableMotors = parametrizacionMotor.getVariables();
                List<SelectionOption> newOptions = new ArrayList<>();

                _iRealmInstance.DeleteObject(new SelectionOption());

                for (VariableMotor variableMotor : variableMotors) {
                    if (variableMotor.getListaData() != null) {
                        for (ListData listData : variableMotor.getListaData()) {
                            newOptions.add(new SelectionOption(variableMotor.getCodigoCampo(), variableMotor.getNombreControl(), listData.getCodigo(), listData.getNombre()));
                        }
                    }
                }

                _iRealmInstance.Insert(newOptions);
                localNotification.setMessage("Sincronización de configuración del app finalizada.");
                localNotification.setTitle("Check!");

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            localNotification.setMessage("Error en la sincronización de la configuración del app.");
            localNotification.setTitle("Check!");
        }
        finally {
            _iNotification.ShowNotification(localNotification);
        }
    }

}
