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
import android.widget.AdapterView;
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

public class TransactionOffline extends AppCompatActivity {
    DatabaseReference databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");
    private TransactionOfflineAdapter mAdapter;
    List<String> menuList = new ArrayList<String>();
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
        RelativeLayout holder = (RelativeLayout) inflate.inflate(R.layout.activity_menu_list, null);
        placeholder.addView(holder);

        mRecyclerView = findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progress_circle);
        makanan2 = findViewById(R.id.makanan_spinner2);
        jumlah = findViewById(R.id.total_tr_off);
        amount = findViewById(R.id.quantity_menu);
        submit = findViewById(R.id.buttonPesan_tr_off);
        listMenu = new ArrayList<>();

//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter<String>.createFromResource(this, android.R.layout.simple_spinner_dropdown_item,menList);
        Intent intent = new Intent(this, TransactionOfflineAdapter.class);
        Bundle extras = getIntent().getExtras();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));
        jumlah.setText(getIntent().getStringExtra("total"));
        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int childCount = mRecyclerView.getChildCount(), i = 0; i < childCount; ++i) {
                    final RecyclerView.ViewHolder holder2 = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
                    String namaMenu = holder2.itemView.findViewById(R.id.nama_menu).toString();
                    String harga = holder2.itemView.findViewById(R.id.harga_menu).toString();
                    String jumlah = holder2.itemView.findViewById(R.id.quantity_menu).toString();

                    listMenuResult.add(new KeranjangMenu(namaMenu, Integer.parseInt(harga), Integer.parseInt(jumlah)));
                }
                Intent intent = new Intent(TransactionOffline.this, ResultActivity.class);
                startActivity(intent);
            }
        });


    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
//            String ItemName = intent.getStringExtra("item");
//            String qty = intent.getStringExtra("quantity");
            String total = intent.getStringExtra("total");
//            Toast.makeText(TransactionOffline.this,ItemName + " " + qty ,Toast.LENGTH_SHORT).show();
            jumlah.setText(total);
        }
    };


    public void handlerOnClickBack(View view) {
        Intent intent = new Intent(this, FiturAdmin.class);
        startActivity(intent);
    }

    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field_transaction_offline, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);


//        databaseMenu.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Upload menu = postSnapshot.getValue(Upload.class);
//                    menuList.add(menu.getNameImage());
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter(TransactionOffline.this, android.R.layout.simple_spinner_item, menuList);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    makanan2.setAdapter(dataAdapter);
//                    spinner2.setOnItemSelectedListener(this);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(TransactionOffline.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void onDelete(View view) {
        parentLinearLayout.removeView((View) view.getParent());
    }
}