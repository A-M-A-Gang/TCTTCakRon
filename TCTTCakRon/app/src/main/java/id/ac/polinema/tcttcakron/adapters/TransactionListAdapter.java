package id.ac.polinema.tcttcakron.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import id.ac.polinema.tcttcakron.R;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ImageViewHolder> {
    private Context mContext;
    private Map.Entry<String, Integer> mUploads;
    private String[] mTanggalMenu;
    private int mTotalTransaksi;

    public TransactionListAdapter(Context context, String[] tanggalMenu , int totalTransaksi){
        mContext = context;
        mTanggalMenu = tanggalMenu;
        mTotalTransaksi = totalTransaksi;
//        Map.Entry<String, Integer> mUploads2 = (Map.Entry<String, Integer>) new HashMap<String, Integer>();
//        Set<Map.Entry<String, Integer>> set = mUploads2.entrySet();

//        Map<Integer, String> mapFromSet = new HashMap<Integer, String>();
//        for(Map.Entry<String, Integer> entry : set)
//        {
//            mapFromSet.put(entry.getValue(), entry.getKey());
//        }
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_most_transaction, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
//        final Upload uploadCurrent = mUploads.get(position);
//        holder.tanggal.setText(mUploads.getKey());
//        holder.total.setText(mUploads.getValue());

        holder.tanggal.setText(mTanggalMenu[position]);
        holder.total.setText(String.valueOf(mTotalTransaksi));
//        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String total2 = intent.getStringExtra(uploadCurrent.getNameImage());
//                holder.total.setText(total2);
//            }
//        };
//        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));
    }

    @Override
    public int getItemCount() {
//        for (Map.Entry<String, Integer> entry : mUploads.){
//            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
//            {
//                maxEntry = entry;
//            }
//        }
        int ji = 0;
        for (int i = 0; i < mTanggalMenu.length; i++){
            if(mTanggalMenu[i] == null){
                ji = i;
                break;
            }
            ji = i;
        }
        return ji;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView tanggal, total;
        DatabaseReference mDatabaseRef;
        public ImageViewHolder(View itemView){
            super(itemView);

            tanggal = itemView.findViewById(R.id.date_transaction_most);
            total = itemView.findViewById(R.id.total_transaction_most);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("temp");
        }
    }
}
