package id.ac.polinema.tcttcakron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.polinema.tcttcakron.adapters.TransactionOfflineAdapter;
import id.ac.polinema.tcttcakron.models.KeranjangMenu;
import id.ac.polinema.tcttcakron.models.Upload;

public class TransactionOffline extends AppCompatActivity {
    DatabaseReference databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");
    DatabaseReference databaseOrder = FirebaseDatabase.getInstance().getReference("temp");
    private TransactionOfflineAdapter mAdapter;
    private LinearLayout parentLinearLayout;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<Upload> listMenu;
    ArrayList<KeranjangMenu> listMenuResult = new ArrayList<>();
    TextView jumlah;
    Spinner makanan, makanan2;
    TextView amount;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_offline);
        RelativeLayout placeholder = findViewById(R.id.list_layout_tr_off);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout holder = (RelativeLayout) inflate.inflate(R.layout.activity_menu_list, null);
        placeholder.addView(holder);

        mRecyclerView = findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progress_circle);
        makanan2 = findViewById(R.id.makanan_spinner2);
        jumlah = findViewById(R.id.total_tr_off);
        amount = findViewById(R.id.quantity_menu);
        submit = findViewById(R.id.buttonPesan_tr_off);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));
        jumlah.setText(getIntent().getStringExtra("total"));
        databaseOrder.setValue(null);
        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMenu = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload menu = postSnapshot.getValue(Upload.class);
                    listMenu.add(menu);
                }
                mAdapter = new TransactionOfflineAdapter(TransactionOffline.this, listMenu);
                mRecyclerView.setAdapter(mAdapter);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TransactionOffline.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        databaseOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(TransactionOffline.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String total = intent.getStringExtra("total");
            jumlah.setText(total);
        }
    };


    public void handlerOnClickBack(View view) {
        Intent intent = new Intent(this, FiturAdmin.class);
        startActivity(intent);
    }

    public void onDelete(View view) {
        parentLinearLayout.removeView((View) view.getParent());
    }
}