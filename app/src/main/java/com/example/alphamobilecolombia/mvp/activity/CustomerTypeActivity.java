package com.example.alphamobilecolombia.mvp.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.Request.GetPagaduriasRequest;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.validaciones.Formulario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.alphamobilecolombia.utils.validaciones.Formulario.DIALOG_REALLY_EXIT_ID;

/**
 * Define la actividad del tipo de cliente
 */
public class CustomerTypeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {

    /************************** INYECCIÓN */

    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();

    /************************** FORMULARIO */

    //Define la clase con funcionalidad para los formularios
    private Formulario formulario;

    /************************** TIPO DE CLIENTE */

    //Define los controles combobox tipo cliente, y tipo contrato
    private Spinner spinner_tipo_cliente;

    //************************** PANEL CAMPOS

    //Define el campo para ocultar los campos según el tipo de cliente
    private LinearLayout panel_campos;

    /************************** TIPO DE CONTRATO */

    //Define la lista de los tipos de contrato
    private List<String> ListaTipoContrato = new ArrayList<>();

    //Define el adaptador de la lista de los tipos de contrato
    private ListViewAdapter adapter;

    //Define el texto del tipo de contrato
    private TextView txttipo_contrato;

    /******************* SPINNER */

    //Define el control de lista de los tipos de contrato
    private Spinner spinner_tipo_contrato;

    /******************* SEARCH */

    //Define el campo buscador de los tipos de contrato
    /*private SearchView search_tipo_contrato;*/

    //Define el control de lista de los tipos de contrato
    /*private ListView listview_tipo_contrato;*/

    /************************** ANTIGUEDAD EN MESES */

    //Define el panel antiguedad en meses
    /*private LinearLayout panel_antiguedad_en_meses;*/

    //Define el campo antiguedad en meses
    /*private EditText edt_antiguedad_en_meses;*/

    /************************** FECHA INGRESO */

    //Define el texto de la fecha de ingreso
    private TextView txtfecha_ingreso;

    //Define el control fecha de ingreso
    private TextView textview_fecha_ingreso;

    //Define el evento del control fecha de ingreso
    private DatePickerDialog.OnDateSetListener evento_fecha_ingreso;

    /************************** FECHA FINALIZACION CONTRATO */

    //Define el control de texto del tipo de contrato
    /*private TextView txtfecha_finalizacion_contrato;*/

    //Define el control fecha de finalización del contrato
    /*private TextView textview_fecha_finalizacion_contrato;*/

    //Define el evento del control fecha finalización del contrato
    /*private DatePickerDialog.OnDateSetListener evento_fecha_finalizacion_contrato;*/

    /**************************************************************** INICIO */

    //Inicializa las propiedades de esta actividad
    public CustomerTypeActivity() {
        formulario = new Formulario(diContainer.injectISelectList(this));
    }

    /*****************************************************************/

    //Se produce cuando se inicia esta actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /********************************************************************** ACTIVIDAD */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_type);

        /********************************************************************** TITULO PAGINA */

        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Nueva solicitud");

        /********************************************************************** PANELES */

        panel_campos = (LinearLayout) findViewById(R.id.panel_campos);

        /*panel_antiguedad_en_meses = (LinearLayout) findViewById(R.id.panel_antiguedad_en_meses);*/

        /*panel_antiguedad_en_meses.setVisibility(View.GONE);*/

        /********************************************************************** TIPO CLIENTE */

        /*spinner_tipo_cliente = (Spinner) findViewById(R.id.spinner_tipo_cliente);
        ArrayAdapter<CharSequence> adapter_tipo_cliente = ArrayAdapter.createFromResource(this,
                R.array.spinner_employee_type, android.R.layout.simple_spinner_item);
        adapter_tipo_cliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo_cliente.setAdapter(adapter_tipo_cliente);*/

        spinner_tipo_cliente = (Spinner) findViewById(R.id.spinner_tipo_cliente);
        formulario.Cargar(this, spinner_tipo_cliente);

