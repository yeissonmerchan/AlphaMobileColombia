package com.example.alphamobilecolombia.data.cloud.firestore;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

public interface ICloudStoreInstance {
    Task<QuerySnapshot> syncCollection(String nameCollection);
}
