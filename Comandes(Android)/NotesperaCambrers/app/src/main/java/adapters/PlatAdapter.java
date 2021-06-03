package adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesperacambrers.PresaComandes;
import com.example.notesperacambrers.R;

import java.util.ArrayList;

import model.LiniaComanda;
import model.Plat;

public class PlatAdapter extends RecyclerView.Adapter<PlatAdapter.ViewHolder>{

    private boolean mButtonActivated = false;
    private int mSessioId;
    private ArrayList<Plat> mCarta;
    private AppCompatActivity mParentActivity;
    OnSelectedItemListener mListener;

    public PlatAdapter(OnSelectedItemListener listener, int sessioId, ArrayList<Plat> carta, AppCompatActivity parentActivity, boolean newComanda){
        mSessioId = sessioId;
        mListener = listener;
        mCarta = carta;
        mParentActivity = parentActivity;
        mButtonActivated = newComanda;
    }

    public static interface OnSelectedItemListener {
        void onSelectedItem(Plat seleccionat);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.plat_layout;
        View filaView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(filaView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plat p = mCarta.get(position);
        holder.txvNomPlat.setText(p.getNom());
        holder.txvPreu.setText(p.getPreu()+"");

        Bitmap bmp = BitmapFactory.decodeByteArray(p.getStream_foto(), 0, p.getStream_foto().length);
        holder.imvPlat.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false));

        if (mButtonActivated){
            holder.btnRemovePlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (LiniaComanda lc: ((PresaComandes)mParentActivity).mComanda) {
                        if (lc.codi_plat == p.getCodi()){
                            lc.quantitat--;
                            if (lc.quantitat <= 0){
                                ((PresaComandes)mParentActivity).mComanda.remove(lc);
                            }
                            ((PresaComandes)mParentActivity).refreshComanda();
                            break;
                        }
                    }
                }
            });

            holder.btnAddPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean added = false;
                    for (LiniaComanda lc: ((PresaComandes)mParentActivity).mComanda) {
                        if (lc.codi_plat == p.getCodi()){
                            lc.quantitat++;
                            added = true;
                            break;
                        }
                    }
                    if(!added){
                        int numLinia = 1;
                        for (LiniaComanda lc: ((PresaComandes)mParentActivity).mComanda) {
                            if (lc.num > numLinia){
                                numLinia = lc.num+1;
                            }
                        }
                        LiniaComanda lc = new LiniaComanda();
                        lc.codi_plat = (int)p.getCodi();
                        lc.quantitat = 1;
                        lc.num = numLinia;
                        ((PresaComandes)mParentActivity).mComanda.add(lc);
                    }
                    ((PresaComandes)mParentActivity).refreshComanda();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mCarta != null) return mCarta.size();
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txvPreu;
        public TextView txvNomPlat;
        public ImageView imvPlat;
        private Button btnRemovePlat;
        private Button btnAddPlat;

        public ViewHolder(@NonNull View fila) {
            super(fila);
            txvPreu = fila.findViewById(R.id.txvPreu);
            txvNomPlat = fila.findViewById(R.id.txvNomPlat);
            imvPlat = fila.findViewById(R.id.imvPlat);
            btnRemovePlat = fila.findViewById(R.id.btnRemovePlat);
            btnAddPlat = fila.findViewById(R.id.btnAddPlat);
        }
    }
}
