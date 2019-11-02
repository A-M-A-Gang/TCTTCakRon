package id.ac.polinema.tcttcakron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.polinema.tcttcakron.adapters.MenuDeleteAdapter;

public class TransactionOffline extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseReference databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");
    List<String> menuList = new ArrayList<String>();
    private MenuDeleteAdapter mAdapter;
    private LinearLayout parentLinearLayout;
    Spinner makanan, makanan2, jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_offline);
        makanan = findViewById(R.id.makanan_spinner);
        makanan2 = findViewById(R.id.makanan_spinner2);
        jumlah = findViewById(R.id.number_spinner);
        parentLinearLayout =  findViewById(R.id.parent_linear_layout);

//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter<String>.createFromResource(this, android.R.layout.simple_spinner_dropdown_item,menList);


        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload menu = postSnapshot.getValue(Upload.class);
                    menuList.add(menu.getNameImage());
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter(TransactionOffline.this, android.R.layout.simple_spinner_item, menuList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    makanan.setAdapter(dataAdapter);
//                    spinner2.setOnItemSelectedListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TransactionOffline.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handlerOnClickBack(View view) {
        Intent intent = new Intent(this, FiturAdmin.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
