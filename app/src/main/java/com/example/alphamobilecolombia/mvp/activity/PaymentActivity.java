package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.Request.GetPagaduriasRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.mvp.presenter.implement.ScannerPresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.validaciones.Formulario;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;

import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {

    // Declare Variables
    ListView listview_pagaduria;
    ListViewAdapter adapter;
    SearchView search_pagaduria;

    //Define la lista de pagadurías
    private List<GetPagaduriasRequest> pagadurias = new ArrayList<>();

    //Define la lista de pagadurías
    private List<String> ListaPagadurias = new ArrayList<>();

    //Define el control combobox destino del credito
    private Spinner spinner_pagaduria, spinner_destino_credito;

    //Se produce cuando se inicia esta actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        /********************************************************************** PAGADURIA */

        listview_pagaduria = (ListView) findViewById(R.id.listview_pagaduria);
        new Formulario().Cargar(this, listview_pagaduria);

        adapter = (ListViewAdapter) listview_pagaduria.getAdapter();

        for (int i = 0; i < adapter.getCount(); i++)
            ListaPagadurias.add(adapter.getItem(i));

        search_pagaduria = (SearchView) findViewById(R.id.search_pagaduria);
        search_pagaduria.setOnQueryTextListener(this);

        listview_pagaduria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String Texto = (String) parent.getItemAtPosition(position);

                search_pagaduria.setQuery(Texto, false);

                listview_pagaduria.setVisibility(View.GONE);

                enableSearchView(search_pagaduria, false);

                EstablecerSeleccion(search_pagaduria, 0);

            }
        });

        int searchCloseButtonId = search_pagaduria.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.search_pagaduria.findViewById(searchCloseButtonId);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableSearchView(search_pagaduria, true);
                search_pagaduria.setQuery("", false);
            }
        });

        /********************************************************************** DESTINO DEL CREDITO */

        spinner_destino_credito = (Spinner) findViewById(R.id.spinner_destino_credito);
        new Formulario().Cargar(this, spinner_destino_credito);

        /***********************************************************************/

    }

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

        boolean Correcto = false;

        for (int Indice = 0; Indice < ListaPagadurias.size(); Indice++) {

            String Cadena1 = search_pagaduria.getQuery().toString().trim().toUpperCase();

            String Cadena2 = ListaPagadurias.get(Indice).trim().toUpperCase();

            if (Cadena1.equals(Cadena2)) {
                Correcto = true;
                break;
            }
        }

        if (Correcto) {
            String[] Campos = new String[]{"spinner_destino_credito"}; //"spinner_pagaduria"

            new Formulario().Validar(this, CustomerTypeActivity.class, Campos);
        } else {
            Toast.makeText(this.getApplicationContext(), "Debe seleccionar una pagaduría valida", Toast.LENGTH_LONG).show(); //Muestra el mensaje
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
            listview_pagaduria.setVisibility(View.VISIBLE);
        } else {
            listview_pagaduria.setVisibility(View.GONE);
        }

        return false;
    }


}


/*        spinner_pagaduria = (Spinner) findViewById(R.id.spinner_pagaduria);
        new Formulario().Cargar(this, spinner_pagaduria);*/

/*        animalNameList = new String[]{"Lion", "Tiger", "Dog",
                "Cat", "Tortoise", "Rat", "Elephant", "Fox",
                "Cow", "Donkey", "Monkey"};




        List<GetPagaduriasRequest> pagadurias = new ArrayList<>();
        ScannerPresenter scannerPresenter = new ScannerPresenter();
        HttpResponse response = scannerPresenter.getPaying(this);
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
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "Pagadurías", ex, this);
        }

        for (int i = 0; i < pagadurias.size(); i++) {
            arraylist.add(pagadurias.get(i).getNombre());
        }

        // Locate the ListView in listview_main.xml
        listview_pagaduria = (ListView) findViewById(R.id.listview_pagaduria);

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        listview_pagaduria.setAdapter(adapter);*/




/*
        for (int i = 0; i < animalNameList.length; i++) {
            arraylist.add(animalNameList[i]);
        }
*/

/*        ScannerPresenter scannerPresenter = new ScannerPresenter();
        HttpResponse response = scannerPresenter.getPaying(this);
        Gson gson = new Gson();
        *//*contextView = this;*//*
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
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(), "Pagadurías", ex, this);
        }

        List<String> names = new ArrayList<>();
        for (GetPagaduriasRequest p : pagadurias) {
            names.add(p.getNombre());
        }

        ArrayAdapter userAdapter = new ArrayAdapter(this, R.layout.spinner, names);

        spinner_pagaduria = (Spinner) findViewById(R.id.spinner_pagaduria);
        spinner_pagaduria.setAdapter(userAdapter);*/








/*        spinner_destino_credito = (Spinner) findViewById(R.id.spinner_destino_credito);
        ArrayAdapter<CharSequence> adapter_destino_credito = ArrayAdapter.createFromResource(this,
                R.array.spinner_credit_destination, android.R.layout.simple_spinner_item);
        adapter_destino_credito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_destino_credito.setAdapter(adapter_destino_credito);*/















/*        if (new Formulario().Validar(this, Campos)) {
                //Pasar a la siguiente pagina
                Intent intent = new Intent(this, CustomerTypeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
        }*/

/*        if (spinner_pagaduria.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "El campo pagaduría es obligatorio", Toast.LENGTH_LONG).show();
        } else if (spinner_destino_credito.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "El campo destino del credito es obligatorio", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(view.getContext(), CustomerTypeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }*/