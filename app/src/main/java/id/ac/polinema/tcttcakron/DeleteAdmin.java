package id.ac.polinema.tcttcakron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteAdmin extends AppCompatActivity {
    final Context context = this;
    ImageView image;
    TextView nama, harga;
    private List<Upload> menu = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MenuDeleteAdapter mAdapter;
    private List<Upload> menuList;
    private ProgressBar mProgressBar;

    DatabaseReference databaseMenu;
    ListView listViewMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        mRecyclerView = findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = findViewById(R.id.progress_circle);
        menuList = new ArrayList<>();

        databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");

        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload menu = postSnapshot.getValue(Upload.class);
                    menuList.add(menu);
                }
                mAdapter = new MenuDeleteAdapter(DeleteAdmin.this, menuList);

                mRecyclerView.setAdapter(mAdapter);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DeleteAdmin.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

//        image = findViewById(R.id.imageView);
//        nama = findViewById(R.id.textMakanan);
//        harga = findViewById(R.id.textHarga);
//        listViewMenu = findViewById(R.id.listView);
//        menuList = new ArrayList<>();

//        new FirebaseDatabaseHelper().readMenu(new FirebaseDatabaseHelper.DataStatus() {
//            @Override
//            public void DataIsLoaded(List<Upload> upload, List<String> keys) {
//                new RecyclerView_Config().setConfig(mRecyclerView, DeleteAdmin.this, menu, keys);
//            }
//
//            @Override
//            public void DataIsInserted() {
//
//            }
//
//            @Override
//            public void DataIsUpdated() {
//
//            }
//
//            @Override
//            public void DataIsDeleted() {
//
//            }
//        });
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("Menu/Makanan");

        // Attach a listener to read the data at our posts reference
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Upload post = dataSnapshot.getValue(Upload.class);
//                System.out.println(post);
//                Toast.makeText(DeleteAdmin.this, "Read", Toast.LENGTH_SHORT).show();
//                nama.setText(post.getHarga());
//                harga.setText(post.getHarga());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });

    }

//    protected void onStart(){
//        super.onStart();
//
//        databaseMenu.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                menuList.clear();
//                for (DataSnapshot menuSnapshot : dataSnapshot.getChildren()){
//                    Upload menu = menuSnapshot.getValue(Upload.class);
//                    menuList.add(menu);
//                }
//                MenuList adapter = new MenuList(DeleteAdmin.this, menuList);
//                listViewMenu.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void handlerOnClickBack(View view) {
        Intent intent = new Intent(this, FiturAdmin.class);
        startActivity(intent);
    }

    public void handlerOnClickDelete(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("Check");

        // set dialog message
        alertDialogBuilder
                .setMessage("Apakah yakin ingin menghapus!")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
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
}