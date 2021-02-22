package com.example.audiolibros;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Tarea extends AsyncTask<String,Integer,String> {


    Context context;
    Libro libro;
    Activity activity;
    public Tarea(Context context, Libro libro, Activity activity){
        this.context=context;
        this.libro=libro;
        this.activity=activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        iniciarServicio();
        denetenerServicio();
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void iniciarServicio(){
        Intent intent=new Intent(context,Servicio.class);
        intent.putExtra("servicioLibro",libro);
        activity.startForegroundService(intent);
    }
    public void denetenerServicio(){
        Intent intent=new Intent(context,Servicio.class);
        activity.stopService(intent);
    }
}
