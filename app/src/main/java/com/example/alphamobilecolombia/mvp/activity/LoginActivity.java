package com.example.alphamobilecolombia.mvp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.data.remote.Models.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.User;
import com.example.alphamobilecolombia.mvp.presenter.LoginPresenter;
import com.example.alphamobilecolombia.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.mobsandgeeks.saripaar.annotation.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {
    private Button loginButton = null;

    //**************    VALIDACIÓN DE CAMPOS     *****************//
    //Camilo Lis - 22-06-2019
    @NotEmpty(message = "Ingrese un valor valido")
    @Length(min = 6, max = 20, message = "La longitud no es correcta")
    private EditText editTextUsername;


    @NotEmpty(message = "Ingrese un valor valido")
    @Length(min = 6, max = 50, message = "La longitud no es correcta")
    private EditText editTextPassword;

    //Validator Instance
    private Validator validator;


    private boolean validationResult = false;

    //***********************************************************//
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        validator = new Validator(this);
        validator.setValidationListener(this);

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorHeader));
        }

        RealmStorage storage = new RealmStorage();
        storage.initLocalStorage(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        EditText edt_names = (EditText) findViewById(R.id.edt_username);
        edt_names.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
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

    //**************    VALIDACIÓN DE CAMPOS     *****************//

    //Mapeo del campos del layout a las variables del activity
    //Camilo Lis - 22-06-2019
    private void initView() {
        editTextUsername = findViewById(R.id.edt_username);
        editTextPassword = findViewById(R.id.edt_password);
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

    //***********************************************************//

    public void NotificacionErrorDatos(final View view, String menssage){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage(menssage);
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

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClickBtn(View view) {
        validator.validate();

        if(validationResult) {
            TextView message = findViewById(R.id.txt_message);
            final EditText user = findViewById(R.id.edt_username);
            final EditText password = findViewById(R.id.edt_password);
            //Toast.makeText(view.getContext(), "Button Clicked", Toast.LENGTH_LONG).show();
            String userText = user.getText().toString();
            String passwordText = password.getText().toString();


                LoginPresenter presenter = new LoginPresenter();
                HttpResponse model = presenter.PostLogin(userText, passwordText,view.getContext());

                if (model != null) {
                    if(model.getCode().contains("200")){
                        try {
                            User usuario = new User();

                            SharedPreferences sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
                            usuario.setData(sharedPref, (JSONObject) model.getData(), userText);
                            mFirebaseAnalytics.setUserProperty("user_success", userText);
                            //throw new Exception("Error scanner");
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            mFirebaseAnalytics.setUserProperty("user_failed", userText);
                            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),userText,e,this);
                        }

                        Intent intent = new Intent(view.getContext(), ModuloActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivityForResult(intent, 0);
                        message.setText(model.getMessage());

                    }
                    else{
                        TextView txt_message = findViewById(R.id.txt_message);
                        txt_message.setText(model.getMessage());
                        NotificacionErrorDatos(view,model.getMessage());
                    }
                }
            }

    }
}
