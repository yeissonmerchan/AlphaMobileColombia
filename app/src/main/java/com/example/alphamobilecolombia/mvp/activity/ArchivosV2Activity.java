package com.example.alphamobilecolombia.mvp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.mvp.presenter.UploadFilesPresenter;
import com.example.alphamobilecolombia.utils.models.File;
import com.example.alphamobilecolombia.utils.models.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ArchivosV2Activity extends AppCompatActivity {
    List<com.example.alphamobilecolombia.utils.models.File> listUpload = new ArrayList<com.example.alphamobilecolombia.utils.models.File>();
    RealmStorage storage = new RealmStorage();
    View view;
    ProgressDialog mDialog;
    String idSujeroCredito;
    String idElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos_v2);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Nueva solicitud");

        idSujeroCredito = getIntent().getStringExtra("IdSujetoCredito");

    }


    public void generateControls(){
        LinearLayout contenedor = new LinearLayout(getApplicationContext());
        contenedor.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        contenedor.setOrientation(LinearLayout.VERTICAL);
        contenedor.setGravity(Gravity.CENTER);
        //Crea ImageView y TextView
        ImageView miImageView = new ImageView(getApplicationContext());
        TextView miTextView = new TextView(getApplicationContext());
        //Agrega propiedades al TextView.
        miTextView.setText("mi TextView");
        miTextView.setBackgroundColor(Color.BLUE);
        //Agrega imagen al ImageView.
        miImageView.setImageResource(R.mipmap.ic_launcher);

        //Agrega vistas al contenedor.
        contenedor.addView(miTextView);
        contenedor.addView(miImageView);

        //FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(400, 1500, Gravity.CENTER);
        //Agrega contenedor con botones.
        //addContentView(contenedor, params);

        LinearLayout lyt_controls = findViewById(R.id.lyt_controls);
        lyt_controls.addView(contenedor);

    }

    public void finalizacion(View view) {
        boolean result = false;
        //List<com.example.alphamobilecolombia.utils.models.File> filesRequired = file.getFiles();


        result = compareLists(listUpload);
        if(result){
            //UploadFilesPresenter uploadFilesPresenter = new UploadFilesPresenter();
            //uploadFilesPresenter.PostGuardarDocumentos(listUpload,view.getContext(),idSujeroCredito);
            Intent intento1=new Intent(this,FinalActivity.class);
            startActivity(intento1);
        }
        else{
            NotificacionArchivospendientes(view);
        }
    }


    public boolean compareLists(List<File> modelList) {
        boolean indicator = false;

        List<com.example.alphamobilecolombia.utils.models.File> filesRequired = new ArrayList<File>();
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(66,"",true,"SolicitudCreditoCara1",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(67,"",true,"SolicitudCreditoCara2",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(68,"",true,"CedulaCara1",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(69,"",true,"CedulaCara2",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(70,"",true,"Desprendible1",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(71,"",true,"Desprendible2",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(77,"",true,"TratamientoDatosPersonales",false));

        ArchivosActivity.ExistFile existFile = new ArchivosActivity.ExistFile();

        for(com.example.alphamobilecolombia.utils.models.File file : modelList) {
            switch (file.getType()){
                case "SolicitudCreditoCara1":
                    existFile.SolicitudCreditoCara1 = true;
                    break;
                case "SolicitudCreditoCara2":
                    existFile.SolicitudCreditoCara2 = true;
                    break;
                case "CedulaCara1":
                    existFile.CedulaCara1 = true;
                    break;
                case "CedulaCara2":
                    existFile.CedulaCara2 = true;
                    break;
                case "Desprendible1":
                    existFile.Desprendible1 = true;
                    break;
                case "Desprendible2":
                    existFile.Desprendible2 = true;
                    break;
                case "TratamientoDatosPersonales":
                    existFile.TratamientoDatosPersonales = true;
                    break;
            }
        }
        if(existFile.CedulaCara1 && existFile.CedulaCara2 && existFile.SolicitudCreditoCara1 && existFile.SolicitudCreditoCara2 && existFile.Desprendible1 && existFile.Desprendible2 && existFile.TratamientoDatosPersonales)
        //if(existFile.SolicitudCreditoCara1 && existFile.SolicitudCreditoCara2)
        //if(existFile.SolicitudCreditoCara1)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean existDocumentUpload(){
        final String nameFile = idElement;
        boolean result = false;

        for(com.example.alphamobilecolombia.utils.models.File file : listUpload) {
            if(file.getType().equals(nameFile)) {
                result = true;
            }
        }
        return result;
    }

    public void tomarFoto(View v) {
        idElement = getIdElementUpload(v);
        boolean existFile = existDocumentUpload();
        view = v;
        if (!existFile){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            java.io.File foto = new java.io.File(getExternalFilesDir(null), getNameFile(v));
            intento1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
            startActivityForResult(intento1,1);
        }
        else{
            NotificacionExistente(v);
        }
    }

    public void showLoading(View v){
        mDialog = new ProgressDialog(v.getContext());
        mDialog.setMessage("Espere...");
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void hideLoading(){
        mDialog.dismiss();
    }

    public void recuperarFoto(View v) {
        showLoading(v);
        idElement = getIdElementView(v);
        Bitmap bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null)+"/"+getNameFile(v));
        getViewImage(v,bitmap1);
        hideLoading();
    }

    public String getNameFile(View view){
        Person person = storage.getPerson(this);
        String nameFile = idElement;

        return person.getNumber()+nameFile+".jgp";
    }

    public void getViewImage(View v, Bitmap bitmap){
        switch (v.getId()) {
            case R.id.btnView1:
                ImageView imagen1 = findViewById(R.id.imageView1);
                //if(imagen1.getVisibility())
                imagen1.setImageBitmap(bitmap);
                imagen1.setVisibility(View.VISIBLE);
                break;
            case R.id.btnView2:
                ImageView imagen2 = findViewById(R.id.imageView2);
                imagen2.setImageBitmap(bitmap);
                imagen2.setVisibility(View.VISIBLE);
                break;
            case R.id.btnView3:
                ImageView imagen3 = findViewById(R.id.imageView3);
                imagen3.setImageBitmap(bitmap);
                imagen3.setVisibility(View.VISIBLE);
                break;
            case R.id.btnView4:
                ImageView imagen4 = findViewById(R.id.imageView4);
                imagen4.setImageBitmap(bitmap);
                imagen4.setVisibility(View.VISIBLE);
                break;
            case R.id.btnView5:
                ImageView imagen5 = findViewById(R.id.imageView5);
                imagen5.setImageBitmap(bitmap);
                imagen5.setVisibility(View.VISIBLE);
                break;
            case R.id.btnView6:
                ImageView imagen6 = findViewById(R.id.imageView6);
                imagen6.setImageBitmap(bitmap);
                imagen6.setVisibility(View.VISIBLE);
                break;
            case R.id.btnView7:
                ImageView imagen7 = findViewById(R.id.imageView7);
                imagen7.setImageBitmap(bitmap);
                imagen7.setVisibility(View.VISIBLE);
                break;
            case R.id.btnView8:
                ImageView imagen8 = findViewById(R.id.imageView8);
                imagen8.setImageBitmap(bitmap);
                imagen8.setVisibility(View.VISIBLE);
                break;
        }

    }

    public String getIdElementView(View v){
        String id = "";
        switch (v.getId()) {
            case R.id.btnView1:
                id = "SolicitudCreditoCara1";
                break;
            case R.id.btnView2:
                id = "SolicitudCreditoCara2";
                break;
            case R.id.btnView3:
                id = "CedulaCara1";
                break;
            case R.id.btnView4:
                id = "CedulaCara2";
                break;
            case R.id.btnView5:
                id = "Desprendible1";
                break;
            case R.id.btnView6:
                id = "Desprendible2";
                break;
            case R.id.btnView7:
                id = "Desprendible3";
                break;
            case R.id.btnView8:
                id = "Desprendible4";
                break;
        }
        return id;

    }

    public String getIdElementUpload(View v){
        String id = "";
        switch (v.getId()) {
            case R.id.btnUpload1:
                id = "SolicitudCreditoCara1";
                break;
            case R.id.btnUpload2:
                id = "SolicitudCreditoCara2";
                break;
            case R.id.btnUpload3:
                id = "CedulaCara1";
                break;
            case R.id.btnUpload4:
                id = "CedulaCara2";
                break;
            case R.id.btnUpload5:
                id = "Desprendible1";
                break;
            case R.id.btnUpload6:
                id = "Desprendible2";
                break;
            case R.id.btnUpload7:
                id = "Desprendible3";
                break;
            case R.id.btnUpload8:
                id = "Desprendible4";
                break;
        }
        return id;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        recuperarFoto(view);
        ConfirmacionImagen(view);
    }

    public void ConfirmacionImagen(final View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("¿ Deseas guardar esta imagen ?");
        builder1.setCancelable(true);
        final UploadFilesPresenter uploadFilesPresenter = new UploadFilesPresenter();
        final String nameFile = idElement;
        builder1.setPositiveButton(
                "Sí",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        com.example.alphamobilecolombia.utils.models.File fileUpload = new com.example.alphamobilecolombia.utils.models.File(0, getNameFile(view),false, nameFile,true);
                        listUpload.add(fileUpload);
                        String pathFile = getExternalFilesDir(null)+"/"+getNameFile(view);
                        //uploadFilesPresenter.uploadFiles(pathFile,view.getContext());
                        uploadFilesPresenter.PostGuardarDocumentos(fileUpload,view.getContext(),idSujeroCredito);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //Bitmap bitmap1 = BitmapFactory.decodeFile(null);
                        //imagen1.setImageBitmap(bitmap1);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void NotificacionExistente(final View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("Este archivo ya esta cargado, por favor selecione otro.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void NotificacionArchivospendientes(final View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("Aun faltan archivos por cargar..");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
