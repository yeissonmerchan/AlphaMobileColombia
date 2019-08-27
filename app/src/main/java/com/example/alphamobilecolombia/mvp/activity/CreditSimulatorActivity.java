package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.utils.validaciones.Formulario;

public class CreditSimulatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Define el control campo monto a desembolsar
    EditText edt_monto_a_desembolsar;
    //Define el control campo cuota
    EditText edt_cuota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_simulator);
        edt_monto_a_desembolsar = (EditText) findViewById(R.id.edt_monto_a_desembolsar);
        edt_cuota = (EditText) findViewById(R.id.edt_cuota);

        SeekBar sk = (SeekBar) findViewById(R.id.seekbar_plazo);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView t = (TextView) findViewById(R.id.textView4);
                t.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

        String[] Campos = new String[]{"edt_monto_a_desembolsar", "edt_cuota"};

        new Formulario().Validar(this, ConditionsSummaryActivity.class, Campos);
    }
}




/*        if (new Formulario().Validar(this, Campos)) {
                //Pasar a la siguiente pagina
                Intent intent = new Intent(this, ConditionsSummaryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
        }*/


/*        if (TextUtils.isEmpty(edt_monto_a_desembolsar.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo monto a desembolsar es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edt_cuota.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo cuota es obligatorio", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(view.getContext(), ConditionsSummaryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }*/