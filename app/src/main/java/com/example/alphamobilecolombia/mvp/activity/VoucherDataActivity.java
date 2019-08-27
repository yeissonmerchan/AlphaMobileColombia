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
import com.example.alphamobilecolombia.utils.validaciones.Formulario;

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

        String[] Campos = new String[]{"edt_ingresos", "edt_salud", "edt_pension"};

        new Formulario().Validar(this, VoucherDiscountActivity.class, Campos);
    }
}


/*        if (new Formulario().Validar(this, Campos)) {
                //Pasar a la siguiente pagina
                Intent intent = new Intent(this, VoucherDiscountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
        }*/


/*        if (TextUtils.isEmpty(edt_ingresos.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo ingresos es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edt_salud.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo salud es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edt_pension.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo pensión es obligatorio", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(view.getContext(), VoucherDiscountActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }*/
