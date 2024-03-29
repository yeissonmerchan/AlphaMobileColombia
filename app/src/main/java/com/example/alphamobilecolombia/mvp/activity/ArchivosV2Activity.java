package com.example.alphamobilecolombia.mvp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.ModelReintentos;
import com.example.alphamobilecolombia.mvp.presenter.FinalPresenter;
import com.example.alphamobilecolombia.mvp.presenter.UploadFilesPresenter;
import com.example.alphamobilecolombia.utils.configuration.ApplicationData;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.models.File;
import com.example.alphamobilecolombia.utils.models.Person;
import com.example.alphamobilecolombia.utils.models.Persona;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;
import io.reactivex.schedulers.Schedulers;


public class ArchivosV2Activity extends AppCompatActivity {
    List<com.example.alphamobilecolombia.utils.models.File> listUpload = new ArrayList<com.example.alphamobilecolombia.utils.models.File>();
    RealmStorage storage = new RealmStorage();
    View view;
    ProgressDialog mDialog;
    Boolean isCreateUserAndSubject = false;
    String idSujeroCredito;
    String idElement;
    Dialog myDialog;
    com.example.alphamobilecolombia.utils.models.File fileUpload;
    private Persona persona = new Persona();
    private String IdTipoEmpleado;
    private String IdTipoContrato;
    private String IdDestinoCredito;
    private String IdPagaduria;
    private String pathNewFile1;
    final Context context = this;
    CountDownLatch executionCompleted;
    List<ModelReintentos> lisReintentos = new ArrayList<>();
    private static final int DIALOG_REALLY_EXIT_ID = 0;

    final UploadFilesPresenter uploadFilesPresenter = new UploadFilesPresenter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        myDialog = new Dialog(this);

        idSujeroCredito = getIntent().getStringExtra("IdSujetoCredito");
        isCreateUserAndSubject = false;

