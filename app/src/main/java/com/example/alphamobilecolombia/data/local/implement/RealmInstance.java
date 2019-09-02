package com.example.alphamobilecolombia.data.local.implement;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Log;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.cryptography.providers.IRSA;

import org.apache.commons.io.input.BOMInputStream;

import java.io.File;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmInstance implements IRealmInstance {
    Context _context;
    IRSA _iRsa;
    public RealmInstance(Context context, IRSA irsa)
    {
        _context = context;
        _iRsa = irsa;
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

            isSuccess = true;
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

    public <T> List<T> GetAllGeneric(RealmObject object){
        try {
            Realm.init(_context);
            Realm realm = Realm.getDefaultInstance();
            Class classObject = object.getClass();
            final RealmResults<T> data = realm.where(classObject).findAll();

            return data;
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Lista",ex,_context);
            ex.printStackTrace();
        }
        return null;
    }

    public <T> T GetByAttribute(RealmObject object, String key, String value){
        try {
            Realm.init(_context);
            Realm realm = Realm.getDefaultInstance();
            Class classObject = object.getClass();
            final T data = (T) realm.where(classObject).equalTo(key,value).findFirst();

            return data;
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"objeto por id",ex,_context);
            ex.printStackTrace();
        }
        return null;
    }

    public <T> boolean deleteObject(RealmObject object){
        boolean isSuccess = false;
        try {
            Realm.init(_context);
            Realm realm = Realm.getDefaultInstance();
            Class classObject = object.getClass();
            final RealmResults<T> findPerson = realm.where(classObject).findAll();
            realm.beginTransaction();
            realm.delete(classObject);
            if (null != findPerson && findPerson.size() > 0) {
                findPerson.deleteAllFromRealm();
            }
            realm.commitTransaction();
            isSuccess = true;
        }
        catch (Exception ex)
        {
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Eliminar persona ",ex,_context);
            ex.printStackTrace();
        }
        return isSuccess;
    }

    public Realm initLocalStorage(){
        try {
            File file = new File(_context.getFilesDir(), "alphaStorage.realm");
            /*if(file.exists()){
                file.delete();
            }*/
            if(!file.exists()) {
                Realm.init(_context);
                RealmConfiguration config = new RealmConfiguration.Builder()
                        .name("alphaStorage.realm")
                        .encryptionKey(_iRsa.createKeyStorage())
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
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Creación de storage",ex,_context);
            return null;
        }
    }



}
