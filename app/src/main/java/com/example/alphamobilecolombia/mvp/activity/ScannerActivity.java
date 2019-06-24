package com.example.alphamobilecolombia.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.utils.cryptography.models.Persona;
import com.example.alphamobilecolombia.utils.extensions.CedulaQrAnalytics;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ScannerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private static final String SCAN = "scan";
    private static final String CANCELLED = "cancelled";
    private static final String FORMAT = "format";
    private static final String TEXT = "text";
    private static final String LOG_TAG = "BarcodeScanner";
    private ArrayList<String> als = new ArrayList<>();
    Collection<String> TYPES = Arrays.asList("PDF417");
    Spinner spinner_tipo_empleado, spinner_tipo_contrato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Datos personales");


        new IntentIntegrator(ScannerActivity.this)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setPrompt("Scan")
                .setCameraId(0)
                .setBeepEnabled(false)
                .setBarcodeImageEnabled(false)
                .initiateScan();


        Spinner spinner_destino_credito = (Spinner) findViewById(R.id.spinner_destino_credito);
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
/*        ArrayAdapter<CharSequence> adapter_tipo_contrato = ArrayAdapter.createFromResource(this,
                R.array.spinner_tipo_contrato_empleado, android.R.layout.simple_spinner_item);
        adapter_tipo_contrato.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo_contrato.setAdapter(adapter_tipo_contrato);
*/
        spinner_tipo_empleado.setOnItemSelectedListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Scaneo cancelado");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Persona p;

                    JSONObject obj = new JSONObject();
                    try {
                        obj.put(TEXT, data.getStringExtra("SCAN_RESULT"));
                        obj.put(FORMAT, data.getStringExtra("SCAN_RESULT_FORMAT"));
                        obj.put(CANCELLED, false);
                    } catch (JSONException e) {
                        Log.d(LOG_TAG, "JSONException "+e.getMessage());
                    }
                    String nuevo = obj.getString("text").replace("\u0000", "|");
                    String cadena = nuevo.replaceAll("\\s", "|");
                    cadena = cadena.replaceAll("\\t", "|");
                    cadena = cadena.replaceAll("\\|{2,}", " ");

                    p = CedulaQrAnalytics.parse(cadena);
                    //this.db.add(p);
                    //this.als.add(p.toString());
                    Toast.makeText(getApplicationContext(),
                            p.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity", "Scaneado");

                    EditText edt_names = (EditText) findViewById(R.id.edt_names);
                    edt_names.setText(p.getNombre());

                    EditText edt_names2 = (EditText) findViewById(R.id.edt_names2);
                    edt_names2.setText("");//(p.getNombre());

                    EditText edt_lastNames = (EditText) findViewById(R.id.edt_lastNames);
                    edt_lastNames.setText(p.getApellido1());

                    EditText edt_lastNames2 = (EditText) findViewById(R.id.edt_lastNames2);
                    edt_lastNames.setText(p.getApellido2());

                    EditText edt_numberIdentification = (EditText) findViewById(R.id.edt_numberIdentification);
                    edt_numberIdentification.setText(p.getCedula());

                    EditText edt_birthDate = (EditText) findViewById(R.id.edt_birthDate);
                    edt_birthDate.setText(p.getFechaNacimiento());

                    EditText edt_genero = (EditText) findViewById(R.id.edt_genero);
                    edt_genero.setText(Character.toString(p.getGenero()));

                    EditText edt_factor = (EditText) findViewById(R.id.edt_factor);
                    edt_factor.setText(p.getFactorRh());


                    Toast.makeText(this, p.toString(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    //Toast.makeText(this, "Error: No se pudo hacer el parse"+e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClickBtnNextTerms(View view) {
        Intent intent = new Intent (view.getContext(), TerminosActivity.class);
        startActivityForResult(intent, 0);

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
}
