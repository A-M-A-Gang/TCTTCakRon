package id.ac.polinema.tcttcakron;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SettingAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_admin);
    }

    public void handlerOnClickHome(View view) {
        Intent intent = new Intent(this, FiturAdmin.class);
        startActivity(intent);
    }

    public void handlerOnClickHistory(View view) {
        Intent intent = new Intent(this, HistoryAdmin.class);
        startActivity(intent);
    }

    public void handlerOnClickLogout(View view) {
        Intent intent = new Intent(this, LoginAdmin.class);
        startActivity(intent);
    }
}
