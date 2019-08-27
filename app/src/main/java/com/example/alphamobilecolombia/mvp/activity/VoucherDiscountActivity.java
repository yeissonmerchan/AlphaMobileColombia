package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;

public class VoucherDiscountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Define los campos de compras de cartera
    EditText edt_compras_cartera1, edt_compras_cartera2, edt_compras_cartera3, edt_compras_cartera4;

    //Define los campos de saneamientos
    EditText edt_saneamientos1, edt_saneamientos2;

    //Define la casilla de comprobación de si no hay deudas
    CheckBox checkbox_no_hay_deudas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_discount);

        edt_compras_cartera1 = (EditText) findViewById(R.id.edt_compras_cartera1);
        edt_compras_cartera2 = (EditText) findViewById(R.id.edt_compras_cartera2);
        edt_compras_cartera3 = (EditText) findViewById(R.id.edt_compras_cartera3);
        edt_compras_cartera4 = (EditText) findViewById(R.id.edt_compras_cartera4);
        edt_saneamientos1 = (EditText) findViewById(R.id.edt_saneamientos1);
        edt_saneamientos2 = (EditText) findViewById(R.id.edt_saneamientos2);

        checkbox_no_hay_deudas = (CheckBox) findViewById(R.id.checkbox_no_hay_deudas);

        checkbox_no_hay_deudas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                              @Override
                                                              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                                  if (isChecked) {
                                                                      edt_compras_cartera1.setEnabled(false);
                                                                      edt_compras_cartera2.setEnabled(false);
                                                                      edt_compras_cartera3.setEnabled(false);
                                                                      edt_compras_cartera4.setEnabled(false);
                                                                      edt_saneamientos1.setEnabled(false);
                                                                      edt_saneamientos2.setEnabled(false);
                                                                  } else {
                                                                      edt_compras_cartera1.setEnabled(true);
                                                                      edt_compras_cartera2.setEnabled(true);
                                                                      edt_compras_cartera3.setEnabled(true);
                                                                      edt_compras_cartera4.setEnabled(true);
                                                                      edt_saneamientos1.setEnabled(true);
                                                                      edt_saneamientos2.setEnabled(true);
                                                                  }
                                                              }
                                                          }
        );
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

        //Define si no hay inconsistencias en las validaciones
        boolean Correcto = false;

        //Define el mensaje que se va a mostrar en caso de que algunos de los campos no cumplan las condiciones requeridas
        String Mensaje_obligatoriedad = "";

        //Si hay deudas entonces valida los campos obligatorios
        if (!checkbox_no_hay_deudas.isChecked()) {

            if (!TextUtils.isEmpty(edt_compras_cartera1.getText().toString()) ||
                    !TextUtils.isEmpty(edt_compras_cartera2.getText().toString()) ||
                    !TextUtils.isEmpty(edt_compras_cartera3.getText().toString()) ||
                    !TextUtils.isEmpty(edt_compras_cartera4.getText().toString())
            ) {
                Correcto = true;
            } else {
                Mensaje_obligatoriedad = "Debe haber al menos un alguna compra de cartera";
            }
        } else {
            Correcto = true;
        }

        //Si hay inconsistencias en las validaciones entonces
        if (!Correcto) {
            Toast.makeText(getApplicationContext(), Mensaje_obligatoriedad, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(view.getContext(), OtherDiscountsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }
    }
}
