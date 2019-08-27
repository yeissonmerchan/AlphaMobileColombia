package com.example.alphamobilecolombia.mvp.presenter;

import com.example.alphamobilecolombia.mvp.models.File;

import java.util.List;

public interface IUploadFilesPresenter {
    boolean SendFileList(List<File> listUpload, String codeCreditSubject, String pathFile);
}
