package com.example.alphamobilecolombia.mvp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.PostConsultarReporteCreditoResponse;
import com.example.alphamobilecolombia.mvp.presenter.ConsultaCreditosPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaCreditosActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_creditos);


        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }

        RealmStorage storage = new RealmStorage();
        storage.initLocalStorage(this);


        SharedPreferences sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String user = sharedPref.getString("idUser", "");

        // Consumo
        ConsultaCreditosPresenter presenter = new ConsultaCreditosPresenter();
        HttpResponse model = presenter.PostConsultarSolicitudes(user);
        // HttpResponse model = null;

        if (model != null) {

            JSONObject data = (JSONObject) model.getData();

            try {

                List<PostConsultarReporteCreditoResponse> ReporteCredito = new ArrayList<>();

                JSONArray jSONArray = (JSONArray) data.getJSONArray("data");
                PostConsultarReporteCreditoResponse ReporteCreditoResponse;
                for (int i = 0; i < jSONArray.length(); i++) {
                    ReporteCreditoResponse = new PostConsultarReporteCreditoResponse();
                    JSONObject object = (JSONObject) jSONArray.get(i);
                    ReporteCreditoResponse.setEstadoGeneral(object.getString("estadoGeneral"));
                    ReporteCreditoResponse.setRegional(object.getString("regional"));
                    ReporteCreditoResponse.setOficina(object.getString("oficina"));
                    ReporteCreditoResponse.setCoordinador(object.getString("coordinador"));
                    ReporteCreditoResponse.setAsesor(object.getString("asesor"));
                    ReporteCreditoResponse.setPagaduria(object.getString("pagaduria"));
                    ReporteCreditoResponse.setDocumentoCliente(object.getString("documentoCliente"));
                    ReporteCreditoResponse.setCliente(object.getString("cliente"));
                    ReporteCreditoResponse.setFechaEnvioPrevalidacion(object.getString("fechaEnvioPrevalidacion"));
                    ReporteCreditoResponse.setMontoSugerido(object.getString("montoSugerido"));
                    ReporteCreditoResponse.setCuotaSug(object.getString("cuotaSug"));
                    ReporteCreditoResponse.setPlazoSugerido(object.getString("plazoSugerido"));
                    ReporteCreditoResponse.setFechaPrevalidacion(object.getString("fechaPrevalidacion"));
                    ReporteCreditoResponse.setObservacionCredito(object.getString("observacionCredito"));
                    ReporteCreditoResponse.setNumeroSolicitud(object.getString("numeroSolicitud"));
                    ReporteCreditoResponse.setTipoCr(object.getString("tipoCr"));
                    ReporteCredito.add(ReporteCreditoResponse);
                }
                generateControls(ReporteCredito);
            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // Intent intent = new Intent(view.getContext(), ModuloActivity.class);
            // startActivityForResult(intent, 0);
            // message.setText(model.getMessage());
        }
    }
    public void generateControls(List data){

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
            /*Button button = new Button(context);
            button.setText("Buscar");
            tableRow.addView(button, 3);*/

            tableLayout.addView(tableRow);

        }
    }

}
