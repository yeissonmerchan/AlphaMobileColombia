package com.example.alphamobilecolombia.mvp.presenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.jibble.simpleftp.*;

public class UploadFilesPresenter {

    public void uploadFiles()
    {
        try
        {
            SimpleFTP ftp = new SimpleFTP();

            //Connect to an FTP server on port 21.
            ftp.connect("ftp.somewhere.net", 21, "username", "password");

            //Set binary mode.
            ftp.bin();

            //Change to a new working directory on the FTP server.
            ftp.cwd("web");

            //Upload some files.
            ftp.stor(new File("webcam.jpg"));
            ftp.stor(new File("comicbot-latest.png"));

            //You can also upload from an InputStream, e.g.
            ftp.stor(new FileInputStream(new File("test.png")), "test.png");
            //ftp.stor(someSocket.getInputStream(), "blah.dat");

            //Quit from the FTP server.
            ftp.disconnect();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
