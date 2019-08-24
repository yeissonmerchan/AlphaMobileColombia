package com.example.alphamobilecolombia.mvp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.mvp.presenter.ILoginPresenter;
import com.example.alphamobilecolombia.utils.DependencyInjectionContainer;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {
    DependencyInjectionContainer diContainer = new DependencyInjectionContainer();
    ILoginPresenter _iLoginPresenter;
    public LoginActivity(){
        _iLoginPresenter = diContainer.injectDIILoginPresenter(this);
    }

    @NotEmpty(message = "Ingrese un valor valido")
    @Length(min = 6, max = 20, message = "La longitud no es correcta")
    EditText editTextUsername;

    @NotEmpty(message = "Ingrese un valor valido")
    @Length(min = 6, max = 50, message = "La longitud no es correcta")
    EditText editTextPassword;
    private Validator validator;
    private boolean validationResult = false;

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
            String userText = editTextUsername.getText().toString();
            String passwordText = editTextPassword.getText().toString();
            Boolean isValid = _iLoginPresenter.LoginCheck(userText, passwordText);
            if (isValid){
                Intent intent = new Intent(view.getContext(), ModuleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent, 0);
            }
            else{
                TextView txt_message = findViewById(R.id.txt_message);
                txt_message.setText(_iLoginPresenter.MessageError());
                errorNotification(view,_iLoginPresenter.MessageError());
            }
        }
    }

    public void errorNotification(final View view, String message){
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
