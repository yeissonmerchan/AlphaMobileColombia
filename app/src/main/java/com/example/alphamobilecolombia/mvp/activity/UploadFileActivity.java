package com.example.alphamobilecolombia.mvp.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.local.entity.Parameter;
import com.example.alphamobilecolombia.data.local.implement.RealmInstance;
import com.example.alphamobilecolombia.data.local.implement.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.mvp.presenter.ICreditSubjectPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IPersonPresenter;
import com.example.alphamobilecolombia.mvp.presenter.IUploadFilesPresenter;
import com.example.alphamobilecolombia.mvp.presenter.implement.ProcessCompletedPresenter;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.mvp.models.File;
import com.example.alphamobilecolombia.mvp.models.Person;
import com.example.alphamobilecolombia.mvp.models.Persona;
import com.example.alphamobilecolombia.utils.cryptography.implement.RSA;
import com.getbase.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import id.zelory.compressor.Compressor;
import io.realm.RealmObject;


public class UploadFileActivity extends AppCompatActivity {
    List<com.example.alphamobilecolombia.mvp.models.File> listUpload = new ArrayList<com.example.alphamobilecolombia.mvp.models.File>();
    RealmStorage storage = new RealmStorage();
    View view;
    Boolean isCreateUserAndSubject = false;
    String idSujeroCredito;
    String idElement;
    Dialog myDialog;
    com.example.alphamobilecolombia.mvp.models.File fileUpload;
    private Persona persona = new Persona();
    private String IdTipoEmpleado;
    private String IdTipoContrato;
    private String IdDestinoCredito;
    private String IdPagaduria;
    private String pathNewFile1;
    final Context context = this;
    private static final int DIALOG_REALLY_EXIT_ID = 0;

    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    IUploadFilesPresenter _iUploadFilesPresenter;
    IRealmInstance iRealmInstance =new RealmInstance(this, new RSA(this));
    ICreditSubjectPresenter _iCreditSubjectPresenter;
    IPersonPresenter _iPersonPresenter;

    // PopupMenu
    ImageButton btnCloseView1,btnCloseView2,btnCloseView3,btnCloseView4,btnCloseView5,btnCloseView6,btnCloseView7,btnCloseView8,btnCloseView9;

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private final int SELECT_PICTURE = 200;
    private final int PHOTO_CODE = 100;

    String nameUriPath;

    public UploadFileActivity(){
        _iUploadFilesPresenter = diContainer.injectDIIUploadFilesPresenter(this);
        _iCreditSubjectPresenter = diContainer.injectDIICreditSubjectPresenter(this);
        _iPersonPresenter = diContainer.injectDIIPersonPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
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

        /*Parameter newParameter = new Parameter();
        newParameter.setKey("campo1");
        newParameter.setValue("fgfgfhfghfghgfhgfhgfhfg");
        iRealmInstance.Insert(newParameter);

        Parameter newParameter2 = new Parameter();
        newParameter2.setKey("campo2");
        newParameter2.setValue("673478487456");
        iRealmInstance.Insert(newParameter2);

        List<RealmObject> lisParameters = iRealmInstance.GetAll(newParameter);
        List<Parameter> lisParameters2 = iRealmInstance.GetAllGeneric(newParameter);

        Parameter busqueda = iRealmInstance.GetByAttribute(newParameter,"Key",newParameter.getKey());
        String key = busqueda.getKey();
        String value = busqueda.getValue();*/

        // View PopupMenu
        btnCloseView1 = (ImageButton) findViewById(R.id.btnCloseView1);
        //btnCloseView2 = (ImageButton) findViewById(R.id.btnCloseView2);
        btnCloseView3 = (ImageButton) findViewById(R.id.btnCloseView3);
        btnCloseView4 = (ImageButton) findViewById(R.id.btnCloseView4);
        btnCloseView5 = (ImageButton) findViewById(R.id.btnCloseView5);
        btnCloseView6 = (ImageButton) findViewById(R.id.btnCloseView6);
        btnCloseView7 = (ImageButton) findViewById(R.id.btnCloseView7);
        btnCloseView8 = (ImageButton) findViewById(R.id.btnCloseView8);
        btnCloseView9 = (ImageButton) findViewById(R.id.btnCloseView9);
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
        boolean isValidSendFiles = _iUploadFilesPresenter.SendFileList(listUpload,idSujeroCredito,pathNewFile1);

        if(!isValidSendFiles){
            ValidacionCargueDocumentos(view);
        }
        else{
            Intent intent = new Intent(view.getContext(), ProcessCompletedActivity.class);
            startActivity(intent);
        }
    }


