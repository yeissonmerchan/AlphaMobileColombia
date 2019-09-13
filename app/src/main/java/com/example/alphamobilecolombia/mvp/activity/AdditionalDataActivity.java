package com.example.alphamobilecolombia.mvp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.validaciones.Formulario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.alphamobilecolombia.utils.validaciones.Formulario.DIALOG_REALLY_EXIT_ID;

public class AdditionalDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {

    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    Formulario formulario;

    public AdditionalDataActivity() {
        formulario = new Formulario(diContainer.injectISelectList(this));
    }
    //************************** CIUDAD EXPEDICIÓN CEDULA

    //Define la lista de ciudades de expedición de la cedula
    private List<String> ListaCiudadExpedicionCedula = new ArrayList<>();

    //Define el campo buscador de ciudades de expedición de la cedula
    SearchView search_ciudad_expedicion_cedula;

    //Define el control de lista de las ciudades de expedición de la cedula
    ListView listview_ciudad_expedicion_cedula;

    //Define el adaptador de la lista de las ciudades de la cedula
    ListViewAdapter adapter;

    //************************** FECHA EXPEDICIÓN CEDULA

    //Define el control fecha expedición cedula
    private TextView textview_fecha_expedicion_cedula;

    //Define el evento del control fecha expedición cedula
    private DatePickerDialog.OnDateSetListener evento_fecha_expedicion_cedula;

    //**************************

    //Se produce cuando se inicia esta actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_data);

        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Nueva solicitud");

        //********************************************************************** CIUDAD EXPEDICIÓN CEDULA

        listview_ciudad_expedicion_cedula = (ListView) findViewById(R.id.listview_ciudad_expedicion_cedula);
        formulario.Cargar(this, listview_ciudad_expedicion_cedula);

        adapter = (ListViewAdapter) listview_ciudad_expedicion_cedula.getAdapter();

        for (int i = 0; i < adapter.getCount(); i++)
            ListaCiudadExpedicionCedula.add(adapter.getItem(i));

        search_ciudad_expedicion_cedula = (SearchView) findViewById(R.id.search_ciudad_expedicion_cedula);
        search_ciudad_expedicion_cedula.setOnQueryTextListener(this);

        listview_ciudad_expedicion_cedula.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String Texto = (String) parent.getItemAtPosition(position);

                search_ciudad_expedicion_cedula.setQuery(Texto, false);

                listview_ciudad_expedicion_cedula.setVisibility(View.GONE);

                enableSearchView(search_ciudad_expedicion_cedula, false);

                EstablecerSeleccion(search_ciudad_expedicion_cedula, 0);

            }
        });

        int searchCloseButtonId = search_ciudad_expedicion_cedula.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.search_ciudad_expedicion_cedula.findViewById(searchCloseButtonId);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableSearchView(search_ciudad_expedicion_cedula, true);
                search_ciudad_expedicion_cedula.setQuery("", false);
            }
        });

        //EstablecerTamañoMaximo(search_ciudad_expedicion_cedula, 10);

        //********************************************************************** COMBOBOX DEPARTAMENTO, CIUDAD

        /*spinner_departamento_expedicion_cedula = (Spinner) findViewById(R.id.spinner_departamento_expedicion_cedula);*/
        /*spinner_ciudad_expedicion_cedula = (Spinner) findViewById(R.id.spinner_ciudad_expedicion_cedula);*/

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
                /*                dialog.getDatePicker().setMaxDate((long) (cal.getTimeInMillis() - (5.682e+11)));*/

                Calendar FechaMinima = Calendar.getInstance();

                String[] FechaNacimiento = formulario.ObtenerValor(AdditionalDataActivity.this, "edt_birthDate").split("/");

                FechaMinima.set(Integer.valueOf(FechaNacimiento[0]) + 18,
                        Integer.valueOf(Integer.valueOf(FechaNacimiento[1]) - 1),
                        Integer.valueOf(FechaNacimiento[2]));

                //Si la fecha minima y maxima son iguales arroja una excepción, por lo que se resta un día
                if (FechaMinima.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                        FechaMinima.get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
                        FechaMinima.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)
                ) {
                    FechaMinima.add(FechaMinima.DAY_OF_MONTH, -1);
                    dialog.getDatePicker().setMinDate(FechaMinima.getTimeInMillis());
                } else {
                    dialog.getDatePicker().setMinDate(FechaMinima.getTimeInMillis());
                }

                dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());




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

    }

