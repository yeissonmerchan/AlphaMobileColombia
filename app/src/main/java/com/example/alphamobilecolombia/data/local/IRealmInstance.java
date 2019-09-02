package com.example.alphamobilecolombia.data.local;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

public interface IRealmInstance {
    <T> boolean Insert(T realmObject);
    List<RealmObject> GetAll(RealmObject object);
    <T> List<T> GetAllGeneric(RealmObject object);
    <T> T GetByAttribute(RealmObject object, String key, String value);
    <T> boolean deleteObject(RealmObject object);
    Realm initLocalStorage();
}
