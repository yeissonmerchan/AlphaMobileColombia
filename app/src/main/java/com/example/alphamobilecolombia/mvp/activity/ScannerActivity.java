package com.example.alphamobilecolombia.mvp.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.implement.RealmStorage;
import com.example.alphamobilecolombia.mvp.models.Person;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.validaciones.Formulario;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.util.Calendar;

import co.venko.api.android.cedula.DocumentManager;

public class ScannerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //************************** LECTOR

    //Define la persona
    private Person person;

    RealmStorage storage = new RealmStorage();

    Context contextView;

    //************************** CAMPOS PRIMER NOMBRE, SEGUNDO NOMBRE, PRIMER APELLIDO, SEGUNDO APELLIDO, NUMERO IDENTIFICACIÓN

    //Define los campos
    private EditText edt_names, edt_names2, edt_lastNames, edt_lastNames2, edt_numberIdentification;

    //************************** GENERO

    //Define el control combobox genero
    private Spinner spinner_genero;

    ArrayAdapter<CharSequence> adapter_genero;

    //************************** FECHA NACIMIENTO

    //Define el control fecha de nacimiento
    private TextView textview_fecha_nacimiento;

    //Define el evento del control fecha nacimiento
    private DatePickerDialog.OnDateSetListener evento_fecha_nacimiento;

    //**************************

    //Se produce cuando se inicia esta actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Define la orientación de la pagina
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Llama al padre
        super.onCreate(savedInstanceState);

        //Establece la vista de la pagina
        setContentView(R.layout.activity_scanner);

        /********************************************************************** PRIMER NOMBRE */

        edt_names = (EditText) findViewById(R.id.edt_names);
        edt_names.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edt_names.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+")) {
                            return cs;
                        }
                        return cs.toString().replaceAll("[^A-Za-zñÑáéíóúÁÉÍÓÚ ]", "");
                    }
                }
        });

        /********************************************************************** SEGUNDO NOMBRE */

        edt_names2 = (EditText) findViewById(R.id.edt_names2);
        edt_names2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edt_names2.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+")) {
                            return cs;
                        }
                        return cs.toString().replaceAll("[^A-Za-zñÑáéíóúÁÉÍÓÚ ]", "");
                    }
                }
        });

        /********************************************************************** PRIMER APELLIDO */

        edt_lastNames = (EditText) findViewById(R.id.edt_lastNames);
        edt_lastNames.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edt_lastNames.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+")) {
                            return cs;
                        }
                        return cs.toString().replaceAll("[^A-Za-zñÑáéíóúÁÉÍÓÚ ]", "");
                    }
                }
        });

        /********************************************************************** SEGUNDO APELLIDO */

        edt_lastNames2 = (EditText) findViewById(R.id.edt_lastNames2);
        edt_lastNames2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edt_lastNames2.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+")) {
                            return cs;
                        }
                        return cs.toString().replaceAll("[^A-Za-zñÑáéíóúÁÉÍÓÚ ]", "");
                    }
                }
        });

        /********************************************************************** NUMERO IDENTIFICACIÓN */

        edt_numberIdentification = (EditText) findViewById(R.id.edt_numberIdentification);

        /********************************************************************** GENERO */

