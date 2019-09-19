package com.example.alphamobilecolombia.utils.extensions;

import android.content.Intent;
import android.util.Log;

import com.example.alphamobilecolombia.mvp.models.Persona;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CedulaQrAnalytics {
    private static byte[] keysArray = new byte[]{
            (byte)0x27,
            (byte)0x30,
            (byte)0x04,
            (byte)0xA0,
            (byte)0x00,
            (byte)0x0F,
            (byte)0x93,
            (byte)0x12,
            (byte)0xA0,
            (byte)0xD1,
            (byte)0x33,
            (byte)0xE0,
            (byte)0x03,
            (byte)0xD0,
            (byte)0x00,
            (byte)0xDf,
            (byte)0x00
    };

    private static final String SCAN = "scan";
    private static final String CANCELLED = "cancelled";
    private static final String FORMAT = "format";
    private static final String TEXT = "text";
    private static final String LOG_TAG = "BarcodeScanner";
    private ArrayList<String> als = new ArrayList<>();
    private short[] data3;
    Collection<String> TYPES = Arrays.asList("PDF417");

    public static Persona parse(Intent data) throws JSONException {
        //Implementación anterior
        JSONObject obj = new JSONObject();
        try {
            obj.put(TEXT, data.getStringExtra("SCAN_RESULT"));
            obj.put(FORMAT, data.getStringExtra("SCAN_RESULT_FORMAT"));
            obj.put(CANCELLED, false);
        } catch (JSONException ex) {
            Log.d(LOG_TAG, "JSONException " + ex.getMessage());
            ex.printStackTrace();


        }

        String nuevo = obj.getString("text").replace("\u0000", "|");
        String cadena = nuevo.replaceAll("\\s", "|");
        cadena = cadena.replaceAll("\\t", "|");
        cadena = cadena.replaceAll("\\|{2,}", " ");

        return parse(cadena.getBytes());
    }

    public static Persona parse(byte[] raw){
        String d = new String(raw);
        Persona p = new Persona();
        try
        {
            p.setCedula(d.substring(0, 9).trim());
            p.setApellido1(d.substring(9, 35).trim());
            p.setApellido2(d.substring(35, 61).trim());
            p.setNombre(d.substring(61, 91).trim());
            p.setGenero(d.substring(91, 92).trim());//(d.charAt(91));
            p.setFechaNacimiento(d.substring(92, 96)+"-"+d.substring(96, 98)+"-"+d.substring(98, 100));
            p.setFechaVencimiento(d.substring(100, 104)+"-"+d.substring(104, 106)+"-"+d.substring(106, 108));
        }
        catch (Exception e){
            e.printStackTrace();
            p = null;
        }
        return p;
    }

    public static Persona parse(String cadena){
        String[] d = cadena.split("\\s");
        Persona p = new Persona();
        try
        {
            String idDocuento;
            String contienellavePublica = d[1];
            String llavePublica;
            String numeroidentificacion;
            String nombre1;
            String nombre2;
            String apellido1;
            String apellido2;
            String genero;
            String factor;
            String fechaNaciemiento;

            if(contienellavePublica.equals("PubDSK_1") && d.length == 8){
                idDocuento = d[2];
                llavePublica = d[0];
                if(d.length == 8){
                    numeroidentificacion = d[3].replaceAll("[^0-9]", "");
                    if (numeroidentificacion.length()>=18){
                        String subNumero = Integer.toString(Integer.parseInt(numeroidentificacion.substring(8,18)));
                        numeroidentificacion = subNumero;
                    }
                }
                else{
                    numeroidentificacion = d[2].replaceAll("[^0-9]", "");
                    if (numeroidentificacion.length()>=18){
                        String subNumero = Integer.toString(Integer.parseInt(numeroidentificacion.substring(8,18)));
                        numeroidentificacion = subNumero;
                    }
                }

                nombre1 = d[5];
                nombre2 = d[6];
                apellido1 = d[3].replaceAll("[^A-Za-z]", "");
                apellido2 = d[4];
                genero = String.valueOf(d[7].charAt(1));
                factor = d[7].substring(16, 18);
                fechaNaciemiento = d[7].substring(2, 6) + "-" + d[7].substring(6, 8) + "-" + d[7].substring(8, 10);
            }
            else{
                idDocuento = d[1];
                llavePublica = d[0];
                if(d.length == 8){
                    numeroidentificacion = d[3].replaceAll("[^0-9]", "");
                    if (numeroidentificacion.length()>=18){
                        String subNumero = Integer.toString(Integer.parseInt(numeroidentificacion.substring(8,18)));
                        numeroidentificacion = subNumero;
                    }
                }
                else{
                    numeroidentificacion = d[2].replaceAll("[^0-9]", "");
                    if (numeroidentificacion.length()>=18){
                        String subNumero = Integer.toString(Integer.parseInt(numeroidentificacion.substring(8,18)));
                        numeroidentificacion = subNumero;
                    }
                }
                nombre1 = d[4];
                nombre2 = d[5];
                apellido1 = d[2];
                apellido2 = d[3];
                genero = String.valueOf(d[6].charAt(1));
                factor = d[6].substring(16, 18);
                fechaNaciemiento = d[6].substring(2, 6) + "-" + d[6].substring(6, 8) + "-" + d[6].substring(8, 10);
            }

            numeroidentificacion = numeroidentificacion.replaceAll("[^0-9]", "");
            nombre1 = nombre1.replaceAll("[^A-Za-z]", "");
            nombre2 = nombre2.replaceAll("[^A-Za-z]", "");
            apellido1 = apellido1.replaceAll("[^A-Za-z]", "");
            apellido2 = apellido2.replaceAll("[^A-Za-z]", "");

            p.setCedula(numeroidentificacion);
            p.setApellido1(apellido1);
            p.setApellido2(apellido2);
            p.setNombre(nombre1 + " " + nombre2);
            p.setGenero(genero);//.charAt(0)
            p.setFactorRh(factor);
            p.setFechaNacimiento(fechaNaciemiento);
            p.setFechaVencimiento("");


        }
        catch (Exception e){
            e.printStackTrace();
            p = null;
        }
        return p;
    }
}
