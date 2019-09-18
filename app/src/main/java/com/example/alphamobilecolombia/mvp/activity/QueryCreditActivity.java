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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.alphamobilecolombia.utils.validaciones.Formulario.DIALOG_REALLY_EXIT_ID;

public class QueryCreditActivity extends AppCompatActivity {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    IQueryCreditPresenter _iQueryCreditPresenter;
    private String initDate = "";
    private String endDate = "";
    private String typeQuery = "";
    private String user;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Date date = new Date();
    private String documentNumber;
    //Nuevo
    private String pValor = "3";
    EditText searchEditext;
    TextView count_rows;
    ImageView iconCancel;
    private ImageView arrow;
    private int tabSelect = 0;

    public QueryCreditActivity() {
        _iQueryCreditPresenter = diContainer.injectDIIQueryCreditPresenter(this);
    }

    Dialog myDialog;
    RealmStorage storage = new RealmStorage();
    private TabLayout tabFilters;
    RecyclerView recyclerCredits;
    RecyclerAdapterQueryCredit adapter;
    //List<PostConsultarReporteCreditoResponse> listReporteCredito = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_credit);

        myDialog = new Dialog(this);

        Window window = this.getWindow();
        adapter = new RecyclerAdapterQueryCredit(new ArrayList<>(), recyclerCredits);
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Mis solicitudes");

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorHeader));
        }

        SharedPreferences sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        user = sharedPref.getString("idUser", "");
        //user = "1";
        typeQuery = "4";

        tabFilters = (TabLayout) findViewById(R.id.tabFilters);
        tabFilters.addTab(tabFilters.newTab().setText("3 DÍAS"));
        tabFilters.addTab(tabFilters.newTab().setText("2 SEMANAS"));
        tabFilters.addTab(tabFilters.newTab().setText("MÁS DE 2 SEMANAS"));

        tabFilters.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelect = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:
                        typeQuery = "4";
                        refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -3, 1)), dateFormat.format(date), pValor);
                        break;
                    case 1:
                        typeQuery = "4";
                        refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -14, 1)), dateFormat.format(date), pValor);

                        break;
                    case 2:
                        typeQuery = "4";
                        try {
                            Date initDateMonth = dateFormat.parse("2019-01-01");
                            int maxMonths = monthsBetweenDates(initDateMonth, date);
                            refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -maxMonths, 2)), dateFormat.format(date), pValor);
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

        endDate = dateFormat.format(date);
        initDate = dateFormat.format(operarFecha(date, -3, 1));
        myDialog.setContentView(R.layout.loading_page);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        //List<PostConsultarReporteCreditoResponse> ReporteCredito = new ArrayList<>();

        //Metodo para buscar con el boton del teclado
        searchEditext = findViewById(R.id.searchEditext);
        iconCancel = findViewById(R.id.icon_cancel);
        searchEditext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchEditext.getText().length() > 0) {
                        typeQuery = "2";
                        pValor = searchEditext.getText().toString();
                        refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -3, 1)), dateFormat.format(date), pValor);
                    } else {

                    }
                    return true;
                }
                return false;
            }
        });

        searchEditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    iconCancel.setVisibility(View.GONE);
                    tabFilters.setTabTextColors(getResources().getColor(R.color.ColorGray),
                            getResources().getColor(R.color.colorBlack));
                    typeQuery = "4";
                    LinearLayout tabStrip = ((LinearLayout) tabFilters.getChildAt(0));
                    for (int j = 0; j < tabStrip.getChildCount(); j++) {
                        tabStrip.getChildAt(j).setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                    }
                } else {
                    iconCancel.setVisibility(View.VISIBLE);
                    typeQuery = "2";
                    LinearLayout tabStrip = ((LinearLayout) tabFilters.getChildAt(0));
                    for (int j = 0; j < tabStrip.getChildCount(); j++) {
                        tabFilters.setTabTextColors(getResources().getColor(R.color.ColorGrayLight),
                                getResources().getColor(R.color.ColorGrayLight));
                        tabStrip.getChildAt(j).setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Bundle dataqr = this.getIntent().getExtras();
        if (dataqr != null) {
            documentNumber = dataqr.getString("DocumentNumber");
        }

        if (documentNumber != null) {
            refreshData(user, "2", initDate, endDate, documentNumber);
        } else {
            refreshData(user, typeQuery, initDate, endDate, pValor);
        }


        // Counts_rows
        count_rows = findViewById(R.id.count_rows);
        arrow = (ImageView) findViewById(R.id.arrow);

        //configurerView(model);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, ModuleActivity.class); //ScannerActivity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0);
        }
        return true;
    }

    private void refreshData(String user, String typeQuery, String initDate, String endDate, String documentNumber) {
        myDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryCreditActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PostQueryCredit[] model = _iQueryCreditPresenter.GetQuery(typeQuery, documentNumber, "18", user, initDate, endDate);
                        configurerView(model);
                    }
                });

            }
        }).start();
    }

    public void configurerView(PostQueryCredit[] model) {

        //RecyclerView
        recyclerCredits = findViewById(R.id.recyclerCredits);
        recyclerCredits.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Client> clients = new ArrayList<>();
        String title = "";

        Client client;
        if (model != null && model.length > 0) {
            for (int j = 0; j < model.length; j++) {
                List<PostQueryCredit> modelAux = new ArrayList<>();
                title = String.format("Cedula: %1s \nNombre: %2s \nNº solicitud: %3s", model[j].getDocumentoCliente(), model[j].getCliente(), model[j].getNumeroSolicitud());
                String cedula = model[j].getDocumentoCliente();
                String name = model[j].getCliente();
                String solicitud = model[j].getNumeroSolicitud();
                modelAux.add(model[j]);

                client = new Client(cedula, name, solicitud, modelAux);
                clients.add(client);
            }

            adapter = new RecyclerAdapterQueryCredit(clients, recyclerCredits);
            recyclerCredits.setAdapter(adapter);
            count_rows.setText(String.format("%1s Solicitudes", model.length));


        } else {
            adapter = new RecyclerAdapterQueryCredit(clients, recyclerCredits);
            recyclerCredits.setAdapter(adapter);
            count_rows.setText(String.format("%1s Solicitudes", clients.size()));
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


    public void onClickCancel(View view) {
        myDialog.show();
        searchEditext.setText("");
        switch (tabSelect) {
            case 0:
                typeQuery = "4";
                refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -3, 1)), dateFormat.format(date), pValor);
                break;
            case 1:
                typeQuery = "4";
                refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -14, 1)), dateFormat.format(date), pValor);

                break;
            case 2:
                typeQuery = "4";
                try {
                    Date initDateMonth = dateFormat.parse("2019-01-01");
                    int maxMonths = monthsBetweenDates(initDateMonth, date);
                    refreshData(user, typeQuery, dateFormat.format(operarFecha(date, -maxMonths, 2)), dateFormat.format(date), pValor);
                } catch (ParseException ex) {
                    // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
                }

                break;
        }

    }
}
