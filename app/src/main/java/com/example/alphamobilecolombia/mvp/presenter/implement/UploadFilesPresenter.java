package com.example.alphamobilecolombia.mvp.presenter.implement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.alphamobilecolombia.data.remote.Models.Request.PostSaveDocumentRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostRetriesModelResponse;
import com.example.alphamobilecolombia.mvp.adapter.IUploadFileAdapter;
import com.example.alphamobilecolombia.mvp.presenter.IUploadFilesPresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.files.IFileStorage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class UploadFilesPresenter implements IUploadFilesPresenter {

    IUploadFileAdapter _iUploadFileAdapter;
    IFileStorage _iFileStorage;
    Context _context;
    CountDownLatch executionCompleted;
    List<HttpResponse> listResponses = new ArrayList<>();
    List<PostRetriesModelResponse> lisReintentos = new ArrayList<>();
    List<com.example.alphamobilecolombia.mvp.models.File> listFiles = null;

    public UploadFilesPresenter(IUploadFileAdapter iUploadFileAdapter, IFileStorage iFileStorage, Context context){
        _iUploadFileAdapter = iUploadFileAdapter;
        _iFileStorage = iFileStorage;
        _context = context;
    }

    private int GetIdDocument(String name){
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

    private ApiResponse Upload(com.example.alphamobilecolombia.mvp.models.File fileUpload, String codeCreditSubject, String pathFile){
        String base64 = _iFileStorage.GetFile(fileUpload.getName(),codeCreditSubject,pathFile);
        String pathFileLocal = pathFile+"/"+fileUpload.getName();

        SharedPreferences sharedPref = _context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        String user = sharedPref.getString("idUser", "");

        PostSaveDocumentRequest newDocument = new PostSaveDocumentRequest();
        newDocument.setRutaArchivo(fileUpload.getName());
        newDocument.setSujetoCreditoID(Integer.parseInt(codeCreditSubject));
        newDocument.setTipoArchivoID(GetIdDocument(fileUpload.getType()));
        newDocument.setTipoArchivoNombre(fileUpload.getType());
        newDocument.setUsuarioRegistroID(Integer.parseInt(user));
        newDocument.setExtensionArchivo(".jpg");
        newDocument.setNombreArchivo(fileUpload.getName());
        newDocument.setArchivo(base64);

       return _iUploadFileAdapter.Post(newDocument);
    }

    public boolean SendFileList(List<com.example.alphamobilecolombia.mvp.models.File> listUpload, String codeCreditSubject, String pathFile) {
        boolean isValid = false;
        try {
            executionCompleted = new CountDownLatch(listUpload.size());
            for (com.example.alphamobilecolombia.mvp.models.File file : listUpload) {
                try {
                    new Thread() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            System.out.println("Thread number :" + Thread.currentThread().getName());
                            ApiResponse apiResponse = Upload(file, codeCreditSubject,pathFile);
                            // One thread has completed its job
                            executionCompleted.countDown();
                            if (apiResponse != null) {
                                HttpResponse httpResponse = new HttpResponse();
                                httpResponse.setCode(apiResponse.getCodigoRespuesta().toString());
                                httpResponse.setData(apiResponse.getData());
                                httpResponse.setMessage(apiResponse.getMensaje());
                                httpResponse.setNameFile(file.getName());
                                listResponses.add(httpResponse);
                            }
                        }
                    }.start();
                } catch (Exception ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                    LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "Hilo almacenamiento " + file.getName(), ex, _context);
                }
            }
            executionCompleted.await();
            // Wait till the count down latch opens.In the given case till five
            // times countDown method is invoked
            System.out.println("All over");
            System.out.println("Cantidad de respuestas " + listResponses.size());
            isValid = ReadResponse(codeCreditSubject,pathFile);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Error en arbol de hilos ",ex,_context);
        }
        return isValid;
    }

    private boolean ReadResponse(String codeCreditSubject,String pathFile){
        boolean isValidSendFiles = false;
        if (listResponses.size() > 0) {
            lisReintentos = new ArrayList<>();
            for (HttpResponse httpResponse : listResponses) {
                if (httpResponse != null) {
                    System.out.println("Proceso de envio de archivo, Codigo de respuesta" + httpResponse.getCode());
                    System.out.println("Proceso de envio de archivo, Mensaje de respuesta" + httpResponse.getMessage());
                    System.out.println("Proceso de envio de archivo, Data de respuesta" + httpResponse.getData());
                    //System.out.println("Proceso de envio de archivo, Data de envio" + httpResponse.getSendData());

                    if (httpResponse.getCode() != null) {
                        if (!httpResponse.getCode().contains("200")) {
                            System.out.println("Paso error 400:  " + httpResponse.getCode());
                            PostRetriesModelResponse postRetriesModelResponse = new PostRetriesModelResponse();
                            postRetriesModelResponse.setModelResponse(httpResponse);
                            postRetriesModelResponse.setNameFile(httpResponse.getNameFile());
                            lisReintentos.add(postRetriesModelResponse);
                            sendErrorFailedUpload(httpResponse);
                        } else {
                            try {
                                JSONObject objectCompleted = (JSONObject) httpResponse.getData();
                                //JSONArray objectData = objectCompleted.getJSONArray("data");
                                //JSONObject objectFile = new JSONObject(objectData.getString(0));
                                String codigoRespuesta = objectCompleted.getString("codigoRespuesta");
                                String nombreAnterior = objectCompleted.getString("nombreAnterior");
                                System.out.println("nombreAnterior:  " + nombreAnterior);
                                System.out.println("codigoRespuesta2:  " + codigoRespuesta);
                                if (!codigoRespuesta.contains("200")) {
                                    System.out.println("Paso error 400:  " + nombreAnterior);
                                    PostRetriesModelResponse postRetriesModelResponse = new PostRetriesModelResponse();
                                    postRetriesModelResponse.setModelResponse(httpResponse);
                                    postRetriesModelResponse.setNameFile(nombreAnterior);
                                    lisReintentos.add(postRetriesModelResponse);
                                    sendErrorFailedUpload(httpResponse);
                                }
                                else{
                                    updateUploadFile(nombreAnterior);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "Procesando respuesta " + httpResponse.getNameFile(), ex, _context);
                            }
                        }
                    }
                }
            }
        }

        if(lisReintentos.size()>0){
            SendFileList(pendingUpload(),codeCreditSubject,pathFile);
        }
        else{
            isValidSendFiles = true;
        }

        return isValidSendFiles;
    }

    private List<com.example.alphamobilecolombia.mvp.models.File> pendingUpload(){
        List<com.example.alphamobilecolombia.mvp.models.File> pendingFiles = new ArrayList<>();
        for (com.example.alphamobilecolombia.mvp.models.File file : listFiles) {
            if (!file.isUpload() && file.isRequired()){
                pendingFiles.add(file);
            }
        }

        return pendingFiles;
    }

    private void updateUploadFile(String nameFile){
        if(listFiles == null)
            listFiles = _iFileStorage.GetListFiles();

        for (com.example.alphamobilecolombia.mvp.models.File file : listFiles) {

            if(file.getName().equals(nameFile))
            {
                file.setUpload(true);
            }
        }
    }

    private void sendErrorFailedUpload(HttpResponse httpResponse){
        try {
            throw new Exception("Error de envio de archivo: "+ httpResponse.getNameFile() + ". Error: " + httpResponse.getMessage());
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Fallo de sincronización "+httpResponse.getNameFile(),ex,_context);
        }
    }

}
