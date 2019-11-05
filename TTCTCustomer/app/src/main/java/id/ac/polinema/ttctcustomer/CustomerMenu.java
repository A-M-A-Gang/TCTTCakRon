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

import id.ac.polinema.ttctcustomer.adapter.Customer;
import id.ac.polinema.ttctcustomer.adapter.CustomerAdapter;

public class CustomerMenu extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
    }
}