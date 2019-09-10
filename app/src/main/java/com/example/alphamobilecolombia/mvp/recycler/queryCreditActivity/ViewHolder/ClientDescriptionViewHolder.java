package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.ClientDescription;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class ClientDescriptionViewHolder extends ChildViewHolder {

    private TextView estadoText;
    private TextView pagaduriaText;
    private TextView fechaEnvioPrevaText;
    private TextView montoSugeridoText;
    private TextView cuotaSugText;
    private TextView plazoSugeridoText;
    private TextView fechaPrevalidacionText;
    private TextView observacionPrevaText;
    private TextView observacionCreditoText;

    public ClientDescriptionViewHolder(View itemView) {
        super(itemView);

        estadoText = itemView.findViewById(R.id.estadoText);
        pagaduriaText = itemView.findViewById(R.id.pagaduriaText);
        fechaEnvioPrevaText = itemView.findViewById(R.id.fechaEnvioPrevaText);
        montoSugeridoText = itemView.findViewById(R.id.montoSugeridoText);
        cuotaSugText = itemView.findViewById(R.id.cuotaSugText);
        plazoSugeridoText = itemView.findViewById(R.id.plazoSugeridoText);
        fechaPrevalidacionText = itemView.findViewById(R.id.fechaPrevalidacionText);
        observacionPrevaText = itemView.findViewById(R.id.observacionPrevaText);
        observacionCreditoText = itemView.findViewById(R.id.observacionCreditoText);
    }

    public void bind(ClientDescription creditDescription){
        estadoText.setText(creditDescription.estadoText);
        pagaduriaText.setText(creditDescription.pagaduriaText);
        fechaEnvioPrevaText.setText(creditDescription.fechaEnvioPrevaText);
        montoSugeridoText.setText(creditDescription.montoSugeridoText);
        cuotaSugText.setText(creditDescription.cuotaSugText);
        plazoSugeridoText.setText(creditDescription.plazoSugeridoText);
        fechaPrevalidacionText.setText(creditDescription.fechaPrevalidacionText);
        observacionPrevaText.setText(creditDescription.observacionPrevaText);
        observacionCreditoText.setText(creditDescription.observacionCreditoText);
    }
}
