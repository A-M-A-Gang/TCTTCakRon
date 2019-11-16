package id.ac.polinema.tcttcakron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.polinema.tcttcakron.adapters.TransactionOfflineAdapter;
import id.ac.polinema.tcttcakron.adapters.TransactionOnlineAdapter;
import id.ac.polinema.tcttcakron.adapters.TransactionOnlineAdapter2;
import id.ac.polinema.tcttcakron.models.KeranjangMenu;
import id.ac.polinema.tcttcakron.models.Order;
import id.ac.polinema.tcttcakron.models.Upload;

public class TransactionOnline extends AppCompatActivity {
    DatabaseReference databaseMenu = FirebaseDatabase.getInstance().getReference("Order");
    private RecyclerView mRecyclerView;
    private List<Order> listOrder;
    private TransactionOnlineAdapter mAdapter;
    private TransactionOnlineAdapter2 mAdapter2;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_online);

        RelativeLayout placeholder = findViewById(R.id.list_order);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout holder = (RelativeLayout) inflate.inflate(R.layout.activity_menu_list, null);
        placeholder.addView(holder);

//        RelativeLayout placeholder2 = findViewById(R.id.list_order_layout);
//        LayoutInflater inflate2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final RelativeLayout holder2 = (RelativeLayout) inflate2.inflate(R.layout.activity_menu_list, null);
//        placeholder2.addView(holder2);

        mRecyclerView = findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progress_circle);


        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOrder = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Order menu = postSnapshot.getValue(Order.class);
                    listOrder.add(menu);
                }
                mAdapter = new TransactionOnlineAdapter(TransactionOnline.this, listOrder);
//                mAdapter2 = new TransactionOnlineAdapter2(TransactionOnline.this, listOrder);
                mRecyclerView.setAdapter(mAdapter);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TransactionOnline.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void handlerOnClickBack(View view) {
        Intent intent = new Intent(this, FiturAdmin.class);
        startActivity(intent);
    }
}
