package com.example.alphamobilecolombia.mvp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Models.GetPagaduriasRequest;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.mvp.presenter.QueryActiveValidationPresenter;
import com.example.alphamobilecolombia.mvp.presenter.ScannerPresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.mvp.models.Person;
import com.example.alphamobilecolombia.mvp.models.Persona;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphamobilecolombia.R;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.venko.api.android.cedula.DocumentManager;

public class OtherDiscountsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Define los campos
    EditText edt_medicina_prepagada, edt_fondo_pension_voluntario, edt_ahorro, edt_otros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //
        super.onCreate(savedInstanceState);

        //
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
        }
    }
}
