package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ClientDescription implements Parcelable {
    public final String estadoText;
    public final String pagaduriaText;
    public final String fechaEnvioPrevaText;
    public final String montoSugeridoText;
    public final String cuotaSugText;
    public final String plazoSugeridoText;
    public final String fechaPrevalidacionText;
    public final String observacionPrevaText;
    public final String observacionCreditoText;



    public ClientDescription(String estadoText, String pagaduriaText, String fechaEnvioPrevaText, String montoSugeridoText, String cuotaSugText, String plazoSugeridoText, String fechaPrevalidacionText, String observacionPrevaText, String observacionCreditoText) {
        this.estadoText = estadoText;
        this.pagaduriaText = pagaduriaText;
        this.fechaEnvioPrevaText = fechaEnvioPrevaText;
        this.montoSugeridoText = montoSugeridoText;
        this.cuotaSugText = cuotaSugText;
        this.plazoSugeridoText = plazoSugeridoText;
        this.fechaPrevalidacionText = fechaPrevalidacionText;
        this.observacionPrevaText = observacionPrevaText;
        this.observacionCreditoText = observacionCreditoText;
    }


    protected ClientDescription(Parcel in) {
        estadoText = in.readString();
        pagaduriaText = in.readString();
        fechaEnvioPrevaText = in.readString();
        montoSugeridoText = in.readString();
        cuotaSugText = in.readString();
        plazoSugeridoText = in.readString();
        fechaPrevalidacionText = in.readString();
        observacionPrevaText = in.readString();
        observacionCreditoText = in.readString();

    }

    public static final Creator<ClientDescription> CREATOR = new Creator<ClientDescription>() {
        @Override
        public ClientDescription createFromParcel(Parcel in) {
            return new ClientDescription(in );
        }

        @Override
        public ClientDescription[] newArray(int size) {
            return new ClientDescription[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(estadoText);
        parcel.writeString(pagaduriaText);
        parcel.writeString(fechaEnvioPrevaText);
        parcel.writeString(montoSugeridoText);
        parcel.writeString(cuotaSugText);
        parcel.writeString(plazoSugeridoText);
        parcel.writeString(fechaPrevalidacionText);
        parcel.writeString(observacionPrevaText);
        parcel.writeString(observacionCreditoText);
    }
}
