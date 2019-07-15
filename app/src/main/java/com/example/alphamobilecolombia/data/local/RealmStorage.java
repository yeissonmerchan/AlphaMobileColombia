package com.example.alphamobilecolombia.data.local;


import android.content.Context;
import android.security.KeyPairGeneratorSpec;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.PostConsultarReporteCreditoResponse;
import com.example.alphamobilecolombia.utils.models.Person;

import java.security.KeyStoreException;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;
import android.util.Log;

import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;

import io.realm.Realm;
import io.realm.*;
import java.math.BigInteger;
import java.security.*;
import java.util.List;

public class RealmStorage {

    public Realm initLocalStorage(Context context){
        try {

            Realm.init(context);
            String license = context.getResources().getString(R.string.local_storage_Key);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name("alphaStorage.realm")
                    .encryptionKey(createNewKeys(context))
                    .schemaVersion(42)
                    .build();

            Realm myRealm = Realm.getInstance(config);
            return myRealm;
        }
        catch (Exception ex){
            return null;
        }
    }

    public void savePerson(Context context, Person person){
        Realm.init(context);

        Realm realm = Realm.getDefaultInstance();

        final RealmResults<Person> findPerson = realm.where(Person.class).equalTo("number", person.getNumber()).findAll();

        if(!(findPerson.size() > 0)){
            // Persist your data in a transaction
            realm.beginTransaction();
            final Person managedDog = realm.copyToRealm(person); // Persist unmanaged objects
            realm.commitTransaction();
        }
    }


    public Person getPerson(Context context){
        try {
            Realm.init(context);

            Realm realm = Realm.getDefaultInstance();

            final RealmResults<Person> findPerson = realm.where(Person.class).findAll();

            return findPerson.first();
        }
        catch (Exception ex){
            return null;
        }
    }

    public void saveConsultaCreditro(Context context, List<PostConsultarReporteCreditoResponse> data){
        try {
            Realm.init(context);

            Realm realm = Realm.getDefaultInstance();

            // final RealmResults<Person> findPerson = realm.where(Person.class).equalTo("number", data.getNumber()).findAll();

            //if(!(findPerson.size() > 0)){
            // Persist your data in a transaction
            realm.beginTransaction();
            realm.copyToRealm(data); // Persist unmanaged objects
            realm.commitTransaction();
            //}
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public RealmResults<PostConsultarReporteCreditoResponse> getConsultaCreditro(Context context){
        try {
            Realm.init(context);

            Realm realm = Realm.getDefaultInstance();

            final RealmResults<PostConsultarReporteCreditoResponse> data = realm.where(PostConsultarReporteCreditoResponse.class).findAll();

            return data;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void deleteInfoConsultaCreditro(Context context){
        try {
            Realm.init(context);
            Realm realm = Realm.getDefaultInstance();
            final RealmResults<PostConsultarReporteCreditoResponse> data = realm.where(PostConsultarReporteCreditoResponse.class).findAll();
            /*for (Person object : findPerson) {
                object.deleteFromRealm();
            }*/
            realm.beginTransaction();
            realm.delete(PostConsultarReporteCreditoResponse.class);
            if (null != data && data.size() > 0) {
                data.deleteAllFromRealm();
            }
            realm.commitTransaction();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void deleteTable(Context context){
        try {
            Realm.init(context);
            Realm realm = Realm.getDefaultInstance();
            final RealmResults<Person> findPerson = realm.where(Person.class).findAll();
            realm.beginTransaction();
            realm.delete(Person.class);
            if (null != findPerson && findPerson.size() > 0) {
                findPerson.deleteAllFromRealm();
            }
            realm.commitTransaction();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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

        } catch (Exception e) {
            Log.e("Key Realm", Log.getStackTraceString(e));
        }
        return null;
    }

}
