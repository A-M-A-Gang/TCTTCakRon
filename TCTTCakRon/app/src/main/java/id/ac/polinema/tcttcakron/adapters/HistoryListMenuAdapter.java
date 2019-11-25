package id.ac.polinema.tcttcakron.adapters;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import id.ac.polinema.tcttcakron.R;
import id.ac.polinema.tcttcakron.models.Upload;

public class HistoryListMenuAdapter extends RecyclerView.Adapter<HistoryListMenuAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public HistoryListMenuAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_history_list_menu_adapter, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        final Upload uploadCurrent = mUploads.get(position);
        holder.nama.setText(uploadCurrent.getNameImage());
        holder.total.setText("0");
        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String total2 = intent.getStringExtra(uploadCurrent.getNameImage());
                holder.total.setText(total2);
            }
        };
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, total;
        DatabaseReference mDatabaseRef;
        public ImageViewHolder(View itemView){
            super(itemView);

            nama = itemView.findViewById(R.id.date_transaction_most);
            total = itemView.findViewById(R.id.total_transaction_most);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("temp");
        }
    }
}
