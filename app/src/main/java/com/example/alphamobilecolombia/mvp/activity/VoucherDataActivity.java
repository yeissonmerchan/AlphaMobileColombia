package com.example.alphamobilecolombia.mvp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
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

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import co.venko.api.android.cedula.DocumentManager;
//import faranjit.currency.edittext.CurrencyEditText;
import me.abhinay.input.CurrencyEditText;
/*import me.abhinay.input.CurrencyEditText;*/


public class VoucherDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Define el control campo ingresos
    EditText edt_ingresos;

    //Defineel control campo salud
    EditText edt_salud;

    //Define el control campo pension
    EditText edt_pension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_data);

        edt_ingresos = (EditText) findViewById(R.id.edt_ingresos);
        edt_salud = (EditText) findViewById(R.id.edt_salud);
        edt_pension = (EditText) findViewById(R.id.edt_pension);
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
        if (TextUtils.isEmpty(edt_ingresos.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo ingresos es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edt_salud.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo salud es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edt_pension.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo pensión es obligatorio", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(view.getContext(), VoucherDiscountActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }
    }


}




/*    //Define el control campo ingresos
    CurrencyEditText edt_ingresos;

    //Defineel control campo salud
    CurrencyEditText edt_salud;

    //Define el control campo pension
    CurrencyEditText edt_pension;*/

/*        //********************************************* INGRESOS

        edt_ingresos = (CurrencyEditText) findViewById(R.id.edt_ingresos);
        edt_ingresos.setCurrency("$");
        edt_ingresos.setDelimiter(false);
        edt_ingresos.setSpacing(true);
        edt_ingresos.setDecimals(false);
        edt_ingresos.setSeparator(".");

        //********************************************* SALUD

        edt_salud = (CurrencyEditText) findViewById(R.id.edt_salud);
        edt_salud.setCurrency("$");
        edt_salud.setDelimiter(false);
        edt_salud.setSpacing(true);
        edt_salud.setDecimals(false);
        edt_salud.setSeparator(".");

        //********************************************* PENSION

        edt_pension = (CurrencyEditText) findViewById(R.id.edt_pension);
        edt_pension.setCurrency("$");
        edt_pension.setDelimiter(false);
        edt_pension.setSpacing(true);
        edt_pension.setDecimals(false);
        edt_pension.setSeparator(".");

        //*********************************************
        EditText mEditText = (EditText) findViewById(R.id.mEditText);
        *//* don't allow user to move cursor while entering price *//*
        mEditText.setMovementMethod(null);

        mEditText.addTextChangedListener(new TextWatcher() {
            private String current = "";
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            private double parsed;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    *//* remove listener to prevent stack overflow from infinite loop *//*
                    mEditText.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[$ ,]", "");

                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (java.lang.NumberFormatException e) {
                        parsed = 0;
                    }

                    formatter.setMaximumFractionDigits(0);
                    String formatted = formatter.format(parsed);

                    current = formatted;
                    mEditText.setText(formatted);

                    *//* add listener back *//*
                    mEditText.addTextChangedListener(this);
                    *//* print a toast when limit is reached... see xml below.
 * this is for 6 chars *//*
                    if (start == 7) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Maximum Limit Reached", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

