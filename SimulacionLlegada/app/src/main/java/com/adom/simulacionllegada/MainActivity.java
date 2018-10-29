package com.adom.simulacionllegada;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int clientes;
    private static int capacidad;
    private double tllegadaanterior=0;
    private double tsalidaanterior=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button entry = (Button) findViewById(R.id.Ingresar);

        entry.setOnClickListener(new View.OnClickListener() { // hago clic en el botón

            @Override
            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.capacidad);
                EditText text2 = (EditText)findViewById(R.id.clientes);
                clientes=Integer.parseInt(text.getText().toString());
                capacidad=Integer.parseInt(text2.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        Tabla tabla = new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla);

        for(int i = 1; i < clientes+1; i++)
        {
            ArrayList<String> elementos = new ArrayList<String>();
            double aleatorio1= Math.random();
            double aleatorio2= Math.random();
            double tllegada1=tllegada(aleatorio1,clientes);
            double tatencion1=tatencion(aleatorio2,capacidad);
            double mllegada1=mllegada(tllegada1,tllegadaanterior,i);
            double tiservicio1=tiservicio(i,mllegada1,tsalidaanterior);
            double tespera1=tespera(tiservicio1,mllegada1);
            double tsalida1=tsalida(mllegada1,tatencion1,tespera1);
            tllegadaanterior=mllegada1;
            tsalidaanterior=tsalida1;
            elementos.add(Integer.toString(i));
            elementos.add(Double.toString(aleatorio1));
            elementos.add(Double.toString(aleatorio2));
            elementos.add(Double.toString(tllegada1));
            elementos.add(Double.toString(mllegada1));
            elementos.add(Double.toString(tiservicio1));
            elementos.add(Double.toString(tespera1));
            elementos.add(Double.toString(tsalida1));
            elementos.add(Double.toString(tatencion1));
            tabla.agregarFilaTabla(elementos);
        }

    }
    public double tllegada(double aleatorio1, int clientes){
        return -Math.log(1-aleatorio1)*60*1/clientes;
    }
    public double mllegada(double tllegada,double tllegadaanterior,int cliente){
        if(cliente==1){
            return tllegada;
        }else{
            return tllegada+tllegadaanterior;
        }
    }
    public double tatencion(double aleatorio2, int capacidad){
        return -Math.log(1-aleatorio2)*1/capacidad*60;
    }
    public double tsalida(double mllegada,double tatencion,double tespera){
        return (mllegada+tatencion)+tespera;
    }

    public double tiservicio(int cliente,double mllegada,double tsalidaanterior){
        if(cliente==1){
            return mllegada;
        }else{
            return tsalidaanterior;
        }
    }
    public double tespera(double tiservicio, double mllegada){
        return tiservicio-mllegada;
    }
}
