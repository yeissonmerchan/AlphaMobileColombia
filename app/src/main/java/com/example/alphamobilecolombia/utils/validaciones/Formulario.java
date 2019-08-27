package com.example.alphamobilecolombia.utils.validaciones;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.Request.GetPagaduriasRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.mvp.activity.AdditionalDataActivity;
import com.example.alphamobilecolombia.mvp.presenter.implement.ScannerPresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Formulario {


    //<summary>Valida la pagina</summary>
    //<pagina>Define la pagina que tiene los controles a validar</pagina>
    //<actividad>Define la actividad a llamar en el caso de que no hayan errores en los campos</actividad>
    //<arrayId>Define los Ids de los campos a obtener de la pagina especificada</arrayId>
    public boolean Validar(AppCompatActivity pagina, Class<?> clase, String[] arrayId) {

        ArrayList<String> ListaErrores = new ArrayList<String>(); //Define la lista de errores

        for (int Indice = 0; Indice < arrayId.length; Indice++) { //Recorre la lista de Ids de los controles

            int Id = pagina.getResources().getIdentifier(arrayId[Indice], "id", pagina.getPackageName()); //Obtiene el Id del recurso del Control

            View Control = pagina.findViewById(Id); //Obtiene el control

            if (Control != null) {
                //Si el texto x está vacío entonces
                if (Control instanceof TextView) {
                    String Texto = ((TextView) Control).getText().toString();
                    if (TextUtils.isEmpty(Texto.trim())) {
                        ListaErrores.add("El campo " + arrayId[Indice] + " es obligatorio");
                    }
                    //Si el campo x está vacío entonces
                } else if (Control instanceof EditText) {
                    String Texto = ((EditText) Control).getText().toString();
                    if (TextUtils.isEmpty(Texto.trim())) {
                        ListaErrores.add("El campo " + arrayId[Indice] + " es obligatorio");
                    }
                    //Si el spinner x está vacío entonces
                } else if (Control instanceof Spinner) {
                    if (((Spinner) Control).getSelectedItem() == null) {
                        ListaErrores.add("El campo " + arrayId[Indice] + " es obligatorio");
                    }
                }
            }
        }

        //Si hay errores entonces
        if (ListaErrores.size() > 0) {
            Toast.makeText(pagina.getApplicationContext(), ListaErrores.get(0), Toast.LENGTH_LONG).show(); //Muestra el mensaje
            return false; //Retorna falso
        } else {
            //Pasar a la siguiente pagina
            Intent intent = new Intent(pagina, clase);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pagina.startActivityForResult(intent, 0);
            return true; //Retorna verdadero
        }
    }

    //Carga el Spinner especificado
    public void Cargar(AppCompatActivity pagina, Spinner spinner) {

        ArrayAdapter<CharSequence> adapter; //Define el adaptador

        switch (spinner.getId()) {
            /*******************************************************************/
            case R.id.spinner_genero:
                spinner = (Spinner) pagina.findViewById(R.id.spinner_genero); //Establece el Spinner
                adapter = ArrayAdapter.createFromResource(pagina, R.array.spinner_gender, android.R.layout.simple_spinner_item); //Establece el adaptador del recurso
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Establece el diseño del adaptador
                spinner.setAdapter(adapter); //Establece el adaptador al Spinner
                break;
            /*******************************************************************/
            case R.id.spinner_tipo_cliente:
                spinner = (Spinner) pagina.findViewById(R.id.spinner_tipo_cliente);
                adapter = ArrayAdapter.createFromResource(pagina, R.array.spinner_employee_type, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                break;
            /*******************************************************************/
            case R.id.spinner_pagaduria:
                List<GetPagaduriasRequest> pagadurias = new ArrayList<>();
                ScannerPresenter scannerPresenter = new ScannerPresenter();
                HttpResponse response = scannerPresenter.getPaying(pagina);
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
                } catch (JSONException ex) {
                    ex.printStackTrace();
                    LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "Pagadurías", ex, pagina);
                }

                List<String> names = new ArrayList<>();
                for (GetPagaduriasRequest p : pagadurias) {
                    names.add(p.getNombre());
                }

                adapter = new ArrayAdapter(pagina, R.layout.spinner, names);

                spinner = (Spinner) pagina.findViewById(R.id.spinner_pagaduria);
                spinner.setAdapter(adapter);

                break;
            /*******************************************************************/
            case R.id.spinner_destino_credito:
                spinner = (Spinner) pagina.findViewById(R.id.spinner_destino_credito);
                ArrayAdapter<CharSequence> adapter_destino_credito = ArrayAdapter.createFromResource(pagina, R.array.spinner_credit_destination, android.R.layout.simple_spinner_item);
                adapter_destino_credito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter_destino_credito);
                break;
        }


    }
}
