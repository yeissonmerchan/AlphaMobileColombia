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

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Models.GetPagaduriasRequest;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.mvp.presenter.ConsultarPrevalidacionActivaPresenter;
import com.example.alphamobilecolombia.mvp.presenter.ScannerPresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.models.Person;
import com.example.alphamobilecolombia.utils.models.Persona;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CapturaInformacionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Person person = new Person();
    Spinner spinner_tipo_empleado, spinner_tipo_contrato, spinner_destino_credito, spinner_genero;
    Persona p;
    RealmStorage storage = new RealmStorage();
    List<GetPagaduriasRequest> pagadurias = new ArrayList<>();
    Dialog myDialog;
    Context contextView;
    private static final int DIALOG_REALLY_EXIT_ID = 0;
    String msgError = "Faltan campos obligatorios por completar.";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_informacion);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Nueva solicitud");

        ScannerPresenter scannerPresenter = new ScannerPresenter();
        HttpResponse response = scannerPresenter.getPagadurias(this);
        Gson gson = new Gson();
        contextView = this;
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
        }catch (JSONException ex) {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Pagadurías",ex,this);
        }

        spinner_genero = (Spinner) findViewById(R.id.spinner_genero);
        ArrayAdapter<CharSequence> adapter_genero = ArrayAdapter.createFromResource(this,
                R.array.spinner_genero, android.R.layout.simple_spinner_item);
        adapter_genero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_genero.setAdapter(adapter_genero);

        spinner_destino_credito = (Spinner) findViewById(R.id.spinner_destino_credito);
        ArrayAdapter<CharSequence> adapter_destino_credito = ArrayAdapter.createFromResource(this,
                R.array.spinner_destino_credito, android.R.layout.simple_spinner_item);
        adapter_destino_credito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_destino_credito.setAdapter(adapter_destino_credito);


        spinner_tipo_empleado = (Spinner) findViewById(R.id.spinner_tipo_empleado);
        ArrayAdapter<CharSequence> adapter_tipo_empleado = ArrayAdapter.createFromResource(this,
                R.array.spinner_tipo_empleado, android.R.layout.simple_spinner_item);
        adapter_tipo_empleado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo_empleado.setAdapter(adapter_tipo_empleado);


        spinner_tipo_contrato = (Spinner) findViewById(R.id.spinner_tipo_contrato);
        spinner_tipo_empleado.setOnItemSelectedListener(this);

        EditText edt_names = (EditText) findViewById(R.id.edt_names);
        edt_names.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edt_names.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+")){
                            return cs;
                        }
                        return cs.toString().replaceAll("[^A-Za-zñÑáéíóúÁÉÍÓÚ ]", "");
                    }
                }
        });

        EditText edt_names2 = (EditText) findViewById(R.id.edt_names2);
        edt_names2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edt_names2.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+")){
                            return cs;
                        }
                        return cs.toString().replaceAll("[^A-Za-zñÑáéíóúÁÉÍÓÚ ]", "");
                    }
                }
        });

        EditText edt_lastNames = (EditText) findViewById(R.id.edt_lastNames);
        edt_lastNames.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edt_lastNames.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+")){
                            return cs;
                        }
                        return cs.toString().replaceAll("[^A-Za-zñÑáéíóúÁÉÍÓÚ ]", "");
                    }
                }
        });

        EditText edt_lastNames2 = (EditText) findViewById(R.id.edt_lastNames2);
        edt_lastNames2.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edt_lastNames2.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+")){
                            return cs;
                        }
                        return cs.toString().replaceAll("[^A-Za-zñÑáéíóúÁÉÍÓÚ ]", "");
                    }
                }
        });

        List<String> names = new ArrayList<>();
        for (GetPagaduriasRequest p : pagadurias) {
            names.add(p.getNombre());
        }

        ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.spinner, names);

        Spinner userSpinner = (Spinner) findViewById(R.id.spinner_pagaduria);
        userSpinner.setAdapter(userAdapter);

        // Inicio Datepicker
        mDisplayDate = (TextView) findViewById(R.id.edt_birthDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CapturaInformacionActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate((long) (cal.getTimeInMillis()-(5.682e+11)));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String monthS;
                String dayS;

                monthS = String.valueOf(month);
                dayS = String.valueOf(day);

                if (monthS.length()==1){
                    monthS = "0"+monthS;
                }
                if (dayS.length()==1){
                    dayS = "0"+dayS;
                }

                String date = year + "/" + monthS + "/" + dayS;
                mDisplayDate.setText(date);
            }
        };

        // Fin Datepicker

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
    protected Dialog onCreateDialog(int id) {
        final Dialog dialog;
        switch(id) {
            case DIALOG_REALLY_EXIT_ID:
                dialog = new AlertDialog.Builder(this).setMessage(
                        "¿ Desea terminar el proceso ?")
                        .setCancelable(false)
                        .setPositiveButton("Sí",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent a = new Intent(getBaseContext(),ModuloActivity.class);
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

    public void ValidarPrevalidacionesActivas(String Documento,Person person) throws JSONException {

        ConsultarPrevalidacionActivaPresenter presenter = new ConsultarPrevalidacionActivaPresenter();
        HttpResponse model = presenter.GetConsultarPrevalidacionActiva(Documento,getBaseContext());

        if (model != null) {

            JSONObject data = (JSONObject) model.getData();

            JSONArray jSONArray = (JSONArray) data.getJSONArray("data");

            if (jSONArray.length()>0){

                JSONObject object = (JSONObject) jSONArray.get(0);

                boolean accion;
                accion = Boolean.parseBoolean(object.getString("accion"));

                if(accion){

                    try{

                        AlertDialog.Builder Alert = new AlertDialog.Builder(this);
                        Alert.setTitle("IMPORTANTE");
                        Alert.setMessage(object.getString("mensaje"));
                        Alert.setCancelable(false);

                        Alert.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        EnvioDataCambioPagina(person);
                                    }
                                });


                        AlertDialog AlertMsg = Alert.create();
                        AlertMsg.setCanceledOnTouchOutside(false);
                        AlertMsg.show();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                        LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Prevalidaciones",ex,this);
                    }
                }else {
                    EnvioDataCambioPagina(person);
                }
            }else {
                EnvioDataCambioPagina(person);
            }
        }else {
            EnvioDataCambioPagina(person);
        }
    }

    public void  EnvioDataCambioPagina(Person person){
        Intent intent = new Intent (getBaseContext(), ArchivosV2Activity.class);

        EditText edt_names = (EditText) findViewById(R.id.edt_names);
        EditText edt_names2 = (EditText) findViewById(R.id.edt_names2);
        EditText edt_lastNames = (EditText) findViewById(R.id.edt_lastNames);
        EditText edt_lastNames2 = (EditText) findViewById(R.id.edt_lastNames2);
        EditText edt_numberIdentification = (EditText) findViewById(R.id.edt_numberIdentification);
        EditText edt_birthDate = (EditText) findViewById(R.id.edt_birthDate);
        String edt_genero = spinner_genero.getSelectedItem().toString();

        String pagaduria = (String) ((Spinner) findViewById(R.id.spinner_pagaduria)).getSelectedItem();
        String codePagaduria = getCodePagaduria(pagaduria, pagadurias);
        String bithdate = edt_birthDate.getText().toString();

        if (edt_genero.equals("Femenino")){
            edt_genero = "F";
        }
        else{
            edt_genero = "M";
        }

        person.setFirstName(edt_names.getText().toString());
        person.setSecondName(edt_names2.getText().toString());
        person.setSurename(edt_lastNames.getText().toString());
        person.setSecondSurename(edt_lastNames2.getText().toString());
        person.setGender(edt_genero);
        person.setNumber(edt_numberIdentification.getText().toString());


        intent.putExtra("PERSONA_Documento", edt_numberIdentification.getText().toString());
        intent.putExtra("PERSONA_PNombre", edt_names.getText().toString());
        intent.putExtra("PERSONA_SNombre", edt_names2.getText().toString());
        intent.putExtra("PERSONA_PApellido", edt_lastNames.getText().toString());
        intent.putExtra("PERSONA_SApellido", edt_lastNames2.getText().toString());
        intent.putExtra("PERSONA_FechaNac", bithdate);
        intent.putExtra("PERSONA_Genero", edt_genero);
        intent.putExtra("PERSONA_Celular", "0");

        intent.putExtra("IdTipoEmpleado", spinner_tipo_empleado.getSelectedItem().toString());
        intent.putExtra("IdTipoContrato", spinner_tipo_contrato.getSelectedItem().toString());
        intent.putExtra("IdDestinoCredito", spinner_destino_credito.getSelectedItem().toString());
        intent.putExtra("IdPagaduria", codePagaduria);

        storage.savePerson(contextView,person);

        startActivityForResult(intent, 0);
    }


    public void onClickSaveInformation(View view) throws JSONException {
        boolean isValidForm = true;
        EditText edt_names = (EditText) findViewById(R.id.edt_names);
        EditText edt_names2 = (EditText) findViewById(R.id.edt_names2);
        EditText edt_lastNames = (EditText) findViewById(R.id.edt_lastNames);
        EditText edt_lastNames2 = (EditText) findViewById(R.id.edt_lastNames2);
        EditText edt_numberIdentification = (EditText) findViewById(R.id.edt_numberIdentification);
        EditText edt_birthDate = (EditText) findViewById(R.id.edt_birthDate);
        String edt_genero = spinner_genero.getSelectedItem().toString();

        if (!(edt_names.getText().toString().length() > 1)){
            isValidForm = false;
            msgError = "Faltan campos obligatorios por completar.";
        }
        if (!(edt_lastNames.getText().toString().length() > 1)){
            isValidForm = false;
            msgError = "Faltan campos obligatorios por completar.";
        }

        try {
            long isNumbre = Long.parseLong(edt_numberIdentification.getText().toString());

            if (!(isNumbre > 0)){
                isValidForm = false;
                msgError = "Faltan campos obligatorios por completar.";
            }
        } catch (NumberFormatException excepcion) {
            isValidForm = false;
            msgError = "Ingrese un número de identificación valido.";
        }

        try {
            if (!(edt_birthDate.getText().toString().length() > 1)){
                isValidForm = false;
                msgError = "Faltan campos obligatorios por completar.";
            }
        } catch (NumberFormatException excepcion) {
            isValidForm = false;
            msgError = "Ingrese una fecha de nacimiento valida.";
        }

        if(isValidForm) {

            ValidarPrevalidacionesActivas(edt_numberIdentification.getText().toString(),person);

            /*Intent intent = new Intent(getBaseContext(), ArchivosV2Activity.class);

            String pagaduria = (String) ((Spinner) findViewById(R.id.spinner_pagaduria)).getSelectedItem();
            String codePagaduria = getCodePagaduria(pagaduria, pagadurias);
            //String bithdate = person.getBirthday().substring(0, 4) + "-" + person.getBirthday().substring(4, 6) + "-" + person.getBirthday().substring(6, 8);
            //String bithdate = "1996-08-09";
            String bithdate = edt_birthDate.getText().toString();

            if (edt_genero.equals("Femenino")){
                edt_genero = "F";
            }
            else{
                edt_genero = "M";
            }

            person.setFirstName(edt_names.getText().toString());
            person.setSecondName(edt_names2.getText().toString());
            person.setSurename(edt_lastNames.getText().toString());
            person.setSecondSurename(edt_lastNames2.getText().toString());
            person.setGender(edt_genero);
            person.setNumber(edt_numberIdentification.getText().toString());


            intent.putExtra("PERSONA_Documento", edt_numberIdentification.getText().toString());
            intent.putExtra("PERSONA_PNombre", edt_names.getText().toString());
            intent.putExtra("PERSONA_SNombre", edt_names2.getText().toString());
            intent.putExtra("PERSONA_PApellido", edt_lastNames.getText().toString());
            intent.putExtra("PERSONA_SApellido", edt_lastNames2.getText().toString());
            intent.putExtra("PERSONA_FechaNac", bithdate);
            intent.putExtra("PERSONA_Genero", edt_genero);
            intent.putExtra("PERSONA_Celular", "0");

            intent.putExtra("IdTipoEmpleado", spinner_tipo_empleado.getSelectedItem().toString());
            intent.putExtra("IdTipoContrato", spinner_tipo_contrato.getSelectedItem().toString());
            intent.putExtra("IdDestinoCredito", spinner_destino_credito.getSelectedItem().toString());
            intent.putExtra("IdPagaduria", codePagaduria);

            storage.savePerson(contextView,person);

            //startActivityForResult(intent, 0);*/
        }
        else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
            builder1.setMessage(msgError);
            builder1.setCancelable(false);
            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.setCanceledOnTouchOutside(false);
            alert11.show();
        }
    }

    private String getCodePagaduria(String name, List<GetPagaduriasRequest> listPagadurias) {
        int i = -1;
        for (GetPagaduriasRequest cc: listPagadurias) {
            i++;
            if (cc.getNombre().equals(name))
                break;
        }

        List<Integer> idPagadurias = new ArrayList<>();
        for (GetPagaduriasRequest p : listPagadurias) {
            idPagadurias.add(p.getId());
        }
        return idPagadurias.get(i).toString();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        String selected = spinner_tipo_empleado.getSelectedItem().toString();
        if (spinner_tipo_empleado.getSelectedItem().toString().equals("Empleado")) {

            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.spinner_tipo_contrato_empleado, android.R.layout.simple_spinner_item);
            spinner_tipo_contrato.setAdapter(adapter2);
        } else {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.spinner_tipo_contrato_pensionado, android.R.layout.simple_spinner_item);
            spinner_tipo_contrato.setAdapter(adapter2);
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}