        cleanInitImages();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void finalizacion(View view) throws InterruptedException, ExecutionException {
        try {

            this.view = view;
            boolean result = compareLists(listUpload);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
            builder1.setMessage("¿ Deseas guardar este sujeto ?");
            builder1.setCancelable(false);
            myDialog.setContentView(R.layout.loading_page);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
            final String nameFile = idElement;
            builder1.setPositiveButton(
                    "Sí",
                    new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            if(result){
                                if(!isCreateUserAndSubject){
                                    SavePersonAndSubject();
                                }
                                saveFiles(view);
                            }
                            else{
                                NotificacionArchivospendientes(view);
                                myDialog.dismiss();
                            }
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            myDialog.dismiss();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.setCanceledOnTouchOutside(false);
            alert11.show();

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void saveFiles(View view){
        List<HttpResponse> listResponses = new ArrayList<>();
        boolean isValidSendFiles = false;

        if(lisReintentos.size()>0){
            executionCompleted = new CountDownLatch(lisReintentos.size());
        }
        else{
            executionCompleted = new CountDownLatch(listUpload.size());
        }

        for(com.example.alphamobilecolombia.utils.models.File file : listUpload)
        {
            if(lisReintentos.size()>0){
                boolean isNewSend = false;
                for (ModelReintentos modelReintentos : lisReintentos){
                    if (modelReintentos.getNameFile().equals(file.getName())){
                        isNewSend = true;
                    }
                }

                if(isNewSend) {
                    try {
                        new Thread()
                        {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run ()
                            {
                                System.out.println("I am executed by :" + Thread.currentThread().getName());
                                HttpResponse httpResponse = uploadFilesPresenter.PostGuardarDocumentos(file, view.getContext(), idSujeroCredito, pathNewFile1);
                                // One thread has completed its job
                                executionCompleted.countDown();
                                if (httpResponse != null) {
                                    listResponses.add(httpResponse);
                                }
                            }
                        }.start();
                    } catch (Exception ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                        LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Hilo almacenamiento "+file.getName(),ex,this);
                    }
                }
            }
            else{
                try
                {
                    new Thread()
                    {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run ()
                        {
                            System.out.println("I am executed by 2:" + Thread.currentThread().getName());
                            HttpResponse httpResponse = uploadFilesPresenter.PostGuardarDocumentos(file,view.getContext(),idSujeroCredito,pathNewFile1);
                            // One thread has completed its job
                            executionCompleted.countDown();
                            if (httpResponse != null) {
                                listResponses.add(httpResponse);
                            }
                        }
                    }.start();

                }
                catch (Exception ex)
                {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                    LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Hilo almacenamiento "+file.getName(),ex,this);
                }
            }
        }

        try
        {
            executionCompleted.await();
            // Wait till the count down latch opens.In the given case till five
            // times countDown method is invoked
            System.out.println("All over");
            System.out.println("Cantidad de respuestas "+listResponses.size());
            //Toast.makeText(getApplicationContext(), listResponses.size(), Toast.LENGTH_LONG).show();
            //if (listResponses.size() == listUpload.size()){
            if (listResponses.size() > 0){
                lisReintentos = new ArrayList<>();
                for(HttpResponse httpResponse : listResponses){
                    System.out.println("Proceso de envio de archivo " + httpResponse);
                    if(httpResponse != null) {
                        System.out.println("Proceso de envio de archivo, Codigo de respuesta" + httpResponse.getCode());
                        System.out.println("Proceso de envio de archivo, Mensaje de respuesta" + httpResponse.getMessage());
                        System.out.println("Proceso de envio de archivo, Data de respuesta" + httpResponse.getData());
                        //System.out.println("Proceso de envio de archivo, Data de envio" + httpResponse.getSendData());

                        if(httpResponse.getCode() != null) {
                            if (!httpResponse.getCode().contains("200")) {
                                isValidSendFiles = true;
                                System.out.println("Paso error 400:  " + httpResponse.getCode());
                                ModelReintentos modelReintentos = new ModelReintentos();
                                modelReintentos.setModelResponse(httpResponse);
                                modelReintentos.setNameFile(httpResponse.getNameFile());
                                lisReintentos.add(modelReintentos);
                            }
                            else {
                                try {
                                    JSONObject objeto2 = (JSONObject) httpResponse.getData();
                                    //JSONObject objetoNew =  new JSONObject(objeto2.getString("result"));
                                    JSONArray objeto3 = objeto2.getJSONArray("data");
                                    JSONObject objeto4 = new JSONObject(objeto3.getString(0));
                                    String codigoRespuesta3 = objeto4.getString("codigoRespuesta");
                                    String nombreAnterior = objeto4.getString("nombreAnterior");
                                    System.out.println("nombreAnterior:  " + nombreAnterior);
                                    System.out.println("codigoRespuesta2:  " + codigoRespuesta3);
                                    if (!codigoRespuesta3.contains("200")) {
                                        System.out.println("Paso error 400:  " + nombreAnterior);
                                        ModelReintentos modelReintentos = new ModelReintentos();
                                        modelReintentos.setModelResponse(httpResponse);
                                        modelReintentos.setNameFile(nombreAnterior);
                                        lisReintentos.add(modelReintentos);
                                        isValidSendFiles = true;
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    isValidSendFiles = true;
                                    LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Procesando respuesta "+httpResponse.getNameFile(),ex,this);
                                }
                            }
                        }
                    }
                }


            }
            else{
                //Toast.makeText(getApplicationContext(), listResponses.size(), Toast.LENGTH_LONG).show();
                ValidacionCargueDocumentos(view);
            }
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Procesando peticiones ",ex,this);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Procesando peticiones ",ex,this);
        }

        /*if (listResponses.size() != listUpload.size()) {
            if (listResponses.size() != listUpload.size() - 1) {
                ValidacionCargueDocumentos(view);
            }
        }*/

        if(isValidSendFiles){
            ValidacionCargueDocumentos(view);
        }
        else{
            deleteFiles();
            Intent intento1=new Intent(view.getContext(),FinalActivity.class);
            startActivity(intento1);
        }
    }

    public void deleteFiles() {
        /*for (com.example.alphamobilecolombia.utils.models.File file : listUpload){
            try {
                String nameFile = file.getName();
                //String pathFileLocal = context.getExternalFilesDir(null) + "/" + nameFile;
                String pathFileLocalCompress = pathNewFile1 + nameFile;
                //java.io.File fileLocal = new java.io.File(pathFileLocal);
                java.io.File fileLocalCompress = new java.io.File(pathFileLocalCompress);
                //fileLocal.delete();
                fileLocalCompress.delete();
            }
            catch (Exception ex){
                LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Eliminando archivo "+file.getName(),ex,this);
                ex.printStackTrace();
            }
        }*/
        ApplicationData applicationData = new ApplicationData();
        applicationData.ClearDirectoryTemp(getApplicationContext());
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
        //boolean existFile = existDocumentUpload();
        view = v;
        //if (!existFile){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            java.io.File foto = new java.io.File(getExternalFilesDir(null), getNameFile(v));
            intento1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
            startActivityForResult(intento1,1);
        //}
        //else{
        //    NotificacionExistente(v);
        //}
    }

    public void recuperarFotoCargada(View v) {
        try {
            idElement = getIdElementView(v);

            boolean isExist = false;
            for(com.example.alphamobilecolombia.utils.models.File file : listUpload) {
                if(file.getType().equals(idElement)) {
                    isExist = true;
                }
            }

            if(isExist) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                //Bitmap bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null) + "/" + getNameFile(v));
                Bitmap bitmap1 = BitmapFactory.decodeFile(pathNewFile1 + "/" + getNameFile(v));
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);

                getViewImage(v, rotatedBitmap, true);
            }
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"recuperando archivo "+getNameFile(v),ex,this);
            ex.printStackTrace();
        }
    }

