package id.ac.polinema.tcttcakron.adapters;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import id.ac.polinema.tcttcakron.R;
import id.ac.polinema.tcttcakron.models.KeranjangMenu;

public class TransactionOnlineAdapter2 extends RecyclerView.Adapter<TransactionOnlineAdapter2.ImageViewHolder> {
    private Context mContext;
    private List<KeranjangMenu> mMenu;
    private String nama;
    int totalPerMenu = 0;

    public  TransactionOnlineAdapter2(Context context, List<KeranjangMenu> orders, String nNama){
        mContext = context;
        mMenu = orders;
        nama = nNama;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_transaction_online_adapter2, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        final KeranjangMenu uploadCurrent = mMenu.get(position);
        holder.menu.setText(uploadCurrent.getNamaMenu());
        holder.jumlah.setText(String.valueOf(uploadCurrent.getJumlah()));
        holder.harga.setText(String.valueOf(uploadCurrent.getHarga()));
        holder.totalPerMenu.setText(String.valueOf(uploadCurrent.getJumlah() * uploadCurrent.getHarga()));
        totalPerMenu += uploadCurrent.getJumlah() * uploadCurrent.getHarga();
        mContext.toString();
        Intent intent = new Intent(nama);
        intent.putExtra("total", String.valueOf(totalPerMenu));
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return mMenu.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView menu, harga, jumlah, totalPerMenu, total;
        DatabaseReference mDatabaseRef;

        public ImageViewHolder(View itemView) {
            super(itemView);

            menu = itemView.findViewById(R.id.menu_order);
            harga = itemView.findViewById(R.id.harga_order);
            jumlah = itemView.findViewById(R.id.jumlah_order);
            totalPerMenu = itemView.findViewById(R.id.total_menu);
            total = itemView.findViewById(R.id.total_biaya_order);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Order");
        }
    }
}