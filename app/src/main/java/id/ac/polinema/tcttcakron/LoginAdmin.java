package id.ac.polinema.tcttcakron;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class LoginAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
    }

    public void handlerOnClickLogin(View view) {
        setContentView(R.layout.activity_fitur_admin);
    }
}
