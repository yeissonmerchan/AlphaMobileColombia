package com.example.alphamobilecolombia.mvp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.graphics.Matrix;
import android.util.Base64;
import java.util.List;
import java.util.concurrent.Callable;

import org.jibble.simpleftp.*;
import org.json.JSONObject;

import android.os.Build;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Enviroment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.PostSaveDocuments;
import com.example.alphamobilecolombia.data.remote.PostGuardarDocumentos;
import com.example.alphamobilecolombia.utils.models.Person;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UploadFilesPresenter {

    public void uploadFiles(String pathFile, Context context)
    {
        try
        {
            File file = new File(pathFile);
            Bitmap bitmap1 = BitmapFactory.decodeFile(pathFile);
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();

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



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public HttpResponse PostGuardarDocumentos(List<com.example.alphamobilecolombia.utils.models.File> filesUpload, Context context, String idSujetoCredito) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //TODO: Cambiar a implementación de flavors
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_storage),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8083/").addConverterFactory(ScalarsConverterFactory.create()).build();//Pruebas
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apps.vivecreditos.com:8083/").addConverterFactory(ScalarsConverterFactory.create()).build();//Producción
            PostGuardarDocumentos postService = retrofit.create(PostGuardarDocumentos.class);

            RealmStorage storage = new RealmStorage();
            Gson gson = new Gson();
            List<PostSaveDocuments> listDocuments = new ArrayList<PostSaveDocuments>();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String sujetoCredito = preferences.getString("codigoTransaccion", "DEFAULT");

            SharedPreferences sharedPref = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
            String user = sharedPref.getString("idUser", "");

            Person person = storage.getPerson(context);
            for(com.example.alphamobilecolombia.utils.models.File file : filesUpload) {
                String nameFile = file.getName();
                //String pathFile = person.getNumber()+nameFile+".jgp";

                String pathFileLocal = context.getExternalFilesDir(null)+"/"+nameFile;
                File fileLocal = new File(pathFileLocal);
                Bitmap bitmap1 = BitmapFactory.decodeFile(pathFileLocal);
                OutputStream os = new BufferedOutputStream(new FileOutputStream(fileLocal));
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.close();

                String base64 = "";
                try {
                    byte[] buffer = new byte[(int) fileLocal.length() + 100];
                    @SuppressWarnings("resource")
                    int length = new FileInputStream(fileLocal).read(buffer);
                    base64 = Base64.encodeToString(buffer, 0, length,
                            Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                PostSaveDocuments newDocument = new PostSaveDocuments();
                newDocument.setRutaArchivo(nameFile);
                newDocument.setSujetoCreditoID(Integer.parseInt(idSujetoCredito));
                newDocument.setTipoArchivoID(GetIdDocument(file.getType()));
                newDocument.setTipoArchivoNombre(file.getType());
                newDocument.setUsuarioRegistroID(Integer.parseInt(user));
                newDocument.setExtensionArchivo(".jpg");
                newDocument.setNombreArchivo(nameFile);
                newDocument.setArchivo(base64);

                listDocuments.add(newDocument);
                fileLocal.delete();
            }


            String data = gson.toJson(listDocuments);
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.Upload( body1 );

            Response response = call.execute();

            JSONObject jsonObject = new JSONObject(response.body().toString());
            String value = jsonObject.toString();
            responseModel.setCode("200");
            responseModel.setData(jsonObject);
            responseModel.setMessage("Solicitud de acceso correcta.");

            return responseModel;

        }
        catch (Exception ex){
            System.out.println("Ha ocurrido un error! "+ex.getMessage());
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static HttpResponse PostGuardarDocumentos(com.example.alphamobilecolombia.utils.models.File filesUpload, Context context, String idSujetoCredito) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            //TODO: Cambiar a implementación de flavors
            String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_storage),context);//Obtener Ip a partir de configuración
            Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(ScalarsConverterFactory.create()).build();
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://181.57.145.20:8083/").addConverterFactory(ScalarsConverterFactory.create()).build();//Pruebas
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("http://apps.vivecreditos.com:8083/").addConverterFactory(ScalarsConverterFactory.create()).build();//Producción
            PostGuardarDocumentos postService = retrofit.create(PostGuardarDocumentos.class);

            RealmStorage storage = new RealmStorage();
            Gson gson = new Gson();
            List<PostSaveDocuments> listDocuments = new ArrayList<PostSaveDocuments>();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String sujetoCredito = preferences.getString("codigoTransaccion", "DEFAULT");

            SharedPreferences sharedPref = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
            String user = sharedPref.getString("idUser", "");

            Person person = storage.getPerson(context);

                String nameFile = filesUpload.getName();
                //String pathFile = person.getNumber()+nameFile+".jgp";
                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                String pathFileLocal = context.getExternalFilesDir(null)+"/"+nameFile;
                File fileLocal = new File(pathFileLocal);
                Bitmap bitmap1 = BitmapFactory.decodeFile(pathFileLocal);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
                OutputStream os = new BufferedOutputStream(new FileOutputStream(fileLocal));
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                os.close();

                String base64 = "";
                try {
                    byte[] buffer = new byte[(int) fileLocal.length() + 100];
                    @SuppressWarnings("resource")
                    int length = new FileInputStream(fileLocal).read(buffer);
                    base64 = Base64.encodeToString(buffer, 0, length,
                            Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                PostSaveDocuments newDocument = new PostSaveDocuments();
                newDocument.setRutaArchivo(nameFile);
                newDocument.setSujetoCreditoID(Integer.parseInt(idSujetoCredito));
                newDocument.setTipoArchivoID(GetIdDocument(filesUpload.getType()));
                newDocument.setTipoArchivoNombre(filesUpload.getType());
                newDocument.setUsuarioRegistroID(Integer.parseInt(user));
                newDocument.setExtensionArchivo(".jpg");
                newDocument.setNombreArchivo(nameFile);
                newDocument.setArchivo(base64);

                listDocuments.add(newDocument);
            fileLocal.delete();



            String data = gson.toJson(listDocuments);
            RequestBody body1 = RequestBody.create( MediaType.parse("application/json"), data);

            Call<String> call = postService.Upload( body1 );

            Response response = call.execute();

            JSONObject jsonObject;
            if (!(response.code() != 200)) {
                jsonObject = new JSONObject(response.body().toString());
                String value = jsonObject.toString();
                responseModel.setCode(String.valueOf(response.code()));
                responseModel.setData(jsonObject);
                responseModel.setMessage(response.message());
            }
            else{
                String errorResponse = response.errorBody().string();
                JSONObject object = new JSONObject(errorResponse);
                responseModel.setCode(String.valueOf(response.code()));
                responseModel.setData(object);
                responseModel.setMessage(String.valueOf(object.get("mensaje")));
            }

            return responseModel;

        }
        catch (Exception ex){
            System.out.println("Ha ocurrido un error! "+ex.getMessage());
        }
        return null;
    }


    public static int GetIdDocument(String name){
        int idDocument = 0;

        switch(name){
            case "SolicitudCreditoCara1":
                idDocument = 66;
                break;
            case "SolicitudCreditoCara2":
                idDocument = 67;
                break;
            case "CedulaCara1":
                idDocument = 68;
                break;
            case "CedulaCara2":
                idDocument = 69;
                break;
            case "Desprendible1":
                idDocument = 70;
                break;
            case "Desprendible2":
                idDocument = 71;
                break;
            case "Desprendible3":
                idDocument = 72;
                break;
            case "Desprendible4":
                idDocument = 73;
                break;
            case "FormatoFirmaRuego":
                idDocument = 74;
                break;
            case "SelloNotariaFirmaRuego":
                idDocument = 75;
                break;
            case "CedulaTestigoFirmaRuego":
                idDocument = 76;
                break;
            case "TratamientoDatosPersonales":
                idDocument = 77;
                break;
        }

        return idDocument;
    }


}