/*
    private void EstablecerTamañoMaximo(View view, int tamaño) {
        if (view instanceof EditText) {
            ((EditText) view).setFilters(new InputFilter[]{new InputFilter.LengthFilter(tamaño)});
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                EstablecerSeleccion(child, tamaño);
            }
        }
    }
*/


    private void EstablecerSeleccion(View view, int position) {
        if (view instanceof EditText) {
            ((EditText) view).setSelection(position);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                EstablecerSeleccion(child, position);
            }
        }
    }

    private void enableSearchView(View view, boolean enabled) {
        if (view instanceof EditText) {
            view.setEnabled(enabled);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                enableSearchView(child, enabled);
            }
        }
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

        boolean Correcto = false;

        for (int Indice = 0; Indice < ListaCiudadExpedicionCedula.size(); Indice++) {

            String Cadena1 = search_ciudad_expedicion_cedula.getQuery().toString().trim().toUpperCase();

            String Cadena2 = ListaCiudadExpedicionCedula.get(Indice).trim().toUpperCase();

            if (Cadena1.equals(Cadena2)) {
                Correcto = true;
                break;
            }
        }

        if (Correcto) {

            String[] Campos = new String[]{"search_ciudad_expedicion_cedula", "edt_fecha_expedicion_cedula"}; //"spinner_pagaduria"

            formulario.Validar(this, PaymentActivity.class, Campos, new String[] {});
        } else {
            Toast.makeText(this.getApplicationContext(), "Debe seleccionar una ciudad de expedición de cédula valida", Toast.LENGTH_LONG).show(); //Muestra el mensaje
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;

        if (adapter.filter(text)) {
            listview_ciudad_expedicion_cedula.setVisibility(View.VISIBLE);
        } else {
            listview_ciudad_expedicion_cedula.setVisibility(View.GONE);
        }

        return false;
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


    /********************************************************* DEBERÍA GENERALIZARSE EN FORMULARIO */

    @Override
    protected Dialog onCreateDialog(int id) {
        final Dialog dialog;
        switch (id) {
            case DIALOG_REALLY_EXIT_ID:
                dialog = new AlertDialog.Builder(this).setMessage(
                        "¿ Desea terminar el proceso ?")
                        .setCancelable(false)
                        .setPositiveButton("Sí",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent a = new Intent(getBaseContext(), ModuleActivity.class);
                                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(a);
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }).create();
                break;
            default:
                dialog = null;
        }
        return dialog;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog(DIALOG_REALLY_EXIT_ID);
        }
        return true;
    }

    /*********************************************************************************/

}

/*        String[] Campos = new String[]{"edt_fecha_expedicion_cedula"}; //"spinner_departamento_expedicion_cedula", "spinner_ciudad_expedicion_cedula",

        new Formulario().Validar(this, PaymentActivity.class, Campos);*/

//Define los controles combobox
//private Spinner spinner_ciudad_expedicion_cedula; //spinner_departamento_expedicion_cedula,

/*        if (new Formulario().Validar(this, Campos)) {
                //Pasar a la siguiente pagina
                Intent intent = new Intent(this, PaymentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
        }*/

/*
        if (spinner_departamento_expedicion_cedula.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "El campo departamento de expedición de la cédula es obligatorio", Toast.LENGTH_LONG).show();
        } else if (spinner_ciudad_expedicion_cedula.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "El campo ciudad de expedición de la cédula es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(textview_fecha_expedicion_cedula.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "La fecha de expedición de la cédula es obligatoria", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(view.getContext(), PaymentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }*/