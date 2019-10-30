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
    DatabaseReference databaseMenu;
    private List<Upload> menuList;
    private MenuDeleteAdapter mAdapter;
    private LinearLayout parentLinearLayout;
    ArrayAdapter<String> adapter2;
    Spinner spinner2;
    ArrayList<String> menList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_offline);

        Spinner spinner = findViewById(R.id.spinnerJumlah);
        spinner2 = findViewById(R.id.spinnerMakanan);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.many_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        menuList = new ArrayList<>();
        databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");
        menList = new ArrayList<>();
//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter<String>.createFromResource(this, android.R.layout.simple_spinner_dropdown_item,menList);
        adapter2 = new ArrayAdapter<String>(TransactionOffline.this, android.R.layout.simple_spinner_dropdown_item,menList);
        final List<String> menList = new ArrayList<String>();

        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload menu = postSnapshot.getValue(Upload.class);
                    menList.add(menu.getNameImage().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TransactionOffline.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, menList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
        spinner2.setOnItemSelectedListener(this);
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
    }
}
