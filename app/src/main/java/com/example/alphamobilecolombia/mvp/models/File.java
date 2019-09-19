package com.example.alphamobilecolombia.mvp.models;

import java.util.ArrayList;
import java.util.List;

public class File {
    private int IdType;
    private String Type;
    private String Name;
    private boolean IsRequired;
    private Boolean isPhoto;
    private String filePath;

    public boolean isUpload() {
        return IsUpload;
    }

    public void setUpload(boolean upload) {
        IsUpload = upload;
    }

    private boolean IsUpload = false;

    public File(int setIdType, String setName, boolean setRequired, String setType, boolean setUpload, String setPath, Boolean setIsPhoto) {
        IdType = setIdType;
        Name = setName;
        IsRequired = setRequired;
        Type = setType;
        IsUpload = setUpload;
        filePath = setPath;
        isPhoto = setIsPhoto;

    }

    public int getIdType() {
        return IdType;
    }

    public void setIdType(int idType) {
        IdType = idType;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isRequired() {
        return IsRequired;
    }

    public void setRequired(boolean required) {
        IsRequired = required;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Boolean getPhoto() {
        return isPhoto;
    }

    public void setPhoto(Boolean photo) {
        isPhoto = photo;
    }

    public List<File> getFiles(){
        List<File> list = new ArrayList<File>();
        list.add(new File(27,"",true,"CargueDocumentosPreValidación",false,"",true));
        //list.add(new File(67,"",true,"SolicitudCreditoCara2",false,"",true));
        list.add(new File(68,"",true,"CedulaCara1",false,"",true));
        list.add(new File(69,"",true,"CedulaCara2",false,"",true));
        list.add(new File(70,"",true,"Desprendible1",false,"",true));
        list.add(new File(71,"",true,"Desprendible2",false,"",true));
        /*list.add(new File(72,"",false,"Desprendible3",false));
        list.add(new File(73,"",false,"Desprendible4",false));
        list.add(new File(74,"",false,"FormatoFirmaRuego",false));
        list.add(new File(75,"",false,"FormatoFirmaRuego",false));
        list.add(new File(76,"",false,"CedulaTestigoFirmaRuego",false));*/

        return list;
    }
}
