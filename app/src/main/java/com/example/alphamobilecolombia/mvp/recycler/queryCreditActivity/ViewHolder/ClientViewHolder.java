package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.ViewHolder;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;

import com.example.alphamobilecolombia.data.remote.Models.Response.ObjectExpandible;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.Client;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientViewHolder extends GroupViewHolder {
    private TextView cedulaText;
    private TextView name;
    private TextView num_solicitud;
    private ImageView arrow;

    public ClientViewHolder(View itemView) {
        super(itemView);
        arrow = itemView.findViewById(R.id.arrow);
        cedulaText = itemView.findViewById(R.id.cedulaText);
        name = itemView.findViewById(R.id.nombreText);
        num_solicitud = itemView.findViewById(R.id.solicitudText);

    }

    public void bind(Client credit) {
        List<String> arrayList = Arrays.asList(credit.getTitle().split("\\s*,\\s*"));
        cedulaText.setText(arrayList.get(0));
        name.setText(arrayList.get(1));
        num_solicitud.setText(arrayList.get(2));


    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(180, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
        arrow.setRotation(180);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
        arrow.setRotation(360);
    }
}
