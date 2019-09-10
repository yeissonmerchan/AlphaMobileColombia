package com.example.alphamobilecolombia.mvp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.implement.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostConsultarReporteCreditoResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostQueryCredit;
import com.example.alphamobilecolombia.data.remote.instance.implement.MapRequest;
import com.example.alphamobilecolombia.data.remote.instance.implement.RetrofitInstance;
import com.example.alphamobilecolombia.mvp.adapter.ICreditSubjectAdapter;
import com.example.alphamobilecolombia.mvp.adapter.implement.QueryCreditAdapter;
import com.example.alphamobilecolombia.mvp.presenter.ICreditSubjectPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IQueryCreditPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.QueryCreditPresenter;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Adapter.RecyclerAdapterQueryCredit;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.Client;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.ClientDescription;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryCreditActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    IQueryCreditPresenter _iQueryCreditPresenter;
    public QueryCreditActivity(){
        _iQueryCreditPresenter = diContainer.injectDIIQueryCreditPresenter(this);
    }
    Dialog myDialog;
    RealmStorage storage = new RealmStorage();
    //List<PostConsultarReporteCreditoResponse> listReporteCredito = new ArrayList<>();
    ArrayList<Client> clients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_credit);

        myDialog = new Dialog(this);

        Window window = this.getWindow();

        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Mis solicitudes");

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorHeader));
        }

        SharedPreferences sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        //String user = sharedPref.getString("idUser", "");
        String user = "1";
        String initDate = "2019-07-02";
        String endDate = "2019-07-02";

        myDialog.setContentView(R.layout.loading_page);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        //List<PostConsultarReporteCreditoResponse> ReporteCredito = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                PostQueryCredit[] model = _iQueryCreditPresenter.GetQuery(user,initDate,endDate);

                myDialog.show();
            }
        }).start();
        configurerView();
    }

    public void configurerView() {
        TabLayout tabFilters = (TabLayout) findViewById(R.id.tabFilters);
        tabFilters.addTab(tabFilters.newTab().setText("3 DÍAS"));
        tabFilters.addTab(tabFilters.newTab().setText("2 SEMANAS"));
        tabFilters.addTab(tabFilters.newTab().setText("MÁS DE 2 SEMANAS"));

        //RecyclerView
        RecyclerView recyclerCredits = findViewById(R.id.recyclerCredits);
        recyclerCredits.setLayoutManager(new LinearLayoutManager(this));

        RecyclerAdapterQueryCredit adapter = new RecyclerAdapterQueryCredit(clients);
        recyclerCredits.setAdapter(adapter);



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

   /*public void generateControls(List data){

        //Se elimina la data anterior
        storage.deleteInfoConsultaCreditro(this);
        //Se guarda la data en el Storage
        storage.saveConsultaCreditro(this,data);

        for (int i = 0; i < data.size(); i++) {

            List<PostConsultarReporteCreditoResponse> ReporteCredito2;

            ReporteCredito2 = data;

            String Documento = ReporteCredito2.get(i).getDocumentoCliente(); //object.getString("documentoCliente");
            String NombreCliente = ReporteCredito2.get(i).getCliente(); //object.getString("cliente");
            String Estado = ReporteCredito2.get(i).getEstadoGeneral(); //object.getString("estadoGeneral");

            Context context = null;

            TableLayout tableLayout = (TableLayout)findViewById(R.id.Tabla);

            context = getApplicationContext();

            // Create a new table row.
            TableRow tableRow = new TableRow(context);
            tableRow.setPadding(0,10,0,10);
            tableRow.setBackgroundColor(Color.rgb(235,238,255));

            // Set new table row layout parameters.
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(layoutParams);

            // Add a TextView in the first column.
            TextView TextViewDoc = new TextView(context);
            TextViewDoc.setText(Documento);
            TextViewDoc.setTextColor(Color.rgb(91,91,91));
            TextViewDoc.setPadding(15,0,0,0);
            TextViewDoc.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            tableRow.addView(TextViewDoc, 0);

            // Add a TextView in the second column
            TextView TextViewNom = new TextView(context);
            TextViewNom.setPadding(0,0,20,0);
            TextViewNom.setText(NombreCliente);
            TextViewNom.setTextColor(Color.rgb(91,91,91));
            TextViewNom.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            tableRow.addView(TextViewNom, 1);

            // Add a TextView in the second column
            TextView TextViewEst = new TextView(context);
            TextViewEst.setText(Estado);
            TextViewEst.setTextColor(Color.rgb(91,91,91));
            TextViewEst.setPadding(0,0,15,0);
            TextViewEst.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            tableRow.addView(TextViewEst, 2);

            // Add a button in the third column

            Button[] my_button = new Button[data.size()];
            int Index = i;

            my_button[Index] = new Button(context);
            /*my_button[Index].setBackgroundResource(R.mipmap.logo_lupa);
            my_button[Index].setMaxHeight(35);
            my_button[Index].setMaxWidth(30);

           // my_button[Index].setText("Ver");
            my_button[Index].setId(Index);

            my_button[Index].setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    if (my_button[Index].getId() == ((Button) v).getId()){
                        //Toast.makeText(getBaseContext(), (Integer) data.get(Index), 0).show();

                        //Se obtienen los datos almacenados en el storage
                        List<PostConsultarReporteCreditoResponse> ConsultaResponse = storage.getConsultaCreditro(getApplicationContext());

                        if (ConsultaResponse == null)
                        {
                            Toast.makeText(getBaseContext(),"Se presento un error al obtener el detalle",1).show();
                            return;
                        }

                        TextView txtclose;
                        TextView numeroSolicitud;
                        TextView tipoCr;
                        TextView estadoGeneral;
                        TextView regional;
                        TextView oficina;
                        TextView coordinador;
                        TextView asesor;
                        TextView pagaduria;
                        TextView documentoCliente;
                        TextView cliente;
                        TextView fechaEnvioPrevalidacion;
                        TextView montoSugerido;
                        TextView cuotaSug;
                        TextView plazoSugerido;
                        TextView fechaPrevalidacion;
                        TextView observacionCredito;

                        myDialog.setContentView(R.layout.query_credit_poppup);

                        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);

                        numeroSolicitud =(TextView) myDialog.findViewById(R.id.numeroSolicitud);
                        tipoCr =(TextView) myDialog.findViewById(R.id.tipoCr);
                        estadoGeneral =(TextView) myDialog.findViewById(R.id.estadoGeneral);
                        regional =(TextView) myDialog.findViewById(R.id.regional);
                        oficina =(TextView) myDialog.findViewById(R.id.oficina);
                        coordinador =(TextView) myDialog.findViewById(R.id.coordinador);
                        asesor =(TextView) myDialog.findViewById(R.id.asesor);
                        pagaduria =(TextView) myDialog.findViewById(R.id.pagaduria);
                        documentoCliente =(TextView) myDialog.findViewById(R.id.documentoCliente);
                        cliente =(TextView) myDialog.findViewById(R.id.cliente);
                        fechaEnvioPrevalidacion =(TextView) myDialog.findViewById(R.id.fechaEnvioPrevalidacion);
                        montoSugerido =(TextView) myDialog.findViewById(R.id.montoSugerido);
                        cuotaSug =(TextView) myDialog.findViewById(R.id.cuotaSug);
                        plazoSugerido =(TextView) myDialog.findViewById(R.id.plazoSugerido);
                        fechaPrevalidacion =(TextView) myDialog.findViewById(R.id.fechaPrevalidacion);
                        observacionCredito =(TextView) myDialog.findViewById(R.id.observacionCredito);

                        numeroSolicitud.setText(ConsultaResponse.get(Index).getNumeroSolicitud());
                        tipoCr.setText(ConsultaResponse.get(Index).getTipoCr());
                        estadoGeneral.setText(ConsultaResponse.get(Index).getEstadoGeneral());
                        regional.setText(ConsultaResponse.get(Index).getRegional());
                        oficina.setText(ConsultaResponse.get(Index).getOficina());
                        coordinador.setText(ConsultaResponse.get(Index).getCoordinador());
                        asesor.setText(ConsultaResponse.get(Index).getAsesor());
                        pagaduria.setText(ConsultaResponse.get(Index).getPagaduria());
                        documentoCliente.setText(ConsultaResponse.get(Index).getDocumentoCliente());
                        cliente.setText(ConsultaResponse.get(Index).getCliente());
                        fechaEnvioPrevalidacion.setText(ConsultaResponse.get(Index).getFechaEnvioPrevalidacion());
                        montoSugerido.setText(ConsultaResponse.get(Index).getMontoSugerido());
                        cuotaSug.setText(ConsultaResponse.get(Index).getCuotaSug());
                        plazoSugerido.setText(ConsultaResponse.get(Index).getPlazoSugerido());
                        fechaPrevalidacion.setText(ConsultaResponse.get(Index).getFechaPrevalidacion());
                        observacionCredito.setText(ConsultaResponse.get(Index).getObservacionCredito());

                        txtclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog.dismiss();
                            }
                        });
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        myDialog.show();

                    }
                }
            });
            tableRow.addView(my_button[Index], 3);

            tableLayout.addView(tableRow);

        }

    }*/

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    // Tab_filters

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
