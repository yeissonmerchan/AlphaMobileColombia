package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.validaciones.Formulario;

public class OtherDiscountsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    Formulario formulario;
    public OtherDiscountsActivity(){
        formulario = new Formulario(diContainer.injectISelectList(this));
    }
    //Define los campos
    EditText edt_medicina_prepagada, edt_fondo_pension_voluntario, edt_ahorro, edt_otros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_discounts);
        //***************************** Establecer campos
        edt_medicina_prepagada = (EditText) findViewById(R.id.edt_medicina_prepagada);
        edt_fondo_pension_voluntario = (EditText) findViewById(R.id.edt_fondo_pension_voluntario);
        edt_ahorro = (EditText) findViewById(R.id.edt_ahorro);
        edt_otros = (EditText) findViewById(R.id.edt_otros);
        //*****************************
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Lifecycle", "onPause()");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Lifecycle", "onStop()");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
        Log.d("Lifecycle", "onDestroy()");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    //Se produce cuando se presiona el botón Siguiente
    public void onClickBtnNewRequest(View view) {

        String[] Campos = new String[]{"edt_medicina_prepagada", "edt_fondo_pension_voluntario", "edt_ahorro", "edt_otros"};

        formulario.Validar(this, PaymentsThirdPartiesActivity.class, Campos, new String[] {});
    }
}



/*        if (new Formulario().Validar(this, Campos)) {
                //Pasar a la siguiente pagina
                Intent intent = new Intent(this, PaymentsThirdPartiesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
        }*/





/*
        if (TextUtils.isEmpty(edt_medicina_prepagada.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "El campo medicina prepagada es obligatorio", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(edt_fondo_pension_voluntario.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "El campo fondo pensión voluntario es obligatorio", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(edt_ahorro.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "El campo ahorro es obligatorio", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(edt_otros.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "El campo otros es obligatorio", Toast.LENGTH_LONG).show();
                } else {
                Intent intent = new Intent(view.getContext(), PaymentsThirdPartiesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
        }*/
