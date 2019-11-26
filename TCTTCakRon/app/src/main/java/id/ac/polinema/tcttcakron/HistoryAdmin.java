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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.polinema.tcttcakron.adapters.HistoryListMenuAdapter;
import id.ac.polinema.tcttcakron.adapters.TransactionListAdapter;
import id.ac.polinema.tcttcakron.adapters.TransactionOfflineAdapter;
import id.ac.polinema.tcttcakron.models.KeranjangMenu;
import id.ac.polinema.tcttcakron.models.Report;
import id.ac.polinema.tcttcakron.models.ReportTotal;
import id.ac.polinema.tcttcakron.models.Upload;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class HistoryAdmin extends AppCompatActivity {
    TextView dateFrom, dateTo, totalMenu, menuLaris, tanggalTr, totalTr;
    Button show, reset, hari3, minggu, bulanini, hari30, bulan3, tahunini, tahun1;
    private ProgressBar mProgressBar;
    private HistoryListMenuAdapter mAdapter;
    private TransactionListAdapter mAdapter2;
    private RecyclerView mRecyclerView, mRecyclerView2;

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
        hari3 = findViewById(R.id.hari3_button);
        minggu = findViewById(R.id.minggu_button);
        bulanini = findViewById(R.id.bulan_button);
        hari30 = findViewById(R.id.hari30_button);
        bulan3 = findViewById(R.id.bulan3_button);
        tahunini = findViewById(R.id.tahun_button);
        tahun1 = findViewById(R.id.tahun1_button);

        RelativeLayout placeholder = findViewById(R.id.list_history_menu);
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout holder = (RelativeLayout) inflate.inflate(R.layout.activity_menu_list, null);
        placeholder.addView(holder);
        mRecyclerView = findViewById(R.id.recycleview_menu);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = findViewById(R.id.progress_circle);

//        RelativeLayout placeholder2 = findViewById(R.id.list_transaksi_menu);
//        LayoutInflater inflate2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final RelativeLayout holder2 = (RelativeLayout) inflate2.inflate(R.layout.activity_menu_list, null);
//        placeholder2.addView(holder2);
//        mRecyclerView2 = findViewById(R.id.recycleview_menu);
//        mRecyclerView2.setHasFixedSize(true);
//        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));

        final Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        dateFrom.setText(formattedDate);
        dateTo.setText(formattedDate);

        reports = new ArrayList<>();

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


        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                databaseReportTotal.setValue(null);
                String strDateFrom = dateFrom.getText().toString();
                String strDateTo = dateTo.getText().toString();
                final DateTime dateFrom= convertToDateTime(strDateFrom);
                final DateTime dateTo = convertToDateTime(strDateTo);
                if (dateTo.compareTo(dateFrom) < 0) {
                    Toast.makeText(HistoryAdmin.this, "Invalid!", Toast.LENGTH_SHORT).show();
                } else {
//                        displayCurrentBirthday(todayDateTime, birthdayDateTime);
//                        displayNextBirthday(todayDateTime, birthdayDateTime);

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
                            String[] tanggalMenu = new String[10];
//                            int[] jumlahTransaksi;
                            int totalTransaksi = 0;
                            int counter = 0, counterloop = 0;

                            String dateTemp = null;
                            final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                            HashMap<String, Integer> tanggal = new HashMap<>();
                            final Intent intent = new Intent("custom-message");
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                                final Report test = snapshot.getValue(Report.class);

                                try {
                                    SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    Date dateFromData = sdfSource.parse(test.getDate());
                                    SimpleDateFormat sdfDestination = new SimpleDateFormat("dd/MM/yyyy");
                                    String strDateData = sdfDestination.format(dateFromData);
                                    counter++;
                                    if (dateTemp == null || !dateTemp.equals(strDateData)){
                                        System.out.println("1");
                                        dateTemp = strDateData;
                                        counter = 1;
                                        if (tanggalMenu[0] == null){
                                            tanggalMenu[0] = strDateData;
                                        }
                                    } else if (totalTransaksi < counter) {
                                        tanggalMenu = new String[10];
                                        counterloop = 0;
                                        System.out.println("2");
                                        totalTransaksi = counter;
                                        tanggalMenu[counterloop] = strDateData;
                                    } else if (totalTransaksi == counter){
                                        System.out.println("3");
                                        counterloop++;
                                        tanggalMenu[counterloop] = strDateData;
                                    } else {
                                        System.out.println("4");
                                    }
                                    DateTime dateData= convertToDateTime(strDateData);
                                    System.out.println(dateData);

                                    if (dateData.compareTo(dateFrom) < 0 || dateData.compareTo(dateTo) > 0) {
//                                        Toast.makeText(HistoryAdmin.this, "Invalid!", Toast.LENGTH_SHORT).show();
                                    } else {
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

                                                    Map.Entry<String, Integer> firstEntry = hashMap.entrySet().iterator().next();
                                                    String largestKey = firstEntry.getKey();
                                                    int largestKeyValue = firstEntry.getValue();

                                                    for (Map.Entry<String, Integer> map : hashMap.entrySet()) {
                                                        int key = map.getValue();
                                                        if (key > largestKeyValue) {
                                                            largestKeyValue = key;
                                                            largestKey = map.getKey();
                                                        }
                                                    }
                                                    System.out.println("Largest Key       : " + largestKey);
                                                    System.out.println("Largest Key Value : " + largestKeyValue);

                                                    totalMenu.setText(String.valueOf(largestKeyValue));
                                                    menuLaris.setText(largestKey);

                                                    ReportTotal reportTotal = new ReportTotal(getFood.getNamaMenu(), hashMap.get(getFood.getNamaMenu()));
                                                    databaseReportTotal.child(getFood.getNamaMenu()).setValue(reportTotal);
                                                    intent.putExtra(getFood.getNamaMenu(), String.valueOf(hashMap.get(getFood.getNamaMenu())));
                                                    LocalBroadcastManager.getInstance(HistoryAdmin.this).sendBroadcast(intent);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
//                                Map.Entry<String, Integer> maxEntry = null;
//                                for (Map.Entry<String, Integer> entry : tanggal.entrySet()){
//                                    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
//                                    {
//                                        maxEntry = entry;
//                                    }
//                                }
//                                mAdapter2 = new TransactionListAdapter(  HistoryAdmin.this, maxEntry);
//                                mRecyclerView2.setAdapter(mAdapter2);
//                                mProgressBar.setVisibility(View.INVISIBLE);
                            }

                            for (int i = 0; i < tanggalMenu.length; i++){
                                if(tanggalMenu[i] == null){
                                    break;
                                }
                                System.out.println("Tanggal: " + tanggalMenu[i]);
                            }
                            System.out.println("Total: " + totalTransaksi);
                            System.out.println("Counter: " + counter);
                            System.out.println("Counterloop: " + counterloop);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        hari3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(c.getTime() - ((24*60*60*1000) * 3));
                dateFrom.setText(formattedDate);
                show.performClick();
            }
        });

        minggu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(c.getTime() - ((24*60*60*1000) * 7));
                dateFrom.setText(formattedDate);
                show.performClick();
            }
        });

        bulanini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH, 1);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String strDateData = df.format(c.getTime());
                dateFrom.setText(strDateData);
                show.performClick();
            }
        });

        hari30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, -1);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(c.getTime());
                dateFrom.setText(formattedDate);
                show.performClick();
            }
        });

        bulan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, -3);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(c.getTime());
                dateFrom.setText(formattedDate);
                show.performClick();
            }
        });

        tahunini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.set(Calendar.MONTH, 0);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String strDateData = df.format(c.getTime());
                dateFrom.setText(strDateData);
                show.performClick();
            }
        });

        tahun1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.YEAR, -1);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = df.format(c.getTime());
                dateFrom.setText(formattedDate);
                show.performClick();
            }
        });
    }

    private DateTime convertToDateTime(String stringToConvert) {
        String[] newStringArray = convertStringToArray(stringToConvert);
        int year = Integer.parseInt(newStringArray[2].trim());
        int day = Integer.parseInt(newStringArray[0].trim());
        int month = Integer.parseInt(newStringArray[1].trim());
        LocalDate mLocalDate = new LocalDate(year, month, day);
        DateTime firstDateTime = mLocalDate.toDateTime(LocalTime.fromDateFields(mLocalDate.toDate()));
        return firstDateTime;
    }

    private String[] convertStringToArray(String stringToConvert){
        String[] newStringArray = stringToConvert.split("/");
        return newStringArray;
    }
}
