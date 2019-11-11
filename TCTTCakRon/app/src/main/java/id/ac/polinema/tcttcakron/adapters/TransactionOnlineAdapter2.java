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
import id.ac.polinema.tcttcakron.models.Order;

public class TransactionOnlineAdapter2 extends RecyclerView.Adapter<TransactionOnlineAdapter2.ImageViewHolder> {
    private Context mContext;
    private List<Order> mOrders;

    public TransactionOnlineAdapter2(Context context, List<Order> orders){
        mContext = context;
        mOrders = orders;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_transaction_online_adapter2, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        final Order uploadCurrent = mOrders.get(position);
        holder.nama.setText(uploadCurrent.getNama());
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView nama;
        DatabaseReference mDatabaseRef;

        public ImageViewHolder(View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama_order);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Order");
        }
    }
}