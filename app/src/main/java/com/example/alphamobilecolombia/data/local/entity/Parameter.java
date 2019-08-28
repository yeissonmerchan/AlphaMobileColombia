package com.example.alphamobilecolombia.data.local.entity;

import io.realm.RealmObject;

public class Parameter extends RealmObject {
    private String Key;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    private String Value;
}
