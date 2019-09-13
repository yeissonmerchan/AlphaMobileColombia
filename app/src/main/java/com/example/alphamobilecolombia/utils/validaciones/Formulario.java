package com.example.alphamobilecolombia.utils.validaciones;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.local.entity.Parameter;
import com.example.alphamobilecolombia.data.local.implement.RealmInstance;
import com.example.alphamobilecolombia.data.remote.Models.Request.GetPagaduriasRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.mvp.activity.AdditionalDataActivity;
import com.example.alphamobilecolombia.mvp.activity.ListViewAdapter;
import com.example.alphamobilecolombia.mvp.presenter.implement.ScannerPresenter;
import com.example.alphamobilecolombia.utils.configuration.ISelectList;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.cryptography.implement.RSA;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.realm.RealmObject;

public class Formulario {
    ISelectList _iSelectList;

    public static final int DIALOG_REALLY_EXIT_ID = 0;

    public Formulario(ISelectList iSelectList) {
        _iSelectList = iSelectList;
    }

    //<summary>Valida la pagina</summary>
    //<pagina>Define la pagina que tiene los controles a validar</pagina>
    //<actividad>Define la actividad a llamar en el caso de que no hayan errores en los campos</actividad>
    //<arrayId>Define los Ids de los campos a obtener de la pagina especificada</arrayId>
    public boolean Validar(AppCompatActivity pagina, Class<?> clase, String[] arrayId, String[] arrayOptionals) {

        List<Parameter> ListaValores = new ArrayList<>(); //Define la lista de valores
        ArrayList<String> ListaErrores = new ArrayList<String>(); //Define la lista de errores

        for (int Indice = 0; Indice < arrayId.length; Indice++) { //Recorre la lista de Ids de los controles

            int Id = pagina.getResources().getIdentifier(arrayId[Indice], "id", pagina.getPackageName()); //Obtiene el Id del recurso del Control

            View Control = pagina.findViewById(Id); //Obtiene el control

            if (Control != null) {

                String Texto = "";

                //Si el control x es un TextView entonces
                if (Control instanceof TextView) {
                    Texto = ((TextView) Control).getText().toString();
                    //Si el control x es un EditText entonces
                } else if (Control instanceof EditText) {
                    Texto = ((EditText) Control).getText().toString();
                    //Si el control x es Spinner entonces
                } else if (Control instanceof Spinner && ((Spinner) Control).getSelectedItem() != null) {
                    //Texto = ((Spinner) Control).getSelectedItem().toString();
                    Texto = String.valueOf(_iSelectList.GetValueByCodeField(((Spinner) Control).getSelectedItem().toString()));
                } else if (Control instanceof SearchView) {
                    SearchView searchView = (SearchView)Control;
                    Texto = searchView.getQuery().toString();
                    if(arrayId[Indice] == "search_tipo_contrato" && Texto.trim().equals("")){
                        Texto = "15";
                    }
                    else{
                        Texto = String.valueOf(_iSelectList.GetValueByCodeField(Texto));
                    }
                }

                //Si el texto es vacío entonces
                if (TextUtils.isEmpty(Texto.trim())) {

                    String Textico = Control.getTag() == null ? arrayId[Indice] : Control.getTag().toString();

                    ListaErrores.add("El campo " + Textico + " es obligatorio"); //arrayId[Indice]
                } else {
                    Parameter Parametro = new Parameter();
                    Parametro.setKey(arrayId[Indice]);
                    Parametro.setValue(Texto);
                    ListaValores.add(Parametro);
                }
            }
        }




        //Si hay errores entonces
        if (ListaErrores.size() > 0) {
            Toast.makeText(pagina.getApplicationContext(), ListaErrores.get(0), Toast.LENGTH_LONG).show(); //Muestra el mensaje
            return false; //Retorna falso
        } else {

            /************************************************** CAMPOS OPCIONALES*/

            for(String CampoOpcional : arrayOptionals)
            {
                int Id = pagina.getResources().getIdentifier(CampoOpcional, "id", pagina.getPackageName()); //Obtiene el Id del recurso del Control

                View Control = pagina.findViewById(Id); //Obtiene el control

                if (Control != null) {

                    String Texto = "";

                    //Si el control x es un TextView entonces
                    if (Control instanceof TextView) {
                        Texto = ((TextView) Control).getText().toString();
                        //Si el control x es un EditText entonces
                    } else if (Control instanceof EditText) {
                        Texto = ((EditText) Control).getText().toString();
                        //Si el control x es Spinner entonces
                    } else if (Control instanceof Spinner && ((Spinner) Control).getSelectedItem() != null) {
                        //Texto = ((Spinner) Control).getSelectedItem().toString();
                        Texto = String.valueOf(_iSelectList.GetValueByCodeField(((Spinner) Control).getSelectedItem().toString()));
                    } else if (Control instanceof SearchView) {
                        SearchView searchView = (SearchView) Control;
                    }

                        Parameter Parametro = new Parameter();
                        Parametro.setKey(CampoOpcional);
                        Parametro.setValue(Texto);
                        ListaValores.add(Parametro);
                }
            }

            /************************************************** FIN CAMPOS OPCIONALES*/

            //Guardar en Realm
            IRealmInstance iRealmInstance = new RealmInstance(pagina.getApplicationContext(), new RSA(pagina.getApplicationContext()));

            for (Parameter Parametro : ListaValores) {
                iRealmInstance.Insert(Parametro);
            }

            //Pasar a la siguiente pagina
            Intent intent = new Intent(pagina, clase);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pagina.startActivityForResult(intent, 0);
            return true; //Retorna verdadero
        }



    }

