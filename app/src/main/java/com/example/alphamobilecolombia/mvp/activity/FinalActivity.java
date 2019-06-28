package com.example.alphamobilecolombia.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.mvp.presenter.FinalPresenter;
import com.example.alphamobilecolombia.utils.models.Persona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;

import org.json.JSONException;
import org.json.JSONObject;

public class FinalActivity extends AppCompatActivity {
    final Context context = this;
    private String IdTipoEmpleado;
    private String IdTipoContrato;
    private String IdDestinoCredito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Finalización");


    }
    public void onClickBtnNewRequest(View view) {
        Intent intent = new Intent (view.getContext(), ScannerActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onClickBtnUpdFiles(View view) {



        /*
        //////////////      GUARDAR PERSONA
        Persona persona = new Persona();


        persona.setCedula(getIntent().getStringExtra("PERSONA_Documento"));
        persona.setNombre(getIntent().getStringExtra("PERSONA_PNombre"));
        persona.setApellido1(getIntent().getStringExtra("PERSONA_PApellido"));
        persona.setApellido2(getIntent().getStringExtra("PERSONA_SApellido"));
        persona.setFechaNacimiento(getIntent().getStringExtra("PERSONA_FechaNac"));
        persona.setGenero(getIntent().getStringExtra("PERSONA_Genero"));
        persona.setCelular(getIntent().getStringExtra("PERSONA_Celular"));
        IdTipoEmpleado = getIntent().getStringExtra("IdTipoEmpleado");
        IdTipoContrato = getIntent().getStringExtra("IdTipoContrato");
        IdDestinoCredito = getIntent().getStringExtra("IdDestinoCredito");

        SharedPreferences sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String user = sharedPref.getString("idUser", "");


        FinalPresenter presenter = new FinalPresenter();
        HttpResponse model = presenter.PostInsertPerson(persona, user);

        if (model != null) {

            try {


                JSONObject objeto = (JSONObject) model.getData();
                setData(sharedPref, objeto);
                String codigoTransaccion = objeto.getString("codigoTransaccion");
                int IdTypeEmployee = Integer.parseInt(getCodeTipoEmpleado(IdTipoEmpleado));
                int IdTypeCont = Integer.parseInt(getCodeTipoContrato(IdTipoEmpleado));
                int IdTypeDest = Integer.parseInt(getCodeDestinoCredito(IdDestinoCredito));


                HttpResponse modelSujetoCredito = presenter.PostInsertSujetoCredito(persona, codigoTransaccion, IdTypeEmployee, IdTypeCont, IdTypeDest,user);
                if (modelSujetoCredito!=null){
                    JSONObject objeto2 = (JSONObject) modelSujetoCredito.getData();
                    setData(sharedPref, objeto2);

                    Intent intent = new Intent(context, WebViewUpdFilesActivity.class);
                    startActivity(intent);
                }

            }
            catch (JSONException ex)
            {

            }
        }
    */

    }

}
