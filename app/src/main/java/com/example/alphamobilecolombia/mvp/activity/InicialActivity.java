package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.mvp.presenter.LoginPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mobsandgeeks.saripaar.Validator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.os.SystemClock;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class InicialActivity extends AppCompatActivity {
    String version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        LoginPresenter presenter = new LoginPresenter();
        HttpResponse model = presenter.GetVersion(version);

        if (model.getCode().contains("200")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(InicialActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                }
            },2000);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(InicialActivity.this, ActualizacionActivity.class);
                    intent.putExtra("MessageError", model.getMessage());
                    startActivityForResult(intent, 0);
                }
            },2000);
        }
    }



}