/*        spinner_genero = (Spinner) findViewById(R.id.spinner_genero);
        adapter_genero = ArrayAdapter.createFromResource(this,
                R.array.spinner_gender, android.R.layout.simple_spinner_item);
        adapter_genero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_genero.setAdapter(adapter_genero);*/

        spinner_genero = (Spinner) findViewById(R.id.spinner_genero);
        new Formulario().Cargar(this, spinner_genero);

        /********************************************************************** FECHA NACIMIENTO */

        textview_fecha_nacimiento = (TextView) findViewById(R.id.edt_birthDate);

        textview_fecha_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ScannerActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        evento_fecha_nacimiento,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate((long) (cal.getTimeInMillis() - (5.682e+11)));
                dialog.show();
            }
        });

        evento_fecha_nacimiento = new DatePickerDialog.OnDateSetListener() {
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
                textview_fecha_nacimiento.setText(date);
            }
        };

        //*********************************************************************** LECTOR

        contextView = this;
        Bundle dataqr = this.getIntent().getExtras();
        if(dataqr != null){
            boolean qr = dataqr.getBoolean("qr");
            if(qr == true){
                new IntentIntegrator(ScannerActivity.this)
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                        .setPrompt("¿Desea realizar prevalidación presencial?")
                        .setCameraId(0)
                        .setBeepEnabled(false)
                        .setBarcodeImageEnabled(false)
                        .initiateScan();
            }
        }


        //***********************************************************************
    }

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onClickBtnNextTerms(View view) throws JSONException {

        String[] Campos = new String[]{"edt_names", "edt_lastNames", "edt_numberIdentification", "edt_birthDate", "spinner_genero"};

        new Formulario().Validar(this, AdditionalDataActivity.class, Campos);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Log.d("ScannerActivity", "Scaneo cancelado");
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                try {
                    //byte[] rawdata = result.getContents().getBytes();
                    byte[] result2 = data.getStringExtra("SCAN_RESULT").getBytes("ISO-8859-1");
                    getReadBarCode(result2);

                    //Toast.makeText(this, p.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "Escaneo", ex, this);
                    //Toast.makeText(this, "Error: No se pudo hacer el parse"+e.toString(), Toast.LENGTH_LONG).show();
                    NotificacionErrorDatos(this);
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getReadBarCode(byte[] arrayData) {

        try {
            String user = getResources().getString(R.string.user_key);
            String license = getResources().getString(R.string.licenceKey);
            DocumentManager documentManager = new DocumentManager();
            String resultScan = documentManager.parse(user, license, arrayData);
            person = new Gson().fromJson(resultScan, Person.class);
            //p = CedulaQrAnalytics.parse(data);

            if (person != null) {
                if (person.getNumber().length() > 0) {

                    edt_names.setText(person.getFirstName());
                    edt_names2.setText(person.getSecondName());
                    edt_lastNames.setText(person.getSurename());
                    edt_lastNames2.setText(person.getSecondSurename());
                    edt_numberIdentification.setText(person.getNumber());

                    /*                    String p = person.getBirthday();*/

                    textview_fecha_nacimiento.setText(person.getBirthday().substring(0, 4) + "/" +
                            person.getBirthday().substring(4, 6) + "/" +
                            person.getBirthday().substring(6, 8)
                    );

                    String genero = person.getGender().equals("M") ? "Masculino" : "Femenino";

                    int spinnerPosition = adapter_genero.getPosition(genero);
                    spinner_genero.setSelection(spinnerPosition);

                    storage.savePerson(contextView, person);

                    edt_names.setEnabled(false);
                    edt_names2.setEnabled(false);
                    edt_lastNames.setEnabled(false);
                    edt_lastNames2.setEnabled(false);
                    edt_numberIdentification.setEnabled(false);
                    textview_fecha_nacimiento.setEnabled(false);
                    spinner_genero.setEnabled(false);

                } else {
                    NotificacionErrorDatos(contextView);
                }
            } else {
                NotificacionErrorDatos(contextView);
            }

        } catch (Exception ex) {
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "Escaneo", ex, this);
            //Toast.makeText(this, "Error: No se pudo hacer el parse"+e.toString(), Toast.LENGTH_LONG).show();
            NotificacionErrorDatos(this);
        }
    }

    public void NotificacionErrorDatos(final Context view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view);
        builder1.setMessage("No ha sido posible obtener información del código de barras de la cédula. ¿ Desea realizar la captura de datos manual ?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Sí",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(view, ScannerActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(view, ModuleActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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


}




/*
        if (new Formulario().Validar(this, AdditionalDataActivity.class, Campos)) {
        //Pasar a la siguiente pagina
        Intent intent = new Intent(this, AdditionalDataActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
        }
*/

/*        if (TextUtils.isEmpty(edt_names.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo primer nombre es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edt_names2.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo segundo nombre es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edt_lastNames.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo primer apellido es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edt_lastNames2.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo segundo apellido es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(edt_numberIdentification.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "El campo número de identificación es obligatorio", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(textview_fecha_nacimiento.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "La fecha de nacimiento es obligatoria", Toast.LENGTH_LONG).show();
        } else if (spinner_genero.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "El selector genero es obligatorio", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, AdditionalDataActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }*/