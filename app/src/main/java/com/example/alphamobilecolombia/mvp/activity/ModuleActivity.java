package com.example.alphamobilecolombia.mvp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.mvp.presenter.IModulePresenter;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class ModuleActivity extends AppCompatActivity {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    IModulePresenter _iModulePresenter;

    public ModuleActivity() {
        _iModulePresenter = diContainer.injectDIIModulePresenter(this);
    }

    private static final String ID_VIDEO_PROPERTY = "idVideoProperty";
    private static final String REMOTE_CONFIG_ID_VIDEO = "id_video_youtube";
    private static final String REMOTE_CONFIG_SHOW = "show_tutorial";
    private Dialog myDialog;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private String link_tutorial;
    private boolean show_tutorial;
    private CardView cardTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        myDialog = new Dialog(this);
        Window window = this.getWindow();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        cardTutorial = findViewById(R.id.cv_video_tutorial);
        modulo.setText("Módulo de solicitudes");
        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        validateRemoteConfigParams();
    }


    private void validateRemoteConfigParams() {
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        link_tutorial = mFirebaseRemoteConfig.getString(REMOTE_CONFIG_ID_VIDEO);
                        show_tutorial = mFirebaseRemoteConfig.getBoolean(REMOTE_CONFIG_SHOW);
                        managementTutorial(show_tutorial);

                    } else {
                        Toast.makeText(ModuleActivity.this, "Fetch failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void managementTutorial(Boolean show_tutorial) {
        if (show_tutorial) {
            cardTutorial.setVisibility(View.VISIBLE);
        } else {
            cardTutorial.setVisibility(View.GONE);
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
    }

    public void onClickBtnNewRequest(View view) {
        _iModulePresenter.CleanCreditInformation();
        new AlertDialog.Builder(ModuleActivity.this)
                .setTitle("¡Atención!")
                .setMessage("¿Desea realizar prevalidación presencial?")
                .setPositiveButton(R.string.alert_activity_module, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(view.getContext(), ScannerActivity.class); //ScannerActivity
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("qr", true);
                        startActivityForResult(intent, 0);
                    }
                })
                .setNegativeButton(R.string.alert_activity_module_c, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(view.getContext(), ScannerActivity.class); //ScannerActivity
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("qr", false);
                        startActivityForResult(intent, 0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void onClickBtnListRequest(View view) {
        Intent intent = new Intent(view.getContext(), QueryCreditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
    }

    public void onClickBtnTutorial(View view) {
        Intent intent = new Intent(this, YouTubeVideoActivity.class);
        intent.putExtra(ID_VIDEO_PROPERTY, link_tutorial);
        startActivity(intent);
    }
}
