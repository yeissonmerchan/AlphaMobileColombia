package com.example.alphamobilecolombia.mvp.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import android.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Enviroment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.PostSaveDocuments;
import com.example.alphamobilecolombia.data.remote.PostGuardarDocumentos;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UploadFilesPresenter {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static HttpResponse Post(com.example.alphamobilecolombia.mvp.models.File filesUpload, Context context, String codeCreditSubject, String pathFile) {
        final HttpResponse responseModel = new HttpResponse();
        try {
            //TODO: Quitar el policy y poner asíncrono
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Gson gson = new Gson();
            List<PostSaveDocuments> listDocuments = new ArrayList<PostSaveDocuments>();

            SharedPreferences sharedPref = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
            String user = sharedPref.getString("idUser", "");

                String nameFile = filesUpload.getName();
                File fileLocal;
                boolean isValidUpload = false;
                String pathFileLocal = pathFile+"/"+nameFile;
                String base64 = "";
                try {
                    fileLocal = new File(pathFileLocal);

                    byte[] buffer = new byte[(int) fileLocal.length() + 100];
                    int length = new FileInputStream(fileLocal).read(buffer);
                    base64 = Base64.encodeToString(buffer, 0, length,
                            Base64.DEFAULT);
                    isValidUpload = true;
                } catch (Exception ex) {
                    LogError.SendErrorCrashlytics(context.getClass().getSimpleName(),codeCreditSubject,ex,context);
                    System.out.println("Ha ocurrido un error en la lectura del archivo! "+ex.getMessage());
                    ex.printStackTrace();
                }

                if(isValidUpload) {
                    PostSaveDocuments newDocument = new PostSaveDocuments();
                    newDocument.setRutaArchivo(nameFile);
                    newDocument.setSujetoCreditoID(Integer.parseInt(codeCreditSubject));
                    newDocument.setTipoArchivoID(GetIdDocument(filesUpload.getType()));
                    newDocument.setTipoArchivoNombre(filesUpload.getType());
                    newDocument.setUsuarioRegistroID(Integer.parseInt(user));
                    newDocument.setExtensionArchivo(".jpg");
                    newDocument.setNombreArchivo(nameFile);
                    newDocument.setArchivo(base64);

                    listDocuments.add(newDocument);
                    //fileLocal.delete();

                    String data = gson.toJson(listDocuments);
                    RequestBody body1 = RequestBody.create(MediaType.parse("application/json"), data);
                    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .build();

                    //TODO: Cambiar a implementación de flavors
                    String urlApi = ApiEnviroment.GetIpAddressApi(context.getResources().getString(R.string.api_storage),context);//Obtener Ip a partir de configuración
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(urlApi).client(okHttpClient).addConverterFactory(ScalarsConverterFactory.create()).build();
                    PostGuardarDocumentos postService = retrofit.create(PostGuardarDocumentos.class);

                    Call<String> call = postService.Upload(body1);
                    Response response = null;

                    try{
                        response = call.execute();
                    }
                    catch (Exception ex){
                        System.out.println("Ha ocurrido un error en la ejecución! "+ex.getMessage());
                        System.out.println("Ha ocurrido un error! Traza: "+ex.getStackTrace());
                        System.out.println("Ha ocurrido un error! "+ ex);
                        ex.printStackTrace();
                        LogError.SendErrorCrashlytics(context.getClass().getSimpleName(),codeCreditSubject,ex,context);
                    }

                    JSONObject jsonObject;
                    if (response != null) {
                        if (!(response.code() != 200)) {
                            jsonObject = new JSONObject(response.body().toString());
                            String value = jsonObject.toString();
                            responseModel.setCode(String.valueOf(response.code()));
                            responseModel.setData(jsonObject);
                            responseModel.setMessage(response.message());
                            responseModel.setSendData(data);
                            responseModel.setNameFile(filesUpload.getName());
                        } else {
                            String errorResponse = response.errorBody().string();
                            System.out.println("Trama de error! "+ errorResponse);
                            try {
                                JSONObject object = new JSONObject(errorResponse);
                                responseModel.setCode(String.valueOf(response.code()));
                                responseModel.setData(object);
                                responseModel.setMessage(String.valueOf(object.get("mensaje")));
                                responseModel.setSendData(data);
                                responseModel.setNameFile(filesUpload.getName());
                            }
                            catch (Exception ex){
                                responseModel.setCode(String.valueOf(response.code()));
                                responseModel.setData(errorResponse);
                                responseModel.setMessage("Error mapeo a json de error");
                                responseModel.setSendData(data);
                                responseModel.setNameFile(filesUpload.getName());
                                LogError.SendErrorCrashlytics(context.getClass().getSimpleName(),codeCreditSubject,ex,context);
                            }

                        }
                    }

                    return responseModel;
                }

        }
        catch (Exception ex){
            System.out.println("Ha ocurrido un error! "+ ex.getMessage());
            System.out.println("Ha ocurrido un error! Traza: "+ex.getStackTrace());
            System.out.println("Ha ocurrido un error! "+ ex);
            ex.printStackTrace();
            Log.d("Error servicio", String.valueOf(ex.getStackTrace()));
            LogError.SendErrorCrashlytics(context.getClass().getSimpleName(),codeCreditSubject,ex,context);
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
