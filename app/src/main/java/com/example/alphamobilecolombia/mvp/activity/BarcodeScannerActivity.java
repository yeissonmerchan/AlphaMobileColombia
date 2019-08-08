package com.example.alphamobilecolombia.mvp.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import com.example.alphamobilecolombia.utils.BarcodeRectOverlay.BarcodeRectOverlay;

import com.bobekos.bobek.scanner.BarcodeView;
import com.bobekos.bobek.scanner.overlay.BarcodeOverlay;
import com.example.alphamobilecolombia.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class BarcodeScannerActivity extends AppCompatActivity {

    private Disposable mDisposable = null;
    private boolean isFlashOn = true;
    private boolean isBeepOn = false;
    private boolean isVibrateOn = false;
    private int levelVibrate = 0;
    private static final int DIALOG_REALLY_EXIT_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barcode_scanner);

        if(isVibrateOn)
            levelVibrate = 500;
        else levelVibrate = 0;
        BarcodeView barcodeView = findViewById(R.id.barcodeView);
        this.mDisposable = barcodeView
                .drawOverlay(new BarcodeRectOverlay(this))
                .setBeepSound(true)
                .setVibration(levelVibrate)
                .setAutoFocus(true)
                .setBarcodeFormats(Barcode.PDF417)
                .setFlash(isFlashOn)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Consumer)(new Consumer() {
                            // $FF: synthetic method
                            // $FF: bridge method
                            public void accept(Object var1) {
                                this.accept((Barcode)var1);
                            }

                            public final void accept(Barcode it) {
                                //Toast.makeText(getBaseContext(), (CharSequence)it.displayValue, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent (getBaseContext(), ScannerActivity.class);
                                intent.putExtra("isLoadNextVersion", true);
                                intent.putExtra("strDataScan",it.displayValue);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivityForResult(intent, 0);
                            }
                        }));

    }

    public void btnFlash(View view) {
        BarcodeView barcodeView = findViewById(R.id.barcodeView);
        isFlashOn = !isFlashOn;
        barcodeView.setFlash(isFlashOn);
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
        mDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
        Log.d("Lifecycle", "onDestroy()");
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
}
