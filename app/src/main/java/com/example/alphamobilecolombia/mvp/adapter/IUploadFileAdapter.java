package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Request.PostSaveDocumentRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;

import retrofit2.Call;

public interface IUploadFileAdapter {
    ApiResponse Post(PostSaveDocumentRequest postSaveDocumentRequest);
    Call<String> PostAsync(PostSaveDocumentRequest postSaveDocumentRequest);
}
