package id.ac.polinema.tcttcakron.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.polinema.tcttcakron.models.KeranjangMenu;
import id.ac.polinema.tcttcakron.R;

public class ResultActivityAdapter extends RecyclerView.Adapter<ResultActivityAdapter.ImageViewHolder>{
    private Context mContext;
    private List<KeranjangMenu> listMenu;
    int jumlah_harga = 0;

    public ResultActivityAdapter(Context context, List<KeranjangMenu> list){
        mContext = context;
        listMenu = list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.menu_list_result, parent, false);
        return new ResultActivityAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        final KeranjangMenu uploadCurrent = listMenu.get(position);
        holder.jumlah.setText(String.valueOf(uploadCurrent.getJumlah()));
        holder.menu.setText(uploadCurrent.getNamaMenu());
        holder.harga.setText(String.valueOf(uploadCurrent.getHarga()));
        jumlah_harga += (uploadCurrent.getJumlah() * uploadCurrent.getHarga());
        Intent intent = new Intent("custom-message");
        intent.putExtra("total", String.valueOf(jumlah_harga));
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView jumlah, menu, harga, total;
        public ImageViewHolder(View view){
            super(view);
            jumlah = view.findViewById(R.id.jumlah_result);
            menu = view.findViewById(R.id.menu_result);
            harga = view.findViewById(R.id.harga_result);
            total = view.findViewById(R.id.total_result);
        }
    }
}
