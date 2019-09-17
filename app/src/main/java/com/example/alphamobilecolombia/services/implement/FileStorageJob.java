package com.example.alphamobilecolombia.services.implement;

import com.example.alphamobilecolombia.data.local.entity.FileStorage;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.mvp.models.File;
import com.example.alphamobilecolombia.mvp.presenter.IUploadFilesPresenter;
import com.example.alphamobilecolombia.utils.configuration.IFileStorageService;
import com.example.alphamobilecolombia.utils.notification.local.INotification;
import com.example.alphamobilecolombia.utils.notification.model.LocalNotification;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

public class FileStorageJob {
    IUploadFilesPresenter _iUploadFilesPresenter;
    IFileStorageService _iFileStorageService;
    INotification _iNotification;
    int countStop = 3;
    int countTrans = 1;
    private Bus bus;

    public FileStorageJob(IUploadFilesPresenter iUploadFilesPresenter, IFileStorageService iFileStorageService, INotification iNotification) {
        _iUploadFilesPresenter = iUploadFilesPresenter;
        _iFileStorageService = iFileStorageService;
        _iNotification = iNotification;
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
            int documentNumber = 0;
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
                    _iFileStorageService.DeleteForCreditSubject(Integer.parseInt(creditSubject));
                    LocalNotification localNotification = new LocalNotification();
                    localNotification.setTitle("Proceso finalizado.");
                    localNotification.setMessage(/*creditSubject + */" La solicitud de crédito para el cliente con número de documento " + documentNumber + " . Ha finalizado.");
                    _iNotification.ShowNotification(localNotification);
                }else {
                    bus.post("FAIL");
                }
            } else {
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