    public boolean compareLists(List<File> modelList) {
        List<com.example.alphamobilecolombia.mvp.models.File> filesRequired = new ArrayList<File>();
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(27,"",true,"CargueDocumentosPreValidación",false,"",true));
       // filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(67,"",true,"SolicitudCreditoCara2",false,"",true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(68,"",true,"CedulaCara1",false,"",true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(69,"",true,"CedulaCara2",false,"",true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(70,"",true,"Desprendible1",false,"",true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(71,"",true,"Desprendible2",false,"",true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(77,"",true,"TratamientoDatosPersonales",false,"",true));

        ExistFile existFile = new ExistFile();

        if(listUpload.size()>0) {
            for (com.example.alphamobilecolombia.mvp.models.File file : modelList) {
                switch (file.getType()) {
                    case "CargueDocumentosPreValidación":
                        existFile.CargueDocumentosPreValidación = true;
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
        if(existFile.CedulaCara1 && existFile.CedulaCara2 && existFile.CargueDocumentosPreValidación && existFile.SolicitudCreditoCara2 && existFile.Desprendible1 && existFile.Desprendible2 && existFile.TratamientoDatosPersonales)
        //if(existFile.SolicitudCreditoCara1 && existFile.SolicitudCreditoCara2)
        //if(existFile.SolicitudCreditoCara1)
        {
            return true;
        }
        else{
            validStatusUpload(existFile.CargueDocumentosPreValidación, existFile.SolicitudCreditoCara2, existFile.CedulaCara1, existFile.CedulaCara2,  existFile.Desprendible1, existFile.Desprendible2, existFile.TratamientoDatosPersonales);
            return false;
        }
    }

    // Method Permission Camera

    private void checkPermissionCamera(View v) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            tomarFoto(v);
        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
            } else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
                tomarFoto(v);
            }

        }

        return;
    }

    public void tomarFoto(View v) {
        //idElement = getIdElementUpload(v);
        view = v;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        java.io.File foto = new java.io.File(getExternalFilesDir(null), getNameFile(v));
        intento1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
        startActivityForResult(intento1,PHOTO_CODE);
    }


    public static class ExistFile{
        public boolean CargueDocumentosPreValidación = false;
        public boolean SolicitudCreditoCara2 = false;
        public boolean CedulaCara1 = false;
        public boolean CedulaCara2 = false;
        public boolean Desprendible1 = false;
        public boolean Desprendible2 = false;
        public boolean TratamientoDatosPersonales = false;
    }

    public void recuperarFotoCargada(View v) {
        try {
            //idElement = getIdElementView(v);
            boolean isExist = false;
            boolean isPhoto = false;
            String path = "";
            for(com.example.alphamobilecolombia.mvp.models.File file : listUpload) {
                if(file.getType().equals(idElement)) {
                    isExist = true;
                    isPhoto = file.getPhoto();
                    path = file.getFilePath();
                    break;
                }
            }

            if(isExist) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotatedBitmap = null;
                //Bitmap bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null) + "/" + getNameFile(v));
                if (isPhoto){
                    Bitmap bitmap1 = BitmapFactory.decodeFile(pathNewFile1 + "/" + getNameFile(v));
                    rotatedBitmap = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
                }else {
                    try {
                        Uri imageUri = Uri.parse(path);
                        Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        bitmap1 = Bitmap.createScaledBitmap(bitmap1,bitmap1.getWidth(), bitmap1.getHeight(), true);
                        rotatedBitmap = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                getViewImage(v, rotatedBitmap, true);
            }
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"recuperando archivo "+getNameFile(v),ex,this);
            ex.printStackTrace();
        }
    }

    public void recuperarImagen(View v, boolean isfetch, String path) {
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

    public void recuperarImagenGaleria(View v, boolean isfetch, String path) {
        try {
            Bitmap bitmap = null;
            try {
                Uri imageUri = Uri.parse(path);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(), bitmap.getHeight(), true);
                getViewImage(v,bitmap,isfetch);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*Matrix matrix = new Matrix();
            matrix.postRotate(90);

            Bitmap bitmap1 = BitmapFactory.decodeFile(pathNewFile1+"/"+getNameFile(v));
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);*/

            //getViewImage(v,rotatedBitmap,isfetch);
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
            case "CargueDocumentosPreValidación":
                LinearLayout rl = (LinearLayout)findViewById(R.id.lyt_cargue1);
                if(status){
                    rl.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;
            /*case "SolicitudCreditoCara2":
                LinearLayout rl2 = (LinearLayout)findViewById(R.id.lyt_cargue2);
                if(status){
                    rl2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }
                break;*/
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

               /* LinearLayout rl2 = (LinearLayout)findViewById(R.id.lyt_cargue2);
                if(status2){
                    rl2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.success_bar));
                }
                else{
                    rl2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.failed_bar));
                }*/

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

        //ImageView imagen2 = findViewById(R.id.imageView2);
        //imagen2.setImageBitmap(null);

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
        if (isFetch)
        {
            idButton = getIdViewImage(idElement);

            /*ImageView imagen1 = findViewById(R.id.imageView);
            if(imagen1.getVisibility() == View.GONE && isFetch){
                imagen1.setImageBitmap(bitmap);
                imagen1.setVisibility(View.VISIBLE);
            }
            else if(imagen1.getVisibility() == View.VISIBLE){
                imagen1.setImageBitmap(null);
                imagen1.setVisibility(View.GONE);
            }*/
            switch (idButton) {
                case R.id.imageView1:
                    ImageView imagen1 = findViewById(R.id.imageView1);
                    if(imagen1.getVisibility() == View.GONE && isFetch){
                        imagen1.setImageBitmap(bitmap);
                        imagen1.setVisibility(View.VISIBLE);
                        btnCloseView1.setVisibility(View.VISIBLE);
                    }
                    else if(imagen1.getVisibility() == View.VISIBLE){
                        imagen1.setImageBitmap(null);
                        imagen1.setVisibility(View.GONE);
                        btnCloseView1.setVisibility(View.GONE);
                    }
                    break;
               /* case R.id.imageView2:
                    ImageView imagen2 = findViewById(R.id.imageView2);
                    if(imagen2.getVisibility() == View.GONE && isFetch){
                        imagen2.setImageBitmap(bitmap);
                        imagen2.setVisibility(View.VISIBLE);
                        btnCloseView2.setVisibility(View.VISIBLE);
                    }
                    else if(imagen2.getVisibility() == View.VISIBLE){
                        imagen2.setImageBitmap(null);
                        imagen2.setVisibility(View.GONE);
                    }
                    break;*/
                case R.id.imageView3:
                    ImageView imagen3 = findViewById(R.id.imageView3);
                    if(imagen3.getVisibility() == View.GONE && isFetch){
                        imagen3.setImageBitmap(bitmap);
                        imagen3.setVisibility(View.VISIBLE);
                        btnCloseView3.setVisibility(View.VISIBLE);
                    }
                    else if(imagen3.getVisibility() == View.VISIBLE){
                        imagen3.setImageBitmap(null);
                        imagen3.setVisibility(View.GONE);

                    }
                    break;
                case R.id.imageView4:
                    ImageView imagen4 = findViewById(R.id.imageView4);
                    if(imagen4.getVisibility() == View.GONE && isFetch){
                        imagen4.setImageBitmap(bitmap);
                        imagen4.setVisibility(View.VISIBLE);
                        btnCloseView4.setVisibility(View.VISIBLE);
                    }
                    else if(imagen4.getVisibility() == View.VISIBLE){
                        imagen4.setImageBitmap(null);
                        imagen4.setVisibility(View.GONE);

                    }
                    break;
                case R.id.imageView5:
                    ImageView imagen5 = findViewById(R.id.imageView5);
                    if(imagen5.getVisibility() == View.GONE && isFetch){
                        imagen5.setImageBitmap(bitmap);
                        imagen5.setVisibility(View.VISIBLE);
                        btnCloseView5.setVisibility(View.VISIBLE);
                    }
                    else if(imagen5.getVisibility() == View.VISIBLE){
                        imagen5.setImageBitmap(null);
                        imagen5.setVisibility(View.GONE);
                    }
                    break;
                case R.id.imageView6:
                    ImageView imagen6 = findViewById(R.id.imageView6);
                    if(imagen6.getVisibility() == View.GONE && isFetch){
                        imagen6.setImageBitmap(bitmap);
                        imagen6.setVisibility(View.VISIBLE);
                        btnCloseView6.setVisibility(View.VISIBLE);
                    }
                    else if(imagen6.getVisibility() == View.VISIBLE){
                        imagen6.setImageBitmap(null);
                        imagen6.setVisibility(View.GONE);
                    }
                    break;
                case R.id.imageView7:
                    ImageView imagen7 = findViewById(R.id.imageView7);
                    if(imagen7.getVisibility() == View.GONE && isFetch){
                        imagen7.setImageBitmap(bitmap);
                        imagen7.setVisibility(View.VISIBLE);
                        btnCloseView7.setVisibility(View.VISIBLE);
                    }
                    else if(imagen7.getVisibility() == View.VISIBLE){
                        imagen7.setImageBitmap(null);
                        imagen7.setVisibility(View.GONE);

                    }
                    break;
                case R.id.imageView8:
                    ImageView imagen8 = findViewById(R.id.imageView8);
                    if(imagen8.getVisibility() == View.GONE && isFetch){
                        imagen8.setImageBitmap(bitmap);
                        imagen8.setVisibility(View.VISIBLE);
                        btnCloseView8.setVisibility(View.VISIBLE);
                    }
                    else if(imagen8.getVisibility() == View.VISIBLE){
                        imagen8.setImageBitmap(null);
                        imagen8.setVisibility(View.GONE);

                    }
                    break;

                case R.id.imageView9:
                    ImageView imagen9 = findViewById(R.id.imageView9);
                    if(imagen9.getVisibility() == View.GONE && isFetch){
                        imagen9.setImageBitmap(bitmap);
                        imagen9.setVisibility(View.VISIBLE);
                        btnCloseView9.setVisibility(View.VISIBLE);
                    }
                    else if(imagen9.getVisibility() == View.VISIBLE){
                        imagen9.setImageBitmap(null);
                        imagen9.setVisibility(View.GONE);
                    }
                    break;
            }
        }

    }

    public String getIdElementView(View v){
        String id = "";
        switch (v.getId()) {
            /*case R.id.btnView1:
                id = "CargueDocumentosPreValidación";
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
                break;*/
        }
        return id;

    }

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
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
                                        Intent a = new Intent(getBaseContext(), ModuleActivity.class);
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
            case "CargueDocumentosPreValidación":
                id = R.id.imageView1;
                break;
            /*case "SolicitudCreditoCara2":
                id = R.id.imageView2;
                break;*/
            case "CedulaCara1":
                id = R.id.imageView3;
                break;
            case "CedulaCara2":
                id = R.id.imageView4;
                break;
            case "Desprendible1":
                id = R.id.imageView5;
                break;
            case "Desprendible2":
                id = R.id.imageView6;
                break;
            case "Desprendible3":
                id = R.id.imageView7;
                break;
            case "Desprendible4":
                id = R.id.imageView8;
                break;
            case "TratamientoDatosPersonales":
                id = R.id.imageView9;
                break;
        }
        return id;

    }

    /*public String getIdElementUpload(View v){
        String id = "";
        switch (v.getId()) {
            case R.id.btnUpload:
                id = "CargueDocumentosPreValidación";
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
    }*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        recuperarFotoCargada(view);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_CODE:
            case 0 :
                if (resultCode != 0) {
                    String path = pathNewFile1+"/"+getNameFile(view);
                    ConfirmacionImagen(view, path,true);

                }
                break;

            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    //ConfirmacionImagen(view);
                    Uri selectedImageUri = data.getData();
                    ConfirmacionImagen(view,selectedImageUri.toString(),false);
                }
        }

    }

    public void ConfirmacionImagen(final View view,final String path,final Boolean isPhoto){
        //AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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

                        for(com.example.alphamobilecolombia.mvp.models.File file : listUpload) {
                            if(file.getType().equals(nameFile)) {
                                isExist = true;
                            }
                        }

                        if (!isExist){
                            fileUpload = new com.example.alphamobilecolombia.mvp.models.File(0, getNameFile(view),false, nameFile,true,path,isPhoto);
                            listUpload.add(fileUpload);
                        }

                        //LoadinAsyncTask loadinAsyncTask = new LoadinAsyncTask();
                        //loadinAsyncTask.execute();
                        //uploadFilesPresenter.uploadFiles(pathFile,this);
                        if(isPhoto){
                            recuperarImagen(view,false,path);
                        }else {
                            recuperarImagenGaleria(view,false,path);
                        }

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
        SharedPreferences sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        String user = sharedPref.getString("idUser", "");
        try {
            String genero = getIntent().getStringExtra("PERSONA_Genero");
            int generoId = 0;
            switch (genero) {
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

        }
        catch (Exception ex){
            persona.setCedula("1052407752");
            persona.setNombre("Yeisson");
            persona.setNombre2("Andres");
            persona.setApellido1("Merchan");
            persona.setApellido2("Lemus");
            persona.setFechaNacimiento("1996/08/08");
            persona.setGenero(String.valueOf(1));
            persona.setCelular("234");
            IdTipoEmpleado = "1";
            IdTipoContrato = "2";
            IdDestinoCredito = "1";
            IdPagaduria = "35";
        }

        boolean isSuccessPerson = _iPersonPresenter.SavePerson(persona,user);
        if (isSuccessPerson){
            boolean isSuccessSubjectCredit = _iCreditSubjectPresenter.SaveCreditSubject(persona,user,_iPersonPresenter.GetIdPerson());
            if(isSuccessSubjectCredit){
                idSujeroCredito = String.valueOf(_iCreditSubjectPresenter.GetIdSubjectCredit());
                isCreateUserAndSubject = true;
            }
            else {
                NotificacionErrorDatos(this.context);
            }
        }
        else {
            NotificacionErrorDatos(this.context);
        }

        /*
        ProcessCompletedPresenter presenter = new ProcessCompletedPresenter();
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

                    HttpResponse modelcreditSubject = presenter.PostInsertCreditSubject(persona, codigoTransaccion, IdTypeEmployee, IdTypeCont, IdTypeDest,user, idPagaduria,this.context);
                    if (modelcreditSubject!=null){

                        if(modelcreditSubject.getCode().contains("200")) {

                            JSONObject objeto2 = (JSONObject) modelcreditSubject.getData();
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
        */
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
                        Intent intent = new Intent(view, ModuleActivity.class);
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
        for (String cc: getResources().getStringArray(R.array.spinner_credit_destination)) {
            i++;
            if (cc.equals(name))
                break;
        }
        return getResources().getStringArray(R.array.spinner_code_credit_destination)[i];
    }


    private String getCodeTipoContrato(String name) {
        int i = -1;
        for (String cc: getResources().getStringArray(R.array.spinner_all_type_contract_employee)) {
            i++;
            if (cc.equals(name))
                break;
        }
        return getResources().getStringArray(R.array.spinner_code_all_type_contract_employee)[i];
    }


    private String getCodeTipoEmpleado(String name) {
        int i = -1;
        for (String cc: getResources().getStringArray(R.array.spinner_employee_type)) {
            i++;
            if (cc.equals(name))
                break;
        }
        return getResources().getStringArray(R.array.spinner_code_employee_type)[i];
    }

    // inflate_PopupMenu

    public String getByNameImage(View v){
        String id = "";
        switch (v.getId()) {
            case R.id.btnPopup1:
                id = "CargueDocumentosPreValidación";
                break;
            /*case R.id.btnPopup2:
                id = "SolicitudCreditoCara2";
                break;*/
            case R.id.btnPopup3:
                id = "CedulaCara1";
                break;
            case R.id.btnPopup4:
                id = "CedulaCara2";
                break;
            case R.id.btnPopup5:
                id = "Desprendible1";
                break;
            case R.id.btnPopup6:
                id = "Desprendible2";
                break;
            case R.id.btnPopup7:
                id = "Desprendible3";
                break;
            case R.id.btnPopup8:
                id = "Desprendible4";
                break;
            case R.id.btnPopup9:
                id = "TratamientoDatosPersonales";
                break;
        }
        return id;

    }

    public void onClickOptions(View view) {
        idElement = getByNameImage(view);
        PopupMenu popup = new PopupMenu(this, view);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_upload_file);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.btnUpload:
                        checkPermissionCamera(view);


                        break;

                    case R.id.btnLoadGallery:
                        checkPermissionGallery(view);

                        break;

                    case R.id.btnView:
                        recuperarFotoCargada(view);
                        break;

                }
                return false;
            }
        });
        popup.show();

        closeButtonImage(view, popup);

    }

    private void closeButtonImage(View view, PopupMenu popup){
        switch (view.getId()){
            case R.id.btnCloseView1:
                btnCloseView1.setVisibility(View.GONE);
                ImageView imagen1 = findViewById(R.id.imageView1);
                imagen1.setImageBitmap(null);
                imagen1.setVisibility(View.GONE);
                popup.dismiss();
                break;
            /*case R.id.btnCloseView2:
                btnCloseView2.setVisibility(View.GONE);

                ImageView imagen2 = findViewById(R.id.imageView2);
                imagen2.setImageBitmap(null);
                imagen2.setVisibility(View.GONE);
                popup.dismiss();
                break;*/

            case R.id.btnCloseView3:
                btnCloseView3.setVisibility(View.GONE);
                ImageView imagen3 = findViewById(R.id.imageView3);
                imagen3.setImageBitmap(null);
                imagen3.setVisibility(View.GONE);
                popup.dismiss();
                break;
            case R.id.btnCloseView4:
                btnCloseView4.setVisibility(View.GONE);
                ImageView imagen4 = findViewById(R.id.imageView4);
                imagen4.setImageBitmap(null);
                imagen4.setVisibility(View.GONE);
                popup.dismiss();
                break;
            case R.id.btnCloseView5:
                btnCloseView5.setVisibility(View.GONE);
                ImageView imagen5 = findViewById(R.id.imageView5);
                imagen5.setImageBitmap(null);
                imagen5.setVisibility(View.GONE);
                popup.dismiss();
                break;
            case R.id.btnCloseView6:
                btnCloseView6.setVisibility(View.GONE);
                ImageView imagen6 = findViewById(R.id.imageView6);
                imagen6.setImageBitmap(null);
                imagen6.setVisibility(View.GONE);
                popup.dismiss();
                break;
            case R.id.btnCloseView7:
                btnCloseView7.setVisibility(View.GONE);
                ImageView imagen7 = findViewById(R.id.imageView7);
                imagen7.setImageBitmap(null);
                imagen7.setVisibility(View.GONE);
                popup.dismiss();
                break;
            case R.id.btnCloseView8:
                btnCloseView8.setVisibility(View.GONE);
                ImageView imagen8 = findViewById(R.id.imageView8);
                imagen8.setImageBitmap(null);
                imagen8.setVisibility(View.GONE);
                popup.dismiss();
                break;
            case R.id.btnCloseView9:
                btnCloseView9.setVisibility(View.GONE);
                ImageView imagen9 = findViewById(R.id.imageView9);
                imagen9.setImageBitmap(null);
                imagen9.setVisibility(View.GONE);
                popup.dismiss();
                break;
        }
    }

    //OpenGallery
    private void checkPermissionGallery(View view) {
        this.view = view;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery(view);
        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            } else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
                openGallery(view);
            }

        }

        return;
    }

    private void openGallery(View v) {
        view = v;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        java.io.File foto = new java.io.File(getExternalFilesDir(null), getNameFile(v));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
        startActivityForResult(intent.createChooser(intent, "Selecciona app imagen"), SELECT_PICTURE);
    }
}