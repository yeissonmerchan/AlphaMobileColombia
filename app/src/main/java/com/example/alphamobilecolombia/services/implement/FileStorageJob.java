package com.example.alphamobilecolombia.services.implement;

import android.content.Context;
import android.content.Intent;

import com.example.alphamobilecolombia.data.local.entity.FileStorage;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.mvp.activity.QueryCreditActivity;
import com.example.alphamobilecolombia.mvp.models.File;
import com.example.alphamobilecolombia.mvp.presenter.IUploadFilesPresenter;
import com.example.alphamobilecolombia.utils.configuration.IFileStorageService;
import com.example.alphamobilecolombia.utils.notification.local.INotification;
import com.example.alphamobilecolombia.utils.notification.model.LocalNotification;

import java.util.ArrayList;
import java.util.List;

public class FileStorageJob {
    IUploadFilesPresenter _iUploadFilesPresenter;
    IFileStorageService _iFileStorageService;
    INotification _iNotification;
    Context _context;
    int countStop = 3;
    int countTrans = 1;

    public FileStorageJob(IUploadFilesPresenter iUploadFilesPresenter, IFileStorageService iFileStorageService, INotification iNotification, Context context) {
        _iUploadFilesPresenter = iUploadFilesPresenter;
        _iFileStorageService = iFileStorageService;
        _iNotification = iNotification;
        _context = context;
    }

    public void SendFilesToStorage() {
        List<FileStorage> fileStorages = _iFileStorageService.GetListForAll();
        List<String> listSubjectCredit = new ArrayList<>();

        if (fileStorages != null) {
            for (FileStorage file : fileStorages) {
                if (!listSubjectCredit.contains(String.valueOf(file.getIdCreditSubject()))) {
                    listSubjectCredit.add(String.valueOf(file.getIdCreditSubject()));
                }
            }

            if (listSubjectCredit != null) {
                for (String creditSubject : listSubjectCredit) {
                    if (!creditSubject.equals("0"))
                        SendFile(creditSubject);
                }
            }
        }
    }

    private boolean SendFile(String creditSubject) {
        try {
            String documentNumber = "";
            List<File> listUpload = new ArrayList<>();
            List<FileStorage> fileStoragesForCreditSubject = _iFileStorageService.GetListForCreditSubject(Integer.parseInt(creditSubject));
            for (FileStorage itemFile : fileStoragesForCreditSubject) {
                documentNumber = itemFile.getDocumentNumber();
                File newFile = new File(itemFile.getIdTypeFile(), itemFile.getName(), itemFile.isRequired(), itemFile.getNameType(), itemFile.isUpload(), itemFile.getFilePath(), true);
                listUpload = new ArrayList<>();
                listUpload.add(newFile);

                if (!itemFile.isUpload()) {
                    _iUploadFilesPresenter.SendFileList(listUpload, creditSubject, itemFile.getFilePath(), String.valueOf(documentNumber));
                    /*if (isValidSend) {
                        itemFile.setUpload(true);
                        _iFileStorageService.UpdateState(itemFile);
                    }*/
                }
            }

            boolean isPendingFiles = _iUploadFilesPresenter.PendingFiles(creditSubject);
            if (!isPendingFiles) {
                //Cosnumir api de validación
                boolean isCompletedCredit = _iUploadFilesPresenter.CompleteCredit(creditSubject);
                if (isCompletedCredit) {
                    Intent notificationIntent = new Intent(_context, QueryCreditActivity.class); // Replace ACTIVITY_TO_BE_DISPLAYED to Activity to which you wanna show
                    notificationIntent.putExtra("DocumentNumber",documentNumber);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    _iFileStorageService.DeleteForCreditSubject(Integer.parseInt(creditSubject));
                    LocalNotification localNotification = new LocalNotification();
                    localNotification.setTitle("Proceso finalizado.");
                    localNotification.setMessage(/*creditSubject + */" La solicitud de crédito para el cliente con número de documento " + documentNumber + " . Ha finalizado.");
                    localNotification.setDocumentNumber(documentNumber);
                    _iNotification.ShowNotification(localNotification,notificationIntent);
                }
            }
            else{
                countTrans = countTrans + 1;
                if (countTrans <= countStop)
                    SendFile(creditSubject);
            }


            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
