package com.example.alphamobilecolombia;

import com.example.alphamobilecolombia.utils.cryptography.implement.MD5Hashing;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.*;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CryptographyTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void hashMd5() throws Exception {

        String text = "123456789";
        //String textHashing = instanceCryptography.encryptText(text);
        //assertEquals("5L6D1+wynH5hf5oYgQvmkA==",textHashing);
        //String textHashing = MD5Hashing.encrypt(text);
        //String textHashing = encrypt(text);
        //String decode = decrypt(textHashing);

        //String target="1234567890";
        //String encrypted=new String(encode(target.getBytes()));
        //String decrypted=new String(decode(encrypted.getBytes()));

        //System.out.println("String To Encrypt: "+ target);
        //System.out.println("Encrypted String:" + encrypted);
        //System.out.println("Decrypted String:" + decrypted);
    }

    private static String myKey = "Code?0505050505";

    public String harden(String unencryptedString) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest(MD5Hashing.MD5(myKey).getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] plainTextBytes = unencryptedString.getBytes("utf-8");
        byte[] buf = cipher.doFinal(plainTextBytes);
        byte[] base64Bytes = Base64.getEncoder().encode(buf);
        String base64EncryptedString = new String(base64Bytes);

        return base64EncryptedString;
    }

    public String soften(String encryptedString) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(encryptedString == null)
        {
            return "";
        }
        byte[] message = Base64.getDecoder().decode(encryptedString.getBytes("utf-8"));

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digestOfPassword = md.digest(myKey.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");

        Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] plainText = decipher.doFinal(message);

        return new String(plainText, "UTF-8");

    }


    SecretKey key;
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;

    public String encrypt(String unencryptedString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        myEncryptionKey = "x2";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = MD5Hashing.MD5(myKey).getBytes("utf-8");
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        key = skf.generateSecret(ks);
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.getEncoder().encode((encryptedText)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }


    public String decrypt(String encryptedString) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        myEncryptionKey = "ThisIsSpartaThisIsSparta";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = MD5Hashing.MD5(myKey).getBytes("utf-8");
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        key = skf.generateSecret(ks);
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.getDecoder().decode(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }





    private static String TRIPLE_DES_TRANSFORMATION = "DESede/ECB/PKCS5Padding";
    private static String ALGORITHM = "DESede";
    private static String BOUNCY_CASTLE_PROVIDER = "BC";
    private Cipher encrypter;
    private Cipher decrypter;
    private static byte[] sharedkey = new byte[]{5, (byte)59, (byte)45, (byte)189, (byte)37, (byte)129, (byte)147, (byte)252, (byte)192, (byte)12, (byte)18, (byte)124, (byte)162, (byte)118, (byte)90, (byte)235};

    public byte[] encode(byte[] input) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, NoSuchProviderException, InvalidKeySpecException, UnsupportedEncodingException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] digestOfPassword2  = MD5Hashing.MD5(myKey).getBytes();
        String cifrar = MD5Hashing.MD5(myKey);
        byte[] digestOfPassword = MD5Hashing.MD5(myKey).getBytes("utf-8");
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        String newCadena = new String(digestOfPassword,"utf-8");
        byte[] arraynew = newCadena.getBytes("ISO-8859-1");
        String Message=new String(digestOfPassword,0,16, "UTF-16");
        String Message2=new String(digestOfPassword,0,16, "US-ASCII");
        byte[] arraynew2 = Message2.getBytes("ISO-8859-1");
        String Message3=new String(digestOfPassword,0,16, "UTF-16BE");
        String Message4=new String(digestOfPassword,0,16, "UTF-16LE");

        byte[] encoded = cifrar.getBytes("utf-16be");



        SecretKey keySpec = new SecretKeySpec(arraynew2, ALGORITHM);
        encrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION, BOUNCY_CASTLE_PROVIDER);
        encrypter.init(Cipher.ENCRYPT_MODE, keySpec);
        return encrypter.doFinal(input);
    }

    public byte[] decode(byte[] input) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, NoSuchProviderException, UnsupportedEncodingException {
        byte[] digestOfPassword = MD5Hashing.MD5(myKey).getBytes("utf-8");
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }
        Security.addProvider(new BouncyCastleProvider());
        SecretKey keySpec = new SecretKeySpec(sharedkey, ALGORITHM);
        decrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION, BOUNCY_CASTLE_PROVIDER);
        decrypter.init(Cipher.DECRYPT_MODE, keySpec);
        return decrypter.doFinal(input);
    }
}
