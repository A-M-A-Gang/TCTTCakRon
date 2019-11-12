package id.ac.polinema.tcttcakron.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.polinema.tcttcakron.R;
import id.ac.polinema.tcttcakron.models.KeranjangMenu;
import id.ac.polinema.tcttcakron.models.Order;
import id.ac.polinema.tcttcakron.models.Upload;

public class TransactionOnlineAdapter extends RecyclerView.Adapter<TransactionOnlineAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Order> mOrders;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<Order> listOrder;
    private List<KeranjangMenu> listMenu;
    private TransactionOnlineAdapter2 mAdapter2;

    public TransactionOnlineAdapter(Context context, List<Order> orders){
        mContext = context;
        mOrders = orders;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_transaction_online_adapter, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {
        final Order uploadCurrent = mOrders.get(position);
        holder.nama.setText(uploadCurrent.getNama());

//        RelativeLayout placeholder2 = itemView.findViewById(R.id.list_order_layout);

        LayoutInflater inflate2 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout holder2 = (RelativeLayout) inflate2.inflate(R.layout.activity_menu_list, null);
        holder.placeholder2.addView(holder2);

        mRecyclerView = holder2.findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mProgressBar = holder2.findViewById(R.id.progress_circle);
        listOrder = new ArrayList<>();
        listMenu = new ArrayList<>();

//        mDatabaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Order menu = postSnapshot.getValue(Order.class);
//                    listOrder.add(menu);
//                }
//                    for (int i = 0; i < listOrder.size(); i++){
//                        listMenu = listOrder.get(i).getFoods();
//                    }
//                    listMenu = listMenu.add(listOrder.get(1).getFoods());
                mAdapter2 = new TransactionOnlineAdapter2(mContext, mOrders.get(position).getFoods());
                mRecyclerView.setAdapter(mAdapter2);
                mProgressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                mProgressBar.setVisibility(View.INVISIBLE);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView nama;
        DatabaseReference mDatabaseRef;
        private RecyclerView mRecyclerView;
        private ProgressBar mProgressBar;


        RelativeLayout placeholder2;

        public ImageViewHolder(View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama_order);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Order");

            placeholder2 = itemView.findViewById(R.id.list_order_layout);
        }
    }
}
