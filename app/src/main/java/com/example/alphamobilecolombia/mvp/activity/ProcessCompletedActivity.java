package com.example.alphamobilecolombia.mvp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.mvp.presenter.IProcessCompletedPresenter;
import com.example.alphamobilecolombia.services.FileStorageService;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;

public class ProcessCompletedActivity extends AppCompatActivity {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    IProcessCompletedPresenter _iProcessCompletedPresenter;

    public ProcessCompletedActivity() {
        _iProcessCompletedPresenter = diContainer.injectDIIProcessCompletedPresenter(this);
    }

    private static final int DIALOG_REALLY_EXIT_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_completed);
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorHeader));
        }
        TextView modulo = findViewById(R.id.txt_modulo);
        modulo.setText("Finalización");
        _iProcessCompletedPresenter.CleanCreditInformation();


        /*ProcessCompletedActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent startServiceIntent = new Intent(ProcessCompletedActivity.this, FileStorageService.class);
                startService(startServiceIntent);
            }
        });*/
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final Dialog dialog;
        switch (id) {
            case DIALOG_REALLY_EXIT_ID:
                dialog = new AlertDialog.Builder(this).setMessage(
                        "¡Tu solicitud ha sido cargada satisfactoriamente!")
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent a = new Intent(getBaseContext(), ModuleActivity.class);
                                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(a);
                                    }
                                }).create();
                break;
            default:
                dialog = null;
        }
        return dialog;
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog(DIALOG_REALLY_EXIT_ID);
        }
        return true;
    }

    public void onclickExit(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onClickBtnNewRequest(View view) {
        Intent intent = new Intent(view.getContext(), ScannerActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onClickBtnUpdFiles(View view) {
        Intent intent = new Intent(view.getContext(), ModuleActivity.class);
        startActivityForResult(intent, 0);
    }

}
