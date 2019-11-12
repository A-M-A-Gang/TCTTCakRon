package id.ac.polinema.tcttcakron.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

        mAdapter2 = new TransactionOnlineAdapter2(mContext, mOrders.get(position).getFoods(), uploadCurrent.getNama());
        mRecyclerView.setAdapter(mAdapter2);
        mProgressBar.setVisibility(View.INVISIBLE);

        holder.selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                // set title
                alertDialogBuilder.setTitle("Check");
                alertDialogBuilder
                        .setMessage("Apakah yakin sudah selesai!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                                Query applesQuery = databaseRef.child("Order").orderByChild("nama").equalTo(uploadCurrent.getNama());
                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(mContext, "failed database", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                // current activity
//                        DeleteAdmin.this.finish();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
//            String ItemName = intent.getStringExtra("item");
//            String qty = intent.getStringExtra("quantity");
                String total2 = intent.getStringExtra("total");
//            Toast.makeText(TransactionOffline.this,ItemName + " " + qty ,Toast.LENGTH_SHORT).show();
                holder.total.setText(total2);
            }
        };

        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver, new IntentFilter(uploadCurrent.getNama()));
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, total;
        Button selesai;
        DatabaseReference mDatabaseRef;
        private RecyclerView mRecyclerView;
        private ProgressBar mProgressBar;
        RelativeLayout placeholder2;

        public ImageViewHolder(View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama_order);
            selesai = itemView.findViewById(R.id.delete_order);
            total = itemView.findViewById(R.id.total_biaya_order);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("Order");
            placeholder2 = itemView.findViewById(R.id.list_order_layout);
        }
    }


}
