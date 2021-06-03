package com.example.notesperacambrers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.LiniaComandaAdapter;
import adapters.PlatAdapter;
import adapters.TaulaAdapter;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.BDUtils;
import model.LiniaComanda;
import model.Plat;
import model.Taula;

public class PresaComandes extends AppCompatActivity implements PlatAdapter.OnSelectedItemListener, LiniaComandaAdapter.OnSelectedItemListener {

    private RecyclerView rcvPlats;
    private RecyclerView rcvLinies;
    private PlatAdapter mCartaAdapter;
    private LiniaComandaAdapter mComandaAdapter;

    private int mComandaId = -1;
    private int mSessionId = 0;
    private boolean mNewComanda = true;
    public ArrayList<LiniaComanda> mComanda;
    private ArrayList<Plat> mCarta;

    private Spinner spnCategories;
    private Button btnConfirm;
    private TextView txvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presa_comandes);

        Bundle b = getIntent().getExtras();
        mComandaId = -1; // or other values
        if(b != null){
            mComandaId = b.getInt("comanda");
            Log.d("PRESACOMANDES",mComandaId+"");
            mSessionId = b.getInt("session");
            Log.d("PRESACOMANDES",mSessionId+"");
            mNewComanda = (mComandaId <= 0);
        }

        txvTotal = findViewById(R.id.txvTotal);

        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMain();
            }
        });
        spnCategories= findViewById(R.id.spnCategories);
        rcvPlats = findViewById(R.id.rcvPlats);
        rcvPlats.setLayoutManager(new GridLayoutManager(this,3,RecyclerView.VERTICAL,false));
        rcvLinies = findViewById(R.id.rcvLinies);

        rcvLinies.setLayoutManager(new LinearLayoutManager(this));

        if (!mNewComanda) getComanda();
        else{
            mComanda = new ArrayList<LiniaComanda>();
            refreshComanda();
        }

        getCarta();

    }

    private void getComanda() {
        if (mSessionId > 0){
            try{
                // Crida assíncrona
                Observable.fromCallable(() -> {
                    //---------------- START OF THREAD ------------------------------------
                    // Això és el codi que s'executarà en un fil
                    Log.d("PRESACOMANDES", "Observable");
                    ArrayList<LiniaComanda> comanda = BDUtils.getComanda(mSessionId,mComandaId);
                    return comanda;
                    //--------------- END OF THREAD-------------------------------------
                })
                        .subscribeOn(Schedulers.io())
                        .onErrorReturnItem(new ArrayList<LiniaComanda>())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((comanda) -> {
                            //-------------  UI THREAD ---------------------------------------
                            // El codi que tenim aquí s'executa només quan el fil
                            // ha acabat !! A més, aquest codi s'executa en el fil
                            // d'interfície gràfica.
                            if (comanda != null && comanda.size() > 0){
                                mComanda = comanda;
                                refreshComanda();
                            }
                            else{
                                Log.d("error", "null");
                                moveToMain();
                            }
                            //-------------  END OF UI THREAD ---------------------------------------
                        });
            }
            catch(Exception ex){
                Log.e("error","ex.toString()");
                moveToMain();
            }
        }
        else{
            moveToMain();
        }
    }

    public void refreshComanda() {
        mComandaAdapter = new LiniaComandaAdapter(this,mSessionId, mComanda, this, mCarta);
        rcvLinies.setAdapter(mComandaAdapter);

        double preuTotal = 0;
        if(mComanda != null){
            for (LiniaComanda lc: mComanda) {
                double preuPlat = 0;
                if(mCarta != null) {
                    for (Plat p : mCarta) {
                        if (p.getCodi() == lc.codi_plat) {
                            preuPlat = p.getPreu();
                            break;
                        }
                    }
                }
                preuTotal += lc.quantitat * preuPlat;
            }
        }

        txvTotal.setText(preuTotal+"");
    }

    private void getCarta() {
        if (mSessionId > 0){
            try{
                // Crida assíncrona
                Observable.fromCallable(() -> {
                    //---------------- START OF THREAD ------------------------------------
                    // Això és el codi que s'executarà en un fil
                    Log.d("PRESACOMANDES", "Observable");
                    ArrayList<Plat> carta = BDUtils.getCarta(mSessionId);
                    return carta;
                    //--------------- END OF THREAD-------------------------------------
                })
                        .subscribeOn(Schedulers.io())
                        .onErrorReturnItem(new ArrayList<Plat>())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((carta) -> {
                            //-------------  UI THREAD ---------------------------------------
                            // El codi que tenim aquí s'executa només quan el fil
                            // ha acabat !! A més, aquest codi s'executa en el fil
                            // d'interfície gràfica.
                            if (carta != null && carta.size() > 0){
                                mCarta = carta;
                                mCartaAdapter = new PlatAdapter(this,mSessionId, mCarta, this, mNewComanda);
                                rcvPlats.setAdapter(mCartaAdapter);
                                refreshComanda();
//                                spnCategories.setAdapter(new SpinnerAdapter() {
//                                    @Override
//                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
//                                        return null;
//                                    }
//
//                                    @Override
//                                    public void registerDataSetObserver(DataSetObserver observer) {
//
//                                    }
//
//                                    @Override
//                                    public void unregisterDataSetObserver(DataSetObserver observer) {
//
//                                    }
//
//                                    @Override
//                                    public int getCount() {
//                                        return 0;
//                                    }
//
//                                    @Override
//                                    public Object getItem(int position) {
//                                        return null;
//                                    }
//
//                                    @Override
//                                    public long getItemId(int position) {
//                                        return 0;
//                                    }
//
//                                    @Override
//                                    public boolean hasStableIds() {
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public View getView(int position, View convertView, ViewGroup parent) {
//                                        return null;
//                                    }
//
//                                    @Override
//                                    public int getItemViewType(int position) {
//                                        return 0;
//                                    }
//
//                                    @Override
//                                    public int getViewTypeCount() {
//                                        return 0;
//                                    }
//
//                                    @Override
//                                    public boolean isEmpty() {
//                                        return false;
//                                    }
//                                });
                            }
                            else{
                                Log.d("error", "null");
                                moveToMain();
                            }
                            //-------------  END OF UI THREAD ---------------------------------------
                        });
            }
            catch(Exception ex){
                Log.e("error","ex.toString()");
                moveToMain();
            }
        }
        else{
            moveToMain();
        }
    }

    private void moveToMain() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public void onSelectedItem(Plat seleccionat) {

    }


    @Override
    public void onSelectedItem(LiniaComanda seleccionada) {

    }
}