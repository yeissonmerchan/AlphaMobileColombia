package com.example.alphamobilecolombia.data.remote.Models.entity;

import com.google.gson.annotations.SerializedName;

public class UserRoleInformation {
    @SerializedName("idUsuario")
    private Long IdUser;
    @SerializedName("contrasena")
    private String Password;
    @SerializedName("idRol")
    private Long IdRole;
    @SerializedName("rolDescripc")
    private String RoleDescription;
    @SerializedName("idPersona")
    private Long IdPerson;
    @SerializedName("cambioClave")
    private Boolean ChangePassword;
    @SerializedName("nombreCompleto")
    private String FullName;
    @SerializedName("idRegional")
    private Long IdRegional;
    @SerializedName("regionalNombre")
    private String RegionalName;
    @SerializedName("nombreSucursal")
    private String BranchOfficeName;
    @SerializedName("bloqueoUsuario")
    private Integer UserBlocked;


}
