package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.utils.models.Persona;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;

public class TerminosActivity extends AppCompatActivity {
    private Persona persona = new Persona();
    private String IdTipoEmpleado;
    private String IdTipoContrato;
    private String IdDestinoCredito;

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

    }


    public void onClickBtnAgree(View view) {
        Intent intent = new Intent (view.getContext(), FinalActivity.class);
        intent.putExtra("PERSONA_Documento", persona.getCedula());
        intent.putExtra("PERSONA_PNombre", persona.getNombre());
        intent.putExtra("PERSONA_SNombre", "");
        intent.putExtra("PERSONA_PApellido", persona.getApellido1());
        intent.putExtra("PERSONA_SApellido", persona.getApellido2());
        intent.putExtra("PERSONA_FechaNac", persona.getFechaNacimiento());
        intent.putExtra("PERSONA_Genero", persona.getGenero());
        intent.putExtra("PERSONA_Celular", persona.getCelular());
        intent.putExtra("IdTipoEmpleado", IdTipoEmpleado);
        intent.putExtra("IdTipoContrato", IdTipoContrato);
        intent.putExtra("IdDestinoCredito", IdDestinoCredito);
        startActivityForResult(intent, 0);

    }
}
