package id.ac.polinema.tcttcakron;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.ac.polinema.tcttcakron.models.User;

public class LoginAdmin extends AppCompatActivity {
    EditText username, password;
    Button login;
    FirebaseDatabase database;
    DatabaseReference karyawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        database = FirebaseDatabase.getInstance();
        karyawan = database.getReference("Karyawan");
        username = findViewById(R.id.in_username);
        password = findViewById(R.id.in_password);
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void signIn(final String username, final String password) {
        karyawan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if (!username.isEmpty()){
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if (login.getPassword().equals("")){
                            Toast.makeText(LoginAdmin.this, "Isi password dulu", Toast.LENGTH_SHORT).show();
                        } else if (login.getPassword().equals(password)){
                            Toast.makeText(LoginAdmin.this, "Success login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SettingAdmin.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginAdmin.this, "Password salah", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(LoginAdmin.this, "Tidak ada akun, silahkan daftar dulu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginAdmin.this, "Username tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginAdmin.this, "?????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handlerOnClickSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}