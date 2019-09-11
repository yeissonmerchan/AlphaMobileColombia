package com.example.alphamobilecolombia.mvp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.implement.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostQueryCredit;
import com.example.alphamobilecolombia.mvp.presenter.IQueryCreditPresenter;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Adapter.RecyclerAdapterQueryCredit;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.Client;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QueryCreditActivity extends AppCompatActivity {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    IQueryCreditPresenter _iQueryCreditPresenter;
    private String initDate = "";
    private String endDate = "";
    private String typeQuery = "";
    private String user;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Date date = new Date();

    public QueryCreditActivity() {
        _iQueryCreditPresenter = diContainer.injectDIIQueryCreditPresenter(this);
    }

    Dialog myDialog;
    RealmStorage storage = new RealmStorage();
    //List<PostConsultarReporteCreditoResponse> listReporteCredito = new ArrayList<>();

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
        // user = sharedPref.getString("idUser", "");
        user = "1";
        typeQuery = "4";

        TabLayout tabFilters = (TabLayout) findViewById(R.id.tabFilters);
        tabFilters.addTab(tabFilters.newTab().setText("3 DÍAS"));
        tabFilters.addTab(tabFilters.newTab().setText("2 SEMANAS"));
        tabFilters.addTab(tabFilters.newTab().setText("MÁS DE 2 SEMANAS"));

        tabFilters.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        typeQuery = "4";
                        QueryCreditActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshData(user, typeQuery, initDate, dateFormat.format(operarFecha(date, -3, 1)));
                            }
                        });
                        break;
                    case 1:
                        typeQuery = "4";
                        QueryCreditActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -14, 1)), dateFormat.format(date));
                            }
                        });
                        break;
                    case 2:
                        typeQuery = "4";
                        QueryCreditActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -8, 2)), dateFormat.format(date));
                            }
                        });
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //endDate = dateFormat.format(date);
        endDate = "2019-09-11";
        initDate = "2019-01-11";
        //initDate = dateFormat.format(operarFecha(date, -3, 1));
        myDialog.setContentView(R.layout.loading_page);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        //List<PostConsultarReporteCreditoResponse> ReporteCredito = new ArrayList<>();

        refreshData(user, typeQuery, initDate, endDate);

        //configurerView(model);
    }

    private void refreshData(String user, String typeQuery, String initDate, String endDate) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PostQueryCredit[] model = _iQueryCreditPresenter.GetQuery(typeQuery, "3", "18", user, initDate, endDate);
                myDialog.show();

                QueryCreditActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        configurerView(model);
                    }
                });

            }
        }).start();
    }

    public void configurerView(PostQueryCredit[] model) {

        //RecyclerView
        RecyclerView recyclerCredits = findViewById(R.id.recyclerCredits);
        recyclerCredits.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Client> clients = new ArrayList<>();
        String title = "";

        Client client;
        if (model != null) {
            for (int j = 0; j < model.length; j++) {
                List<PostQueryCredit> modelAux = new ArrayList<>();
                title = String.format(" %s1 \n %s2 \n  %s3", model[j].getDocumentoCliente(), model[j].getCliente(), model[j].getNumeroSolicitud());
                modelAux.add(model[j]);

                client = new Client(title, modelAux);
                clients.add(client);
            }

            RecyclerAdapterQueryCredit adapter = new RecyclerAdapterQueryCredit(clients);
            recyclerCredits.setAdapter(adapter);
            myDialog.dismiss();
        } else {
            showDialog("Atención!", "No se encontraron registros para el filtro aplicado.\n\n Aplica un nuevo filtro e intenta nuevamente");
        }
    }


    public Date operarFecha(Date fecha, int valor, int type) {
        Calendar calendar = Calendar.getInstance();
        if (type == 1) {
            calendar.setTime(fecha);
            calendar.add(Calendar.DAY_OF_YEAR, valor);
        } else if (type == 2) {
            calendar.setTime(fecha);
            calendar.add(Calendar.MONTH, valor);
        }
        return calendar.getTime();
    }

    private void showDialog(String title, String mensaje) {
        AlertDialog.Builder Alert = new AlertDialog.Builder(this);
        Alert.setTitle(title);
        Alert.setMessage(mensaje);
        Alert.setCancelable(false);

        Alert.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //myDialog.dismiss();
                        dialog.cancel();
                    }
                });


        AlertDialog AlertMsg = Alert.create();
        AlertMsg.setCanceledOnTouchOutside(false);
        AlertMsg.show();

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

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
    }

}
