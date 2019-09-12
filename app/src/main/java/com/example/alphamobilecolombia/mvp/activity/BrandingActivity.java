package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.mvp.presenter.IVersionUpdatePresenter;
import com.example.alphamobilecolombia.services.FileStorageService;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.security.IAccessToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BrandingActivity extends AppCompatActivity {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    IVersionUpdatePresenter _iVersionUpdatePresenter;

    public BrandingActivity(){
        _iVersionUpdatePresenter = diContainer.injectDIIVersionUpdatePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branding);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }

        Intent startServiceIntent = new Intent(BrandingActivity.this, FileStorageService.class);
        startService(startServiceIntent);

        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

        boolean isValidVersion = _iVersionUpdatePresenter.IsValidVersion();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isValidVersion){
                    Intent intentSuccess = new Intent(getBaseContext(), LoginActivity.class);
                    intentSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentSuccess);
                }
                else{
                    Intent intentFailed = new Intent(getBaseContext(), VersionUpdateActivity.class);
                    intentFailed.putExtra("MessageError", _iVersionUpdatePresenter.MessageError());
                    intentFailed.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentFailed);
                }
            }},2000);
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

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
