package com.example.alphamobilecolombia.mvp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.mvp.presenter.FinalPresenter;
import com.example.alphamobilecolombia.utils.models.Persona;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TerminosActivity extends AppCompatActivity {
    private Persona persona = new Persona();
    private String IdTipoEmpleado;
    private String IdTipoContrato;
    private String IdDestinoCredito;
    private String IdPagaduria;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);


        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Autorización de datos");



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
        IdPagaduria = getIntent().getStringExtra("IdPagaduria");


        btnAgree();
    }


    public void btnAgree() {

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
        IdPagaduria = getIntent().getStringExtra("IdPagaduria");
        SharedPreferences sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String user = sharedPref.getString("idUser", "");


        FinalPresenter presenter = new FinalPresenter();
        HttpResponse model = presenter.PostInsertPerson(persona, user,this.context);

        if (model != null) {

            try {
                if(model.getCode().contains("200")){

                    JSONObject objeto = (JSONObject) model.getData();
                    setData(sharedPref, objeto);
                    String codigoTransaccion = objeto.getString("codigoTransaccion");
                    int IdTypeEmployee = Integer.parseInt(getCodeTipoEmpleado(IdTipoEmpleado));
                    int IdTypeCont = Integer.parseInt(getCodeTipoContrato(IdTipoEmpleado));
                    int IdTypeDest = Integer.parseInt(getCodeDestinoCredito(IdDestinoCredito));
                    int idPagaduria = Integer.parseInt(IdPagaduria);

                    HttpResponse modelSujetoCredito = presenter.PostInsertSujetoCredito(persona, codigoTransaccion, IdTypeEmployee, IdTypeCont, IdTypeDest,user, idPagaduria,this.context);
                    if (modelSujetoCredito!=null){

                        if(modelSujetoCredito.getCode().contains("200")) {

                            JSONObject objeto2 = (JSONObject) modelSujetoCredito.getData();
                            setData(sharedPref, objeto2);
                            String idSujetoCredito = objeto2.getString("codigoTransaccion");

                            Context ctx = this.context;
                            Intent intent = new Intent(ctx, ArchivosV2Activity.class);
                            intent.putExtra("PERSONA_Documento", persona.getCedula());
                            intent.putExtra("PERSONA_PNombre", persona.getNombre());
                            intent.putExtra("PERSONA_SNombre", persona.getApellido2());
                            intent.putExtra("PERSONA_PApellido", persona.getApellido1());
                            intent.putExtra("PERSONA_SApellido", persona.getApellido2());
                            intent.putExtra("PERSONA_FechaNac", persona.getFechaNacimiento());
                            intent.putExtra("PERSONA_Genero", persona.getGenero());
                            intent.putExtra("PERSONA_Celular", persona.getCelular());
                            intent.putExtra("IdTipoEmpleado", IdTipoEmpleado);
                            intent.putExtra("IdTipoContrato", IdTipoContrato);
                            intent.putExtra("IdDestinoCredito", IdDestinoCredito);
                            intent.putExtra("IdSujetoCredito", idSujetoCredito);
                            intent.putExtra("IdPagaduria", IdPagaduria);
                            startActivityForResult(intent, 0);

                        }
                        else{
                            NotificacionErrorDatos(this.context);
                        }
                    }
                }
                else{
                    NotificacionErrorDatos(this.context);
                }
            }
            catch (JSONException ex)
            {
                System.out.println("Ha ocurrido un error! "+ex.getMessage());
            }
        }
        else{
            NotificacionErrorDatos(this.context);
        }
    }

    public void NotificacionErrorDatos(final Context view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view);
        builder1.setMessage("Ha ocurrido un error inesperado. Intentalo mas tarde.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(view, ModuloActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public void onClickBtnAgree(View view) {


    }

    public void setData(SharedPreferences sharedPref, JSONObject objeto) throws JSONException {

        String data = objeto.getString("codigoTransaccion");

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("codigoTransaccion", data);
        editor.commit();

    }

    private String getCodeDestinoCredito(String name) {
        int i = -1;
        for (String cc: getResources().getStringArray(R.array.spinner_destino_credito)) {
            i++;
            if (cc.equals(name))
                break;
        }
        return getResources().getStringArray(R.array.spinner_codigos_destino_credito)[i];
    }


    private String getCodeTipoContrato(String name) {
        int i = -1;
        for (String cc: getResources().getStringArray(R.array.spinner_tipo_contrato_empleado)) {
            i++;
            if (cc.equals(name))
                break;
        }
        return getResources().getStringArray(R.array.spinner_codigos_tipo_contrato_empleado)[i];
    }


    private String getCodeTipoEmpleado(String name) {
        int i = -1;
        for (String cc: getResources().getStringArray(R.array.spinner_tipo_empleado)) {
            i++;
            if (cc.equals(name))
                break;
        }
        return getResources().getStringArray(R.array.spinner_codigos_tipo_empleado)[i];
    }

}
