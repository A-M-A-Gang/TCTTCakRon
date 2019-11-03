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
import android.widget.AdapterView;
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
    Spinner makanan, makanan2, jumlah;
    TextView amount;
    int counter = 0;

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
        jumlah = findViewById(R.id.number_spinner);

        amount = findViewById(R.id.quantity_menu);

        listMenu = new ArrayList<>();

//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter<String>.createFromResource(this, android.R.layout.simple_spinner_dropdown_item,menList);


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
    }

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