package id.ac.polinema.tcttcakron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
    }

    public void handlerOnClickFitur(View view) {
        Intent intent = new Intent(getApplicationContext(), FiturAdmin.class);
        startActivity(intent);
    }

    public void handlerOnClickStatistik(View view) {
        Intent intent = new Intent(getApplicationContext(), StatisticActivity.class);
        startActivity(intent);
    }
}
