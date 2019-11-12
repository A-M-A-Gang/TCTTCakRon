package id.ac.polinema.tcttcakron.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import id.ac.polinema.tcttcakron.R;
import id.ac.polinema.tcttcakron.models.KeranjangMenu;
import id.ac.polinema.tcttcakron.models.Order;

public class TransactionOnlineAdapter2 extends RecyclerView.Adapter<TransactionOnlineAdapter2.ImageViewHolder> {
    private Context mContext;
    private List<Order> mOrders;
    private List<KeranjangMenu> mMenu;

    public TransactionOnlineAdapter2(Context context, List<KeranjangMenu> orders){
        mContext = context;
        mMenu = orders;
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
//        List<KeranjangMenu> order = uploadCurrent.getFoods();
        holder.menu.setText(uploadCurrent.getNamaMenu());
    }

    @Override
    public int getItemCount() {
        return mMenu.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView menu, harga, jumlah;
        DatabaseReference mDatabaseRef;

        public ImageViewHolder(View itemView) {
            super(itemView);

            menu = itemView.findViewById(R.id.menu_order);
            harga = itemView.findViewById(R.id.harga_order);
            jumlah = itemView.findViewById(R.id.jumlah_order);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Order");
        }
    }
}