    //Carga el Spinner especificado
    public void Cargar(AppCompatActivity pagina, AdapterView spinner) {
        String idField = pagina.getResources().getResourceEntryName(spinner.getId());
        if (spinner instanceof Spinner) {
            ListViewAdapter adapter = new ListViewAdapter(pagina, _iSelectList.GetArrayByIdField(idField));
            spinner.setAdapter(adapter);
        }
        else{
            ListViewAdapter Adaptadortm = new ListViewAdapter(pagina, _iSelectList.GetArrayByIdField(idField));
            spinner.setAdapter(Adaptadortm);
        }

        /*switch (spinner.getId()) {
            case R.id.spinner_genero:
                ListViewAdapter Adaptadortm = new ListViewAdapter(pagina, _iSelectList.GetArrayByCodeField("10X027"));
                spinner.setAdapter(Adaptadortm);
                break;
            case R.id.spinner_tipo_cliente:
                ListViewAdapter Adaptadortc = new ListViewAdapter(pagina, _iSelectList.GetArrayByCodeField("10X001"));
                spinner.setAdapter(Adaptadortc);
                break;
            case R.id.listview_pagaduria:
                ListViewAdapter Adaptador = new ListViewAdapter(pagina, _iSelectList.GetArrayByCodeField("10X015"));
                spinner.setAdapter(Adaptador);
                break;
            case R.id.spinner_destino_credito:
                ListViewAdapter AdaptadorDc = new ListViewAdapter(pagina, _iSelectList.GetArrayByCodeField("10X004"));
                spinner.setAdapter(AdaptadorDc);
                break;
            case R.id.listview_ciudad_expedicion_cedula:
                ListViewAdapter Adaptador23 = new ListViewAdapter(pagina, _iSelectList.GetArrayByCodeField("10X026"));
                spinner.setAdapter(Adaptador23);
                break;
        }*/
    }

    //Obtiene el valor especificado
    //<pagina>La pagina que se quiere conectar a Realm</pagina>
    //<Llave>La llave a la que se le quiere obtener su valor</Llave>
    public String ObtenerValor(AppCompatActivity pagina, String Llave) {
        IRealmInstance iRealmInstance = new RealmInstance(pagina, new RSA(pagina.getBaseContext()));
        Parameter newParameter = new Parameter();
        newParameter.setKey("2234"); //Valor quemado
        newParameter.setValue("jejejeje"); //Valor quemado
        iRealmInstance.Insert(newParameter);

        List<RealmObject> lisParameters = iRealmInstance.GetAll(newParameter); //Actualmente se pasa Parameter para que pueda obtener los objetos del tipo especificado

        for (RealmObject ObjetoRealm : lisParameters) {

            Parameter Parametro = (Parameter) ObjetoRealm;

            String LlaveActual = Parametro.getKey().trim().toUpperCase();

            if (LlaveActual.equals(Llave.trim().toUpperCase())) {
                return Parametro.getValue();
            }
        }

        return "";
    }




}