        spinner_tipo_cliente.setOnItemSelectedListener(this);

        /*********************************************************************** TIPO CONTRATO */

        txttipo_contrato = (TextView) findViewById(R.id.txttipo_contrato);

        /******************* SPINNER */

        spinner_tipo_contrato = (Spinner) findViewById(R.id.search_tipo_contrato);
        /*spinner_tipo_contrato.setOnItemSelectedListener(this);*/

        /******************* SEARCH */

        /*search_tipo_contrato = (SearchView) findViewById(R.id.search_tipo_contrato);
        listview_tipo_contrato = (ListView) findViewById(R.id.listview_tipo_contrato);*/

        /*new Formulario().Cargar(this, listview_tipo_contrato);*/

        /*adapter = (ListViewAdapter) listview_tipo_contrato.getAdapter();*/

        /*for (int i = 0; i < adapter.getCount(); i++)
            ListaTipoContrato.add(adapter.getItem(i));*/

        /* search_tipo_contrato.setOnQueryTextListener(this);*/

        /*listview_tipo_contrato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String Texto = (String) parent.getItemAtPosition(position);

                search_tipo_contrato.setQuery(Texto, false);

                listview_tipo_contrato.setVisibility(View.GONE);

                enableSearchView(search_tipo_contrato, false);

                EstablecerSeleccion(search_tipo_contrato, 0);

            }
        });

        int searchCloseButtonId = search_tipo_contrato.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = (ImageView) this.search_tipo_contrato.findViewById(searchCloseButtonId);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableSearchView(search_tipo_contrato, true);
                search_tipo_contrato.setQuery("", false);
            }
        });*/

        /****************************/

        /********************************************************************** FECHA INGRESO */

        txtfecha_ingreso = (TextView) findViewById(R.id.txtfecha_ingreso);

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


                Calendar FechaMinima = Calendar.getInstance();

                String[] FechaNacimiento = formulario.ObtenerValor(CustomerTypeActivity.this, "edt_birthDate").split("/");

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

/*        txtfecha_finalizacion_contrato = (TextView) findViewById(R.id.txtfecha_finalizacion_contrato);
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
                dialog.getDatePicker().setMaxDate((long) (cal.getTimeInMillis()));
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
        };*/

        //*********************************************************************** ANTIGUEDAD EN MESES

        /*edt_antiguedad_en_meses = (EditText) findViewById(R.id.edt_antiguedad_en_meses);*/

