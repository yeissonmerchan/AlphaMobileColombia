package com.example.alphamobilecolombia.utils.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.example.alphamobilecolombia.R;

public class DialogClass {
    private AlertDialog dialog;
    private Context context;

    public DialogClass(Context context) {
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View customLayout = inflater.inflate(R.layout.loading_page, null);
        builder.setView(customLayout);
        builder.setCancelable(false);
        dialog = builder.create();
    }

    public void show(){
        if (!dialog.isShowing()){
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void dismiss(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
