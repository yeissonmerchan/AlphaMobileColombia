package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;

public class PaymentsThirdPartiesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Define los controles combobox
    private Spinner spinner_pago_a_terceros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_third_parties);

        spinner_pago_a_terceros = (Spinner) findViewById(R.id.spinner_pago_a_terceros);
        spinner_pago_a_terceros.setOnItemSelectedListener(this);
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    //Se produce cuando se presiona el botón Siguiente
    public void onClickBtnNewRequest(View view) {
/*
        String selected = "";

        if (spinner_pago_a_terceros.getSelectedItem() != null) {
            //Obtiene el elemento seleccionado
            selected = spinner_pago_a_terceros.getSelectedItem().toString();
        }

        if (TextUtils.isEmpty(selected.trim())) {
            Toast.makeText(getApplicationContext(), "El selector pago a terceros es obligatorio", Toast.LENGTH_LONG).show();
        } else {*/
            Intent intent = new Intent(view.getContext(), CreditSimulatorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        /*}*/
    }

}
