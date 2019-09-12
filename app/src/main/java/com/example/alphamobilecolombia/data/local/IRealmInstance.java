package com.example.alphamobilecolombia.data.local;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

public interface IRealmInstance {
    <T> boolean Insert(T realmObject);
    <T> boolean Insert(List<T> realmObject);
    <T> boolean Update(T realmObject);
    <T> List<T> GetAll(RealmObject object);
    <T> List<T> GetAllByAttribute(RealmObject object, String key, String value);
    <T> List<T> GetAllGeneric(RealmObject object);
    <T> T GetByAttribute(RealmObject object, String key, String value);
    <T> boolean DeleteObject(RealmObject object);
    <T> boolean DeleteByKey(RealmObject object, String key, String value);
    Realm InitLocalStorage();
}
