package com.example.alphamobilecolombia.utils.models;

import java.util.ArrayList;
import java.util.List;

public class File {
    private int IdType;
    private String Type;
    private String Name;
    private boolean IsRequired;

    public boolean isUpload() {
        return IsUpload;
    }

    public void setUpload(boolean upload) {
        IsUpload = upload;
    }

    private boolean IsUpload = false;

    public File(int setIdType, String setName, boolean setRequired, String setType, boolean setUpload) {
        IdType = setIdType;
        Name = setName;
        IsRequired = setRequired;
        Type = setType;
        IsUpload = setUpload;
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

    public List<File> getFiles(){
        List<File> list = new ArrayList<File>();
        list.add(new File(66,"",true,"SolicitudCreditoCara1",false));
        list.add(new File(67,"",true,"SolicitudCreditoCara2",false));
        list.add(new File(68,"",true,"CedulaCara1",false));
        list.add(new File(69,"",true,"CedulaCara2",false));
        list.add(new File(70,"",true,"Desprendible1",false));
        list.add(new File(71,"",true,"Desprendible2",false));
        /*list.add(new File(72,"",false,"Desprendible3",false));
        list.add(new File(73,"",false,"Desprendible4",false));
        list.add(new File(74,"",false,"FormatoFirmaRuego",false));
        list.add(new File(75,"",false,"FormatoFirmaRuego",false));
        list.add(new File(76,"",false,"CedulaTestigoFirmaRuego",false));*/

        return list;
    }
}
