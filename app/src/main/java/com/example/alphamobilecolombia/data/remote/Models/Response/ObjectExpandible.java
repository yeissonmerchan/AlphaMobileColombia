package com.example.alphamobilecolombia.data.remote.Models.Response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ObjectExpandible implements Parcelable {

    public String title;
    public List<PostQueryCredit> list;

    public ObjectExpandible(Parcel in) {
        title = in.readString();
    }

    public static final Creator<ObjectExpandible> CREATOR = new Creator<ObjectExpandible>() {
        @Override
        public ObjectExpandible createFromParcel(Parcel in) {
            return new ObjectExpandible(in);
        }

        @Override
        public ObjectExpandible[] newArray(int size) {
            return new ObjectExpandible[size];
        }
    };

    public ObjectExpandible() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
    }
}
