package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesperacambrers.R;

import java.util.ArrayList;

import model.LiniaComanda;
import model.Plat;

public class LiniaComandaAdapter extends RecyclerView.Adapter<LiniaComandaAdapter.ViewHolder>{

    private boolean mButtonActivated = false;
    private int mSessioId;
    private ArrayList<LiniaComanda> mComanda;
    private ArrayList<Plat> mCarta;
    private AppCompatActivity mParentActivity;
    OnSelectedItemListener mListener;

    public LiniaComandaAdapter(OnSelectedItemListener listener, int sessioId, ArrayList<LiniaComanda> comanda, AppCompatActivity parentActivity, ArrayList<Plat> carta){
        mSessioId = sessioId;
        mListener = listener;
        mComanda = comanda;
        mParentActivity = parentActivity;
        mCarta = carta;
    }

    public static interface OnSelectedItemListener {
        void onSelectedItem(LiniaComanda seleccionada);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.linia_comanda_layout;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LiniaComanda lc = mComanda.get(position);

        String st = "Desconegut";
        double preu = 0;
        if (mCarta != null){
            for (Plat pl: mCarta) {
                if (pl.getCodi() == lc.codi_plat){
                    st = pl.getNom();
                    preu = pl.getPreu();
                    break;
                }
            }
        }
        holder.txvPlat.setText(st);
        holder.txvPreuUn.setText(preu+"");
        holder.txvQuantitat.setText(lc.quantitat+"");

        double preuTotal = preu*lc.quantitat;
        holder.txvPreuTotal.setText(preuTotal+"");
    }

    @Override
    public int getItemCount() {
        if (mComanda != null) return mComanda.size();
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txvPreuUn;
        public TextView txvPlat;
        public TextView txvPreuTotal;
        public TextView txvQuantitat;

        public ViewHolder(@NonNull View fila) {
            super(fila);
            txvPreuUn = fila.findViewById(R.id.txvPreuUn);
            txvPlat = fila.findViewById(R.id.txvPlat);
            txvPreuTotal = fila.findViewById(R.id.txvPreuTotal);
            txvQuantitat = fila.findViewById(R.id.txvQuantitat);
        }
    }
}
