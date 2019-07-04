package com.example.alphamobilecolombia.mvp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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

        this.view = view;
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

        if(listUpload.size()>0) {
            for (com.example.alphamobilecolombia.utils.models.File file : modelList) {
                switch (file.getType()) {
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
        }
        if(existFile.CedulaCara1 && existFile.CedulaCara2 && existFile.SolicitudCreditoCara1 && existFile.SolicitudCreditoCara2 && existFile.Desprendible1 && existFile.Desprendible2 && existFile.TratamientoDatosPersonales)
        //if(existFile.SolicitudCreditoCara1 && existFile.SolicitudCreditoCara2)
        //if(existFile.SolicitudCreditoCara1)
        {
            return true;
        }
        else{
            validStatusUpload(existFile.SolicitudCreditoCara1, existFile.SolicitudCreditoCara2, existFile.CedulaCara1, existFile.CedulaCara2,  existFile.Desprendible1, existFile.Desprendible2, existFile.TratamientoDatosPersonales);
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
        //idElement = getIdElementView(v);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null)+"/"+getNameFile(v));
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);

        getViewImage(v,rotatedBitmap);
        hideLoading();
    }

    public String getNameFile(View view){
        Person person = storage.getPerson(this);
        String nameFile = idElement;

        return person.getNumber()+nameFile+".jgp";
    }

    public void changeStatusUpload(boolean status){

        switch (idElement) {
            case "SolicitudCreditoCara1":
                LinearLayout rl = (LinearLayout)findViewById(R.id.lyt_cargue1);
                if(status){
                    rl.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
            case "SolicitudCreditoCara2":
                LinearLayout rl2 = (LinearLayout)findViewById(R.id.lyt_cargue2);
                if(status){
                    rl2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
            case "CedulaCara1":
                LinearLayout rl3 = (LinearLayout)findViewById(R.id.lyt_cargue3);
                if(status){
                    rl3.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl3.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
            case "CedulaCara2":
                LinearLayout rl4 = (LinearLayout)findViewById(R.id.lyt_cargue4);
                if(status){
                    rl4.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl4.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
            case "Desprendible1":
                LinearLayout rl5 = (LinearLayout)findViewById(R.id.lyt_cargue5);
                if(status){
                    rl5.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl5.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
            case "Desprendible2":
                LinearLayout rl6 = (LinearLayout)findViewById(R.id.lyt_cargue6);
                if(status){
                    rl6.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl6.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
            case "Desprendible3":
                LinearLayout rl7 = (LinearLayout)findViewById(R.id.lyt_cargue7);
                if(status){
                    rl7.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl7.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
            case "Desprendible4":
                LinearLayout rl8 = (LinearLayout)findViewById(R.id.lyt_cargue8);
                if(status){
                    rl8.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl8.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;

            case "TratamientoDatosPersonales":
                LinearLayout rl9 = (LinearLayout)findViewById(R.id.lyt_cargue9);
                if(status){
                    rl9.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl9.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
        }
    }


    public void validStatusUpload(boolean status1, boolean status2, boolean status3, boolean status4, boolean status5, boolean status6, boolean status7){


                LinearLayout rl = (LinearLayout)findViewById(R.id.lyt_cargue1);
                if(status1){
                    rl.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }

                LinearLayout rl2 = (LinearLayout)findViewById(R.id.lyt_cargue2);
                if(status2){
                    rl2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }

                LinearLayout rl3 = (LinearLayout)findViewById(R.id.lyt_cargue3);
                if(status3){
                    rl3.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl3.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }

                LinearLayout rl4 = (LinearLayout)findViewById(R.id.lyt_cargue4);
                if(status4){
                    rl4.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl4.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }

                LinearLayout rl5 = (LinearLayout)findViewById(R.id.lyt_cargue5);
                if(status5){
                    rl5.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl5.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }

                LinearLayout rl6 = (LinearLayout)findViewById(R.id.lyt_cargue6);
                if(status6){
                    rl6.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl6.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }

                LinearLayout rl9 = (LinearLayout)findViewById(R.id.lyt_cargue9);
                if(status7){
                    rl9.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl9.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }

    }

    public void getViewImage(View v, Bitmap bitmap){
        switch (v.getId()) {
            case R.id.btnView1:
                ImageView imagen1 = findViewById(R.id.imageView1);
                if(imagen1.getVisibility() == View.GONE){
                    imagen1.setImageBitmap(bitmap);
                    imagen1.setVisibility(View.VISIBLE);
                }
                else if(imagen1.getVisibility() == View.VISIBLE){
                    imagen1.setImageBitmap(null);
                    imagen1.setVisibility(View.GONE);
                }
                break;
            case R.id.btnView2:
                ImageView imagen2 = findViewById(R.id.imageView2);
                if(imagen2.getVisibility() == View.GONE){
                    imagen2.setImageBitmap(bitmap);
                    imagen2.setVisibility(View.VISIBLE);
                }
                else if(imagen2.getVisibility() == View.VISIBLE){
                    imagen2.setImageBitmap(null);
                    imagen2.setVisibility(View.GONE);
                }
                break;
            case R.id.btnView3:
                ImageView imagen3 = findViewById(R.id.imageView3);
                if(imagen3.getVisibility() == View.GONE){
                    imagen3.setImageBitmap(bitmap);
                    imagen3.setVisibility(View.VISIBLE);
                }
                else if(imagen3.getVisibility() == View.VISIBLE){
                    imagen3.setImageBitmap(null);
                    imagen3.setVisibility(View.GONE);
                }
                break;
            case R.id.btnView4:
                ImageView imagen4 = findViewById(R.id.imageView4);
                if(imagen4.getVisibility() == View.GONE){
                    imagen4.setImageBitmap(bitmap);
                    imagen4.setVisibility(View.VISIBLE);
                }
                else if(imagen4.getVisibility() == View.VISIBLE){
                    imagen4.setImageBitmap(null);
                    imagen4.setVisibility(View.GONE);
                }
                break;
            case R.id.btnView5:
                ImageView imagen5 = findViewById(R.id.imageView5);
                if(imagen5.getVisibility() == View.GONE){
                    imagen5.setImageBitmap(bitmap);
                    imagen5.setVisibility(View.VISIBLE);
                }
                else if(imagen5.getVisibility() == View.VISIBLE){
                    imagen5.setImageBitmap(null);
                    imagen5.setVisibility(View.GONE);
                }
                break;
            case R.id.btnView6:
                ImageView imagen6 = findViewById(R.id.imageView6);
                if(imagen6.getVisibility() == View.GONE){
                    imagen6.setImageBitmap(bitmap);
                    imagen6.setVisibility(View.VISIBLE);
                }
                else if(imagen6.getVisibility() == View.VISIBLE){
                    imagen6.setImageBitmap(null);
                    imagen6.setVisibility(View.GONE);
                }
                break;
            case R.id.btnView7:
                ImageView imagen7 = findViewById(R.id.imageView7);
                if(imagen7.getVisibility() == View.GONE){
                    imagen7.setImageBitmap(bitmap);
                    imagen7.setVisibility(View.VISIBLE);
                }
                else if(imagen7.getVisibility() == View.VISIBLE){
                    imagen7.setImageBitmap(null);
                    imagen7.setVisibility(View.GONE);
                }
                break;
            case R.id.btnView8:
                ImageView imagen8 = findViewById(R.id.imageView8);
                if(imagen8.getVisibility() == View.GONE){
                    imagen8.setImageBitmap(bitmap);
                    imagen8.setVisibility(View.VISIBLE);
                }
                else if(imagen8.getVisibility() == View.VISIBLE){
                    imagen8.setImageBitmap(null);
                    imagen8.setVisibility(View.GONE);
                }
                break;

            case R.id.btnView9:
                ImageView imagen9 = findViewById(R.id.imageView9);
                if(imagen9.getVisibility() == View.GONE){
                    imagen9.setImageBitmap(bitmap);
                    imagen9.setVisibility(View.VISIBLE);
                }
                else if(imagen9.getVisibility() == View.VISIBLE){
                    imagen9.setImageBitmap(null);
                    imagen9.setVisibility(View.GONE);
                }
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
            case R.id.btnView9:
                id = "TratamientoDatosPersonales";
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
            case R.id.btnUpload9:
                id = "TratamientoDatosPersonales";
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
                        changeStatusUpload(true);
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
                        changeStatusUpload(false);
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
