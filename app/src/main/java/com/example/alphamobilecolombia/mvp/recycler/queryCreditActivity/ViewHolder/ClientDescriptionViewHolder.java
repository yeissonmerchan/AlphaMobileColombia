package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostQueryCredit;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.DecimalFormat;

public class ClientDescriptionViewHolder extends ChildViewHolder {

    //Fileds
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
        if (estado.equals("")){
            fieldEstado.setVisibility(View.GONE);
        }else{
            estadoText.setText(estado);
        }

        if (creditDescription.getPagaduria().equals("")){
            fieldPagaduria.setVisibility(View.GONE);
        }else{
            pagaduriaText.setText(creditDescription.getPagaduria());
        }

        if (creditDescription.getFechaEnvioPrevalidacion().equals("")){
            fieldFechaEnvio.setVisibility(View.GONE);
        }else{
            fechaEnvioPrevaText.setText(creditDescription.getFechaEnvioPrevalidacion());
        }

        String montoSugerido = format.format(Integer.parseInt(creditDescription.getMontoSugerido()));
        if (montoSugerido.equals("")){
            fieldMontoSugerido.setVisibility(View.GONE);
        }else{
            montoSugeridoText.setText(montoSugerido);
        }

        String cuotaSugerida = format.format(Integer.parseInt(creditDescription.getCuotaSug()));
        if (cuotaSugerida.equals("")){
            fieldCuotaSugerida.setVisibility(View.GONE);
        }else{
            cuotaSugText.setText(cuotaSugerida);
        }

        String plazoSugerido = String.format("%1s Meses", creditDescription.getPlazoSugerido());
        if (plazoSugerido.equals("")){
            fieldPlazoSugerido.setVisibility(View.GONE);
        }else{
            plazoSugeridoText.setText(plazoSugerido);
        }

        if (creditDescription.getFechaPrevalidacion().equals("")){
            fieldFechaPrevalidacion.setVisibility(View.GONE);
        }else{
            fechaPrevalidacionText.setText(creditDescription.getFechaPrevalidacion());
        }


        String obsPreva = creditDescription.getObsPrevalidacion();
        if (obsPreva.equals("") || obsPreva == null){
            fieldObsPrevalidacion.setVisibility(View.GONE);
        }else{
            observacionPrevaText.setText(obsPreva);
        }

       String obsCredrit = creditDescription.getObservacionCredito();
        if (obsCredrit !=null){
            observacionCreditoText.setText(obsCredrit);
        }else{
            fieldObsCredito.setVisibility(View.GONE);
        }
    }

}
