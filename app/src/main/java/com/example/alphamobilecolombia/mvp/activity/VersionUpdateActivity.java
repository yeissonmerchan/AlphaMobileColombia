package com.example.alphamobilecolombia.mvp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.utils.configuration.ApplicationData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class VersionUpdateActivity extends AppCompatActivity {
    String MessageError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_update);

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }

        MessageError = getIntent().getStringExtra("MessageError");
        TextView txtSolicitudCredito = findViewById(R.id.txtSolicitudCredito);
        txtSolicitudCredito.setText(MessageError);
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

    public void updateStore(View view) {
        ApplicationData applicationData = new ApplicationData();
        applicationData.RestartNewVersionApp(getApplicationContext());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.url_store)));
        startActivity(intent);
    }

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
    }
}
