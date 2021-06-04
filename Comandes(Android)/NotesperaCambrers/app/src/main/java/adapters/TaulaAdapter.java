package adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesperacambrers.MainActivity;
import com.example.notesperacambrers.PresaComandes;
import com.example.notesperacambrers.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.*;

public class TaulaAdapter extends RecyclerView.Adapter<TaulaAdapter.ViewHolder> {

    private static final int TIPUS_BUIDA = 0;
    private static final int TIPUS_MEVA = 1;
    private static final int TIPUS_NO_MEVA = 2;
    private static final int NO_COMANDA = -1;

    private int idxTaulaSeleccionada = -1;
    private int mSessioId;
    private List<Taula> mTaules;
    private AppCompatActivity mParentActivity;
    OnSelectedItemListener mListener;

    public TaulaAdapter(OnSelectedItemListener listener, int sessioId, ArrayList<Taula> taules, AppCompatActivity parentActivity){
        mSessioId = sessioId;
        mListener = listener;
        mTaules = taules;
        mParentActivity = parentActivity;
    }

    public static interface OnSelectedItemListener {
        void onSelectedItem(Taula seleccionada);
    }

    @Override
    public int getItemViewType(int position) {
        Taula t = mTaules.get(position);
        if (t.comanda_activa_id <= 0) return TIPUS_BUIDA;
        else{
            return t.es_meva?TIPUS_MEVA:TIPUS_NO_MEVA;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case TIPUS_BUIDA:      layout = R.layout.taula_layout_buida; break;
            case TIPUS_MEVA:  layout = R.layout.taula_layout_meva; break;
            case TIPUS_NO_MEVA:  layout = R.layout.taula_layout_no_meva; break;
            default:throw new RuntimeException("tipus no suportat");
        }
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Taula t = mTaules.get(position);
        holder.txvTaula.setText(t.taula_id+"");
        holder.txvCambrer.setText(t.nom_cambrer);
        if (getItemViewType(position)==TIPUS_MEVA && t.comanda_activa_id > 0){
            int progress = ((t.plats_preparats*100)/t.plats_totals);
            holder.pgbTaula.setProgress(progress);
        }
    }

    @Override
    public int getItemCount() {
        if (mTaules != null) return mTaules.size();
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txvTaula;
        public TextView txvCambrer;
        public ProgressBar pgbTaula;

        public ViewHolder(@NonNull View fila) {
            super(fila);
            txvTaula = fila.findViewById(R.id.txvTaula);
            txvCambrer = fila.findViewById(R.id.txvCambrer);
            pgbTaula = fila.findViewById(R.id.pgbTaula);

            pgbTaula.setMax(100);

            fila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int anticIdxSeleccionat = idxTaulaSeleccionada;;
                    idxTaulaSeleccionada = getAdapterPosition();
                    notifyItemChanged(anticIdxSeleccionat);
                    notifyItemChanged(idxTaulaSeleccionada);

                    Taula t = mTaules.get(idxTaulaSeleccionada);
                    moveToPresaComandes(t.comanda_activa_id);
                    Log.d("TAULES", "idxTaulaSeleccionada:"+idxTaulaSeleccionada);
                }
            });
            fila.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // Avisem al listener que algú a premut una taula
                    if (mTaules != null){
                        int idx = getAdapterPosition();
                        Taula t = mTaules.get(idx);
                        Log.d("TAULES", "longClick "+t.comanda_activa_id);
                        if (t.comanda_activa_id != NO_COMANDA){
                            // Obrim Dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(mParentActivity);

                            builder.setMessage("Vol buidar la taula seleccionada?")
                                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Buida Taula
                                            Log.d("ADAPTER","Sí");
                                            try{
                                                // Crida assíncrona per descarregar el JSON
                                                Observable.fromCallable(() -> {
                                                    //---------------- START OF THREAD ------------------------------------
                                                    // Això és el codi que s'executarà en un fil
                                                    Log.d("TAULAADAPTER", "Observable");
                                                    boolean buidada = BDUtils.buidarTaula(mSessioId, t);
                                                    return buidada;
                                                    //--------------- END OF THREAD-------------------------------------
                                                })
                                                        .subscribeOn(Schedulers.io())
                                                        .onErrorReturnItem(false)
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe((buidada) -> {
                                                            //-------------  UI THREAD ---------------------------------------
                                                            // El codi que tenim aquí s'executa només quan el fil
                                                            // ha acabat !! A més, aquest codi s'executa en el fil
                                                            // d'interfície gràfica.
                                                            if (buidada){
                                                                ((MainActivity)mParentActivity).getTaules();
                                                            }
                                                            else{
                                                                Log.d("error", "false");
                                                            }
                                                            //-------------  END OF UI THREAD ---------------------------------------
                                                        });
                                            }
                                            catch(Exception ex){
                                                Log.e("error","ex.toString()");
                                            }
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // No buidar-la
                                            Log.d("ADAPTER","No");
                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            ((MainActivity)mParentActivity).dialogObert = true;
                            dialog.show();
                        }
                    }
                    return true;
                }
            });
        }
    }

    private void moveToPresaComandes(int comandaId) {
        Intent comandesIntent = new Intent(mParentActivity, PresaComandes.class);
        Bundle b = new Bundle();
        b.putInt("comanda", comandaId);
        b.putInt("session", mSessioId);
        int taulaId = mTaules.get(idxTaulaSeleccionada).taula_id;
        b.putInt("taula", taulaId);
        comandesIntent.putExtras(b);
        mParentActivity.startActivity(comandesIntent);
    }
}
