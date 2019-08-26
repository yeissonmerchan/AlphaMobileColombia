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

public class PaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Define la lista de pagadurías
    private List<GetPagaduriasRequest> pagadurias = new ArrayList<>();

    //Define el control combobox destino del credito
    private Spinner spinner_pagaduria, spinner_destino_credito;

    //Se produce cuando se inicia esta actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        /********************************************************************** PAGADURIA */

        ScannerPresenter scannerPresenter = new ScannerPresenter();
        HttpResponse response = scannerPresenter.getPaying(this);
        Gson gson = new Gson();
        /*contextView = this;*/
        JSONObject data = (JSONObject) response.getData();
        try {
            JSONArray jSONArray = (JSONArray) data.getJSONArray("data");
            GetPagaduriasRequest getPagaduriasRequest;
            for (int i = 0; i < jSONArray.length(); i++) {
                getPagaduriasRequest = new GetPagaduriasRequest();
                JSONObject object = (JSONObject) jSONArray.get(i);
                getPagaduriasRequest.setId(Integer.parseInt(object.getString("id")));
                getPagaduriasRequest.setNombre(object.getString("nombre"));
                pagadurias.add(getPagaduriasRequest);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "Pagadurías", ex, this);
        }

        List<String> names = new ArrayList<>();
        for (GetPagaduriasRequest p : pagadurias) {
            names.add(p.getNombre());
        }

        ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.spinner, names);

        spinner_pagaduria = (Spinner) findViewById(R.id.spinner_pagaduria);
        spinner_pagaduria.setAdapter(userAdapter);

        /********************************************************************** DESTINO DEL CREDITO */

        spinner_destino_credito = (Spinner) findViewById(R.id.spinner_destino_credito);
        ArrayAdapter<CharSequence> adapter_destino_credito = ArrayAdapter.createFromResource(this,
                R.array.spinner_credit_destination, android.R.layout.simple_spinner_item);
        adapter_destino_credito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_destino_credito.setAdapter(adapter_destino_credito);

        /***********************************************************************/
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

        if (spinner_pagaduria.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "El campo pagaduría es obligatorio", Toast.LENGTH_LONG).show();
        } else if (spinner_destino_credito.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "El campo destino del credito es obligatorio", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(view.getContext(), CustomerTypeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }
    }
}
