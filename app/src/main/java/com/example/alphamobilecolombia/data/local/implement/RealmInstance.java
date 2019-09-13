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
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Insert",ex,_context);
        }
        return isSuccess;
    }

    public <T> boolean Insert(List<T> realmObject){
        boolean isSuccess = false;
        try {
            Realm.init(_context);

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.insert((List<RealmObject>) realmObject);
            realm.commitTransaction();

            realm.close();

            isSuccess = true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Insert",ex,_context);
        }
        return isSuccess;
    }

    public <T> boolean Update(T realmObject){
        boolean isSuccess = false;
        try {
            Realm.init(_context);

            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.copyToRealmOrUpdate((RealmObject)realmObject);
            realm.commitTransaction();

            realm.close();

            isSuccess = true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Update",ex,_context);
        }
        return isSuccess;
    }

    public <T> List<T> GetAll(RealmObject object){
        try {
            Realm.init(_context);
            Realm realm = Realm.getDefaultInstance();
            Class classObject = object.getClass();
            final RealmResults<T> data = realm.where(classObject).findAll();

            return data;
        }
        catch (Exception ex){
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"GetAll",ex,_context);
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
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"GetAllGeneric",ex,_context);
        }
        return null;
    }

    public <T> List<T> GetAllByAttribute(RealmObject object, String key, String value){
        try {
            Realm.init(_context);
            Realm realm = Realm.getDefaultInstance();
            Class classObject = object.getClass();
            final RealmResults<T> data = realm.where(classObject).equalTo(key,value).findAll();

            return data;
        }
        catch (Exception ex){
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"GetAllByAttribute",ex,_context);
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
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"GetByAttribute",ex,_context);
        }
        return null;
    }

    public <T> boolean DeleteObject(RealmObject object){
        boolean isSuccess = false;
        try {
            Realm.init(_context);
            Realm realm = Realm.getDefaultInstance();
            Class classObject = object.getClass();
            RealmResults<T> findPerson = realm.where(classObject).findAll();
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
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"DeleteObject",ex,_context);
        }
        return isSuccess;
    }

    public <T> boolean DeleteByKey(RealmObject object, String key, String value){
        boolean isSuccess = false;
        try {
            Realm.init(_context);
            Realm realm = Realm.getDefaultInstance();
            Class classObject = object.getClass();
            RealmResults<T> rowsDeleted = realm.where(classObject).equalTo(key, value).findAll();
            realm.beginTransaction();
            rowsDeleted.deleteAllFromRealm();
            realm.commitTransaction();
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"DeleteByKey",ex,_context);
        }
        return isSuccess;
    }

    public Realm InitLocalStorage(){
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("alphaStorage.realm")
                .encryptionKey(_iRsa.createKeyStorage())
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        try {
            File file = new File(_context.getFilesDir(), "alphaStorage.realm");
            /*if(file.exists()){
                file.delete();
            }*/
            if(!file.exists()) {
                Realm.init(_context);
                Realm myRealm = Realm.getInstance(config);

                return myRealm;
            }
            else{
                return null;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"InitLocalStorage",ex,_context);
            Realm.deleteRealm(config);
            Realm.getInstance(config);
            return null;
        }
    }



}
