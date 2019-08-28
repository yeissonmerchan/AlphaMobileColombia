package com.example.alphamobilecolombia.data.local.implement;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Log;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;

import java.io.File;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.util.Calendar;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmInstance implements IRealmInstance {
    Context _context;
    public RealmInstance(Context context){
        _context = context;
    }

    public <T> boolean Insert(T realmObject){
        boolean isSuccess = false;
        try {
            Realm.init(_context);

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.copyToRealm((RealmObject)realmObject);
            realm.commitTransaction();

            realm.close();
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Consulta credito",ex,_context);
            ex.printStackTrace();
        }
        return isSuccess;
    }

    public List<RealmObject> GetAll(RealmObject object){
        try {
            Realm.init(_context);
            Realm realm = Realm.getDefaultInstance();
            Class classObject = object.getClass();
            final RealmResults<RealmObject> data = realm.where(classObject).findAll();

            return data;
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Lista creditos",ex,_context);
            ex.printStackTrace();
        }
        return null;
    }


    public Realm initLocalStorage(Context context){
        try {
            File file = new File(context.getFilesDir(), "alphaStorage.realm");
            /*if(file.exists()){
                file.delete();
            }*/
            if(!file.exists()) {
                Realm.init(context);
                RealmConfiguration config = new RealmConfiguration.Builder()
                        .name("alphaStorage.realm")
                        .encryptionKey(createNewKeys(context))
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();

                Realm myRealm = Realm.getInstance(config);

                return myRealm;
            }
            else{
                return null;
            }
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Creación de storage",ex,context);
            return null;
        }
    }


    public byte[] createNewKeys(Context context) throws KeyStoreException {

        try {

            String license = context.getResources().getString(R.string.local_storage_Key);
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            end.add(Calendar.YEAR, 1);
            KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                    .setAlias(license)
                    .setSubject(new X500Principal("CN=Sample Name, O=Android Authority"))
                    .setSerialNumber(BigInteger.ONE)
                    .setStartDate(start.getTime())
                    .setEndDate(end.getTime())
                    .build();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
            generator.initialize(spec);

            KeyPair keyPair = generator.generateKeyPair();

            //byte[] key = keyPair.getPublic().getEncoded();
            byte[] bytes = new byte[64];

            for (int i=0, len=bytes.length; i<len; i++) {
                bytes[i] = Byte.parseByte(String.valueOf(keyPair.getPublic().getEncoded()[i]));
            }

            return bytes;

        } catch (Exception ex) {
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Crear llaves",ex,context);
            Log.e("Key Realm", Log.getStackTraceString(ex));
        }
        return null;
    }
}
