package com.example.alphamobilecolombia.mvp.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.mvp.presenter.ILoginPresenter;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.example.alphamobilecolombia.utils.security.IAccessToken;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;


//Define la actividad del Login
public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    //************************************************************************************ INYECCION

    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    ILoginPresenter _iLoginPresenter;
    IAccessToken _iAccessToken;

    //************************************************************************************ CAMPOS

    //Define el campo usuario
    @NotEmpty(message = "Ingrese un valor valido")
    @Length(min = 6, max = 20, message = "La longitud no es correcta")
    private EditText edt_username;

    //Define el campo contraseña
    @NotEmpty(message = "Ingrese un valor valido")
    @Length(min = 6, max = 50, message = "La longitud no es correcta")
    private EditText edt_password;

    //************************************************************************************ VALIDACIÓN

    private Validator validator;

    private boolean validationResult = false;

    private static final String TAG = "MainActivity";

    //************************************************************************************ CONSTRUCTOR

    //Se produce cuando se inicia esta actividad
    public LoginActivity() {
        _iAccessToken = diContainer.injectIAccessToken(this);
        _iLoginPresenter = diContainer.injectDIILoginPresenter(this);
    }

    //************************************************************************************ INICIO

    //Se produce al crearse esta actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        validator = new Validator(this);
        validator.setValidationListener(this);
        //_iAccessToken.CleanToken();
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorHeader));
        }

        //************************************************************************************ USUARIO

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_username.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        //************************************************************************************ CONTRASEÑA

        edt_password = (EditText) findViewById(R.id.edt_password); //Obtiene el campo contraseña

        ImageView show_pass_btn = (ImageView) findViewById(R.id.show_pass_btn); //Obtiene el icono del ojito de la contraseña

        //Establece el evento de cambio de texto del campo contraseña
        edt_password.addTextChangedListener(new TextWatcher() {

            //Se produce antes de cambiar el texto
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            //Se produce en el momento de cambiar el texto
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            //Se produce cuando se cambia el texto del campo contraseña
            @Override
            public void afterTextChanged(Editable valor) {
                if (valor != null && valor.length() > 0) { //Si campo contraseña contiene texto entonces
                    show_pass_btn.setVisibility(View.VISIBLE); //Muestra el ojito
                } else {
                    show_pass_btn.setVisibility(View.GONE); //Oculta el ojito
                }
            }
        });

        //************************************************************************************ SERVICIO SEGUNDO PLANO

        ImagesBackgroundService Servicio = new ImagesBackgroundService(this); //Define el servicio en segundo plano
        Intent Intencion = new Intent(this, Servicio.getClass()); //Define la intención del servicio en segundo plano
        if (!isMyServiceRunning(Servicio.getClass())) { //Si el servicio en segundo plano no está corriendo entonces
            startService(Intencion); //Ejecuta la intención del servicio en segundo plano
        }

        //************************************************************************************
    }

    //************************************************************************************ SERVICIO SEGUNDO PLANO

    //Comprueba si el servicio ya está corriendo
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    //************************************************************************************ CONTRASEÑA

    //Cambia el icono del ojito de la contraseña al ser presionado
    public void ShowHidePass(View view) {

        if (view.getId() == R.id.show_pass_btn) {

            if (edt_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.mipmap.ocultar_password);

                //Show Password
                edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                edt_password.setSelection(edt_password.length());
            } else {
                ((ImageView) (view)).setImageResource(R.mipmap.mostrar_password);

                //Hide Password
                edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                edt_password.setSelection(edt_password.length());

            }
        }
    }

    //************************************************************************************

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
        //stopService(Intencion);
        super.onDestroy();
        Runtime.getRuntime().gc();
        Log.d("Lifecycle", "onDestroy()");
    }

    @Override
    public void onValidationSucceeded() {
        validationResult = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
        validationResult = false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClickBtn(View view) {
        validator.validate();
        if (validationResult) {
            TextView message = findViewById(R.id.txt_message);
            String userText = edt_username.getText().toString();
            String passwordText = edt_password.getText().toString();
            Boolean isValid = _iLoginPresenter.LoginCheck(userText, passwordText);
            if (isValid) {
                Intent intent = new Intent(view.getContext(), ModuleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent, 0);
            } else {
                TextView txt_message = findViewById(R.id.txt_message);
                txt_message.setText(_iLoginPresenter.MessageError());
                errorNotification(view, _iLoginPresenter.MessageError());
            }
        }
    }

    public void errorNotification(final View view, String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
