package id.ac.polinema.tcttcakron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TransactionAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_admin);
    }

    public void handlerOnClickOffline(View view) {
        Intent intent = new Intent(this, TransactionOffline.class);
        startActivity(intent);
    }

    public void handlerOnClickOnline(View view) {
        Intent intent = new Intent(this, TransactionOnline.class);
        startActivity(intent);
    }

    public void handlerOnClickBack(View view) {
        Intent intent = new Intent(this, FiturAdmin.class);
        startActivity(intent);
    }
}
