package com.example.alphamobilecolombia.mvp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.data.remote.GetPagadurias;
import com.example.alphamobilecolombia.data.remote.Models.GetPagaduriasRequest;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.ListGetPagaduriasRequest;
import com.example.alphamobilecolombia.mvp.presenter.ConsultarPrevalidacionActivaPresenter;
import com.example.alphamobilecolombia.mvp.presenter.ScannerPresenter;
import com.example.alphamobilecolombia.utils.extensions.CedulaQrAnalytics;
import com.example.alphamobilecolombia.utils.models.Person;
import com.example.alphamobilecolombia.utils.models.Persona;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
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
import com.google.gson.JsonElement;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.venko.api.android.cedula.DocumentManager;
import freemarker.template.utility.CollectionUtils;

public class ScannerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Person person;
    Spinner spinner_tipo_empleado, spinner_tipo_contrato, spinner_destino_credito, spinner_pagaduria;
    Persona p;
    RealmStorage storage = new RealmStorage();
    List<GetPagaduriasRequest> pagadurias = new ArrayList<>();
    Dialog myDialog;
    Context contextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        myDialog = new Dialog(this);
        storage.deleteTable(this);
        Window window = this.getWindow();
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
        }catch (JSONException e) {
            e.printStackTrace();
        }

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Datos personales");


        new IntentIntegrator(ScannerActivity.this)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setPrompt("Por favor escanear el código de barras de la cédula.")
                .setCameraId(0)
                .setBeepEnabled(false)
                .setBarcodeImageEnabled(false)
                .initiateScan();


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

        List<String> names = new ArrayList<>();
        for (GetPagaduriasRequest p : pagadurias) {
            names.add(p.getNombre());
        }

        ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.spinner, names);

        Spinner userSpinner = (Spinner) findViewById(R.id.spinner_pagaduria);
        userSpinner.setAdapter(userAdapter);


    }

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Scaneo cancelado");
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                try {

                    DocumentManager documentManager = new DocumentManager();
                    byte[] rawdata = result.getContents().getBytes();
                    byte[] result2 = data.getStringExtra("SCAN_RESULT").getBytes("ISO-8859-1");
                    String user = getResources().getString(R.string.userkey);
                    String license = getResources().getString(R.string.licenceKey);

                    String resultScan = documentManager.parse(user, license, result2);
                    person = new Gson().fromJson(resultScan, Person.class);
                    p = CedulaQrAnalytics.parse(data);


                    if(person != null){
                        if(person.getNumber().length()>0)
                        {
                            storage.savePerson(contextView,person);
                        }
                        else{
                            NotificacionErrorDatos(contextView);
                        }
                    }
                    else{
                        NotificacionErrorDatos(contextView);
                    }

                    /*
                    EditText edt_names = (EditText) findViewById(R.id.edt_names);
                    EditText edt_names2 = (EditText) findViewById(R.id.edt_names2);
                    EditText edt_lastNames = (EditText) findViewById(R.id.edt_lastNames);
                    EditText edt_lastNames2 = (EditText) findViewById(R.id.edt_lastNames2);
                    EditText edt_numberIdentification = (EditText) findViewById(R.id.edt_numberIdentification);
                    EditText edt_birthDate = (EditText) findViewById(R.id.edt_birthDate);
                    //EditText edt_celular = (EditText) findViewById(R.id.edt_celular);
                    EditText edt_genero = (EditText) findViewById(R.id.edt_genero);
                    EditText edt_factor = (EditText) findViewById(R.id.edt_factor);
                    */

                    /*edt_names.setText((person.getFirstName().length() < 0 ? person.getFirstName() : p.getNombre()));
                    edt_names2.setText((person.getSecondName().length() < 0 ? person.getSecondName() : ""));
                    edt_lastNames.setText((person.getSurename().length() < 0 ? person.getSurename() : p.getApellido1()));
                    edt_lastNames2.setText((person.getSecondSurename().length() < 0 ? person.getSecondSurename() : p.getApellido2()));
                    edt_numberIdentification.setText((person.getNumber().length()< 0 ? person.getNumber() : p.getCedula()));
                    edt_birthDate.setText((person.getBirthday().length()< 0 ? person.getBirthday() : p.getFechaNacimiento()));
                    edt_genero.setText((person.getGender().length()< 0 ? person.getGender() : p.getGenero()));
                    edt_factor.setText((person.getBloodType().length()< 0 ? person.getBloodType() : p.getFactorRh()));
                    edt_celular.setText((person.getPlaceBirth().length()< 0 ? person.getPlaceBirth() : ""));
                    */

                    /*
                    edt_names.setText(person.getFirstName());
                    edt_names2.setText(person.getSecondName());
                    edt_lastNames.setText(person.getSurename());
                    edt_lastNames2.setText(person.getSecondSurename());
                    edt_numberIdentification.setText(person.getNumber());
                    edt_birthDate.setText(person.getBirthday());
                    edt_genero.setText(person.getGender());
                    edt_factor.setText(person.getBloodType());
                    //edt_celular.setText(person.getPlaceBirth());
                    */

                    //Toast.makeText(getApplicationContext(),p.toString(), Toast.LENGTH_SHORT).show();
                    //Log.d("MainActivity", "Scaneado");


                    //Toast.makeText(this, p.toString(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    //Toast.makeText(this, "Error: No se pudo hacer el parse"+e.toString(), Toast.LENGTH_LONG).show();
                    NotificacionErrorDatos(this);
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void ValidarPrevalidacionesActivas(String Documento) throws JSONException {

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
                    Toast.makeText(getBaseContext(),object.getString("mensaje"),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onClickBtnNextTerms(View view) throws JSONException {
        Intent intent = new Intent (view.getContext(), ArchivosV2Activity.class);
        /*intent.putExtra("PERSONA_Documento", p.getCedula());
        intent.putExtra("PERSONA_PNombre", p.getNombre());
        intent.putExtra("PERSONA_SNombre", "");
        intent.putExtra("PERSONA_PApellido", p.getApellido1());
        intent.putExtra("PERSONA_SApellido", p.getApellido2());
        intent.putExtra("PERSONA_FechaNac", p.getFechaNacimiento());
        intent.putExtra("PERSONA_Genero", p.getGenero());
        intent.putExtra("PERSONA_Celular", p.getCelular());
        */

        String pagaduria = (String) ((Spinner)findViewById(R.id.spinner_pagaduria) ).getSelectedItem();
        String codePagaduria = getCodePagaduria(pagaduria,pagadurias);

        if(person == null){
            person = storage.getPerson(this);
        }

        if(person != null){
            if(person.getNumber().length()>0)
            {
                // Inicio: Prevalidacion
                ValidarPrevalidacionesActivas(person.getNumber());
                // Fin: Prevalidacion

                String bithdate = person.getBirthday().substring(0, 4) + "-" + person.getBirthday().substring(4, 6) + "-" + person.getBirthday().substring(6, 8);

                intent.putExtra("PERSONA_Documento", person.getNumber());
                intent.putExtra("PERSONA_PNombre", person.getFirstName());
                intent.putExtra("PERSONA_SNombre", person.getSecondName());
                intent.putExtra("PERSONA_PApellido", person.getSurename());
                intent.putExtra("PERSONA_SApellido", person.getSecondSurename());
                intent.putExtra("PERSONA_FechaNac", bithdate);
                intent.putExtra("PERSONA_Genero", person.getGender());
                intent.putExtra("PERSONA_Celular", person.getPlaceBirth());

                intent.putExtra("IdTipoEmpleado",spinner_tipo_empleado.getSelectedItem().toString());
                intent.putExtra("IdTipoContrato",spinner_tipo_contrato.getSelectedItem().toString());
                intent.putExtra("IdDestinoCredito",spinner_destino_credito.getSelectedItem().toString());
                intent.putExtra("IdPagaduria",codePagaduria);

                startActivityForResult(intent, 0);
            }
            else{
                NotificacionErrorDatos(this);
            }
        }
        else{
            NotificacionErrorDatos(this);
        }



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

    public void showPersonalInfo(View view) {
        if(person != null){
            if(person.getNumber().length()>0)
            {
                TextView txtclose;
                TextView txt_primer_nombre;
                TextView txt_segundo_nombre;
                TextView txt_primer_apellido;
                TextView txt_segundo_apellido;
                TextView txt_numero_identificacion;
                TextView txt_fecha_nacimiento;
                TextView txt_genero;
                TextView txt_tipo_sangre;

                myDialog.setContentView(R.layout.content_scanner_poppup);

                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);

                txt_primer_nombre =(TextView) myDialog.findViewById(R.id.txt_primer_nombre);
                txt_segundo_nombre =(TextView) myDialog.findViewById(R.id.txt_segundo_nombre);
                txt_primer_apellido =(TextView) myDialog.findViewById(R.id.txt_primer_apellido);
                txt_segundo_apellido =(TextView) myDialog.findViewById(R.id.txt_segundo_apellido);
                txt_numero_identificacion =(TextView) myDialog.findViewById(R.id.txt_numero_identificacion);
                txt_fecha_nacimiento =(TextView) myDialog.findViewById(R.id.txt_fecha_nacimiento);
                txt_genero =(TextView) myDialog.findViewById(R.id.txt_genero);
                txt_tipo_sangre =(TextView) myDialog.findViewById(R.id.txt_tipo_sangre);

                txt_primer_nombre.setText(person.getFirstName());
                txt_segundo_nombre.setText(person.getSecondName());
                txt_primer_apellido.setText(person.getSurename());
                txt_segundo_apellido.setText(person.getSecondSurename());
                txt_numero_identificacion.setText(person.getNumber());
                txt_fecha_nacimiento.setText(person.getBirthday());
                txt_genero.setText(person.getGender());
                txt_tipo_sangre.setText(person.getBloodType());

                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
            else{
                NotificacionErrorDatos(this);
            }
        }
        else{
            NotificacionErrorDatos(this);
        }

    }

    public void NotificacionErrorDatos(final Context view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view);
        builder1.setMessage("No ha sido posible obtener información de la cédula, por favor inténtelo nuevamente.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(view, ModuloActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
