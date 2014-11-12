package com.codigoprogramacion.devf_android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.codigoprogramacion.devf_android.api.GitHubService;
import com.codigoprogramacion.devf_android.modelos.GitHubUser;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;


public class RetrofitActivity extends Activity {

    GitHubUser usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
    }

    @Override
    protected void onStart(){
        super.onStart();
        //probemos RetroFit

        //Constructor GSON
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class,new DateTypeAdapter())
                .create();

        //Creamos RestAdapter conectado con github
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .setConverter(new GsonConverter(gson))
                .build();

        //Generamos el servicio implementando el adaptador rest
        final GitHubService gitHubService = restAdapter.create(GitHubService.class);

        //Creamos un nuevo WorkerThread
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Se traduce directo a usuario github gracias a GSON Builder
                    usuario = gitHubService.getUser("xymind");
                    updateGUI();

                } catch (RetrofitError e) {
                    //Error de tipo retrofit pueden ser codigos HTTP 400,403, 500 etc..
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void updateGUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.someText))
                        .setText(usuario.getName() + " " +
                                usuario.getEmail() + " " +
                                usuario.getFollowers());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.retrofit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
