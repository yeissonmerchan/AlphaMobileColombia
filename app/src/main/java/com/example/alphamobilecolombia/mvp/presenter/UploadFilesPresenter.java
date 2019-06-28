package com.example.alphamobilecolombia.mvp.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jibble.simpleftp.*;
import android.graphics.Bitmap.*;

public class UploadFilesPresenter {

    public void uploadFiles(String pathFile)
    {
        try
        {
            File file = new File(pathFile);
            Bitmap bitmap1 = BitmapFactory.decodeFile(pathFile);
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();

            /*File f = new File(pathFile);


            f.createNewFile();

            //Convert bitmap to byte array
            Bitmap bitmap = bitmap1;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();*/

            if(file.exists()) {
                SimpleFTP ftp = new SimpleFTP();
                //Connect to an FTP server on port 21.
                ftp.connect("181.57.145.20", 21, "test.ftp", "Test.2019*");
                ftp.bin();
                //Change to a new working directory on the FTP server.
                ftp.cwd("web");
                //Upload some files.
                ftp.stor(file);
                //ftp.stor(new File("comicbot-latest.png"));

                //You can also upload from an InputStream, e.g.
                //ftp.stor(new FileInputStream(new File("test.png")), "test.png");
                //ftp.stor(someSocket.getInputStream(), "blah.dat");

                //Quit from the FTP server.
                ftp.disconnect();

                file.delete();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
