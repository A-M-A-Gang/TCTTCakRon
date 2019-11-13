package id.ac.polinema.tcttcakron;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.polinema.tcttcakron.adapters.ResultActivityAdapter;
import id.ac.polinema.tcttcakron.models.KeranjangMenu;
import id.ac.polinema.tcttcakron.models.Order;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<KeranjangMenu> menuList;
    DatabaseReference databaseMenu, newOrder;
    private ResultActivityAdapter mAdapter;
    TextView totalBelanja;
    Button pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        RelativeLayout placeholder = findViewById(R.id.list_result_menu);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout holder = (RelativeLayout) inflate.inflate(R.layout.activity_menu_list, null);
        placeholder.addView(holder);

        mRecyclerView = findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuList = new ArrayList<>();
        mProgressBar = findViewById(R.id.progress_circle);
        totalBelanja = findViewById(R.id.total_result);
        pesan = findViewById(R.id.order_result);
        databaseMenu = FirebaseDatabase.getInstance().getReference("temp");
        newOrder = FirebaseDatabase.getInstance().getReference("Order");
        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    KeranjangMenu menu = postSnapshot.getValue(KeranjangMenu.class);
                    menuList.add(menu);
                }
                mAdapter = new ResultActivityAdapter(ResultActivity.this, menuList);
                mRecyclerView.setAdapter(mAdapter);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));
        totalBelanja.setText(getIntent().getStringExtra("total"));
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ResultActivity.this);
        alertDialog.setTitle("Satu langkah lagi!");
        alertDialog.setMessage("Masukkan nama: ");
        final EditText nama = new EditText(ResultActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        nama.setLayoutParams(lp);
        alertDialog.setView(nama);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Order order = new Order(nama.getText().toString(), menuList);
                newOrder.child(nama.getText().toString()).setValue(order);
                databaseMenu.removeValue();
                finish();
            }
        });
        alertDialog.show();
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String total = intent.getStringExtra("total");
            totalBelanja.setText(total);
        }
    };
}
