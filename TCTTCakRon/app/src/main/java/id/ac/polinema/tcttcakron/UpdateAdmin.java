package id.ac.polinema.tcttcakron;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.ac.polinema.tcttcakron.adapters.MenuUpdateAdapter;
import id.ac.polinema.tcttcakron.models.MenuUpdate;

public class UpdateAdmin extends AppCompatActivity {
    final Context context = this;
    RecyclerView rvMenu;
    List<Upload> listMenu = new ArrayList<>();
    private List<Upload> menu = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MenuUpdateAdapter mAdapter;
//    private MenuDeleteAdapter mAdapter;
    private List<Upload> menuListUpdate;
    private ProgressBar mProgressBar;
    //instansiasi Recyclerview
    RecyclerView rvSuperHero;
    //instansiasi list superhero
    List<MenuUpdate> listSuperHero = new ArrayList<>();

    DatabaseReference databaseMenu;
    ListView listViewMenu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin);
        RelativeLayout placeholder = findViewById(R.id.list_layout_update);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout holder = (RelativeLayout) inflate.inflate(R.layout.activity_menu_list, null);
        placeholder.addView(holder);

        mRecyclerView = findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = findViewById(R.id.progress_circle);
        menuListUpdate = new ArrayList<>();

        databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");
//        //menyambungkan rvSuperHero ke layout
//        rvSuperHero = findViewById(R.id.recycleview_menulist);
//        //Membuat object hero
//        menuListUpdate = new ArrayList<>();
//
//        databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");

        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload menu = postSnapshot.getValue(Upload.class);
                    menuListUpdate.add(menu);
                }

                mAdapter = new MenuUpdateAdapter(UpdateAdmin.this, menuListUpdate);

//                MenuUpdateAdapter superHeroAdapter = new MenuUpdateAdapter(UpdateAdmin.this,menuListUpdate);

                mRecyclerView.setAdapter(mAdapter);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateAdmin.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void handlerOnClickBack(View view) {
        Intent intent = new Intent(this, FiturAdmin.class);
        startActivity(intent);
    }
    public void handlerOnClickUpdate(View view) {
//        Intent intent = new Intent(this, UpdateMenuAdmin.class);
//        startActivity(intent);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("Check");

        // set dialog message
        alertDialogBuilder
                .setMessage("Apakah yakin ingin mengupdate!")
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
