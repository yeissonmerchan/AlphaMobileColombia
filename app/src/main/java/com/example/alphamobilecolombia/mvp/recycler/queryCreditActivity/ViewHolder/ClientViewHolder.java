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

public class ClientViewHolder extends GroupViewHolder {
    private TextView cedulaText;
    private ImageView arrow;

    public ClientViewHolder(View itemView) {
        super(itemView);

        arrow = itemView.findViewById(R.id.arrow);
        cedulaText = itemView.findViewById(R.id.cedulaText);

    }

    public void bind(Client credit) {

        cedulaText.setText(credit.getTitle());
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
        RotateAnimation rotate = new RotateAnimation(360, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}
