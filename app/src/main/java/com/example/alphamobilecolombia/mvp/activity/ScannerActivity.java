package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.utils.extensions.CedulaQrAnalytics;
import com.example.alphamobilecolombia.utils.models.Person;
import com.example.alphamobilecolombia.utils.models.Persona;

import androidx.appcompat.app.AppCompatActivity;
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
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import co.venko.api.android.cedula.DocumentManager;

public class ScannerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Person person;
    Spinner spinner_tipo_empleado, spinner_tipo_contrato, spinner_destino_credito;
    Persona p;
    RealmStorage storage = new RealmStorage();

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


        spinner_destino_credito = (Spinner) findViewById(R.id.spinner_destino_credito);
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

                    DocumentManager documentManager = new DocumentManager();
                    byte[] rawdata = result.getContents().getBytes();
                    byte[] result2 = data.getStringExtra("SCAN_RESULT").getBytes("ISO-8859-1");
                    String user = getResources().getString(R.string.userkey);
                    String license = getResources().getString(R.string.licenceKey);

                    String resultScan = documentManager.parse(user, license, result2);
                    person = new Gson().fromJson(resultScan, Person.class);
                    p = CedulaQrAnalytics.parse(data);

                    storage.savePerson(this,person);

                    EditText edt_names = (EditText) findViewById(R.id.edt_names);
                    EditText edt_names2 = (EditText) findViewById(R.id.edt_names2);
                    EditText edt_lastNames = (EditText) findViewById(R.id.edt_lastNames);
                    EditText edt_lastNames2 = (EditText) findViewById(R.id.edt_lastNames2);
                    EditText edt_numberIdentification = (EditText) findViewById(R.id.edt_numberIdentification);
                    EditText edt_birthDate = (EditText) findViewById(R.id.edt_birthDate);
                    EditText edt_celular = (EditText) findViewById(R.id.edt_celular);
                    EditText edt_genero = (EditText) findViewById(R.id.edt_genero);
                    EditText edt_factor = (EditText) findViewById(R.id.edt_factor);

                    /*edt_names.setText((person.getFirstName().length() < 0 ? person.getFirstName() : p.getNombre()));
                    edt_names2.setText((person.getSecondName().length() < 0 ? person.getSecondName() : ""));
                    edt_lastNames.setText((person.getSurename().length() < 0 ? person.getSurename() : p.getApellido1()));
                    edt_lastNames2.setText((person.getSecondSurename().length() < 0 ? person.getSecondSurename() : p.getApellido2()));
                    edt_numberIdentification.setText((person.getNumber().length()< 0 ? person.getNumber() : p.getCedula()));
                    edt_birthDate.setText((person.getBirthday().length()< 0 ? person.getBirthday() : p.getFechaNacimiento()));
                    edt_genero.setText((person.getGender().length()< 0 ? person.getGender() : p.getGenero()));
                    edt_factor.setText((person.getBloodType().length()< 0 ? person.getBloodType() : p.getFactorRh()));
                    edt_celular.setText((person.getPlaceBirth().length()< 0 ? person.getPlaceBirth() : ""));
                    */

                    edt_names.setText(person.getFirstName());
                    edt_names2.setText(person.getSecondName());
                    edt_lastNames.setText(person.getSurename());
                    edt_lastNames2.setText(person.getSecondSurename());
                    edt_numberIdentification.setText(person.getNumber());
                    edt_birthDate.setText(person.getBirthday());
                    edt_genero.setText(person.getGender());
                    edt_factor.setText(person.getBloodType());
                    edt_celular.setText(person.getPlaceBirth());

                    //Toast.makeText(getApplicationContext(),p.toString(), Toast.LENGTH_SHORT).show();
                    //Log.d("MainActivity", "Scaneado");


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
        /*intent.putExtra("PERSONA_Documento", p.getCedula());
        intent.putExtra("PERSONA_PNombre", p.getNombre());
        intent.putExtra("PERSONA_SNombre", "");
        intent.putExtra("PERSONA_PApellido", p.getApellido1());
        intent.putExtra("PERSONA_SApellido", p.getApellido2());
        intent.putExtra("PERSONA_FechaNac", p.getFechaNacimiento());
        intent.putExtra("PERSONA_Genero", p.getGenero());
        intent.putExtra("PERSONA_Celular", p.getCelular());
        */

        if(person == null){
            person = storage.getPerson(this);
        }

        String bithdate = person.getBirthday().substring(0, 4) + "-" + person.getBirthday().substring(4, 6) + "-" + person.getBirthday().substring(6, 8);

        intent.putExtra("PERSONA_Documento", person.getNumber());
        intent.putExtra("PERSONA_PNombre", person.getFirstName());
        intent.putExtra("PERSONA_SNombre", person.getSecondName());
        intent.putExtra("PERSONA_PApellido", person.getSurename());
        intent.putExtra("PERSONA_SApellido", person.getSecondSurename());
        intent.putExtra("PERSONA_FechaNac", bithdate);
        intent.putExtra("PERSONA_Genero", person.getBloodType());
        intent.putExtra("PERSONA_Celular", person.getPlaceBirth());

        intent.putExtra("IdTipoEmpleado",spinner_tipo_empleado.getSelectedItem().toString());
        intent.putExtra("IdTipoContrato",spinner_tipo_contrato.getSelectedItem().toString());
        intent.putExtra("IdDestinoCredito",spinner_destino_credito.getSelectedItem().toString());

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
