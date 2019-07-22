package com.example.alphamobilecolombia.data.remote.Models;

public class ModelReintentos {
    public HttpResponse getModelResponse() {
        return ModelResponse;
    }

    public void setModelResponse(HttpResponse modelResponse) {
        ModelResponse = modelResponse;
    }

    public String getNameFile() {
        return NameFile;
    }

    public void setNameFile(String nameFile) {
        NameFile = nameFile;
    }

    private HttpResponse ModelResponse;
    private String NameFile;
}
