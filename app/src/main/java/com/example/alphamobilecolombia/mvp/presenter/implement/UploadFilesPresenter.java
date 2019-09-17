package com.example.alphamobilecolombia.mvp.presenter.implement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.alphamobilecolombia.data.local.entity.FileStorage;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostSaveDocumentRequest;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostUserRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostRetriesModelResponse;
import com.example.alphamobilecolombia.data.remote.Models.entity.UserRoleInformation;
import com.example.alphamobilecolombia.mvp.activity.ProcessCompletedActivity;
import com.example.alphamobilecolombia.mvp.adapter.ICompletedSubjectCredit;
import com.example.alphamobilecolombia.mvp.adapter.ICreditSubjectAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IPersonAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IQueryPendingFilesAdapter;
import com.example.alphamobilecolombia.mvp.adapter.IUploadFileAdapter;
import com.example.alphamobilecolombia.mvp.models.File;
import com.example.alphamobilecolombia.mvp.models.Person;
import com.example.alphamobilecolombia.mvp.presenter.ICreditSubjectPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IPersonPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IUploadFilesPresenter;
import com.example.alphamobilecolombia.utils.configuration.IFileStorageService;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.files.IFileStorage;
import com.google.api.BackendOrBuilder;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class UploadFilesPresenter implements IUploadFilesPresenter {

    IUploadFileAdapter _iUploadFileAdapter;
    IFileStorage _iFileStorage;
    IFileStorageService _iFileStorageService;
    Context _context;
    ICompletedSubjectCredit _iCompletedSubjectCredit;
    IQueryPendingFilesAdapter _iQueryPendingFilesAdapter;

    CountDownLatch executionCompleted;
    List<HttpResponse> listResponses = new ArrayList<>();
    List<PostRetriesModelResponse> lisReintentos = new ArrayList<>();
    List<com.example.alphamobilecolombia.mvp.models.File> listFiles = null;

    public UploadFilesPresenter(IUploadFileAdapter iUploadFileAdapter, IFileStorage iFileStorage, IFileStorageService iFileStorageService, Context context, ICompletedSubjectCredit iCompletedSubjectCredit, IQueryPendingFilesAdapter iQueryPendingFilesAdapter) {
        _iUploadFileAdapter = iUploadFileAdapter;
        _iFileStorage = iFileStorage;
        _iFileStorageService = iFileStorageService;
        _context = context;
        _iCompletedSubjectCredit = iCompletedSubjectCredit;
        _iQueryPendingFilesAdapter = iQueryPendingFilesAdapter;
    }

    private int GetIdDocument(String name) {
        int idDocument = 0;

        switch (name) {
            case "CargueDocumentosPreValidación":
                idDocument = 27;
                break;
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

    private String GetNameDocument(int idDocument) {
        String nameDocument = null;

        switch (idDocument) {
            case 27:
                nameDocument = "CargueDocumentosPreValidación";
                break;
            case 66:
                nameDocument = "SolicitudCreditoCara1";
                break;
            case 67:
                nameDocument = "SolicitudCreditoCara2";
                break;
            case 68:
                nameDocument = "CedulaCara1";
                break;
            case 69:
                nameDocument = "CedulaCara2";
                break;
            case 70:
                nameDocument = "Desprendible1";
                break;
            case 71:
                nameDocument = "Desprendible2";
                break;
            case 72:
                nameDocument = "Desprendible3";
                break;
            case 73:
                nameDocument = "Desprendible4";
                break;
            case 74:
                nameDocument = "FormatoFirmaRuego";
                break;
            case 75:
                nameDocument = "SelloNotariaFirmaRuego";
                break;
            case 76:
                nameDocument = "CedulaTestigoFirmaRuego";
                break;
            case 77:
                nameDocument = "TratamientoDatosPersonales";
                break;
        }

        return nameDocument;
    }

    private String getNameFile(String documentNumber, String typeFile) {
        return documentNumber + typeFile + ".jpg";
    }

    public boolean PendingFiles(String idSujetoCredito) {
        boolean isValid = true;
        ApiResponse apiResponse = _iQueryPendingFilesAdapter.Get(idSujetoCredito);
        if (apiResponse.getCodigoRespuesta() == 200) {
            try {
                String data = apiResponse.getData().toString();
                JSONArray jsonObject = new JSONArray(data);
                PostSaveDocumentRequest[] httpResponse = new Gson().fromJson(jsonObject.toString(), PostSaveDocumentRequest[].class);

                if (httpResponse != null) {
                    if (httpResponse.length == 0) {
                        isValid = false;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), idSujetoCredito, e, _context);
            } catch (Exception e) {
                e.printStackTrace();
                LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), idSujetoCredito, e, _context);
            }
        }
        return isValid;
    }

    public boolean CompleteCredit(String idSujetoCredito) {
        boolean isValid = false;
        ApiResponse apiResponse = _iCompletedSubjectCredit.Get(idSujetoCredito);
        if (apiResponse.getCodigoRespuesta() == 200) {
            isValid = true;
        }
        return isValid;
    }

    private ApiResponse Upload(com.example.alphamobilecolombia.mvp.models.File fileUpload, String codeCreditSubject, String pathFile, String documentNumber) {
        String base64 = _iFileStorage.GetFile(getNameFile(documentNumber, fileUpload.getType()), codeCreditSubject, pathFile);

        SharedPreferences sharedPref = _context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        String user = sharedPref.getString("idUser", "");
        PostSaveDocumentRequest newDocument = new PostSaveDocumentRequest();
        //newDocument.setRutaArchivo(fileUpload.getName());
        newDocument.setSujetoCreditoID(Integer.parseInt(codeCreditSubject));
        newDocument.setTipoArchivoID(GetIdDocument(fileUpload.getType()));
        newDocument.setTipoArchivoNombre(fileUpload.getType());
        newDocument.setUsuarioRegistroID(Integer.parseInt(user));
        newDocument.setExtensionArchivo(".jpg");
        newDocument.setNombreArchivo(fileUpload.getName());
        newDocument.setArchivo(base64);

        //updateUploadFileName(fileUpload.getType(),fileUpload.getName());

        return _iUploadFileAdapter.Post(newDocument);
    }

    private boolean SaveFilesLocalStorage(ApiResponse insertList, String pathFiles, String documentNumber) {
        boolean isValid = false;
        List<FileStorage> fileStorages = new ArrayList<>();
        try {
            if (insertList.getCodigoRespuesta() == 200) {
                String data = insertList.getData().toString();
                JSONArray jsonObject = new JSONArray(data);
                PostSaveDocumentRequest[] httpResponse = new Gson().fromJson(jsonObject.toString(), PostSaveDocumentRequest[].class);

                for (PostSaveDocumentRequest file : httpResponse) {
                    FileStorage fileStorage = new FileStorage();
                    fileStorage.setIdPerson(file.getUsuarioRegistroID());
                    fileStorage.setIdCreditSubject(file.getSujetoCreditoID());
                    fileStorage.setIdTypeFile(file.getTipoArchivoID());
                    fileStorage.setDocumentNumber(documentNumber);
                    fileStorage.setNameType(GetNameDocument(file.getTipoArchivoID()));
                    fileStorage.setName(file.getNombreArchivo());
                    fileStorage.setRequired(true);
                    fileStorage.setUpload(false);
                    fileStorage.setFilePath(pathFiles);
                    fileStorages.add(fileStorage);
                }

                isValid = _iFileStorageService.InsertFilesForCreditSubject(fileStorages);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "SaveFilesLocalStorage", ex, _context);
        }
        return isValid;
    }

    public boolean SaveListTotalFiles(List<com.example.alphamobilecolombia.mvp.models.File> listUpload, String codeCreditSubject, String pathFiles, String documentNumber) {

        String Nombre = "";

        try {
            List<PostSaveDocumentRequest> listSendRequest = new ArrayList<>();
            SharedPreferences sharedPref = _context.getSharedPreferences("Login", Context.MODE_PRIVATE);
            String user = sharedPref.getString("idUser", "");
            for(com.example.alphamobilecolombia.mvp.models.File file : listUpload) {
                Nombre = file.getName();
                PostSaveDocumentRequest newDocument = new PostSaveDocumentRequest();
                //newDocument.setRutaArchivo(file.getName());
                newDocument.setSujetoCreditoID(Integer.parseInt(codeCreditSubject));
                newDocument.setTipoArchivoID(GetIdDocument(file.getType()));
                newDocument.setTipoArchivoNombre(file.getType());
                newDocument.setUsuarioRegistroID(Integer.parseInt(user));
                newDocument.setExtensionArchivo(".jpg");
                newDocument.setNombreArchivo(file.getName());
                listSendRequest.add(newDocument);
            }

            ApiResponse resultFiles = _iUploadFileAdapter.Post(listSendRequest);
            return SaveFilesLocalStorage(resultFiles, pathFiles, documentNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "SaveListTotalFiles: " + Nombre, ex, _context);
        }
        return false;
    }

    public boolean SendFileList(List<com.example.alphamobilecolombia.mvp.models.File> listUpload, String codeCreditSubject, String pathFile, String documentNumber) {
        boolean isValid = false;
        try {
            List<com.example.alphamobilecolombia.mvp.models.File> listSend = new ArrayList<>();
            /*if(listFiles != null){
                if(pendingUpload().size()>0){
                    listSend = pendingUpload();
                }
            }
            else{
                listSend = listUpload;
            }*/
            listSend = listUpload;
            //executionCompleted = new CountDownLatch(listSend.size());
            for (com.example.alphamobilecolombia.mvp.models.File file : listSend) {
                try {
                    /*new Thread() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {*/
                    //System.out.println("Thread number :" + Thread.currentThread().getName());
                    ApiResponse apiResponse = Upload(file, codeCreditSubject, pathFile, documentNumber);
                    // One thread has completed its job
                    //executionCompleted.countDown();
                    if (apiResponse != null) {
                        HttpResponse httpResponse = new HttpResponse();
                        httpResponse.setCode(apiResponse.getCodigoRespuesta().toString());
                        httpResponse.setData(apiResponse.getData());
                        httpResponse.setMessage(apiResponse.getMensaje());
                        httpResponse.setNameFile(file.getType());
                        listResponses.add(httpResponse);

                        if (apiResponse.getCodigoRespuesta() == 200) {
                            isValid = true;
                        }
                    }
                        /*}
                    }.start();*/
                } catch (Exception ex) {
                    ex.printStackTrace();
                    LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "SendFileList: " + file.getName(), ex, _context);
                }
            }
            //executionCompleted.await();
            // Wait till the count down latch opens.In the given case till five
            // times countDown method is invoked
            System.out.println("All over");
            System.out.println("Cantidad de respuestas " + listResponses.size());

            //isValid = ReadResponse(codeCreditSubject,pathFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"SendFileList",ex,_context);
        }
        return isValid;
    }

    private boolean ReadResponse(String codeCreditSubject, String pathFile) {
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
                                //JSONObject objectCompleted = (JSONObject) httpResponse.getData();
                                JSONArray objectData = (JSONArray) httpResponse.getData();
                                JSONObject objectFile = new JSONObject((String) objectData.getString(0));
                                String codigoRespuesta = objectFile.getString("codigoRespuesta");
                                String nombreAnterior = objectFile.getString("nombreAnterior");
                                System.out.println("nombreAnterior:  " + nombreAnterior);
                                System.out.println("codigoRespuesta2:  " + codigoRespuesta);
                                if (!codigoRespuesta.contains("200")) {
                                    System.out.println("Paso error 400:  " + nombreAnterior);
                                    PostRetriesModelResponse postRetriesModelResponse = new PostRetriesModelResponse();
                                    postRetriesModelResponse.setModelResponse(httpResponse);
                                    postRetriesModelResponse.setNameFile(nombreAnterior);
                                    lisReintentos.add(postRetriesModelResponse);
                                    sendErrorFailedUpload(httpResponse);
                                } else {
                                    updateUploadFile(httpResponse.getNameFile());
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "ReadResponse: " + httpResponse.getNameFile(), ex, _context);
                            }
                        }
                    }
                }
            }
        }

        if (pendingUpload().size() == 0) {
            isValidSendFiles = true;
        }

        return isValidSendFiles;
    }

    private List<com.example.alphamobilecolombia.mvp.models.File> pendingUpload() {
        List<com.example.alphamobilecolombia.mvp.models.File> pendingFiles = new ArrayList<>();
        for (com.example.alphamobilecolombia.mvp.models.File file : listFiles) {
            if (!file.isUpload() && file.isRequired()) {
                pendingFiles.add(file);
            }
        }

        return pendingFiles;
    }

    private void updateUploadFileName(String fileType, String nameFile) {
        if (listFiles == null)
            listFiles = _iFileStorage.GetListFiles();

        for (com.example.alphamobilecolombia.mvp.models.File file : listFiles) {

            if (file.getType().equals(fileType)) {
                file.setName(nameFile);
            }
        }
    }


    private void updateUploadFile(String nameFile) {
        if (listFiles == null)
            listFiles = _iFileStorage.GetListFiles();

        for (com.example.alphamobilecolombia.mvp.models.File file : listFiles) {

            if (file.getType().equals(nameFile)) {
                file.setUpload(true);
            }
        }
    }

    private void sendErrorFailedUpload(HttpResponse httpResponse) {
        try {
            throw new Exception("Error de envio de archivo: " + httpResponse.getNameFile() + ". Error: " + httpResponse.getMessage());
        } catch (Exception ex) {
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "Fallo de sincronización " + httpResponse.getNameFile(), ex, _context);
        }
    }

}
