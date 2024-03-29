package id.ac.polinema.tcttcakron;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FiturAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_admin);
    }

    public void handlerOnClickUpdate(View view) {
        Intent intent = new Intent(this, UpdateAdmin.class);
        startActivity(intent);
    }

    public void handlerOnClickTransaction(View view) {
        Intent intent = new Intent(this, TransactionAdmin.class);
        startActivity(intent);
    }

    public void handlerOnClickAdd(View view) {
        Intent intent = new Intent(this, AddAdmin.class);
        startActivity(intent);
    }

    public void handlerOnClickDelete(View view) {
        Intent intent = new Intent(this, DeleteAdmin.class);
        startActivity(intent);
    }
}
