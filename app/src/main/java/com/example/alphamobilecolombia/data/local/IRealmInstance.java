package com.example.alphamobilecolombia.data.local;

import java.util.List;

import io.realm.RealmObject;

public interface IRealmInstance {
    <T> boolean Insert(T realmObject);
    List<RealmObject> GetAll(RealmObject object);
}
