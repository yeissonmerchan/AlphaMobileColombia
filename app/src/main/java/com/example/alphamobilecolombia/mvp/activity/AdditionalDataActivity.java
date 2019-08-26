package com.example.alphamobilecolombia.mvp.activity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.utils.extensions.StringHelper;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.venko.api.android.cedula.DocumentManager;

public class AdditionalDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Define los controles combobox
    private Spinner spinner_departamento_expedicion_cedula, spinner_ciudad_expedicion_cedula;

    //************************** FECHA EXPEDICIÓN CEDULA

    //Define el control fecha expedición cedula
    private TextView textview_fecha_expedicion_cedula;

    //Define el evento del control fecha expedición cedula
    private DatePickerDialog.OnDateSetListener evento_fecha_expedicion_cedula;

    //**************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_data);

        //********************************************************************** COMBOBOX DEPARTAMENTO, CIUDAD

        spinner_departamento_expedicion_cedula = (Spinner) findViewById(R.id.spinner_departamento_expedicion_cedula);
        spinner_ciudad_expedicion_cedula = (Spinner) findViewById(R.id.spinner_ciudad_expedicion_cedula);

        //********************************************************************** FECHA EXPEDICIÓN CEDULA

        textview_fecha_expedicion_cedula = (TextView) findViewById(R.id.edt_fecha_expedicion_cedula);

        textview_fecha_expedicion_cedula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AdditionalDataActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        evento_fecha_expedicion_cedula,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate((long) (cal.getTimeInMillis() - (5.682e+11)));
                dialog.show();
            }
        });

        evento_fecha_expedicion_cedula = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String monthS;
                String dayS;

                monthS = String.valueOf(month);
                dayS = String.valueOf(day);

                if (monthS.length() == 1) {
                    monthS = "0" + monthS;
                }
                if (dayS.length() == 1) {
                    dayS = "0" + dayS;
                }

                String date = year + "/" + monthS + "/" + dayS;
                textview_fecha_expedicion_cedula.setText(date);
            }
        };

        /***********************************************************************/

/*        if (savedInstanceState != null) {
            // Restore value of members from saved state
            String mytext = savedInstanceState.getString("textview_fecha_expedicion_cedula");
            textview_fecha_expedicion_cedula.setText(mytext);
        } else {
            // Probably initialize members with default values for a new instance
        }*/

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
/*        if (spinner_departamento_expedicion_cedula.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "El campo departamento de expedición de la cédula es obligatorio", Toast.LENGTH_LONG).show();
        } else if (spinner_ciudad_expedicion_cedula.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "El campo ciudad de expedición de la cédula es obligatorio", Toast.LENGTH_LONG).show();
        } else*/
        if (TextUtils.isEmpty(textview_fecha_expedicion_cedula.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "La fecha de expedición de la cédula es obligatoria", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(view.getContext(), PaymentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }
    }

/*    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString("textview_fecha_expedicion_cedula", textview_fecha_expedicion_cedula.getText().toString());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }*/


}
