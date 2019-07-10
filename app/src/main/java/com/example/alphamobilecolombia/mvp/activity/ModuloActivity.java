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

import android.util.Log;
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

    public void onClickBtnNewRequest(View view) {
        Intent intent = new Intent (view.getContext(), ScannerActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onClickBtnListRequest(View view) {
        Intent intent = new Intent (view.getContext(), ConsultaCreditosActivity.class);
        startActivityForResult(intent, 0);


        /*
        new Thread(new Runnable() {
            @Override
            public void run() {

                myDialog.setContentView(R.layout.loading_page);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.show();

                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent (view.getContext(), ConsultaCreditosActivity.class);
                        startActivityForResult(intent, 0);
                        myDialog.dismiss();
                    }
                });

            }
        }).start();
        */
    }


}
