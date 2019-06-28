package com.example.alphamobilecolombia.mvp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.mvp.presenter.UploadFilesPresenter;
import com.example.alphamobilecolombia.utils.models.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArchivosActivity extends AppCompatActivity {
    private ImageView imagen1;
    Spinner spinner_tipo_documento;
    RealmStorage storage = new RealmStorage();
    View view;
    Context context;
    com.example.alphamobilecolombia.utils.models.File file;
    List<com.example.alphamobilecolombia.utils.models.File> listUpload = new ArrayList<com.example.alphamobilecolombia.utils.models.File>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Cargue de documentos");


        //Relacionamos con el XML
        imagen1=(ImageView)findViewById(R.id.imageView);

        spinner_tipo_documento = (Spinner) findViewById(R.id.spinner_tipo_documento);
        ArrayAdapter<CharSequence> adapter_destino_credito = ArrayAdapter.createFromResource(this,
                R.array.spinner_tipo_documento, android.R.layout.simple_spinner_item);
        adapter_destino_credito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tipo_documento.setAdapter(adapter_destino_credito);
    }


    public void tomarFoto(View v) {
        boolean existFile = existDocumentUpload();
        view = v;
        if (!existFile){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File foto = new File(getExternalFilesDir(null), getNameFile(v));
            intento1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
            startActivityForResult(intento1,1);
        }
        else{
            NotificacionExistente(v);
        }
    }

    public void recuperarFoto(View v) {
        Bitmap bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null)+"/"+getNameFile(v));
        imagen1.setImageBitmap(bitmap1);
    }

    public void ver(View v) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intento1=new Intent(this,CapturarActivity.class);
        startActivity(intento1);
    }

    public String getNameFile(View view){
        Person person = storage.getPerson(this);
        String nameFile = spinner_tipo_documento.getSelectedItem().toString();

        return person.getNumber()+nameFile+".jgp";
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        recuperarFoto(view);
        ConfirmacionImagen(view);
    }

    public static <T> T findByProperty(Collection<T> col, Predicate<T> filter) {
        return col.stream().filter(filter).findFirst().orElse(null);
    }

    public void finalizacion(View view) {
        boolean result = false;
        //List<com.example.alphamobilecolombia.utils.models.File> filesRequired = file.getFiles();


        result = compareLists(listUpload);
        if(result){
            UploadFilesPresenter uploadFilesPresenter = new UploadFilesPresenter();
            uploadFilesPresenter.PostGuardarDocumentos(listUpload,view.getContext());
            Intent intento1=new Intent(this,FinalActivity.class);
            startActivity(intento1);
        }
        else{
            NotificacionArchivospendientes(view);
        }
    }

    public class ExistFile{
        public boolean SolicitudCreditoCara1 = false;
        public boolean SolicitudCreditoCara2 = false;
        public boolean CedulaCara1 = false;
        public boolean CedulaCara2 = false;
        public boolean Desprendible1 = false;
        public boolean Desprendible2 = false;
        public boolean TratamientoDatosPersonales = false;
    }

    public boolean compareLists(List<com.example.alphamobilecolombia.utils.models.File> modelList) {
        boolean indicator = false;

        List<com.example.alphamobilecolombia.utils.models.File> filesRequired = new ArrayList<com.example.alphamobilecolombia.utils.models.File>();
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(66,"",true,"SolicitudCreditoCara1",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(67,"",true,"SolicitudCreditoCara2",false));
        /*filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(68,"",true,"CedulaCara1",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(69,"",true,"CedulaCara2",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(70,"",true,"Desprendible1",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(71,"",true,"Desprendible2",false));
        filesRequired.add(new com.example.alphamobilecolombia.utils.models.File(77,"",true,"TratamientoDatosPersonales",false));*/

        ExistFile existFile = new ExistFile();

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
        //if(existFile.CedulaCara1 && existFile.CedulaCara2 && existFile.SolicitudCreditoCara1 && existFile.SolicitudCreditoCara2 && existFile.Desprendible1 && existFile.Desprendible2 && existFile.TratamientoDatosPersonales)
        if(existFile.SolicitudCreditoCara1 && existFile.SolicitudCreditoCara2)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean existDocumentUpload(){
        final String nameFile = spinner_tipo_documento.getSelectedItem().toString();
        boolean result = false;

        for(com.example.alphamobilecolombia.utils.models.File file : listUpload) {
            if(file.getType().equals(nameFile)) {
                result = true;
            }
        }
        return result;
    }

    public void ConfirmacionImagen(final View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("¿ Deseas guardar esta imagen ?");
        builder1.setCancelable(true);
        UploadFilesPresenter uploadFilesPresenter = new UploadFilesPresenter();
        final String nameFile = spinner_tipo_documento.getSelectedItem().toString();
        builder1.setPositiveButton(
                "Sí",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        listUpload.add(new com.example.alphamobilecolombia.utils.models.File(0, getNameFile(view),false, nameFile,true));
                        String pathFile = getExternalFilesDir(null)+"/"+getNameFile(view);
                        uploadFilesPresenter.uploadFiles(pathFile,view.getContext());
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Bitmap bitmap1 = BitmapFactory.decodeFile(null);
                        imagen1.setImageBitmap(bitmap1);
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
