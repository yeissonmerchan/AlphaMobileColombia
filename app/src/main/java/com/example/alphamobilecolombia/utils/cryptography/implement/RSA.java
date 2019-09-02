package com.example.alphamobilecolombia.utils.cryptography.implement;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Log;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.cryptography.providers.IRSA;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;

public class RSA implements IRSA {

    Context _context;
    public RSA(Context context){
        _context = context;
    }

    public byte[] createKeyStorage() {
        try {

            String license = _context.getResources().getString(R.string.local_storage_Key);
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            end.add(Calendar.YEAR, 1);
            KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(_context)
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
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Crear llaves",ex,_context);
            Log.e("Key Realm", Log.getStackTraceString(ex));
        }
        return null;
    }
}
