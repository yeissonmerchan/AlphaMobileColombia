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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.text.ParseException;
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

    //Nuevo
    private String pValor = "3";
    EditText searchEditext;
    TextView count_rows;

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
        //user = sharedPref.getString("idUser", "");
        user = "1";
        typeQuery = "4";

        final TabLayout tabFilters = (TabLayout) findViewById(R.id.tabFilters);
        tabFilters.addTab(tabFilters.newTab().setText("3 DÍAS"));
        tabFilters.addTab(tabFilters.newTab().setText("2 SEMANAS"));
        tabFilters.addTab(tabFilters.newTab().setText("MÁS DE 2 SEMANAS"));

        tabFilters.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        typeQuery = "4";
                        refreshData(user, typeQuery, initDate, dateFormat.format(operarFecha(date, -3, 1)));
                        break;
                    case 1:
                        typeQuery = "4";
                        refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -14, 1)), dateFormat.format(date));

                        break;
                    case 2:
                        typeQuery = "4";
                        try {
                            Date initDateMonth = dateFormat.parse("2019-01-01");
                            int maxMonths = monthsBetweenDates(initDateMonth, date);
                            refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -maxMonths, 2)), dateFormat.format(date));
                        } catch (ParseException ex) {
                            // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
                        }

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

        endDate = "2019-09-11";
        initDate = dateFormat.format(operarFecha(date, -3, 1));
        myDialog.setContentView(R.layout.loading_page);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        //List<PostConsultarReporteCreditoResponse> ReporteCredito = new ArrayList<>();

        //Metodo para buscar con el boton del teclado
        searchEditext = findViewById(R.id.searchEditext);
        searchEditext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchEditext.getText().length() > 0) {
                        typeQuery = "2";
                        pValor = searchEditext.getText().toString();
                        refreshData(user, typeQuery, initDate, dateFormat.format(operarFecha(date, -3, 1)));
                    } else {
                        Toast toast1 = Toast.makeText(getApplicationContext(), "Por favor digite un número de cédula válido", Toast.LENGTH_LONG);
                        toast1.show();
                    }
                    return true;
                }
                return false;
            }
        });


        //Metodo para detectar cuando hace alguna accion con el teclado
        searchEditext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //detecta la tecla de borrar en el teclado
                    if (searchEditext.getText().length() == 0) {
                        typeQuery = "4";
                        LinearLayout tabStrip = ((LinearLayout) tabFilters.getChildAt(0));
                        for (int i = 0; i < tabStrip.getChildCount(); i++) {
                            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return false;
                                }
                            });
                        }
                    }
                } else {
                    typeQuery = "2";
                    LinearLayout tabStrip = ((LinearLayout) tabFilters.getChildAt(0));
                    for (int i = 0; i < tabStrip.getChildCount(); i++) {
                        tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                }
                return false;
            }
        });

        refreshData(user, typeQuery, initDate, endDate);

        // Counts_rows
        count_rows = findViewById(R.id.count_rows);


        //configurerView(model);
    }

    private void refreshData(String user, String typeQuery, String initDate, String endDate) {
        myDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryCreditActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PostQueryCredit[] model = _iQueryCreditPresenter.GetQuery(typeQuery, pValor, "18", user, initDate, endDate);
                        configurerView(model);
                    }
                });

            }
        }).start();
    }

    public void configurerView(PostQueryCredit[] model) {

        //RecyclerView
        RecyclerView recyclerCredits = findViewById(R.id.recyclerCredits);
        RecyclerAdapterQueryCredit adapter = null;
        recyclerCredits.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Client> clients = new ArrayList<>();
        String title = "";

        Client client;
        if (model != null && model.length > 0) {
            for (int j = 0; j < model.length; j++) {
                List<PostQueryCredit> modelAux = new ArrayList<>();
                title = String.format("Cedula: %s1 \nNombre: %s2 \nNº solicitud: %s3", model[j].getDocumentoCliente(), model[j].getCliente(), model[j].getNumeroSolicitud());
                modelAux.add(model[j]);

                client = new Client(title, modelAux);
                clients.add(client);
            }

            adapter = new RecyclerAdapterQueryCredit(clients);
            recyclerCredits.setAdapter(adapter);
            count_rows.setText(String.format("%1s Resultados", model.length));

        } else {
            adapter = new RecyclerAdapterQueryCredit(clients);
            recyclerCredits.setAdapter(adapter);
            count_rows.setText(String.format("%1s Resultados", clients.size()));
            showDialog("Atención!", "No se encontraron registros para el filtro aplicado.\n\n Aplica un nuevo filtro e intenta nuevamente");
        }
        myDialog.dismiss();
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

    public int monthsBetweenDates(Date startDate, Date endDate) {

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        int monthsBetween = 0;
        int dateDiff = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH);

        if (dateDiff < 0) {
            int borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH);
            dateDiff = (end.get(Calendar.DAY_OF_MONTH) + borrrow) - start.get(Calendar.DAY_OF_MONTH);
            monthsBetween--;

            if (dateDiff > 0) {
                monthsBetween++;
            }
        } else {
            monthsBetween++;
        }
        monthsBetween += end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        monthsBetween += (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return monthsBetween;
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
