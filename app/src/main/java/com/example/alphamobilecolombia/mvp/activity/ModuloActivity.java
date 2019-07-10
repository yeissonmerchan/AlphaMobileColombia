package com.example.alphamobilecolombia.mvp.activity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphamobilecolombia.R;

public class ModuloActivity extends AppCompatActivity {

    Dialog myDialog;
    //AnimationDrawable LoadingAnimation;
    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo);

        myDialog = new Dialog(this);
        /*ImageView imageView = (ImageView)findViewById(R.id.imageLoading);
        imageView.setBackgroundResource(R.drawable.loading_animation);
        LoadingAnimation = (AnimationDrawable) imageView.getBackground();*/
        //progressBar = (ProgressBar)findViewById(R.id.progressBar);

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Módulo de solicitudes");




    }

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onClickBtnNewRequest(View view) {
        Intent intent = new Intent (view.getContext(), ScannerActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onClickBtnListRequest(View view) {

        /*
        myDialog.setContentView(R.layout.loading_page);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        //myDialog.dismiss();

        //LoadingAnimation.start();

        */
        /*
        Intent intent = new Intent (view.getContext(), ConsultaCreditosActivity.class);
        startActivityForResult(intent, 0);
        */


        LoadinAsyncTask loadinAsyncTask = new LoadinAsyncTask();
        loadinAsyncTask.execute();

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {

                myDialog.setContentView(R.layout.loading_page);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //myDialog.dismiss();
                        Intent intent = new Intent (view.getContext(), ConsultaCreditosActivity.class);
                        startActivityForResult(intent, 0);

                    }
                });
            }
        }).start();
        */
    }

    private class LoadinAsyncTask extends AsyncTask<Void,Integer,Boolean>{
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

        @Override
        protected Boolean doInBackground(Void... voids) {
            // Toast.makeText(getBaseContext(),"Hola 123",Toast.LENGTH_LONG).show();
            /*for (int i=0;i<=10; i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i*10);
                if(isCancelled()){
                    break;
                }
            }*/
            Intent intent = new Intent (getBaseContext(), ConsultaCreditosActivity.class);
            startActivityForResult(intent, 0);
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