    public void recuperarImagen(View v, boolean isfetch) {
        try {
            //showLoading(v);
            //idElement = getIdElementView(v);
            String pathFileLocal = getExternalFilesDir(null)+"/"+getNameFile(v);

            java.io.File fileLocal = new java.io.File(pathFileLocal);
            int file_size_original = Integer.parseInt(String.valueOf(fileLocal.length()/1024));
            System.out.println("file_size_original " + file_size_original);
            java.io.File file = new Compressor(context)
                    .setQuality(60)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .compressToFile(fileLocal);

            fileLocal.delete();
            file.getParentFile().mkdirs();
            file.createNewFile();
            int file_size_compress = Integer.parseInt(String.valueOf(file.length()/1024));
            System.out.println("file_size_compress " + file_size_compress);
            pathNewFile1 = file.getParent();
            String pathFileLocalCompress = pathNewFile1+"/"+getNameFile(v);

            java.io.File f = new java.io.File(getExternalFilesDir(null), getNameFile(v));
            f.createNewFile();
            Bitmap bitmap = BitmapFactory.decodeFile(pathFileLocalCompress);
            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            int file_size_final = Integer.parseInt(String.valueOf(file.length()/1024));
            System.out.println("file_size_final " + file_size_final);
            file.delete();

            pathNewFile1 = getExternalFilesDir(null).getAbsolutePath();

            Matrix matrix = new Matrix();
            matrix.postRotate(90);

            Bitmap bitmap1 = BitmapFactory.decodeFile(pathNewFile1+"/"+getNameFile(v));
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);

            getViewImage(v,rotatedBitmap,isfetch);
        }
        catch (Exception ex){
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"recuperando archivo "+getNameFile(v),ex,this);
        }
    }



    public String getNameFile(View view){
        String documentNumber;
        String nameFile = idElement;
        try {
            Person person = storage.getPerson(view.getContext());
            documentNumber = person.getNumber();
        }
        catch (Exception ex){
            documentNumber = getIntent().getStringExtra("PERSONA_Documento");
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"nombre archivo "+documentNumber,ex,this);
        }

        return documentNumber+nameFile+".jpg";
    }

    public void changeStatusUpload(boolean status){

        // Se obtiene el bitmap1 para saber si ya hay una imagen cargada, si no encuentra ninguna imagen la variable queda nula
        Bitmap bitmap1 = BitmapFactory.decodeFile(pathNewFile1+"/"+getNameFile(view));
        if (bitmap1 != null){
            status = true;
            bitmap1 = null;
        }

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
                    //rl7.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
            case "Desprendible4":
                LinearLayout rl8 = (LinearLayout)findViewById(R.id.lyt_cargue8);
                if(status){
                    rl8.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    //rl8.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
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

    public void cleanInitImages(){
        ImageView imagen1 = findViewById(R.id.imageView1);
        imagen1.setImageBitmap(null);

        ImageView imagen2 = findViewById(R.id.imageView2);
        imagen2.setImageBitmap(null);

        ImageView imagen3 = findViewById(R.id.imageView3);
        imagen3.setImageBitmap(null);

        ImageView imagen4 = findViewById(R.id.imageView4);
        imagen4.setImageBitmap(null);

        ImageView imagen5 = findViewById(R.id.imageView5);
        imagen5.setImageBitmap(null);

        ImageView imagen6 = findViewById(R.id.imageView6);
        imagen6.setImageBitmap(null);

        ImageView imagen7 = findViewById(R.id.imageView7);
        imagen7.setImageBitmap(null);

        ImageView imagen8 = findViewById(R.id.imageView8);
        imagen8.setImageBitmap(null);

        ImageView imagen9 = findViewById(R.id.imageView9);
        imagen9.setImageBitmap(null);
    }

    public void getViewImage(View v, Bitmap bitmap, boolean isFetch){
        int idButton = v.getId();
        if (!isFetch)
        {
            idButton = getIdViewImage(idElement);
        }

        switch (idButton) {
            case R.id.btnView1:
                ImageView imagen1 = findViewById(R.id.imageView1);
                if(imagen1.getVisibility() == View.GONE && isFetch){
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
                if(imagen2.getVisibility() == View.GONE && isFetch){
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
                if(imagen3.getVisibility() == View.GONE && isFetch){
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
                if(imagen4.getVisibility() == View.GONE && isFetch){
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
                if(imagen5.getVisibility() == View.GONE && isFetch){
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
                if(imagen6.getVisibility() == View.GONE && isFetch){
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
                if(imagen7.getVisibility() == View.GONE && isFetch){
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
                if(imagen8.getVisibility() == View.GONE && isFetch){
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
                if(imagen9.getVisibility() == View.GONE && isFetch){
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

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    public void notificationEndProcess(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getBaseContext());
        builder1.setMessage("¿ Desea terminar el proceso ?");
        builder1.setCancelable(false);

        final String nameFile = idElement;
        builder1.setPositiveButton(
                "Sí",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent a = new Intent(getBaseContext(),ModuloActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final Dialog dialog;
        switch(id) {
            case DIALOG_REALLY_EXIT_ID:
                dialog = new AlertDialog.Builder(this).setMessage(
                        "¿ Desea terminar el proceso ?")
                        .setCancelable(false)
                        .setPositiveButton("Sí",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent a = new Intent(getBaseContext(),ModuloActivity.class);
                                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(a);
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }).create();
                break;
            default:
                dialog = null;
        }
        return dialog;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog(DIALOG_REALLY_EXIT_ID);
        }
        return true;
    }

    public int getIdViewImage(String typeFile){
        int id = 0;
        switch (typeFile) {
            case "SolicitudCreditoCara1":
                id = R.id.btnView1;
                break;
            case "SolicitudCreditoCara2":
                id = R.id.btnView2;
                break;
            case "CedulaCara1":
                id = R.id.btnView3;
                break;
            case "CedulaCara2":
                id = R.id.btnView4;
                break;
            case "Desprendible1":
                id = R.id.btnView5;
                break;
            case "Desprendible2":
                id = R.id.btnView6;
                break;
            case "Desprendible3":
                id = R.id.btnView7;
                break;
            case "Desprendible4":
                id = R.id.btnView8;
                break;
            case "TratamientoDatosPersonales":
                id = R.id.btnView9;
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
        //recuperarFotoCargada(view);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            ConfirmacionImagen(view);
        }
    }

    public void ConfirmacionImagen(final View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("¿ Deseas guardar esta imagen ?");
        builder1.setCancelable(false);

        final String nameFile = idElement;
        builder1.setPositiveButton(
                "Sí",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        boolean isExist = false;

                        for(com.example.alphamobilecolombia.utils.models.File file : listUpload) {
                            if(file.getType().equals(nameFile)) {
                                isExist = true;
                            }
                        }

                        if (!isExist){
                            fileUpload = new com.example.alphamobilecolombia.utils.models.File(0, getNameFile(view),false, nameFile,true);
                            listUpload.add(fileUpload);
                        }

                        //LoadinAsyncTask loadinAsyncTask = new LoadinAsyncTask();
                        //loadinAsyncTask.execute();
                        //uploadFilesPresenter.uploadFiles(pathFile,view.getContext());
                        recuperarImagen(view,false);
                        changeStatusUpload(true);
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
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
    }


    public void ValidacionCargueDocumentos(final View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("Hubo un problema al cargar los archivos. Por favor inténtalo de nuevo");
        builder1.setCancelable(false);
        this.view = view;
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        saveFiles(view);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
        myDialog.setContentView(R.layout.loading_page);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    public void NotificacionExistente(final View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("Este archivo ya esta cargado, por favor selecione otro.");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
    }

    public void NotificacionArchivospendientes(final View view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("Aun faltan archivos por cargar..");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
    }


    public void SavePersonAndSubject() {

        Persona persona = new Persona();
        String genero = getIntent().getStringExtra("PERSONA_Genero");
        int generoId = 0;
        switch (genero){
            case "M":
                generoId = 1;
                break;
            case "F":
                generoId = 2;
                break;
        }

        persona.setCedula(getIntent().getStringExtra("PERSONA_Documento"));
        persona.setNombre(getIntent().getStringExtra("PERSONA_PNombre"));
        persona.setNombre2(getIntent().getStringExtra("PERSONA_SNombre"));
        persona.setApellido1(getIntent().getStringExtra("PERSONA_PApellido"));
        persona.setApellido2(getIntent().getStringExtra("PERSONA_SApellido"));
        persona.setFechaNacimiento(getIntent().getStringExtra("PERSONA_FechaNac"));
        persona.setGenero(String.valueOf(generoId));
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
                    int IdTypeCont = Integer.parseInt(getCodeTipoContrato(IdTipoContrato));
                    int IdTypeDest = Integer.parseInt(getCodeDestinoCredito(IdDestinoCredito));
                    int idPagaduria = Integer.parseInt(IdPagaduria);

                    HttpResponse modelSujetoCredito = presenter.PostInsertSujetoCredito(persona, codigoTransaccion, IdTypeEmployee, IdTypeCont, IdTypeDest,user, idPagaduria,this.context);
                    if (modelSujetoCredito!=null){

                        if(modelSujetoCredito.getCode().contains("200")) {

                            JSONObject objeto2 = (JSONObject) modelSujetoCredito.getData();
                            setData(sharedPref, objeto2);
                            String idSujetoCredito = objeto2.getString("codigoTransaccion");
                            idSujeroCredito = idSujetoCredito;
                            isCreateUserAndSubject = true;
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
                LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Guardar persona "+persona.getCedula(),ex,this);
            }
        }
        else{
            NotificacionErrorDatos(this.context);
        }
    }


    public void NotificacionErrorDatos(final Context view){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view);
        builder1.setMessage("Ha ocurrido un error inesperado. Intentalo mas tarde.");
        builder1.setCancelable(false);
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
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
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
        for (String cc: getResources().getStringArray(R.array.spinner_tipo_contrato_empleado_Todos)) {
            i++;
            if (cc.equals(name))
                break;
        }
        return getResources().getStringArray(R.array.spinner_codigos_tipo_contrato_empleado_Todos)[i];
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


    private class LoadinAsyncTask extends AsyncTask<Void,Integer,Boolean> {
        public LoadinAsyncTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setMax(100);
            //progressBar.setProgress(0);
            myDialog.setContentView(R.layout.loading_page);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
            // Hilo principal
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {
            //uploadFilesPresenter.PostGuardarDocumentos(fileUpload,view.getContext(),idSujeroCredito);
            try
            {
                // Wait till the count down latch opens.In the given case till five
                // times countDown method is invoked
                executionCompleted.await();
                System.out.println("All over");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            return true;
            // Ejecutar en segundo plano
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //progressBar.setProgress(values[0].intValue());
            // Hilo principal
            // Se conecta con hilo principal
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            // super.onPostExecute(aVoid);
            if (aVoid){
                //Toast.makeText(getBaseContext(),"Tarea larga finalizada AsynTask1111.",Toast.LENGTH_LONG).show();
                myDialog.dismiss();
            }
            // Puede ser un mensaje de que el hilo a finalizado
            // Lo que se ejecute despues de terminar el hilo
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            //Toast.makeText(getBaseContext(),"Tarea larga ha sido cancelada111.",Toast.LENGTH_LONG).show();
            // Para cancelar el hilo mientras esta haciendo loading
        }
    }

}
