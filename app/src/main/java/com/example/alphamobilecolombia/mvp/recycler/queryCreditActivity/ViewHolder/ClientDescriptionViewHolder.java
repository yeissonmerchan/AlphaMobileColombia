package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostQueryCredit;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.DecimalFormat;

public class ClientDescriptionViewHolder extends ChildViewHolder {

    //Fileds
    LinearLayout linear3, linear4, linear5, linear6, linear7, linear8, linear9;
    private TextView fieldEstado;
    private TextView fieldPagaduria;
    private TextView fieldFechaEnvio;
    private TextView fieldMontoSugerido;
    private TextView fieldCuotaSugerida;
    private TextView fieldPlazoSugerido;
    private TextView fieldFechaPrevalidacion;
    private TextView fieldObsPrevalidacion;
    private TextView fieldObsCredito;

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
        //Fields
        linear3 = itemView.findViewById(R.id.linear3);
        linear4 = itemView.findViewById(R.id.linear4);
        linear5 = itemView.findViewById(R.id.linear5);
        linear6 = itemView.findViewById(R.id.linear6);
        linear7 = itemView.findViewById(R.id.linear7);
        linear8 = itemView.findViewById(R.id.linear8);
        linear9 = itemView.findViewById(R.id.linear3);
        linear3 = itemView.findViewById(R.id.linear3);
        linear3 = itemView.findViewById(R.id.linear3);
        linear3 = itemView.findViewById(R.id.linear3);

        fieldEstado = itemView.findViewById(R.id.fieldEstado);
        fieldPagaduria = itemView.findViewById(R.id.fieldPagaduria);
        fieldFechaEnvio = itemView.findViewById(R.id.fieldFechaEnvio);
        fieldMontoSugerido = itemView.findViewById(R.id.fieldMontoSugerido);
        fieldCuotaSugerida = itemView.findViewById(R.id.fieldCuotaSugerida);
        fieldPlazoSugerido = itemView.findViewById(R.id.fieldPlazoSugerido);
        fieldFechaPrevalidacion = itemView.findViewById(R.id.fieldFechaPrevalidacion);
        fieldObsPrevalidacion = itemView.findViewById(R.id.fieldObsPrevalidacion);
        fieldObsCredito = itemView.findViewById(R.id.fieldObsCredito);

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

        String estado = creditDescription.getGeneralState();
        if (estado != null && !estado.equals("")) {
            estadoText.setText(estado);
        } else {
            fieldEstado.setVisibility(View.GONE);
        }

        if (creditDescription.getPagaduria() != null)
            if (!creditDescription.getPagaduria().equals("")) {
                pagaduriaText.setText(creditDescription.getPagaduria());
            } else {
                fieldPagaduria.setVisibility(View.GONE);
            }

        if (creditDescription.getFechaEnvioPrevalidacion() != null && !creditDescription.getFechaEnvioPrevalidacion().equals("")) {
            fechaEnvioPrevaText.setText(creditDescription.getFechaEnvioPrevalidacion());
        } else {
            fieldFechaEnvio.setVisibility(View.GONE);
            linear3.setVisibility(View.GONE);
        }

        String montoSugerido = format.format(Integer.parseInt(creditDescription.getMontoSugerido()));
        if (montoSugerido != null && !montoSugerido.equals("")) {
                montoSugeridoText.setText(montoSugerido);
            } else {
                fieldMontoSugerido.setVisibility(View.GONE);
                linear4.setVisibility(View.GONE);
            }

        String cuotaSugerida = format.format(Integer.parseInt(creditDescription.getCuotaSug()));
        if (cuotaSugerida != null)
            if (!cuotaSugerida.equals("")) {
                cuotaSugText.setText(cuotaSugerida);
            } else {
                fieldCuotaSugerida.setVisibility(View.GONE);
                linear5.setVisibility(View.GONE);
            }

        String plazoSugerido = String.format("%1s Meses", creditDescription.getPlazoSugerido());
        if (plazoSugerido.equals("")) {
            fieldPlazoSugerido.setVisibility(View.GONE);
            linear6.setVisibility(View.GONE);
        } else {
            plazoSugeridoText.setText(plazoSugerido);
        }

        if (creditDescription.getFechaPrevalidacion() != null && !creditDescription.getFechaPrevalidacion().equals("")) {
            fechaPrevalidacionText.setText(creditDescription.getFechaPrevalidacion());
        } else {
            fieldFechaPrevalidacion.setVisibility(View.GONE);
            linear7.setVisibility(View.GONE);
        }

        String obsPreva = creditDescription.getObsPrevalidacion();
        if (obsPreva == null || obsPreva.equals("")) {
            fieldObsPrevalidacion.setVisibility(View.GONE);
        } else {
            observacionPrevaText.setText(obsPreva);
        }

        String obsCredrit = creditDescription.getObservacionCredito();
        if (obsCredrit != null && !obsCredrit.equals("")) {
            observacionCreditoText.setText(obsCredrit);
        } else {
            fieldObsCredito.setVisibility(View.GONE);
        }
    }

}
