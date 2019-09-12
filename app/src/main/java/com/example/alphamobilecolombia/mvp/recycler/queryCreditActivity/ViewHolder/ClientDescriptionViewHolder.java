package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostQueryCredit;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.ClientDescription;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.DecimalFormat;

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
    private DecimalFormat format = new DecimalFormat("$###,###.##");

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

    public void bind(PostQueryCredit creditDescription) {
        estadoText.setText(creditDescription.getGeneralState());
        pagaduriaText.setText(creditDescription.getPagaduria());
        fechaEnvioPrevaText.setText(creditDescription.getFechaEnvioPrevalidacion());
        montoSugeridoText.setText(format.format(Integer.parseInt(creditDescription.getMontoSugerido())));
        cuotaSugText.setText(format.format(Integer.parseInt(creditDescription.getCuotaSug())));
        plazoSugeridoText.setText(String.format("%1s Meses", creditDescription.getPlazoSugerido()));
        fechaPrevalidacionText.setText(creditDescription.getFechaPrevalidacion());
        observacionPrevaText.setText(creditDescription.getObsPrevalidacion());
        observacionCreditoText.setText(creditDescription.getObservacionCredito());
    }
}
