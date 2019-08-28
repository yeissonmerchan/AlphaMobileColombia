package com.example.alphamobilecolombia.data.local.implement;

public class GenericObject<T>
{
    private Class<T> type;
    public GenericObject(Class<T> cls)
    {
        type= cls;
    }
    Class<T> getType(){return type;}
}
