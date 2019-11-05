package id.ac.polinema.tcttcakron;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import id.ac.polinema.tcttcakron.adapters.ResultActivityAdapter;

public class ResultActivity extends AppCompatActivity {
    RelativeLayout placeholder;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<KeranjangMenu> menuList;
    DatabaseReference databaseMenu;
    private ResultActivityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mRecyclerView = findViewById(R.id.recycleview_menu);
        placeholder = findViewById(R.id.list_layout_delete);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout holder = (RelativeLayout) inflate.inflate(R.layout.activity_menu_list, null);
        placeholder.addView(holder);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progress_circle);
        menuList = new ArrayList<>();

        databaseMenu = FirebaseDatabase.getInstance().getReference("Order");
        for (KeranjangMenu keranjangMenu : menuList){

        }
    }
}
