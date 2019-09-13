package com.example.alphamobilecolombia.mvp.adapter;

import com.example.alphamobilecolombia.data.remote.Models.Request.PostSaveDocumentRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;

import java.util.List;

import retrofit2.Call;

public interface IUploadFileAdapter {
    ApiResponse Post(PostSaveDocumentRequest postSaveDocumentRequest);
    ApiResponse Post(List<PostSaveDocumentRequest> postSaveListDocumentRequest);
    Call<String> PostAsync(PostSaveDocumentRequest postSaveDocumentRequest);
}
