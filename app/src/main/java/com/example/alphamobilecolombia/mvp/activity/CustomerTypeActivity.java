package com.example.alphamobilecolombia.mvp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;

import java.util.Calendar;

public class CustomerTypeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Define el control de texto del tipo de contrato
    private TextView txtfecha_finalizacion_contrato;

    //Define los controles combobox tipo cliente, y tipo contrato
    private Spinner spinner_tipo_cliente, spinner_tipo_contrato;

    //************************** PANEL CAMPOS

    private LinearLayout panel_campos;

    //************************** ANTIGUEDAD EN MESES

    private LinearLayout panel_antiguedad_en_meses;

    private EditText edt_antiguedad_en_meses;

    //************************** FECHA INGRESO

    //Define el control fecha de ingreso
    private TextView textview_fecha_ingreso;

    //Define el evento del control fecha de ingreso
    private DatePickerDialog.OnDateSetListener evento_fecha_ingreso;

    //************************** FECHA FINALIZACION CONTRATO

    //Define el control fecha de finalización del contrato
    private TextView textview_fecha_finalizacion_contrato;

    //Define el evento del control fecha finalización del contrato
    private DatePickerDialog.OnDateSetListener evento_fecha_finalizacion_contrato;

    //**************************

    //Se produce cuando se inicia esta actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_type);

        /********************************************************************** PANELES */

        panel_campos = (LinearLayout) findViewById(R.id.panel_campos);

        panel_antiguedad_en_meses = (LinearLayout) findViewById(R.id.panel_antiguedad_en_meses);

        panel_antiguedad_en_meses.setVisibility(View.GONE);

        /********************************************************************** TIPO CLIENTE */

        spinner_tipo_cliente = (Spinner) findViewById(R.id.spinner_tipo_cliente);
        ArrayAdapter<CharSequence> adapter_tipo_cliente = ArrayAdapter.createFromResource(this,
                R.array.spinner_employee_type, android.R.layout.simple_spinner_item);
        adapter_tipo_cliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo_cliente.setAdapter(adapter_tipo_cliente);

        spinner_tipo_cliente.setOnItemSelectedListener(this);

        /*********************************************************************** TIPO CONTRATO */

        txtfecha_finalizacion_contrato = (TextView) findViewById(R.id.txtfecha_finalizacion_contrato);
        spinner_tipo_contrato = (Spinner) findViewById(R.id.spinner_tipo_contrato);
        spinner_tipo_contrato.setOnItemSelectedListener(this);

        /********************************************************************** FECHA INGRESO */

        textview_fecha_ingreso = (TextView) findViewById(R.id.edt_fecha_ingreso);

        textview_fecha_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CustomerTypeActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        evento_fecha_ingreso,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMinDate((long) (cal.getTimeInMillis() - (5.682e+11)));
                dialog.show();
            }
        });

        evento_fecha_ingreso = new DatePickerDialog.OnDateSetListener() {
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
                textview_fecha_ingreso.setText(date);
            }
        };

        /********************************************************************** FECHA FINALIZACIÓN CONTRATO */

        textview_fecha_finalizacion_contrato = (TextView) findViewById(R.id.edt_fecha_finalizacion_contrato);

        textview_fecha_finalizacion_contrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CustomerTypeActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        evento_fecha_finalizacion_contrato,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                /*dialog.getDatePicker().setMaxDate((long) (cal.getTimeInMillis() - (5.682e+11)));*/
                dialog.show();
            }
        });

        evento_fecha_finalizacion_contrato = new DatePickerDialog.OnDateSetListener() {
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
                textview_fecha_finalizacion_contrato.setText(date);
            }
        };

        //*********************************************************************** ANTIGUEDAD EN MESES

        edt_antiguedad_en_meses = (EditText) findViewById(R.id.edt_antiguedad_en_meses);

        //***********************************************************************

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

    //Se produce al seleccionar un combobox
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Compara el Spinner que ejecutó el evento
        switch (parent.getId()) {
            case R.id.spinner_tipo_cliente:

                if (spinner_tipo_cliente.getSelectedItem().toString().toUpperCase().equals("EMPLEADO")) {

                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                            R.array.spinner_type_contract_employee, android.R.layout.simple_spinner_item);
                    spinner_tipo_contrato.setAdapter(adapter2);

                    panel_campos.setVisibility(View.VISIBLE);
                    panel_antiguedad_en_meses.setVisibility(View.GONE);

                } else {

                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                            R.array.spinner_type_contract_retired, android.R.layout.simple_spinner_item);
                    spinner_tipo_contrato.setAdapter(adapter2);

                    panel_campos.setVisibility(View.GONE);
                    panel_antiguedad_en_meses.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.spinner_tipo_contrato:

                //Si el tipo de contrato es fijo o temporal entonces
                if (spinner_tipo_contrato.getSelectedItem().toString().trim().toUpperCase().equals("FIJO") ||
                        spinner_tipo_contrato.getSelectedItem().toString().trim().toUpperCase().equals("TEMPORAL")
                ) {
                    txtfecha_finalizacion_contrato.setVisibility(View.VISIBLE);
                    textview_fecha_finalizacion_contrato.setVisibility(View.VISIBLE);
                } else {
                    txtfecha_finalizacion_contrato.setVisibility(View.GONE);
                    textview_fecha_finalizacion_contrato.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    //Se produce cuando se presiona el botón Siguiente
    public void onClickBtnNewRequest(View view) {

        //Define si las validaciones son correctas
        boolean Correcto = false;

        //************************************************************ Si el empleado es activo entonces
        if (panel_antiguedad_en_meses.getVisibility() == View.GONE) {
            if (spinner_tipo_cliente.getSelectedItem() == null) {
                Toast.makeText(getApplicationContext(), "El campo tipo de cliente es obligatorio", Toast.LENGTH_LONG).show();
            } else if (spinner_tipo_contrato.getSelectedItem() == null) {
                Toast.makeText(getApplicationContext(), "El campo tipo de contrato es obligatorio", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(textview_fecha_ingreso.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "La fecha de ingreso es obligatoria", Toast.LENGTH_LONG).show();
            } else if (
                    (spinner_tipo_contrato.getSelectedItem().toString().trim().toUpperCase().equals("FIJO") ||
                            spinner_tipo_contrato.getSelectedItem().toString().trim().toUpperCase().equals("TEMPORAL")) &&
                            TextUtils.isEmpty(textview_fecha_finalizacion_contrato.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "La fecha de finalización del contrato es obligatoria", Toast.LENGTH_LONG).show();
            } else {
                Correcto = true;
            }
        }
        //************************************************************ Si el empleado es pensionado entonces
        else if (panel_antiguedad_en_meses.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(edt_antiguedad_en_meses.getText().toString().trim())) {
                Toast.makeText(getApplicationContext(), "La antiguedad en meses es obligatoria", Toast.LENGTH_LONG).show();
            } else {
                Correcto = true;
            }
        }
        //************************************************************

        //Si es correcto entonces
        if (Correcto) {
            Intent intent = new Intent(view.getContext(), VoucherDataActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }
    }

}
