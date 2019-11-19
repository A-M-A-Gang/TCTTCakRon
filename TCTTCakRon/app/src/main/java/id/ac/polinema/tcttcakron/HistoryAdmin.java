package id.ac.polinema.tcttcakron;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import id.ac.polinema.tcttcakron.adapters.HistoryListMenuAdapter;
import id.ac.polinema.tcttcakron.adapters.TransactionOfflineAdapter;
import id.ac.polinema.tcttcakron.models.KeranjangMenu;
import id.ac.polinema.tcttcakron.models.Report;
import id.ac.polinema.tcttcakron.models.ReportTotal;
import id.ac.polinema.tcttcakron.models.Upload;

public class HistoryAdmin extends AppCompatActivity {
    TextView dateFrom, dateTo, totalMenu, menuLaris;
    Button show, reset;
    private ProgressBar mProgressBar;
    private HistoryListMenuAdapter mAdapter;
    private RecyclerView mRecyclerView;

    Calendar calendar;
    DatePickerDialog dpd;

    private List<Upload> listMenu;

    DatabaseReference databaseReport = FirebaseDatabase.getInstance().getReference("Report");
    DatabaseReference databaseReportTotal = FirebaseDatabase.getInstance().getReference("Report Total");
    DatabaseReference databaseMenu = FirebaseDatabase.getInstance().getReference("Menu");
    List<Report> reports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_admin);
        dateFrom = findViewById(R.id.display_from_date);
        dateTo = findViewById(R.id.display_to_date);
        show = findViewById(R.id.button_show);
        reset = findViewById(R.id.button_clear);
        totalMenu = findViewById(R.id.total_menu_laris);
        menuLaris = findViewById(R.id.menu_laris);

        RelativeLayout placeholder = findViewById(R.id.list_history_menu);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout holder = (RelativeLayout) inflate.inflate(R.layout.activity_menu_list, null);
        placeholder.addView(holder);
        mRecyclerView = findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progress_circle);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        dateFrom.setText(formattedDate);
        dateTo.setText(formattedDate);



        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(HistoryAdmin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dateFrom.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(HistoryAdmin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        dateTo.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });




        reports = new ArrayList<>();
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseMenu.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listMenu = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Upload menu = postSnapshot.getValue(Upload.class);
                            listMenu.add(menu);
                        }
                        mAdapter = new HistoryListMenuAdapter(  HistoryAdmin.this, listMenu);
                        mRecyclerView.setAdapter(mAdapter);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(HistoryAdmin.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });

                databaseReport.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                        final Intent intent = new Intent("custom-message");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            final Report test = snapshot.getValue(Report.class);

                            for (final KeranjangMenu getFood : test.getFoods()){
                                System.out.println(getFood.getNamaMenu());


                                databaseReportTotal.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (hashMap.get(getFood.getNamaMenu()) != null){
                                            int asd = hashMap.get(getFood.getNamaMenu());
                                            int kok = asd + getFood.getJumlah();
                                            hashMap.put(getFood.getNamaMenu(), kok);
                                        } else {
                                            hashMap.put(getFood.getNamaMenu(), getFood.getJumlah());
                                        }

                                        ReportTotal reportTotal = new ReportTotal(getFood.getNamaMenu(), hashMap.get(getFood.getNamaMenu()));
                                        databaseReportTotal.child(getFood.getNamaMenu()).setValue(reportTotal);
                                        intent.putExtra(getFood.getNamaMenu(), String.valueOf(hashMap.get(getFood.getNamaMenu())));
                                        LocalBroadcastManager.getInstance(HistoryAdmin.this).sendBroadcast(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
//                                if (databaseReportTotal.child(getFood.getNamaMenu()) != null){
//                                    String temp = databaseReportTotal.child(getFood.getNamaMenu()).child("totalPenjualan").getKey();
//                                    ReportTotal reportTotal = new ReportTotal(getFood.getNamaMenu(), (getFood.getJumlah() + Integer.parseInt(temp)));
//                                    databaseReportTotal.child(getFood.getNamaMenu()).setValue(reportTotal);
//                                } else {
//                                    ReportTotal reportTotal = new ReportTotal(getFood.getNamaMenu(), getFood.getJumlah());
//                                    databaseReportTotal.child(getFood.getNamaMenu()).setValue(reportTotal);
//                                }
//                                final int[] total = new int[1];
//                                hashMap.put(getFood.getNamaMenu(), getFood.getJumlah());
//                                Intent intent = new Intent("custom-message");
//                                intent.putExtra("map", hashMap);

//                                BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//                                    @Override
//                                    public void onReceive(Context context, Intent intent) {
//                                        String total2 = intent.getStringExtra(getFood.getNamaMenu());
//                                        total[0] = Integer.parseInt(total2);
//                                    }
//                                };
//                                LocalBroadcastManager.getInstance(HistoryAdmin.this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message2"));
//
//                                int ju = getFood.getJumlah();

//                                ReportTotal reportTotal = new ReportTotal(getFood.getNamaMenu(), getFood.getJumlah());
//                                Query query = databaseReportTotal.orderByChild("namaMenu").equalTo(test.getNama());
//                                query.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                                        appleSnapshot.child("totalPenjualan").getValue(Double.class);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });

//                                total[0] += ju;
//
//                                intent.putExtra(getFood.getNamaMenu(), String.valueOf(total[0]));
//                                LocalBroadcastManager.getInstance(HistoryAdmin.this).sendBroadcast(intent);
//                                jumlah.setText(getIntent().getStringExtra("total"));
                            }
//                            reports.add(test);
//                            totalMenu.setText(test.getDate());
//                            menuLaris.setText(test.getNama());
                        }
//                        menuLaris.setText(reports.get(0).getNama());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
