package com.example.alphamobilecolombia.data.cloud.firestore.implement;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.alphamobilecolombia.data.cloud.firestore.ICloudStoreInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static com.google.firebase.firestore.DocumentChange.Type.ADDED;
import static io.fabric.sdk.android.Fabric.TAG;

public class CloudStoreInstance implements ICloudStoreInstance {
    Context _context;
    public CloudStoreInstance(Context context){
        _context = context;
    }

    public CollectionReference instance(){
        try {
            FirebaseFirestoreSettings firestoreSettings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                    .build();
            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            rootRef.setFirestoreSettings(firestoreSettings);
            CollectionReference productsRef = rootRef.collection("Decisor");
            DocumentReference documentReference = productsRef.document();
            Query query = productsRef.whereEqualTo("Proceso", 3);

            DocumentReference contactListener = rootRef.collection("Decisor").document("Z3RYGx9bvRmAl69IPyDs");
            contactListener.addSnapshotListener(new EventListener < DocumentSnapshot > () {
                @Override
                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d("ERROR", e.getMessage());
                        return;
                    }
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        //Toast.makeText(MainActivity.this, "Current data:" + documentSnapshot.getData(), Toast.LENGTH_SHORT).show();
                    }

                }
            });


            rootRef.collection("Decisor").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (e !=null)
                    {

                    }

                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                    {
                        String   FechaUltimaActualizacion =  documentChange.getDocument().getData().get("FechaUltimaActualizacion").toString();
                        String  Proceso   =  documentChange.getDocument().getData().get("Proceso").toString();
                        String IdParameter = documentChange.getDocument().getData().get("IdParameter").toString();

                    }
                }
            });

            /*DocumentReference user = rootRef.collection("Decisor").document("Z3RYGx9bvRmAl69IPyDs");
            user.get().addOnCompleteListener(new OnCompleteListener< DocumentSnapshot >() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot doc = task.getResult();
                        StringBuilder fields = new StringBuilder("");
                        fields.append("FechaUltimaActualizacion: ").append(doc.get("FechaUltimaActualizacion"));
                        fields.append("\nProceso: ").append(doc.get("Proceso"));
                        fields.append("\nIdParameter: ").append(doc.get("IdParameter"));

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
*/
            return productsRef;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }
}
