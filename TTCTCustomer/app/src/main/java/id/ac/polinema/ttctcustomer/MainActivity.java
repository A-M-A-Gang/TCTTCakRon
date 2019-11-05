package id.ac.polinema.ttctcustomer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

import id.ac.polinema.ttctcustomer.adapter.CustomerAdapter;


public class MainActivity extends AppCompatActivity{
    DatabaseReference databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");
    private CustomerAdapter mAdapter;
    final Context context = this;
    List<String> menuList = new ArrayList<String>();
    private LinearLayout parentLinearLayout;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<Upload> listMenu;
    Spinner makanan, makanan2, jumlah;
    TextView amount;
    int counter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout placeholder = findViewById(R.id.list_layout_tr_off);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout holder = (RelativeLayout) inflate.inflate(R.layout.field, null);
        placeholder.addView(holder);

        mRecyclerView = findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = findViewById(R.id.progress_circle);


        amount = findViewById(R.id.quantity_menu);

        listMenu = new ArrayList<>();

        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload menu = postSnapshot.getValue(Upload.class);
                    listMenu.add(menu);
                }
                mAdapter = new CustomerAdapter(MainActivity.this, listMenu);
                mRecyclerView.setAdapter(mAdapter);
                mProgressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(CustomerMenu.this, "OK", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(CustomerMenu.this, "????????", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void handlerOnClickPesan (View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("Check");

        // set dialog message
        alertDialogBuilder
                .setMessage("Apakah yakin ingin memesan!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
//                        DeleteAdmin.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
}