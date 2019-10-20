package id.ac.polinema.tcttcakron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAdmin extends AppCompatActivity {
    EditText username, password;
    FirebaseDatabase database;
    DatabaseReference karyawan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        username = findViewById(R.id.in_username);
        password = findViewById(R.id.in_password);
        database = FirebaseDatabase.getInstance();
        karyawan = database.getReference("Karyawan");
    }

    public void handlerOnClickLogin(View view) {
        signIn(username.getText().toString(), password.getText().toString());
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//
//        Intent intent = new Intent(this, FiturAdmin.class);
//        startActivity(intent);
    }

    private void signIn(final String username, final String password) {
        karyawan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if (!username.isEmpty()){
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if (login.getPassword().equals(password)){
                            Toast.makeText(LoginAdmin.this, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginAdmin.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(LoginAdmin.this, "Username Not Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginAdmin.this, "Not Found", Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//                // ...
//            }
//        }
//    }
}