        //***********************************************************************

    }

    //Se produce al seleccionar un combobox
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Compara el Spinner que ejecutó el evento
        switch (parent.getId()) {
            case R.id.spinner_tipo_cliente:

                if (spinner_tipo_cliente.getSelectedItem().toString().toUpperCase().equals("EMPLEADO")) {

                    /*panel_campos.setVisibility(View.VISIBLE);*/
                    /*txtfecha_ingreso.setText("Fecha de ingreso *");*/
                    /*panel_antiguedad_en_meses.setVisibility(View.GONE);*/

                    //Fecha de Ingreso
                    txtfecha_ingreso.setVisibility(View.VISIBLE);
                    textview_fecha_ingreso.setVisibility(View.VISIBLE);

                    //Tipo de Contrato
                    txttipo_contrato.setVisibility(View.VISIBLE);

                    /******************* CARGA SPINNER TIPO CONTRATO */

/*                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.spinner_type_contract_employee, android.R.layout.simple_spinner_item);
                    spinner_tipo_contrato.setAdapter(adapter2);*/

                    ListaTipoContrato.clear();

                    ListaTipoContrato.add("CARRERA ADMINISTRATIVA");
                    ListaTipoContrato.add("LIBRE NOMBRAMIENTO Y REMOCION");
                    ListaTipoContrato.add("PROVISIONAL");
                    ListaTipoContrato.add("OTRO");
                    ListaTipoContrato.add("INDEFINIDO");
                    ListaTipoContrato.add("EN PROPIEDAD");
                    ListaTipoContrato.add("CORRETAJE");
                    ListaTipoContrato.add("ORDINARIO-ACTIVO");
                    ListaTipoContrato.add("PERIODO DE PRUEBA");
                    ListaTipoContrato.add("PLANTA");
                    ListaTipoContrato.add("RESOLUCION");
                    ListaTipoContrato.add("TEMPORAL");
                    ListaTipoContrato.add("PLANTA PERMANENTE");
                    ListaTipoContrato.add("FIJO");

                    adapter = new ListViewAdapter(this, ListaTipoContrato);

                    spinner_tipo_contrato.setAdapter(adapter);

                    spinner_tipo_contrato.setVisibility(View.VISIBLE);

                    /******************* CARGA SEARCH TIPO CONTRATO */

                    ListaTipoContrato.clear();

                    ListaTipoContrato.add("CARRERA ADMINISTRATIVA");
                    ListaTipoContrato.add("LIBRE NOMBRAMIENTO Y REMOCION");
                    ListaTipoContrato.add("PROVISIONAL");
                    ListaTipoContrato.add("OTRO");
                    ListaTipoContrato.add("INDEFINIDO");
                    ListaTipoContrato.add("EN PROPIEDAD");
                    ListaTipoContrato.add("CORRETAJE");
                    ListaTipoContrato.add("ORDINARIO-ACTIVO");
                    ListaTipoContrato.add("PERIODO DE PRUEBA");
                    ListaTipoContrato.add("PLANTA");
                    ListaTipoContrato.add("RESOLUCION");
                    ListaTipoContrato.add("TEMPORAL");
                    ListaTipoContrato.add("PLANTA PERMANENTE");
                    ListaTipoContrato.add("FIJO");

                    /*adapter = new ListViewAdapter(this, ListaTipoContrato);*/

                    /*listview_tipo_contrato.setAdapter(adapter);*/

                    /*search_tipo_contrato.setVisibility(View.VISIBLE);*/
                    /*search_tipo_contrato.setQuery("", false);*/

                    /********************/

                    //Fecha de Finalización del Contrato
                    /*txtfecha_finalizacion_contrato.setVisibility(View.VISIBLE);*/
                    /*textview_fecha_finalizacion_contrato.setVisibility(View.VISIBLE);*/

                } else {

                    //Fecha de Ingreso
                    txtfecha_ingreso.setVisibility(View.GONE);
                    textview_fecha_ingreso.setVisibility(View.GONE);

                    /*panel_campos.setVisibility(View.GONE);*/
                    /*txtfecha_ingreso.setText("Fecha de ingreso");*/
                    /*panel_antiguedad_en_meses.setVisibility(View.VISIBLE);*/

                    /******************* CARGA SPINNER TIPO CONTRATO */

                    ListaTipoContrato.clear();

                    ListaTipoContrato.add("PENSIONADO");

                    adapter = new ListViewAdapter(this, ListaTipoContrato);

                    /* listview_tipo_contrato.setAdapter(adapter);*/

                    /*search_tipo_contrato.setVisibility(View.GONE);*/
                    /*search_tipo_contrato.setQuery("", false);*/

                    /******************* CARGA SEARCH TIPO CONTRATO */

                    /*ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.spinner_type_contract_retired, android.R.layout.simple_spinner_item);*/
                    spinner_tipo_contrato.setAdapter(adapter);

                    /********************/

                    //Fecha de Finalización del Contrato
                    /*txtfecha_finalizacion_contrato.setVisibility(View.GONE);
                    textview_fecha_finalizacion_contrato.setVisibility(View.GONE);*/
                }

                break;
/*            case R.id.spinner_tipo_contrato:

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

                break;*/
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    //Se produce cuando se presiona el botón Siguiente
    public void onClickBtnNewRequest(View view) {

        /*boolean Correcto = search_tipo_contrato.getVisibility() == View.GONE ? true : false;

        //Si el tipo de contrato es obligatorio entonces valida su valor correcto
        if (!Correcto)
            for (int Indice = 0; Indice < ListaTipoContrato.size(); Indice++) {

                String Cadena1 = search_tipo_contrato.getQuery().toString().trim().toUpperCase();

                String Cadena2 = ListaTipoContrato.get(Indice).trim().toUpperCase();

                if (Cadena1.equals(Cadena2)) {
                    Correcto = true;
                    break;
                }
            }

        if (Correcto) {*/

        //Define la lista de campos a validar
        ArrayList<String> Campos = new ArrayList<String>();

        //************************************************************ Si el empleado es activo entonces

        if (spinner_tipo_cliente.getSelectedItem().toString().trim().toUpperCase().equals("EMPLEADO")) {
            /*        if (panel_antiguedad_en_meses.getVisibility() == View.GONE) {*/

            //Agrega los campos a validar
            Campos.add("spinner_tipo_cliente");
            Campos.add("search_tipo_contrato"); /*OJO CAMBIAR NOMBRE POR spinner_tipo_contrato*/
            Campos.add("edt_fecha_ingreso");

            //Si el tipo de contrato es FIJO o TEMPORAL
/*            if ((spinner_tipo_contrato.getSelectedItem().toString().trim().toUpperCase().equals("FIJO") ||
                    spinner_tipo_contrato.getSelectedItem().toString().trim().toUpperCase().equals("TEMPORAL")) &&
                    TextUtils.isEmpty(textview_fecha_finalizacion_contrato.getText().toString().trim())) {
                Campos.add("edt_fecha_finalizacion_contrato");
            }*/

/*            if (textview_fecha_finalizacion_contrato.getVisibility() == View.VISIBLE) {
                Campos.add("edt_fecha_finalizacion_contrato");
            }*/
        }
        //************************************************************ Si el empleado es pensionado entonces
        /*        else if (panel_antiguedad_en_meses.getVisibility() == View.VISIBLE) {*/
        else if (spinner_tipo_cliente.getSelectedItem().toString().trim().toUpperCase().equals("PENSIONADO")) {
            //Agrega los campos a validar
            /*            Campos.add("edt_antiguedad_en_meses");*/
        }

        //************************************************************

        /*Campos.add("search_tipo_contrato");*/

        //Valida los campos
        formulario.Validar(this, UploadFileActivity.class, Campos.toArray(new String[Campos.size()]), new String[]{});

        //************************************************************

/*        } else {
            Toast.makeText(this.getApplicationContext(), "Debe seleccionar un tipo de contrato valido", Toast.LENGTH_LONG).show(); //Muestra el mensaje
        }*/


//        ArrayList<String> Campos = new ArrayList<String>();
//
//        Campos.add("spinner_tipo_cliente");
//        Campos.add("spinner_tipo_contrato");
//        Campos.add("edt_fecha_ingreso");
//
//        formulario.Validar(this, UploadFileActivity.class, Campos.toArray(new String[Campos.size()]), new String[]{});


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        /*String text = s;

        if (adapter.filter(text)) {
            listview_tipo_contrato.setVisibility(View.VISIBLE);
        } else {
            listview_tipo_contrato.setVisibility(View.GONE);
        }

        if (text.toUpperCase().equals("FIJO") || text.toUpperCase().equals("TEMPORAL")) {
            txtfecha_finalizacion_contrato.setVisibility(View.VISIBLE);
            textview_fecha_finalizacion_contrato.setVisibility(View.VISIBLE);
        } else {
            txtfecha_finalizacion_contrato.setVisibility(View.GONE);
            textview_fecha_finalizacion_contrato.setVisibility(View.GONE);
        }*/

        return false;
    }

    /*private void EstablecerSeleccion(View view, int position) {
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
    }*/

    /*private void enableSearchView(View view, boolean enabled) {
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
    }*/

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

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
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

    /*********************************************************************************/
}

/*
//Valida los campos
        if (new Formulario().Validar(this, Campos.toArray(new String[Campos.size()]))) {
                Intent intent = new Intent(view.getContext(), VoucherDataActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
        }
*/


/*        //Define si las validaciones son correctas
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
        }*/