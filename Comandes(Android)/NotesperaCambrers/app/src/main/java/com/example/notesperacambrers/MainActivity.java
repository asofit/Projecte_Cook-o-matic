package com.example.notesperacambrers;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import adapters.TaulaAdapter;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.BDUtils;
import model.Login;
import model.Taula;

public class MainActivity extends AppCompatActivity implements TaulaAdapter.OnSelectedItemListener {

    private int session_id;

    private RecyclerView rcvTaules;
    private TaulaAdapter mTaulaAdapter;

    public boolean dialogObert = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvTaules = findViewById(R.id.rcvTaules);
        rcvTaules.setLayoutManager(new GridLayoutManager(this,2,RecyclerView.VERTICAL,false));
//        rcvTaules.setLayoutManager(new LinearLayoutManager(this));

        readSessionFromSp();


    }

    private void readSessionFromSp() {
        SharedPreferences sp = getSharedPreferences("TAULES",MODE_PRIVATE);
        session_id = sp.getInt("session",0);
        Log.d("TAULES", String.valueOf(session_id));

        getTaules();
    }

    public void getTaules() {
        if (session_id > 0){
            try{
                // Crida assíncrona per descarregar el JSON
                Observable.fromCallable(() -> {
                    //---------------- START OF THREAD ------------------------------------
                    // Això és el codi que s'executarà en un fil
                    Log.d("TAULAADAPTER", "Observable");
                    ArrayList<Taula> taules = BDUtils.getTaules(session_id);
                    return taules;
                    //--------------- END OF THREAD-------------------------------------
                })
                        .subscribeOn(Schedulers.io())
                        .onErrorReturnItem(new ArrayList<Taula>())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((taules) -> {
                            //-------------  UI THREAD ---------------------------------------
                            // El codi que tenim aquí s'executa només quan el fil
                            // ha acabat !! A més, aquest codi s'executa en el fil
                            // d'interfície gràfica.
                            if (taules != null && taules.size() > 0){
                                mTaulaAdapter = new TaulaAdapter(this,session_id, taules, this);
                                rcvTaules.setAdapter(mTaulaAdapter);
                            }
                            else{
                                Log.d("error", "null");
                            }
                            //-------------  END OF UI THREAD ---------------------------------------
                        });
            }
            catch(Exception ex){
                Log.e("error","ex.toString()");
            }
        }
        else{
            mTaulaAdapter = new TaulaAdapter(this,session_id, new ArrayList<Taula>(), this);
            rcvTaules.setAdapter(mTaulaAdapter);
        }
    }

    @Override
    public void onSelectedItem(Taula seleccionada) {
        Log.d("TAULES", "selectedTaula");
    }
}