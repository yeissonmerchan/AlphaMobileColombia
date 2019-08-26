package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Hashtable;

public class Formulario extends AppCompatActivity {

    public static Hashtable<String, Formulario> Coleccion_Formularios = new Hashtable<String, Formulario>();

    public Intent intent;

    public Formulario() {
        Agregar_Formulario(this);
    }

    public Formulario(Formulario Formulario) {

        Intent intent = new Intent(Formulario, AdditionalDataActivity.class);

    }

    public void Agregar_Formulario(Formulario Formulario) {

        intent = new Intent(Formulario, AdditionalDataActivity.class);

/*        Coleccion_Formularios.put("", value);*/

        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivityForResult(intent, 0);
    }
}
