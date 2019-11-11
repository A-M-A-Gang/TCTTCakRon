package id.ac.polinema.tcttcakron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.ac.polinema.tcttcakron.models.User;

public class SignUpActivity extends AppCompatActivity {
    EditText username, password;
    Button signUp;
    FirebaseDatabase database;
    DatabaseReference karyawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        database = FirebaseDatabase.getInstance();
        karyawan = database.getReference("Karyawan");

        username = findViewById(R.id.in_username_signup);
        password = findViewById(R.id.in_password_signup);
        signUp = findViewById(R.id.signUpButton);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User(username.getText().toString(), password.getText().toString());

                karyawan.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(user.getUsername()).exists()){
                            Toast.makeText(SignUpActivity.this, "Username telah ada", Toast.LENGTH_SHORT).show();
                        } else {
                            karyawan.child(user.getUsername()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(SignUpActivity.this, "Tidak connect", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
